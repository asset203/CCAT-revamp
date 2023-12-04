package com.asset.ccat.gateway.models.responses.admin.account_groups;

import com.asset.ccat.gateway.models.admin.AccountGroupModel;

import java.util.List;

/**
 * @author mohamed.metwaly
 */
public class GetAllAccountGroupsResponse {
    private List<AccountGroupModel> accountGroups;

    public GetAllAccountGroupsResponse() {
    }

    public GetAllAccountGroupsResponse(List<AccountGroupModel> accountGroups) {
        this.accountGroups = accountGroups;
    }

    public List<AccountGroupModel> getAccountGroups() {
        return accountGroups;
    }

    public void setAccountGroups(List<AccountGroupModel> accountGroups) {
        this.accountGroups = accountGroups;
    }
}
