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
import com.asset.ccat.gateway.models.requests.customer_care.usage_counter.AddOrUpdateUsageRequest;
import com.asset.ccat.gateway.models.requests.customer_care.usage_counter.DeleteUsageThresholdsRequest;
import com.asset.ccat.gateway.models.requests.customer_care.usage_counter.GetAllUsageCountersRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.customer_care.GetAllUsageCountersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author assem.hassan
 */
@Component
public class UsageCountersProxy {

    @Autowired
    Properties properties;

    @Autowired
    WebClient webClient;

    @LogExecutionTime
    public GetAllUsageCountersResponse getAllUsageCounters(GetAllUsageCountersRequest getAllUsageCountersRequest) throws GatewayException {
        BaseResponse<GetAllUsageCountersResponse> response = null;
        GetAllUsageCountersResponse usageCountersResponse = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + getAllUsageCountersRequest + "]");
            Mono<BaseResponse<GetAllUsageCountersResponse>> responseAsync = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.ContextPaths.AIR_SERVICE_CONTEXT_PATH
                            + Defines.ContextPaths.USAGE_COUNTERS
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(getAllUsageCountersRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetAllUsageCountersResponse>>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    usageCountersResponse = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving UsageCounters " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving UsageCounters " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + usageCountersResponse + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving UsageCounters ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving UsageCounters ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "air-service[" + properties.getAirServiceUrls() + "]");
        }

        return usageCountersResponse;
    }

    @LogExecutionTime
    public void deleteUsageThresholds(DeleteUsageThresholdsRequest deleteUTRequest) throws GatewayException {
        BaseResponse response = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + deleteUTRequest + "]");
            CCATLogger.DEBUG_LOGGER.info("Calling air-service for delete usage thresholds");
            Mono<BaseResponse> responseAsync = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.ContextPaths.AIR_SERVICE_CONTEXT_PATH
                            + Defines.ContextPaths.USAGE_COUNTERS
                            + Defines.ContextPaths.USAGE_THRESHOLDS
                            + Defines.WEB_ACTIONS.DELETE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(deleteUTRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));

            if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                CCATLogger.DEBUG_LOGGER.info("Error while deleting usage thresholds" + response);
                CCATLogger.DEBUG_LOGGER.error("Error while  deleting usage thresholds " + response);
                throw new GatewayException(response.getStatusCode(), response.getStatusMessage());
            }
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
            CCATLogger.DEBUG_LOGGER.info("Calling air-service for deleting usage thresholds finished successfully");
        } catch (GatewayException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Runtime Exception occurred while deleting usage thresholds");
            CCATLogger.DEBUG_LOGGER.error("Runtime Exception occurred while deleting usage thresholds", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "air-service[" + properties.getAirServiceUrls() + "]");
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Unknown exception occurred while deleting usage thresholds ");
            CCATLogger.DEBUG_LOGGER.error("Unknown exception occurred while deleting usage thresholds ", ex);
            throw new GatewayException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }

    @LogExecutionTime
    public void updateUsageCountersAndThresholds(AddOrUpdateUsageRequest addOrUpdateUsageRequest) throws GatewayException {
        BaseResponse response = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + addOrUpdateUsageRequest + "]");
            CCATLogger.DEBUG_LOGGER.info("Calling air-service update usage counters and thresholds");
            Mono<BaseResponse> responseAsync = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.ContextPaths.AIR_SERVICE_CONTEXT_PATH
                            + Defines.ContextPaths.USAGE_COUNTERS
                            + Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(addOrUpdateUsageRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));

            if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                CCATLogger.DEBUG_LOGGER.info("Error while updating usage counters and thresholds" + response);
                CCATLogger.DEBUG_LOGGER.error("Error while  updating usage counters and thresholds " + response);
                throw new GatewayException(response.getStatusCode(), response.getStatusMessage());
            }
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
            CCATLogger.DEBUG_LOGGER.info("Calling air-service for updating usage counters and thresholds finished successfully");
        } catch (GatewayException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Runtime Exception occurred while updating usage counters and thresholds");
            CCATLogger.ERROR_LOGGER.error("Runtime Exception occurred while updating usage counters and thresholds", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "air-service[" + properties.getAirServiceUrls() + "]");
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Unknown exception occurred while updating usage counters and thresholds ");
            CCATLogger.ERROR_LOGGER.error("Unknown exception occurred while updating usage counters and thresholds ", ex);
            throw new GatewayException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }
}
