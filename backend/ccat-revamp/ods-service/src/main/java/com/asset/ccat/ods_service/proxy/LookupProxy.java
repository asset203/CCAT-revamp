/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.ods_service.proxy;

import com.asset.ccat.ods_service.models.DSSNodesModel;
import com.asset.ccat.ods_service.models.requests.transaction.*;
import com.asset.ccat.ods_service.models.responses.transaction.*;
import com.asset.ccat.ods_service.configurations.Properties;
import com.asset.ccat.ods_service.defines.Defines;
import com.asset.ccat.ods_service.defines.ErrorCodes;
import com.asset.ccat.ods_service.exceptions.ODSException;
import com.asset.ccat.ods_service.logger.CCATLogger;
import com.asset.ccat.ods_service.models.ods_models.*;
import com.asset.ccat.ods_service.models.responses.BaseResponse;
import com.asset.ccat.ods_service.models.responses.transaction.GetAllTransactionTypeResponse;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author Mahmoud Shehab
 */
@Component
public class LookupProxy {

    @Autowired
    WebClient webClient;

    @Autowired
    Properties properties;

    public HashMap<String, ODSActivityModel> getOdsActivities() throws ODSException {
        HashMap<String, ODSActivityModel> map = null;
        try {
            Mono<BaseResponse<HashMap<String, ODSActivityModel>>> responseAsync = webClient.get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.ContextPaths.LOOKUPS
                            + Defines.ContextPaths.CACHED_LOOKUPS
                            + Defines.ContextPaths.ODS
                            + Defines.ContextPaths.ACTIVITIES
                            + Defines.WEB_ACTIONS.GET_ALL
                    )
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<HashMap<String, ODSActivityModel>>>() {
                    }).log();

