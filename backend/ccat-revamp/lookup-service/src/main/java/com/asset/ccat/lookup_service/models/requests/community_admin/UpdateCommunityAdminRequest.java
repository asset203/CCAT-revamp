package com.asset.ccat.lookup_service.models.requests.community_admin;

import com.asset.ccat.lookup_service.models.CommunityAdminModel;
import com.asset.ccat.lookup_service.models.requests.BaseRequest;

/**
 * @author mohamed.metwaly
 */
public class UpdateCommunityAdminRequest extends BaseRequest {
    private CommunityAdminModel updatedCommunity;

    public UpdateCommunityAdminRequest() {
    }

    public CommunityAdminModel getUpdatedCommunity() {
        return updatedCommunity;
    }

    public void setUpdatedCommunity(CommunityAdminModel updatedCommunity) {
        this.updatedCommunity = updatedCommunity;
    }
}
