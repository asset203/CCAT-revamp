package com.asset.ccat.gateway.models.responses.admin.account_groups_bits_desc;


import com.asset.ccat.gateway.models.admin.AccountGroupBitDescModel;

import java.util.List;

public class GetAllAccountGroupsBitsDescResponse {

    private List<AccountGroupBitDescModel> accountGroupsList;

    public GetAllAccountGroupsBitsDescResponse() {

    }

    public GetAllAccountGroupsBitsDescResponse(List<AccountGroupBitDescModel> accountGroupsList) {
        this.accountGroupsList = accountGroupsList;
    }

    public List<AccountGroupBitDescModel> getAccountGroupsList() {
        return accountGroupsList;
    }

    public void setAccountGroupsList(List<AccountGroupBitDescModel> accountGroupsList) {
        this.accountGroupsList = accountGroupsList;
    }


    @Override
    public String toString() {
        return "GetAllAccountGroupsBitsDescResponse{" +
                "accountGroupsList=" + accountGroupsList +
                '}';
    }
}
