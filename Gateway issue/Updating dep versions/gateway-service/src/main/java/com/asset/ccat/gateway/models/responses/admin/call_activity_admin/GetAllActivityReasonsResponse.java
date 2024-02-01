package com.asset.ccat.gateway.models.responses.admin.call_activity_admin;

import com.asset.ccat.gateway.models.admin.ReasonActivityModel;

import java.util.ArrayList;
import java.util.List;

public class GetAllActivityReasonsResponse {

    private List<ReasonActivityModel> reasonActivities;

    public GetAllActivityReasonsResponse() {
    }

    public GetAllActivityReasonsResponse(List<ReasonActivityModel> reasonActivities) {
        this.reasonActivities = reasonActivities;
    }

    public List<ReasonActivityModel> getReasonActivities() {
        return reasonActivities;
    }

    public void setReasonActivities(ArrayList<ReasonActivityModel> reasonActivities) {
        this.reasonActivities = reasonActivities;
    }

    @Override
    public String toString() {
        return "GetAllReasonActivitiesResponse{" +
                "reasonActivities=" + reasonActivities +
                '}';
    }
}
