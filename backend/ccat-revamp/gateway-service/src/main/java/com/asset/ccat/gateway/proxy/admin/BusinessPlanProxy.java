/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.proxy.admin;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.business_plan.AddBusinessPlanRequest;
import com.asset.ccat.gateway.models.requests.admin.business_plan.DeleteBusinessPlanRequest;
import com.asset.ccat.gateway.models.requests.admin.business_plan.UpdateBusinessPlanRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.business_plan.GetAllBusinessPlansResponse;
import com.asset.ccat.gateway.models.responses.admin.business_plan.GetBusinessPlanResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author wael.mohamed
 */
@Component
public class BusinessPlanProxy {

    @Autowired
    WebClient webClient;

    @Autowired
    Properties properties;

    @LogExecutionTime
    public GetAllBusinessPlansResponse getAllBusinessPlans() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getAllBusinessPlans - call lookup-service");
        GetAllBusinessPlansResponse getAllBusinessPlansResponse = null;
        try {
            CCATLogger.DEBUG_LOGGER.debug("Calling The lookup-service....");
            Mono<BaseResponse<GetAllBusinessPlansResponse>> res = webClient
                    .get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.BUSINESS_PLANS
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetAllBusinessPlansResponse>>() {
                    }).log();
            BaseResponse<GetAllBusinessPlansResponse> response = res.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    getAllBusinessPlansResponse = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving BusinessPlans " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving BusinessPlans " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            for (var businessPlans : getAllBusinessPlansResponse.getBusinessPlans()) {
                CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                        + ", statusCode: " + response.getStatusCode()
                        + ", payload: " + response.getPayload()
                        + ", payload:["
                        + ", businessPlans:["
                        + ", name: " + businessPlans.getBusinessPlanName()
                        + ", code: " + businessPlans.getBusinessPlanCode()
                        + ", id: " + businessPlans.getBusinessPlanId()
                        + ", hlrProfileId: " + businessPlans.getHlrProfileId()
                        + ", serviceClassId: " + businessPlans.getServiceClassId()
                        + "]"
                        + "]"
                        + "]");
            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving BusinessPlans ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving BusinessPlans ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service [" + properties.getLookupsServiceUrls() + "]");
        }
        return getAllBusinessPlansResponse;
    }

    @LogExecutionTime
    public GetBusinessPlanResponse getBusinessPlansById(Integer businessPlanId) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getBusinessPlansById - call lookup-service");
        GetBusinessPlanResponse businessPlan = null;

        try {

            CCATLogger.INTERFACE_LOGGER.info("request is [" + "businessPlanId:" + businessPlanId + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The lookup-service....");
            Mono<BaseResponse<GetBusinessPlanResponse>> res = webClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path(properties.getLookupsServiceUrls()
                                    + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                                    + Defines.LOOKUP_SERVICE.BUSINESS_PLANS
                                    + Defines.WEB_ACTIONS.GET)
                            .queryParam("businessPlanId", businessPlanId)
                            .build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetBusinessPlanResponse>>() {
                    }).log();
            BaseResponse<GetBusinessPlanResponse> response = res.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    businessPlan = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving businessPlan " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving businessPlan " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + ", payload:["
                    + ", businessPlans:["
                    + ", name: " + businessPlan.getBusinessPlan().getBusinessPlanName()
                    + ", code: " + businessPlan.getBusinessPlan().getBusinessPlanCode()
                    + ", id: " + businessPlan.getBusinessPlan().getBusinessPlanId()
                    + ", hlrProfileId: " + businessPlan.getBusinessPlan().getHlrProfileId()
                    + ", serviceClassId: " + businessPlan.getBusinessPlan().getServiceClassId()
                    + "]"
                    + "]"
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving businessPlan ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving businessPlan ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return businessPlan;
    }

    @LogExecutionTime
    public BaseResponse addBusinessPlan(AddBusinessPlanRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting addBusinessPlan - call lookup-service");
        BaseResponse response = null;
        try {

            CCATLogger.INTERFACE_LOGGER.info("request is [" + "name: " + request.getBusinessPlan().getBusinessPlanName()
                    + ", code: " + request.getBusinessPlan().getBusinessPlanCode()
                    + ", id: " + request.getBusinessPlan().getBusinessPlanId()
                    + ", hlrProfileId: " + request.getBusinessPlan().getHlrProfileId()
                    + ", serviceClassId: " + request.getBusinessPlan().getServiceClassId()
                    + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The lookup-service....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.BUSINESS_PLANS
                            + Defines.WEB_ACTIONS.ADD)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();

            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Adding Offer " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Adding Offer " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Adding Offer ");
            CCATLogger.ERROR_LOGGER.error("Error while Adding Offer", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse updateBusinessPlan(UpdateBusinessPlanRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting updateOffer - call lookup-service");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "name: " + request.getBusinessPlan().getBusinessPlanName()
                    + ", code: " + request.getBusinessPlan().getBusinessPlanCode()
                    + ", id: " + request.getBusinessPlan().getBusinessPlanId()
                    + ", hlrProfileId: " + request.getBusinessPlan().getHlrProfileId()
                    + ", serviceClassId: " + request.getBusinessPlan().getServiceClassId()
                    + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The lookup-service....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.BUSINESS_PLANS
                            + Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();

            response = res.block();

            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Updating Offer " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Updating Offer " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Updating Offer ");
            CCATLogger.ERROR_LOGGER.error("Error while Updating Offer", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse deleteBusinessPlanById(DeleteBusinessPlanRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting deleteBusinessPlan - call lookup-service");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "businessPlanId:" + request.getBusinessPlanId() + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The lookup-service....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.BUSINESS_PLANS
                            + Defines.WEB_ACTIONS.DELETE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Deleting BusinessPlan " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Deleting BusinessPlan " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Deleting BusinessPlan ");
            CCATLogger.ERROR_LOGGER.error("Error while Deleting BusinessPlan", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

}
