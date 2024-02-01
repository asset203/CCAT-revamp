package com.asset.ccat.gateway.validators.admins;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.admin.service_offering_plans.*;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ServiceOfferingPlanValidator {

    public void validateGetServiceOfferingPlan(GetServiceOfferingPlanBitsRequest request) throws GatewayValidationException {
        if (Objects.isNull(request.getPlanId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "planId");
        }
    }


    public void validateGetServiceOfferingServiceClassDesc(GetServiceClassServiceOfferingPlanDescs request) throws GatewayValidationException {
//        if (Objects.isNull(request.getPlanId())) {
//            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "planId");
//        }
    }


    public void validateAddServiceOfferingPlan(AddServiceOfferingPlanRequest request) throws GatewayValidationException {
        if (Objects.isNull(request.getPlanId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "planId");
        } else if (Objects.isNull(request.getPlanName())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "planName");
        } else if (Objects.isNull(request.getServiceOfferingPlanBits())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "serviceOfferingPlanBits");
        }
    }

    public void validateAddServiceClassPlanDesc(AddServiceClassPlanDescriptionRequest request) throws GatewayValidationException {
        if (Objects.isNull(request.getPlanId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "planId");
        } else if (Objects.isNull(request.getServiceClassId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "serviceClassId");
        }
        if (Objects.isNull(request.getDescription())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "description");
        }
    }


    public void validateUpdateServiceOfferingPlan(UpdateServiceOfferingPlanRequest request) throws GatewayValidationException {
        if (Objects.isNull(request.getPlanId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "planId");
        } else if (Objects.isNull(request.getPlanName())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "planName");
        } else if (Objects.isNull(request.getServiceOfferingPlanBits())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "serviceOfferingPlanBits");
        }
    }

    public void validateUpdateServiceClassPlanDesc(UpdateServiceClassPlanDescriptionRequest request) throws GatewayValidationException {
        if (Objects.isNull(request.getPlanId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "planId");
        } else if (Objects.isNull(request.getServiceClassId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "serviceClassId");
        } else if (Objects.isNull(request.getDescription())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "description");
        }
    }

    public void validateDeleteServiceOfferingPlan(DeleteServiceOfferingPlanRequest request) throws GatewayValidationException {
        if (Objects.isNull(request.getPlanId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "planId");
        }
    }

    public void validateDeleteServiceClassPlanDesc(DeleteServiceClassPlanDescriptionRequest request) throws GatewayValidationException {
        if (Objects.isNull(request.getPlanId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "planId");
        } else if (Objects.isNull(request.getServiceClassId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "classId");
        }
    }
}
