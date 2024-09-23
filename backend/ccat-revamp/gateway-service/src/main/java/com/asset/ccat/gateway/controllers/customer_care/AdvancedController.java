package com.asset.ccat.gateway.controllers.customer_care;

import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.customer_care.advanced.DisconnectSubscriberRequest;
import com.asset.ccat.gateway.models.requests.customer_care.advanced.InstallSubscriberRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.AdvancedService;
import com.asset.ccat.gateway.validators.customer_care.AdvancedValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = Defines.ContextPaths.ADVANCED)
public class AdvancedController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AdvancedService advancedService;
    @Autowired
    private AdvancedValidator advancedValidator;


    @LogFootprint
    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse install(HttpServletRequest req, @RequestBody InstallSubscriberRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        String profileName = tokendata.get(Defines.SecurityKeywords.PROFILE_NAME).toString();
        Optional.ofNullable(request.getFootprintModel()).ifPresent(footprintModel ->
                request.getFootprintModel().setProfileName(profileName));
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Install Subscriber Request [" + request + "]");
        advancedValidator.validateSubscriberInstall(request);
        BaseResponse response = advancedService.installSubscriber(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Install Subscriber Request Successfully!!");

        return new BaseResponse<>(response.getStatusCode(),
                response.getStatusMessage(),
                response.getSeverity(),
                request.getRequestId(),
                null);
    }


    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse disconnect(HttpServletRequest req, @RequestBody DisconnectSubscriberRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Disconnect Subscriber Request [" + request + "]");
        advancedValidator.validateSubscriberDisconnect(request);
        BaseResponse response = advancedService.disconnectSubscriber(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Disconnect Subscriber Request Successfully!!");

        return new BaseResponse<>(response.getStatusCode(),
                response.getStatusMessage(),
                response.getSeverity(),
                request.getRequestId(),
                null);
    }
}
