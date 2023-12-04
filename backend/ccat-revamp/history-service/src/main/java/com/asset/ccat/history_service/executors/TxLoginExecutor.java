package com.asset.ccat.history_service.executors;

import com.asset.ccat.history_service.cache.MessagesCache;
import com.asset.ccat.history_service.configurations.Properties;
import com.asset.ccat.history_service.exceptions.HistoryException;
import com.asset.ccat.history_service.logger.CCATLogger;
import com.asset.ccat.history_service.services.TxLoginService;
import com.asset.ccat.rabbitmq.models.TxLoginModel;
import com.asset.concurrent.executor.EagerBatchExecutor;
import com.asset.concurrent.factory.CustomThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author assem.hassan
 */
@Component
public class TxLoginExecutor extends EagerBatchExecutor<TxLoginModel, TxLoginModel> {

    @Autowired
    private TxLoginService txLoginService;

    @Autowired
    private MessagesCache messagesCache;

    public TxLoginExecutor(Properties properties) {
        super(properties.getFootprintBatchSize(),
                properties.getFootprintExecutorPoolSize(),
                properties.getFootprintExecutorPoolSize(),
                properties.getFootprintExecutorPoolKeepAliveTime(),
                TimeUnit.MILLISECONDS,
                properties.getFootprintBatchSize(),
                new CustomThreadFactory("TxLogin-Executor-Pool"));
    }

    @Override
    protected void consumeBatchList(ArrayList<TxLoginModel> batchList) {
        try {
            CCATLogger.DEBUG_LOGGER.debug("Start inserting tx_adjustment batch of size [" + batchList.size() + "]");
            txLoginService.insertTxLoginBatch(batchList);
            CCATLogger.DEBUG_LOGGER.debug("Inserted tx_adjustment batch of size [" + batchList.size() + "] successfully");
        } catch (HistoryException ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to insert batch with error [" + messagesCache.getErrorMsg(ex.getErrorCode()) + "]");
        }
    }

    @Override
    protected TxLoginModel processBeforeAdding(TxLoginModel payload) {
        return payload;
    }
}
