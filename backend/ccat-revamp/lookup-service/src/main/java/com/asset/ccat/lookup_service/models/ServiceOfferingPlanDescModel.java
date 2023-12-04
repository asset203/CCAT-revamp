package com.asset.ccat.lookup_service.models;

public class ServiceOfferingPlanDescModel {

    private Integer planId;
    private String planDescription;

    public ServiceOfferingPlanDescModel() {
    }

    public ServiceOfferingPlanDescModel(Integer planId, String planDescription) {
        this.planId = planId;
        this.planDescription = planDescription;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public void setPlanDescription(String planDescription) {
        this.planDescription = planDescription;
    }
}
