package com.asset.ccat.nba_service.models.requests.tibco.redeemGift;

import java.util.ArrayList;

public class CustomerAccount {
    public ArrayList<Id> id;

    public CustomerAccount() {
    }

    public CustomerAccount(ArrayList<Id> id) {
        this.id = id;
    }

    public ArrayList<Id> getId() {
        return id;
    }

    public void setId(ArrayList<Id> id) {
        this.id = id;
    }
}
