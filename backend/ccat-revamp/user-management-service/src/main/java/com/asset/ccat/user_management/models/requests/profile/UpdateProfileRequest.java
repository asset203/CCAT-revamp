package com.asset.ccat.user_management.models.requests.profile;

import com.asset.ccat.user_management.models.requests.BaseRequest;
import com.asset.ccat.user_management.models.users.ProfileModel;

/**
 *
 * @author marwa.elshawarby
 */
public class UpdateProfileRequest extends BaseRequest {

    private ProfileModel profile;

    public ProfileModel getProfile() {
        return profile;
    }

    public void setProfile(ProfileModel profile) {
        this.profile = profile;
    }

}
