package com.asset.ccat.ci_service.validators;

import com.asset.ccat.ci_service.defines.CIDefines;
import com.asset.ccat.ci_service.defines.ErrorCodes;
import com.asset.ccat.ci_service.exceptions.CIException;
import com.asset.ccat.ci_service.exceptions.CIServiceException;
import com.asset.ccat.ci_service.logger.CCATLogger;
import org.springframework.stereotype.Component;

@Component
public class PrepaidVBPValidator {
    public void subscribeResponseCodeValidator(String responseCode) throws CIServiceException, CIException {
        CCATLogger.DEBUG_LOGGER.debug("Validating Started with responseCode[" + responseCode + "].");
        if(responseCode.contains("-")){
            CCATLogger.DEBUG_LOGGER.debug("CI Exception occurred based on CI response : [" + responseCode + "].");
            CCATLogger.ERROR_LOGGER.debug("CI Exception occurred based on CI response : [" + responseCode + "].");
            throw new CIException(Integer.valueOf(responseCode));
        }else if(responseCode.equals(CIDefines.PREPAIDVBP.SUBSCRIPTION_RESPONSES.SUCCESS_CODE)){
            CCATLogger.DEBUG_LOGGER.debug("Validating Ended Successfully with responseCode[" + responseCode + "].");
        }else {
            CCATLogger.DEBUG_LOGGER.error("Unknown error occurred while validating subscribe for prepaidVBP CI returned response code : "+responseCode);
            CCATLogger.ERROR_LOGGER.error("Unknown error occurred while validating subscribe for prepaidVBP CI returned response code : "+responseCode);
            throw new CIServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR, "failure");
        }
    }
    public void unsubscribeResponseCodeValidator(String responseCode) throws CIServiceException, CIException {
        CCATLogger.DEBUG_LOGGER.debug("Validating Started with responseCode[" + responseCode + "].");
        if(responseCode.contains("-")){
            CCATLogger.DEBUG_LOGGER.debug("CI Exception occurred based on CI response : [" + responseCode + "].");
            CCATLogger.ERROR_LOGGER.debug("CI Exception occurred based on CI response : [" + responseCode + "].");
            throw new CIException( Integer.valueOf(responseCode));
        }else if(responseCode.equals(CIDefines.PREPAIDVBP.UNSUBSCRIPTION_RESPONSES.SUCCESS_CODE)){
            CCATLogger.DEBUG_LOGGER.debug("Validating Ended Successfully with responseCode[" + responseCode + "].");
        }else {
            CCATLogger.DEBUG_LOGGER.error("Unknown error occurred while validating unsubscribing for prepaidVBP CI returned response code : "+responseCode);
            CCATLogger.ERROR_LOGGER.error("Unknown error occurred while validating unsubscribing for prepaidVBP CI returned response code : "+responseCode);
            throw new CIServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR, "failure");
        }
    }
    public Boolean checkSubscriptionResponseCodeValidator(String responseCode) throws CIException, CIServiceException {
        CCATLogger.DEBUG_LOGGER.debug("Validating Started with responseCode[" + responseCode + "].");
        if(responseCode.contains("-")){
            CCATLogger.DEBUG_LOGGER.debug("CI Exception occurred based on CI response : [" + responseCode + "].");
            throw new CIException(Integer.valueOf(responseCode));
        }else {
            CCATLogger.DEBUG_LOGGER.debug("Validating Ended Successfully ");
            if(responseCode.equals(CIDefines.PREPAIDVBP.CHECK_SUBSCRIPTION_RESPONSES.SUCCESS_CODE)){
                return true;
            }else {
               return false;
            }
        }

    }
}
