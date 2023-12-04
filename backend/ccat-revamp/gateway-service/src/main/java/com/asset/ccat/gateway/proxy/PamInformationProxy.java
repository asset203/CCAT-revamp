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
import com.asset.ccat.gateway.models.requests.customer_care.pam_information.AddPamInformationRequest;
import com.asset.ccat.gateway.models.requests.customer_care.pam_information.DeletePamInformationRequest;
import com.asset.ccat.gateway.models.requests.customer_care.pam_information.EvaluatePamInformationRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 *
 * @author wael.mohamed
 */
@Component
public class PamInformationProxy {

    @Autowired
    private WebClient webClient;

    @Autowired
    Properties properties;

    @LogExecutionTime
    public BaseResponse addPamInfo(AddPamInformationRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting addPamInfo - call lookup-service");
        BaseResponse response = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The AIR-Service....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.AIR_SERVICE.CONTEXT_PATH
                            + Defines.AIR_SERVICE.PAM_INFORMATION
                            + Defines.WEB_ACTIONS.ADD)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();

            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Adding PamInfo " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Adding PamInfo " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Adding PamInfo ");
            CCATLogger.ERROR_LOGGER.error("Error while Adding PamInfo", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "Air-service[" + properties.getAirServiceUrls() + "]");
        }
        return response;
    }

    public BaseResponse deletePamInfo(DeletePamInformationRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting deletePamInfo - call lookup-service");
        BaseResponse response = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The AIR-Service....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.AIR_SERVICE.CONTEXT_PATH
                            + Defines.AIR_SERVICE.PAM_INFORMATION
                            + Defines.WEB_ACTIONS.DELETE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();

            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Deleting PamInfo " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Deleting PamInfo " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Deleting PamInfo ");
            CCATLogger.ERROR_LOGGER.error("Error while Deleting PamInfo", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "Air-service[" + properties.getAirServiceUrls() + "]");
        }
        return response;
    }

    public BaseResponse evaluatePamInfo(EvaluatePamInformationRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting evaluatePamInfo - call lookup-service");
        BaseResponse response = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The AIR-Service....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.AIR_SERVICE.CONTEXT_PATH
                            + Defines.AIR_SERVICE.PAM_INFORMATION
                            + Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();

            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Evaluating PamInfo " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Evaluating PamInfo " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Evaluating PamInfo ");
            CCATLogger.ERROR_LOGGER.error("Error while Evaluating PamInfo", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "Air-service[" + properties.getAirServiceUrls() + "]");
        }
        return response;
    }

}
