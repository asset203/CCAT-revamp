package com.asset.ccat.lookup_service.models.requests.account_groups;


import com.asset.ccat.lookup_service.models.AccountGroupModel;
import com.asset.ccat.lookup_service.models.requests.BaseRequest;

/**
 * @author mohamed.metwaly
 */
public class UpdateAccountGroupRequest extends BaseRequest {
    public UpdateAccountGroupRequest() {
    }

    private AccountGroupModel updatedAccountGroup;

    public AccountGroupModel getUpdatedAccountGroup() {
        return updatedAccountGroup;
    }

    public void setUpdatedAccountGroup(AccountGroupModel updatedAccountGroup) {
        this.updatedAccountGroup = updatedAccountGroup;
    }
}
