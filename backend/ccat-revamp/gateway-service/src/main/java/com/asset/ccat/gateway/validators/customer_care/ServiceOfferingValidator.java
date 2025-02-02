package com.asset.ccat.gateway.validators.customer_care;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.admin.ServiceOfferingPlanBitModel;
import com.asset.ccat.gateway.models.requests.customer_care.service_offering_plans.GetServiceOfferingsRequest;
import com.asset.ccat.gateway.models.requests.customer_care.service_offering_plans.UpdateServiceOfferingRequest;
import com.asset.ccat.gateway.util.GatewayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceOfferingValidator {

    @Autowired
    private GatewayUtil gatewayUtil;

    public void validateGetServiceOfferingRequest(GetServiceOfferingsRequest request) throws GatewayException {
        if (!gatewayUtil.isMsisdnValid(request.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.INVALID_INPUT, "msisdn");
        }
        if (request.getServiceClassId() == null) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "serviceClassId");
        }
    }

    public void validateUpdateServiceOfferingRequest(UpdateServiceOfferingRequest request) throws GatewayException {
        if (!gatewayUtil.isMsisdnValid(request.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.INVALID_INPUT, "msisdn");
        }
        if (request.getCurrentServiceOffering() == null) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "currentServiceOffering");
        }
        if (request.getCurrentServiceOffering().getBits() == null || request.getCurrentServiceOffering().getBits().isEmpty()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "currentServiceBits");
        }

        if (request.getNewServiceOffering() == null) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "newServiceOffering");
        }

        if (request.getNewServiceOffering().getBits() == null || request.getNewServiceOffering().getBits().isEmpty()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "newServiceBits");
        }

//        // validate there has been a change in data bits
//        if (getDecimalValue(request.getCurrentServiceOffering().getBits())
//                .equals(getDecimalValue(request.getNewServiceOffering().getBits()))) {
//            throw new GatewayValidationException(ErrorCodes.WARNING.NO_CHANGE_DETECTED);
//        }
    }

    private Double getDecimalValue(List<ServiceOfferingPlanBitModel> bits) {
        Double decimalValue = 0.0;
        for (ServiceOfferingPlanBitModel bit : bits) {
            if (bit.getIsEnabled()) {
                decimalValue += Math.pow(2, bit.getBitPosition());
            }
        }
        return decimalValue;
    }
}