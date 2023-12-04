/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.dynamic_page.manager;

import com.asset.ccat.dynamic_page.factories.CustomThreadFactory;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.tasks.RefreshDynamicPagesTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Mahmoud Shehab
 */
@Component
public class ServiceManager {

    private final ScheduledThreadPoolExecutor poolTaskScheduler;
    private final RefreshDynamicPagesTask refreshDynamicPagesTask;

    public ServiceManager(RefreshDynamicPagesTask refreshDynamicPagesTask) {
        this.poolTaskScheduler = new ScheduledThreadPoolExecutor(1, new CustomThreadFactory("RefreshPool", "RefreshTask"));
        this.refreshDynamicPagesTask = refreshDynamicPagesTask;
    }

    private void init() {
        poolTaskScheduler.scheduleWithFixedDelay(refreshDynamicPagesTask, 0, 30000, TimeUnit.MILLISECONDS); // TODO: take from properties
    }

    @EventListener
    public void startupEvent(ContextRefreshedEvent event) {
        CCATLogger.DEBUG_LOGGER.debug("Starting user management service");
        try {
            init();
        } catch (Exception ex) {
            CCATLogger.ERROR_LOGGER.error("Failed to start dynamic page service", ex);
        }
    }
}
