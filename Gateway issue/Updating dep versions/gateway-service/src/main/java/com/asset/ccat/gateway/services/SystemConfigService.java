package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.system_config.GetAllConfigurationsRequest;
import com.asset.ccat.gateway.models.requests.admin.system_config.UpdateConfigurationsRequest;
import com.asset.ccat.gateway.models.responses.admin.system_config.GetAllConfigurationsResponse;
import com.asset.ccat.gateway.proxy.ServiceRefreshProxy;
import com.asset.ccat.gateway.proxy.SystemConfigProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemConfigService {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Value("${spring.cloud.config.label}")
    private String label;

    @Autowired
    SystemConfigProxy configProxy;

    private final Properties properties;

    @Autowired
    ServiceRefreshProxy serviceRefreshProxy;

    public SystemConfigService(Properties properties) {
        this.properties = properties;
    }

    public GetAllConfigurationsResponse getAllConfigurations(GetAllConfigurationsRequest request) throws GatewayException {
        request.setProfile(activeProfile);
        request.setLabel(label);
        CCATLogger.DEBUG_LOGGER.info("Get all system configurations calling proxy");
        GetAllConfigurationsResponse configs = configProxy.getAllConfigs(request);
//        configs.getConfigurations().forEach((s, systemConfigurationModels) -> {
//                systemConfigurationModels.forEach(systemConfigurationModel -> {
//                    if(systemConfigurationModel.getValueType().intValue()==4){
//                        systemConfigurationModels.remove(systemConfigurationModel);
//                        systemConfigurationModel.setValue(null);
//                        systemConfigurationModels.add(systemConfigurationModel);
//                    }
//                });
//           });
        return configs;

    }

    public void updateConfigurations(UpdateConfigurationsRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Start serving update system configurations request ");
        request.setProfile(activeProfile);
        request.setLabel(label);
        configProxy.updateSystemConfigurations(request);

        CCATLogger.DEBUG_LOGGER.debug("Start refreshing all services configurations");
        String actuatorURI = "/actuator/busrefresh";

        String airURI = properties.getAirServiceUrls()
                + Defines.ContextPaths.AIR_SERVICE_CONTEXT_PATH
                + actuatorURI;
        serviceRefreshProxy.refreshService(airURI);

        String ciURI = properties.getCiServiceUrls()
                + Defines.CI_SERVICE.CONTEXT_PATH
                + actuatorURI;
        serviceRefreshProxy.refreshService(ciURI);

        String historyURI = properties.getHistoryServiceUrls()
                + Defines.ContextPaths.HISTORY_SERVICE_CONTEXT_PATH
                + actuatorURI;
        serviceRefreshProxy.refreshService(historyURI);

        String lookupURI = properties.getLookupsServiceUrls()
                + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                + actuatorURI;
        serviceRefreshProxy.refreshService(lookupURI);

        String nbaURI = properties.getNbaServiceUrls()
                + Defines.ContextPaths.NBA_SERVICE
                + actuatorURI;
        serviceRefreshProxy.refreshService(nbaURI);

        String odsURI = properties.getOdsServiceUrls()
                + Defines.ODS_SERVICE.CONTEXT_PATH
                + actuatorURI;
        serviceRefreshProxy.refreshService(odsURI);

        String userManagementURI = properties.getUserManagementUrls()
                + Defines.ContextPaths.USER_MANAGEMENT_CONTEXT_PATH
                + actuatorURI;
        serviceRefreshProxy.refreshService(userManagementURI);

        CCATLogger.DEBUG_LOGGER.debug("Finished refreshing all services configurations");

        CCATLogger.DEBUG_LOGGER.info("Finished serving update system configurations request successfully");
    }
}
