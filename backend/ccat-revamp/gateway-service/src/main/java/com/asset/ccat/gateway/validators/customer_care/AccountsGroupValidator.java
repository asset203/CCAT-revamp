package com.asset.ccat.gateway.validators.customer_care;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.admin.AccountGroupBitModel;
import com.asset.ccat.gateway.models.requests.customer_care.account_group.GetAccountGroupsRequest;
import com.asset.ccat.gateway.models.requests.customer_care.account_group.UpdateAccountGroupRequest;
import com.asset.ccat.gateway.util.GatewayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountsGroupValidator {

    @Autowired
    private GatewayUtil gatewayUtil;

    public void validateGetAccountGroupsRequest(GetAccountGroupsRequest request) throws GatewayException {
        if (!gatewayUtil.isMsisdnValid(request.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.INVALID_INPUT, "msisdn");
        }
        if (request.getServiceClassId() == null) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "serviceClassId");
        }
    }

    public void validateUpdateAccountGroupRequest(UpdateAccountGroupRequest request) throws GatewayException {
        if (!gatewayUtil.isMsisdnValid(request.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.INVALID_INPUT, "msisdn");
        }
        if (request.getCurrentAccountGroup() == null) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "currentAccountGroup");
        }
        if (request.getCurrentAccountGroup().getBits() == null || request.getCurrentAccountGroup().getBits().isEmpty()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "currentAccountGroupBits");
        }

        if (request.getNewAccountGroup() == null) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "newAccountGroup");
        }

        if (request.getNewAccountGroup().getBits() == null || request.getNewAccountGroup().getBits().isEmpty()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "newAccountGroupBits");
        }

        // validate there has been a change in data bits
        if (getDecimalValue(request.getCurrentAccountGroup().getBits())
                .equals(getDecimalValue(request.getNewAccountGroup().getBits()))) {
            throw new GatewayValidationException(ErrorCodes.WARNING.NO_CHANGE_DETECTED);
        }
    }

    private Double getDecimalValue(List<AccountGroupBitModel> bits) {
        Double decimalValue = 0.0;
        for (AccountGroupBitModel bit : bits) {
            if (bit.getIsEnabled()) {
                decimalValue += Math.pow(2, bit.getBitPosition());
            }
        }
        return decimalValue;
    }
}
