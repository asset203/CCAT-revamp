package com.asset.ccat.balancedisputemapperservice.controllers;

import com.asset.ccat.balancedisputemapperservice.defines.Defines;
import com.asset.ccat.balancedisputemapperservice.defines.Defines.ContextPaths;
import com.asset.ccat.balancedisputemapperservice.defines.Defines.WEB_ACTIONS;
import com.asset.ccat.balancedisputemapperservice.defines.ErrorCodes;
import com.asset.ccat.balancedisputemapperservice.loggers.CCATLogger;
import com.asset.ccat.balancedisputemapperservice.models.requests.BalanceDisputeServiceRequest;
import com.asset.ccat.balancedisputemapperservice.models.requests.MapTodayDataUsageRequest;
import com.asset.ccat.balancedisputemapperservice.models.response.BalanceDisputeReportResponse;
import com.asset.ccat.balancedisputemapperservice.models.response.BaseResponse;
import com.asset.ccat.balancedisputemapperservice.models.response.BdGetTodayUsageMapperResponse;
import com.asset.ccat.balancedisputemapperservice.services.BalanceDisputeMapperService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Assem.Hassan
 */
@RestController
@RequestMapping(value = Defines.ContextPaths.BALANCE_DISPUTE_REPORT)
public class BalanceDisputeMapperController {

  private final BalanceDisputeMapperService balanceDisputeMapperService;

  @Autowired
  public BalanceDisputeMapperController(BalanceDisputeMapperService balanceDisputeMapperService) {
    this.balanceDisputeMapperService = balanceDisputeMapperService;
  }

  @PostMapping(value = WEB_ACTIONS.MAP)
  public BaseResponse<BalanceDisputeReportResponse> getBalanceDisputeReport(
      @RequestBody BalanceDisputeServiceRequest request)
      throws Exception {
    CCATLogger.DEBUG_LOGGER.debug(
        "BalanceDisputeController -> getBalanceDisputeMap() Request : { " + request + " }");
    ThreadContext.put("requestId", request.getRequestId());
    ThreadContext.put("sessionId", request.getSessionId());
    BalanceDisputeReportResponse response = balanceDisputeMapperService.getBalanceDisputeReport(
        request);

    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, response);
  }

  @PostMapping(value = WEB_ACTIONS.MAP + ContextPaths.TODAY_DATA_USAGE)
  public BaseResponse<BdGetTodayUsageMapperResponse> mapTodayDataUsage(
      @RequestBody MapTodayDataUsageRequest request)
      throws Exception {
    CCATLogger.DEBUG_LOGGER.debug(
        "BalanceDisputeController -> getBalanceDisputeMap() Request : { " + request + " }");
    ThreadContext.put("requestId", request.getRequestId());
    ThreadContext.put("sessionId", request.getSessionId());
    BdGetTodayUsageMapperResponse response = balanceDisputeMapperService.getTodayDataUsage(
        request);

    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, response);
  }
}


