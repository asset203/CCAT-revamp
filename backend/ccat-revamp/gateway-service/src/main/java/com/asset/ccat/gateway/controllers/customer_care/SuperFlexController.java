package com.asset.ccat.gateway.controllers.customer_care;

import com.asset.ccat.gateway.annotation.SubscriberOwnership;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.SubscriberRequest;
import com.asset.ccat.gateway.models.requests.customer_care.super_flex.ActivateAddonRequest;
import com.asset.ccat.gateway.models.requests.customer_care.super_flex.ActivateThresholdRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.customer_care.super_flex.GetMIThresholdResponse;
import com.asset.ccat.gateway.models.responses.customer_care.super_flex.GetOptInAddonsResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.SuperFlexService;
import com.asset.ccat.gateway.validators.customer_care.SuperFlexValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping(Defines.ContextPaths.SUPER_FLEX)
public class SuperFlexController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private SuperFlexValidator superFlexValidator;
    @Autowired
    private SuperFlexService superFlexService;

    @SubscriberOwnership
    @PostMapping(value = Defines.ContextPaths.SUPER_FLEX_ADDONS + Defines.WEB_ACTIONS.GET)
    public BaseResponse<GetOptInAddonsResponse> getOptinAddons(@RequestBody SubscriberRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setRequestId(UUID.randomUUID().toString());
        request.setUsername(username);
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Optin Addons Request [" + request + "]");
        superFlexValidator.validateGetOptinAddonsRequest(request);
        GetOptInAddonsResponse response = superFlexService.getOptinAddons(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Optin Addons Request Successfully!!");


        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                response);
    }

    @SubscriberOwnership
    @PostMapping(value = Defines.ContextPaths.SUPER_FLEX_ADDONS + Defines.WEB_ACTIONS.ACTIVATE)
    public BaseResponse activateAddon(@RequestBody ActivateAddonRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setRequestId(UUID.randomUUID().toString());
        request.setUsername(username);
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Activate Addon Request [" + request + "]");
        superFlexValidator.validateActivateAddonRequest(request);
        superFlexService.activateAddon(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Activate Addon Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }

    @SubscriberOwnership
    @PostMapping(value = Defines.ContextPaths.SUPER_FLEX_ADDONS + Defines.WEB_ACTIONS.DEACTIVATE)
    public BaseResponse deactivateAddon(@RequestBody SubscriberRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setRequestId(UUID.randomUUID().toString());
        request.setUsername(username);
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received DeActivate Addon Request [" + request + "]");
        superFlexValidator.validateDeactivateAddonRequest(request);
        superFlexService.deactivateAddon(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving DeActivate Addon Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }

    @SubscriberOwnership
    @PostMapping(value = Defines.ContextPaths.SUPER_FLEX_THRESHOLDS + Defines.WEB_ACTIONS.GET)
    public BaseResponse<GetMIThresholdResponse> getMiThreshold(@RequestBody SubscriberRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setRequestId(UUID.randomUUID().toString());
        request.setUsername(username);
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Mi Threshold Request [" + request + "]");
        superFlexValidator.validateGetMiThresholdRequest(request);
        GetMIThresholdResponse response = superFlexService.getMiThreshold(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Mi Threshold Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                response);
    }

    @SubscriberOwnership
    @PostMapping(value = Defines.ContextPaths.SUPER_FLEX_THRESHOLDS + Defines.WEB_ACTIONS.ACTIVATE)
    public BaseResponse activateThreshold(@RequestBody ActivateThresholdRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setRequestId(UUID.randomUUID().toString());
        request.setUsername(username);
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Activate Threshold Request [" + request + "]");
        superFlexValidator.validateActivateThresholdRequest(request);
        superFlexService.activateThreshold(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Activate Threshold Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }

    @SubscriberOwnership
    @PostMapping(value = Defines.ContextPaths.SUPER_FLEX_THRESHOLDS + Defines.WEB_ACTIONS.DEACTIVATE)
    public BaseResponse deactivateThreshold(@RequestBody SubscriberRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setRequestId(UUID.randomUUID().toString());
        request.setUsername(username);
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received DeActivate Threshold Request [" + request + "]");
        superFlexValidator.validateDeactivateThresholdRequest(request);
        superFlexService.deactivateThreshold(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving DeActivate Threshold Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }

    @SubscriberOwnership
    @PostMapping(value = Defines.ContextPaths.SUPER_FLEX_STOP_MI)
    public BaseResponse stopMIThreshold(@RequestBody SubscriberRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setRequestId(UUID.randomUUID().toString());
        request.setUsername(username);
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Stop MI Threshold Request [" + request + "]");
        superFlexValidator.validateStopMIThresholdRequest(request);
        superFlexService.stopMIThreshold(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Stop MI Threshold Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }

    @SubscriberOwnership
    @PostMapping(value = Defines.ContextPaths.SUPER_FLEX_STOP_MI + Defines.WEB_ACTIONS.DEACTIVATE)
    public BaseResponse deactivateStopMI(@RequestBody SubscriberRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setRequestId(UUID.randomUUID().toString());
        request.setUsername(username);
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received DeActivate Stop MI Request [" + request + "]");
        superFlexValidator.validateDeactivateStopMIRequest(request);
        superFlexService.deactivateStopMI(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving DeActivate Stop MI Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }
}
