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
import com.asset.ccat.user_management.models.requests.marquee.AddMarqueeRequest;
import com.asset.ccat.user_management.models.requests.marquee.DeleteAllMarqueeRequest;
import com.asset.ccat.user_management.models.requests.marquee.DeleteMarqueeRequest;
import com.asset.ccat.user_management.models.requests.marquee.GetMarqueeRequest;
import com.asset.ccat.user_management.models.requests.marquee.UpdateAllMarqueeRequest;
import com.asset.ccat.user_management.models.requests.marquee.UpdateMarqueeRequest;
import com.asset.ccat.user_management.models.responses.BaseResponse;
import com.asset.ccat.user_management.models.responses.marquee.GetAllMarqueeResponse;
import com.asset.ccat.user_management.models.users.MarqueeModel;
import com.asset.ccat.user_management.services.Es2alnyMarqueeService;
import javax.servlet.http.HttpServletRequest;
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
@RequestMapping(value = Defines.ContextPaths.MARQUEES)
public class Es2alnyMarqueeController {

    @Autowired
    private Es2alnyMarqueeService es2alnyMarquee;

    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllMarqueeResponse> getAllMarquees(HttpServletRequest httpReq, @RequestBody GetMarqueeRequest request) throws UserManagementException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());

        CCATLogger.DEBUG_LOGGER.info("Recieved get All Marquees request [" + request + "]");
        GetAllMarqueeResponse response = es2alnyMarquee.getAllMarquees();
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse addMarquee(HttpServletRequest httpReq, @RequestBody AddMarqueeRequest request) throws UserManagementException {
        MarqueeModel marquee = new MarqueeModel(request.getTitle(), request.getDescription());
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());

        CCATLogger.DEBUG_LOGGER.info("Recieved add marquee request [" + request + "]");
        es2alnyMarquee.addMarquee(marquee);
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse deleteMarqueeById(HttpServletRequest httpReq, @RequestBody DeleteMarqueeRequest request) throws UserManagementException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());

        CCATLogger.DEBUG_LOGGER.info("Recieved delete marquee ById request [" + request + "]");
        es2alnyMarquee.deleteMarqueeById(request.getId());
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE_ALL)
    public BaseResponse deleteAllMarquees(HttpServletRequest httpReq, @RequestBody DeleteAllMarqueeRequest request) throws UserManagementException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());

        CCATLogger.DEBUG_LOGGER.info("Recieved delete All Marquees request [" + request + "]");
        es2alnyMarquee.deleteAllMarquees();
        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE_ALL)
    public BaseResponse updateAllMarquees(HttpServletRequest httpReq, @RequestBody UpdateAllMarqueeRequest request) throws UserManagementException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Recieved update All Marquees request [" + request + "]");
        es2alnyMarquee.updateAllMarquees(request.getMarquees());
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, null);
    }
    
    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateMarquee(HttpServletRequest httpReq, @RequestBody UpdateMarqueeRequest request) throws UserManagementException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Recieved update All Marquees request [" + request + "]");
        es2alnyMarquee.updateMarquee(request.getMarquees());
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, null);
    }

}
