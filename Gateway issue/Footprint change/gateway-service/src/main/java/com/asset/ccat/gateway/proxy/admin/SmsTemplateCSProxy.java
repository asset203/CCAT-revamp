package com.asset.ccat.gateway.proxy.admin;


import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.sms_template_cs.AddTemplateRequest;
import com.asset.ccat.gateway.models.requests.admin.sms_template_cs.DeleteTemplateRequest;
import com.asset.ccat.gateway.models.requests.admin.sms_template_cs.GetAllTemplatesRequest;
import com.asset.ccat.gateway.models.requests.admin.sms_template_cs.UpdateTemplateRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.sms_template_cs.GetAllSmsTemplatesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author Assem.Hassan
 */
@Component
public class SmsTemplateCSProxy {

    @Autowired
    WebClient webClient;

    @Autowired
    Properties properties;


    @LogExecutionTime
    public GetAllSmsTemplatesResponse getAllSmsTemplates(GetAllTemplatesRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting SmsTemplateCSProxy - getAllSmsTemplates()");
        GetAllSmsTemplatesResponse getAllSmsTemplatesResponse = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The Notification Service....");
            String URI = properties.getNotificationServiceUrls()
                    + Defines.NOTIFICATION_SERVICE.CONTEXT_PATH
                    + Defines.NOTIFICATION_SERVICE.SMS_TEMPLATE_CS
                    + Defines.WEB_ACTIONS.GET_ALL;
            Mono<BaseResponse<GetAllSmsTemplatesResponse>> res = webClient.post()
                    .uri(URI)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetAllSmsTemplatesResponse>>() {
                    }).log();
            BaseResponse<GetAllSmsTemplatesResponse> response = res.block();
            if (Objects.nonNull(response)) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    getAllSmsTemplatesResponse = response.getPayload();
                    if (Objects.isNull(getAllSmsTemplatesResponse) || getAllSmsTemplatesResponse.getSmsTemplates().isEmpty()) {
                        throw new GatewayException(ErrorCodes.ERROR.NO_DATA_FOUND);
                    }
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving SMS Templates " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving SMS Templates " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + getAllSmsTemplatesResponse + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving SMS Templates ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving SMS Templates ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "Notification-service [" + properties.getNotificationServiceUrls() + "]");
        }
        return getAllSmsTemplatesResponse;
    }


    @LogExecutionTime
    public BaseResponse addSmsTemplates(AddTemplateRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting SmsTemplateCSProxy - addSmsTemplates()");
        BaseResponse response;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The Notification Service....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getNotificationServiceUrls()
                            + Defines.NOTIFICATION_SERVICE.CONTEXT_PATH
                            + Defines.NOTIFICATION_SERVICE.SMS_TEMPLATE_CS
                            + Defines.WEB_ACTIONS.ADD)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block();
            if (Objects.nonNull(response)) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Adding SMS Templates " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Adding SMS Templates " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Adding SMS Templates ");
            CCATLogger.ERROR_LOGGER.error("Error while Adding SMS Templates ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "Notification-Service Unreachable [" + properties.getNotificationServiceUrls() + "]");
        }
        return response;
    }


    @LogExecutionTime
    public BaseResponse updateSmsTemplates(UpdateTemplateRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting SmsTemplateCSProxy - updateSmsTemplates()");
        BaseResponse response;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The Notification Service....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getNotificationServiceUrls()
                            + Defines.NOTIFICATION_SERVICE.CONTEXT_PATH
                            + Defines.NOTIFICATION_SERVICE.SMS_TEMPLATE_CS
                            + Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block();
            if (Objects.nonNull(response)) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Updating SMS Templates " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Updating SMS Templates " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Updating SMS Templates ");
            CCATLogger.ERROR_LOGGER.error("Error while Updating SMS Templates", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service [" + properties.getNotificationServiceUrls() + "]");
        }
        return response;
    }


    @LogExecutionTime
    public BaseResponse deleteSmsTemplates(DeleteTemplateRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting SmsTemplateCSProxy - deleteSmsTemplates()");
        BaseResponse response;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The Notification Service....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getNotificationServiceUrls()
                            +Defines.NOTIFICATION_SERVICE.CONTEXT_PATH
                            + Defines.NOTIFICATION_SERVICE.SMS_TEMPLATE_CS
                            + Defines.WEB_ACTIONS.DELETE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block();
            if (Objects.nonNull(response)) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Deleting SMS Templates " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Deleting SMS Templates " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Deleting SMS Templates ");
            CCATLogger.ERROR_LOGGER.error("Error while Deleting SMS Templates", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service [" + properties.getNotificationServiceUrls() + "]");
        }
        return response;
    }
}
