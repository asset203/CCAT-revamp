package com.asset.ccat.gateway.proxy.admin;


import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.admin.ReasonActivityModel;
import com.asset.ccat.gateway.models.requests.admin.call_activity_admin.*;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Component
public class ReasonActivityProxy {


    @Autowired
    WebClient webClient;

    @Autowired
    Properties properties;


    @LogExecutionTime
    public List<ReasonActivityModel> getAllActivitiesReasons(GetAllActivitiesWithTypeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Started ReasonActivityProxy - getAllActivitiesReasons()");
        BaseResponse<List<ReasonActivityModel>> response = null;
        List<ReasonActivityModel> reasons = null;

        long start = 0;
        long executionTime;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");


            Mono<BaseResponse<List<ReasonActivityModel>>> responseAsync = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CALL_ACTIVITY_ADMIN
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<List<ReasonActivityModel>>>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            ;


            if (response != null) {

                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {

                    reasons = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving activity reasons " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving  activity reasons list " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving activity reasons ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving activity reasons ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }

        return reasons;
    }


    @LogExecutionTime
    public BaseResponse addReasonActivity(AddActivityReasonRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Started ReasonActivityProxy - addReasonActivity()");
        BaseResponse response = null;
        long start = 0;
        long executionTime;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");


            Mono<BaseResponse> responseAsync = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CALL_ACTIVITY_ADMIN
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
                    CCATLogger.DEBUG_LOGGER.info("Error while adding reason activity " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while adding reason activity " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while adding  reason activity ");
            CCATLogger.ERROR_LOGGER.error("Error while adding  reason activity", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }

        return response;
    }


    @LogExecutionTime
    public BaseResponse updateReasonActivity(UpdateReasonActivityRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Started ReasonActivityProxy - updateReasonActivity()");
        BaseResponse response = null;
        long start = 0;
        long executionTime;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");


            Mono<BaseResponse> responseAsync = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CALL_ACTIVITY_ADMIN
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
                    CCATLogger.DEBUG_LOGGER.info("Error while updating reason activity " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while updating reason activity " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while updating  reason activity ");
            CCATLogger.ERROR_LOGGER.error("Error while updating  reason activity", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }

        return response;
    }


    @LogExecutionTime
    public BaseResponse deleteReasonActivity(DeleteReasonActivityRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Started ReasonActivityProxy - deleteReasonActivity()");
        BaseResponse response = null;
        long start = 0;
        long executionTime;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");


            Mono<BaseResponse> responseAsync = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CALL_ACTIVITY_ADMIN
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
                    CCATLogger.DEBUG_LOGGER.info("Error while deleting reason activity " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while deleting reason activity " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while deleting  reason activity ");
            CCATLogger.ERROR_LOGGER.error("Error while deleting  reason activity", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }

        return response;
    }

    @LogExecutionTime
    public ResponseEntity<Resource> downloadActivities(DownloadActivitiesRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Started ReasonActivityProxy - downloadActivities()");
        ResponseEntity<Resource> response = null;
        long start = 0;
        long executionTime;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            Mono<ResponseEntity<Resource>> responseAsync = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CALL_ACTIVITY_ADMIN
                            + Defines.WEB_ACTIONS.DOWNLOAD)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .toEntity(Resource.class);
            ;
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
//
//            if (response != null) {
//
//                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
//                    CCATLogger.DEBUG_LOGGER.info("Error while download  activities " + response);
//                    CCATLogger.DEBUG_LOGGER.error("Error while download  activities " + response);
//                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
//                }
//            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while download  activities");
            CCATLogger.ERROR_LOGGER.error("Error while download  activities", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }

        return response;
    }


    @LogExecutionTime
    public BaseResponse uploadActivities(UploadActivitiesFileRequest uploadRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Started ReasonActivityProxy - uploadActivities()");
        BaseResponse response = null;
        long start = 0;
        long executionTime;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + uploadRequest + "]");

            MultipartBodyBuilder builder = new MultipartBodyBuilder();
            builder.part("file", uploadRequest.getFile().getResource());
            builder.part("fileName", uploadRequest.getFileName());
            builder.part("fileExt", uploadRequest.getFileExt());
            builder.part("requestId", uploadRequest.getRequestId());
            builder.part("sessionId", uploadRequest.getSessionId());
            builder.part("token", uploadRequest.getToken());

            Mono<BaseResponse> responseAsync = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CALL_ACTIVITY_ADMIN
                            + Defines.WEB_ACTIONS.UPLOAD)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                    .body(BodyInserters.fromMultipartData(builder.build()))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while upload  activities file " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while upload  activities file" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while upload  activities file");
            CCATLogger.ERROR_LOGGER.error("Error while upload  activities file", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }

        return response;
    }


}
