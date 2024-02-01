package com.asset.ccat.gateway.validators.customer_care;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.SubscriberRequest;
import com.asset.ccat.gateway.models.requests.customer_care.super_flex.ActivateAddonRequest;
import com.asset.ccat.gateway.models.requests.customer_care.super_flex.ActivateThresholdRequest;
import com.asset.ccat.gateway.models.responses.customer_care.super_flex.GetMIThresholdResponse;
import com.asset.ccat.gateway.models.responses.customer_care.super_flex.GetOptInAddonsResponse;
import com.asset.ccat.gateway.util.GatewayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SuperFlexValidator {

    @Autowired
    GatewayUtil gatewayUtil;

    public void validateGetOptinAddonsRequest(SubscriberRequest request) throws GatewayException {
        if (request.getMsisdn() == null
                || request.getMsisdn().isBlank()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        } else if (!gatewayUtil.isMsisdnValid(request.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.INVALID_INPUT, "msisdn");
        }
    }

    public void validateActivateAddonRequest(ActivateAddonRequest request) throws GatewayException {
        if (request.getMsisdn() == null
                || request.getMsisdn().isBlank()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        } else if (!gatewayUtil.isMsisdnValid(request.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.INVALID_INPUT, "msisdn");
        } else if (request.getPackageId() == null
                || request.getPackageId().isBlank()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.INVALID_INPUT, "packageId");
        }
    }

    public void validateDeactivateAddonRequest(SubscriberRequest request) throws GatewayException {
        if (request.getMsisdn() == null
                || request.getMsisdn().isBlank()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        } else if (!gatewayUtil.isMsisdnValid(request.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.INVALID_INPUT, "msisdn");
        }

    }

    public void validateGetMiThresholdRequest(SubscriberRequest request) throws GatewayException {
        if (request.getMsisdn() == null
                || request.getMsisdn().isBlank()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        } else if (!gatewayUtil.isMsisdnValid(request.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.INVALID_INPUT, "msisdn");
        }
    }

    public void validateActivateThresholdRequest(ActivateThresholdRequest request) throws GatewayException {
        if (request.getMsisdn() == null
                || request.getMsisdn().isBlank()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        } else if (!gatewayUtil.isMsisdnValid(request.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.INVALID_INPUT, "msisdn");
        } else if (request.getThresholdAmount() == null) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        }
    }

    public void validateDeactivateThresholdRequest(SubscriberRequest request) throws GatewayException {
        if (request.getMsisdn() == null
                || request.getMsisdn().isBlank()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        } else if (!gatewayUtil.isMsisdnValid(request.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.INVALID_INPUT, "msisdn");
        }
    }

    public void validateStopMIThresholdRequest(SubscriberRequest request) throws GatewayException {
        if (request.getMsisdn() == null
                || request.getMsisdn().isBlank()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        } else if (!gatewayUtil.isMsisdnValid(request.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.INVALID_INPUT, "msisdn");
        }
    }

    public void validateDeactivateStopMIRequest(SubscriberRequest request) throws GatewayException {
        if (request.getMsisdn() == null
                || request.getMsisdn().isBlank()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        } else if (!gatewayUtil.isMsisdnValid(request.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.INVALID_INPUT, "msisdn");
        }
    }
}
