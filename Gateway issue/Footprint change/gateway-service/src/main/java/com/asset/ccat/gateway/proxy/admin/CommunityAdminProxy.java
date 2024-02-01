package com.asset.ccat.gateway.proxy.admin;


import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.community_admin.AddCommunityAdminRequest;
import com.asset.ccat.gateway.models.requests.admin.community_admin.DeleteCommunityAdminRequest;
import com.asset.ccat.gateway.models.requests.admin.community_admin.GetAllCommunitiesRequest;
import com.asset.ccat.gateway.models.requests.admin.community_admin.UpdateCommunityAdminRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.shared.CommunitiesModel;
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
public class CommunityAdminProxy {


    @Autowired
    WebClient webClient;

    @Autowired
    Properties properties;


    @LogExecutionTime
    public List<CommunitiesModel> getAllCommunitiesAdmin(GetAllCommunitiesRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Started CommunityAdminProxy - getAllCommunitiesAdmin()");
        BaseResponse<List<CommunitiesModel>> response = null;
        List<CommunitiesModel> communities = null;
        long start = 0;
        long executionTime;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");


            Mono<BaseResponse<List<CommunitiesModel>>> responseAsync = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.COMMUNITIES
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<List<CommunitiesModel>>>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    communities = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving community admin  list " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving  community  admin list " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving community  admin  list ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving community  admin  list ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }

        return communities;
    }


    @LogExecutionTime
    public BaseResponse updateCommunityAdmin(UpdateCommunityAdminRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Started CommunityAdminProxy - updateCommunityAdmin");
        BaseResponse response = null;
        long start = 0;
        long executionTime;

        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");

            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls() +
                            Defines.LOOKUP_SERVICE.CONTEXT_PATH +
                            Defines.LOOKUP_SERVICE.COMMUNITIES +
                            Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {

                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Updating Community Admin " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Updating Community Admin " + response);
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
            CCATLogger.DEBUG_LOGGER.info("Error while Updating Community Admin  ");
            CCATLogger.ERROR_LOGGER.error("Error while Updating Community Admin ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse deleteCommunityAdmin(DeleteCommunityAdminRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Started CommunityAdminProxy - deleteCommunityAdmin");
        BaseResponse response = null;
        long start = 0;
        long executionTime;

        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");

            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls() +
                            Defines.LOOKUP_SERVICE.CONTEXT_PATH +
                            Defines.LOOKUP_SERVICE.COMMUNITIES +
                            Defines.WEB_ACTIONS.DELETE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {

                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Deleting Community Admin " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Deleting Community Admin" + response);
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
            CCATLogger.DEBUG_LOGGER.info("Error while Deleting Community Admin  ");
            CCATLogger.ERROR_LOGGER.error("Error while Deleting Community Admin ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse addCommunityAdmin(AddCommunityAdminRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Started CommunityAdminProxy - addCommunityAdmin");
        BaseResponse response = null;
        long start = 0;
        long executionTime;

        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");

            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls() +
                            Defines.LOOKUP_SERVICE.CONTEXT_PATH +
                            Defines.LOOKUP_SERVICE.COMMUNITIES +
                            Defines.WEB_ACTIONS.ADD)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {

                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Adding Community Admin " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Adding Community Admin " + response);
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
            CCATLogger.DEBUG_LOGGER.info("Error while Adding Community Admin  ");
            CCATLogger.ERROR_LOGGER.error("Error while Adding Community Admin ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

}
