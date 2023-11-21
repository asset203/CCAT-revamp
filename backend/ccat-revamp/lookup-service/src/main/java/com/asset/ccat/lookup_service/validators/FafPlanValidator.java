package com.asset.ccat.lookup_service.validators;


import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.FafPlanModel;
import com.asset.ccat.lookup_service.services.FafPlansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FafPlanValidator {


    @Autowired
    FafPlansService fafPlansService;

    public void isFafPlanUpdateValid(FafPlanModel fafPlan) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Start validating if fafPlan [" + fafPlan + "] exists");
        if (!fafPlansService.isFafPlanIdExists(fafPlan.getPlanId())) {
            CCATLogger.DEBUG_LOGGER.debug("Validating fafPlan failed , fafPlan with PLAN_ID [" + fafPlan.getPlanId() + "] not found");
            throw new LookupException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.VALIDATION,"fafPlan planId:"+ fafPlan.getPlanId()+" is not found");
        }
        if (fafPlansService.isFafPlanDescExists(fafPlan)) {
            CCATLogger.DEBUG_LOGGER.debug("Validating fafPlan failed , fafPlan with Description [" + fafPlan.getName() + "] exist");
            throw new LookupException(ErrorCodes.ERROR.DUPLICATED_DATA, Defines.SEVERITY.VALIDATION,"fafPlan Description:"+ fafPlan.getName());
        }
        CCATLogger.DEBUG_LOGGER.info("Finished validating  fafPlan  successfully");
    }

    public void isFafPlanAdminAddValid(FafPlanModel fafPlan) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Start validating if fafPlan [" + fafPlan + "] exists");
        if (fafPlansService.isFafPlanIdExists(fafPlan.getPlanId())) {
            CCATLogger.DEBUG_LOGGER.debug("Validating fafPlan failed , fafPlan with ID [" + fafPlan.getPlanId() + "] exist");
            throw new LookupException(ErrorCodes.ERROR.DUPLICATED_DATA, Defines.SEVERITY.VALIDATION,"FafPlan Id:" + fafPlan.getPlanId()+"");
        }
        if (fafPlansService.isFafPlanDescExists(fafPlan)) {
            CCATLogger.DEBUG_LOGGER.debug("Validating fafPlan failed , fafPlan with Description [" + fafPlan.getName() + "] exist");
            throw new LookupException(ErrorCodes.ERROR.DUPLICATED_DATA, Defines.SEVERITY.VALIDATION,"fafPlan Description:"+ fafPlan.getName());
        }
        CCATLogger.DEBUG_LOGGER.info("Finished validating  Community Admin successfully");
    }
}
