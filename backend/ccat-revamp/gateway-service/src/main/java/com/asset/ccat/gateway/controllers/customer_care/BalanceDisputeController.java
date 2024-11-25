package com.asset.ccat.gateway.controllers.customer_care;

import com.asset.ccat.gateway.annotation.SubscriberOwnership;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.Defines.ContextPaths;
import com.asset.ccat.gateway.defines.Defines.WEB_ACTIONS;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.SubscriberRequest;
import com.asset.ccat.gateway.models.requests.customer_care.balance_dispute.GetBalanceDisputeReportRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.customer_care.balance_dispute.BalanceDisputeReportResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.BalanceDisputeService;
import java.util.HashMap;
import java.util.UUID;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(value = "*")
@RequestMapping(value = ContextPaths.BALANCE_DISPUTE)
public class BalanceDisputeController {

  private final JwtTokenUtil jwtTokenUtil;
  private final BalanceDisputeService balanceDisputeService;

  public BalanceDisputeController(JwtTokenUtil jwtTokenUtil,
      BalanceDisputeService balanceDisputeService) {
    this.jwtTokenUtil = jwtTokenUtil;
    this.balanceDisputeService = balanceDisputeService;
  }

  @CrossOrigin(origins = "*")
  @SubscriberOwnership
  @PostMapping(value = WEB_ACTIONS.GET)
  public BaseResponse<BalanceDisputeReportResponse> getBalanceDispute(
      @RequestBody GetBalanceDisputeReportRequest request) throws GatewayException {
    HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
    String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
    String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
    CCATLogger.DEBUG_LOGGER.debug(
        "Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
    request.setUsername(username);
    request.setRequestId(UUID.randomUUID().toString());
    request.setSessionId(sessionId);
    ThreadContext.put("sessionId", sessionId);
    ThreadContext.put("requestId", request.getRequestId());
    CCATLogger.DEBUG_LOGGER.info("Received Get Balance Dispute Request [" + request + "]");

    BalanceDisputeReportResponse response = balanceDisputeService.getBalanceDisputeReport(request);

    CCATLogger.DEBUG_LOGGER.info("Finished Serving Balance Dispute Request Successfully!!");

    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success",
        0,
        request.getRequestId(),
        response);
  }

  @CrossOrigin(origins = "*")
  @PostMapping(value = WEB_ACTIONS.EXPORT)
  public ResponseEntity<Resource> exportBalanceDispute(
      @RequestBody SubscriberRequest request) throws GatewayException {
    HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
    String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
    String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
    CCATLogger.DEBUG_LOGGER.debug(
        "Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
    request.setUsername(username);
    request.setRequestId(UUID.randomUUID().toString());
    request.setSessionId(sessionId);
    ThreadContext.put("sessionId", sessionId);
    ThreadContext.put("requestId", request.getRequestId());
    CCATLogger.DEBUG_LOGGER.info("Received Get Balance Dispute Request [" + request + "]");

    ResponseEntity<Resource> response = balanceDisputeService.exportBalanceDisputeReport(request);

    CCATLogger.DEBUG_LOGGER.info("Finished Serving Balance Dispute Request Successfully!!");

    return response;
  }

  @CrossOrigin(origins = "*")
  @PostMapping(value = (WEB_ACTIONS.GET + ContextPaths.TODAY_DATA_USAGE))
  public ResponseEntity<Resource> getBalanceDisputeTodayDataUsageReport(
      @RequestBody SubscriberRequest request) throws GatewayException {
    HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
    String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
    String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
    request.setUsername(username);
    request.setRequestId(UUID.randomUUID().toString());
    request.setSessionId(sessionId);
    ThreadContext.put("sessionId", sessionId);
    ThreadContext.put("requestId", request.getRequestId());
    CCATLogger.DEBUG_LOGGER.info("Received Get Balance Dispute Today Data Usage Request = {}", request);

    ResponseEntity<Resource> response = balanceDisputeService.getBalanceDisputeTodayDataUsageReport(request);

    CCATLogger.DEBUG_LOGGER.info(
        "Finished Serving Get Balance Dispute Today Data Usage Request Successfully!!");

    return response;
  }
}
