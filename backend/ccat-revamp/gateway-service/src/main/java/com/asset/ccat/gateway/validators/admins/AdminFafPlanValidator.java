package com.asset.ccat.gateway.validators.admins;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.admin_faf_plan.AddAdminFafPlanRequest;
import com.asset.ccat.gateway.models.requests.admin.admin_faf_plan.DeleteAdminFafPlanRequest;
import com.asset.ccat.gateway.models.requests.admin.admin_faf_plan.UpdateAdminFafPlanRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author mohamed.metwaly
 */
@Component
public class AdminFafPlanValidator {
    public void addAdminFafPlanValidator(AddAdminFafPlanRequest request) throws GatewayValidationException {
        CCATLogger.DEBUG_LOGGER.debug("start add Faf Plan Validator");
        if (Objects.isNull(request.getAddedPlan().getPlanId())) {
            CCATLogger.DEBUG_LOGGER.info(" plan id not found or null");
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "planId");
        }
        else if (Objects.isNull(request.getAddedPlan().getName())) {
            CCATLogger.DEBUG_LOGGER.info(" name not found or null");
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "name");
        }
        else if (Objects.isNull(request.getAddedPlan().getFafIndicatorId())) {
            CCATLogger.DEBUG_LOGGER.info(" fafIndicatorId not found or null");
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "fafIndicatorId");
        }
        CCATLogger.DEBUG_LOGGER.debug("end successfully add Faf Plan Validator ");

    }
    public void updateAdminFafPlanValidator(UpdateAdminFafPlanRequest request) throws GatewayValidationException {
        CCATLogger.DEBUG_LOGGER.debug("start add Faf Plan Validator");
        if (Objects.isNull(request.getUpdatedPlan().getPlanId())) {
            CCATLogger.DEBUG_LOGGER.info(" plan id not found or null");
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "planId");
        }
        else if (Objects.isNull(request.getUpdatedPlan().getName())) {
            CCATLogger.DEBUG_LOGGER.info(" name not found or null");
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "name");
        }
        else if (Objects.isNull(request.getUpdatedPlan().getFafIndicatorId())) {
            CCATLogger.DEBUG_LOGGER.info(" fafIndicatorId not found or null");
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "fafIndicatorId");
        }
        CCATLogger.DEBUG_LOGGER.debug("end successfully update Faf Plan Validator ");

    }
    public void deleteAdminFafPlanValidator(DeleteAdminFafPlanRequest request) throws GatewayValidationException {
        CCATLogger.DEBUG_LOGGER.debug("start add Faf Plan Validator");
        if (Objects.isNull(request.getFafPlanId())) {
            CCATLogger.DEBUG_LOGGER.info(" deleted plan id not found or null");
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "fafPlanId");
        }

        CCATLogger.DEBUG_LOGGER.debug("end successfully delete Faf Plan Validator ");

    }
}
