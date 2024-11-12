package com.asset.ccat.air_service.controllers;

import com.asset.ccat.air_service.defines.Defines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.requests.UpdateBalanceAndDateRequest;
import com.asset.ccat.air_service.models.responses.BaseResponse;
import com.asset.ccat.air_service.models.shared.ServiceInfo;
import com.asset.ccat.air_service.services.UpdateBalanceAndDateService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

/**
 * @author Mahmoud Shehab
 */
@RestController
@RequestMapping(value = Defines.ContextPaths.BALANCE_AND_DATE)
public class UpdateBalanceAndDatesController {

    @Autowired
    Environment environment;

    @Autowired
    UpdateBalanceAndDateService updateBalanceAndDateService;

    @RequestMapping(value = Defines.WEB_ACTIONS.UPDATE, method = RequestMethod.POST)
    public BaseResponse updateBalanceAndDates(HttpServletRequest req,
                                              @RequestBody UpdateBalanceAndDateRequest request) throws AuthenticationException, AIRServiceException, AIRException, Exception {
        CCATLogger.DEBUG_LOGGER.info("Received Update Balance And Dates Request [" + request + "]");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        updateBalanceAndDateService.updateBalanceAndDate(request);
        CCATLogger.DEBUG_LOGGER.info("IP => " + InetAddress.getLocalHost().getHostAddress() + environment.getProperty("server.port"));
        CCATLogger.DEBUG_LOGGER.info("Finished Update Balance And Dates Request Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                new ServiceInfo(InetAddress.getLocalHost().getHostAddress(), environment.getProperty("server.port")),
                null);
    }
}
