package com.asset.ccat.gateway.models.responses.admin.community_admin;

import com.asset.ccat.gateway.models.shared.CommunitiesModel;

import java.util.List;

public class GetAllCommunitiesAdminResponse {

    private List<CommunitiesModel> communitiesAdmin;

    public GetAllCommunitiesAdminResponse(List<CommunitiesModel> communitiesAdmin) {
        this.communitiesAdmin = communitiesAdmin;
    }
    public GetAllCommunitiesAdminResponse(){}

    public List<CommunitiesModel> getCommunitiesAdmin() {
        return communitiesAdmin;
    }

    public void setCommunitiesAdmin(List<CommunitiesModel> communitiesAdmin) {
        this.communitiesAdmin = communitiesAdmin;
    }
}
