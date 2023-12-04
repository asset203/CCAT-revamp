package com.asset.ccat.history_service.executors;

import com.asset.ccat.history_service.cache.MessagesCache;
import com.asset.ccat.history_service.configurations.Properties;
import com.asset.ccat.history_service.database.dao.SubscriberAdjustmentDao;
import com.asset.ccat.history_service.exceptions.HistoryException;
import com.asset.ccat.history_service.logger.CCATLogger;
import com.asset.ccat.rabbitmq.models.TxAdjustmentModel;
import com.asset.concurrent.executor.EagerBatchExecutor;
import com.asset.concurrent.factory.CustomThreadFactory;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author wael.mohamed
 */
@Component
public class TxAdjustmentExecutor extends EagerBatchExecutor<TxAdjustmentModel, TxAdjustmentModel> {

    @Autowired
    private SubscriberAdjustmentDao adjustmentDao;

    @Autowired
    private MessagesCache messagesCache;

    public TxAdjustmentExecutor(Properties properties) {
        super(properties.getFootprintBatchSize(),
                properties.getFootprintExecutorPoolSize(),
                properties.getFootprintExecutorPoolSize(),
                properties.getFootprintExecutorPoolKeepAliveTime(),
                TimeUnit.MILLISECONDS,
                properties.getFootprintBatchSize(),
                new CustomThreadFactory("TxAdjustment-Executor-Pool"));
    }

    @Override
    protected void consumeBatchList(ArrayList<TxAdjustmentModel> batchList) {
        try {
            CCATLogger.DEBUG_LOGGER.debug("Start inserting tx_adjustment batch of size [" + batchList.size() + "]");
            adjustmentDao.insertAdjustmentBatch(batchList);
            CCATLogger.DEBUG_LOGGER.debug("Inserted tx_adjustment batch of size [" + batchList.size() + "] successfully");
        } catch (HistoryException ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to insert batch with error [" + messagesCache.getErrorMsg(ex.getErrorCode()) + "]");
        }
    }

    @Override
    protected TxAdjustmentModel processBeforeAdding(TxAdjustmentModel payload) {
        return payload;
    }
}
