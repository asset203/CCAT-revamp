package com.asset.ccat.gateway.validators.admins;


import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.admin.account_groups_bits_desc.UpdateAccountGroupsBitsDescRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AccountGroupsBitsDescValidator {


    public void validateAccountGroupsBitsDesc(UpdateAccountGroupsBitsDescRequest updateAccountGroupsBitsDescRequest) throws GatewayValidationException {

        if (Objects.isNull(updateAccountGroupsBitsDescRequest.getAccountGroupBit())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "accountGroupBit");
        }
        if (Objects.isNull(updateAccountGroupsBitsDescRequest.getAccountGroupBit().getBitPosition())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "accountGroupBitPosition");
        }
        if (Objects.isNull(updateAccountGroupsBitsDescRequest.getAccountGroupBit().getBitName())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "accountGroupBitName");
        }
    }
}
