package com.asset.ccat.gateway.controllers.admins;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.account_groups_bits_desc.GetAllAccountGroupsBitsDescRequest;
import com.asset.ccat.gateway.models.requests.admin.account_groups_bits_desc.UpdateAccountGroupsBitsDescRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.account_groups_bits_desc.GetAllAccountGroupsBitsDescResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.admin.AccountGroupsBitsDescService;
import com.asset.ccat.gateway.validators.admins.AccountGroupsBitsDescValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = Defines.ContextPaths.ACCOUNT_GROUPS_BITS_DESCRIPTION)
public class AccountGroupsBitsDescController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AccountGroupsBitsDescValidator accountGroupsBitsDescValidator;
    @Autowired
    private AccountGroupsBitsDescService accountGroupsBitsDescService;


    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllAccountGroupsBitsDescResponse> getAllAccountGroupsBitsDesc
            (@RequestBody GetAllAccountGroupsBitsDescRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All Account Groups Bits Desc Request [" + request + "]");
        GetAllAccountGroupsBitsDescResponse response =
                accountGroupsBitsDescService.getAllAccountGroupsBitsDesc(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All Account Groups Bits Desc Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                response);
    }


    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public ResponseEntity<BaseResponse> updateAccountGroupsBitsDesc(
            @RequestBody UpdateAccountGroupsBitsDescRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Account Groups Bits Desc Request [" + request + "]");
        accountGroupsBitsDescValidator.validateAccountGroupsBitsDesc(request);
        accountGroupsBitsDescService.updateAccountGroupsBitsDesc(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Account Groups Bits Desc Request Successfully!!");

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null), HttpStatus.OK);

    }
}
