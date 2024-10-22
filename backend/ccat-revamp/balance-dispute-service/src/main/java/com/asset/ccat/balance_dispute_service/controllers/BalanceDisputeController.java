package com.asset.ccat.balance_dispute_service.controllers;

import static com.asset.ccat.balance_dispute_service.defines.Defines.ContextPaths.BALANCE_DISPUTE;

import com.asset.ccat.balance_dispute_service.defines.Defines;
import com.asset.ccat.balance_dispute_service.defines.Defines.ContextPaths;
import com.asset.ccat.balance_dispute_service.defines.Defines.WEB_ACTIONS;
import com.asset.ccat.balance_dispute_service.defines.ErrorCodes;
import com.asset.ccat.balance_dispute_service.dto.requests.GetBalanceDisputeReportRequest;
import com.asset.ccat.balance_dispute_service.dto.requests.SubscriberRequest;
import com.asset.ccat.balance_dispute_service.dto.responses.BalanceDisputeReportResponse;
import com.asset.ccat.balance_dispute_service.dto.responses.BaseResponse;
import com.asset.ccat.balance_dispute_service.exceptions.BalanceDisputeException;
import com.asset.ccat.balance_dispute_service.logger.CCATLogger;
import com.asset.ccat.balance_dispute_service.services.BalanceDisputeService;
import java.text.ParseException;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping(BALANCE_DISPUTE)
public class BalanceDisputeController {

  private final BalanceDisputeService balanceDisputeService;

  public BalanceDisputeController(BalanceDisputeService balanceDisputeService) {
    this.balanceDisputeService = balanceDisputeService;
  }

  @PostMapping(value = Defines.WEB_ACTIONS.GET)
  public BaseResponse<BalanceDisputeReportResponse> getBalanceDisputeMap(
      @RequestBody GetBalanceDisputeReportRequest request)
      throws BalanceDisputeException {
    ThreadContext.put("sessionId", request.getSessionId());
    ThreadContext.put("requestId", request.getRequestId());
    CCATLogger.DEBUG_LOGGER.debug("Get BD request Started with request = {}", request);

    BalanceDisputeReportResponse responseMap = balanceDisputeService.getBalanceDisputeMap(request);
    BaseResponse<BalanceDisputeReportResponse> response = new BaseResponse<>(
        ErrorCodes.SUCCESS.SUCCESS, "success", Defines.SEVERITY.CLEAR, request.getRequestId(),
        responseMap);
    CCATLogger.DEBUG_LOGGER.debug("Get BD request Ended");
    ThreadContext.remove("sessionId");
    ThreadContext.remove("requestId");
    return response;
  }

  @PostMapping(value = (WEB_ACTIONS.GET + ContextPaths.TODAY_DATA_USAGE))
  public ResponseEntity<Resource> getTodayDataUsage(
      @RequestBody SubscriberRequest request)
      throws BalanceDisputeException {

    ThreadContext.put("sessionId", request.getSessionId());
    ThreadContext.put("requestId", request.getRequestId());
    CCATLogger.DEBUG_LOGGER.debug("getTodayDataUsage request Started with body = {}", request);

    ByteArrayResource resource = new ByteArrayResource(
        balanceDisputeService.getTodayDataUsageReport(request));

    CCATLogger.DEBUG_LOGGER.debug("getTodayDataUsage request Ended");
    ThreadContext.remove("sessionId");
    ThreadContext.remove("requestId");
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=" + Defines.BALANCE_DISPUTE.BALANCE_DISPUTE_CSV_FILE_NAME)
        .contentLength(resource.contentLength())
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(resource);
  }

  @PostMapping(value = Defines.WEB_ACTIONS.Export)
  public ResponseEntity<Resource> exportBalanceDisputeReport(
      @RequestBody SubscriberRequest request)
      throws BalanceDisputeException {
    ThreadContext.put("sessionId", request.getSessionId());
    ThreadContext.put("requestId", request.getRequestId());
    CCATLogger.DEBUG_LOGGER.debug("Export Balance Dispute Report request started with body = {}", request);
    ByteArrayResource resource = new ByteArrayResource(
        balanceDisputeService.exportBalanceDisputeExcelReport(request));
    CCATLogger.DEBUG_LOGGER.debug("Export request Ended Successfully");
    ThreadContext.remove("sessionId");
    ThreadContext.remove("requestId");
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=" + Defines.BALANCE_DISPUTE.DB_CALCULATION_FILE_XLSM)
        .contentLength(resource.contentLength())
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(resource);
  }
}
