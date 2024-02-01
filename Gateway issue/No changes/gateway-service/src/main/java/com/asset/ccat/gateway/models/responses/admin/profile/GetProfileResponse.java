package com.asset.ccat.gateway.models.responses.admin.profile;

import com.asset.ccat.gateway.models.users.ProfileModel;
import com.asset.ccat.gateway.models.users.UserProfileModel;

/**
 *
 * @author nour.ihab
 */
public class GetProfileResponse {

    private ProfileModel profile;

    public GetProfileResponse() {
    }

    public GetProfileResponse(ProfileModel profile) {
        this.profile = profile;
    }

    public ProfileModel getProfile() {
        return profile;
    }

    public void setProfile(ProfileModel profile) {
        this.profile = profile;
    }
}
