package com.asset.ccat.gateway.models.responses.admin.profile;

import com.asset.ccat.gateway.models.users.ProfileModel;
import com.asset.ccat.gateway.models.users.UserProfileModel;
import java.util.List;

/**
 *
 * @author nour.ihab
 */
public class GetAllProfilesResponse {

    private List<ProfileModel> profilesList;

    public GetAllProfilesResponse() {
    }

    public GetAllProfilesResponse(List<ProfileModel> profilesList) {
        this.profilesList = profilesList;
    }

    public List<ProfileModel> getProfilesList() {
        return profilesList;
    }

    public void setProfilesList(List<ProfileModel> profilesList) {
        this.profilesList = profilesList;
    }
}
