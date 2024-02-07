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
import com.asset.ccat.gateway.models.requests.admin.transaction.AddTransactionCodeRequest;
import com.asset.ccat.gateway.models.requests.admin.transaction.AddTransactionTypeRequest;
import com.asset.ccat.gateway.models.requests.admin.transaction.DeleteTransactionRequest;
import com.asset.ccat.gateway.models.requests.admin.transaction.GetAllTransactionCodeRequest;
import com.asset.ccat.gateway.models.requests.admin.transaction.GetAllTransactionLinkRequest;
import com.asset.ccat.gateway.models.requests.admin.transaction.GetAllTransactionTypeRequest;
import com.asset.ccat.gateway.models.requests.admin.transaction.UpdateTransactionCodeRequest;
import com.asset.ccat.gateway.models.requests.admin.transaction.UpdateTransactionLinkRequest;
import com.asset.ccat.gateway.models.requests.admin.transaction.UpdateTransactionTypeRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.transaction.GetAllTransactionCodeResponse;
import com.asset.ccat.gateway.models.responses.admin.transaction.GetAllTransactionTypeResponse;
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
public class AdmTransactionProxy {

    @Autowired
    WebClient webClient;

    @Autowired
    Properties properties;

    @LogExecutionTime
    public GetAllTransactionCodeResponse getAllTransactionCodes(GetAllTransactionCodeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getAllTransactionCodes - call lookup-service");
        GetAllTransactionCodeResponse getAllResponse = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The lookup-service....");
            Mono<BaseResponse<GetAllTransactionCodeResponse>> res = webClient
                    .post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.TRANSACTION_ADMIN
                            + Defines.LOOKUP_SERVICE.CODE
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetAllTransactionCodeResponse>>() {
                    }).log();
            BaseResponse<GetAllTransactionCodeResponse> response = res.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    getAllResponse = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving TransactionCodes " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving TransactionCodes " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            for (var transactionCodes : getAllResponse.getTransactionCodes()) {
                CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                        + ", statusCode: " + response.getStatusCode()
                        + ", payload: " + response.getPayload()
                        + ", payload:["
                        + ", transactionCodes:["
                        + ", description: " + transactionCodes.getDescription()
                        + ", id: " + transactionCodes.getId()
                        + ", name: " + transactionCodes.getName()
                        + ", value: " + transactionCodes.getValue()
                        + "]"
                        + "]"
                        + "]");
            }

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving TransactionCodes ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving TransactionCodes ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service [" + properties.getLookupsServiceUrls() + "]");
        }
        return getAllResponse;
    }

    @LogExecutionTime
    public GetAllTransactionTypeResponse getAllTransactionTypes(GetAllTransactionTypeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getAllTransactionTypes - call lookup-service");
        GetAllTransactionTypeResponse getAllBusinessPlansResponse = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The lookup-service....");
            Mono<BaseResponse<GetAllTransactionTypeResponse>> res = webClient
                    .post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.TRANSACTION_ADMIN
                            + Defines.LOOKUP_SERVICE.TYPE
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetAllTransactionTypeResponse>>() {
                    }).log();
            BaseResponse<GetAllTransactionTypeResponse> response = res.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    getAllBusinessPlansResponse = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving TransactionTypes " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving TransactionTypes " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            for (var transactionTypes : getAllBusinessPlansResponse.getTransactionTypes()) {

                CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                        + ", statusCode: " + response.getStatusCode()
                        + ", payload: " + response.getPayload()
                        + ", payload:["
                        + ", transactionTypes:["
                        + ", ccFeatutes:["
                        + ", ccFeatues: " + transactionTypes.getCcFeatures()
                        + ", id: " + transactionTypes.getId()
                        + ", name: " + transactionTypes.getName()
                        + ", value: " + transactionTypes.getValue()
                        + "]"
                        + "]"
                        + "]"
                        + "]");

            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving TransactionTypes ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving TransactionTypes ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service [" + properties.getLookupsServiceUrls() + "]");
        }
        return getAllBusinessPlansResponse;
    }

    @LogExecutionTime
    public GetAllTransactionCodeResponse getAllTransactionLinks(GetAllTransactionLinkRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getAllTransactionLinks - call lookup-service");
        GetAllTransactionCodeResponse getAllResponse = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The lookup-service....");
            Mono<BaseResponse<GetAllTransactionCodeResponse>> res = webClient
                    .post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.TRANSACTION_ADMIN
                            + Defines.LOOKUP_SERVICE.LINK
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetAllTransactionCodeResponse>>() {
                    }).log();
            BaseResponse<GetAllTransactionCodeResponse> response = res.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    getAllResponse = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving TransactionLinks " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving TransactionLinks " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            for (var transactionCodes : getAllResponse.getTransactionCodes()) {

                CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                        + ", statusCode: " + response.getStatusCode()
                        + ", payload: " + response.getPayload()
                        + ", payload:["
                        + ", transactionTypes:["
                        + ", ccFeatutes:["
                        + ", description: " + transactionCodes.getDescription()
                        + ", id: " + transactionCodes.getId()
                        + ", name: " + transactionCodes.getName()
                        + ", value: " + transactionCodes.getValue()
                        + "]"
                        + "]"
                        + "]"
                        + "]");

            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving TransactionLinks ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving TransactionLinks ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service [" + properties.getLookupsServiceUrls() + "]");
        }
        return getAllResponse;
    }

    @LogExecutionTime
    public BaseResponse updateTransactionCode(UpdateTransactionCodeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting updateTransactionCode - call lookup-service");
        BaseResponse response = null;
        try {

            CCATLogger.INTERFACE_LOGGER.info("request is [" + "description:" + request.getDescription()
                    + ", name:" + request.getName()
                    + ", value:" + request.getValue()
                    + ", id:" + request.getId()
                    + "]");

            CCATLogger.DEBUG_LOGGER.debug("Calling The lookup-service....");
            Mono<BaseResponse> res = webClient
                    .post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.TRANSACTION_ADMIN
                            + Defines.LOOKUP_SERVICE.CODE
                            + Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while updating TransactionCode " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while updating TransactionCode " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while updating TransactionCode ");
            CCATLogger.ERROR_LOGGER.error("Error while updating TransactionCode ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse updateTransactionType(UpdateTransactionTypeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting updateTransactionType - call lookup-service");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "description:" + request.getDescription()
                    + ", name:" + request.getName()
                    + ", value:" + request.getValue()
                    + ", id:" + request.getId()
                    + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The lookup-service....");
            Mono<BaseResponse> res = webClient
                    .post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.TRANSACTION_ADMIN
                            + Defines.LOOKUP_SERVICE.TYPE
                            + Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while updating TransactionType " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while updating TransactionType " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while updating TransactionType ");
            CCATLogger.ERROR_LOGGER.error("Error while updating TransactionType ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse updateTransactionLink(UpdateTransactionLinkRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting updateTransactionLink - call lookup-service");
        BaseResponse response = null;

        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "codeId:" + request.getCodeId()
                    + ", linkType:" + request.getLinkType()
                    + ", typeId:" + request.getTypeId()
                    + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The lookup-service....");
            Mono<BaseResponse> res = webClient
                    .post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.TRANSACTION_ADMIN
                            + Defines.LOOKUP_SERVICE.LINK
                            + Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while updating TransactionLink " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while updating TransactionLink " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while updating TransactionLink ");
            CCATLogger.ERROR_LOGGER.error("Error while updating TransactionLink ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse addTransactionCode(AddTransactionCodeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting addTransactionCode - call lookup-service");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "name:" + request.getName()
                    + ", description:" + request.getDescription()
                    + ", value:" + request.getValue()
                    + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The lookup-service....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.TRANSACTION_ADMIN
                            + Defines.LOOKUP_SERVICE.CODE
                            + Defines.WEB_ACTIONS.ADD)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Adding TransactionCode " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Adding TransactionCode " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Adding TransactionCode ");
            CCATLogger.ERROR_LOGGER.error("Error while Adding TransactionCode", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse addTransactionType(AddTransactionTypeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting addTransactionType - call lookup-service");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "name:" + request.getName()
                    + ", description:" + request.getDescription()
                    + ", value:" + request.getValue()
                    + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The lookup-service....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.TRANSACTION_ADMIN
                            + Defines.LOOKUP_SERVICE.TYPE
                            + Defines.WEB_ACTIONS.ADD)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();

            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.error("Error while Adding TransactionType " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Adding TransactionType ");
            CCATLogger.ERROR_LOGGER.error("Error while Adding TransactionType", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse deleteTransactionCode(DeleteTransactionRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting deleteTransactionCode - call lookup-service");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request.getId() + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The lookup-service....");
            Mono<BaseResponse> res = webClient
                    .post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.TRANSACTION_ADMIN
                            + Defines.LOOKUP_SERVICE.CODE
                            + Defines.WEB_ACTIONS.DELETE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Deleting TransactionCode " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Deleting TransactionCode " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Deleting TransactionCode ");
            CCATLogger.ERROR_LOGGER.error("Error while Deleting TransactionCode", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse deleteTransactionType(DeleteTransactionRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting deleteTransactionType - call lookup-service");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request.getId() + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The lookup-service....");
            Mono<BaseResponse> res = webClient
                    .post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.TRANSACTION_ADMIN
                            + Defines.LOOKUP_SERVICE.TYPE
                            + Defines.WEB_ACTIONS.DELETE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Deleting TransactionType " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Deleting TransactionType " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Deleting TransactionType ");
            CCATLogger.ERROR_LOGGER.error("Error while Deleting TransactionType", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

}
