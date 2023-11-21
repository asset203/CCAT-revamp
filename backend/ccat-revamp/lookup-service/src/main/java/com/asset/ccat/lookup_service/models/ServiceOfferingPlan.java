package com.asset.ccat.lookup_service.models;

import java.util.HashMap;

/**
 * @author marwa.elshawarby
 */
public class ServiceOfferingPlan {

    private Integer planId;
    private String name;
    private HashMap<Integer, Boolean> servicePlanBits;

    private Integer decimalValue;

    private HashMap<Integer, SOServiceClassDescriptionModel> soServiceClassDescriptions;

    public HashMap<Integer, SOServiceClassDescriptionModel> getSoServiceClassDescriptions() {
        return soServiceClassDescriptions;
    }

    public void setSoServiceClassDescriptions(HashMap<Integer, SOServiceClassDescriptionModel> soServiceClassDescriptions) {
        this.soServiceClassDescriptions = soServiceClassDescriptions;
    }

    public Integer getDecimalValue() {
        return decimalValue;
    }

    public void setDecimalValue(Integer decimalValue) {
        this.decimalValue = decimalValue;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<Integer, Boolean> getServicePlanBits() {
        return servicePlanBits;
    }

    public void setServicePlanBits(HashMap<Integer, Boolean> servicePlanBits) {
        this.servicePlanBits = servicePlanBits;
    }
}
