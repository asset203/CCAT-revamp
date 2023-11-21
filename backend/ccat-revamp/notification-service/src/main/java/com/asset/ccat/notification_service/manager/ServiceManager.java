package com.asset.ccat.notification_service.manager;

import com.asset.ccat.notification_service.cache.CachedLookups;
import com.asset.ccat.notification_service.configurations.Properties;
import com.asset.ccat.notification_service.exceptions.NotificationException;
import com.asset.ccat.notification_service.factories.CustomThreadFactory;
import com.asset.ccat.notification_service.logger.CCATLogger;
import com.asset.ccat.notification_service.models.SMSActionModel;
import com.asset.ccat.notification_service.models.SmsTemplateModel;
import com.asset.ccat.notification_service.services.SmsTemplateCSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Mahmoud Shehab
 */
@Component
public class ServiceManager {
    private final ScheduledThreadPoolExecutor poolTaskScheduler;
    private final ReentrantLock reentrantLock;
    private final HashMap<String, Object> cachedLookupsMap;

    @Autowired
    SmsTemplateCSService smsTemplateCSService;

    @Autowired
    private CachedLookups cachedLookups;

    @Autowired
    private Properties properties;

    public ServiceManager() {
        poolTaskScheduler = new ScheduledThreadPoolExecutor(2, new CustomThreadFactory("RefreshLookupsPool", "RefreshLookupsTask"));
        reentrantLock = new ReentrantLock();
        cachedLookupsMap = new HashMap<>();
    }

    @EventListener
    public void startupEvent(ApplicationPreparedEvent event) {
        try {
            init();
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("ERROR while start config server " + ex);
            CCATLogger.ERROR_LOGGER.error("ERROR while start config server ", ex);
        }
    }

    public void init() {
        CCATLogger.DEBUG_LOGGER.info("Start retrieve serviceClasses");
        Runnable refreshLockupsTask = () -> {
            refreshLookups();
        };
        CCATLogger.DEBUG_LOGGER.info("Start init runnable " + refreshLockupsTask);
        poolTaskScheduler.scheduleWithFixedDelay(refreshLockupsTask, 0, 60000, TimeUnit.MILLISECONDS);
    }

    public void refreshLookups() {
        try {
            CCATLogger.DEBUG_LOGGER.info("Start retrieve serviceClasses");
            long start = System.currentTimeMillis();
            List<SmsTemplateModel> serviceClasses = smsTemplateCSService.listSmsTemplates();
            if (serviceClasses != null && !serviceClasses.isEmpty()) {
                reentrantLock.lock();
                cachedLookups.setTemplates(serviceClasses);
            }
            CCATLogger.DEBUG_LOGGER.info("Retrieve serviceClasses finished successfully");
        } catch (NotificationException ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while refreshing lookups");
            CCATLogger.ERROR_LOGGER.error("Error while refreshing lookups", ex);
        } finally {
            reentrantLock.unlock();
        }
    }
}
