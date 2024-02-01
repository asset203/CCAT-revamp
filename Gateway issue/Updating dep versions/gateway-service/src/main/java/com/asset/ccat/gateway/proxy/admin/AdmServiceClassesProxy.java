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
import com.asset.ccat.gateway.models.admin.AdmServiceClassModel;
import com.asset.ccat.gateway.models.requests.admin.service_class.AddServiceClassRequest;
import com.asset.ccat.gateway.models.requests.admin.service_class.UpdateServiceClassRequest;
import com.asset.ccat.gateway.models.requests.service_class.ImportServiceClassesLKRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.service_class.ImportServiceClassesResponse;
import com.asset.ccat.gateway.models.responses.admin.service_class.ServiceClassResponse;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.asset.ccat.gateway.models.responses.admin.migration.ServiceClassesMigrationResponse;
import com.asset.ccat.gateway.models.shared.ServiceClassesMigrationSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@Qualifier("AdmServiceClassesProxy")
public class AdmServiceClassesProxy {

    @Autowired
    WebClient webClient;

    @Autowired
    Properties properties;

    @LogExecutionTime
    public List<ServiceClassResponse> getAllServiceClasses() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("getAllServiceClasses Started successfully.");
        List<ServiceClassResponse> list = null;
        try {
            Mono<BaseResponse<ServiceClassResponse[]>> responseAsync = webClient
                    .get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.SERVICE_CLASSES
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<ServiceClassResponse[]>>() {
                    }).log();

