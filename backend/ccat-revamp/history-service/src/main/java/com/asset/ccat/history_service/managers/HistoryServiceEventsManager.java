package com.asset.ccat.history_service.managers;

import com.asset.ccat.history_service.logger.CCATLogger;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author marwa.elshawarby
 */
@Component
public class HistoryServiceEventsManager {

    private final FootprintManager footprintManager;
    private final TxAdjustmentManager adjustmentManager;
    private final TxLoginManager txLoginManager;

    public HistoryServiceEventsManager(FootprintManager footprintManager, TxAdjustmentManager adjustmentManager, TxLoginManager txLoginManager) {
        this.footprintManager = footprintManager;
        this.adjustmentManager = adjustmentManager;
        this.txLoginManager = txLoginManager;
    }

    @EventListener
    public final void initialize(ContextRefreshedEvent event) throws Exception {
        CCATLogger.DEBUG_LOGGER.info("Starting History Service Intialization...");

        CCATLogger.DEBUG_LOGGER.debug("Calling footprint manager initialization");
        footprintManager.start();
        CCATLogger.DEBUG_LOGGER.debug("Calling tx_adjustment manager initialization");
        adjustmentManager.start();
        CCATLogger.DEBUG_LOGGER.debug("Calling tx_login manager initialization");
        txLoginManager.start();
        CCATLogger.DEBUG_LOGGER.info("History Service Initialization Finished Successfully...");
    }

    @EventListener
    public final void shutdown(ContextClosedEvent event) {

        CCATLogger.DEBUG_LOGGER.info("Starting History Service Shutdown...");

        CCATLogger.DEBUG_LOGGER.debug("Calling footprint manager shutdown");
        footprintManager.shutdown();
        CCATLogger.DEBUG_LOGGER.debug("Calling tx_adjustment manager shutdown");
        adjustmentManager.shutdown();
        CCATLogger.DEBUG_LOGGER.debug("Calling tx_login manager shutdown");
        txLoginManager.shutdown();
        CCATLogger.DEBUG_LOGGER.info("History Service Shutdown Finished Successfully...");
    }
}
