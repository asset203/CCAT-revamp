package com.asset.ccat.gateway.proxy.admin;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.ods_nodes.*;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.ods_nodes.GetAllODSNodesResponse;
import com.asset.ccat.gateway.models.responses.admin.ods_nodes.GetODSNodeResponse;
import com.asset.ccat.gateway.models.users.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Objects;

/**
 * @author Assem.Hassan
 */
@Component
public class ODSNodesProxy {

    @Autowired
    WebClient webClient;

    @Autowired
    Properties properties;

    @LogExecutionTime
    public GetAllODSNodesResponse getAllOdsNodes(GetAllODSNodesRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getAllOdsNodes() - call look-up service");
        GetAllODSNodesResponse getAllODSNodesResponse = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The LookUp-Service....");
            Mono<BaseResponse<GetAllODSNodesResponse>> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.ODS_NODES
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetAllODSNodesResponse>>() {
                    }).log();
            BaseResponse<GetAllODSNodesResponse> response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    getAllODSNodesResponse = response.getPayload();
                    if (Objects.isNull(getAllODSNodesResponse) || getAllODSNodesResponse.getOdsNodesModelList().isEmpty()) {
                        throw new GatewayException(ErrorCodes.ERROR.NO_DATA_FOUND);
                    }
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving ODS-Nodes " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving ODS-Nodes " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + getAllODSNodesResponse + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving ODS-Nodes ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving ODS-Nodes ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return getAllODSNodesResponse;
    }

    @LogExecutionTime
    public GetODSNodeResponse getOdsNodes(GetODSNodeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getOdsNodes() - call look-up service");
        GetODSNodeResponse getODSNodeResponse = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The LookUp-Service....");
            Mono<BaseResponse<GetODSNodeResponse>> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.ODS_NODES
                            + Defines.WEB_ACTIONS.GET)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetODSNodeResponse>>() {
                    }).log();
            BaseResponse<GetODSNodeResponse> response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    getODSNodeResponse = response.getPayload();
                    if (Objects.isNull(getODSNodeResponse)) {
                        throw new GatewayException(ErrorCodes.ERROR.NO_DATA_FOUND);
                    }
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving ODS-Node " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving ODS-Node" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + getODSNodeResponse + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving ODS-Node");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving ODS-Node", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return getODSNodeResponse;
    }


    @LogExecutionTime
    public BaseResponse addODSNode(AddODSNodeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting addODSNode() - call lookup-service");
        BaseResponse response = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The LookUp-Service....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.ODS_NODES
                            + Defines.WEB_ACTIONS.ADD)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();

            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (Objects.nonNull(response)) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Adding ODS-Node " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Adding ODS-Node " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Adding ODS-Node ");
            CCATLogger.ERROR_LOGGER.error("Error while Adding ODS-Node", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse updateODSNode(UpdateODSNodeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting updateODSNode() - call lookup-service");
        BaseResponse response = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The LookUp-Service....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.ODS_NODES
                            + Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (Objects.nonNull(response)) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Updating Ods-Node " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Updating Ods-Node " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Updating Ods-Node ");
            CCATLogger.ERROR_LOGGER.error("Error while Updating Ods-Node", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse deleteODSNode(DeleteODSNodeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting deleteODSNode() - call lookup-service");
        BaseResponse response = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The LookUp-Service....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.ODS_NODES
                            + Defines.WEB_ACTIONS.DELETE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (Objects.nonNull(response)) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Deleting Ods-Node " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Deleting Ods-Node " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Deleting Ods-Node ");
            CCATLogger.ERROR_LOGGER.error("Error while Deleting Ods-Node", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

}
