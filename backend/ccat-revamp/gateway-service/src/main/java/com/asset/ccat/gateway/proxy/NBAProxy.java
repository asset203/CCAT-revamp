/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.proxy;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.nba.AcceptGiftRequest;
import com.asset.ccat.gateway.models.nba.GiftModel;
import com.asset.ccat.gateway.models.nba.RejectGiftRequest;
import com.asset.ccat.gateway.models.nba.SendGiftRequest;
import com.asset.ccat.gateway.models.requests.nba.GetAllGiftsRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author Mahmoud Shehab
 */
@Component
public class NBAProxy {

  @Autowired
  Properties properties;

  @Autowired
  WebClient webClient;

  @LogExecutionTime
  public List<GiftModel> getAllGifts(GetAllGiftsRequest getAllGiftsRequest)
      throws GatewayException {
    BaseResponse<GiftModel[]> response = null;
    List<GiftModel> model = null;
    long start = 0;
    long executionTime;
    try {
      start = System.currentTimeMillis();
      CCATLogger.INTERFACE_LOGGER.info("request is [" + getAllGiftsRequest + "]");
      Boolean tibcoIsEnable = properties.getNbaInterfaceSelector();
      StringBuilder URI = new StringBuilder(properties.getNbaServiceUrls());
      URI.append(Defines.ContextPaths.NBA_SERVICE);
      if (tibcoIsEnable) {
        URI.append(Defines.ContextPaths.TIBCO_GIFTS);
      } else {
        URI.append(Defines.ContextPaths.GIFTS);
      }
      URI.append(Defines.WEB_ACTIONS.GET_ALL);
      Mono<BaseResponse<GiftModel[]>> responseAsync = webClient.post()
          .uri(URI.toString())
          .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
          .body(BodyInserters.fromValue(getAllGiftsRequest))
          .retrieve()
          .bodyToMono(new ParameterizedTypeReference<BaseResponse<GiftModel[]>>() {
          }).log();
      response = responseAsync.block();
      if (response != null) {
        if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
          model = Arrays.stream(response.getPayload()).collect(Collectors.toList());
        } else {
          CCATLogger.DEBUG_LOGGER.info("Error while update balance and date " + response);
          CCATLogger.DEBUG_LOGGER.error("Error while update balance and date " + response);
          throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
        }
      }
      CCATLogger.INTERFACE_LOGGER.info("response is [" + model + "]");
      executionTime = System.currentTimeMillis() - start;
      CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
    } catch (RuntimeException ex) {
      CCATLogger.DEBUG_LOGGER.info("Error while retrieving AccumulatorModel ");
      CCATLogger.ERROR_LOGGER.error("Error while retrieving AccumulatorModel ", ex);
      throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE,
          "nba-service[" + properties.getNbaServiceUrls() + "]");
    }
    return model;
  }

  @LogExecutionTime
  public void acceptGift(AcceptGiftRequest acceptGiftRequest) throws GatewayException {
    BaseResponse response = null;
    long start = 0;
    long executionTime;
    try {
      start = System.currentTimeMillis();
      CCATLogger.INTERFACE_LOGGER.info("request is [" + acceptGiftRequest + "]");
      Boolean tibcoIsEnable = properties.getNbaInterfaceSelector();
      StringBuilder URI = new StringBuilder(properties.getNbaServiceUrls());
      URI.append(Defines.ContextPaths.NBA_SERVICE);
      if (tibcoIsEnable) {
        URI.append(Defines.ContextPaths.TIBCO_GIFTS);
      } else {
        URI.append(Defines.ContextPaths.GIFTS);
      }
      URI.append(Defines.WEB_ACTIONS.ACCEPT);
      Mono<BaseResponse> responseAsync = webClient.post()
          .uri(URI.toString())
          .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
          .body(BodyInserters.fromValue(acceptGiftRequest))
          .retrieve()
          .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
          }).log();
      response = responseAsync.block();
      if (response != null) {
        if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
          CCATLogger.DEBUG_LOGGER.info("Error while update Balance and date " + response);
          CCATLogger.DEBUG_LOGGER.error("Error while update Balance and date " + response);
          throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
        }
      }

      executionTime = System.currentTimeMillis() - start;
      CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
    } catch (RuntimeException ex) {
      CCATLogger.DEBUG_LOGGER.info("Error while retrieving AccumulatorModel ");
      CCATLogger.ERROR_LOGGER.error("Error while retrieving AccumulatorModel ", ex);
      throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE,
          "NBA-service[" + properties.getNbaServiceUrls() + "]");
    }
  }

  @LogExecutionTime
  public void rejectGift(RejectGiftRequest rejectGiftRequest) throws GatewayException {
    BaseResponse response = null;
    long start = 0;
    long executionTime;
    try {
      start = System.currentTimeMillis();
      CCATLogger.INTERFACE_LOGGER.info("request is [" + rejectGiftRequest + "]");
      Mono<BaseResponse> responseAsync = webClient.post()
          .uri(properties.getNbaServiceUrls() + Defines.ContextPaths.NBA_SERVICE
              + Defines.ContextPaths.GIFTS + Defines.WEB_ACTIONS.REJECT)
          .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
          .body(BodyInserters.fromValue(rejectGiftRequest))
          .retrieve()
          .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
          }).log();
      response = responseAsync.block();
      if (response != null) {
        if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
          CCATLogger.DEBUG_LOGGER.info("Error while update Balance and date " + response);
          CCATLogger.DEBUG_LOGGER.error("Error while update Balance and date " + response);
          throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
        }
      }
      executionTime = System.currentTimeMillis() - start;
      CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
    } catch (RuntimeException ex) {
      CCATLogger.DEBUG_LOGGER.info("Error while retrieving AccumulatorModel ");
      CCATLogger.ERROR_LOGGER.error("Error while retrieving AccumulatorModel ", ex);
      throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE,
          "NBA-service[" + properties.getNbaServiceUrls() + "]");
    }
  }

  @LogExecutionTime
  public void sendSmsGift(SendGiftRequest sendGiftRequest) throws GatewayException {
    BaseResponse response = null;
    long start = 0;
    long executionTime;
    try {
      Boolean tibcoIsEnable = properties.getNbaInterfaceSelector();
      start = System.currentTimeMillis();
      CCATLogger.INTERFACE_LOGGER.info("request is [" + sendGiftRequest + "]");

      StringBuilder URI = new StringBuilder(properties.getNbaServiceUrls());
      URI.append(Defines.ContextPaths.NBA_SERVICE);
      if (tibcoIsEnable) {
        URI.append(Defines.ContextPaths.TIBCO_GIFTS);
      } else {
        URI.append(Defines.ContextPaths.GIFTS);
      }
      URI.append(Defines.WEB_ACTIONS.SEND);

      Mono<BaseResponse> responseAsync = webClient.post()
          .uri(URI.toString())
          .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
          .body(BodyInserters.fromValue(sendGiftRequest))
          .retrieve()
          .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
          }).log();
      response = responseAsync.block();
      if (response != null) {
        if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
          CCATLogger.DEBUG_LOGGER.info("Error while update Balance and date " + response);
          CCATLogger.DEBUG_LOGGER.error("Error while update Balance and date " + response);
          throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
        }
      }
      executionTime = System.currentTimeMillis() - start;
      CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
    } catch (RuntimeException ex) {
      CCATLogger.DEBUG_LOGGER.info("Error while retrieving AccumulatorModel ");
      CCATLogger.ERROR_LOGGER.error("Error while retrieving AccumulatorModel ", ex);
      throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE,
          "NBA-service[" + properties.getNbaServiceUrls() + "]");
    }
  }
}
