package com.asset.ccat.gateway.models.requests.customer_care.account_group;

import com.asset.ccat.gateway.models.customer_care.AccountGroupModel;
import com.asset.ccat.gateway.models.requests.SubscriberRequest;

public class UpdateAccountGroupRequest extends SubscriberRequest {
    private AccountGroupModel currentAccountGroup;
    private AccountGroupModel newAccountGroup;

    public UpdateAccountGroupRequest() {
    }

    public UpdateAccountGroupRequest(AccountGroupModel currentAccountGroup, AccountGroupModel newAccountGroup) {
        this.currentAccountGroup = currentAccountGroup;
        this.newAccountGroup = newAccountGroup;
    }

    public AccountGroupModel getCurrentAccountGroup() {
        return currentAccountGroup;
    }

    public void setCurrentAccountGroup(AccountGroupModel currentAccountGroup) {
        this.currentAccountGroup = currentAccountGroup;
    }

    public AccountGroupModel getNewAccountGroup() {
        return newAccountGroup;
    }

    public void setNewAccountGroup(AccountGroupModel newAccountGroup) {
        this.newAccountGroup = newAccountGroup;
    }

    @Override
    public String toString() {
        return "UpdateAccountGroupRequest{" +
                "currentAccountGroup=" + currentAccountGroup +
                ", newAccountGroup=" + newAccountGroup +
                ", msisdn='" + msisdn + '\'' +
                '}';
    }
}
