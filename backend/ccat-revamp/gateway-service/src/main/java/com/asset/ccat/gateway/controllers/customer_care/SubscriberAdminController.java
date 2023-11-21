package com.asset.ccat.gateway.controllers.customer_care;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.customer_care.SubscriberAccountModel;
import com.asset.ccat.gateway.models.requests.SubscriberRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.customer_care.GetMainProductResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.SubscriberAdminService;
import com.asset.ccat.gateway.validators.customer_care.CustomerBalanceValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author Mahmoud Shehab
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(Defines.ContextPaths.SUBSCRIBER_ACCOUNT)
public class SubscriberAdminController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private SubscriberAdminService subscriberService;
    @Autowired
    private CustomerBalanceValidator balanceAndDateValidator;


    @LogFootprint
    @LogExecutionTime
    @RequestMapping(value = Defines.WEB_ACTIONS.GET, method = RequestMethod.POST)
    public BaseResponse<SubscriberAccountModel> getSubscriberAccount(HttpServletRequest req,
                                                                     @RequestBody SubscriberRequest request) throws AuthenticationException, Exception {
        SubscriberAccountModel subscriberModel = null;
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setRequestId(UUID.randomUUID().toString());
        request.setUsername(username);
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Subscriber Account Request [" + request + "]");
        subscriberModel = subscriberService.getAccountDetails(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Subscriber Account Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                subscriberModel);
    }


    @LogFootprint
    @LogExecutionTime
    @RequestMapping(value = Defines.WEB_ACTIONS.GET + "test", method = RequestMethod.POST)
    public BaseResponse<SubscriberAccountModel> test(HttpServletRequest req,
                                                                     @RequestBody SubscriberRequest request) throws AuthenticationException, Exception {
        SubscriberAccountModel subscriberModel = null;
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setRequestId(UUID.randomUUID().toString());
        request.setUsername(username);
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Subscriber Account Request [" + request + "]");
        subscriberModel = subscriberService.test(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Subscriber Account Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }

    @LogFootprint
    @RequestMapping(value = Defines.ContextPaths.SUBSCRIBER_MAIN_PRODUCT + Defines.WEB_ACTIONS.GET, method = RequestMethod.POST)
    public BaseResponse<List<GetMainProductResponse>> getSubscriberMainProducts(HttpServletRequest req,
                                                                                @RequestBody SubscriberRequest request) throws AuthenticationException, GatewayException {
        List<GetMainProductResponse> products = null;
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setRequestId(UUID.randomUUID().toString());
        request.setUsername(username);
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Main Product Request [" + request + "]");
        products = subscriberService.getMainProducts(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Main Product Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                products);
    }
}
