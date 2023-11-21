package com.asset.ccat.history_service.executors;

import com.asset.ccat.history_service.cache.MessagesCache;
import com.asset.ccat.history_service.configurations.Properties;
import com.asset.ccat.history_service.exceptions.HistoryException;
import com.asset.ccat.history_service.logger.CCATLogger;
import com.asset.ccat.history_service.services.FootprintService;
import com.asset.ccat.rabbitmq.models.FootprintModel;
import com.asset.concurrent.executor.EagerBatchExecutor;
import com.asset.concurrent.factory.CustomThreadFactory;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author marwa.elshawarby
 */
@Component
public class FootprintExecutor extends EagerBatchExecutor<FootprintModel, FootprintModel> {

    @Autowired
    private FootprintService footprintService;

    @Autowired
    private MessagesCache messagesCache;

    public FootprintExecutor(Properties properties) {
        super(properties.getFootprintBatchSize(),
                properties.getFootprintExecutorPoolSize(),
                properties.getFootprintExecutorPoolSize(),
                properties.getFootprintExecutorPoolKeepAliveTime(),
                TimeUnit.MILLISECONDS,
                properties.getFootprintBatchSize(),
                new CustomThreadFactory("Footprint-Executor-Pool"));
    }

    @Override
    protected void consumeBatchList(ArrayList<FootprintModel> batchList) {
        try {
            CCATLogger.DEBUG_LOGGER.debug("Start inserting footprint batch of size [" + batchList.size() + "]");
            footprintService.insertFootprintBatch(batchList);
            CCATLogger.DEBUG_LOGGER.debug("Inserted footprint batch of size [" + batchList.size() + "] successfully");
        } catch (HistoryException ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to insert batch with error [" + messagesCache.getErrorMsg(ex.getErrorCode()) + "]");
        }
    }

    @Override
    protected FootprintModel processBeforeAdding(FootprintModel payload) {
        return payload;
    }
}
