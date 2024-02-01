package com.asset.ccat.gateway.proxy;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.constants.FlexType;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.customer_care.flex_share.FlexShareInquiryResponse;
import com.asset.ccat.gateway.models.requests.customer_care.flex_share.GetFlexShareEligibilityRequest;
import com.asset.ccat.gateway.models.requests.customer_care.flex_share.GetFlexShareHistoryRequest;
import com.asset.ccat.gateway.models.requests.customer_care.flex_share.GetFlexShareStatesRequest;
import com.asset.ccat.gateway.models.requests.customer_care.flex_share.UpdateBWStateRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.customer_care.flex_share.GetFlexShareEligibilityResponse;
import com.asset.ccat.gateway.models.responses.customer_care.flex_share.GetFlexShareHistoryResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class FlexShareProxy {
    private final WebClient webClient;

    private final Properties properties;

    public FlexShareProxy(WebClient webClient, Properties properties) {
        this.webClient = webClient;
        this.properties = properties;
    }

    @LogExecutionTime
    public FlexShareInquiryResponse inquiry(GetFlexShareStatesRequest request) throws GatewayException {
        BaseResponse<FlexShareInquiryResponse> response;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is :" + request);
            CCATLogger.DEBUG_LOGGER.info("Start FLex Share Inquiry");
            String uri = properties.getProcedureServiceUrls() +
                    Defines.ContextPaths.PROCEDURE_SERVICE_CONTEXT_PATH +
                    Defines.ContextPaths.FLEX_SHARE +
                    Defines.WEB_ACTIONS.INQUIRY;
            Mono<BaseResponse<FlexShareInquiryResponse>> responseAsync = webClient.post()
                    .uri(uri)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<FlexShareInquiryResponse>>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while inquiring flex share " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while inquiring flex share" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while inquiring flex share ");
            CCATLogger.ERROR_LOGGER.error("Error while inquiring flex share ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "procedure-service[" + properties.getProcedureServiceUrls() + "]");
        }
        return response.getPayload();
    }

    @LogExecutionTime
    public void update(UpdateBWStateRequest request) throws GatewayException {
        BaseResponse response;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is :" + request);
            CCATLogger.DEBUG_LOGGER.info("Start FLex Share Update");
            String uri = properties.getProcedureServiceUrls() +
                    Defines.ContextPaths.PROCEDURE_SERVICE_CONTEXT_PATH +
                    Defines.ContextPaths.FLEX_SHARE +
                    Defines.WEB_ACTIONS.UPDATE;
            Mono<BaseResponse> responseAsync = webClient.post()
                    .uri(uri)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while updating flex share " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while updating flex share " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while updating flex share ");
            CCATLogger.ERROR_LOGGER.error("Error while updating flex share s ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "procedure-service[" + properties.getProcedureServiceUrls() + "]");
        }
    }


    @LogExecutionTime
    public GetFlexShareEligibilityResponse getEligibility(GetFlexShareEligibilityRequest request) throws GatewayException {
        BaseResponse<GetFlexShareEligibilityResponse> response;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is :" + request);
            CCATLogger.DEBUG_LOGGER.info("Start FLex Share getEligibility");
            String uri = properties.getCiServiceUrls() +
                    Defines.CI_SERVICE.CONTEXT_PATH +
                    Defines.ContextPaths.FLEX_SHARE +
                    Defines.ContextPaths.FLEX_SHARE_ELIGIBILITY +
                    Defines.WEB_ACTIONS.GET;
            Mono<BaseResponse<GetFlexShareEligibilityResponse>> responseAsync = webClient.post()
                    .uri(uri)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetFlexShareEligibilityResponse>>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while getting flex share eligibility " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while getting flex share eligibility " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while getting flex share eligibility ");
            CCATLogger.ERROR_LOGGER.error("Error while getting flex share eligibility ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "CI-service[" + properties.getCiServiceUrls() + "]");
        }
        return response.getPayload();
    }

    @LogExecutionTime
    public GetFlexShareHistoryResponse getFlexShareHistory(GetFlexShareHistoryRequest request) throws GatewayException {
        BaseResponse<GetFlexShareHistoryResponse> response;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is :" + request);
            CCATLogger.DEBUG_LOGGER.info("Start FLex Share getFlexShareHistory");
            String uri = properties.getOdsServiceUrls() +
                    Defines.ODS_SERVICE.CONTEXT_PATH +
                    Defines.ODS_SERVICE.FLEX_SHARE_HISTORY +
                    Defines.WEB_ACTIONS.GET_ALL;
            Mono<BaseResponse<GetFlexShareHistoryResponse>> responseAsync = webClient.post()
                    .uri(uri)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetFlexShareHistoryResponse>>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while getting flex share history " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while getting flex share history " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while getting flex share history ");
            CCATLogger.ERROR_LOGGER.error("Error while getting flex share history ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "ODS-service[" + properties.getOdsServiceUrls() + "]");
        }
        return response.getPayload();
    }

}
