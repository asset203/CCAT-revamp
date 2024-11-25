package com.asset.ccat.gateway.models.requests.admin.account_groups_bits_desc;


import com.asset.ccat.gateway.models.admin.AccountGroupBitDescModel;
import com.asset.ccat.gateway.models.requests.BaseRequest;

public class UpdateAccountGroupsBitsDescRequest extends BaseRequest {

    private AccountGroupBitDescModel accountGroupBit;

    public AccountGroupBitDescModel getAccountGroupBit() {
        return accountGroupBit;
    }


    @Override
    public String toString() {
        return "UpdateAccountGroupsBitsDescRequest{" +
                "accountGroupBit=" + accountGroupBit +
                '}';
    }
}