            BaseResponse<ServiceClassResponse[]> response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    list = Arrays.stream(response.getPayload()).collect(Collectors.toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("getAllServiceClasses() Ended  with "
                            + "| Exception [ statusCode : " + response.getStatusCode() + ", statusMessage : " + response.getStatusMessage() + "].");
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving getAllServiceClasses " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }

            }
            for (var serviceClassess : list) {
                CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                        + ", statusCode: " + response.getStatusCode()
                        + ", payload: " + response.getPayload()
                        + ", payload:["
                        + ", serviceClassess:["
                        + ", code: " + serviceClassess.getCode()
                        + ", name: " + serviceClassess.getName()
                        + "]"
                        + "]"
                        + "]");
            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving service classes from [lookup-service] ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving service classes from [lookup-service] ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service");
        }
        CCATLogger.DEBUG_LOGGER.debug("getAllServiceClasses() Ended successfully.");
        return list;
    }

    @LogExecutionTime
    public AdmServiceClassModel getServiceClassById(Integer serviceClassId) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("getServiceClassById [ " + serviceClassId + " ] Started successfully.");
        AdmServiceClassModel serviceClass = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + serviceClassId + "]");
            Mono<BaseResponse<AdmServiceClassModel>> responseAsync = WebClient
                    .create(properties.getLookupsServiceUrls())
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path(Defines.LOOKUP_SERVICE.CONTEXT_PATH
                                    + Defines.LOOKUP_SERVICE.SERVICE_CLASSES
                                    + Defines.WEB_ACTIONS.GET)
                            .queryParam("serviceClassID", serviceClassId).build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<AdmServiceClassModel>>() {
                    }).log();

            BaseResponse<AdmServiceClassModel> response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    serviceClass = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("getServiceClassById() Ended  with "
                            + "| Exception [ statusCode : " + response.getStatusCode() + ", statusMessage : " + response.getStatusMessage() + "].");
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving getServiceClassById " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }

            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + ", payload:["
                    + ", serviceClassess:["
                    + ", code: " + serviceClass.getCode()
                    + ", name: " + serviceClass.getName()
                    + "]"
                    + "]"
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving service classes from [lookup-service] ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving service classes from [lookup-service] ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service");
        }
        CCATLogger.DEBUG_LOGGER.debug("getAllServiceClasses() Ended successfully.");
        return serviceClass;
    }

    @LogExecutionTime
    public void updateServiceClass(UpdateServiceClassRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("UpdateServiceClass() Started successfully.");
        BaseResponse response = null;
        try {

            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");

            Mono<BaseResponse> responseAsync = webClient
                    .post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.SERVICE_CLASSES
                            + Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = responseAsync.block();
            if (response != null) {
                CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                        + ", statusCode: " + response.getStatusCode()
                        + ", payload: " + response.getPayload()
                        + "]");
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while update service class " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while update service class " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("updateServiceClass() Ended  with "
                            + "| Exception [ statusCode : " + response.getStatusCode() + ", statusMessage : " + response.getStatusMessage() + "].");
                    CCATLogger.DEBUG_LOGGER.info("Error while update Service Class " + response);
                }
            }

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving update service class ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving update service class ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service");
        }
    }

    @LogExecutionTime
    public void addServiceClass(AddServiceClassRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("addServiceClass() Started successfully.");
        BaseResponse response = null;
        try {

            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");

            Mono<BaseResponse> responseAsync = webClient
                    .post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.SERVICE_CLASSES
                            + Defines.WEB_ACTIONS.ADD)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = responseAsync.block();
            if (response != null) {
                CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                        + ", statusCode: " + response.getStatusCode()
                        + ", payload: " + response.getPayload()
                        + "]");

                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while add service class " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while add service class " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("addServiceClass() Ended  with "
                            + "| Exception [ statusCode : " + response.getStatusCode() + ", statusMessage : " + response.getStatusMessage() + "].");
                    CCATLogger.DEBUG_LOGGER.info("Error while add Service Class " + response);
                }
            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving add service class ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving add service class ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service");
        }
    }

    @LogExecutionTime
    public void deleteServiceClassById(Integer serviceClassId) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("deleteServiceClassById Started successfully.");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + serviceClassId + "]");
            Mono<BaseResponse> responseAsync = WebClient.create(properties.getLookupsServiceUrls())
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path(Defines.LOOKUP_SERVICE.CONTEXT_PATH
                                    + Defines.LOOKUP_SERVICE.SERVICE_CLASSES
                                    + Defines.WEB_ACTIONS.DELETE)
                            .queryParam("serviceClassID", serviceClassId).build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();

            response = responseAsync.block();
            if (response != null) {
                CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                        + ", statusCode: " + response.getStatusCode()
                        + ", payload: " + response.getPayload()
                        + "]");
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("deleteServiceClassById() Ended  with "
                            + "| Exception [ statusCode : " + response.getStatusCode() + ", statusMessage : " + response.getStatusMessage() + "].");
                    CCATLogger.DEBUG_LOGGER.info("Error while deleting deleteServiceClassById " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while deleting service classes from [lookup-service] ");
            CCATLogger.ERROR_LOGGER.error("Error while deleting service classes from [lookup-service] ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service");
        }
        CCATLogger.DEBUG_LOGGER.debug("deleteServiceClassById() Ended successfully.");
    }

    @LogExecutionTime
    public ServiceClassesMigrationResponse exportAllServiceClassesTables() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("exportAllServiceClassesToFile Started successfully.");
        ServiceClassesMigrationResponse migrationResponse = null;
        try {
            Mono<BaseResponse<ServiceClassesMigrationResponse>> responseAsync = webClient
                    .get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.SERVICE_CLASSES
                            + Defines.WEB_ACTIONS.EXPORT)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<ServiceClassesMigrationResponse>>() {
                    }).log();

            BaseResponse<ServiceClassesMigrationResponse> response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    migrationResponse = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("exportAllServiceClassesToFile() Ended  with "
                            + "| Exception [ statusCode : " + response.getStatusCode() + ", statusMessage : " + response.getStatusMessage() + "].");
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving exportAllServiceClassesToFile " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage());
                }

            }
            for (var migrationList : migrationResponse.getServiceClassesMigrationList()) {
                CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                        + ", statusCode: " + response.getStatusCode()
                        + ", payload: " + response.getPayload()
                        + ", payload:["
                        + ", migrationList:["
                        + "keyIdentifier: " + migrationList.getKeyIdentifier()
                        + ", tableName: " + migrationList.getTableName()
                        + ", headers: " + migrationList.getHeaders()
                        + ", data: " + migrationList.getData()
                        + "]"
                        + "]"
                        + "]");
            }

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving service classes tables from [lookup-service] ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving service classes tables from [lookup-service] ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service");
        }
        CCATLogger.DEBUG_LOGGER.debug("getAllServiceClassesTables() Ended successfully.");

        return migrationResponse;
    }

    @LogExecutionTime
    public List<ServiceClassesMigrationSummary> importServiceClasses(ImportServiceClassesLKRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("ImportServiceClasses() Started successfully.");
        BaseResponse<ImportServiceClassesResponse> response = null;
        try {
            Mono<BaseResponse<ImportServiceClassesResponse>> responseAsync = webClient
                    .post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.SERVICE_CLASSES
                            + Defines.WEB_ACTIONS.IMPORT)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<ImportServiceClassesResponse>>() {
                    }).log();
            response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while import service class " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while import service class " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("importServiceClasses() Ended  with "
                            + "| Exception [ statusCode : " + response.getStatusCode() + ", statusMessage : " + response.getStatusMessage() + "].");
                    CCATLogger.DEBUG_LOGGER.debug("Error while import Service Class " + response);
                }

                for (var migrations : request.getMigrationModels()) {
                    CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                            + ", statusCode: " + response.getStatusCode()
                            + ", payload: " + response.getPayload()
                            + ", payload:["
                            + ", migrationList:["
                            + "keyIdentifier: " + migrations.getKeyIdentifier()
                            + ", tableName: " + migrations.getTableName()
                            + ", headers: " + migrations.getHeaders()
                            + ", data: " + migrations.getData()
                            + "]"
                            + "]"
                            + "]");
                }

            }
            return response.getPayload().getSummary();
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.debug("Error while retrieving import service class ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving import service class ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service");
        }
    }
}
