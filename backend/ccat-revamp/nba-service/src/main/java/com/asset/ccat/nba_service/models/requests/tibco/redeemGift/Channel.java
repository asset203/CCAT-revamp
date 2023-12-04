package com.asset.ccat.nba_service.models.requests.tibco.redeemGift;

import java.util.ArrayList;

public class Channel {
    public ArrayList<Id> id;

    public Channel() {
    }

    public Channel(ArrayList<Id> id) {
        this.id = id;
    }

    public ArrayList<Id> getId() {
        return id;
    }

    public void setId(ArrayList<Id> id) {
        this.id = id;
    }
}
