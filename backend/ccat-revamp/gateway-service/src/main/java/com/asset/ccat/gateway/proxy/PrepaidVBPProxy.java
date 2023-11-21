package com.asset.ccat.gateway.proxy;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.customer_care.prepaidVBP.PrepaidCheckSubscriptionRequest;
import com.asset.ccat.gateway.models.requests.customer_care.prepaidVBP.PrepaidVBPSubscriptionRequest;
import com.asset.ccat.gateway.models.requests.customer_care.prepaidVBP.PrepaidVBPUnsubscriptionRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.customer_care.PrepaidVBPCheckResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Objects;

@Component
public class PrepaidVBPProxy {
    @Autowired
    WebClient webClient;

    @Autowired
    Properties properties;


    @LogExecutionTime
    public Boolean prepaidVBPCheckSubscription(PrepaidCheckSubscriptionRequest prepaidCheckSubscriptionRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting prepaidVBPCheckSubscription - calling CI service");
        BaseResponse<PrepaidVBPCheckResponse> response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("Check Prepaid VBP CheckSubscription request [" + prepaidCheckSubscriptionRequest + "]");
            String URI = properties.getCiServiceUrls()
                    +Defines.CI_SERVICE.CONTEXT_PATH
                    + Defines.ContextPaths.PREPAID_VBP
                    + Defines.WEB_ACTIONS.CHECK;

            CCATLogger.DEBUG_LOGGER.debug("Calling The CI Service... With URI  : "+URI);
            Mono<BaseResponse<PrepaidVBPCheckResponse>> res = webClient
                    .post()
                    .uri(URI)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(prepaidCheckSubscriptionRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<PrepaidVBPCheckResponse>>() {
                    }).log();
            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Checking Subscription for PrepaidVBP" + response);
                    CCATLogger.DEBUG_LOGGER.error("rror while Checking Subscription for PrepaidVBP " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Checking Subscription for PrepaidVBP");
            CCATLogger.ERROR_LOGGER.error("Error while Checking Subscription for PrepaidVBP", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "CI-service[" + properties.getCiServiceUrls() + "]");
        }
        return response.getPayload().getIsSubscribed();
    }

    @LogExecutionTime
    public void prepaidVBPUnsubscription(PrepaidVBPUnsubscriptionRequest prepaidVBPUnsubscriptionRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting prepaidVBPUnsubscription - calling CI service");
        BaseResponse<PrepaidVBPCheckResponse> response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("Check Prepaid VBP Unsubscription request [" + prepaidVBPUnsubscriptionRequest + "]");
            String URI = properties.getCiServiceUrls()
                    +Defines.CI_SERVICE.CONTEXT_PATH
                    + Defines.ContextPaths.PREPAID_VBP
                    + Defines.WEB_ACTIONS.UNSUBSCRIBE;

            CCATLogger.DEBUG_LOGGER.debug("Calling The CI Service... With URI  : "+URI);
            Mono<BaseResponse> res = webClient
                    .post()
                    .uri(URI)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(prepaidVBPUnsubscriptionRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Canceling Subscription for PrepaidVBP (Unsubscribing)" + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Canceling Subscription for PrepaidVBP (Unsubscribing) " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Canceling Subscription for PrepaidVBP (Unsubscribing) ");
            CCATLogger.ERROR_LOGGER.error("Error while Canceling Subscription for PrepaidVBP (Unsubscribing)", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "CI-service[" + properties.getCiServiceUrls() + "]");
        }
    }

    @LogExecutionTime
    public void prepaidVBSubscription(PrepaidVBPSubscriptionRequest prepaidVBPSubscriptionRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting prepaidVBPSubscription - calling CI service");
        BaseResponse<PrepaidVBPCheckResponse> response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("Check Prepaid VBP Subscription request [" + prepaidVBPSubscriptionRequest + "]");
            String URI = properties.getCiServiceUrls()
                    +Defines.CI_SERVICE.CONTEXT_PATH
                    + Defines.ContextPaths.PREPAID_VBP
                    + Defines.WEB_ACTIONS.SUBSCRIBE;

            CCATLogger.DEBUG_LOGGER.debug("Calling The CI Service... With URI  : "+URI);
            Mono<BaseResponse> res = webClient
                    .post()
                    .uri(URI)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(prepaidVBPSubscriptionRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (Objects.nonNull(response)) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Starting Subscription for PrepaidVBP (Subscribing)" + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Starting Subscription for PrepaidVBP (subscribing) " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Starting Subscription for PrepaidVBP (Subscribing) ");
            CCATLogger.ERROR_LOGGER.error("Error while Starting Subscription for PrepaidVBP (Subscribing)", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "CI-service[" + properties.getCiServiceUrls() + "]");
        }

    }

}
