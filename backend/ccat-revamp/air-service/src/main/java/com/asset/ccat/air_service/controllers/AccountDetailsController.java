package com.asset.ccat.air_service.controllers;

import com.asset.ccat.air_service.annotation.LogExecutionTime;
import com.asset.ccat.air_service.defines.Defines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.SubscriberAccountModel;
import com.asset.ccat.air_service.models.requests.SubscriberRequest;
import com.asset.ccat.air_service.models.responses.BaseResponse;
import com.asset.ccat.air_service.models.shared.ServiceInfo;
import com.asset.ccat.air_service.services.GetAccountDetailsService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.net.InetAddress;

/**
 * @author Mahmoud Shehab
 */
@RestController
public class AccountDetailsController {

    @Autowired
    Environment environment;

    @Autowired
    GetAccountDetailsService getAccountDetailsService;

    @LogExecutionTime
    @PostMapping(value = Defines.ContextPaths.ACCOUNT_DETAILS + Defines.WEB_ACTIONS.GET)
    public BaseResponse<SubscriberAccountModel> accountDetails(HttpServletRequest req, @RequestBody SubscriberRequest request) throws Exception {
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Subscriber Request [{}]", request);
        SubscriberAccountModel response = getAccountDetailsService.getAccountDetails(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Subscriber request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                new ServiceInfo(InetAddress.getLocalHost().getHostAddress(), environment.getProperty("server.port")),
                response);
    }
}
