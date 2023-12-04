package com.asset.ccat.springcloudconfigserver.models.requests;

public class GetAllConfigurationsRequest extends BaseRequest {

    private String profile;
    private String label;

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
