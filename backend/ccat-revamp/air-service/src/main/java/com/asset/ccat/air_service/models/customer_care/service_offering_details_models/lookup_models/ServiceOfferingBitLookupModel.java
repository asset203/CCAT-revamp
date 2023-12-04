package com.asset.ccat.air_service.models.customer_care.service_offering_details_models.lookup_models;

import java.util.HashMap;

public class ServiceOfferingBitLookupModel {

    private Integer bitPosition;

    private String bitName;

    private Boolean isEnabled ;

    private HashMap<Integer,String> serviceClassDesc;

    public ServiceOfferingBitLookupModel() {
    }

    public ServiceOfferingBitLookupModel(Integer bitPosition, String bitName, Boolean isEnable, HashMap<Integer,String> serviceClassDesc) {
        this.bitPosition = bitPosition;
        this.bitName = bitName;
        this.isEnabled = isEnable;
        this.serviceClassDesc = serviceClassDesc;
    }

    public Integer getBitPosition() {
        return bitPosition;
    }

    public void setBitPosition(Integer bitPosition) {
        this.bitPosition = bitPosition;
    }

    public String getBitName() {
        return bitName;
    }

    public void setBitName(String bitName) {
        this.bitName = bitName;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public HashMap<Integer, String> getServiceClassDesc() {
        return serviceClassDesc;
    }

    public void setServiceClassDesc(HashMap<Integer, String> serviceClassDesc) {
        this.serviceClassDesc = serviceClassDesc;
    }
}
