package com.asset.ccat.gateway.models.requests.admin.profile;

import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author nour.ihab
 */
public class GetProfileRequest extends BaseRequest {

    private Integer profileId;

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    @Override
    public String toString() {
        return "GetProfileRequest{" +
                "profileId=" + profileId +
                '}';
    }
}
