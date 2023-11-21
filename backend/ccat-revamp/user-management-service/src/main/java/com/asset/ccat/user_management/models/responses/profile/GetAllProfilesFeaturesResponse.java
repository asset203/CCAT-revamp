package com.asset.ccat.user_management.models.responses.profile;

import com.asset.ccat.user_management.models.requests.profile.ProfileFeaturesModel;

import java.util.List;

public class GetAllProfilesFeaturesResponse {
    private List<ProfileFeaturesModel> profiles;

    public GetAllProfilesFeaturesResponse(List<ProfileFeaturesModel> profiles) {
        this.profiles = profiles;
    }

    public GetAllProfilesFeaturesResponse() {
    }

    public List<ProfileFeaturesModel> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<ProfileFeaturesModel> profiles) {
        this.profiles = profiles;
    }

    @Override
    public String toString() {
        return "GetAllProfilesFeaturesResponse{" +
                "profiles=" + profiles +
                '}';
    }
}
