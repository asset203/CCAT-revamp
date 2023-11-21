package com.asset.ccat.gateway.models.requests.admin.profile;

import com.asset.ccat.gateway.models.requests.BaseRequest;
import com.asset.ccat.gateway.models.users.ProfileModel;

/**
 * @author nour.ihab
 */
public class AddProfileRequest extends BaseRequest {

    private ProfileModel profile;

    public ProfileModel getProfile() {
        return profile;
    }

    public void setProfile(ProfileModel profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "AddProfileRequest{" +
                "profile=" + profile +
                '}';
    }
}
