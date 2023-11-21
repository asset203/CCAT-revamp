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
import com.asset.ccat.gateway.models.responses.BaseResponse;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 *
 * @author wael.mohamed
 */
@Component
public class SequenceTableProxy {

    @Autowired
    WebClient webClient;

    @Autowired
    Properties properties;

    @LogExecutionTime
    public Map<String, List<Integer>> getDedAccountSequence() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("getDedAccountSequence Started successfully.");
        Map<String, List<Integer>> sequences = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            Mono<BaseResponse<Map<String, List<Integer>>>> responseAsync = webClient
                    .get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.SEQUENCE
                            + Defines.LOOKUP_SERVICE.DED_ACCOUNT
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<Map<String, List<Integer>>>>() {
                    }).log();

            BaseResponse<Map<String, List<Integer>>> response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    sequences = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("getDedAccountSequence Ended  with "
                            + "| Exception [ statusCode : " + response.getStatusCode() + ", statusMessage : " + response.getStatusMessage() + "].");
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving getServiceClassById " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + sequences + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving getSequence from [lookup-service] ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving getSequence from [lookup-service] ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service");
        }
        CCATLogger.DEBUG_LOGGER.debug("getDedAccountSequence Ended successfully.");
        return sequences;
    }

    @LogExecutionTime
    public Map<String, List<Integer>> getAccumulatorSequence() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("getAccumulatorSequence Started successfully.");
        Map<String, List<Integer>> sequences = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            Mono<BaseResponse<Map<String, List<Integer>>>> responseAsync = webClient
                    .get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.SEQUENCE
                            + Defines.LOOKUP_SERVICE.ACCUMULATOR
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<Map<String, List<Integer>>>>() {
                    }).log();

            BaseResponse<Map<String, List<Integer>>> response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    sequences = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("getAccumulatorSequence Ended  with "
                            + "| Exception [ statusCode : " + response.getStatusCode() + ", statusMessage : " + response.getStatusMessage() + "].");
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving getServiceClassById " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + sequences + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving getSequence from [lookup-service] ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving getSequence from [lookup-service] ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service");
        }
        CCATLogger.DEBUG_LOGGER.debug("getAccumulatorSequence Ended successfully.");
        return sequences;
    }

}
