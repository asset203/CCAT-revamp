package com.asset.ccat.lookup_service.services;

import com.asset.ccat.lookup_service.configurations.Properties;
import com.asset.ccat.lookup_service.database.dao.BarringReasonDao;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.BarringReasonModel;
import com.asset.ccat.lookup_service.models.requests.AddBarringReasonRequest;
import com.asset.ccat.lookup_service.models.requests.DeleteBarringReasonRequest;
import com.asset.ccat.lookup_service.models.requests.GetBarringReasonRequest;
import com.asset.ccat.lookup_service.models.responses.GetBarringReasonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BarringReasonService {

    @Autowired
    private BarringReasonDao barringReasonDao;

    @Autowired
    private Properties properties;

    public GetBarringReasonResponse getBarringReason(GetBarringReasonRequest getBarringReasonRequest) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Started BarringReasonService - getBarringReason()");
        CCATLogger.DEBUG_LOGGER.debug("Start serving get barring reason request");
        int barringType = getBarringReasonRequest.getTempBlocked() != null && getBarringReasonRequest.getTempBlocked() ? 1 : 2;
        CCATLogger.DEBUG_LOGGER.debug("Get barring reason for barring type : " + barringType);
        Long modMsisdn = (Long.parseLong(getBarringReasonRequest.getMsisdn()) % properties.getSubPartitons());
        CCATLogger.DEBUG_LOGGER.debug("Msisdn Modulus = " + modMsisdn);
//        String activePartition = "p" + modMsisdn;
//        CCATLogger.DEBUG_LOGGER.debug("Configured active partion = " + activePartition);
        String res = barringReasonDao.retrieveReason(getBarringReasonRequest.getMsisdn(), barringType);
        CCATLogger.DEBUG_LOGGER.debug("Finished serving get barring reason request");
        CCATLogger.DEBUG_LOGGER.info("Ended BarringReasonService - getBarringReason()");
        return new GetBarringReasonResponse(res);
    }

    public void addBarringReason(AddBarringReasonRequest addBarringReasonRequest) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Started BarringReasonService - addBarringReason()");
        CCATLogger.DEBUG_LOGGER.debug("Start serving add barring reason request");
        BarringReasonModel reasonModel = addBarringReasonRequest.getReasonModel();
        Long modMsisdn = (Long.parseLong(reasonModel.getMsisdn()) % properties.getSubPartitons());
        CCATLogger.DEBUG_LOGGER.debug("Msisdn Modulus = " + modMsisdn);
        reasonModel.setModMsisdn(modMsisdn.intValue());
        barringReasonDao.addReason(reasonModel);
        CCATLogger.DEBUG_LOGGER.debug("Finished serving add barring reason request");
        CCATLogger.DEBUG_LOGGER.info("Ended BarringReasonService - addBarringReason()");
    }

    public void deleteBarringReason(DeleteBarringReasonRequest deleteBarringReasonRequest) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Started BarringReasonService - deleteBarringReason()");
        CCATLogger.DEBUG_LOGGER.debug("Start serving delete barring reason request");
        Long modMsisdn = (Long.parseLong(deleteBarringReasonRequest.getMsisdn()) % properties.getSubPartitons());
        CCATLogger.DEBUG_LOGGER.debug("Msisdn Modulus = " + modMsisdn);
//        String activePartition = "p" + modMsisdn;
//        CCATLogger.DEBUG_LOGGER.debug("Configured active partion = " + activePartition);
        barringReasonDao.deleteReason(deleteBarringReasonRequest.getMsisdn(), deleteBarringReasonRequest.getBarringType());
        CCATLogger.DEBUG_LOGGER.debug("Finished serving delete barring reason request");
        CCATLogger.DEBUG_LOGGER.info("Ended BarringReasonService - deleteBarringReason()");
    }
}
