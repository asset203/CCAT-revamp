package com.asset.ccat.dynamic_page.tasks;

import com.asset.ccat.dynamic_page.cache.DynamicPagesCache;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.models.dynamic_page.DynamicPageModel;
import com.asset.ccat.dynamic_page.services.DynamicPagesService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class RefreshDynamicPagesTask implements Runnable {
    @Autowired
    private DynamicPagesService dynamicPagesService;

    @Autowired
    private DynamicPagesCache dynamicPagesCache;

    private final ReentrantLock reentrantLock;

    public RefreshDynamicPagesTask() {
        this.reentrantLock = new ReentrantLock();
    }

    @Override
    public void run() {
        try {
            CCATLogger.DEBUG_LOGGER.debug("Start refreshing dynamic pages cache");
            Long start = System.currentTimeMillis();
            HashMap<Integer, DynamicPageModel> pages = dynamicPagesService.retrieveAllPagesWithDetails();
            if (pages != null && !pages.isEmpty()) {
                reentrantLock.lock();
                dynamicPagesCache.setDynamicPages(pages);
            }
            CCATLogger.DEBUG_LOGGER.debug("Finished refreshing dynamic pages cache in [" + (System.currentTimeMillis() - start) + "]");

        } catch (Throwable th) {
            CCATLogger.DEBUG_LOGGER.debug("Failed to refresh dynamic pages cache");
            CCATLogger.ERROR_LOGGER.error("Failed to refresh dynamic pages cache", th);
        } finally {
            reentrantLock.unlock();
        }

    }
}
