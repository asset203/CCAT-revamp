package com.asset.ccat.gateway.controllers.customer_care;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.customer_care.account_group.GetAccountGroupsRequest;
import com.asset.ccat.gateway.models.requests.customer_care.account_group.UpdateAccountGroupRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.customer_care.GetAllAccountGroupsResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.AccountGroupsService;
import com.asset.ccat.gateway.validators.customer_care.AccountsGroupValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = Defines.ContextPaths.ACCOUNT_GROUPS)
public class AccountGroupsController {

    private final JwtTokenUtil jwtTokenUtil;
    private final AccountGroupsService accountGroupsService;
    private final AccountsGroupValidator accountsGroupValidator;

    public AccountGroupsController(JwtTokenUtil jwtTokenUtil, AccountGroupsService accountGroupsService, AccountsGroupValidator accountsGroupValidator) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.accountGroupsService = accountGroupsService;
        this.accountsGroupValidator = accountsGroupValidator;
    }

    @PostMapping(Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllAccountGroupsResponse> getAllAccountGroups(@RequestBody GetAccountGroupsRequest request) throws GatewayException {
        HashMap<String, Object> tokenData = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokenData.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokenData.get(Defines.SecurityKeywords.USERNAME).toString();
        Integer userId = Integer.parseInt(tokenData.get(Defines.SecurityKeywords.USER_ID).toString());
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setSessionId(sessionId);
        request.setRequestId(UUID.randomUUID().toString());
        request.setUserId(userId);
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All Account Groups Request [" + request + "]");
        accountsGroupValidator.validateGetAccountGroupsRequest(request);
        GetAllAccountGroupsResponse payload = accountGroupsService.getAllAccountGroups(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All Account Groups Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                payload);
    }

    @PostMapping(Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateAccountGroup(@RequestBody UpdateAccountGroupRequest request) throws GatewayException {
        HashMap<String, Object> tokenData = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokenData.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokenData.get(Defines.SecurityKeywords.USERNAME).toString();
        Integer userId = Integer.parseInt(tokenData.get(Defines.SecurityKeywords.USER_ID).toString());
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setSessionId(sessionId);
        request.setRequestId(UUID.randomUUID().toString());
        request.setUserId(userId);
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Account Group Request [" + request + "]");
        accountsGroupValidator.validateUpdateAccountGroupRequest(request);
        accountGroupsService.updateAccountGroup(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Account Group Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }
}
