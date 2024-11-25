package com.asset.ccat.history_service.managers;

import com.asset.ccat.history_service.configurations.Properties;
import com.asset.ccat.history_service.defines.Defines;
import com.asset.ccat.history_service.executors.TxAdjustmentExecutor;
import com.asset.ccat.history_service.logger.CCATLogger;
import com.asset.ccat.history_service.tasks.TxAdjustmentDequeueTask;
import com.asset.rabbitmq.client.util.RabbitmqUtil;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author wael.mohamed
 */
@Component
public class TxAdjustmentManager {

    @Autowired
    private TxAdjustmentDequeueTask adjustmentDequeueTask;

    @Autowired
    private RabbitmqUtil rabbitmqUtil;

    @Autowired
    private Properties properties;

    @Autowired
    private TxAdjustmentExecutor adjustmentExecutor;

    private DirectMessageListenerContainer directMessageListenerContainer;

    public void start() {

        try {
            CCATLogger.DEBUG_LOGGER.debug("Starting tx_adjustment dequeuers");
            directMessageListenerContainer = rabbitmqUtil.connectToExistingQueue(
                    Defines.RABBIT_MQ.TX_ADJUSTMENT_QUEUE,
                    adjustmentDequeueTask,
                    "handleRecievedTxAdjustment",
                    properties.getFootprintDequeuersNumber());
            CCATLogger.DEBUG_LOGGER.debug("Starting tx_adjustment dequeuers finished successfully");
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to start tx_adjustment manager with error[" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Failed to start tx_adjustment manager", ex);
        }
    }

    public void shutdown() {
        try {
            if (directMessageListenerContainer != null) {
                CCATLogger.DEBUG_LOGGER.debug("Stopping tx_adjustment dequeuers");
                directMessageListenerContainer.stop();
            }
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to stop tx_adjustment dequeuers with error[" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Failed to stop tx_adjustment dequeuers", ex);
        }

        try {
            CCATLogger.DEBUG_LOGGER.debug("Shutting down tx_adjustment executor");
            adjustmentExecutor.shutdownExecutor();
            adjustmentExecutor.awaitExecutorTermination();
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to stop tx_adjustment executor with error[" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Failed to stop tx_adjustment executor", ex);
        }
    }
}
