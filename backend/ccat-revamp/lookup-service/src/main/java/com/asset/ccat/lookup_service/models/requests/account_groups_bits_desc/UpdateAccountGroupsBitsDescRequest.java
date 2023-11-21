package com.asset.ccat.lookup_service.models.requests.account_groups_bits_desc;


import com.asset.ccat.lookup_service.models.AccountGroupBitModel;
import com.asset.ccat.lookup_service.models.requests.BaseRequest;

public class UpdateAccountGroupsBitsDescRequest extends BaseRequest {

    private AccountGroupBitModel accountGroupBit;

    public AccountGroupBitModel getAccountGroupBit() {
        return accountGroupBit;
    }


    @Override
    public String toString() {
        return "UpdateAccountGroupsBitsDescRequest{" +
                "accountGroupBit=" + accountGroupBit +
                '}';
    }
}
