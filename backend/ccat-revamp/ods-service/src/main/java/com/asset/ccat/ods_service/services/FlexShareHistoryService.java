package com.asset.ccat.ods_service.services;

import com.asset.ccat.ods_service.configurations.Properties;
import com.asset.ccat.ods_service.database.dao.AccountHistoryDao;
import com.asset.ccat.ods_service.database.dao.FlexShareHistoryDao;
import com.asset.ccat.ods_service.exceptions.ODSException;
import com.asset.ccat.ods_service.logger.CCATLogger;
import com.asset.ccat.ods_service.models.SubscriberActivityModel;
import com.asset.ccat.ods_service.models.requests.AccountHistoryRequest;
import com.asset.ccat.ods_service.models.requests.GetFlexShareHistoryRequest;
import com.asset.ccat.ods_service.models.responses.GetFlexShareHistoryResponse;
import com.asset.ccat.ods_service.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class FlexShareHistoryService {

    @Autowired
    private FlexShareHistoryDao flexShareHistoryDao;

    @Autowired
    private DateUtils dateUtils;

    @Autowired
    private Properties properties;

    public GetFlexShareHistoryResponse getFlexShareHistory(GetFlexShareHistoryRequest request) throws ODSException {
        CCATLogger.DEBUG_LOGGER.debug("Starting FlexShareHistoryService - retrieveRecords for Page | msisdn " + request.getMsisdn());
        CCATLogger.DEBUG_LOGGER.info("Start serving get flex share history for msisdn " + request.getMsisdn());
        //TODO default search period
        Integer defaultSearchPeriod = properties.getFlexDefaultSearchPeriod() == null ? 0 : properties.getFlexDefaultSearchPeriod();
        if (request.getDateFrom() == null && request.getDateTo() == null && defaultSearchPeriod > 0) {
            request.setDateTo(System.currentTimeMillis()); // set to date to today
            // set from date to today minus x number of days where x equals the default search period
            Calendar today = Calendar.getInstance();
            today.add(Calendar.DATE, defaultSearchPeriod * -1);
            request.setDateFrom(today.getTimeInMillis());
        }
        CCATLogger.DEBUG_LOGGER.debug("Formatting dates..");
        String fromDate = dateUtils.getFlexShareFormattedDate(new Date(request.getDateFrom()));
        String toDate = dateUtils.getFlexShareFormattedDate(new Date(request.getDateTo()));

        CCATLogger.DEBUG_LOGGER.debug("Formatted from date >> " + fromDate);
        CCATLogger.DEBUG_LOGGER.debug("Formatted from date >> " + toDate);

        CCATLogger.DEBUG_LOGGER.debug("Call execute Flex Share History Stored procedure");
        GetFlexShareHistoryResponse response = flexShareHistoryDao.retrieveHistory(request.getMsisdn(),
                fromDate,
                toDate,
                request.getFlag());
        CCATLogger.DEBUG_LOGGER.info("Finished serving get flex share history for msisdn " + request.getMsisdn());
        CCATLogger.DEBUG_LOGGER.debug("Ended FlexShareHistoryService - retrieveRecords for Page | msisdn " + request.getMsisdn());

        return response;

    }
}
