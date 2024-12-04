package com.asset.ccat.gateway.controllers.admins;


import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.admin.ReasonActivityModel;
import com.asset.ccat.gateway.models.requests.admin.call_activity_admin.*;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.call_activity_admin.GetAllActivityReasonsResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.admin.ReasonActivityService;
import com.asset.ccat.gateway.validators.admins.CallActivityAdminValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = Defines.ContextPaths.CALL_ACTIVITY_ADMIN)
public class CallActivityAdminController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private CallActivityAdminValidator callActivityAdminValidator;
    @Autowired
    private ReasonActivityService reasonActivityService;


    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllActivityReasonsResponse> getAllReasonActivities
            (@RequestBody GetAllActivitiesWithTypeRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All Activities With Type Request [" + request + "]");
        callActivityAdminValidator.validateGetAllActivityReasonsRequest(request);
        List<ReasonActivityModel> list = reasonActivityService.getAllActivityReasons(request);
        GetAllActivityReasonsResponse response = new GetAllActivityReasonsResponse(list);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All Activities With Type Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                response);
    }

    @LogFootprint
    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse addReasonActivity(@RequestBody AddActivityReasonRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        String profileName = tokendata.get(Defines.SecurityKeywords.PROFILE_NAME).toString();
        Optional.ofNullable(request.getFootprintModel()).ifPresent(footprintModel ->
                request.getFootprintModel().setProfileName(profileName));
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Add Activity Reason Request [" + request + "]");
        callActivityAdminValidator.validateAddActivityReasonRequest(request);
        reasonActivityService.addReasonActivity(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Add Activity Reason Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }


    @LogFootprint
    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateReasonActivity(@RequestBody UpdateReasonActivityRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        String profileName = tokendata.get(Defines.SecurityKeywords.PROFILE_NAME).toString();
        Optional.ofNullable(request.getFootprintModel()).ifPresent(footprintModel ->
                request.getFootprintModel().setProfileName(profileName));
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Activity Reason Request [" + request + "]");
        callActivityAdminValidator.validateUpdateActivityReasonRequest(request);
        reasonActivityService.updateReasonActivity(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Activity Reason Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }


    @LogFootprint
    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse deleteReasonActivity(@RequestBody DeleteReasonActivityRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        String profileName = tokendata.get(Defines.SecurityKeywords.PROFILE_NAME).toString();
        Optional.ofNullable(request.getFootprintModel()).ifPresent(footprintModel ->
                request.getFootprintModel().setProfileName(profileName));
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Delete Activity Reason Request [" + request + "]");
        callActivityAdminValidator.validateDeleteActivityReasonRequest(request);
        reasonActivityService.deleteReasonActivity(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Delete Activity Reason Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }


    @LogFootprint
    @PostMapping(value = Defines.WEB_ACTIONS.DOWNLOAD)
    public ResponseEntity<Resource> downloadActivities(@RequestBody DownloadActivitiesRequest request) throws GatewayException, IOException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        String profileName = tokendata.get(Defines.SecurityKeywords.PROFILE_NAME).toString();
        Optional.ofNullable(request.getFootprintModel()).ifPresent(footprintModel ->
                request.getFootprintModel().setProfileName(profileName));
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Download Activities Request [" + request + "]");
        ResponseEntity<Resource> response = reasonActivityService.downloadActivities(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Download Activities Request Successfully!!");

        return response;
    }


    @LogFootprint
    @PostMapping(value = Defines.WEB_ACTIONS.UPLOAD)
    public BaseResponse uploadActivitiesFile(
            @ModelAttribute UploadActivitiesFileRequest request,
            HttpServletResponse response) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        String profileName = tokendata.get(Defines.SecurityKeywords.PROFILE_NAME).toString();
        Optional.ofNullable(request.getFootprintModel()).ifPresent(footprintModel ->
                request.getFootprintModel().setProfileName(profileName));
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Upload Activities File Request [" + request + "]");
        callActivityAdminValidator.validateUploadActivityReasonRequest(request);
        reasonActivityService.uploadActivities(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Upload Activities File Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                null);
    }


//    @PostMapping(value = Defines.WEB_ACTIONS.GET)
//    public BaseResponse<GetReasonActivityResponse> getReasonActivity
//            (@RequestBody GetReasonActivityRequest request) throws GatewayException {
//
//        CCATLogger.DEBUG_LOGGER.debug("Started CallActivityAdminController - getReasonActivity()");
//        HashMap<String, Object> tokenData = jwtTokenUtil.extractDataFromToken(request.getToken());
//        String sessionId = tokenData.get(Defines.SecurityKeywords.SESSION_ID).toString();
//        String username = tokenData.get(Defines.SecurityKeywords.USERNAME).toString();
//        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[ " + sessionId + " ]username=[ " + username + " ]");
//        request.setUsername(username);
//        request.setRequestId(UUID.randomUUID().toString());
//        request.setSessionId(sessionId);
//        ThreadContext.put("sessionId", sessionId);
//        ThreadContext.put("requestId", request.getRequestId());
//        CCATLogger.DEBUG_LOGGER.info("Received get reason activity Request");
//        // Call Validator and service
//        callActivityAdminValidator.isReasonActivityGetValid(request);
//        GetReasonActivityResponse response =
//                new GetReasonActivityResponse(
//                        new ReasonActivityModel(4, "REASON", 3, "first reason"));
//        CCATLogger.DEBUG_LOGGER.info("Get reason activity request finished Successfully");
//        CCATLogger.DEBUG_LOGGER.debug("Ending CallActivityAdminController - getReasonActivity()");
//        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
//                "success",
//                0,
//                request.getRequestId(),
//                response);
//
//    }
}

