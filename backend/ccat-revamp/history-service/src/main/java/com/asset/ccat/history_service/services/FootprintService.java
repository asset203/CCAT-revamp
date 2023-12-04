package com.asset.ccat.history_service.services;

import com.asset.ccat.history_service.database.dao.FootprintDao;
import com.asset.ccat.history_service.database.dao.FootprintDetailsDao;
import com.asset.ccat.history_service.defines.Defines;
import com.asset.ccat.history_service.defines.ErrorCodes;
import com.asset.ccat.history_service.exceptions.HistoryException;
import com.asset.ccat.history_service.logger.CCATLogger;
import com.asset.ccat.rabbitmq.models.FootprintModel;
import com.asset.ccat.rabbitmq.models.FootprintDetailsModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Assem Hassan
 */
@Component
public class FootprintService {

    @Autowired
    private FootprintDao footprintDao;

    @Autowired
    private FootprintDetailsDao footprintDetailsDao;

    @Transactional(rollbackFor = Exception.class)
    public void insertFootprintBatch(List<FootprintModel> footprintBatchList) throws HistoryException {
        int[][] footprintBatchResult;
        int[][] footprintDetailsBatchResult;
        try {
            if (Objects.isNull(footprintBatchList) || footprintBatchList.isEmpty()) {
                CCATLogger.DEBUG_LOGGER.error("FootprintBatchList List is Empty or Null!!");
                throw new HistoryException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR);
            } else {
                List<FootprintDetailsModel> footprintDetailsBatchList = getFootprintDetailsBatchList(footprintBatchList);
                footprintBatchResult = footprintDao.insertFootprintBatch(footprintBatchList);
                CCATLogger.DEBUG_LOGGER.debug("insertFootprintBatch() ResultRowsCount=[" +
                        Arrays.deepToString(footprintBatchResult) + "]");

                if (!footprintDetailsBatchList.isEmpty()) {
                    footprintDetailsBatchResult = footprintDetailsDao.insertFootPrintDetailsBatch(footprintDetailsBatchList);
                    CCATLogger.DEBUG_LOGGER.debug("insertFootprintBatch() ResultRowsCount=[" +
                            Arrays.deepToString(footprintDetailsBatchResult) + "]");
                }
            }
        } catch (HistoryException ex) {
            CCATLogger.FOOTPRINT_LOGGER
                    .error("Database error occurred while executing insertFootprintBatch()");
            CCATLogger.ERROR_LOGGER
                    .error("Database error occurred while executing insertFootprintBatch()");
            throw new HistoryException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR);
        }
    }

    private List<FootprintDetailsModel> getFootprintDetailsBatchList(List<FootprintModel> footprintBatchList) {
        List<FootprintDetailsModel> footprintDetailsBatchList = new ArrayList<>();
        for (FootprintModel footprint : footprintBatchList) {
            if (!Objects.isNull(footprint.getFootPrintDetails())
                    && !footprint.getFootPrintDetails().isEmpty()) {
                footprint.getFootPrintDetails()
                        .forEach(detail -> detail.setRequestId(footprint.getRequestId()));
                footprintDetailsBatchList.addAll(footprint.getFootPrintDetails());
            }
        }
        return footprintDetailsBatchList;
    }
}
