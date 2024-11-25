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
import com.asset.ccat.gateway.models.requests.offer.DeleteOfferRequest;
import com.asset.ccat.gateway.models.requests.offer.GetOfferRequest;
import com.asset.ccat.gateway.models.requests.offer.OfferRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.GetAllOffersResponse;
import com.asset.ccat.gateway.models.users.UserModel;
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
public class OfferProxy {

    @Autowired
    WebClient webClient;

    @Autowired
    Properties properties;

    @LogExecutionTime
    public GetAllOffersResponse getAllOffers(GetOfferRequest offerRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getAllOffers - call air serivce");
        GetAllOffersResponse getAllOffersResponse = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + offerRequest + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The AIR-Service....");
            Mono<BaseResponse<GetAllOffersResponse>> res = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.AIR_SERVICE.CONTEXT_PATH
                            + Defines.AIR_SERVICE.OFFERS
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(offerRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetAllOffersResponse>>() {
                    }).log();
            BaseResponse<GetAllOffersResponse> response = res.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    getAllOffersResponse = response.getPayload();
                    if (getAllOffersResponse == null || getAllOffersResponse.getOffers().isEmpty()) {
                        throw new GatewayException(ErrorCodes.ERROR.NO_DATA_FOUND);
                    }
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving Offers " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving Offers " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + getAllOffersResponse + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving Offers ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving Offers ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "air-service[" + properties.getAirServiceUrls() + "]");
        }
        return getAllOffersResponse;
    }

    @LogExecutionTime
    public BaseResponse addOffer(OfferRequest offerRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting addOffer - call air serivce");
        BaseResponse response = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + offerRequest + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The AIR-Service....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.AIR_SERVICE.CONTEXT_PATH
                            + Defines.AIR_SERVICE.OFFERS
                            + Defines.WEB_ACTIONS.ADD)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(offerRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();

            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Adding Offer " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Adding Offer " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Adding Offer ");
            CCATLogger.ERROR_LOGGER.error("Error while Adding Offer", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "air-service[" + properties.getAirServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse updateOffer(OfferRequest offerRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting updateOffer - call air serivce");
        BaseResponse response = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + offerRequest + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The AIR-Service....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.AIR_SERVICE.CONTEXT_PATH
                            + Defines.AIR_SERVICE.OFFERS
                            + Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(offerRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();

            response = res.block();

            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Updating Offer " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Updating Offer " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Updating Offer ");
            CCATLogger.ERROR_LOGGER.error("Error while Updating Offer", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "air-service[" + properties.getAirServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse deleteOffer(DeleteOfferRequest offerRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting deleteUser - call air serivce");
        BaseResponse response = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + offerRequest + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The AIR-Service....");
            Mono<BaseResponse<UserModel[]>> res = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.AIR_SERVICE.CONTEXT_PATH
                            + Defines.AIR_SERVICE.OFFERS
                            + Defines.WEB_ACTIONS.DELETE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(offerRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<UserModel[]>>() {
                    }).log();
            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Deleting Offer " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Deleting Offer " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Deleting Offer ");
            CCATLogger.ERROR_LOGGER.error("Error while Deleting Offer", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "air-service[" + properties.getAirServiceUrls() + "]");
        }
        return response;
    }

}
