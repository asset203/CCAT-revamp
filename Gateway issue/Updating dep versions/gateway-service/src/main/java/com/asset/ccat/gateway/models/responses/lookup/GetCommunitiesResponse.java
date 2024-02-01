package com.asset.ccat.gateway.models.responses.lookup;

import java.util.List;

import com.asset.ccat.gateway.models.shared.CommunitiesModel;

public class GetCommunitiesResponse {

    List<CommunitiesModel> communities;

    public GetCommunitiesResponse() {
    }

    public GetCommunitiesResponse(List<CommunitiesModel> communities) {
        this.communities = communities;
    }

    public List<CommunitiesModel> getCommunities() {
        return communities;
    }

    public void setCommunities(List<CommunitiesModel> communities) {
        this.communities = communities;
    }
}
