package com.asset.ccat.gateway.models.admin;

public class ServiceOfferingBitDescModel {


    private Integer bitPosition;
    private String bitName;

    public ServiceOfferingBitDescModel() {
    }

    public ServiceOfferingBitDescModel(Integer bitPosition, String bitName) {
        this.bitPosition = bitPosition;
        this.bitName = bitName;
    }

    public Integer getBitPosition() {
        return bitPosition;
    }

    public String getBitName() {
        return bitName;
    }


}
