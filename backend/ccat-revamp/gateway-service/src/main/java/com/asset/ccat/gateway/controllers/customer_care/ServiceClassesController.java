package com.asset.ccat.gateway.controllers.customer_care;

import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.annotation.SubscriberOwnership;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.customer_care.ServiceClassModel;
import com.asset.ccat.gateway.models.requests.service_class.GetAllServiceClassRequest;
import com.asset.ccat.gateway.models.requests.service_class.ServiceClassRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.GetAllServiceClassesResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.ServiceClassesService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author wael.mohamed
 */
@RestController
@RequestMapping(path = Defines.ContextPaths.SERVICE_CLASSES)
public class ServiceClassesController {

    @Autowired
    private ServiceClassesService serviceClassesService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @CrossOrigin(origins = "*")
    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllServiceClassesResponse> getAllServiceClasses(HttpServletRequest req, @RequestBody GetAllServiceClassRequest request) throws GatewayException {
        HashMap<String, Object> tokenData = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokenData.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokenData.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All Service Classes Request [" + request + "]");
        List<ServiceClassModel> response = serviceClassesService.getAllServiceClasses();
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All Service Classes Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                new GetAllServiceClassesResponse(response));
    }


    @CrossOrigin(origins = "*")
    @LogFootprint
    @SubscriberOwnership
    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse<String> updateServiceClass(HttpServletRequest servletRequest,
                                           @RequestBody ServiceClassRequest request) throws GatewayException {
        HashMap<String, Object> tokenData = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokenData.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokenData.get(Defines.SecurityKeywords.USERNAME).toString();
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Update Service Class request started with body = {}", request);
        serviceClassesService.updateServiceClasses(request);
        CCATLogger.DEBUG_LOGGER.info("Update Service Class request ended.");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }
}
