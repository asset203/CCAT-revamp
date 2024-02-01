package com.asset.ccat.gateway.models.admin;

/**
 * @author mohamed.metwaly
 */
public class AdminFafPlanModel {
    private  Integer planId;
    private  String name;
    private Integer fafIndicatorId;

    public AdminFafPlanModel() {
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

    public Integer getFafIndicatorId() {
        return fafIndicatorId;
    }

    public void setFafIndicatorId(Integer fafIndicatorId) {
        this.fafIndicatorId = fafIndicatorId;
    }
}
