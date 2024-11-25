package com.asset.ccat.lookup_service.models.responses.account_groups;



import com.asset.ccat.lookup_service.models.AccountGroupModel;

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
