package com.asset.ccat.balance_dispute_service.proxy;

import com.asset.ccat.balance_dispute_service.annotation.LogExecutionTime;
import com.asset.ccat.balance_dispute_service.configrations.Properties;
import com.asset.ccat.balance_dispute_service.defines.Defines;
import com.asset.ccat.balance_dispute_service.defines.Defines.ContextPaths;
import com.asset.ccat.balance_dispute_service.defines.ErrorCodes;
import com.asset.ccat.balance_dispute_service.dto.requests.MapBalanceDisputeServiceRequest;
import com.asset.ccat.balance_dispute_service.dto.requests.MapTodayDataUsageRequest;
import com.asset.ccat.balance_dispute_service.dto.responses.BalanceDisputeReportResponse;
import com.asset.ccat.balance_dispute_service.dto.responses.BaseResponse;
import com.asset.ccat.balance_dispute_service.dto.responses.BdGetTodayUsageMapperResponse;
import com.asset.ccat.balance_dispute_service.exceptions.BalanceDisputeException;
import com.asset.ccat.balance_dispute_service.logger.CCATLogger;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
public class BalanceDisputeMapperProxy {

  @Autowired
  private WebClient webClient;
  @Autowired
  private Properties properties;

  @LogExecutionTime
  public BalanceDisputeReportResponse mapBalanceDisputeReport(
      MapBalanceDisputeServiceRequest request) throws BalanceDisputeException {
    BalanceDisputeReportResponse balanceDisputeReportResponse = null;
    try {
      StringBuilder uri = new StringBuilder(properties.getBdMapperServiceUrls());
      uri.append(Defines.ContextPaths.BALANCE_DISPUTE_MAPPER_SERVICE);
      uri.append(Defines.ContextPaths.BALANCE_DISPUTE_REPORT);
      uri.append(Defines.WEB_ACTIONS.MAP);

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
          throw new BalanceDisputeException(response.getStatusCode(), response.getStatusMessage());
        }
      }
    } catch (RuntimeException ex) {
      CCATLogger.DEBUG_LOGGER.error("Error while  calling  Balance Dispute Mapper Service ", ex);
      CCATLogger.ERROR_LOGGER.error("Error while calling Balance Dispute Mapper Service ", ex);
      throw new BalanceDisputeException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "[ Balance Dispute Mapper Service ]");
    }
    return balanceDisputeReportResponse;
  }

  @LogExecutionTime
  public BdGetTodayUsageMapperResponse mapTodayDataUsage(MapTodayDataUsageRequest request) throws BalanceDisputeException {
    BdGetTodayUsageMapperResponse balanceDisputeReportResponse = null;
    try {
      CCATLogger.INTERFACE_LOGGER.info(" mapTodayDataUsage() : Started with request : {}", request.getTodayDataUsageMap());
      StringBuilder uri = new StringBuilder(properties.getBdMapperServiceUrls());
      uri.append(Defines.ContextPaths.BALANCE_DISPUTE_MAPPER_SERVICE)
              .append(Defines.ContextPaths.BALANCE_DISPUTE_REPORT)
              .append(Defines.WEB_ACTIONS.MAP)
              .append(ContextPaths.TODAY_DATA_USAGE);
      CCATLogger.DEBUG_LOGGER.info("Start call mapTodayDataUsage with URI : {}", uri);

      Mono<BaseResponse<BdGetTodayUsageMapperResponse>> responseAsync = webClient.post()
          .uri(uri.toString())
          .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
          .body(BodyInserters.fromValue(request))
          .retrieve()
          .bodyToMono(new ParameterizedTypeReference<BaseResponse<BdGetTodayUsageMapperResponse>>() {
          }).log();
      BaseResponse<BdGetTodayUsageMapperResponse> response = responseAsync.block();

      if (Objects.nonNull(response)) {
        if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
          balanceDisputeReportResponse = response.getPayload();
        } else {
          CCATLogger.DEBUG_LOGGER.error("Failed response while calling mapTodayDataUsage {}", response);
          throw new BalanceDisputeException(response.getStatusCode(), response.getStatusMessage());
        }
      }
    } catch (RuntimeException ex) {
      CCATLogger.DEBUG_LOGGER.error("Error while  calling  Balance Dispute Mapper Service {}", ex.getMessage());
      CCATLogger.ERROR_LOGGER.error("Error while calling Balance Dispute Mapper Service ", ex);
      throw new BalanceDisputeException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "[ Balance Dispute Mapper Service ]");
    }
    return balanceDisputeReportResponse;
  }
}
