package com.asset.ccat.air_service.models;

public class AccountGroupBitModel {

    private String bitName;
    private Integer bitPosition;
    private Boolean isEnabled;

    public AccountGroupBitModel() {
    }

    public AccountGroupBitModel(String bitName, Integer bitPosition, Boolean isEnabled) {
        this.bitName = bitName;
        this.bitPosition = bitPosition;
        this.isEnabled = isEnabled;
    }

    public String getBitName() {
        return bitName;
    }

    public void setBitName(String bitName) {
        this.bitName = bitName;
    }

    public Integer getBitPosition() {
        return bitPosition;
    }

    public void setBitPosition(Integer bitPosition) {
        this.bitPosition = bitPosition;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean enabled) {
        isEnabled = enabled;
    }


    @Override
    public String toString() {
        return "ServiceOfferingPlanBitModel{" +
                "bitName='" + bitName + '\'' +
                ", bitPosition=" + bitPosition +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
