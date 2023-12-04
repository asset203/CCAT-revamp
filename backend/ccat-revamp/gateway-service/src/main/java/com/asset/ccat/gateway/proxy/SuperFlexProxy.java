package com.asset.ccat.gateway.proxy;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.customer_care.FamilyAndFriendsModel;
import com.asset.ccat.gateway.models.requests.SubscriberRequest;
import com.asset.ccat.gateway.models.requests.customer_care.super_flex.ActivateAddonRequest;
import com.asset.ccat.gateway.models.requests.customer_care.super_flex.ActivateThresholdRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.customer_care.PrepaidVBPCheckResponse;
import com.asset.ccat.gateway.models.responses.customer_care.super_flex.GetMIThresholdResponse;
import com.asset.ccat.gateway.models.responses.customer_care.super_flex.GetOptInAddonsResponse;
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

@Component
public class SuperFlexProxy {

    @Autowired
    private WebClient webClient;

    @Autowired
    private Properties properties;

    @LogExecutionTime
    public GetOptInAddonsResponse getOptinAddons(SubscriberRequest request) throws GatewayException {

        CCATLogger.DEBUG_LOGGER.debug("Started SuperFlexProxy - getOptinAddons()");
        BaseResponse<GetOptInAddonsResponse> response;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "msisdn:" + request.getMsisdn() + "]");
            CCATLogger.DEBUG_LOGGER.info("Start getting optin addons from air service");
            Mono<BaseResponse<GetOptInAddonsResponse>> responseAsync = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.ContextPaths.AIR_SERVICE_CONTEXT_PATH
                            + Defines.AIR_SERVICE.SUPER_FLEX
                            + Defines.AIR_SERVICE.SUPER_FLEX_ADDON
                            + Defines.WEB_ACTIONS.GET
                    )
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetOptInAddonsResponse>>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while getting optin addons from air service " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while getting optin addons from air service" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while getting optin addons from air service ");
            CCATLogger.ERROR_LOGGER.error("Error while getting optin addons from air service", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "air-service[" + properties.getAirServiceUrls() + "]");
        }
        CCATLogger.DEBUG_LOGGER.debug("Ended SuperFlexProxy - getOptinAddons()");
        CCATLogger.DEBUG_LOGGER.info("Finished getting optin addons from air service");
        return response.getPayload();
    }


    @LogExecutionTime
    public void activateAddon(ActivateAddonRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started SuperFlexProxy - activateAddon()");
        BaseResponse response;
        try {
            CCATLogger.INTERFACE_LOGGER.info("Request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.info("Start activate addons from ci service");

            Mono<BaseResponse> res = webClient
                    .post()
                    .uri(properties.getCiServiceUrls()
                            + Defines.CI_SERVICE.CONTEXT_PATH
                            + Defines.CI_SERVICE.SUPER_FLEX
                            + Defines.CI_SERVICE.SUPER_FLEX_ADDONS
                            + Defines.WEB_ACTIONS.ACTIVATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while activate addons from ci service " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while activate addons from ci service" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while activate addons from ci service");
            CCATLogger.ERROR_LOGGER.error("Error while activate addons from ci service", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "CI-service[" + properties.getCiServiceUrls() + "]");
        }
        CCATLogger.DEBUG_LOGGER.debug("Ended SuperFlexProxy - activateAddon()");
        CCATLogger.DEBUG_LOGGER.info("Finished activate addons from ci service");
    }

    @LogExecutionTime
    public void deactivateAddon(SubscriberRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started SuperFlexProxy - deactivateAddon()");
        BaseResponse response;
        try {
            CCATLogger.INTERFACE_LOGGER.info("Request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.info("Start deactivate addons from ci service");

            Mono<BaseResponse> res = webClient
                    .post()
                    .uri(properties.getCiServiceUrls()
                            + Defines.CI_SERVICE.CONTEXT_PATH
                            + Defines.CI_SERVICE.SUPER_FLEX
                            + Defines.CI_SERVICE.SUPER_FLEX_ADDONS
                            + Defines.WEB_ACTIONS.DEACTIVATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while deactivate addons from ci service " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while deactivate addons from ci service" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while deactivate addons from ci service");
            CCATLogger.ERROR_LOGGER.error("Error while deactivate addons from ci service", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "CI-service[" + properties.getCiServiceUrls() + "]");
        }
        CCATLogger.DEBUG_LOGGER.debug("Ended SuperFlexProxy - deactivateAddon()");
        CCATLogger.DEBUG_LOGGER.info("Finished deactivate addons from ci service");
    }

    @LogExecutionTime
    public GetMIThresholdResponse getMiThreshold(SubscriberRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started SuperFlexProxy - getMiThreshold()");
        BaseResponse<GetMIThresholdResponse> response;
        try {
            CCATLogger.INTERFACE_LOGGER.info("Request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.info("Start get MI threshold from ci service");

            Mono<BaseResponse<GetMIThresholdResponse>> res = webClient
                    .post()
                    .uri(properties.getCiServiceUrls()
                            + Defines.CI_SERVICE.CONTEXT_PATH
                            + Defines.CI_SERVICE.SUPER_FLEX
                            + Defines.CI_SERVICE.SUPER_FLEX_THRESHOLDS
                            + Defines.WEB_ACTIONS.GET)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetMIThresholdResponse>>() {
                    }).log();
            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while get MI threshold from ci service " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while get MI threshold from ci service" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while get MI threshold from ci services from ci service");
            CCATLogger.ERROR_LOGGER.error("Error while get MI threshold from ci service from ci service", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "CI-service[" + properties.getCiServiceUrls() + "]");
        }
        CCATLogger.DEBUG_LOGGER.debug("Ended SuperFlexProxy - getMiThreshold()");
        CCATLogger.DEBUG_LOGGER.info("Finished get MI threshold from ci service");
        return response.getPayload();
    }

    @LogExecutionTime
    public void activateThreshold(ActivateThresholdRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started SuperFlexProxy - activateThreshold()");
        BaseResponse response;
        try {
            CCATLogger.INTERFACE_LOGGER.info("Request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.info("Start activate MI threshold from ci service");

            Mono<BaseResponse> res = webClient
                    .post()
                    .uri(properties.getCiServiceUrls()
                            + Defines.CI_SERVICE.CONTEXT_PATH
                            + Defines.CI_SERVICE.SUPER_FLEX
                            + Defines.CI_SERVICE.SUPER_FLEX_THRESHOLDS
                            + Defines.WEB_ACTIONS.ACTIVATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while activate MI threshold from ci service " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while activate MI threshold from ci service" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while activate MI threshold from ci services from ci service");
            CCATLogger.ERROR_LOGGER.error("Error while activate MI threshold from ci service from ci service", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "CI-service[" + properties.getCiServiceUrls() + "]");
        }
        CCATLogger.DEBUG_LOGGER.debug("Ended SuperFlexProxy - activateThreshold()");
        CCATLogger.DEBUG_LOGGER.info("Finished activate MI threshold from ci service");

    }

    @LogExecutionTime
    public void deactivateThreshold(SubscriberRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started SuperFlexProxy - deactivateThreshold()");
        BaseResponse response;
        try {
            CCATLogger.INTERFACE_LOGGER.info("Request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.info("Start deactivate MI threshold from ci service");

            Mono<BaseResponse> res = webClient
                    .post()
                    .uri(properties.getCiServiceUrls()
                            + Defines.CI_SERVICE.CONTEXT_PATH
                            + Defines.CI_SERVICE.SUPER_FLEX
                            + Defines.CI_SERVICE.SUPER_FLEX_THRESHOLDS
                            + Defines.WEB_ACTIONS.DEACTIVATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while deactivate MI threshold from ci service " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while deactivate MI threshold from ci service" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while deactivate MI threshold from ci service");
            CCATLogger.ERROR_LOGGER.error("Error while deactivate MI threshold from ci service", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "CI-service[" + properties.getCiServiceUrls() + "]");
        }
        CCATLogger.DEBUG_LOGGER.debug("Ended SuperFlexProxy - deactivateThreshold()");
        CCATLogger.DEBUG_LOGGER.info("Finished deactivate MI threshold from ci service");
    }

    @LogExecutionTime
    public void stopMIThreshold(SubscriberRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started SuperFlexProxy - stopMIThreshold()");
        BaseResponse response;
        try {
            CCATLogger.INTERFACE_LOGGER.info("Request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.info("Start stop MI threshold from ci service");

            Mono<BaseResponse> res = webClient
                    .post()
                    .uri(properties.getCiServiceUrls()
                            + Defines.CI_SERVICE.CONTEXT_PATH
                            + Defines.CI_SERVICE.SUPER_FLEX
                            + Defines.CI_SERVICE.SUPER_FLEX_STOP_MI)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while stop MI threshold from ci service " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while stop MI threshold from ci service" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while stop MI threshold from ci service");
            CCATLogger.ERROR_LOGGER.error("Error while stop MI threshold from ci service", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "CI-service[" + properties.getCiServiceUrls() + "]");
        }
        CCATLogger.DEBUG_LOGGER.debug("Ended SuperFlexProxy - stopMIThreshold()");
        CCATLogger.DEBUG_LOGGER.info("Finished stop MI threshold from ci service");
    }

    public void deactivateStopMI(SubscriberRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started SuperFlexProxy - deactivateStopMI()");
        BaseResponse response;
        try {
            CCATLogger.INTERFACE_LOGGER.info("Request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.info("Start deactivate stop MI threshold from ci service");

            Mono<BaseResponse> res = webClient
                    .post()
                    .uri(properties.getCiServiceUrls()
                            + Defines.CI_SERVICE.CONTEXT_PATH
                            + Defines.CI_SERVICE.SUPER_FLEX
                            + Defines.CI_SERVICE.SUPER_FLEX_STOP_MI
                            + Defines.WEB_ACTIONS.DEACTIVATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while deactivate stop MI threshold from ci service " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while deactivate stop MI threshold from ci service" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while deactivate stop MI threshold from ci service");
            CCATLogger.ERROR_LOGGER.error("Error while deactivate stop MI threshold from ci service", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "CI-service[" + properties.getCiServiceUrls() + "]");
        }
        CCATLogger.DEBUG_LOGGER.debug("Ended SuperFlexProxy - deactivateStopMI()");
        CCATLogger.DEBUG_LOGGER.info("Finished deactivate stop MI threshold from ci service");
    }
}
