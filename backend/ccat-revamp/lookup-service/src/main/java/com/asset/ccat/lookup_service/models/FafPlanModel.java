package com.asset.ccat.lookup_service.models;

public class FafPlanModel {

    private Integer planId;
    private String name;
    private Integer fafIndicatorId;
    private Integer mappedFafIndicatorId;

    public FafPlanModel() {
    }

    public FafPlanModel(Integer planId, String name, Integer fafIndicatorId, Integer mappedFafIndicatorId) {
        this.planId = planId;
        this.name = name;
        this.fafIndicatorId = fafIndicatorId;
        this.mappedFafIndicatorId = mappedFafIndicatorId;
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

    public Integer getMappedFafIndicatorId() {
        return mappedFafIndicatorId;
    }

    public void setMappedFafIndicatorId(Integer mappedFafIndicatorId) {
        this.mappedFafIndicatorId = mappedFafIndicatorId;
    }

    @Override
    public String toString() {
        return "FafPlanModel{" +
                " planId=" + planId +
                ", name='" + name + '\'' +
                ", fafIndicatorId=" + fafIndicatorId +
                ", mappedFafIndicatorId=" + mappedFafIndicatorId +
                '}';
    }
}
