package com.asset.ccat.springcloudconfigserver.models.response;

import com.asset.ccat.springcloudconfigserver.models.SystemConfigurationModel;

import java.util.HashMap;
import java.util.List;

public class GetAllConfigurationsResponse {

    private HashMap<String, List<SystemConfigurationModel>> configurations;

    public GetAllConfigurationsResponse(HashMap<String, List<SystemConfigurationModel>> configurations) {
        this.configurations = configurations;
    }

    public GetAllConfigurationsResponse() {
    }

    public HashMap<String, List<SystemConfigurationModel>> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(HashMap<String, List<SystemConfigurationModel>> configurations) {
        this.configurations = configurations;
    }
}
