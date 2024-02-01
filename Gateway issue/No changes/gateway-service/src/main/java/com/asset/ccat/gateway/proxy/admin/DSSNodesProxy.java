package com.asset.ccat.gateway.proxy.admin;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.dss_nodes.*;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.dss_nodes.GetAllDSSNodesResponse;
import com.asset.ccat.gateway.models.responses.admin.dss_nodes.GetDSSNodeResponse;
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
public class DSSNodesProxy {

    @Autowired
    WebClient webClient;

    @Autowired
    Properties properties;

    @LogExecutionTime
    public GetAllDSSNodesResponse getAllDssNodes(GetAllDSSNodesRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getAllDssNodes() - call look-up service");
        GetAllDSSNodesResponse getAllDSSNodesResponse = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The LookUp-Service....");
            Mono<BaseResponse<GetAllDSSNodesResponse>> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.DSS_NODES
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetAllDSSNodesResponse>>() {
                    }).log();
            BaseResponse<GetAllDSSNodesResponse> response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    getAllDSSNodesResponse = response.getPayload();
                    if (Objects.isNull(getAllDSSNodesResponse) || getAllDSSNodesResponse.getDssNodesList().isEmpty()) {
                        throw new GatewayException(ErrorCodes.ERROR.NO_DATA_FOUND);
                    }
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving DSS-Nodes " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving DSS-Nodes " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + getAllDSSNodesResponse + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving DSS-Nodes ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving DSS-Nodes ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return getAllDSSNodesResponse;
    }

    @LogExecutionTime
    public GetDSSNodeResponse getDssNode(GetDSSNodeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getDssNode() - call look-up service");
        GetDSSNodeResponse getDSSNodeResponse = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The LookUp-Service....");
            Mono<BaseResponse<GetDSSNodeResponse>> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.DSS_NODES
                            + Defines.WEB_ACTIONS.GET)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetDSSNodeResponse>>() {
                    }).log();
            BaseResponse<GetDSSNodeResponse> response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    getDSSNodeResponse = response.getPayload();
                    if (Objects.isNull(getDSSNodeResponse)) {
                        throw new GatewayException(ErrorCodes.ERROR.NO_DATA_FOUND);
                    }
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving DSS-Node " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving DSS-Node" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + getDSSNodeResponse + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving DSS-Node");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving DSS-Node", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return getDSSNodeResponse;
    }


    @LogExecutionTime
    public BaseResponse addDSSNode(AddDSSNodeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting addDSSNode() - call lookup-service");
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
                            + Defines.LOOKUP_SERVICE.DSS_NODES
                            + Defines.WEB_ACTIONS.ADD)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();

            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (Objects.nonNull(response)) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Adding DSS-Node " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Adding DSS-Node " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Adding DSS-Node ");
            CCATLogger.ERROR_LOGGER.error("Error while Adding DSS-Node", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse updateDSSNode(UpdateDSSNodeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting updateDSSNode() - call lookup-service");
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
                            + Defines.LOOKUP_SERVICE.DSS_NODES
                            + Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (Objects.nonNull(response)) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Updating Dss-Node " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Updating Dss-Node " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Updating Dss-Node ");
            CCATLogger.ERROR_LOGGER.error("Error while Updating Dss-Node", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse deleteDSSNode(DeleteDSSNodeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting deleteDSSNode() - call lookup-service");
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
                            + Defines.LOOKUP_SERVICE.DSS_NODES
                            + Defines.WEB_ACTIONS.DELETE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (Objects.nonNull(response)) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Deleting Dss-Node " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Deleting Dss-Node " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Deleting Dss-Node ");
            CCATLogger.ERROR_LOGGER.error("Error while Deleting Dss-Node", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }

}
