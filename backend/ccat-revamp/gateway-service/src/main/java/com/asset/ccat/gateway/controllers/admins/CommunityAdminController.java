package com.asset.ccat.gateway.controllers.admins;

import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.community_admin.AddCommunityAdminRequest;
import com.asset.ccat.gateway.models.requests.admin.community_admin.DeleteCommunityAdminRequest;
import com.asset.ccat.gateway.models.requests.admin.community_admin.GetAllCommunitiesRequest;
import com.asset.ccat.gateway.models.requests.admin.community_admin.UpdateCommunityAdminRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.community_admin.GetAllCommunitiesAdminResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.admin.CommunityAdminService;
import com.asset.ccat.gateway.validators.admins.CommunityAdminValidator;
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
@RequestMapping(path = Defines.ContextPaths.COMMUNITY_ADMIN)
public class CommunityAdminController {

    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    CommunityAdminService communityAdminService;
    @Autowired
    CommunityAdminValidator communityAdminValidator;


    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllCommunitiesAdminResponse> getAllCommunitiesAdmin
            (@RequestBody GetAllCommunitiesRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All Communities Request [" + request + "]");
        GetAllCommunitiesAdminResponse response =
                new GetAllCommunitiesAdminResponse(communityAdminService.getAllCommunitiesAdmin(request));
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All Communities Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                response);
    }


    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    @LogFootprint
    public BaseResponse addCommunityAdmin(@RequestBody AddCommunityAdminRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Add Community Admin Request [" + request + "]");
        this.communityAdminValidator.addCommunityAdminValidator(request);
        this.communityAdminService.addCommunityAdmin(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Add Community Admin Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    @LogFootprint
    public BaseResponse updateCommunityAdmin(@RequestBody UpdateCommunityAdminRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Community Admin Request [" + request + "]");
        this.communityAdminValidator.updateCommunityAdminValidator(request);
        this.communityAdminService.updateCommunityAdmin(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Community Admin Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    @LogFootprint
    public BaseResponse deleteCommunityAdmin(@RequestBody DeleteCommunityAdminRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Delete Community Admin Request [" + request + "]");
        this.communityAdminValidator.deleteCommunityAdminValidator(request);
        this.communityAdminService.deleteCommunityAdmin(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Delete Community Admin Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }
}


