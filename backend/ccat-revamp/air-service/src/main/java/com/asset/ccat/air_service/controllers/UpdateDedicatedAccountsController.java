/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.controllers;

import com.asset.ccat.air_service.defines.Defines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.requests.UpdateDedicatedBalanceRequest;
import com.asset.ccat.air_service.models.responses.BaseResponse;
import com.asset.ccat.air_service.models.shared.ServiceInfo;
import com.asset.ccat.air_service.services.UpdateDedicatedAccountsService;
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
import java.net.UnknownHostException;

/**
 * @author Mahmoud Shehab
 */
@RestController
public class UpdateDedicatedAccountsController {

    @Autowired
    Environment environment;

    @Autowired
    UpdateDedicatedAccountsService updateDedAccountsService;

    @RequestMapping(value = Defines.ContextPaths.DEDICATED_ACCOUNTS + Defines.WEB_ACTIONS.UPDATE, method = RequestMethod.POST)
    public BaseResponse updateBalanceAndDates(HttpServletRequest req,
                                              @RequestBody UpdateDedicatedBalanceRequest request) throws AuthenticationException, AIRServiceException, AIRException, UnknownHostException {
        CCATLogger.DEBUG_LOGGER.debug("Received Update Dedicated Balance Request [{}]", request);
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        updateDedAccountsService.updateDedicatedAccounts(request);
        CCATLogger.DEBUG_LOGGER.debug("Finished Update Dedicated Balance Request Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                new ServiceInfo(InetAddress.getLocalHost().getHostAddress(), environment.getProperty("server.port")),
                null);
    }
}
