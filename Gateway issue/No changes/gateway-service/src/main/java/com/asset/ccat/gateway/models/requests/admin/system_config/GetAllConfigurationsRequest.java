package com.asset.ccat.gateway.models.requests.admin.system_config;

import com.asset.ccat.gateway.models.requests.BaseRequest;

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

    @Override
    public String toString() {
        return "GetAllConfigurationsRequest{" +
                "profile='" + profile + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
