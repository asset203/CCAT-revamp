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
import com.asset.ccat.gateway.models.admin.PamAdminstirationModel;
import com.asset.ccat.gateway.models.requests.admin.pam_admin.AddPamRequest;
import com.asset.ccat.gateway.models.requests.admin.pam_admin.DeletePamRequest;
import com.asset.ccat.gateway.models.requests.admin.pam_admin.GetAllPamsRequest;
import com.asset.ccat.gateway.models.requests.admin.pam_admin.UpdatePamRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 *
 * @author wael.mohamed
 */
@Component
public class PamProxy {

    @Autowired
    private WebClient webClient;

    @Autowired
    Properties properties;

    @LogExecutionTime
    public List<PamAdminstirationModel> getAllPamServices(GetAllPamsRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getAllPamServices - call lookup-service");
        List<PamAdminstirationModel> pamList = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The Lookup-Service....");
            Mono<BaseResponse<PamAdminstirationModel[]>> res = webClient
                    .post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.LOOKUPS
                            + Defines.LOOKUP_SERVICE.PAM_SERVICE
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<PamAdminstirationModel[]>>() {
                    }).log();
            BaseResponse<PamAdminstirationModel[]> response = res.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    pamList = Arrays.stream(response.getPayload()).collect(Collectors.toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving PamServices " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving PamServices " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            for (var pams : pamList) {
                CCATLogger.INTERFACE_LOGGER.info("response is ["
                        + "statusMessage: " + response.getStatusMessage()
                        + ", statusCode: " + response.getStatusCode()
                        + ", payload:["
                        + "pams:["
                        + "description: " + pams.getDescription()
                        + ", id: " + pams.getId()
                        + ", pamId: " + pams.getPamTypeId()
                        + "]"
                        + "]"
                        + "]");
            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving PamServices ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving PamServices ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return pamList;
    }

    @LogExecutionTime
    public List<PamAdminstirationModel> getAllPamClasses(GetAllPamsRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getAllPamClasses - call lookup-service");
        List<PamAdminstirationModel> pamList = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The Lookup-Service....");
            Mono<BaseResponse<PamAdminstirationModel[]>> res = webClient
                    .post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.LOOKUPS
                            + Defines.LOOKUP_SERVICE.PAM_CLASS
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<PamAdminstirationModel[]>>() {
                    }).log();
            BaseResponse<PamAdminstirationModel[]> response = res.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    pamList = Arrays.stream(response.getPayload()).collect(Collectors.toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving PamClasses " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving PamClasses " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            for (var pams : pamList) {
                CCATLogger.INTERFACE_LOGGER.info("response is ["
                        + "statusMessage: " + response.getStatusMessage()
                        + ", statusCode: " + response.getStatusCode()
                        + ", payload:["
                        + "pams:["
                        + "description: " + pams.getDescription()
                        + ", id: " + pams.getId()
                        + ", pamId: " + pams.getPamTypeId()
                        + "]"
                        + "]"
                        + "]");
            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving PamClasses ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving PamClasses ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return pamList;
    }

    @LogExecutionTime
    public List<PamAdminstirationModel> getAllPamSchedules(GetAllPamsRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getAllPamSchedules - call lookup-service");
        List<PamAdminstirationModel> pamList = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The Lookup-Service....");
            Mono<BaseResponse<PamAdminstirationModel[]>> res = webClient
                    .post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.LOOKUPS
                            + Defines.LOOKUP_SERVICE.PAM_SCHEDULE
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<PamAdminstirationModel[]>>() {
                    }).log();
            BaseResponse<PamAdminstirationModel[]> response = res.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    pamList = Arrays.stream(response.getPayload()).collect(Collectors.toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving PamSchedules " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving PamSchedules " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            for (var pams : pamList) {
                CCATLogger.INTERFACE_LOGGER.info("response is ["
                        + "statusMessage: " + response.getStatusMessage()
                        + ", statusCode: " + response.getStatusCode()
                        + ", payload:["
                        + "pams:["
                        + "description: " + pams.getDescription()
                        + ", id: " + pams.getId()
                        + ", pamId: " + pams.getPamTypeId()
                        + "]"
                        + "]"
                        + "]");
            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving PamSchedules ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving PamSchedules ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return pamList;
    }

    @LogExecutionTime
    public List<PamAdminstirationModel> getAllPamPeriods(GetAllPamsRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getAllPamPeriods - call lookup-service");
        List<PamAdminstirationModel> pamList = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The Lookup-Service....");
            Mono<BaseResponse<PamAdminstirationModel[]>> res = webClient
                    .post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.LOOKUPS
                            + Defines.LOOKUP_SERVICE.PAM_PERIOD
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<PamAdminstirationModel[]>>() {
                    }).log();
            BaseResponse<PamAdminstirationModel[]> response = res.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    pamList = Arrays.stream(response.getPayload()).collect(Collectors.toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving PamPeriods " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving PamPeriods " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            for (var pams : pamList) {
                CCATLogger.INTERFACE_LOGGER.info("response is ["
                        + "statusMessage: " + response.getStatusMessage()
                        + ", statusCode: " + response.getStatusCode()
                        + ", payload:["
                        + "pams:["
                        + "description: " + pams.getDescription()
                        + ", id: " + pams.getId()
                        + ", pamId: " + pams.getPamTypeId()
                        + "]"
                        + "]"
                        + "]");
            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving PamPeriods ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving PamPeriods ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return pamList;
    }

    @LogExecutionTime
    public List<PamAdminstirationModel> getAllPamPriorites(GetAllPamsRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getAllPamPriorites - call lookup-service");
        List<PamAdminstirationModel> pamList = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The Lookup-Service....");
            Mono<BaseResponse<PamAdminstirationModel[]>> res = webClient
                    .post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.LOOKUPS
                            + Defines.LOOKUP_SERVICE.PAM_PRIORITY
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<PamAdminstirationModel[]>>() {
                    }).log();
            BaseResponse<PamAdminstirationModel[]> response = res.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    pamList = Arrays.stream(response.getPayload()).collect(Collectors.toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving PamPriorites " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving PamPriorites " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            for (var pams : pamList) {
                CCATLogger.INTERFACE_LOGGER.info("response is ["
                        + "statusMessage: " + response.getStatusMessage()
                        + ", statusCode: " + response.getStatusCode()
                        + ", payload:["
                        + "pams:["
                        + "description: " + pams.getDescription()
                        + ", id: " + pams.getId()
                        + ", pamId: " + pams.getPamTypeId()
                        + "]"
                        + "]"
                        + "]");
            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving PamPriorites ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving PamPriorites ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return pamList;
    }

    @LogExecutionTime
    public BaseResponse addPamService(AddPamRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting addPamService - call lookup-service");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "pam[ "
                    + "id: " + request.getPam().getId()
                    + ", description: " + request.getPam().getDescription()
                    + ", pamTypeId: " + request.getPam().getPamTypeId()
                    + "]"
                    + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The Lookup-Service....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.LOOKUPS
                            + Defines.LOOKUP_SERVICE.PAM_SERVICE
                            + Defines.WEB_ACTIONS.ADD)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();

            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Adding PamService " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Adding PamService " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Adding PamService ");
            CCATLogger.ERROR_LOGGER.error("Error while Adding PamService", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse addPamClass(AddPamRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting addPamClass - call lookup-service");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "pam[ "
                    + "id: " + request.getPam().getId()
                    + ", description: " + request.getPam().getDescription()
                    + ", pamTypeId: " + request.getPam().getPamTypeId()
                    + "]"
                    + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The Lookup-Service....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.LOOKUPS
                            + Defines.LOOKUP_SERVICE.PAM_CLASS
                            + Defines.WEB_ACTIONS.ADD)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();

            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Adding PamClass " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Adding PamClass " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Adding PamClass ");
            CCATLogger.ERROR_LOGGER.error("Error while Adding PamClass", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse addPamSchedule(AddPamRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting addPamSchedule - call lookup-service");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "pam[ "
                    + "id: " + request.getPam().getId()
                    + ", description: " + request.getPam().getDescription()
                    + ", pamTypeId: " + request.getPam().getPamTypeId()
                    + "]"
                    + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The Lookup-Service....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.LOOKUPS
                            + Defines.LOOKUP_SERVICE.PAM_SCHEDULE
                            + Defines.WEB_ACTIONS.ADD)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();

            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Adding PamSchedule " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Adding PamSchedule " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Adding PamSchedule ");
            CCATLogger.ERROR_LOGGER.error("Error while Adding PamSchedule", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse addPamPeriod(AddPamRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting addPamPeriod - call lookup-service");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "pam[ "
                    + "id: " + request.getPam().getId()
                    + ", description: " + request.getPam().getDescription()
                    + ", pamTypeId: " + request.getPam().getPamTypeId()
                    + "]"
                    + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The Lookup-Service....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.LOOKUPS
                            + Defines.LOOKUP_SERVICE.PAM_PERIOD
                            + Defines.WEB_ACTIONS.ADD)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();

            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Adding PamPeriod " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Adding PamPeriod " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Adding PamPeriod ");
            CCATLogger.ERROR_LOGGER.error("Error while Adding PamPeriod", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse addPamPriority(AddPamRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting addPamPriority - call lookup-service");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "pam[ "
                    + "id: " + request.getPam().getId()
                    + ", description: " + request.getPam().getDescription()
                    + ", pamTypeId: " + request.getPam().getPamTypeId()
                    + "]"
                    + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The Lookup-Service....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.LOOKUPS
                            + Defines.LOOKUP_SERVICE.PAM_PRIORITY
                            + Defines.WEB_ACTIONS.ADD)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();

            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Adding PamPriority " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Adding PamPriority " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Adding PamPriority ");
            CCATLogger.ERROR_LOGGER.error("Error while Adding PamPriority", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse updatePamService(UpdatePamRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting updatePamService - call lookup-service");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "id: " + request.getId()
                    + ", description: " + request.getDescription()
                    + ", pamTypeId: " + request.getPamTypeId()
                    + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The Lookup-Service....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.LOOKUPS
                            + Defines.LOOKUP_SERVICE.PAM_SERVICE
                            + Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();

            response = res.block();

            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Updating PamService " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Updating PamService " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Updating PamService ");
            CCATLogger.ERROR_LOGGER.error("Error while Updating PamService", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse updatePamClass(UpdatePamRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting updatePamClass - call lookup-service");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "id: " + request.getId()
                    + ", description: " + request.getDescription()
                    + ", pamTypeId: " + request.getPamTypeId()
                    + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The Lookup-Service....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.LOOKUPS
                            + Defines.LOOKUP_SERVICE.PAM_CLASS
                            + Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();

            response = res.block();

            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Updating PamClass " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Updating PamClass " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Updating PamClass ");
            CCATLogger.ERROR_LOGGER.error("Error while Updating PamClass", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse updatePamSchedule(UpdatePamRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting updatePamSchedule - call lookup-service");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "id: " + request.getId()
                    + ", description: " + request.getDescription()
                    + ", pamTypeId: " + request.getPamTypeId()
                    + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The Lookup-Service....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.LOOKUPS
                            + Defines.LOOKUP_SERVICE.PAM_SCHEDULE
                            + Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();

            response = res.block();

            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Updating PamSchedule " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Updating PamSchedule " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Updating PamSchedule ");
            CCATLogger.ERROR_LOGGER.error("Error while Updating PamSchedule", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse updatePamPeriod(UpdatePamRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting updatePamPeriod - call lookup-service");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "id: " + request.getId()
                    + ", description: " + request.getDescription()
                    + ", pamTypeId: " + request.getPamTypeId()
                    + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The Lookup-Service....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.LOOKUPS
                            + Defines.LOOKUP_SERVICE.PAM_PERIOD
                            + Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();

            response = res.block();

            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Updating PamPeriod " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Updating PamPeriod " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Updating PamPeriod ");
            CCATLogger.ERROR_LOGGER.error("Error while Updating PamPeriod", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse updatePamPriority(UpdatePamRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting updatePamPriority - call lookup-service");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "id: " + request.getId()
                    + ", description: " + request.getDescription()
                    + ", pamTypeId: " + request.getPamTypeId()
                    + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The Lookup-Service....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.LOOKUPS
                            + Defines.LOOKUP_SERVICE.PAM_PRIORITY
                            + Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();

            response = res.block();

            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Updating PamPriority " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Updating PamPriority " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Updating PamPriority ");
            CCATLogger.ERROR_LOGGER.error("Error while Updating PamPriority", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse deletePamService(DeletePamRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting deletePamService - call lookup-service");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "id:" + request.getId() + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The Lookup-Service....");
            Mono<BaseResponse> res = webClient
                    .post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.LOOKUPS
                            + Defines.LOOKUP_SERVICE.PAM_SERVICE
                            + Defines.WEB_ACTIONS.DELETE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Deleting PamService " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Deleting PamService " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Deleting PamService ");
            CCATLogger.ERROR_LOGGER.error("Error while Deleting PamService", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse deletePamClass(DeletePamRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting deletePamClass - call lookup-service");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "id:" + request.getId() + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The Lookup-Service....");
            Mono<BaseResponse> res = webClient
                    .post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.LOOKUPS
                            + Defines.LOOKUP_SERVICE.PAM_CLASS
                            + Defines.WEB_ACTIONS.DELETE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Deleting PamClass " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Deleting PamClass " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Deleting PamClass ");
            CCATLogger.ERROR_LOGGER.error("Error while Deleting PamClass", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse deletePamSchedule(DeletePamRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting deletePamSchedule - call lookup-service");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "id:" + request.getId() + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The Lookup-Service....");
            Mono<BaseResponse> res = webClient
                    .post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.LOOKUPS
                            + Defines.LOOKUP_SERVICE.PAM_SCHEDULE
                            + Defines.WEB_ACTIONS.DELETE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Deleting PamSchedule " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Deleting PamSchedule " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Deleting PamSchedule ");
            CCATLogger.ERROR_LOGGER.error("Error while Deleting PamSchedule", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse deletePamPeriod(DeletePamRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting deletePamPeriod - call lookup-service");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "id:" + request.getId() + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The Lookup-Service....");
            Mono<BaseResponse> res = webClient
                    .post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.LOOKUPS
                            + Defines.LOOKUP_SERVICE.PAM_PERIOD
                            + Defines.WEB_ACTIONS.DELETE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Deleting PamPeriod " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Deleting PamPeriod " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Deleting PamPeriod ");
            CCATLogger.ERROR_LOGGER.error("Error while Deleting PamPeriod", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse deletePamPriority(DeletePamRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting deletePamPriority - call lookup-service");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "id:" + request.getId() + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The Lookup-Service....");
            Mono<BaseResponse> res = webClient
                    .post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.LOOKUPS
                            + Defines.LOOKUP_SERVICE.PAM_PRIORITY
                            + Defines.WEB_ACTIONS.DELETE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Deleting PamPriority " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Deleting PamPriority " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Deleting PamPriority ");
            CCATLogger.ERROR_LOGGER.error("Error while Deleting PamPriority", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

}
