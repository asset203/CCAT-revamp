package com.asset.ccat.gateway.models.requests.admin.account_groups;

import com.asset.ccat.gateway.models.admin.AccountGroupModel;
import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author mohamed.metwaly
 */
public class UpdateAccountGroupRequest extends BaseRequest {
    private AccountGroupModel updatedAccountGroup;

    public UpdateAccountGroupRequest() {
    }

    public AccountGroupModel getUpdatedAccountGroup() {
        return updatedAccountGroup;
    }

    public void setUpdatedAccountGroup(AccountGroupModel updatedAccountGroup) {
        this.updatedAccountGroup = updatedAccountGroup;
    }

    @Override
    public String toString() {
        return "UpdateAccountGroupRequest{" +
                "updatedAccountGroup=" + updatedAccountGroup +
                '}';
    }
}
