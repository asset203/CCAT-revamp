package com.asset.ccat.gateway.validators;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.SendSMSRequest;
import com.asset.ccat.gateway.util.GatewayUtil;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SendSMSValidator {

    private final GatewayUtil gatewayUtil;

    public SendSMSValidator(GatewayUtil gatewayUtil) {
        this.gatewayUtil = gatewayUtil;
    }

    public void validateSendSMS(SendSMSRequest sendSMSRequest) throws GatewayException {
        if (Objects.isNull(sendSMSRequest.getTemplateLanguageId()) || sendSMSRequest.getTemplateLanguageId().compareTo(0) <= 0) {
            CCATLogger.DEBUG_LOGGER.error("SendSMSValidator -> send() : Invalid Template Language Id");
            throw new GatewayException(ErrorCodes.WARNING.MISSING_FIELD, "templateLanguageId");
        } else if (Objects.isNull(sendSMSRequest.getActionName()) || sendSMSRequest.getActionName().isBlank()) {
            CCATLogger.DEBUG_LOGGER.error("SendSMSValidator -> send() : Invalid Action Name");
            throw new GatewayException(ErrorCodes.WARNING.MISSING_FIELD, "actionName");
        } else if (!gatewayUtil.isMsisdnValid(sendSMSRequest.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "Msisdn");
        }
    }
}
