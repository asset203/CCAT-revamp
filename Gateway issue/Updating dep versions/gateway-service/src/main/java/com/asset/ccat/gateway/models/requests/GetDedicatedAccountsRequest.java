package com.asset.ccat.gateway.models.requests;

import com.asset.ccat.gateway.models.customer_care.SubscriberAccountModel;

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
                ", msisdn='" + msisdn + '\'' +
                '}';
    }
}
