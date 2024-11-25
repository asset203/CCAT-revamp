package com.asset.ccat.lookup_service.models.responses.account_groups_bits_desc;


import com.asset.ccat.lookup_service.models.AccountGroupBitModel;

import java.util.List;

public class GetAllAccountGroupsBitsDescResponse {

    private List<AccountGroupBitModel> accountGroupsList;

    public GetAllAccountGroupsBitsDescResponse() {

    }

    public GetAllAccountGroupsBitsDescResponse(List<AccountGroupBitModel> accountGroupsList) {
        this.accountGroupsList = accountGroupsList;
    }

    public List<AccountGroupBitModel> getAccountGroupsList() {
        return accountGroupsList;
    }

    public void setAccountGroupsList(List<AccountGroupBitModel> accountGroupsList) {
        this.accountGroupsList = accountGroupsList;
    }


    @Override
    public String toString() {
        return "GetAllAccountGroupsBitsDescResponse{" +
                "accountGroupsList=" + accountGroupsList +
                '}';
    }
}