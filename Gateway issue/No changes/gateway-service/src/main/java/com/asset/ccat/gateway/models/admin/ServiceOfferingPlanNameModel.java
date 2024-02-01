package com.asset.ccat.gateway.models.admin;

public class ServiceOfferingPlanNameModel {

    private Integer planId;
    private String name;

    public ServiceOfferingPlanNameModel() {
    }

    public ServiceOfferingPlanNameModel(Integer planId, String planName) {
        this.planId = planId;
        this.name = planName;
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

    @Override
    public String toString() {
        return "ServiceOfferingPlanName{" + "planId=" + planId + ", planName='" + name + '\'' + '}';
    }
}
