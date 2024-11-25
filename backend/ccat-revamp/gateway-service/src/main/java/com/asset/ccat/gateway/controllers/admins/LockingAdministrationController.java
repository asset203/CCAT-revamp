package com.asset.ccat.gateway.controllers.admins;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.locking_admin.GetAllLockingAdministrationsRequest;
import com.asset.ccat.gateway.models.requests.admin.locking_admin.LockingAdministrationRequest;
import com.asset.ccat.gateway.models.requests.admin.locking_admin.UnlockingAdministrationRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.locking_admin.GetAllLockingAdministrationsResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.admin.LockingAdministrationService;
import com.asset.ccat.gateway.validators.admins.LockingAdministrationValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author nour.ihab
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = Defines.ContextPaths.LOCKING_ADMIN)
public class LockingAdministrationController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private LockingAdministrationService lockingAdministrationService;
    @Autowired
    private LockingAdministrationValidator unlockingAdministrationValidator;

    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllLockingAdministrationsResponse> getAllLockedAdministrations(HttpServletRequest req,
                                                                                          @RequestBody GetAllLockingAdministrationsRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All Locking Administrations Request [" + request + "]");
        GetAllLockingAdministrationsResponse response = lockingAdministrationService.getAllLockingAdministrations();
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All Locking Administrations Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse<String> lockAdministration(HttpServletRequest req,
                                           @RequestBody LockingAdministrationRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Locking Administrations Request [{}] By username = {}", request, username);
        unlockingAdministrationValidator.validateLockAdmin(request);
        lockingAdministrationService.LockAdministration(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Locking Administrations Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse<String> unLockAdministration(HttpServletRequest req,
                                             @RequestBody UnlockingAdministrationRequest request) throws GatewayException {
        String requestId = UUID.randomUUID().toString();
        ThreadContext.put("requestId", requestId);
        CCATLogger.DEBUG_LOGGER.info("Received Unlocking Administrations Request [{}]", request);
        unlockingAdministrationValidator.validateUnlockAdmin(request);
        lockingAdministrationService.unLockAdministration(request.getMsisdn());
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Unlocking Administrations Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }
}
