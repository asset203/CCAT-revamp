package com.asset.ccat.gateway.validators.customer_care;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.customer_care.flex_share.GetFlexShareEligibilityRequest;
import com.asset.ccat.gateway.models.requests.customer_care.flex_share.GetFlexShareHistoryRequest;
import com.asset.ccat.gateway.models.requests.customer_care.flex_share.GetFlexShareStatesRequest;
import com.asset.ccat.gateway.models.requests.customer_care.flex_share.UpdateBWStateRequest;
import com.asset.ccat.gateway.util.GatewayUtil;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class FlexShareValidator {
    private final GatewayUtil gatewayUtil;

    public FlexShareValidator(GatewayUtil gatewayUtil) {
        this.gatewayUtil = gatewayUtil;
    }

    public void getFlexShareStatesValidator(GetFlexShareStatesRequest request) throws GatewayValidationException {
        if (Objects.isNull(request.getMsisdn()) && gatewayUtil.isMsisdnValid(request.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.INVALID_INPUT, " Msisdn ");
        }
    }

    public void updateFlexShareStateValidator(UpdateBWStateRequest request) throws GatewayValidationException {
        if (Objects.isNull(request.getMsisdn()) && gatewayUtil.isMsisdnValid(request.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.INVALID_INPUT, " Msisdn ");
        }
        if (Objects.isNull(request.getUpdatedValue())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, " Updated Value ");
        }
    }

    public void getFlexShareEligibilityValidator(GetFlexShareEligibilityRequest request) throws GatewayValidationException {
        if (Objects.isNull(request.getMsisdn()) && gatewayUtil.isMsisdnValid(request.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.INVALID_INPUT, " Msisdn ");
        }
        if (Objects.isNull(request.getReceiverMsisdn()) && gatewayUtil.isMsisdnValid(request.getReceiverMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, " Receiver Msisdn ");
        }
        if (Objects.isNull(request.getFlexAmount())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, " Flex Amount");
        }
    }

    public void validateGetFlexShareHistoryRequest(GetFlexShareHistoryRequest request) throws GatewayValidationException {
        if (Objects.isNull(request.getMsisdn()) && gatewayUtil.isMsisdnValid(request.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.INVALID_INPUT, " Msisdn ");
        }
        if (Objects.isNull(request.getDateFrom()) && Objects.nonNull(request.getDateTo())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "dateFrom");
        }
        if (Objects.isNull(request.getDateTo()) && Objects.nonNull(request.getDateFrom())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, " dateTo");
        }
        if (Objects.isNull(request.getFlag())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, " flag");
        }
    }
}
