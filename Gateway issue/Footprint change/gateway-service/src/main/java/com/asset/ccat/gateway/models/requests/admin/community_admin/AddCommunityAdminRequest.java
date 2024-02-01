package com.asset.ccat.gateway.models.requests.admin.community_admin;

import com.asset.ccat.gateway.models.requests.BaseRequest;
import com.asset.ccat.gateway.models.shared.CommunitiesModel;

/**
 * @author mohamed.metwaly
 */
public class AddCommunityAdminRequest extends BaseRequest {
    private CommunitiesModel addedCommunity;

    public AddCommunityAdminRequest() {
    }

    public CommunitiesModel getAddedCommunity() {
        return addedCommunity;
    }

    public void setAddedCommunity(CommunitiesModel addedCommunity) {
        this.addedCommunity = addedCommunity;
    }

    @Override
    public String toString() {
        return "AddCommunityAdminRequest{" +
                "addedCommunity=" + addedCommunity +
                '}';
    }
}
