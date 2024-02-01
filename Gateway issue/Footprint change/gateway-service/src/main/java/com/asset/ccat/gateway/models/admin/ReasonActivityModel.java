package com.asset.ccat.gateway.models.admin;

import com.asset.ccat.gateway.constants.ActivityType;

public class ReasonActivityModel {

    private Integer activityId;

    private ActivityType activityType;

    private Integer parentId;

    private String activityName;


    public ReasonActivityModel() {
    }

    public ReasonActivityModel(Integer activityId, ActivityType activityType, Integer parentId, String activityName) {
        this.activityId = activityId;
        this.activityType = activityType;
        this.parentId = parentId;
        this.activityName = activityName;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    @Override
    public String toString() {
        return "ReasonActivityModel{" +
                "activityId=" + activityId +
                ", activityType='" + activityType + '\'' +
                ", parentId=" + parentId +
                ", activityName='" + activityName + '\'' +
                '}';
    }
}
