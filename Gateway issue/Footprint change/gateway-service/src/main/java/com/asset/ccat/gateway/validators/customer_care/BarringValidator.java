package com.asset.ccat.gateway.validators.customer_care;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.BarringRequest;
import com.asset.ccat.gateway.util.GatewayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BarringValidator {

    @Autowired
    private GatewayUtil gatewayUtil;

    public void validateBarringRequest(BarringRequest request) throws GatewayException {
        if (!gatewayUtil.isMsisdnValid(request.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.INVALID_INPUT, "msisdn");
        }
        if (request.getBarringReason() == null || request.getBarringReason().isBlank()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "barringReason");
        }
        if (request.getRequestedBy() == null || request.getRequestedBy().isBlank()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "requestedBy");
        }
    }
}
