package com.asset.ccat.gateway.proxy.admin;


import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.admin_faf_plan.AddAdminFafPlanRequest;
import com.asset.ccat.gateway.models.requests.admin.admin_faf_plan.DeleteAdminFafPlanRequest;
import com.asset.ccat.gateway.models.requests.admin.admin_faf_plan.GetAllAdminFafPlansRequest;
import com.asset.ccat.gateway.models.requests.admin.admin_faf_plan.UpdateAdminFafPlanRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.shared.FafPlanModel;
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
public class AdminFafPlanServiceProxy {
    @Autowired
    WebClient webClient;

    @Autowired
    Properties properties;



    @LogExecutionTime
    public List<FafPlanModel> getAllFafPlans(GetAllAdminFafPlansRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Started AdminFafPlanServiceProxy - getAllFafPlans()");
        BaseResponse<List<FafPlanModel>> response = null;
        List<FafPlanModel> fafPlans = null;

        long start = 0;
        long executionTime;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");


            Mono<BaseResponse< List<FafPlanModel>>> responseAsync = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.FAF_PLANS
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse< List<FafPlanModel>>>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));;


            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    fafPlans = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving fafPlans list " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving  fafPlans list " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving fafPlans  list ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving fafPlans  list ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }

        return fafPlans;
    }


    @LogExecutionTime
    public BaseResponse updateFafPlan(UpdateAdminFafPlanRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Started AdminFafPlanServiceProxy - updateFafPlan");
        BaseResponse  response = null;
        long start = 0;
        long executionTime;

        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" +request+"]");

            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls() +
                            Defines.LOOKUP_SERVICE.CONTEXT_PATH +
                            Defines.LOOKUP_SERVICE.FAF_PLANS +
                            Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {

                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Updating fafPlans " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Updating fafPlans " + response);
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
            CCATLogger.DEBUG_LOGGER.info("Error while Updating FafPlans  ");
            CCATLogger.ERROR_LOGGER.error("Error while Updating FafPlans ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }


    @LogExecutionTime
    public BaseResponse deleteFafPlan(DeleteAdminFafPlanRequest request) throws  GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Started FafPlansProxy - deleteFafPlan");
        BaseResponse  response = null;
        long start = 0;
        long executionTime;

        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" +request+"]");

            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls() +
                            Defines.LOOKUP_SERVICE.CONTEXT_PATH +
                            Defines.LOOKUP_SERVICE.FAF_PLANS +
                            Defines.WEB_ACTIONS.DELETE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {

                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Deleting FafPlan " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Deleting FafPlan " + response);
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
            CCATLogger.DEBUG_LOGGER.info("Error while Deleting Deleting FafPlan");
            CCATLogger.ERROR_LOGGER.error("Error while Deleting FafPlan", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse addFafPlan(AddAdminFafPlanRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Started FafPlansProxy - addFafPlan");
        BaseResponse  response = null;
        long start = 0;
        long executionTime;

        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" +request+"]");

            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls() +
                            Defines.LOOKUP_SERVICE.CONTEXT_PATH +
                            Defines.LOOKUP_SERVICE.FAF_PLANS +
                            Defines.WEB_ACTIONS.ADD)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {

                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Adding FafPlan " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Adding FafPlan " + response);
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
            CCATLogger.DEBUG_LOGGER.info("Error while Adding FafPlan  ");
            CCATLogger.ERROR_LOGGER.error("Error while Adding FafPlan ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

}
