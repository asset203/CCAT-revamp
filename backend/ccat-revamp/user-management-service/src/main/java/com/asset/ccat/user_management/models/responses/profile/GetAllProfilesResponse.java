package com.asset.ccat.user_management.models.responses.profile;

import com.asset.ccat.user_management.models.users.ProfileModel;
import java.util.List;

/**
 *
 * @author marwa.elshawarby
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
