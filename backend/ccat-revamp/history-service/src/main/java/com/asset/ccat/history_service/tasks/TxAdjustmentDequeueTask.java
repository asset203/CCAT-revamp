package com.asset.ccat.history_service.tasks;

import com.asset.ccat.history_service.executors.TxAdjustmentExecutor;
import com.asset.ccat.history_service.logger.CCATLogger;
import com.asset.ccat.rabbitmq.models.TxAdjustmentModel;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.stereotype.Component;

/**
 *
 * @author wael.mohamed
 */
@Component
public class TxAdjustmentDequeueTask {
    private final TxAdjustmentExecutor txAdjustmentExecutor;

    public TxAdjustmentDequeueTask(TxAdjustmentExecutor txAdjustmentExecutor) {
        this.txAdjustmentExecutor = txAdjustmentExecutor;
    }

    private void handleRecievedTxAdjustment(byte[] body) {
        try {
            TxAdjustmentModel adjustment = (TxAdjustmentModel) SerializationUtils.deserialize(body);
            txAdjustmentExecutor.execute(adjustment);
        } catch (Throwable th) {
            CCATLogger.DEBUG_LOGGER.error("An exception caught in tx_adjustment dequeuer task connection to rabbitmq " + th.getMessage());
            CCATLogger.ERROR_LOGGER.error("An exception caught in tx_adjustment dequeuer task connection to rabbitmq ", th);
        }
    }

}
