package com.asset.ccat.springcloudconfigserver.services;

import com.asset.ccat.springcloudconfigserver.database.dao.AdmSystemPropertiesDao;
import com.asset.ccat.springcloudconfigserver.exceptions.ConfigServerException;
import com.asset.ccat.springcloudconfigserver.loggers.CCATLogger;
import com.asset.ccat.springcloudconfigserver.models.SystemConfigurationModel;
import com.asset.ccat.springcloudconfigserver.models.requests.GetAllConfigurationsRequest;
import com.asset.ccat.springcloudconfigserver.models.requests.UpdateConfigurationsRequest;
import com.asset.ccat.springcloudconfigserver.models.response.GetAllConfigurationsResponse;
import com.asset.ccat.springcloudconfigserver.utils.PasswordEncryptorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Component
public class SystemConfigService {

    @Autowired
    private AdmSystemPropertiesDao admSystemPropertiesDao;

    private final PasswordEncryptorUtil passwordEncryptorUtil;

    public SystemConfigService(PasswordEncryptorUtil passwordEncryptorUtil) {
        this.passwordEncryptorUtil = passwordEncryptorUtil;
    }

    public GetAllConfigurationsResponse getAllSystemConfig(GetAllConfigurationsRequest request) throws ConfigServerException {
        CCATLogger.DEBUG_LOGGER.info("Start serving get all system configurations request for profile [" + request.getProfile() + "]");
        HashMap<String, List<SystemConfigurationModel>> allConfig = admSystemPropertiesDao.retrieveSystemPropertiesByProfileAndLabel(request.getProfile(), request.getLabel());
        CCATLogger.DEBUG_LOGGER.info("Finished serving get all system configurations request successfully");
        return new GetAllConfigurationsResponse(allConfig);
    }

    public void updateSystemConfig(UpdateConfigurationsRequest request) throws Exception {
        CCATLogger.DEBUG_LOGGER.info("Start serving update system configurations request for profile [" + request.getProfile() + "]");
        List<SystemConfigurationModel> encryptedConfigs = passwordEncryptorUtil.passwordEncryptor(request);
        request.setConfigurations(encryptedConfigs);
        admSystemPropertiesDao.updateSystemPropertiesByProfileAndLabel(request.getProfile(), request.getLabel(), request.getConfigurations());
        CCATLogger.DEBUG_LOGGER.info("Finished serving update system configurations request successfully");
    }
}
