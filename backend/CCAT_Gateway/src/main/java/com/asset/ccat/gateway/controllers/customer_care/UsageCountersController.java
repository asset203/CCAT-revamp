package com.asset.ccat.gateway.controllers.customer_care;

import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.customer_care.usage_counter.*;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.customer_care.GetAllUsageCountersResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.UsageCountersService;
import com.asset.ccat.gateway.validators.customer_care.UsageCountersValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.UUID;


/**
 * @author nour.ihab
 */
@RestController
@RequestMapping(path = Defines.ContextPaths.USAGE_COUNTERS)
public class UsageCountersController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UsageCountersValidator usageCountersValidator;
    @Autowired
    private UsageCountersService usageCountersService;


    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    @CrossOrigin(origins = "*")
    public BaseResponse<GetAllUsageCountersResponse> getAllUsageCounters(HttpServletRequest req,
                                                                         @RequestBody GetAllUsageCountersRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All Usage Counters Request [" + request + "]");
        usageCountersValidator.validateGetAllUsageCounters(request);
        GetAllUsageCountersResponse getAllUsageCountersResponse = usageCountersService.getAllUsageCounters(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All Usage Counters Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                getAllUsageCountersResponse);
    }


    @CrossOrigin(origins = "*")
    @LogFootprint
    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse addUsageCounter(HttpServletRequest req,
                                        @RequestBody AddUsageCountersRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Add All Usage Counters Request [" + request + "]");
        usageCountersValidator.validateAddUsageCounters(request);
        usageCountersService.addUsageCounters(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Add All Usage Counters Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }

    @CrossOrigin(origins = "*")
    @LogFootprint
    @PostMapping(value = Defines.ContextPaths.USAGE_THRESHOLDS + Defines.WEB_ACTIONS.ADD)
    public BaseResponse addUsageCounterAndThresholds(HttpServletRequest req,
                                                     @RequestBody AddUsageCountersAndThresholdsRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Add Usage Counters And Thresholds Request [" + request + "]");
        usageCountersValidator.validateAddUsageCountersAndThresholds(request);
        usageCountersService.addUsageCountersAndThresholds(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Add Usage Counters And Thresholds Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }

    @CrossOrigin(origins = "*")
    @LogFootprint
    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateUsageCounters(HttpServletRequest req,
                                            @RequestBody UpdateUsageCountersRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Usage Counters Request [" + request + "]");
        usageCountersValidator.validateUpdateUsageCounters(request);
        usageCountersService.updateUsageCounters(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Usage Counters Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }


    @CrossOrigin(origins = "*")
    @LogFootprint
    @PostMapping(value = Defines.ContextPaths.USAGE_THRESHOLDS + Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateUsageCountersAndThresholds(HttpServletRequest req,
                                                         @RequestBody UpdateUsageCountersAndThresholdsRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Usage Counters And Thresholds Request [" + request + "]");
        usageCountersValidator.validateUpdateUsageCountersAndThresholds(request);
        usageCountersService.updateUsageCountersAndThresholds(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Usage Counters And Thresholds Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }


    @CrossOrigin(origins = "*")
    @LogFootprint
    @PostMapping(value = Defines.ContextPaths.USAGE_THRESHOLDS + Defines.WEB_ACTIONS.DELETE)
    public BaseResponse deleteUsageThresholds(HttpServletRequest req,
                                              @RequestBody DeleteUsageThresholdsRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Delete Usage Counters And Thresholds Request [" + request + "]");
        usageCountersValidator.validateDeleteUsageThresholds(request);
        usageCountersService.deleteUsageThresholds(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Delete Usage Counters And Thresholds Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }
}
