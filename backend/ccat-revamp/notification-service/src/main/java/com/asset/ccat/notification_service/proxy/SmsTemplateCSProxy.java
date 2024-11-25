package com.asset.ccat.notification_service.proxy;

import com.asset.ccat.notification_service.annotation.LogExecutionTime;
import com.asset.ccat.notification_service.configurations.Properties;
import com.asset.ccat.notification_service.defines.Defines;
import com.asset.ccat.notification_service.defines.ErrorCodes;
import com.asset.ccat.notification_service.exceptions.NotificationException;
import com.asset.ccat.notification_service.logger.CCATLogger;
import com.asset.ccat.notification_service.models.GetAllLanguagesModel;
import com.asset.ccat.notification_service.models.SMSActionModel;
import com.asset.ccat.notification_service.models.SmsTemplateModel;
import com.asset.ccat.notification_service.models.responses.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public List<SmsTemplateModel> getAllSmsTemplates() throws NotificationException {
        CCATLogger.DEBUG_LOGGER.info("Starting SmsTemplateCSProxy - getAllSmsTemplates()");
        List<SmsTemplateModel> templateList = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.DEBUG_LOGGER.debug("Calling The Lookup Service....");
            Mono<BaseResponse<List<SmsTemplateModel>>> res = webClient.get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                            + Defines.LOOKUP_SERVICE.SMS_TEMPLATE
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<List<SmsTemplateModel>>>() {
                    })
                    .log();
            BaseResponse<List<SmsTemplateModel>> response = res.block();
            if (Objects.nonNull(response)) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    templateList = response.getPayload();
                    if (Objects.isNull(templateList) || templateList.isEmpty()) {
                        throw new NotificationException(ErrorCodes.ERROR.NO_DATA_FOUND);
                    }
                } else {
                    CCATLogger.DEBUG_LOGGER.warn("Error while retrieving SMS  {} ", response);
                    throw new NotificationException(response.getStatusCode(), null, response.getStatusMessage());
                }
            }
            CCATLogger.DEBUG_LOGGER.info("response is [{}] ", templateList);
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.error("RuntimeException while retrieving SMS Templates ", ex);
            CCATLogger.ERROR_LOGGER.error("RuntimeException while retrieving SMS Templates ", ex);
            throw new NotificationException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service [" + properties.getLookupsServiceUrls() + "]");
        } finally {
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.DEBUG_LOGGER.info("executed in {} ms", executionTime);
        }
        return templateList;
    }


    @LogExecutionTime
    public List<SMSActionModel> getAllSmsActions() throws NotificationException {
        CCATLogger.DEBUG_LOGGER.info("Starting SmsTemplateCSProxy - getAllSmsActions()");
        List<SMSActionModel> actionList = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.DEBUG_LOGGER.debug("Calling The Lookup Service....");
            Mono<BaseResponse<List<SMSActionModel>>> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.SMS_ACTIONS
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)

                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<List<SMSActionModel>>>() {
                    }).log();
            BaseResponse<List<SMSActionModel>> response = res.block();
            if (Objects.nonNull(response)) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    actionList = response.getPayload();
                    if (Objects.isNull(actionList) || actionList.isEmpty()) {
                        throw new NotificationException(ErrorCodes.ERROR.NO_DATA_FOUND);
                    }
                } else {
                    CCATLogger.DEBUG_LOGGER.warn("Error while retrieving SMS Actions {}", response);
                    throw new NotificationException(response.getStatusCode(), null, response.getStatusMessage());
                }
            }
            CCATLogger.DEBUG_LOGGER.info("response is [{}]", actionList);
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.error("RuntimeException while retrieving SMS actions ", ex);
            CCATLogger.ERROR_LOGGER.error("RuntimeException while retrieving SMS actions ", ex);
            throw new NotificationException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service [" + properties.getLookupsServiceUrls() + "]");
        } finally {
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.DEBUG_LOGGER.info("executed in {} ms", executionTime);
        }
        return actionList;
    }

    @LogExecutionTime
    public Map<String, String> getAllLanguages() throws NotificationException {
        Map<String, String> getAllLanguageList = null;
        try {
            CCATLogger.DEBUG_LOGGER.info("Start retrieving Languages");

            Mono<BaseResponse<GetAllLanguagesModel[]>> responseAsync = webClient.get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                            + Defines.LOOKUP_SERVICE.LANGUAGE)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetAllLanguagesModel[]>>() {
                    }).log();

            BaseResponse<GetAllLanguagesModel[]> response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    getAllLanguageList = Arrays.stream(response.getPayload())
                            .peek((model) -> CCATLogger.DEBUG_LOGGER.info("language retrieved {}", model))
                            .collect(Collectors.toMap(GetAllLanguagesModel::getKey, GetAllLanguagesModel::getValue));
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving getServiceClasses {}", response);
                    throw new NotificationException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.error("RuntimeException while retrieving languages ", ex);
            CCATLogger.ERROR_LOGGER.error("RuntimeException while retrieving languages ", ex);
            throw new NotificationException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service");
        }
        return getAllLanguageList;

    }
}
