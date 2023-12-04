package com.asset.ccat.gateway.controllers.admins;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.account_groups.AddAccountGroupRequest;
import com.asset.ccat.gateway.models.requests.admin.account_groups.DeleteAccountGroupRequest;
import com.asset.ccat.gateway.models.requests.admin.account_groups.GetAllAccountGroupsRequest;
import com.asset.ccat.gateway.models.requests.admin.account_groups.UpdateAccountGroupRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.account_groups.GetAllAccountGroupsResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.admin.AdminAccountGroupsService;
import com.asset.ccat.gateway.validators.admins.AdminAccountGroupsValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author mohamed.metwaly
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = Defines.ContextPaths.ACCOUNT_GROUPS_ADMIN)
public class AdminAccountGroupsController {
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    AdminAccountGroupsService adminAccountGroupsService;
    @Autowired
    AdminAccountGroupsValidator adminAccountGroupsValidator;


    @PostMapping(Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllAccountGroupsResponse> getAllAccountGroups(@RequestBody GetAllAccountGroupsRequest request) throws GatewayException {

        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All Account Groups Request [" + request + "]");
        GetAllAccountGroupsResponse payload = this.adminAccountGroupsService.getAllAccountGroups(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All Account Groups Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                payload);
    }

    @PostMapping(Defines.WEB_ACTIONS.ADD)
    public BaseResponse addAccountGroup(@RequestBody AddAccountGroupRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Add Account Groups Request [" + request + "]");
        this.adminAccountGroupsValidator.addAccountGroupValidator(request);
        this.adminAccountGroupsService.addAccountGroup(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Add Account Groups Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }

    @PostMapping(Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateAccountGroup(@RequestBody UpdateAccountGroupRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Account Groups Request [" + request + "]");
        this.adminAccountGroupsValidator.updateAccountGroupValidator(request);
        this.adminAccountGroupsService.updateAccountGroup(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Account Groups Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }

    @PostMapping(Defines.WEB_ACTIONS.DELETE)
    public BaseResponse deleteAccountGroup(@RequestBody DeleteAccountGroupRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Delete Account Groups Request [" + request + "]");
        this.adminAccountGroupsValidator.deleteAccountGroupValidator(request);
        this.adminAccountGroupsService.deleteAccountGroup(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Delete Account Groups Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }
}
