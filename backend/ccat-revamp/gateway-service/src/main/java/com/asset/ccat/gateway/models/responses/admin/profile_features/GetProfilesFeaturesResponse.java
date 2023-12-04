package com.asset.ccat.gateway.models.responses.admin.profile_features;

import com.asset.ccat.gateway.models.admin.ExtractProfileModel;

import java.util.List;

/**
 * @author mohamed.metwaly
 */
public class GetProfilesFeaturesResponse {
    private List<ExtractProfileModel> profiles;

    public GetProfilesFeaturesResponse() {
    }

    public GetProfilesFeaturesResponse(List<ExtractProfileModel> profiles) {
        this.profiles = profiles;
    }

    public List<ExtractProfileModel> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<ExtractProfileModel> profiles) {
        this.profiles = profiles;
    }

    @Override
    public String toString() {
        return "GetProfilesFeaturesResponse{" +
                "profiles=" + profiles +
                '}';
    }
}
