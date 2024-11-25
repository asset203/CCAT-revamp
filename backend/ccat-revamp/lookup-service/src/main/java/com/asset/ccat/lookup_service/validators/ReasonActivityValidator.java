package com.asset.ccat.lookup_service.validators;


import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.ReasonActivityModel;
import com.asset.ccat.lookup_service.services.ReasonActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReasonActivityValidator {

    @Autowired
    ReasonActivityService reasonActivityService;

    public void validateAddReasonActivityRequest(ReasonActivityModel reason) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Start validating AddActivityReasonRequest");
        ReasonActivityModel existedReason = reasonActivityService.findReason(reason);
        if (existedReason != null) {
            CCATLogger.DEBUG_LOGGER.debug("Validating AddActivityReasonRequest failed , reason activity with ACTIVITY_NAME [" + reason.getActivityName());
            throw new LookupException(ErrorCodes.ERROR.ALREADY_EXIST, Defines.SEVERITY.VALIDATION, reason.getActivityType().name + " NAME");
        }

        CCATLogger.DEBUG_LOGGER.info("Finished validating  AddActivityReasonRequest  successfully");
    }


    public void validateUpdateReasonActivityRequest(ReasonActivityModel reason) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Start validating UpdateReasonActivityRequest");
        ReasonActivityModel existedReason = reasonActivityService.findReason(reason);
        if (existedReason != null) {
            CCATLogger.DEBUG_LOGGER.debug("Validating UpdateReasonActivityRequest failed , reason activity with name [" + reason.getActivityName());
            throw new LookupException(ErrorCodes.ERROR.DUPLICATED_DATA, Defines.SEVERITY.VALIDATION, " reason activity with name " + reason.getActivityName());
        }

        CCATLogger.DEBUG_LOGGER.info("Finished validating  UpdateReasonActivityRequest  successfully");
    }


}
