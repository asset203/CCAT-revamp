package com.asset.ccat.gateway.proxy.admin;


import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.account_groups.AddAccountGroupRequest;
import com.asset.ccat.gateway.models.requests.admin.account_groups.DeleteAccountGroupRequest;
import com.asset.ccat.gateway.models.requests.admin.account_groups.GetAllAccountGroupsRequest;
import com.asset.ccat.gateway.models.requests.admin.account_groups.UpdateAccountGroupRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;

import com.asset.ccat.gateway.models.responses.admin.account_groups.GetAllAccountGroupsResponse;
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
public class AdminAccountGroupsProxy {

    @Autowired
    WebClient webClient;

    @Autowired
    Properties properties;


    @LogExecutionTime
    public GetAllAccountGroupsResponse getAllAccountGroups(GetAllAccountGroupsRequest getAllAccountGroupsRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Started AccountGroupsProxy - getAllAccountGroups()");
        BaseResponse<GetAllAccountGroupsResponse> response = null;
        GetAllAccountGroupsResponse getAllAccountGroupsResponse = null;
        long start = 0;
        long executionTime;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + getAllAccountGroupsRequest + "]");


            Mono<BaseResponse<GetAllAccountGroupsResponse>> responseAsync = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.ACCOUNT_GROUPS
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(getAllAccountGroupsRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetAllAccountGroupsResponse>>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            ;


            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    getAllAccountGroupsResponse = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving account groups  list " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving  account groups list " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving account groups  list ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving account groups  list ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }

        return getAllAccountGroupsResponse;
    }


    @LogExecutionTime
    public BaseResponse updateAccountGroup(UpdateAccountGroupRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Started AccountGroupsProxy - updateAccountGroup");
        BaseResponse response = null;
        long start = 0;
        long executionTime;

        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");

            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls() +
                            Defines.LOOKUP_SERVICE.CONTEXT_PATH +
                            Defines.LOOKUP_SERVICE.ACCOUNT_GROUPS +
                            Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {

                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Updating Account groups " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Updating Account groups " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "executed in " + executionTime + "ms"
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Updating Account groups  ");
            CCATLogger.ERROR_LOGGER.error("Error while Updating Account groups ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse deleteAccountGroup(DeleteAccountGroupRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Started AccountGroupsProxy - deleteAccountGroup");
        BaseResponse response = null;
        long start = 0;
        long executionTime;

        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");

            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls() +
                            Defines.LOOKUP_SERVICE.CONTEXT_PATH +
                            Defines.LOOKUP_SERVICE.ACCOUNT_GROUPS +
                            Defines.WEB_ACTIONS.DELETE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {

                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Deleting Account groups " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Deleting Account groups " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "executed in " + executionTime + "ms"
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Deleting Account groups  ");
            CCATLogger.ERROR_LOGGER.error("Error while Deleting Account groups ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse addAccountGroup(AddAccountGroupRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Started AccountGroupsProxy - addAccountGroup");
        BaseResponse response = null;
        long start = 0;
        long executionTime;

        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");

            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls() +
                            Defines.LOOKUP_SERVICE.CONTEXT_PATH +
                            Defines.LOOKUP_SERVICE.ACCOUNT_GROUPS +
                            Defines.WEB_ACTIONS.ADD)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {

                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Adding Account group " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Adding Account group " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "executed in " + executionTime + "ms"
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Adding Account group  ");
            CCATLogger.ERROR_LOGGER.error("Error while Adding Account group ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

}
