package com.asset.ccat.air_service.models.requests;

import com.asset.ccat.air_service.models.AccountGroupModel;

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
                '}';
    }
}
