package com.asset.ccat.gateway.validators.customer_care;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.customer_care.call_reasons.AddCallReasonRequest;
import com.asset.ccat.gateway.models.requests.customer_care.call_reasons.UpdateCallReasonRequest;
import com.asset.ccat.gateway.util.GatewayUtil;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CallReasonValidator {

    private final GatewayUtil gatewayUtil;

    public CallReasonValidator(GatewayUtil gatewayUtil) {
        this.gatewayUtil = gatewayUtil;
    }

    public void addCallReason(AddCallReasonRequest request) throws GatewayValidationException {
        if (!gatewayUtil.isMsisdnValid(request.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "Misisdn");
        }
    }
    public void updateCallReason(UpdateCallReasonRequest request) throws GatewayValidationException {
        if (Objects.isNull(request.getCallReasonId()) || request.getCallReasonId().compareTo(0)<=0) {
            CCATLogger.DEBUG_LOGGER.error("CallReasonValidator -> updateCallReason() : Invalid Call Reason Id");
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "Call Reason Id");
        }if (Objects.isNull(request.getReason())) {
            CCATLogger.DEBUG_LOGGER.error("CallReasonValidator -> updateCallReason() : Invalid Reason");
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "Reason");
        }if (Objects.isNull(request.getDirection())) {
            CCATLogger.DEBUG_LOGGER.error("CallReasonValidator -> updateCallReason() : Invalid Direction");
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "Direction");
        }if (Objects.isNull(request.getFamily())) {
            CCATLogger.DEBUG_LOGGER.error("CallReasonValidator -> updateCallReason() : Invalid Family");
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "Family");
        }if (Objects.isNull(request.getType())) {
            CCATLogger.DEBUG_LOGGER.error("CallReasonValidator -> updateCallReason() : Invalid Type");
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "Type");
        }
    }
}
