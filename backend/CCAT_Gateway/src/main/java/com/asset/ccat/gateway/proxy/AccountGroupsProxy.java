package com.asset.ccat.gateway.proxy;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.customer_care.AccountGroupModel;
import com.asset.ccat.gateway.models.requests.customer_care.account_group.GetAccountGroupsRequest;
import com.asset.ccat.gateway.models.requests.customer_care.account_group.UpdateAccountGroupRequest;
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
public class AccountGroupsProxy {

    @Autowired
    private WebClient webClient;

    @Autowired
    private Properties properties;

    @LogExecutionTime
    public AccountGroupModel getCurrentAccountGroup(GetAccountGroupsRequest request) throws GatewayException {

        CCATLogger.DEBUG_LOGGER.debug("Started AccountGroupsProxy - getCurrentAccountGroup()");
        BaseResponse<AccountGroupModel> response;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.info("Start getting current account group from air service");
            Mono<BaseResponse<AccountGroupModel>> responseAsync = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.ContextPaths.AIR_SERVICE_CONTEXT_PATH
                            + Defines.AIR_SERVICE.ACCOUNT_GROUP
                            + Defines.WEB_ACTIONS.GET
                    )
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<AccountGroupModel>>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while getting current account group from air service " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while getting current account group from air service" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while getting current account group from air service ");
            CCATLogger.ERROR_LOGGER.error("Error while getting current account group from air service", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "air-service[" + properties.getAirServiceUrls() + "]");
        }
        CCATLogger.DEBUG_LOGGER.debug("Ended AccountGroupsProxy - getCurrentAccountGroup()");
        CCATLogger.DEBUG_LOGGER.info("Finished getting current account group from air service");
        return response.getPayload();
    }

    @LogExecutionTime
    public void updateAccountGroup(UpdateAccountGroupRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started AccountGroupsProxy - updateAccountGroup()");
        BaseResponse response;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.info("Start updating current account group from air service");
            Mono<BaseResponse> responseAsync = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.ContextPaths.AIR_SERVICE_CONTEXT_PATH
                            + Defines.AIR_SERVICE.ACCOUNT_GROUP
                            + Defines.WEB_ACTIONS.UPDATE
                    )
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while updating current account group from air service " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while updating current account group from air service" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while updating current account group from air service ");
            CCATLogger.ERROR_LOGGER.error("Error while updating account group from air service", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "air-service[" + properties.getAirServiceUrls() + "]");
        }
        CCATLogger.DEBUG_LOGGER.debug("Ended AccountGroupsProxy - updateAccountGroup()");
        CCATLogger.DEBUG_LOGGER.info("Finished updating current account group from air service");
    }
}
