package com.asset.ccat.gateway.tasks;

import com.asset.ccat.gateway.logger.CCATLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheCleanupTask {
    private final CacheManager cacheManager;

    @Autowired
    public CacheCleanupTask(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Scheduled(fixedRate = 600000)
    public void clearTokenCache() {
        CCATLogger.DEBUG_LOGGER.debug("Starting scheduled token cache cleanup");
        try {
            var cache = cacheManager.getCache("tokenCache");
            if (cache != null) {
                cache.clear();
                CCATLogger.DEBUG_LOGGER.debug("Token cache cleanup completed successfully");
            }
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("Error during token cache cleanup: {}", e.getMessage());
            CCATLogger.ERROR_LOGGER.error("Error during token cache cleanup: ", e);
        }
    }
}