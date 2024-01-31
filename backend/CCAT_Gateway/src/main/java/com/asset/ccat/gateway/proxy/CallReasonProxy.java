package com.asset.ccat.gateway.proxy;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.customer_care.CallReasonModel;
import com.asset.ccat.gateway.models.requests.customer_care.call_reasons.AddCallReasonRequest;
import com.asset.ccat.gateway.models.requests.customer_care.call_reasons.CheckCallReasonRequest;
import com.asset.ccat.gateway.models.requests.customer_care.call_reasons.UpdateCallReasonRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Objects;

@Repository
public class CallReasonProxy {

    private final WebClient webClient;

    private final Properties properties;

    public CallReasonProxy(WebClient webClient, Properties properties) {
        this.webClient = webClient;
        this.properties = properties;
    }

    @LogExecutionTime
    public CallReasonModel addCallReason(AddCallReasonRequest request) throws GatewayException {
        BaseResponse<CallReasonModel> response;
        try {
            CCATLogger.INTERFACE_LOGGER.info("CallReasonProxy -> addCallReason() : Started with request : " + request);

            String URI = properties.getUserManagementUrls()
                    + Defines.ContextPaths.USER_MANAGEMENT_CONTEXT_PATH
                    + Defines.ContextPaths.CALL_REASON
                    + Defines.WEB_ACTIONS.ADD;
            CCATLogger.DEBUG_LOGGER.info("Start adding call reason with URI : " + URI);
            Mono<BaseResponse<CallReasonModel>> responseAsync = webClient.post()
                    .uri(URI)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<CallReasonModel>>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (Objects.nonNull(response)) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while adding call reason " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while adding call reason" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage());
                }
            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while adding call reason ");
            CCATLogger.ERROR_LOGGER.error("Error while adding call reason  ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "user-management[" + properties.getUserManagementUrls() + "]");
        }
        return response.getPayload();
    }

    @LogExecutionTime
    public void updateCallReason(UpdateCallReasonRequest request) throws GatewayException {
        BaseResponse<Object> response;
        try {
            CCATLogger.INTERFACE_LOGGER.info("CallReasonProxy -> updateCallReason() : Started with request : " + request);

            String URI = properties.getUserManagementUrls()
                    + Defines.ContextPaths.USER_MANAGEMENT_CONTEXT_PATH
                    + Defines.ContextPaths.CALL_REASON
                    + Defines.WEB_ACTIONS.UPDATE;
            CCATLogger.DEBUG_LOGGER.info("Start updating call reason with URI : " + URI);
            Mono<BaseResponse<Object>> responseAsync = webClient.post()
                    .uri(URI)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<Object>>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (Objects.nonNull(response)) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while updating call reason " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while updating call reason" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while updating call reason ");
            CCATLogger.ERROR_LOGGER.error("Error while updating call reason  ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "user-management[" + properties.getUserManagementUrls() + "]");
        }
    }

    @LogExecutionTime
    public CallReasonModel checkCallReason(CheckCallReasonRequest request) throws GatewayException {
        BaseResponse<CallReasonModel> response;
        try {
            CCATLogger.INTERFACE_LOGGER.info("CallReasonProxy -> checkCallReason() : Started with request : " + request);

            String URI = properties.getUserManagementUrls()
                    + Defines.ContextPaths.USER_MANAGEMENT_CONTEXT_PATH
                    + Defines.ContextPaths.CALL_REASON
                    + Defines.WEB_ACTIONS.CHECK;
            CCATLogger.DEBUG_LOGGER.info("Start checking call reason with URI : " + URI);
            Mono<BaseResponse<CallReasonModel>> responseAsync = webClient.post()
                    .uri(URI)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<CallReasonModel>>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (Objects.nonNull(response)) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while checking call reason " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while checking call reason" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while updating call reason ");
            CCATLogger.ERROR_LOGGER.error("Error while updating call reason  ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "user-management[" + properties.getUserManagementUrls() + "]");
        }
        return response.getPayload();
    }
}
