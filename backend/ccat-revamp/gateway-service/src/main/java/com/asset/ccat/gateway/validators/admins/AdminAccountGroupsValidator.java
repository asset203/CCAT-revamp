package com.asset.ccat.gateway.validators.admins;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.admin.account_groups.AddAccountGroupRequest;
import com.asset.ccat.gateway.models.requests.admin.account_groups.DeleteAccountGroupRequest;
import com.asset.ccat.gateway.models.requests.admin.account_groups.UpdateAccountGroupRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author mohamed.metwaly
 */
@Component
public class AdminAccountGroupsValidator {
    public void addAccountGroupValidator(AddAccountGroupRequest request) throws GatewayValidationException {
        if (Objects.isNull(request.getAddedAccountGroup().getAccountGroupId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "accountGroupId");
        } else if (Objects.isNull(request.getAddedAccountGroup().getAccountGroupDescription())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "accountGroupDescription");
        }
    }

    public void updateAccountGroupValidator(UpdateAccountGroupRequest request) throws GatewayValidationException {
        if (Objects.isNull(request.getUpdatedAccountGroup().getAccountGroupId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "accountGroupId");
        } else if (Objects.isNull(request.getUpdatedAccountGroup().getAccountGroupDescription())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "accountGroupDescription");
        }
    }

    public void deleteAccountGroupValidator(DeleteAccountGroupRequest request) throws GatewayValidationException {
        if (Objects.isNull(request.getAccountGroupId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "accountGroupId");
        }
    }
}
