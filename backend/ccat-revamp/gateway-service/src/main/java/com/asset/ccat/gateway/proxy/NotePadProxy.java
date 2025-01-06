package com.asset.ccat.gateway.proxy;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.customer_care.notepad.CreateNotePadRequest;
import com.asset.ccat.gateway.models.requests.customer_care.notepad.DeleteNotePadRequest;
import com.asset.ccat.gateway.models.requests.customer_care.notepad.GetAllNotePadRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.customer_care.GetAllNotePadResponse;
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
 * @author nour.ihab
 */
@Component
public class NotePadProxy {

    @Autowired
    Properties properties;

    @Autowired
    WebClient webClient;

    @LogExecutionTime
    public GetAllNotePadResponse getAllNotePad(GetAllNotePadRequest getAllNotePadsRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getAllNotePad - call History service");
        GetAllNotePadResponse getAllNotePadResponse = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + getAllNotePadsRequest + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The History Service....");
            Mono<BaseResponse<GetAllNotePadResponse>> res = webClient.post()
                    .uri(properties.getHistoryServiceUrls()
                            + Defines.ContextPaths.HISTORY_SERVICE_CONTEXT_PATH
                            + Defines.ContextPaths.NOTEPAD
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(getAllNotePadsRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetAllNotePadResponse>>() {
                    }).log();
            BaseResponse<GetAllNotePadResponse> response = res.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    getAllNotePadResponse = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while Retrieving NotePad " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Retrieving NotePad" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + getAllNotePadResponse + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving NotePad ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving NotePad ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "history-service[" + properties.getHistoryServiceUrls() + "]");
        }
        return getAllNotePadResponse;
    }

    @LogExecutionTime
    public BaseResponse addNotePad(CreateNotePadRequest createNotePadRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting addNotePad - call History service");
        BaseResponse response = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + createNotePadRequest + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The History Service....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getHistoryServiceUrls()
                            + Defines.ContextPaths.HISTORY_SERVICE_CONTEXT_PATH
                            + Defines.ContextPaths.NOTEPAD
                            + Defines.WEB_ACTIONS.ADD)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(createNotePadRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();

            response = res.block();

            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Adding NotePad " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Adding NotePad " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Adding NotePad ");
            CCATLogger.ERROR_LOGGER.error("Error while Adding NotePad", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "history-service[" + properties.getHistoryServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse deleteNotePad(DeleteNotePadRequest deleteNotePadRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting deleteNotePad - call history service");
        BaseResponse response = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + deleteNotePadRequest + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The history service....");
            Mono<BaseResponse<UserModel[]>> res = webClient.post()
                    .uri(properties.getHistoryServiceUrls() +
                            Defines.ContextPaths.HISTORY_SERVICE_CONTEXT_PATH +
                            Defines.ContextPaths.NOTEPAD +
                            Defines.WEB_ACTIONS.DELETE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(deleteNotePadRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<UserModel[]>>() {
                    }).log();
            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Deleting NotePad " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Deleting NotePad " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Deleting NotePad ");
            CCATLogger.ERROR_LOGGER.error("Error while Deleting NotePad", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "history-service[" + properties.getHistoryServiceUrls() + "]");
        }
        return response;
    }
}
