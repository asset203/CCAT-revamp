package com.asset.ccat.gateway.validators.customer_care;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.customer_care.voucher.*;
import com.asset.ccat.gateway.util.GatewayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author assem.hassan
 */
@Component
public class VoucherValidator {

    @Autowired
    private GatewayUtil gatewayUtil;

    public void validateGetVoucherDetailsRequest(GetVoucherDetailsRequest getVoucherDetailsRequest) throws GatewayException {
        if (Objects.isNull(getVoucherDetailsRequest.getVoucherSerialNumber())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "VoucherSerialNumber");
        } else if (!gatewayUtil.isMsisdnValid(getVoucherDetailsRequest.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.INVALID_INPUT, "msisdn");
        } else if (Objects.isNull(getVoucherDetailsRequest.getServerId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "ServerId");
        }

    }

    public void validateUpdateVoucherState(UpdateVoucherStateRequest updateVoucherStateRequest) throws GatewayException {
        if (Objects.isNull(updateVoucherStateRequest.getVoucherSerialNumber())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "voucherNumber");
        } else if (!gatewayUtil.isMsisdnValid(updateVoucherStateRequest.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.INVALID_INPUT, "msisdn");
        } else if (Objects.isNull(updateVoucherStateRequest.getServerId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "serverId");
        } else if (Objects.isNull(updateVoucherStateRequest.getNewState())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "newState");
        }
    }

    public void validateVoucherBasedRefillRequest(VoucherBasedRefillRequest voucherBasedRefillRequest) throws GatewayException {
        if (Objects.isNull(voucherBasedRefillRequest.getVoucherNumber())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "voucherNumber");
        } else if (!gatewayUtil.isMsisdnValid(voucherBasedRefillRequest.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.INVALID_INPUT, "msisdn");
        } else if (!Objects.isNull(voucherBasedRefillRequest.getIsMaredCard())
                && voucherBasedRefillRequest.getIsMaredCard()
                && Objects.isNull(voucherBasedRefillRequest.getSelectedMaredCard())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "SelectedMaredCard");
        } else if (Objects.isNull(voucherBasedRefillRequest.getIsMaredCard())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "IsMaredCard");
        }
    }

    public void validateCheckVoucherNumberRequest(CheckVoucherNumberRequest checkVoucherNumberRequest) throws GatewayException {
        if (Objects.isNull(checkVoucherNumberRequest.getVoucherSerialNumber())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "VoucherSerialNumber");
        } else if (!gatewayUtil.isMsisdnValid(checkVoucherNumberRequest.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.INVALID_INPUT, "msisdn");
        } else if (Objects.isNull(checkVoucherNumberRequest.getVoucherNumber())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "voucherNumber");
        } else if (String.valueOf(checkVoucherNumberRequest.getVoucherSerialNumber()).length() < 8) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "VoucherSerialNumber length must be 8 or more");
        } else if (checkVoucherNumberRequest.getVoucherNumber().size() >= 5) {
            List<String> nonEmptyDigits = checkVoucherNumberRequest.getVoucherNumber()
                    .stream()
                    .filter(digit -> !"".equals(digit))
                    .toList();
            if (nonEmptyDigits.isEmpty() || nonEmptyDigits.size() < 5) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "voucherNumber must have at least 5 non empty digits");
            }
        }
    }

    public void validateGetPaymentGatewayVoucher(GetPaymentGatewayVoucherRequest getPaymentGatewayVoucherRequest) throws GatewayException {
        if (!gatewayUtil.isMsisdnValid(getPaymentGatewayVoucherRequest.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "Misisdn");
        } if (Objects.isNull(getPaymentGatewayVoucherRequest.getVoucherSerialNumber())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "Voucher Serial Number");
        }
    }
}
