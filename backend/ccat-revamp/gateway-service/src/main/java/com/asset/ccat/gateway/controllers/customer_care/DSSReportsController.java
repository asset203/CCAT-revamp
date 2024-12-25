package com.asset.ccat.gateway.controllers.customer_care;

import com.asset.ccat.gateway.annotation.SubscriberOwnership;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.customer_care.DSSReportModel;
import com.asset.ccat.gateway.models.requests.customer_care.history.DSSReportRequest;
import com.asset.ccat.gateway.models.requests.report.*;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.DSSReportsService;
import com.asset.ccat.gateway.validators.ReportsValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author wael.mohamed
 */
@RestController
@CrossOrigin(value = "*")
@RequestMapping(value = Defines.ContextPaths.DSS_REPORT)
public class DSSReportsController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private DSSReportsService dSSReportsService;
    @Autowired
    private ReportsValidator reportsValidator;

    @SubscriberOwnership
    @PostMapping(value = Defines.ContextPaths.TRAFFIC_BEHAVIOR + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<DSSReportModel> getTrafficBehaviorReport(@RequestBody DSSReportRequest request) throws GatewayException, JsonProcessingException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Traffic Behaviour Report Request [" + request + "]");

        DSSReportModel response = dSSReportsService.getTrafficBehaviorReport(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Traffic Behaviour Report Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                response);
    }

    @SubscriberOwnership
    @PostMapping(value = Defines.ContextPaths.BTIVR_REPORT + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<DSSReportModel> getBtiVrReport(@RequestBody DSSReportRequest request) throws GatewayException, JsonProcessingException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get BtiVr Report Request [" + request + "]");
        DSSReportModel response = dSSReportsService.getBtiVrReport(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get BtiVr Report Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                response);
    }

    @SubscriberOwnership
    @PostMapping(value = Defines.ContextPaths.USSD_REPORT + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<DSSReportModel> getUSSDReport(@RequestBody DSSReportRequest request) throws GatewayException, JsonProcessingException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get USSD Report Request [" + request + "]");
        DSSReportModel response = dSSReportsService.getUSSDReport(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get USSD Report Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                response);
    }
    @SubscriberOwnership
    @PostMapping(value = Defines.ContextPaths.VODAFONE_ONE_REDEEM + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<DSSReportModel> getVodafoneOneRedeemReport(@RequestBody DSSReportRequest request) throws GatewayException, JsonProcessingException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Vodafone One Report Request [" + request + "]");
        DSSReportModel response = dSSReportsService.getVodafoneOneRedeemReport(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Vodafone One Report Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                response);
    }

    @SubscriberOwnership
    @PostMapping(value = Defines.ContextPaths.VODAFONE_ONE_PROFILE + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<DSSReportModel> getVodafoneOneProfileReport(@RequestBody DSSReportRequest request) throws GatewayException, JsonProcessingException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Vodafone One Report Request [" + request + "]");
        DSSReportModel response = dSSReportsService.getVodafoneOneProfileReport(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Vodafone One Report Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                response);
    }

    @SubscriberOwnership
    @PostMapping(value = Defines.ContextPaths.VISITED_URLS + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<DSSReportModel> getVisitedUrlsReport(@RequestBody DSSReportRequest request) throws GatewayException, JsonProcessingException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Visited Urls Report Request [" + request + "]");

        DSSReportModel response = dSSReportsService.getVisitedUrlReport(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Visited Urls Report Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                response);

    }

    @SubscriberOwnership
    @PostMapping(value = Defines.ContextPaths.CONTRACT_BALANCE + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<DSSReportModel> getContractBalanceReport(@RequestBody DSSReportRequest request) throws GatewayException, JsonProcessingException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Vodafone One Report Request [" + request + "]");
        DSSReportModel response = dSSReportsService.getContractBalanceReport(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Vodafone One Report Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                response);
    }


    @SubscriberOwnership
    @PostMapping(value = Defines.ContextPaths.OUTGOING_VIEW + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<DSSReportModel> getOutgoingViewReport(@RequestBody GetOutgoingViewReportRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Outgoing View Report Request [" + request + "]");
        this.reportsValidator.getAllGeneralReportValidator(request);
        this.reportsValidator.getOutGoingViewReportValidator(request);
        DSSReportModel response = dSSReportsService.getGeneralReportData(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Outgoing View Report Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                response);
    }

    @SubscriberOwnership
    @PostMapping(value = Defines.ContextPaths.CONTRACT_BALANCE_TRANSFER + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<DSSReportModel> getContractBalanceTransferReport(@RequestBody DSSReportRequest request) throws GatewayException, JsonProcessingException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Vodafone One Report Request [" + request + "]");
        DSSReportModel response = dSSReportsService.getContractBalanceTransferReport(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Vodafone One Report Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                response);
    }

    @SubscriberOwnership
    @PostMapping(value = Defines.ContextPaths.COMPLAINT_VIEW + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<DSSReportModel> getComplaintViewReport(@RequestBody DSSReportRequest request) throws GatewayException, JsonProcessingException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Complaint View Report Request [" + request + "]");
        //this.reportsValidator.getComplaintViewReportValidator(request);
        DSSReportModel response = dSSReportsService.getComplaintViewReport(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Complaint View Report Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                response);
    }

    @SubscriberOwnership
    @PostMapping(value = Defines.ContextPaths.ETOPUP_TRANSACTIONS + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<DSSReportModel> getEtopupTransactionsReport(@RequestBody GetEtopupTransactionsReportRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Etopup Transactions Report Request [" + request + "]");
        this.reportsValidator.getAllGeneralReportValidator(request);
        DSSReportModel response = dSSReportsService.getGeneralReportData(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Etopup Transactions Report Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                response);
    }

    @SubscriberOwnership
    @PostMapping(value = Defines.ContextPaths.CONTRACT_BILL + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<DSSReportModel> getContractBillReport(@RequestBody GetContractBillReportRequest request) throws GatewayException, JsonProcessingException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Contract Bill Report Request [" + request + "]");
       // this.reportsValidator.getContractBillReportValidator(request);
        DSSReportModel response = dSSReportsService.getContractBillReport(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Contract Bill Report Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                response);
    }

}