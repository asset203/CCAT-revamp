package com.asset.ccat.springcloudconfigserver.models.requests;

import com.asset.ccat.springcloudconfigserver.models.SystemConfigurationModel;

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
}
