package com.asset.ccat.gateway.controllers.customer_care;

import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.customer_care.history.ExportSubscriberActivities;
import com.asset.ccat.gateway.models.requests.customer_care.history.ExportSubscriberActivityDetails;
import com.asset.ccat.gateway.models.requests.customer_care.history.GetSubscriberActivitiesRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.customer_care.history.GetSubscriberActivitiesResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.AccountHistoryService;
import com.asset.ccat.gateway.validators.customer_care.AccountHistoryValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

/**
 * @author wael.mohamed
 */
@RestController
@CrossOrigin(value = "*")
@RequestMapping(value = Defines.ContextPaths.ACCOUNT_HISTORY)
public class AccountHistoryController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AccountHistoryService historyService;
    @Autowired
    private AccountHistoryValidator accountHistoryValidator;

    @PostMapping(value = Defines.WEB_ACTIONS.SEARCH)
    public BaseResponse<GetSubscriberActivitiesResponse> getSubscriberActivities(@RequestBody GetSubscriberActivitiesRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Subscriber Activities Request [" + request + "]");
        accountHistoryValidator.validateGetSubscriberActivities(request);
        GetSubscriberActivitiesResponse response = historyService.getSubscriberActivities(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Subscriber Activities Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                response);
    }


    @LogFootprint
    @RequestMapping(value = Defines.WEB_ACTIONS.EXPORT + Defines.ContextPaths.SUBSCRIBER_ACTIVITIES, method = RequestMethod.POST)
    public ResponseEntity<Resource> exportSubscriberActivities(@RequestBody ExportSubscriberActivities request) throws GatewayException {
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
        CCATLogger.DEBUG_LOGGER.info("Received Get Subscriber Activities Request [" + request + "]");
        byte[] fileAsBytes = historyService.exportSubscriberActivities(request);
        ByteArrayResource resource = new ByteArrayResource(fileAsBytes);
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=AccountHistory.csv");
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Export Subscriber Activities Request Successfully!!");

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(fileAsBytes.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);

    }


    @LogFootprint
    @RequestMapping(value = Defines.WEB_ACTIONS.EXPORT + Defines.ContextPaths.SUBSCRIBER_ACTIVITY_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<Resource> exportSubscriberActivityDetails(@RequestBody ExportSubscriberActivityDetails request) throws GatewayException {
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
        CCATLogger.DEBUG_LOGGER.info("Received Get Subscriber Activity Details Request [" + request + "]");
        byte[] fileContent = historyService.exportSubscriberActivityDetails(request);
        ByteArrayResource resource = new ByteArrayResource(fileContent);
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=AccountHistoryDetails.csv");
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Export Subscriber Activity Details Request Successfully!!");

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(fileContent.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);

    }

//    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
//    public BaseResponse<GetAllSubscriberActivityResponse> getAllSubscriberActivity(@RequestBody GetAllSubscriberActivityRequest request) throws GatewayException {
//        CCATLogger.DEBUG_LOGGER.info("Recieved getAllSubscriberActivities request");
//        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
//        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
//        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
//        request.setUsername(username);
//        request.setRequestId(UUID.randomUUID().toString());
//        request.setSessionId(sessionId);
//        ThreadContext.put("sessionId", sessionId);
//        ThreadContext.put("requestId", request.getRequestId());
//        accountHistoryValidator.validateGetAllSubscriberActivities(request);
//        GetAllSubscriberActivityResponse response = historyService.getAllSubscriberActivity(request);
//        CCATLogger.DEBUG_LOGGER.info("Finished getAllSubscriberActivities request successfully");
//        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, request.getRequestId(), response);
//    }
}
