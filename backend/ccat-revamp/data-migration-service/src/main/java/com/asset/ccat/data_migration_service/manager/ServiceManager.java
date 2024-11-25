package com.asset.ccat.data_migration_service.manager;

import com.asset.ccat.data_migration_service.configurations.Properties;
import com.asset.ccat.data_migration_service.logger.CCATLogger;
import com.asset.ccat.data_migration_service.services.ConsumeDataService;
import com.asset.ccat.data_migration_service.services.GenerateDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Assem.Hassan
 */
@Component
public class ServiceManager {

    @Autowired
    GenerateDataService generateDataService;
    @Autowired
    ConsumeDataService consumeDataService;

    @Autowired
    private Properties properties;

    @EventListener
    public void startupEvent(ContextRefreshedEvent event) {
        try {
            if (Objects.equals(properties.getEngineMode(), 1)) {
                generateDataService.generateDataTables();
            } else {
                consumeDataService.consumeCSVFiles();
            }

        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("ERROR while startupEvent() " + ex);
            CCATLogger.ERROR_LOGGER.error("ERROR while startupEvent() ", ex);
        }
    }
}
