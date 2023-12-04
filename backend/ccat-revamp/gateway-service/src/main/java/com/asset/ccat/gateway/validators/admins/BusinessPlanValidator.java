package com.asset.ccat.gateway.validators.admins;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.admin.business_plan.AddBusinessPlanRequest;
import com.asset.ccat.gateway.models.requests.admin.business_plan.DeleteBusinessPlanRequest;
import com.asset.ccat.gateway.models.requests.admin.business_plan.GetBusinessPlanRequest;
import com.asset.ccat.gateway.models.requests.admin.business_plan.UpdateBusinessPlanRequest;
import java.util.Objects;
import org.springframework.stereotype.Component;

/**
 *
 * @author nour.ihab
 */
@Component
public class BusinessPlanValidator {

    public void validateGetBusinessPlan(GetBusinessPlanRequest getBusinessPlanRequest) throws GatewayException {
        if (Objects.isNull(getBusinessPlanRequest.getBusinessPlanId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "businessPlanId");
        }
    }

    public void validateUpdateBusinessPlan(UpdateBusinessPlanRequest updateBusinessPlanRequest) throws GatewayException {
        if (Objects.isNull(updateBusinessPlanRequest.getBusinessPlan().getBusinessPlanCode())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "businessPlanCode");
        } else if (Objects.isNull(updateBusinessPlanRequest.getBusinessPlan().getBusinessPlanId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "businessPlanId");
        } else if (Objects.isNull(updateBusinessPlanRequest.getBusinessPlan().getHlrProfileId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "hlrProfileId");
        } else if (Objects.isNull(updateBusinessPlanRequest.getBusinessPlan().getServiceClassId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "serviceClassId");
        }
    }

    public void validateDeleteBusinessPlan(DeleteBusinessPlanRequest deleteBusinessPlanRequest) throws GatewayException {
        if (Objects.isNull(deleteBusinessPlanRequest.getBusinessPlanId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "businessPlanId");
        }
    }

    public void validateAddBusinessPlan(AddBusinessPlanRequest addBusinessPlanRequest) throws GatewayException {
        if (Objects.isNull(addBusinessPlanRequest.getBusinessPlan().getBusinessPlanCode())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "businessPlanCode");
        } else if (Objects.isNull(addBusinessPlanRequest.getBusinessPlan().getBusinessPlanName())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "businessPlanName");
        } else if (Objects.isNull(addBusinessPlanRequest.getBusinessPlan().getHlrProfileId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "hlrProfileId");
        } else if (Objects.isNull(addBusinessPlanRequest.getBusinessPlan().getServiceClassId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "serviceClassId");

        }
    }

}
