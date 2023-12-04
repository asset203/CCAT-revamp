package com.asset.ccat.gateway.models.requests.admin.system_config;

import com.asset.ccat.gateway.models.admin.SystemConfigurationModel;
import com.asset.ccat.gateway.models.requests.BaseRequest;

import java.util.List;

public class UpdateConfigurationsRequest extends BaseRequest {

    private String profile;
    private String label;
    private List<SystemConfigurationModel> configurations;

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

    public List<SystemConfigurationModel> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<SystemConfigurationModel> configurations) {
        this.configurations = configurations;
    }

    @Override
    public String toString() {
        return "UpdateConfigurationsRequest{" +
                "profile='" + profile + '\'' +
                ", label='" + label + '\'' +
                ", configurations=" + configurations +
                '}';
    }
}
