package com.asset.ccat.gateway.controllers;

import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.LoginRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.LoginWrapperModel;
import com.asset.ccat.gateway.services.UserService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author Mahmoud Shehab
 */
@RestController
@CrossOrigin(origins = "*")
public class LoginController {

    @Autowired
    UserService userService;


    @LogFootprint
    @PostMapping(value = Defines.WEB_ACTIONS.LOGIN)
    public BaseResponse<LoginWrapperModel> userLogin(HttpServletRequest req, @RequestBody LoginRequest loginRequest) throws AuthenticationException, Exception {
        loginRequest.setRequestId(UUID.randomUUID().toString());
        ThreadContext.put("requestId", loginRequest.getRequestId());
        CCATLogger.DEBUG_LOGGER.debug("Login Request Started with username={}", loginRequest.getUsername());
        LoginWrapperModel loginResponse = userService.userLogin(loginRequest, req.getServerName());
        prepareRequestForFootprintLogging(loginRequest, loginResponse);
        CCATLogger.DEBUG_LOGGER.debug("Login Request Ended.");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                loginRequest.getRequestId(),
                loginResponse);
    }

    private void prepareRequestForFootprintLogging(LoginRequest loginRequest, LoginWrapperModel loginResponse){
        loginRequest.setToken(loginResponse.getToken());
        loginRequest.setSessionId(loginResponse.getSessionId());
        if(loginRequest.getFootprintModel() != null)
            loginRequest.getFootprintModel().setProfileName(loginResponse.getUserProfile().getProfileName());

    }
}
