package com.asset.ccat.air_service.controllers;

import com.asset.ccat.air_service.defines.Defines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.requests.UpdateServiceClassRequest;
import com.asset.ccat.air_service.models.responses.BaseResponse;
import com.asset.ccat.air_service.models.shared.ServiceInfo;
import com.asset.ccat.air_service.services.ServiceClassService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author wael.mohamed
 */
@RestController
@RequestMapping(value = Defines.ContextPaths.SERVICE_CLASSES)
public class ServiceClassController {

    @Autowired
    Environment environment;
    @Autowired
    private ServiceClassService serviceClassService;

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateServiceClass(@RequestBody UpdateServiceClassRequest updateServiceClassRequest) throws AIRServiceException, AIRException, UnknownHostException {
        ThreadContext.put("sessionId", updateServiceClassRequest.getSessionId());
        ThreadContext.put("requestId", updateServiceClassRequest.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Update Service Class request started with body = {}", updateServiceClassRequest);
        serviceClassService.updateServiceClass(updateServiceClassRequest);
        CCATLogger.DEBUG_LOGGER.info("Update Service Class request ended.");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR,
                new ServiceInfo(InetAddress.getLocalHost().getHostAddress(), environment.getProperty("server.port")),
                null);
    }
}
