/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.user_management.controllers;

import com.asset.ccat.user_management.defines.Defines;
import com.asset.ccat.user_management.defines.ErrorCodes;
import com.asset.ccat.user_management.exceptions.UserManagementException;
import com.asset.ccat.user_management.logger.CCATLogger;
import com.asset.ccat.user_management.models.requests.user.CheckLimitRequest;
import com.asset.ccat.user_management.models.requests.user.UpdateLimitRequest;
import com.asset.ccat.user_management.models.responses.BaseResponse;
import com.asset.ccat.user_management.services.UserLimitService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author wael.mohamed
 */
@RestController
@RequestMapping(value = Defines.ContextPaths.LIMITS)
public class UserLimitController {

    @Autowired
    private UserLimitService userLimitService;

    @PostMapping(value = Defines.WEB_ACTIONS.CHECK)
    public BaseResponse checkLimit(HttpServletRequest httpRequest, @RequestBody CheckLimitRequest request) throws UserManagementException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Received check limit request [" + request + "]");
        userLimitService.checkLimit(request);
        CCATLogger.DEBUG_LOGGER.info("Check limit with ID [" + request.getUserId() + "] request finished successfully");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, null);
    }
    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateLimit(HttpServletRequest httpRequest, @RequestBody UpdateLimitRequest request) throws UserManagementException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Received update limit request [" + request + "]");
        userLimitService.updateLimit(request);
        CCATLogger.DEBUG_LOGGER.info("Update limit with ID [" + request.getUserId() + "] request finished successfully");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, null);
    }

}
