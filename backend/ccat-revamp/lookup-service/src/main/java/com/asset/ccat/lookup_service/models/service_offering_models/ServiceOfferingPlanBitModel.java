package com.asset.ccat.lookup_service.models.service_offering_models;

import java.util.HashMap;

public class ServiceOfferingPlanBitModel {
    private String planName;
    private Integer planId;
    private HashMap<Integer,String> serviceClassDisc;

    private HashMap<Integer, ServiceOfferingBitModel> bitModelHashMap;

    private Double decimalValue;



    public ServiceOfferingPlanBitModel() {
    }

    public ServiceOfferingPlanBitModel(String planName, Integer planId, HashMap<Integer, String> serviceClassDisc) {
        this.planName = planName;
        this.planId = planId;
        this.serviceClassDisc = serviceClassDisc;
        this.decimalValue = 0.0;
    }

    public ServiceOfferingPlanBitModel(String planName, Integer planId, HashMap<Integer, String> serviceClassDisc, HashMap<Integer, ServiceOfferingBitModel> bitModelHashMap) {
        this.planName = planName;
        this.planId = planId;
        this.serviceClassDisc = serviceClassDisc;
        this.bitModelHashMap = bitModelHashMap;
        this.decimalValue = 0.0;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public HashMap<Integer, String> getServiceClassDisc() {
        return serviceClassDisc;
    }

    public void setServiceClassDisc(HashMap<Integer, String> serviceClassDisc) {
        this.serviceClassDisc = serviceClassDisc;
    }

    public HashMap<Integer, ServiceOfferingBitModel> getBitModelHashMap() {
        return bitModelHashMap;
    }

    public void setBitModelHashMap(HashMap<Integer, ServiceOfferingBitModel> bitModelHashMap) {
        this.bitModelHashMap = bitModelHashMap;
    }
    public Double getDecimalValue() {
        return decimalValue;
    }

    public void setDecimalValue(Double decimalValue) {
        this.decimalValue = decimalValue;
    }
}
