package com.asset.ccat.lookup_service.models.requests.community_admin;

import com.asset.ccat.lookup_service.models.requests.BaseRequest;

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
}
