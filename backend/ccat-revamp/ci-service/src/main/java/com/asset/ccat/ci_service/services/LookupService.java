package com.asset.ccat.ci_service.services;

import com.asset.ccat.ci_service.defines.ErrorCodes;
import com.asset.ccat.ci_service.exceptions.CIServiceException;
import com.asset.ccat.ci_service.logger.CCATLogger;
import com.asset.ccat.ci_service.models.dtos.CICodesRenewalsValue;
import com.asset.ccat.ci_service.models.shared.SuperFlexLookupModel;
import com.asset.ccat.ci_service.proxy.LookupProxy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author marwa.elshawarby
 */
@Service
public class LookupService {
    @Autowired
    LookupProxy lookupProxy;

    public HashMap<String, String> getMainProductTypes() throws CIServiceException {
        return lookupProxy.getMainProductTypes();
    }

    public CICodesRenewalsValue retrievePrepaidVBPCodesRenewalsValues(Integer codeId) throws CIServiceException {
        CCATLogger.DEBUG_LOGGER.info("LookupService -> retrievePrepaidVBPCodesRenewalsValues with CodeId :" + codeId + " Started Successfully");
        Map<Integer, CICodesRenewalsValue> renewalsValuesMap = lookupProxy.retrievePrepaidVBPCodesRenewalsValues();
        CCATLogger.DEBUG_LOGGER.info("LookupService -> retrievePrepaidVBPCodesRenewalsValues  : Ended Successfully");
        CCATLogger.DEBUG_LOGGER.debug("LookupService -> retrievePrepaidVBPCodesRenewalsValues Ended Successfully with response : " + renewalsValuesMap);
        CICodesRenewalsValue result = renewalsValuesMap.get(codeId);

        if (Objects.isNull(result)) {
            CCATLogger.DEBUG_LOGGER.info("LookupService -> retrievePrepaidVBPCodesRenewalsValues  : No Code Renewal value founded !");
//            throw new CIServiceException(ErrorCodes.ERROR.NO_DATA_FOUND,"No Code Renewal value founded !");
        }

        CCATLogger.DEBUG_LOGGER.info("LookupService -> retrievePrepaidVBPCodesRenewalsValues  : with response : " + result);
        return result;
    }

    public HashMap<Integer, SuperFlexLookupModel> getSuperFlexThresholdOffers() throws CIServiceException {
        HashMap<Integer, SuperFlexLookupModel> map = lookupProxy.getSuperFlexThresholdOffers();
        return map;
    }
}
