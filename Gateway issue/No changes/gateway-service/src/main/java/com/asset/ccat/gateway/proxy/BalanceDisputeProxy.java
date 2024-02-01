package com.asset.ccat.gateway.proxy;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
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
import java.util.Objects;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
public class BalanceDisputeProxy {

  private final WebClient webClient;
  private final Properties properties;

  public BalanceDisputeProxy(WebClient webClient, Properties properties) {
    this.webClient = webClient;
    this.properties = properties;
  }

  @LogExecutionTime
  public BalanceDisputeReportResponse getBalanceDisputeReport(
      GetBalanceDisputeReportRequest request)
      throws GatewayException {
    BalanceDisputeReportResponse balanceDisputeReportResponse = null;

    try {
      CCATLogger.INTERFACE_LOGGER.info(
          "BalanceDisputeProxy -> getBalanceDisputeReport() : Started with request : " + request);
      StringBuilder uri = new StringBuilder(properties.getBalanceDisputeServiceUrls());
      uri.append(Defines.ContextPaths.BALANCE_DISPUTE_SERVICE);
      uri.append(Defines.ContextPaths.BALANCE_DISPUTE);
      uri.append(Defines.WEB_ACTIONS.GET);
      CCATLogger.DEBUG_LOGGER.info("Start call getBalanceDisputeReport with URI : " + uri);

      Mono<BaseResponse<BalanceDisputeReportResponse>> responseAsync = webClient.post()
          .uri(uri.toString())
          .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
          .body(BodyInserters.fromValue(request))
          .retrieve()
          .bodyToMono(new ParameterizedTypeReference<BaseResponse<BalanceDisputeReportResponse>>() {
          }).log();
      BaseResponse<BalanceDisputeReportResponse> response = responseAsync.block();
      if (Objects.nonNull(response)) {
        if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
          balanceDisputeReportResponse = response.getPayload();
        } else {
          CCATLogger.DEBUG_LOGGER.info("Error while  calling balance-dispute-service " + response);
          CCATLogger.DEBUG_LOGGER.error("Error while calling balance-dispute-service " + response);
          throw new GatewayException(response.getStatusCode(), response.getStatusMessage());
        }
      }
    } catch (RuntimeException ex) {
      CCATLogger.DEBUG_LOGGER.info("Error while  calling  getBalanceDisputeReport ");
      CCATLogger.DEBUG_LOGGER.error("Error while calling getBalanceDisputeReport " + ex);
      throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null,
          "[ Balance Dispute Service ]");
    }
    return balanceDisputeReportResponse;
  }

  @LogExecutionTime
  public ResponseEntity<Resource> exportBalanceDisputeReport(
      SubscriberRequest request)
      throws GatewayException {
    ResponseEntity<Resource> response = null;
    long start = 0;
    long executionTime;
    try {
      CCATLogger.INTERFACE_LOGGER.info(
          "BalanceDisputeProxy -> getBalanceDisputeReport() : Started with request : " + request);
      StringBuilder uri = new StringBuilder(properties.getBalanceDisputeServiceUrls());
      uri.append(Defines.ContextPaths.BALANCE_DISPUTE_SERVICE);
      uri.append(Defines.ContextPaths.BALANCE_DISPUTE);
      uri.append(WEB_ACTIONS.EXPORT);
      CCATLogger.DEBUG_LOGGER.info("Start call getBalanceDisputeReport with URI : " + uri);

      Mono<ResponseEntity<Resource>> responseAsync = webClient.post()
          .uri(uri.toString())
          .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
          .body(BodyInserters.fromValue(request))
          .retrieve()
          .toEntity(Resource.class);
      response = responseAsync.block();
      CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
      executionTime = System.currentTimeMillis() - start;
      CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
    } catch (RuntimeException ex) {
      CCATLogger.DEBUG_LOGGER.info("Error while  calling  export BalanceDisputeReport ");
      CCATLogger.DEBUG_LOGGER.error("Error while calling export BalanceDisputeReport " + ex);
      throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null,
          "[ Balance Dispute Service ]");
    }
    return response;
  }

  @LogExecutionTime
  public ResponseEntity<Resource> getBalanceDisputeTodayDataUsageReport(
      SubscriberRequest request)
      throws GatewayException {
    ResponseEntity<Resource> response = null;
    long start = 0;
    long executionTime;
    try {
      CCATLogger.INTERFACE_LOGGER.info(
          "BalanceDisputeProxy -> getBalanceDisputeTodayDataUsageReport() : Started with request : "
              + request);
      StringBuilder uri = new StringBuilder(properties.getBalanceDisputeServiceUrls());
      uri.append(Defines.ContextPaths.BALANCE_DISPUTE_SERVICE);
      uri.append(Defines.ContextPaths.BALANCE_DISPUTE);
      uri.append(WEB_ACTIONS.GET);
      uri.append(ContextPaths.TODAY_DATA_USAGE);
      CCATLogger.DEBUG_LOGGER.info(
          "Start call getBalanceDisputeTodayDataUsageReport with URI : " + uri);

      Mono<ResponseEntity<Resource>> responseAsync = webClient.post()
          .uri(uri.toString())
          .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
          .body(BodyInserters.fromValue(request))
          .retrieve()
          .toEntity(Resource.class);
      response = responseAsync.block();
      CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
      executionTime = System.currentTimeMillis() - start;
      CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
    } catch (RuntimeException ex) {
      CCATLogger.DEBUG_LOGGER.info("Error while  calling  export Today Data Usage ");
      CCATLogger.DEBUG_LOGGER.error("Error while calling export Today Data Usage " + ex);
      throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null,
          "[ Balance Dispute Service ]");
    }
    return response;
  }
}
