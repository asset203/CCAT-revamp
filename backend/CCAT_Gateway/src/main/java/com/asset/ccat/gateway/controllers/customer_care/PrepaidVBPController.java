package com.asset.ccat.gateway.controllers.customer_care;

import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.customer_care.prepaidVBP.PrepaidCheckSubscriptionRequest;
import com.asset.ccat.gateway.models.requests.customer_care.prepaidVBP.PrepaidVBPSubscriptionRequest;
import com.asset.ccat.gateway.models.requests.customer_care.prepaidVBP.PrepaidVBPUnsubscriptionRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.customer_care.PrepaidVBPCheckResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.admin.PrepaidVBPService;
import com.asset.ccat.gateway.validators.customer_care.PrepaidVBPValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = Defines.ContextPaths.PREPAID_VBP)
public class PrepaidVBPController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PrepaidVBPService prepaidVBPService;
    @Autowired
    private PrepaidVBPValidator prepaidVBPValidator;


    @LogFootprint
    @PostMapping(Defines.WEB_ACTIONS.SUBSCRIBE)
    public BaseResponse subscribe(@RequestBody PrepaidVBPSubscriptionRequest request) throws GatewayException {
        HashMap<String, Object> tokenData = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokenData.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokenData.get(Defines.SecurityKeywords.USERNAME).toString();
        Integer userId = Integer.parseInt(tokenData.get(Defines.SecurityKeywords.USER_ID).toString());
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=["
                + username + "] requestId=[" + request.getRequestId() + "]");
        request.setUsername(username);
        request.setSessionId(sessionId);
        request.setRequestId(UUID.randomUUID().toString());
        request.setUserId(userId);
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("userId", request.getUserId() + "");
        CCATLogger.DEBUG_LOGGER.info("Received Prepaid VBP Subscription Request [" + request + "]");
        prepaidVBPValidator.validateSubscribe(request);
        prepaidVBPService.subscribeService(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Prepaid VBP Subscription Request Successfully!!");

        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                null);
    }

    @LogFootprint
    @PostMapping(Defines.WEB_ACTIONS.UNSUBSCRIBE)
    public BaseResponse unsubscribe(@RequestBody PrepaidVBPUnsubscriptionRequest request) throws GatewayException {
        HashMap<String, Object> tokenData = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokenData.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokenData.get(Defines.SecurityKeywords.USERNAME).toString();
        Integer userId = Integer.parseInt(tokenData.get(Defines.SecurityKeywords.USER_ID).toString());
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=["
                + username + "] requestId=[" + request.getRequestId() + "]");
        request.setUsername(username);
        request.setSessionId(sessionId);
        request.setRequestId(UUID.randomUUID().toString());
        request.setUserId(userId);
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("userId", request.getUserId() + "");
        CCATLogger.DEBUG_LOGGER.info("Received Prepaid VBP UnSubscription Request [" + request + "]");
        prepaidVBPValidator.validateUnsubscribe(request);
        prepaidVBPService.unsubscribeService(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Prepaid VBP UnSubscription Request Successfully!!");

        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                null);
    }

    @PostMapping(Defines.WEB_ACTIONS.CHECK)
    public BaseResponse<PrepaidVBPCheckResponse> checkSubscription(@RequestBody PrepaidCheckSubscriptionRequest request) throws GatewayException {
        HashMap<String, Object> tokenData = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokenData.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokenData.get(Defines.SecurityKeywords.USERNAME).toString();
        Integer userId = Integer.parseInt(tokenData.get(Defines.SecurityKeywords.USER_ID).toString());
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=["
                + username + "] requestId=[" + request.getRequestId() + "]");
        request.setUsername(username);
        request.setSessionId(sessionId);
        request.setRequestId(UUID.randomUUID().toString());
        request.setUserId(userId);
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("userId", request.getUserId() + "");
        CCATLogger.DEBUG_LOGGER.info("Received Prepaid Check Subscription Request [" + request + "]");
        prepaidVBPValidator.validateCheckSubscribe(request);
        PrepaidVBPCheckResponse res = prepaidVBPService.checkSubscribeService(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Prepaid Check Subscription Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                res);
    }
}
