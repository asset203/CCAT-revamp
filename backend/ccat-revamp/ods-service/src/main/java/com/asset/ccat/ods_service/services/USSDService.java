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
public class USSDService {

    @Autowired
    private Properties properties;
    @Autowired
    private DSSDao dSSDao;
    
    public DSSResponseModel getUSSDReport(DSSReportRequest request) throws ODSException {
        CCATLogger.DEBUG_LOGGER.debug("Starting USSDService - retrieveRecords for Page "
                + Defines.CONSTANANTS.PAGE_USSD + "| msisdn " + request.getMsisdn());
        DSSResponseModel dSSResponseModel = dSSDao.retrieveUSSDRecords(properties.getUssdProcedure(), request.getMsisdn(),
                request.getDateFrom(), request.getDateFrom(), Defines.CONSTANANTS.PAGE_USSD);
        CCATLogger.DEBUG_LOGGER.debug("Ending USSDService - retrieveRecords for Page: " + Defines.CONSTANANTS.PAGE_USSD + "| msisdn " + request.getMsisdn());
        return dSSResponseModel;
    }
}
