package com.asset.ccat.lookup_service.models.requests.community_admin;


import com.asset.ccat.lookup_service.models.CommunityAdminModel;
import com.asset.ccat.lookup_service.models.requests.BaseRequest;

/**
 * @author mohamed.metwaly
 */
public class AddCommunityAdminRequest extends BaseRequest {
    private CommunityAdminModel addedCommunity;

    public AddCommunityAdminRequest() {
    }

    public CommunityAdminModel getAddedCommunity() {
        return addedCommunity;
    }

    public void setAddedCommunity(CommunityAdminModel addedCommunity) {
        this.addedCommunity = addedCommunity;
    }


}
