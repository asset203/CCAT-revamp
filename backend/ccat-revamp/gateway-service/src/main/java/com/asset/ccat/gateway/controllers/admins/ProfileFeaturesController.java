package com.asset.ccat.gateway.controllers.admins;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.profile_features.GetProfileUsersRequest;
import com.asset.ccat.gateway.models.requests.admin.profile_features.GetProfilesFeaturesRequest;
import com.asset.ccat.gateway.models.requests.admin.user.ExtractUsersProfilesRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.profile_features.GetProfileUsersResponse;
import com.asset.ccat.gateway.models.responses.admin.profile_features.GetProfilesFeaturesResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.admin.ProfileFeaturesService;
import com.asset.ccat.gateway.validators.admins.ProfileValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author mohamed.metwaly
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = Defines.ContextPaths.EXTRACT_PROFILE_FEATURES)
public class ProfileFeaturesController {
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    ProfileFeaturesService profileFeaturesService;
    @Autowired
    private ProfileValidator profileValidator;


    @PostMapping(value = Defines.ContextPaths.USERS + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetProfileUsersResponse> getAllUserProfile(@RequestBody GetProfileUsersRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Profile Users Request [" + request + "]");
        profileValidator.getAllUserProfile(request);
        GetProfileUsersResponse payload = profileFeaturesService.getProfileUsers(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Profile Users Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                payload);
    }

    @PostMapping(Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetProfilesFeaturesResponse> getAllProfilesFeatures(@RequestBody GetProfilesFeaturesRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Profiles Features Request [" + request + "]");
        GetProfilesFeaturesResponse payload = profileFeaturesService.getProfilesFeatures(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Profiles Features Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                payload);
    }

    @RequestMapping(value = Defines.ContextPaths.USERS_PROFILES + Defines.WEB_ACTIONS.EXPORT, method = RequestMethod.POST)
    @CrossOrigin(origins = "*")
    public ResponseEntity<Resource> exportUsersProfileReport(@RequestBody ExtractUsersProfilesRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Extract Users Profiles Request Request [" + request + "]");
        ResponseEntity<Resource> res = profileFeaturesService.exportUsersProfilesReport(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Extract Users Profiles Request Request Successfully!!");

        return res;
    }
}
