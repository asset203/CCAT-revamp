package com.asset.ccat.gateway.proxy;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.BarringRequest;
import com.asset.ccat.gateway.models.requests.SubscriberRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class BarringProxy {

    @Autowired
    Properties properties;

    @Autowired
    WebClient webClient;

    @LogExecutionTime
    public void barTemporaryBlocked(BarringRequest subscriberRequest) throws GatewayException {
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + subscriberRequest + "]");
            Mono<BaseResponse> responseAsync = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.ContextPaths.AIR_SERVICE_CONTEXT_PATH
                            + Defines.ContextPaths.BARRING
                            + Defines.ContextPaths.BAR)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(subscriberRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (!response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                CCATLogger.DEBUG_LOGGER.info("Error while performing barTemporaryBlocked " + response);
                CCATLogger.DEBUG_LOGGER.error("Error while performing barTemporaryBlocked " + response);
                throw new GatewayException(response.getStatusCode(), response.getStatusMessage());
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while performing barTemporaryBlocked ");
            CCATLogger.ERROR_LOGGER.error("Error while performing barTemporaryBlocked ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "air-service[" + properties.getAirServiceUrls() + "]");
        }
    }

    @LogExecutionTime
    public void unbarTemporaryBlocked(BarringRequest subscriberRequest) throws GatewayException {
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + subscriberRequest + "]");
            Mono<BaseResponse> responseAsync = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.ContextPaths.AIR_SERVICE_CONTEXT_PATH
                            + Defines.ContextPaths.BARRING
                            + Defines.ContextPaths.UNBAR)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(subscriberRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (!response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                CCATLogger.DEBUG_LOGGER.info("Error while performing unbarTemporaryBlocked " + response);
                CCATLogger.DEBUG_LOGGER.error("Error while performing unbarTemporaryBlocked " + response);
                throw new GatewayException(response.getStatusCode(), response.getStatusMessage());
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while performing unbarTemporaryBlocked ");
            CCATLogger.ERROR_LOGGER.error("Error while performing unbarTemporaryBlocked ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "air-service[" + properties.getAirServiceUrls() + "]");
        }
    }

    @LogExecutionTime
    public void unbarRefillBarring(SubscriberRequest subscriberRequest) throws GatewayException {
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + subscriberRequest + "]");
            Mono<BaseResponse> responseAsync = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.ContextPaths.AIR_SERVICE_CONTEXT_PATH
                            + Defines.ContextPaths.BARRING
                            + Defines.ContextPaths.UNBAR_REFILL_BARRING)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(subscriberRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (!response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                CCATLogger.DEBUG_LOGGER.info("Error while performing unbarRefillBarring " + response);
                CCATLogger.DEBUG_LOGGER.error("Error while performing unbarRefillBarring " + response);
                throw new GatewayException(response.getStatusCode(), response.getStatusMessage());
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while performing unbarRefillBarring ");
            CCATLogger.ERROR_LOGGER.error("Error while performing unbarRefillBarring ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "air-service[" + properties.getAirServiceUrls() + "]");
        }
    }
}
