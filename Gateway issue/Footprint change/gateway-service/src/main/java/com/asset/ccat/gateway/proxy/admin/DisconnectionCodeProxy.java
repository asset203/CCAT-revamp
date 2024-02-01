/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.proxy.admin;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.admin.DisconnectionCodeModel;
import com.asset.ccat.gateway.models.requests.admin.disconnection_code.CreateDisconnectionCodeRequest;
import com.asset.ccat.gateway.models.requests.admin.disconnection_code.DeleteDisconnectionCodeRequest;
import com.asset.ccat.gateway.models.requests.admin.disconnection_code.GetAllDisconnectionCodesRequest;
import com.asset.ccat.gateway.models.requests.admin.disconnection_code.UpdateDisconnectionCodeRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import java.util.Arrays;
import java.util.List;
import static java.util.stream.Collectors.toList;
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
public class DisconnectionCodeProxy {

    @Autowired
    WebClient webClient;

    @Autowired
    Properties properties;

    @LogExecutionTime
    public List<DisconnectionCodeModel> getAllDisconnectionCodes(GetAllDisconnectionCodesRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getAllDisconnectionCodes - call lookup-service");
        List<DisconnectionCodeModel> disconnectionCodes = null;
        try {

            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The lookup-service....");
            Mono<BaseResponse<DisconnectionCodeModel[]>> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.DISCONNECTION_CODE
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<DisconnectionCodeModel[]>>() {
                    }).log();
            BaseResponse<DisconnectionCodeModel[]> response = res.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    disconnectionCodes = Arrays.stream(response.getPayload()).collect(toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving DisconnectionCodes " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving DisconnectionCodes " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            for (var disconnectionCode : disconnectionCodes) {
                CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                        + ", statusCode: " + response.getStatusCode()
                        + ", payload: " + response.getPayload()
                        + ", payload:["
                        + ", disconnectionCodes:["
                        + ", code: " + disconnectionCode.getCode()
                        + ", id: " + disconnectionCode.getId()
                        + ", description: " + disconnectionCode.getDescription()
                        + "]"
                        + "]"
                        + "]");
            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving DisconnectionCodes ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving DisconnectionCodes ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service [" + properties.getLookupsServiceUrls() + "]");
        }
        return disconnectionCodes;
    }

    @LogExecutionTime
    public BaseResponse createDisconnectionCode(CreateDisconnectionCodeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting createDisconnectionCode - call lookup-service");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "description: " + request.getDescription()
                    + "code: " + request.getCode()
                    + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The lookup-service....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.DISCONNECTION_CODE
                            + Defines.WEB_ACTIONS.ADD)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();

            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Adding DisconnectionCode " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Adding DisconnectionCode " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Adding DisconnectionCode ");
            CCATLogger.ERROR_LOGGER.error("Error while Adding DisconnectionCode", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse updateDisconnectionCode(UpdateDisconnectionCodeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting updateDisconnectionCode - call lookup-service");
        BaseResponse response = null;
        try {

            CCATLogger.INTERFACE_LOGGER.info("request is [" + "description: " + request.getDescription()
                    + "code: " + request.getCode()
                    + "id: " + request.getId()
                    + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The lookup-service....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.DISCONNECTION_CODE
                            + Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();

            response = res.block();

            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Updating DisconnectionCode " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Updating DisconnectionCode " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Updating DisconnectionCode ");
            CCATLogger.ERROR_LOGGER.error("Error while Updating DisconnectionCode", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse deleteDisconnectionCode(DeleteDisconnectionCodeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting deleteDisconnectionCode - call lookup-service");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "id:" + request.getId() + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The lookup-service....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.DISCONNECTION_CODE
                            + Defines.WEB_ACTIONS.DELETE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Deleting DisconnectionCode " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Deleting DisconnectionCode " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Deleting DisconnectionCode ");
            CCATLogger.ERROR_LOGGER.error("Error while Deleting DisconnectionCode", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

}
