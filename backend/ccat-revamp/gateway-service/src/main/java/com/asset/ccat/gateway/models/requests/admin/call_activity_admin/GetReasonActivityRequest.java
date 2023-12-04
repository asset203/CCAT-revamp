package com.asset.ccat.gateway.models.requests.admin.call_activity_admin;

import com.asset.ccat.gateway.models.requests.BaseRequest;

public class GetReasonActivityRequest extends BaseRequest {

    private Integer activityId;

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    @Override
    public String toString() {
        return "GetReasonActivityRequest{" +
                "activityId=" + activityId +
                '}';
    }
}
