package com.asset.ccat.gateway.controllers.customer_care;

import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.annotation.SubscriberOwnership;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayFilesException;
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

    @SubscriberOwnership
    @LogFootprint
    @PostMapping(value = Defines.WEB_ACTIONS.SEARCH)
    public BaseResponse<GetSubscriberActivitiesResponse> getSubscriberActivities(@RequestBody GetSubscriberActivitiesRequest request) throws GatewayException {
        HashMap<String, Object> tokenData = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokenData.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokenData.get(Defines.SecurityKeywords.USERNAME).toString();
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
    @PostMapping(value = Defines.WEB_ACTIONS.EXPORT + Defines.ContextPaths.SUBSCRIBER_ACTIVITIES)
    public ResponseEntity<Resource> exportSubscriberActivities(@RequestBody ExportSubscriberActivities request) throws GatewayException, GatewayFilesException {
        HashMap<String, Object> tokenData = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokenData.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokenData.get(Defines.SecurityKeywords.USERNAME).toString();
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("msisdn", request.getMsisdn());
        CCATLogger.DEBUG_LOGGER.info("Received Get Subscriber Activities Request [{}]", request);
        byte[] fileAsBytes = historyService.exportSubscriberActivities(request);
        ByteArrayResource resource = new ByteArrayResource(fileAsBytes);
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=AccountHistory.csv");
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Export Subscriber Activities Request Successfully!!");
        ThreadContext.remove("msisdn");

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(fileAsBytes.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);

    }


    @LogFootprint
    @PostMapping(value = Defines.WEB_ACTIONS.EXPORT + Defines.ContextPaths.SUBSCRIBER_ACTIVITY_DETAILS)
    public ResponseEntity<Resource> exportSubscriberActivityDetails(@RequestBody ExportSubscriberActivityDetails request) throws GatewayException, GatewayFilesException {
        HashMap<String, Object> tokenData = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokenData.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokenData.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("msisdn", request.getMsisdn());
        CCATLogger.DEBUG_LOGGER.info("Received Get Subscriber Activity Details Request [" + request + "]");
        byte[] fileContent = historyService.exportSubscriberActivityDetails(request);
        ByteArrayResource resource = new ByteArrayResource(fileContent);
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=AccountHistoryDetails.csv");
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Export Subscriber Activity Details Request Successfully!!");
        ThreadContext.remove("msisdn");
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(fileContent.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);

    }
}
