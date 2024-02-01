/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.proxy;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.marquee.CreateMarqueeRequest;
import com.asset.ccat.gateway.models.requests.admin.marquee.DeleteAllMarqueesRequest;
import com.asset.ccat.gateway.models.requests.admin.marquee.DeleteMarqueeRequest;
import com.asset.ccat.gateway.models.requests.admin.marquee.GetAllEs2alnyMarqueeRequest;
import com.asset.ccat.gateway.models.requests.admin.marquee.UpdateAllMarqueesRequest;
import com.asset.ccat.gateway.models.requests.admin.marquee.UpdateMarqueesRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.marquee.GetAllEs2alnyMarqueeResponse;
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
public class MarqueeProxy {

    @Autowired
    WebClient webClient;

    @Autowired
    Properties properties;

    @LogExecutionTime
    public GetAllEs2alnyMarqueeResponse getAllMarquees(GetAllEs2alnyMarqueeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getAllEs2alnyMarquees - call user management serivce");
        GetAllEs2alnyMarqueeResponse marquee = null;
        BaseResponse<GetAllEs2alnyMarqueeResponse> response = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The User Management....");
            Mono<BaseResponse<GetAllEs2alnyMarqueeResponse>> res = webClient.post()
                    .uri(properties.getUserManagementUrls()
                            + Defines.ContextPaths.USER_MANAGEMENT_CONTEXT_PATH
                            + Defines.ContextPaths.MARQUEES
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetAllEs2alnyMarqueeResponse>>() {
                    }).log();
            response = res.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    marquee = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while Retriving Marquees " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Retriving Marquees " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + marquee + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (GatewayException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving Marquees ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving Marquees ", ex);
            throw ex;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving Marquees ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving Marquees", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "user-management-service[" + properties.getUserManagementUrls() + "]");
        }
        return marquee;
    }

    @LogExecutionTime
    public BaseResponse addMarquee(CreateMarqueeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting addMarquee - call user management serivce");
        BaseResponse response = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The User Management....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getUserManagementUrls()
                            + Defines.ContextPaths.USER_MANAGEMENT_CONTEXT_PATH
                            + Defines.ContextPaths.MARQUEES
                            + Defines.WEB_ACTIONS.ADD)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();

            response = res.block();

            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Adding Marquee  " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Adding Marquee  " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (GatewayException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Adding Marquee  ");
            CCATLogger.ERROR_LOGGER.error("Error while Adding Marquee ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "user-management-service[" + properties.getUserManagementUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse updateAllMarquees(UpdateAllMarqueesRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting updateMarquee - call user management serivce");
        BaseResponse response = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The User Management....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getUserManagementUrls()
                            + Defines.ContextPaths.USER_MANAGEMENT_CONTEXT_PATH
                            + Defines.ContextPaths.MARQUEES
                            + Defines.WEB_ACTIONS.UPDATE_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Updating Marquee " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Retriving Marquee " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (GatewayException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Updating Marquee ");
            CCATLogger.ERROR_LOGGER.error("Error while Updating Marquee ", ex);
            throw ex;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Updating Marquee ");
            CCATLogger.ERROR_LOGGER.error("Error while Updating Marquee", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "user-management-service[" + properties.getUserManagementUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse updateMarquee(UpdateMarqueesRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting updateMarquee - call user management serivce");
        BaseResponse response = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The User Management....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getUserManagementUrls()
                            + Defines.ContextPaths.USER_MANAGEMENT_CONTEXT_PATH
                            + Defines.ContextPaths.MARQUEES
                            + Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Updating Marquee " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Retriving Marquee " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (GatewayException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Updating Marquee ");
            CCATLogger.ERROR_LOGGER.error("Error while Updating Marquee ", ex);
            throw ex;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Updating Marquee ");
            CCATLogger.ERROR_LOGGER.error("Error while Updating Marquee", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "user-management-service[" + properties.getUserManagementUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse deleteMarqueeById(DeleteMarqueeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting deleteMarquee - call user management serivce");
        BaseResponse response = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The User Management....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getUserManagementUrls()
                            + Defines.ContextPaths.USER_MANAGEMENT_CONTEXT_PATH
                            + Defines.ContextPaths.MARQUEES
                            + Defines.WEB_ACTIONS.DELETE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Deleting Marquee " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Retriving Marquee " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (GatewayException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Deleting Marquee ");
            CCATLogger.ERROR_LOGGER.error("Error while Deleting Marquee ", ex);
            throw ex;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Deleting Marquee ");
            CCATLogger.ERROR_LOGGER.error("Error while Deleting Marquee", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "user-management-service[" + properties.getUserManagementUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse deleteAllMarquee(DeleteAllMarqueesRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting deleteAllMarquee - call user management serivce");
        BaseResponse response = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The User Management....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getUserManagementUrls()
                            + Defines.ContextPaths.USER_MANAGEMENT_CONTEXT_PATH
                            + Defines.ContextPaths.MARQUEES
                            + Defines.WEB_ACTIONS.DELETE_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Deleting Marquee " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Retriving Marquee " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (GatewayException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Deleting Marquee ");
            CCATLogger.ERROR_LOGGER.error("Error while Deleting Marquee ", ex);
            throw ex;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Deleting Marquee ");
            CCATLogger.ERROR_LOGGER.error("Error while Deleting Marquee", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "user-management-service[" + properties.getUserManagementUrls() + "]");
        }
        return response;
    }
}
