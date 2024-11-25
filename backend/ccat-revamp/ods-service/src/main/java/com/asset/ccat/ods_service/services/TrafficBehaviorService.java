/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.ods_service.services;

import com.asset.ccat.ods_service.configurations.Properties;
import com.asset.ccat.ods_service.database.dao.DSSDao;
import com.asset.ccat.ods_service.defines.Defines;
import com.asset.ccat.ods_service.exceptions.ODSException;
import com.asset.ccat.ods_service.logger.CCATLogger;
import com.asset.ccat.ods_service.models.requests.DSSReportRequest;
import com.asset.ccat.ods_service.models.responses.DSSResponseModel;
import com.asset.ccat.ods_service.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author wael.mohamed
 */
@Service
public class TrafficBehaviorService {

    @Autowired
    private Properties properties;
    @Autowired
    private DSSDao dSSDao;

    public DSSResponseModel getTrafficReport(DSSReportRequest request) throws ODSException {
        CCATLogger.DEBUG_LOGGER.debug("Starting TrafficBehaviorService - retrieveRecords for Page "
                + Defines.CONSTANANTS.PAGE_TRAFFIC_BEHAVIOR + "| msisdn " + request.getMsisdn());

        DSSResponseModel dSSResponseModel = dSSDao.retrieveRecords(properties.getTrafficBehaviorProcedure(), request.getMsisdn(),
                request.getDateFrom(), request.getDateFrom(),
                request.getBtivr(), Defines.CONSTANANTS.PAGE_TRAFFIC_BEHAVIOR);
        CCATLogger.DEBUG_LOGGER.debug("Ending TrafficBehaviorService - retrieveRecords for Page: " + Defines.CONSTANANTS.PAGE_TRAFFIC_BEHAVIOR + "| msisdn " + request.getMsisdn());
        return dSSResponseModel;
    }
}
