package com.asset.ccat.user_management.models.requests.profile;

import com.asset.ccat.user_management.models.requests.BaseRequest;

/**
 *
 * @author marwa.elshawarby
 */
public class GetProfileRequest extends BaseRequest {

    private Integer profileId;

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }
}
