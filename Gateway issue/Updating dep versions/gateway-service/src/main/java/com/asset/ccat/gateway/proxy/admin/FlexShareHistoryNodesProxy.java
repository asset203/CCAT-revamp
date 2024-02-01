package com.asset.ccat.gateway.proxy.admin;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.flex_share_history.*;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.flex_share_history.GetAllFlexShareHistoryNodesResponse;
import com.asset.ccat.gateway.models.responses.admin.flex_share_history.GetFlexShareHistoryNodeResponse;
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
public class FlexShareHistoryNodesProxy {

    @Autowired
    WebClient webClient;

    @Autowired
    Properties properties;

    @LogExecutionTime
    public GetAllFlexShareHistoryNodesResponse getAllFlexShareHistoryNodes(GetAllFlexShareHistoryNodesRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getAllFlexShareHistoryNodes() - call look-up service");
        GetAllFlexShareHistoryNodesResponse getAllFlexShareHistoryNodesResponse = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The LookUp-Service....");
            Mono<BaseResponse<GetAllFlexShareHistoryNodesResponse>> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.FLEX_SHARE_HISTORY_NODES
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetAllFlexShareHistoryNodesResponse>>() {
                    }).log();
            BaseResponse<GetAllFlexShareHistoryNodesResponse> response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    getAllFlexShareHistoryNodesResponse = response.getPayload();
                    if (Objects.isNull(getAllFlexShareHistoryNodesResponse) || getAllFlexShareHistoryNodesResponse.getFlexShareHistoryNodeModelNodesList().isEmpty()) {
                        throw new GatewayException(ErrorCodes.ERROR.NO_DATA_FOUND);
                    }
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving FlexShareHistory-Nodes " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving FlexShareHistory-Nodes " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + getAllFlexShareHistoryNodesResponse + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving FlexShareHistory-Nodes ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving FlexShareHistory-Nodes ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return getAllFlexShareHistoryNodesResponse;
    }

    @LogExecutionTime
    public GetFlexShareHistoryNodeResponse getFlexShareHistoryNodes(GetFlexShareHistoryNodeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getFlexShareHistoryNodes() - call look-up service");
        GetFlexShareHistoryNodeResponse getFlexShareHistoryNodeResponse = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The LookUp-Service....");
            Mono<BaseResponse<GetFlexShareHistoryNodeResponse>> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.FLEX_SHARE_HISTORY_NODES
                            + Defines.WEB_ACTIONS.GET)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetFlexShareHistoryNodeResponse>>() {
                    }).log();
            BaseResponse<GetFlexShareHistoryNodeResponse> response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    getFlexShareHistoryNodeResponse = response.getPayload();
                    if (Objects.isNull(getFlexShareHistoryNodeResponse)) {
                        throw new GatewayException(ErrorCodes.ERROR.NO_DATA_FOUND);
                    }
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving FlexShareHistory-Node " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving FlexShareHistory-Node" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + getFlexShareHistoryNodeResponse + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving FlexShareHistory-Node");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving FlexShareHistory-Node", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return getFlexShareHistoryNodeResponse;
    }


    @LogExecutionTime
    public BaseResponse addFlexShareHistoryNode(AddFlexShareHistoryNodeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting addFlexShareHistoryNode() - call lookup-service");
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
                            + Defines.LOOKUP_SERVICE.FLEX_SHARE_HISTORY_NODES
                            + Defines.WEB_ACTIONS.ADD)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();

            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (Objects.nonNull(response)) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Adding FlexShareHistory-Node " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Adding FlexShareHistory-Node " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Adding FlexShareHistory-Node ");
            CCATLogger.ERROR_LOGGER.error("Error while Adding FlexShareHistory-Node", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse updateFlexShareHistoryNode(UpdateFlexShareHistoryNodeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting updateFlexShareHistoryNode() - call lookup-service");
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
                            + Defines.LOOKUP_SERVICE.FLEX_SHARE_HISTORY_NODES
                            + Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (Objects.nonNull(response)) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Updating FlexShareHistoryNode " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Updating FlexShareHistoryNode " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Updating FlexShareHistoryNode ");
            CCATLogger.ERROR_LOGGER.error("Error while Updating FlexShareHistoryNode", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse deleteFlexShareHistoryNode(DeleteFlexShareHistoryNodeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting deleteFlexShareHistoryNode() - call lookup-service");
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
                            + Defines.LOOKUP_SERVICE.FLEX_SHARE_HISTORY_NODES
                            + Defines.WEB_ACTIONS.DELETE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (Objects.nonNull(response)) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Deleting FlexShareHistoryNode " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Deleting FlexShareHistoryNode " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Deleting FlexShareHistoryNode ");
            CCATLogger.ERROR_LOGGER.error("Error while Deleting FlexShareHistoryNode", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

}
