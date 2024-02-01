package com.asset.ccat.gateway.models.requests.admin.community_admin;

import com.asset.ccat.gateway.models.requests.BaseRequest;
import com.asset.ccat.gateway.models.shared.CommunitiesModel;

/**
 * @author mohamed.metwaly
 */
public class UpdateCommunityAdminRequest extends BaseRequest {
    private CommunitiesModel updatedCommunity;

    public UpdateCommunityAdminRequest() {
    }

    public CommunitiesModel getUpdatedCommunity() {
        return updatedCommunity;
    }

    public void setUpdatedCommunity(CommunitiesModel updatedCommunity) {
        this.updatedCommunity = updatedCommunity;
    }

    @Override
    public String toString() {
        return "UpdateCommunityAdminRequest{" +
                "updatedCommunity=" + updatedCommunity +
                '}';
    }
}
