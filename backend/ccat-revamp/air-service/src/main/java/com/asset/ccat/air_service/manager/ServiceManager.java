package com.asset.ccat.air_service.manager;

import com.asset.ccat.air_service.cache.AIRRequestsCache;
import com.asset.ccat.air_service.configurations.Properties;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author Mahmoud Shehab
 */
@Component
public class ServiceManager {

    @Autowired
    Properties Properties;

    @Autowired
    AIRRequestsCache airRequestsCache;

    @EventListener
    public void startupEvent(ContextRefreshedEvent event) throws AIRServiceException {
        CCATLogger.DEBUG_LOGGER.debug("starting air service");
        CCATLogger.DEBUG_LOGGER.debug("starting load cached air commands");
        airRequestsCache.init();
    }
}
