package com.asset.ccat.air_service.models.responses.customer_care;

import com.asset.ccat.air_service.models.CommunitiesModel;

import java.util.List;

public class GetCommunitiesResponse {

    List<CommunitiesModel> selectedCommunities;
    List<CommunitiesModel> unSelectedCommunities;

    public GetCommunitiesResponse() {
    }

    public GetCommunitiesResponse(List<CommunitiesModel> selectedCommunities, List<CommunitiesModel> unSelectedCommunities) {
        this.selectedCommunities = selectedCommunities;
        this.unSelectedCommunities = unSelectedCommunities;
    }

    public List<CommunitiesModel> getSelectedCommunities() {
        return selectedCommunities;
    }

    public void setSelectedCommunities(List<CommunitiesModel> selectedCommunities) {
        this.selectedCommunities = selectedCommunities;
    }

    public List<CommunitiesModel> getUnSelectedCommunities() {
        return unSelectedCommunities;
    }

    public void setUnSelectedCommunities(List<CommunitiesModel> unSelectedCommunities) {
        this.unSelectedCommunities = unSelectedCommunities;
    }

    @Override
    public String toString() {
        return "GetCommunitiesResponse{" +
                "selectedCommunities=" + selectedCommunities +
                ", unSelectedCommunities=" + unSelectedCommunities +
                '}';
    }
}
