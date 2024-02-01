package com.asset.ccat.gateway.proxy.admin;


import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.admin.ServiceOfferingPlan;
import com.asset.ccat.gateway.models.requests.admin.service_offering_plans.*;
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
import java.util.List;

@Component
public class ServiceOfferingPlansProxy {

    @Autowired
    WebClient webClient;

    @Autowired
    Properties properties;


    @LogExecutionTime
    public List<ServiceOfferingPlan> getAllServiceOfferingPlans(GetAllServiceOfferingPlansRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Started ServiceOfferingPlansProxy - getAllServiceOfferingPlans()");
        BaseResponse<List<ServiceOfferingPlan>> response = null;
        List<ServiceOfferingPlan> plans = null;

        long start = 0;
        long executionTime;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");


            Mono<BaseResponse<List<ServiceOfferingPlan>>> responseAsync = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.SERVICE_OFFERING_PLANS
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<List<ServiceOfferingPlan>>>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            ;


            if (response != null) {

                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {

                    plans = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving service offering description list " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving service offering  bits description list " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving service offering plans  ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving service offering plans  ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }

        return plans;
    }


    @LogExecutionTime
    public ServiceOfferingPlan getServiceOfferingPlanBits(GetServiceOfferingPlanBitsRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Started ServiceOfferingPlansProxy - getServiceOfferingPlanBits()");
        BaseResponse<ServiceOfferingPlan> response = null;
        ServiceOfferingPlan plans = null;

        long start = 0;
        long executionTime;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");


            Mono<BaseResponse<ServiceOfferingPlan>> responseAsync = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.SERVICE_OFFERING_PLANS
                            + Defines.WEB_ACTIONS.GET)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<ServiceOfferingPlan>>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            ;


            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    plans = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving service offering plan bits  " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving service offering plan bits   " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving service offering plan bits  ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving service offering plan bits  ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }

