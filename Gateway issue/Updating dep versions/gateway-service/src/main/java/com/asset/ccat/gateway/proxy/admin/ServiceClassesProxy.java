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
import com.asset.ccat.gateway.models.customer_care.ServiceClassModel;
import com.asset.ccat.gateway.models.requests.service_class.ServiceClassConversionRequest;
import com.asset.ccat.gateway.models.requests.service_class.UpdateServiceClassRequest;
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
 * @author wael.mohamed
 */
@Component
public class ServiceClassesProxy {

    @Autowired
    WebClient webClient;

    @Autowired
    private Properties properties;

    @LogExecutionTime
    public List<ServiceClassModel> getAllServiceClasses() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("ServiceClassesProxy -> getAllServiceClasses() Started successfully.");
        List<ServiceClassModel> list = null;
        try {
            Mono<BaseResponse<ServiceClassModel[]>> responseAsync = webClient
                    .get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                            + Defines.ContextPaths.SERVICE_CLASSES)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<ServiceClassModel[]>>() {
                    }).log();

            BaseResponse<ServiceClassModel[]> response = responseAsync.block();
            if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                list = Arrays.stream(response.getPayload()).collect(Collectors.toList());
            } else {
                CCATLogger.DEBUG_LOGGER.debug("ServiceClassesProxy -> getAllServiceClasses() Ended  with "
                        + "| Exception [ statusCode : " + response.getStatusCode() + ", statusMessage : " + response.getStatusMessage() + "].");
                CCATLogger.DEBUG_LOGGER.info("Error while retrieving getAllServiceClasses " + response);
                throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
            }
            for (var serviceClassess : list) {
                CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                        + ", statusCode: " + response.getStatusCode()
                        + ", payload: " + response.getPayload()
                        + ", payload:["
                        + ", serviceClassess:["
                        + ", code: " + serviceClassess.getCode()
                        + ", name: " + serviceClassess.getName()
                        + "]"
                        + "]"
                        + "]");
            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving service classes from [lookup-service] ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving service classes from [lookup-service] ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service");
        }
        CCATLogger.DEBUG_LOGGER.debug("ServiceClassesProxy -> getAllServiceClasses() Ended successfully.");
        return list;
    }

    @LogExecutionTime
    public void updateServiceClass(UpdateServiceClassRequest serviceClassRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("ServiceClassesProxy -> UpdateServiceClass() Started successfully.");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "newId: " + serviceClassRequest.getNewServiceClassId()
                    + ", currentId: " + serviceClassRequest.getCurrentServiceClassId()
                    + "]");
            Mono<BaseResponse> responseAsync = webClient
                    .post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.ContextPaths.AIR_SERVICE_CONTEXT_PATH
                            + Defines.ContextPaths.SERVICE_CLASSES
                            + Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(serviceClassRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while update service class " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while update service class " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("ServiceClassesProxy -> getAllServiceClasses() Ended  with "
                            + "| Exception [ statusCode : " + response.getStatusCode() + ", statusMessage : " + response.getStatusMessage() + "].");
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving getAllServiceClasses " + response);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving update service class ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving update service class ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "air-service[" + properties.getAirServiceUrls() + "]");
        }
    }

    @LogExecutionTime
    public void serviceClassConversion(ServiceClassConversionRequest serviceClassRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("ServiceClassesProxy -> serviceClassConversion() Started successfully.");
        BaseResponse response;
        try {
            String uri = properties.getCiServiceUrls()
                    + Defines.CI_SERVICE.CONTEXT_PATH
                    + Defines.CI_SERVICE.SERVICE_CLASSES
                    + Defines.WEB_ACTIONS.UPDATE;
            CCATLogger.DEBUG_LOGGER.debug("Request Method = [POST], URI = {}, Request Body = {}", uri, serviceClassRequest);
            CCATLogger.INTERFACE_LOGGER.info("Request Method = [POST], URI = {}, Request Body = {}", uri, serviceClassRequest);

            Mono<BaseResponse> responseAsync = webClient
                    .post()
                    .uri(uri)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(serviceClassRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = responseAsync.block();
            CCATLogger.DEBUG_LOGGER.debug("CI-Response = {}", response);
            CCATLogger.INTERFACE_LOGGER.info("CI-Response = {}", response);
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("ServiceClassesProxy -> serviceClassConversion() Ended  with "
                            + "| Exception [ statusCode : " + response.getStatusCode() + ", statusMessage : " + response.getStatusMessage() + "].");
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving serviceClassConversion " + response);
                }
            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.error("RuntimeException while retrieving conversion service class ", ex);
            CCATLogger.ERROR_LOGGER.error("RuntimeException while retrieving conversion service class ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "air-service[" + properties.getAirServiceUrls() + "]");
        }
    }
}
