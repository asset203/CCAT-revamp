package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.SubscriberRequest;
import com.asset.ccat.gateway.models.requests.customer_care.balance_dispute.GetBalanceDisputeReportRequest;
import com.asset.ccat.gateway.models.responses.customer_care.balance_dispute.BalanceDisputeReportResponse;
import com.asset.ccat.gateway.proxy.BalanceDisputeProxy;
import com.asset.ccat.gateway.redis.repository.BalanceDisputeReportRepositary;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BalanceDisputeService {

  private final BalanceDisputeProxy balanceDisputeProxy;
  private final BalanceDisputeReportRepositary balanceDisputeReportRepositary;

  public BalanceDisputeService(BalanceDisputeProxy balanceDisputeProxy,
      BalanceDisputeReportRepositary balanceDisputeReportRepositary) {
    this.balanceDisputeProxy = balanceDisputeProxy;
    this.balanceDisputeReportRepositary = balanceDisputeReportRepositary;
  }

  public BalanceDisputeReportResponse getBalanceDisputeReport(
      GetBalanceDisputeReportRequest request)
      throws GatewayException {
    CCATLogger.DEBUG_LOGGER.info("Balance Dispute Service Get Balance Report Started");
    BalanceDisputeReportResponse response = balanceDisputeProxy.getBalanceDisputeReport(request);
    CCATLogger.DEBUG_LOGGER.info("Finished Serving Balance Dispute Request Successfully!!");
    return response;
  }


  public ResponseEntity<Resource> exportBalanceDisputeReport(
      SubscriberRequest request)
      throws GatewayException {
    CCATLogger.DEBUG_LOGGER.info("Balance Dispute Service Get Balance Report Started");
    ResponseEntity<Resource> response = balanceDisputeProxy.exportBalanceDisputeReport(request);
    CCATLogger.DEBUG_LOGGER.info("Finished Serving Balance Dispute Request Successfully!!");
    return response;
  }

  public ResponseEntity<Resource> getBalanceDisputeTodayDataUsageReport(SubscriberRequest request) throws GatewayException {
    return balanceDisputeProxy.getBalanceDisputeTodayDataUsageReport(request);
  }

  public void deleteBalanceDisputeReport(String msisdn) {
    balanceDisputeReportRepositary.deleteBySubscriber(msisdn);
  }
}
