package com.asset.ccat.gateway.models.requests.lookup;

import com.asset.ccat.gateway.constants.ActivityType;
import com.asset.ccat.gateway.models.requests.BaseRequest;

public class GetAllCallActivitiesRequest extends BaseRequest {

    private ActivityType activityType;
    private Integer parentId;

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

    @Override
    public String toString() {
        return "GetAllCallActivitiesRequest{" +
                "activityType=" + activityType +
                ", parentId=" + parentId +
                '}';
    }
}
