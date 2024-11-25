/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.controllers.admins;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.disconnection_code.CreateDisconnectionCodeRequest;
import com.asset.ccat.gateway.models.requests.admin.disconnection_code.DeleteDisconnectionCodeRequest;
import com.asset.ccat.gateway.models.requests.admin.disconnection_code.GetAllDisconnectionCodesRequest;
import com.asset.ccat.gateway.models.requests.admin.disconnection_code.UpdateDisconnectionCodeRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.disconnection_codes.GetAllDisconnectionCodesResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.admin.DisconnectionCodeService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.UUID;

/**
 *
 * @author Mahmoud Shehab
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = Defines.ContextPaths.DISCONNECTION_CODE)
public class DisconnectionCodeController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private DisconnectionCodeService codeService;


    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllDisconnectionCodesResponse> getAllDisconnectionCodes(HttpServletRequest req,
            @RequestBody GetAllDisconnectionCodesRequest disconnectionCodesRequest) throws AuthenticationException, GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started Getting disconnection codes");
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(disconnectionCodesRequest.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        CCATLogger.DEBUG_LOGGER.debug("sessionId= " + sessionId);
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("username= " + username);
        disconnectionCodesRequest.setUsername(username);
        disconnectionCodesRequest.setRequestId(UUID.randomUUID().toString());
        disconnectionCodesRequest.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", disconnectionCodesRequest.getRequestId());

        GetAllDisconnectionCodesResponse allDisconnectionCodesResponse = codeService.getAllDisconnectionCodes(disconnectionCodesRequest);

        CCATLogger.DEBUG_LOGGER.info(" All disconnection codes are Retrived Succssfully");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                disconnectionCodesRequest.getRequestId(),
                allDisconnectionCodesResponse);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public ResponseEntity<BaseResponse> createDisconnectionCode(HttpServletRequest req,
            @RequestBody CreateDisconnectionCodeRequest createDisconnectionCodeRequest) throws AuthenticationException, GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started add diconnection");
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(createDisconnectionCodeRequest.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        CCATLogger.DEBUG_LOGGER.debug("sessionId= " + sessionId);
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("username= " + username);
        createDisconnectionCodeRequest.setUsername(username);
        createDisconnectionCodeRequest.setRequestId(UUID.randomUUID().toString());
        createDisconnectionCodeRequest.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", createDisconnectionCodeRequest.getRequestId());
        codeService.createDisconnectionCode(createDisconnectionCodeRequest);
        CCATLogger.DEBUG_LOGGER.info("diconnection added Succssfully");
        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0, createDisconnectionCodeRequest.getRequestId(),
                null), HttpStatus.OK);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public ResponseEntity<BaseResponse> updateDisconnectionCode(HttpServletRequest req,
            @RequestBody UpdateDisconnectionCodeRequest updateDisconnectionCodeRequest) throws AuthenticationException, GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started add diconnection");
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(updateDisconnectionCodeRequest.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        CCATLogger.DEBUG_LOGGER.debug("sessionId= " + sessionId);
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("username= " + username);
        updateDisconnectionCodeRequest.setUsername(username);
        updateDisconnectionCodeRequest.setRequestId(UUID.randomUUID().toString());
        updateDisconnectionCodeRequest.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", updateDisconnectionCodeRequest.getRequestId());
        codeService.updateDisconnectionCode(updateDisconnectionCodeRequest);
        CCATLogger.DEBUG_LOGGER.info("diconnection added Succssfully");
        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0, updateDisconnectionCodeRequest.getRequestId(),
                null), HttpStatus.OK);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public ResponseEntity<BaseResponse> deleteDisconnectionCode(HttpServletRequest req,
            @RequestBody DeleteDisconnectionCodeRequest deleteDisconnectionCodeRequest) throws AuthenticationException, GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started add diconnection");
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(deleteDisconnectionCodeRequest.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        CCATLogger.DEBUG_LOGGER.debug("sessionId= " + sessionId);
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("username= " + username);
        deleteDisconnectionCodeRequest.setUsername(username);
        deleteDisconnectionCodeRequest.setRequestId(UUID.randomUUID().toString());
        deleteDisconnectionCodeRequest.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", deleteDisconnectionCodeRequest.getRequestId());
        codeService.deleteDisconnectionCode(deleteDisconnectionCodeRequest);
        CCATLogger.DEBUG_LOGGER.info("diconnection added Succssfully");
        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0, deleteDisconnectionCodeRequest.getRequestId(),
                null), HttpStatus.OK);
    }

}
