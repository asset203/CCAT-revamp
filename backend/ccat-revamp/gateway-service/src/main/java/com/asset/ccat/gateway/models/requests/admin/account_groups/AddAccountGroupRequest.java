package com.asset.ccat.gateway.models.requests.admin.account_groups;

import com.asset.ccat.gateway.models.admin.AccountGroupModel;
import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author mohamed.metwaly
 */
public class AddAccountGroupRequest extends BaseRequest {
    private AccountGroupModel addedAccountGroup;

    public AddAccountGroupRequest() {
    }

    public AccountGroupModel getAddedAccountGroup() {
        return addedAccountGroup;
    }

    public void setAddedAccountGroup(AccountGroupModel addedAccountGroup) {
        this.addedAccountGroup = addedAccountGroup;
    }
}
