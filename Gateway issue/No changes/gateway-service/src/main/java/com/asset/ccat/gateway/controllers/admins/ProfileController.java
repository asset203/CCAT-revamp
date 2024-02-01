package com.asset.ccat.gateway.controllers.admins;

import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.profile.*;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.profile.GetAllProfilesResponse;
import com.asset.ccat.gateway.models.responses.admin.profile.GetProfileResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.UserProfileService;
import com.asset.ccat.gateway.validators.admins.ProfileValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author nour.ihab
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = Defines.ContextPaths.PROFILE)
public class ProfileController {

    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private ProfileValidator profileValidator;


    @PostMapping(value = Defines.WEB_ACTIONS.GET)
    public BaseResponse<GetProfileResponse> getProfile(HttpServletRequest req,
                                                       @RequestBody GetProfileRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Profile Request [" + request + "]");
        profileValidator.validateGetProfile(request);
        GetProfileResponse getProfileResponse = userProfileService.getPorfile(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Profile Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                getProfileResponse);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllProfilesResponse> getAllProfiles(HttpServletRequest req,
                                                               @RequestBody GetAllProfilesRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All Profiles Request [" + request + "]");
        GetAllProfilesResponse response = userProfileService.getAllProfiles(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All Profiles Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                response);
    }


    @LogFootprint
    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse addProfile(HttpServletRequest req,
                                   @RequestBody AddProfileRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Add Profile Request [" + request + "]");
        profileValidator.validateAddProfile(request);
        userProfileService.addProfile(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Add Profile Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);

    }


    @LogFootprint
    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateProfile(HttpServletRequest req,
                                      @RequestBody UpdateProfileRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Profile Request [" + request + "]");
        profileValidator.validateUpdateProfile(request);
        userProfileService.updateProfile(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Profile Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }


    @LogFootprint
    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse deleteProfile(HttpServletRequest req,
                                      @RequestBody DeleteProfileRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Delete Profile Request [" + request + "]");
        profileValidator.validateDeleteProfile(request);
        userProfileService.deleteProfile(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Delete Profile Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }
}
