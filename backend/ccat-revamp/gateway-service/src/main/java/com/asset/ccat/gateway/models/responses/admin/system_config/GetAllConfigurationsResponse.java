package com.asset.ccat.gateway.models.responses.admin.system_config;

import com.asset.ccat.gateway.models.admin.SystemConfigurationModel;

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
