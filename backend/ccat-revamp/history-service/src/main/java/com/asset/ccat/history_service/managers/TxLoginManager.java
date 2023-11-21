package com.asset.ccat.history_service.managers;

import com.asset.ccat.history_service.configurations.Properties;
import com.asset.ccat.history_service.defines.Defines;
import com.asset.ccat.history_service.executors.TxLoginExecutor;
import com.asset.ccat.history_service.logger.CCATLogger;
import com.asset.ccat.history_service.tasks.TxLoginDequeueTask;
import com.asset.rabbitmq.client.util.RabbitmqUtil;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author assem.hassan
 */
@Component
public class TxLoginManager {

    @Autowired
    private TxLoginDequeueTask txLoginDequeueTask;

    @Autowired
    private RabbitmqUtil rabbitmqUtil;

    @Autowired
    private Properties properties;

    @Autowired
    private TxLoginExecutor txLoginExecutor;

    private DirectMessageListenerContainer directMessageListenerContainer;

    public void start() {

        try {
            CCATLogger.DEBUG_LOGGER.debug("Starting TX_LOGIN dequeues");
            directMessageListenerContainer = rabbitmqUtil.connectToExistingQueue(
                    Defines.RABBIT_MQ.TX_LOGIN_QUEUE,
                    txLoginDequeueTask,
                    "handleReceivedTxLogin",
                    properties.getFootprintDequeuersNumber());
            CCATLogger.DEBUG_LOGGER.debug("Ending TX_LOGIN dequeues finished successfully");
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to start TX_LOGIN manager with error[" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Failed to start TX_LOGIN manager", ex);
        }
    }

    public void shutdown() {
        try {
            if (directMessageListenerContainer != null) {
                CCATLogger.DEBUG_LOGGER.debug("Stopping TX_LOGIN dequeues");
                directMessageListenerContainer.stop();
            }
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to stop TX_LOGIN dequeues with error[" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Failed to stop TX_LOGIN dequeues", ex);
        }

        try {
            CCATLogger.DEBUG_LOGGER.debug("Shutting down TX_LOGIN executor");
            txLoginExecutor.shutdownExecutor();
            txLoginExecutor.awaitExecutorTermination();
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to stop TX_LOGIN executor with error[" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Failed to stop TX_LOGIN executor", ex);
        }
    }
}
