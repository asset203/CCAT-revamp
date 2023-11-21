package com.asset.ccat.springcloudconfigserver.controllers;

import com.asset.ccat.springcloudconfigserver.defines.Defines;
import com.asset.ccat.springcloudconfigserver.defines.ErrorCodes;
import com.asset.ccat.springcloudconfigserver.exceptions.ConfigServerException;
import com.asset.ccat.springcloudconfigserver.loggers.CCATLogger;
import com.asset.ccat.springcloudconfigserver.models.requests.GetAllConfigurationsRequest;
import com.asset.ccat.springcloudconfigserver.models.requests.UpdateConfigurationsRequest;
import com.asset.ccat.springcloudconfigserver.models.response.BaseResponse;
import com.asset.ccat.springcloudconfigserver.models.response.GetAllConfigurationsResponse;
import com.asset.ccat.springcloudconfigserver.services.SystemConfigService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Defines.ContextPaths.SYSTEM_CONFIGURATIONS)
public class SystemConfigController {

    @Autowired
    private SystemConfigService systemConfigService;

    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllConfigurationsResponse> getAllConfigurations(@RequestBody GetAllConfigurationsRequest request) throws ConfigServerException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Recieved get all system configurations request");
        GetAllConfigurationsResponse response = systemConfigService.getAllSystemConfig(request);
        CCATLogger.DEBUG_LOGGER.info("Finished get all system configurations request successfully");
        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateConfigurations(@RequestBody UpdateConfigurationsRequest request) throws Exception {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Recieved update all system configurations request");
        systemConfigService.updateSystemConfig(request);
        CCATLogger.DEBUG_LOGGER.info("Finished update all system configurations request successfully");
        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, null);
    }
}


