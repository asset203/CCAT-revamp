package com.asset.ccat.history_service.tasks;

import com.asset.ccat.history_service.executors.TxLoginExecutor;
import com.asset.ccat.history_service.logger.CCATLogger;
import com.asset.ccat.rabbitmq.models.TxAdjustmentModel;
import com.asset.ccat.rabbitmq.models.TxLoginModel;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.stereotype.Component;

/**
 * @author assem.hassan
 */
@Component
public class TxLoginDequeueTask {
    private final TxLoginExecutor txLoginExecutor;

    public TxLoginDequeueTask(TxLoginExecutor txLoginExecutor) {
        this.txLoginExecutor = txLoginExecutor;
    }

    private void handleReceivedTxLogin(byte[] body) {
        try {
            TxLoginModel loginAction = (TxLoginModel) SerializationUtils.deserialize(body);
            txLoginExecutor.execute(loginAction);
        } catch (Throwable th) {
            CCATLogger.DEBUG_LOGGER.error("An exception caught in tx_login dequeue task connection to rabbitmq " + th.getMessage());
            CCATLogger.ERROR_LOGGER.error("An exception caught in tx_login dequeue task connection to rabbitmq ", th);
        }
    }

}
