package com.asset.ccat.gateway.models.admin;

import java.util.HashMap;

public class ServiceOfferingPlan {

    private Integer planId;
    private String name;
    private HashMap<Integer, Boolean> servicePlanBits;

//    private HashMap<Integer, SOServiceClassDescriptionModel> soServiceClassDescriptions;

    public ServiceOfferingPlan(Integer planId, String planName, HashMap<Integer, Boolean> servicePlanBits) {

        this.planId = planId;
        this.name = planName;
        this.servicePlanBits = servicePlanBits;
    }

    public ServiceOfferingPlan() {
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
