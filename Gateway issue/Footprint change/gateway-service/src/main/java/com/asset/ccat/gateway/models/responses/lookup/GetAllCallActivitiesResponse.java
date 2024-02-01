package com.asset.ccat.gateway.models.responses.lookup;

import com.asset.ccat.gateway.models.admin.ReasonActivityModel;

import java.util.List;

public class GetAllCallActivitiesResponse {

    private List<ReasonActivityModel> activitiesList;

    public GetAllCallActivitiesResponse() {
    }

    public GetAllCallActivitiesResponse(List<ReasonActivityModel> activitiesList) {
        this.activitiesList = activitiesList;
    }

    public List<ReasonActivityModel> getActivitiesList() {
        return activitiesList;
    }

    public void setActivitiesList(List<ReasonActivityModel> activitiesList) {
        this.activitiesList = activitiesList;
    }

    @Override
    public String toString() {
        return "GetAllCallActivitiesResponse{" +
                "activitiesList=" + activitiesList +
                '}';
    }
}
