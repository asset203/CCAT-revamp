package com.asset.ccat.gateway.models.customer_care.service_offering_lookup_models;

import java.util.HashMap;

public class ServiceOfferingPlanBitDetailsModel {
    private String planName;
    private Integer planId;
    private HashMap<Integer,String> serviceClassDisc;

    private HashMap<Integer, ServiceOfferingBitDetailsModel> bitModelHashMap;

    private Double decimalValue;



    public ServiceOfferingPlanBitDetailsModel() {
    }

    public ServiceOfferingPlanBitDetailsModel(String planName, Integer planId, HashMap<Integer, String> serviceClassDisc) {
        this.planName = planName;
        this.planId = planId;
        this.serviceClassDisc = serviceClassDisc;
        this.decimalValue = 0.0;
    }

    public ServiceOfferingPlanBitDetailsModel(String planName, Integer planId, HashMap<Integer, String> serviceClassDisc, HashMap<Integer, ServiceOfferingBitDetailsModel> bitModelHashMap) {
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

    public HashMap<Integer, ServiceOfferingBitDetailsModel> getBitModelHashMap() {
        return bitModelHashMap;
    }

    public void setBitModelHashMap(HashMap<Integer, ServiceOfferingBitDetailsModel> bitModelHashMap) {
        this.bitModelHashMap = bitModelHashMap;
    }
    public Double getDecimalValue() {
        return decimalValue;
    }

    public void setDecimalValue(Double decimalValue) {
        this.decimalValue = decimalValue;
    }
}
