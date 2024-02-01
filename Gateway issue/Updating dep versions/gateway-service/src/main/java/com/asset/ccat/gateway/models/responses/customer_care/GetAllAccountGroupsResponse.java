package com.asset.ccat.gateway.models.responses.customer_care;

import com.asset.ccat.gateway.models.customer_care.AccountGroupModel;

import java.util.List;

public class GetAllAccountGroupsResponse {

    List<AccountGroupModel> accountGroups;

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
