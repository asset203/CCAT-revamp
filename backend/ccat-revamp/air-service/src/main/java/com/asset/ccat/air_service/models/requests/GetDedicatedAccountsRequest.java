package com.asset.ccat.air_service.models.requests;

import com.asset.ccat.air_service.models.SubscriberAccountModel;

public class GetDedicatedAccountsRequest extends SubscriberRequest {
    private SubscriberAccountModel accountModel;

    public SubscriberAccountModel getAccountModel() {
        return accountModel;
    }

    public void setAccountModel(SubscriberAccountModel accountModel) {
        this.accountModel = accountModel;
    }

    @Override
    public String toString() {
        return "GetDedicatedAccountsRequest{" +
                "accountModel=" + accountModel +
                '}';
    }
}
