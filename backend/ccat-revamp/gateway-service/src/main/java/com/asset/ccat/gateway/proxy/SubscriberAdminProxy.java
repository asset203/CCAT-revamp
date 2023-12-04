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
import com.asset.ccat.gateway.models.customer_care.GetPrepaidProfileResponse;
import com.asset.ccat.gateway.models.customer_care.SubscriberAccountModel;
import com.asset.ccat.gateway.models.requests.SubscriberRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.customer_care.GetMainProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

/**
 * @author Mahmoud Shehab
 */
@Component
public class SubscriberAdminProxy {

    @Autowired
    Properties properties;

    @Autowired
    WebClient webClient;

    @LogExecutionTime
    public SubscriberAccountModel getAccountDetails(SubscriberRequest subscriberRequest) throws GatewayException {
        SubscriberAccountModel model = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + subscriberRequest + "]");
            CCATLogger.DEBUG_LOGGER.info("Start retrieving account details");
            Mono<BaseResponse<SubscriberAccountModel>> responseAsync = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.ContextPaths.AIR_SERVICE_CONTEXT_PATH
                            + Defines.ContextPaths.ACCOUNT_DETAILS
                            + Defines.WEB_ACTIONS.GET)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(subscriberRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<SubscriberAccountModel>>() {
                    }).log();
            BaseResponse<SubscriberAccountModel> response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    model = response.getPayload();
                    CCATLogger.DEBUG_LOGGER.info("retrieved account details " + response);
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving account details " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + model + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (GatewayException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Runtime Exception occured while retrieving account details ");
            CCATLogger.ERROR_LOGGER.error("Runtime Exception occured while retrieving account details ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "air-service[" + properties.getCiServiceUrls() + "]");
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Unknown exception occured while retrieving account details ");
            CCATLogger.ERROR_LOGGER.error("Unknown exception occured while retrieving account details ", ex);
            throw new GatewayException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
        return model;
    }
    @LogExecutionTime
    public SubscriberAccountModel test(SubscriberRequest subscriberRequest) throws GatewayException {
        SubscriberAccountModel model = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + subscriberRequest + "]");
            CCATLogger.DEBUG_LOGGER.info("Start retrieving account details");
            Mono<BaseResponse<SubscriberAccountModel>> responseAsync = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.ContextPaths.AIR_SERVICE_CONTEXT_PATH
                            + Defines.ContextPaths.ACCOUNT_DETAILS
                            + Defines.WEB_ACTIONS.GET+"test")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(subscriberRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<SubscriberAccountModel>>() {
                    }).log();
            BaseResponse<SubscriberAccountModel> response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    model = response.getPayload();
                    CCATLogger.DEBUG_LOGGER.info("retrieved account details " + response);
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving account details " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + model + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (GatewayException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Runtime Exception occured while retrieving account details ");
            CCATLogger.ERROR_LOGGER.error("Runtime Exception occured while retrieving account details ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "air-service[" + properties.getCiServiceUrls() + "]");
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Unknown exception occured while retrieving account details ");
            CCATLogger.ERROR_LOGGER.error("Unknown exception occured while retrieving account details ", ex);
            throw new GatewayException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
        return model;
    }
    @LogExecutionTime
    public GetPrepaidProfileResponse getProducts(SubscriberRequest subscriberRequest) throws GatewayException {
        GetPrepaidProfileResponse model = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + subscriberRequest + "]");
            CCATLogger.DEBUG_LOGGER.info("Start retrieving account details");
            Mono<BaseResponse<GetPrepaidProfileResponse>> responseAsync = webClient.post()
                    .uri(properties.getCiServiceUrls()
                            + Defines.CI_SERVICE.CONTEXT_PATH
                            + Defines.ContextPaths.PREPAID_PROFILE
                            + Defines.WEB_ACTIONS.GET)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(subscriberRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetPrepaidProfileResponse>>() {
                    }).log();
            BaseResponse<GetPrepaidProfileResponse> response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    model = response.getPayload();
                    CCATLogger.DEBUG_LOGGER.info("retrieved account products " + response);
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving account products " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + model + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (GatewayException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Runtime Exception occured while retrieving account products ");
            CCATLogger.ERROR_LOGGER.error("Runtime Exception occured while retrieving account products ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "ci-service[" + properties.getCiServiceUrls() + "]");
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Unknown exception occured while retrieving account products ");
            CCATLogger.ERROR_LOGGER.error("Unknown exception occured while retrieving account products ", ex);
            throw new GatewayException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
        return model;
    }

    @LogExecutionTime
    public List<GetMainProductResponse> getMainProducts(SubscriberRequest subscriberRequest) throws GatewayException {
        List<GetMainProductResponse> model = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + subscriberRequest + "]");
            CCATLogger.DEBUG_LOGGER.info("Start retrieving main products");
            Mono<BaseResponse<List<GetMainProductResponse>>> responseAsync = webClient.post()
                    .uri(properties.getCiServiceUrls()
                            + Defines.CI_SERVICE.CONTEXT_PATH
                            + Defines.ContextPaths.PREPAID_PROFILE
                            + Defines.ContextPaths.SUBSCRIBER_MAIN_PRODUCTS
                            + Defines.WEB_ACTIONS.GET)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(subscriberRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<List<GetMainProductResponse>>>() {
                    }).log();
            BaseResponse<List<GetMainProductResponse>> response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    model = response.getPayload();
                    CCATLogger.DEBUG_LOGGER.info("retrieved main products " + response);
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving products " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + model + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (GatewayException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Runtime Exception occured while retrieving main products ");
            CCATLogger.ERROR_LOGGER.error("Runtime Exception occured while retrieving main products ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "ci-service[" + properties.getCiServiceUrls() + "]");
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Unknown exception occured while retrieving main products ");
            CCATLogger.ERROR_LOGGER.error("Unknown exception occured while retrieving main products ", ex);
            throw new GatewayException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
        return model;
    }
}
