package com.asset.ccat.lookup_service.models;

public class ServiceOfferingBitModel {


    private Integer bitPosition;

    private String bitName;


    public void setBitPosition(Integer bitPosition) {
        this.bitPosition = bitPosition;
    }

    public void setBitName(String bitName) {
        this.bitName = bitName;
    }

    public Integer getBitPosition() {
        return bitPosition;
    }

    public String getBitName() {
        return bitName;
    }
}
