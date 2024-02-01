package com.asset.ccat.gateway.models.requests.admin.community_admin;

import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author mohamed.metwaly
 */
public class DeleteCommunityAdminRequest extends BaseRequest {
    private Integer communityId;

    public DeleteCommunityAdminRequest() {
    }

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    @Override
    public String toString() {
        return "DeleteCommunityAdminRequest{" +
                "communityId=" + communityId +
                '}';
    }
}
