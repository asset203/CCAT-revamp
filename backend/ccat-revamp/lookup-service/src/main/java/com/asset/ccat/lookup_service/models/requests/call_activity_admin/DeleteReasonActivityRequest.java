package com.asset.ccat.lookup_service.models.requests.call_activity_admin;


import com.asset.ccat.lookup_service.models.requests.BaseRequest;

public class DeleteReasonActivityRequest extends BaseRequest {

    private Integer activityId;

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }
}
