package com.asset.ccat.history_service.services;

import com.asset.ccat.history_service.database.dao.FootPrintReportDao;
import com.asset.ccat.history_service.defines.Defines;
import com.asset.ccat.history_service.defines.ErrorCodes;
import com.asset.ccat.history_service.exceptions.HistoryException;
import com.asset.ccat.history_service.logger.CCATLogger;
import com.asset.ccat.history_service.models.requests.foot_print.GetFootPrintReportRequest;
import com.asset.ccat.history_service.models.responses.foot_print.GetFootPrintReportResponse;
import com.asset.ccat.rabbitmq.models.FootprintModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author Assem.Hassan
 */
@Service
public class FootPrintReportService {

    @Autowired
    FootPrintReportDao footPrintReportDao;

    public GetFootPrintReportResponse getFootPrintReport(GetFootPrintReportRequest getFootPrintReportRequest) throws HistoryException {

        HashMap<String, FootprintModel> footPrintReportMap = footPrintReportDao.getFootPrintReport(getFootPrintReportRequest);
        if (Objects.isNull(footPrintReportMap) || footPrintReportMap.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.error("No Data Found!!");
            throw new HistoryException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR);
        }

        return new GetFootPrintReportResponse(footPrintReportMap);
    }
}