            BaseResponse<HashMap<String, ODSActivityModel>> response = responseAsync.block();
            if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                map = response.getPayload();
            } else {
                CCATLogger.DEBUG_LOGGER.info("Error while retrieving ods activities" + response);
                throw new ODSException(ErrorCodes.ERROR.NO_DATA_FOUND);
            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving ods activities");
            CCATLogger.DEBUG_LOGGER.error("Error while retrieving ods activities ", ex);
            throw new ODSException(ErrorCodes.ERROR.UNREACHABLE_LOOKUPS_SERVICE);
        }
        return map;
    }

    public HashMap<Integer, ODSActivityHeader> getOdsActivitiesHeaders() throws ODSException {
        HashMap<Integer, ODSActivityHeader> map = null;
        try {
            Mono<BaseResponse<HashMap<Integer, ODSActivityHeader>>> responseAsync = webClient.get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.ContextPaths.LOOKUPS
                            + Defines.ContextPaths.CACHED_LOOKUPS
                            + Defines.ContextPaths.ODS
                            + Defines.ContextPaths.ACTIVITIES_HEADERS
                            + Defines.WEB_ACTIONS.GET_ALL
                    )
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<HashMap<Integer, ODSActivityHeader>>>() {
                    }).log();

            BaseResponse<HashMap<Integer, ODSActivityHeader>> response = responseAsync.block();
            if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                map = response.getPayload();
            } else {
                CCATLogger.DEBUG_LOGGER.info("Error while retrieving ods activities headers" + response);
                throw new ODSException(ErrorCodes.ERROR.NO_DATA_FOUND);
            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving ods activities headers");
            CCATLogger.DEBUG_LOGGER.error("Error while retrieving ods activities headers", ex);
            throw new ODSException(ErrorCodes.ERROR.UNREACHABLE_LOOKUPS_SERVICE);
        }
        return map;
    }

    public HashMap<Integer, HashMap<Integer, ODSActivityHeaderMapping>> getOdsActivitiesHeadersMapping() throws ODSException {
        HashMap<Integer, HashMap<Integer, ODSActivityHeaderMapping>> map = null;
        try {
            Mono<BaseResponse<HashMap<Integer, HashMap<Integer, ODSActivityHeaderMapping>>>> responseAsync = webClient.get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.ContextPaths.LOOKUPS
                            + Defines.ContextPaths.CACHED_LOOKUPS
                            + Defines.ContextPaths.ODS
                            + Defines.ContextPaths.ACTIVITIES_HEADERS_MAPPING
                            + Defines.WEB_ACTIONS.GET_ALL
                    )
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<HashMap<Integer, HashMap<Integer, ODSActivityHeaderMapping>>>>() {
                    }).log();

            BaseResponse<HashMap<Integer, HashMap<Integer, ODSActivityHeaderMapping>>> response = responseAsync.block();
            if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                map = response.getPayload();
            } else {
                CCATLogger.DEBUG_LOGGER.info("Error while retrieving ods activities headers mapping " + response);
                throw new ODSException(ErrorCodes.ERROR.NO_DATA_FOUND);
            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving ods activities mapping");
            CCATLogger.DEBUG_LOGGER.error("Error while retrieving ods activities mapping", ex);
            throw new ODSException(ErrorCodes.ERROR.UNREACHABLE_LOOKUPS_SERVICE);
        }
        return map;
    }

    public HashMap<Integer, List<ODSActivityDetailsMapping>> getOdsActivitiesDetailsMapping() throws ODSException {
        HashMap<Integer, List<ODSActivityDetailsMapping>> map = null;
        try {
            Mono<BaseResponse<HashMap<Integer, List<ODSActivityDetailsMapping>>>> responseAsync = webClient.get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.ContextPaths.LOOKUPS
                            + Defines.ContextPaths.CACHED_LOOKUPS
                            + Defines.ContextPaths.ODS
                            + Defines.ContextPaths.ACTIVITIES_DETAILS_MAPPING
                            + Defines.WEB_ACTIONS.GET_ALL
                    )
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<HashMap<Integer, List<ODSActivityDetailsMapping>>>>() {
                    }).log();

            BaseResponse<HashMap<Integer, List<ODSActivityDetailsMapping>>> response = responseAsync.block();
            if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                map = response.getPayload();
            } else {
                CCATLogger.DEBUG_LOGGER.info("Error while retrieving ods activities mapping" + response);
                throw new ODSException(ErrorCodes.ERROR.NO_DATA_FOUND);
            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving ods activities mapping");
            CCATLogger.DEBUG_LOGGER.error("Error while retrieving ods activities mapping", ex);
            throw new ODSException(ErrorCodes.ERROR.UNREACHABLE_LOOKUPS_SERVICE);
        }
        return map;
    }

    public HashMap<String, String> getAccountFlags() throws ODSException {
        HashMap<String, String> map = null;
        try {
            Mono<BaseResponse<HashMap<String, String>>> responseAsync = webClient.get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.ContextPaths.LOOKUPS
                            + Defines.ContextPaths.CACHED_LOOKUPS
                            + Defines.ContextPaths.LK_ACCOUNT_FLAGS
                    )
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<HashMap<String, String>>>() {
                    }).log();

            BaseResponse<HashMap<String, String>> response = responseAsync.block();
            if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                map = response.getPayload();
            } else {
                CCATLogger.DEBUG_LOGGER.info("Error while retrieving ods activities mapping" + response);
                throw new ODSException(ErrorCodes.ERROR.NO_DATA_FOUND);
            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving ods activities mapping");
            CCATLogger.DEBUG_LOGGER.error("Error while retrieving ods activities mapping", ex);
            throw new ODSException(ErrorCodes.ERROR.UNREACHABLE_LOOKUPS_SERVICE);
        }
        return map;
    }

    public HashMap<String, String> getBtStatus() throws ODSException {
        HashMap<String, String> map = null;
        try {
            Mono<BaseResponse<HashMap<String, String>>> responseAsync = webClient.get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.ContextPaths.LOOKUPS
                            + Defines.ContextPaths.CACHED_LOOKUPS
                            + Defines.ContextPaths.BT_STATUS
                    )
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<HashMap<String, String>>>() {
                    }).log();

            BaseResponse<HashMap<String, String>> response = responseAsync.block();
            if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                map = response.getPayload();
            } else {
                CCATLogger.DEBUG_LOGGER.info("Error while retrieving ods activities mapping" + response);
                throw new ODSException(ErrorCodes.ERROR.NO_DATA_FOUND);
            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving ods activities mapping");
            CCATLogger.DEBUG_LOGGER.error("Error while retrieving ods activities mapping", ex);
            throw new ODSException(ErrorCodes.ERROR.UNREACHABLE_LOOKUPS_SERVICE);
        }
        return map;
    }

    public HashMap<String, HashMap<String, String>> getDssReportsColumns() throws ODSException {
        HashMap<String, HashMap<String, String>> map = null;
        try {
            Mono<BaseResponse<HashMap<String, HashMap<String, String>>>> responseAsync = webClient.get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.ContextPaths.LOOKUPS
                            + Defines.ContextPaths.CACHED_LOOKUPS
                            + Defines.ContextPaths.DSS_COLUMNS
                    )
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<HashMap<String, HashMap<String, String>>>>() {
                    }).log();

            BaseResponse<HashMap<String, HashMap<String, String>>> response = responseAsync.block();
            if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                map = response.getPayload();
            } else {
                CCATLogger.DEBUG_LOGGER.info("Error while retrieving dss columns mapping" + response);
                throw new ODSException(ErrorCodes.ERROR.NO_DATA_FOUND);
            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving dss columns");
            CCATLogger.DEBUG_LOGGER.error("Error while retrieving dss columns", ex);
            throw new ODSException(ErrorCodes.ERROR.UNREACHABLE_LOOKUPS_SERVICE);
        }
        return map;
    }

    public HashMap<String, String> getTransactionTypes() throws ODSException {
        HashMap<String, String> map = new HashMap<>();
        try {
            GetAllTransactionTypeRequest request = new GetAllTransactionTypeRequest();
            Mono<BaseResponse<GetAllTransactionTypeResponse>> responseAsync = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.ContextPaths.LOOKUPS
                            + Defines.ContextPaths.TRANSACTION_ADMIN
                            + Defines.ContextPaths.TYPE
                            + Defines.WEB_ACTIONS.GET_ALL
                    ).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetAllTransactionTypeResponse>>() {
                    }).log();

            BaseResponse<GetAllTransactionTypeResponse> response = responseAsync.block();
            if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                if (response.getPayload() != null) {
                    response.getPayload().getTransactionTypes().forEach(type -> {
                        map.put(type.getValue(), type.getName());
                    });
                }
            } else {
                CCATLogger.DEBUG_LOGGER.info("Error while retrieving transaction types" + response);
                throw new ODSException(ErrorCodes.ERROR.NO_DATA_FOUND);
            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving transaction types");
            CCATLogger.DEBUG_LOGGER.error("Error while retrieving transaction types", ex);
            throw new ODSException(ErrorCodes.ERROR.UNREACHABLE_LOOKUPS_SERVICE);
        }
        return map;
    }

    public HashMap<String, String> getTransactionCodes() throws ODSException {
        HashMap<String, String> map = new HashMap<>();
        try {
            GetAllTransactionTypeRequest request = new GetAllTransactionTypeRequest();
            Mono<BaseResponse<GetAllTransactionCodeResponse>> responseAsync = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.ContextPaths.LOOKUPS
                            + Defines.ContextPaths.TRANSACTION_ADMIN
                            + Defines.ContextPaths.CODE
                            + Defines.WEB_ACTIONS.GET_ALL
                    ).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetAllTransactionCodeResponse>>() {
                    }).log();

            BaseResponse<GetAllTransactionCodeResponse> response = responseAsync.block();
            if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                if (response.getPayload() != null) {
                    response.getPayload().getTransactionCodes().forEach(code -> {
                        map.put(code.getValue(), code.getDescription());
                    });
                }
            } else {
                CCATLogger.DEBUG_LOGGER.info("Error while retrieving transaction codes " + response);
                throw new ODSException(ErrorCodes.ERROR.NO_DATA_FOUND);
            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving transaction types");
            CCATLogger.DEBUG_LOGGER.error("Error while retrieving transaction types", ex);
            throw new ODSException(ErrorCodes.ERROR.UNREACHABLE_LOOKUPS_SERVICE);
        }
        return map;
    }

    public HashMap<String, String> getTransactionLinkes() throws ODSException {
        HashMap<String, String> map = new HashMap<>();
        try {
            GetAllTransactionTypeRequest request = new GetAllTransactionTypeRequest();
            Mono<BaseResponse<GetAllTransactionLinkesResponse>> responseAsync = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.ContextPaths.LOOKUPS
                            + Defines.ContextPaths.TRANSACTION_ADMIN
                            + Defines.ContextPaths.TRANSACTION_LINK
                                + Defines.WEB_ACTIONS.GET_ALL
                    ).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetAllTransactionLinkesResponse>>() {
                    }).log();

            BaseResponse<GetAllTransactionLinkesResponse> response = responseAsync.block();
            if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                if (response.getPayload() != null) {
                    response.getPayload().getTransactionLinkes().forEach(link -> {
                        map.put(link.getTypeValue() + "_" + link.getCodeValue(), link.getDescription());
                    });
                }
            } else {
                CCATLogger.DEBUG_LOGGER.info("Error while retrieving transaction codes " + response);
                throw new ODSException(ErrorCodes.ERROR.NO_DATA_FOUND);
            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving transaction types");
            CCATLogger.DEBUG_LOGGER.error("Error while retrieving transaction types", ex);
            throw new ODSException(ErrorCodes.ERROR.UNREACHABLE_LOOKUPS_SERVICE);
        }
        return map;
    }

    public List<ODSNodesModel> getODSNodes() throws ODSException {
        List<ODSNodesModel> odsNodesModelList = null;
        try {
            Mono<BaseResponse<List<ODSNodesModel>>> responseAsync = webClient.get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.ContextPaths.LOOKUPS
                            + Defines.ContextPaths.CACHED_LOOKUPS
                            + Defines.ContextPaths.ODS_NODES
                    )
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<List<ODSNodesModel>>>() {
                    }).log();

            BaseResponse<List<ODSNodesModel>> response = responseAsync.block();
            if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                odsNodesModelList = response.getPayload();
            } else {
                CCATLogger.DEBUG_LOGGER.info("Error while retrieving ods nodes");
                throw new ODSException(ErrorCodes.ERROR.NO_DATA_FOUND);
            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving ods nodes");
            CCATLogger.DEBUG_LOGGER.error("Error while retrieving ods nodes", ex);
            throw new ODSException(ErrorCodes.ERROR.UNREACHABLE_LOOKUPS_SERVICE);
        }
        return odsNodesModelList;
    }

    public List<DSSNodesModel> getDSSNodes() throws ODSException {
        List<DSSNodesModel> dssNodesModelsList = null;
        try {
            Mono<BaseResponse<List<DSSNodesModel>>> responseAsync = webClient.get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.ContextPaths.LOOKUPS
                            + Defines.ContextPaths.CACHED_LOOKUPS
                            + Defines.ContextPaths.DSS_NODES
                    )
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<List<DSSNodesModel>>>() {
                    }).log();

            BaseResponse<List<DSSNodesModel>> response = responseAsync.block();
            if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                dssNodesModelsList = response.getPayload();
            } else {
                CCATLogger.DEBUG_LOGGER.info("Error while retrieving dss nodes" + response);
                throw new ODSException(ErrorCodes.ERROR.NO_DATA_FOUND);
            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving dss nodes");
            CCATLogger.DEBUG_LOGGER.error("Error while retrieving dss nodes", ex);
            throw new ODSException(ErrorCodes.ERROR.UNREACHABLE_LOOKUPS_SERVICE);
        }
        return dssNodesModelsList;
    }

    public List<FlexShareHistoryNodeModel> getFlexHistoryNodes() throws ODSException {
        List<FlexShareHistoryNodeModel> flexHistoryNodes;
        try {
            Mono<BaseResponse<List<FlexShareHistoryNodeModel>>> responseAsync = webClient.get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.ContextPaths.LOOKUPS
                            + Defines.ContextPaths.CACHED_LOOKUPS
                            + Defines.ContextPaths.FLEX_SHARE_HISTORY_NODES
                            + Defines.WEB_ACTIONS.GET_ALL
                    )
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<List<FlexShareHistoryNodeModel>>>() {
                    }).log();

            BaseResponse<List<FlexShareHistoryNodeModel>> response = responseAsync.block();
            if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                flexHistoryNodes = response.getPayload();
            } else {
                CCATLogger.DEBUG_LOGGER.info("Error while retrieving flex share history nodes");
                throw new ODSException(ErrorCodes.ERROR.NO_DATA_FOUND);
            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving flex share history nodes nodes");
            CCATLogger.DEBUG_LOGGER.error("Error while retrieving flex share history nodes nodes", ex);
            throw new ODSException(ErrorCodes.ERROR.UNREACHABLE_LOOKUPS_SERVICE);
        }
        return flexHistoryNodes;
    }
}
