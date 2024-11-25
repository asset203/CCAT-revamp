package com.asset.ccat.lookup_service.models.requests.account_groups;


import com.asset.ccat.lookup_service.models.AccountGroupModel;
import com.asset.ccat.lookup_service.models.requests.BaseRequest;

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
