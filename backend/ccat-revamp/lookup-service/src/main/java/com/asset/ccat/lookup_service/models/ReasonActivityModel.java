package com.asset.ccat.lookup_service.models;


import com.asset.ccat.lookup_service.constants.ActivityType;

import java.util.ArrayList;
import java.util.List;

public class ReasonActivityModel {

    private Integer activityId;
    private ActivityType activityType;
    private String activityName;
    private Integer parentId;
    private ArrayList<Integer> parents;


    public ReasonActivityModel() {
    }

    public ReasonActivityModel(Integer activityId, ActivityType activityType, Integer parentId, String activityName, ArrayList<Integer> parents) {
        this.activityId = activityId;
        this.activityType = activityType;
        this.parentId = parentId;
        this.activityName = activityName;
        this.parents = parents;
    }

    public ReasonActivityModel(Integer activityId, ActivityType activityType, Integer parentId, String activityName) {
        this.activityId = activityId;
        this.activityType = activityType;
        this.activityName = activityName;
        this.parentId = parentId;
    }

    public ReasonActivityModel(ActivityType activityType, Integer parentId, String activityName) {
        this.activityType = activityType;
        this.activityName = activityName;
        this.parentId = parentId;
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

    public ArrayList<Integer> getParents() {

        return parents;
    }

    public void setParents(ArrayList<Integer> parents) {
        parents = parents;
    }

    @Override
    public String toString() {
        return "ReasonActivityModel{" +
                "activityId=" + activityId +
                ", activityType=" + activityType +
                ", parentId=" + parentId +
                ", activityName='" + activityName + '\'' +
                ", parents=" + parents +
                '}';
    }
}

