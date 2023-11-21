package com.asset.ccat.history_service.managers;

import com.asset.ccat.history_service.configurations.Properties;
import com.asset.ccat.history_service.defines.Defines;
import com.asset.ccat.history_service.executors.FootprintExecutor;
import com.asset.ccat.history_service.logger.CCATLogger;
import com.asset.ccat.history_service.tasks.FootprintDequeueTask;
import com.asset.rabbitmq.client.util.RabbitmqUtil;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author marwa.elshawarby
 */
@Component
public class FootprintManager {

    @Autowired
    private FootprintDequeueTask footprintDequeueTask;

    @Autowired
    private RabbitmqUtil rabbitmqUtil;

    @Autowired
    private Properties properties;

    @Autowired
    private FootprintExecutor footprintExecutor;

    private DirectMessageListenerContainer directMessageListenerContainer;

    public void start() {

        try {
            CCATLogger.DEBUG_LOGGER.debug("Starting footprint dequeues");
            directMessageListenerContainer = rabbitmqUtil.connectToExistingQueue(
                    Defines.RABBIT_MQ.FOOTPRINT_QUEUE,
                    footprintDequeueTask,
                    "handleReceivedFootprint",
                    properties.getFootprintDequeuersNumber());
            CCATLogger.DEBUG_LOGGER.debug("Starting footprint dequeues finished successfully");
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to start footprint manager with error[" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Failed to start footprint manager", ex);
        }
    }

    public void shutdown() {
        try {
            if (directMessageListenerContainer != null) {
                CCATLogger.DEBUG_LOGGER.debug("Stopping footprint dequeues");
                directMessageListenerContainer.stop();
            }
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to stop footprint dequeues with error[" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Failed to stop footprint dequeues", ex);
        }

        try {
            CCATLogger.DEBUG_LOGGER.debug("Shutting down footprint executor");
            footprintExecutor.shutdownExecutor();
            footprintExecutor.awaitExecutorTermination();
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to stop footprint executor with error[" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Failed to stop footprint executor", ex);
        }
    }
}
