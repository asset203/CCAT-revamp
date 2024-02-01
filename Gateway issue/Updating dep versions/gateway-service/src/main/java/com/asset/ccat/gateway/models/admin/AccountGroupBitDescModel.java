package com.asset.ccat.gateway.models.admin;

public class AccountGroupBitDescModel {

    private Integer bitPosition;
    private String bitName;

    public AccountGroupBitDescModel() {
    }

    public AccountGroupBitDescModel(Integer bitPosition, String bitName) {
        this.bitPosition = bitPosition;
        this.bitName = bitName;
    }

    public Integer getBitPosition() {
        return bitPosition;
    }

    public String getBitName() {
        return bitName;
    }

    public void setBitName(String bitName) {
        this.bitName = bitName;
    }

    public void setBitPosition(Integer bitPosition) {
        this.bitPosition = bitPosition;
    }
}
