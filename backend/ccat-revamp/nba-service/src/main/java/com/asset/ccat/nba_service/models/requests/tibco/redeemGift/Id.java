package com.asset.ccat.nba_service.models.requests.tibco.redeemGift;

public class Id {
    public String value;

    public Id() {
    }

    public Id(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Id{" +
            "value='" + value + '\'' +
            '}';
    }
}
