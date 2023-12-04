package com.asset.ccat.user_management.models.responses.profile;

import com.asset.ccat.user_management.models.users.ProfileModel;
import com.asset.ccat.user_management.models.users.UserProfileModel;

/**
 *
 * @author marwa.elshawarby
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
