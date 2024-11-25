package com.asset.ccat.air_service.models.customer_care.service_offering_details_models;

public class ServiceOfferingPlanBitModel {

    private String bitName;
    private Integer bitPosition;
    private Boolean isEnabled;
    private Boolean disabled;

    public ServiceOfferingPlanBitModel() {
    }

    public ServiceOfferingPlanBitModel(String bitName, Integer bitPosition, Boolean isEnabled, Boolean disabled) {
        this.bitName = bitName;
        this.bitPosition = bitPosition;
        this.isEnabled = isEnabled;
        this.disabled = disabled;
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

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public String toString() {
        return "ServiceOfferingPlanBitModel{" +
                "bitName='" + bitName + '\'' +
                ", bitPosition=" + bitPosition +
                ", isEnabled=" + isEnabled +
                ", disabled=" + disabled +
                '}';
    }
}