        return plans;
    }


    @LogExecutionTime
    public BaseResponse addServiceOfferingPlan(AddServiceOfferingPlanRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Started ServiceOfferingPlansProxy - addServiceOfferingPlan()");
        BaseResponse response = null;
        long start = 0;
        long executionTime;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");


            Mono<BaseResponse> responseAsync = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.SERVICE_OFFERING_PLANS
                            + Defines.WEB_ACTIONS.ADD)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            ;


            if (response != null) {

                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while adding service offering plan " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while adding service offering plan " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while adding service offering plan ");
            CCATLogger.ERROR_LOGGER.error("Error while adding service offering plan  ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }

        return response;
    }

    @LogExecutionTime
    public BaseResponse updateServiceOfferingPlan(UpdateServiceOfferingPlanRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Started ServiceOfferingPlansProxy - updateServiceOfferingPlan()");
        BaseResponse response = null;
        long start = 0;
        long executionTime;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");


            Mono<BaseResponse> responseAsync = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.SERVICE_OFFERING_PLANS
                            + Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            ;


            if (response != null) {

                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while updating service offering plan " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while updating service offering plan " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while updating service offering plan ");
            CCATLogger.ERROR_LOGGER.error("Error while updating service offering plan  ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }

        return response;
    }

    @LogExecutionTime
    public BaseResponse deleteServiceOfferingPlan(DeleteServiceOfferingPlanRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Started ServiceOfferingPlansProxy - deleteServiceOfferingPlan()");
        BaseResponse response = null;
        long start = 0;
        long executionTime;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");


            Mono<BaseResponse> responseAsync = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.SERVICE_OFFERING_PLANS
                            + Defines.WEB_ACTIONS.DELETE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            ;


            if (response != null) {

                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while deleting service offering plan " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while deleting service offering plan " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while deleting service offering plan ");
            CCATLogger.ERROR_LOGGER.error("Error while deleting service offering plan  ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }

        return response;
    }


//    @LogExecutionTime
//    public ServiceOfferingPlan getAllServiceClassPlanDesc(GetServiceClassServiceOfferingPlanDescs request) throws GatewayException {
//        CCATLogger.DEBUG_LOGGER.info("Started ServiceOfferingPlansProxy - getAllServiceClassPlanDesc()");
//        BaseResponse<ServiceOfferingPlan> response = null;
//        ServiceOfferingPlan descriptions = null;
//
//        long start = 0;
//        long executionTime;
//        try {
//            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
//
//
//            Mono<BaseResponse<ServiceOfferingPlan>> responseAsync = webClient.post()
//                    .uri(properties.getLookupsServiceUrls()
//                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
//                            + Defines.LOOKUP_SERVICE.SERVICE_OFFERING_PLANS
//                            + Defines.LOOKUP_SERVICE.SO_SC_DESC
//                            + Defines.WEB_ACTIONS.GET)
//                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                    .body(BodyInserters.fromValue(request))
//                    .retrieve()
//                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<ServiceOfferingPlan>>() {
//                    }).log();
//            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
//            ;
//
//
//            if (response != null) {
//                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
//                    descriptions = response.getPayload();
//                } else {
//                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving service class plan descriptions  " + response);
//                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving service class plan descriptions" + response);
//                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage());
//                }
//            }
//            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
//            executionTime = System.currentTimeMillis() - start;
//            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
//        } catch (RuntimeException ex) {
//            CCATLogger.DEBUG_LOGGER.info("Error while retrieving service class plan descriptions ");
//            CCATLogger.ERROR_LOGGER.error("Error while retrieving service class plan descriptions", ex);
//            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
//        }
//
//        return descriptions;
//    }
//
//
//    @LogExecutionTime
//    public BaseResponse addServiceClassPlanDesc(AddServiceClassPlanDescriptionRequest request) throws GatewayException {
//        CCATLogger.DEBUG_LOGGER.info("Started ServiceOfferingPlansProxy - addServiceClassPlanDesc()");
//        BaseResponse response = null;
//
//
//        long start = 0;
//        long executionTime;
//        try {
//            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
//
//
//            Mono<BaseResponse> responseAsync = webClient.post()
//                    .uri(properties.getLookupsServiceUrls()
//                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
//                            + Defines.LOOKUP_SERVICE.SERVICE_OFFERING_PLANS
//                            + Defines.LOOKUP_SERVICE.SO_SC_DESC
//                            + Defines.WEB_ACTIONS.ADD)
//                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                    .body(BodyInserters.fromValue(request))
//                    .retrieve()
//                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
//                    }).log();
//            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
//            ;
//
//
//            if (response != null) {
//
//                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
//                    CCATLogger.DEBUG_LOGGER.info("Error while adding service class plan description" + response);
//                    CCATLogger.DEBUG_LOGGER.error("Error while adding service class plan description" + response);
//                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
//                }
//            }
//            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
//            executionTime = System.currentTimeMillis() - start;
//            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
//        } catch (RuntimeException ex) {
//            CCATLogger.DEBUG_LOGGER.info("Error while adding service class plan description");
//            CCATLogger.ERROR_LOGGER.error("Error while adding service class plan description", ex);
//            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
//        }
//
//        return response;
//    }
//
//    @LogExecutionTime
//    public BaseResponse updateServiceClassPlanDesc(UpdateServiceClassPlanDescriptionRequest request) throws GatewayException {
//        CCATLogger.DEBUG_LOGGER.info("Started ServiceOfferingPlansProxy - updateServiceClassPlanDesc()");
//        BaseResponse response = null;
//
//
//        long start = 0;
//        long executionTime;
//        try {
//            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
//
//
//            Mono<BaseResponse> responseAsync = webClient.post()
//                    .uri(properties.getLookupsServiceUrls()
//                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
//                            + Defines.LOOKUP_SERVICE.SERVICE_OFFERING_PLANS
//                            + Defines.LOOKUP_SERVICE.SO_SC_DESC
//                            + Defines.WEB_ACTIONS.UPDATE)
//                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                    .body(BodyInserters.fromValue(request))
//                    .retrieve()
//                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
//                    }).log();
//            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
//            ;
//
//
//            if (response != null) {
//
//                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
//                    CCATLogger.DEBUG_LOGGER.info("Error while updating service class plan description" + response);
//                    CCATLogger.DEBUG_LOGGER.error("Error while updating service class plan description" + response);
//                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
//                }
//            }
//            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
//            executionTime = System.currentTimeMillis() - start;
//            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
//        } catch (RuntimeException ex) {
//            CCATLogger.DEBUG_LOGGER.info("Error while updating service class plan description");
//            CCATLogger.ERROR_LOGGER.error("Error while updating service class plan description", ex);
//            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
//        }
//
//        return response;
//    }
//
//
//    @LogExecutionTime
//    public BaseResponse deleteServiceClassPlanDesc(DeleteServiceClassPlanDescriptionRequest request) throws GatewayException {
//        CCATLogger.DEBUG_LOGGER.info("Started ServiceOfferingPlansProxy - deleteServiceClassPlanDesc()");
//        BaseResponse response = null;
//
//
//        long start = 0;
//        long executionTime;
//        try {
//            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
//
//
//            Mono<BaseResponse> responseAsync = webClient.post()
//                    .uri(properties.getLookupsServiceUrls()
//                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
//                            + Defines.LOOKUP_SERVICE.SERVICE_OFFERING_PLANS
//                            + Defines.LOOKUP_SERVICE.SO_SC_DESC
//                            + Defines.WEB_ACTIONS.DELETE)
//                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                    .body(BodyInserters.fromValue(request))
//                    .retrieve()
//                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
//                    }).log();
//            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
//            ;
//
//
//            if (response != null) {
//
//                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
//                    CCATLogger.DEBUG_LOGGER.info("Error while deleting service class plan description" + response);
//                    CCATLogger.DEBUG_LOGGER.error("Error while deleting service class plan description" + response);
//                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
//                }
//            }
//            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
//            executionTime = System.currentTimeMillis() - start;
//            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
//        } catch (RuntimeException ex) {
//            CCATLogger.DEBUG_LOGGER.info("Error while deleting service class plan description");
//            CCATLogger.ERROR_LOGGER.error("Error while deleting service class plan description", ex);
//            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
//        }
//
//        return response;
//    }

}
