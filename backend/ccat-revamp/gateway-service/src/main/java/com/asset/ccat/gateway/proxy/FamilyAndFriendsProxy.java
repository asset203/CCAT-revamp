package com.asset.ccat.gateway.proxy;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.customer_care.FamilyAndFriendsModel;
import com.asset.ccat.gateway.models.requests.SubscriberRequest;
import com.asset.ccat.gateway.models.requests.customer_care.family_and_friends.AddFamilyAndFriendsRequest;
import com.asset.ccat.gateway.models.requests.customer_care.family_and_friends.DeleteFamilyAndFriendsRequest;
import com.asset.ccat.gateway.models.requests.customer_care.family_and_friends.UpdateFamilyAndFriendsRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class FamilyAndFriendsProxy {

    @Autowired
    private WebClient webClient;

    @Autowired
    private Properties properties;

    @LogExecutionTime
    public List<FamilyAndFriendsModel> getFafList(SubscriberRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started FamilyAndFriendsProxy - getFafList()");
        BaseResponse<List<FamilyAndFriendsModel>> response;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "msisdn:" + request.getMsisdn() + "]");
            CCATLogger.DEBUG_LOGGER.info("Start getting FAF list");
            Mono<BaseResponse<List<FamilyAndFriendsModel>>> responseAsync = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.ContextPaths.AIR_SERVICE_CONTEXT_PATH
                            + Defines.AIR_SERVICE.FAMILY_AND_FRIENDS
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<List<FamilyAndFriendsModel>>>() {
                    }).log();
            response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while getting FAF list " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while getting getting FAF list" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while getting FAF list ");
            CCATLogger.ERROR_LOGGER.error("Error while getting FAF list", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "air-service[" + properties.getAirServiceUrls() + "]");
        }
        CCATLogger.DEBUG_LOGGER.debug("Ended FamilyAndFriendsProxy - getFafList()");
        return response.getPayload();
    }

    @LogExecutionTime
    public void addFafList(AddFamilyAndFriendsRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started FamilyAndFriendsProxy - addFafList()");
        BaseResponse response;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "msisdn:" + request.getMsisdn()
                    + ", familyAndFriendsNumber:" + request.getFamilyAndFriendsNumber()
                    + ", familyAndFriendsPlanId:" + request.getFamilyAndFriendsPlanId()
                    + "]");
            CCATLogger.DEBUG_LOGGER.info("Start adding family and friends");
            Mono<BaseResponse> responseAsync = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.ContextPaths.AIR_SERVICE_CONTEXT_PATH
                            + Defines.AIR_SERVICE.FAMILY_AND_FRIENDS
                            + Defines.WEB_ACTIONS.ADD)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while adding family and friends " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while adding family and friends" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + "]");
            CCATLogger.DEBUG_LOGGER.debug("Ended FamilyAndFriendsProxy - addFafList()");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while adding family and friends");
            CCATLogger.ERROR_LOGGER.error("Error while adding family and friends ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "air-service[" + properties.getAirServiceUrls() + "]");
        }
    }

    @LogExecutionTime
    public void updateFafList(UpdateFamilyAndFriendsRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started FamilyAndFriendsProxy - updateFafList()");
        BaseResponse response;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "msisdn:" + request.getMsisdn()
                    + ", familyAndFriendsNumber:" + request.getFamilyAndFriendsNumber()
                    + ", familyAndFriendsPlanId:" + request.getFamilyAndFriendsPlanId()
                    + "]");
            CCATLogger.DEBUG_LOGGER.info("Start updating family and friends");
            Mono<BaseResponse> responseAsync = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.ContextPaths.AIR_SERVICE_CONTEXT_PATH
                            + Defines.AIR_SERVICE.FAMILY_AND_FRIENDS
                            + Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while updating family and friends " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while updating family and friends" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + "]");
            CCATLogger.DEBUG_LOGGER.debug("Ended FamilyAndFriendsProxy - updateFafList()");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while updating family and friends");
            CCATLogger.ERROR_LOGGER.error("Error while updating family and friends ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "air-service[" + properties.getAirServiceUrls() + "]");
        }
    }

    @LogExecutionTime
    public void deleteFafList(DeleteFamilyAndFriendsRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started FamilyAndFriendsProxy - deleteFafList()");
        BaseResponse response;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "msisdn:" + request.getMsisdn()
                    + ", familyAndFriendsNumber:" + request.getFamilyAndFriendsNumber()
                    + ", familyAndFriendsPlanId:" + request.getFamilyAndFriendsPlanId()
                    + "]");
            CCATLogger.DEBUG_LOGGER.info("Start deleting family and friends");
            Mono<BaseResponse> responseAsync = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.ContextPaths.AIR_SERVICE_CONTEXT_PATH
                            + Defines.AIR_SERVICE.FAMILY_AND_FRIENDS
                            + Defines.WEB_ACTIONS.DELETE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while deleting family and friends " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while deleting family and friends" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + "]");
            CCATLogger.DEBUG_LOGGER.debug("Ended FamilyAndFriendsProxy - deleteFafList()");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while deleting family and friends");
            CCATLogger.ERROR_LOGGER.error("Error while deleting family and friends ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "air-service[" + properties.getAirServiceUrls() + "]");
        }
    }
}
