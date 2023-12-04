/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.controllers.admin;

import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.BaseResponse;
import com.asset.ccat.lookup_service.models.DisconnectionCodeModel;
import com.asset.ccat.lookup_service.models.requests.disconnection_code.CreateDisconnectionCodeRequest;
import com.asset.ccat.lookup_service.models.requests.disconnection_code.DeleteDisconnectionCodeRequest;
import com.asset.ccat.lookup_service.models.requests.disconnection_code.GetAllDisconnectionCodesRequest;
import com.asset.ccat.lookup_service.models.requests.disconnection_code.UpdateDisconnectionCodeRequest;
import com.asset.ccat.lookup_service.services.DisconnectionCodeService;
import java.util.List;
import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Mahmoud Shehab
 */
@RestController
@RequestMapping(path = Defines.ContextPaths.DISCONNECTION_CODE)
public class DisconnectionCodeController {

    @Autowired
    private DisconnectionCodeService codeService;

    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<List<DisconnectionCodeModel>> getAllDisconnectionCodes(HttpServletRequest req,
            @RequestBody GetAllDisconnectionCodesRequest disconnectionCodesRequest) throws AuthenticationException, LookupException {
        CCATLogger.DEBUG_LOGGER.info("Started Getting disconnection codes");
        ThreadContext.put("sessionId", disconnectionCodesRequest.getSessionId());
        ThreadContext.put("requestId", disconnectionCodesRequest.getRequestId());
        List<DisconnectionCodeModel> allDisconnectionCodesResponse = codeService.getAllDisconnectionCodes();
        CCATLogger.DEBUG_LOGGER.info(" All disconnection codes are Retrived Succssfully");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                allDisconnectionCodesResponse);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public ResponseEntity<BaseResponse> createDisconnectionCode(HttpServletRequest req,
            @RequestBody CreateDisconnectionCodeRequest createDisconnectionCodeRequest) throws AuthenticationException, LookupException {
        CCATLogger.DEBUG_LOGGER.info("Started add diconnection");

        ThreadContext.put("sessionId", createDisconnectionCodeRequest.getSessionId());
        ThreadContext.put("requestId", createDisconnectionCodeRequest.getRequestId());
        codeService.createDisconnectionCode(createDisconnectionCodeRequest);
        CCATLogger.DEBUG_LOGGER.info("diconnection added Succssfully");
        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                null), HttpStatus.OK);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public ResponseEntity<BaseResponse> updateDisconnectionCode(HttpServletRequest req,
            @RequestBody UpdateDisconnectionCodeRequest updateDisconnectionCodeRequest) throws AuthenticationException, LookupException {
        CCATLogger.DEBUG_LOGGER.info("Started add diconnection");

        ThreadContext.put("sessionId", updateDisconnectionCodeRequest.getSessionId());
        ThreadContext.put("requestId", updateDisconnectionCodeRequest.getRequestId());
        codeService.updateDisconnectionCode(updateDisconnectionCodeRequest);
        CCATLogger.DEBUG_LOGGER.info("diconnection added Succssfully");
        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                null), HttpStatus.OK);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public ResponseEntity<BaseResponse> deleteDisconnectionCode(HttpServletRequest req,
            @RequestBody DeleteDisconnectionCodeRequest deleteDisconnectionCodeRequest) throws AuthenticationException, LookupException {
        CCATLogger.DEBUG_LOGGER.info("Started add diconnection");

        ThreadContext.put("sessionId", deleteDisconnectionCodeRequest.getSessionId());
        ThreadContext.put("requestId", deleteDisconnectionCodeRequest.getRequestId());
        codeService.deleteDisconnectionCode(deleteDisconnectionCodeRequest);
        CCATLogger.DEBUG_LOGGER.info("diconnection added Succssfully");
        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                null), HttpStatus.OK);
    }

}
