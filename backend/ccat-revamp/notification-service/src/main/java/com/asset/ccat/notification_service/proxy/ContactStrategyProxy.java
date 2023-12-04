package com.asset.ccat.notification_service.proxy;

import com.asset.ccat.notification_service.configurations.Properties;
import com.asset.ccat.notification_service.defines.ErrorCodes;
import com.asset.ccat.notification_service.exceptions.NotificationException;
import com.asset.ccat.notification_service.logger.CCATLogger;
import com.asset.ccat.notification_service.models.CSRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class ContactStrategyProxy {


    private final WebClient webClient;

    private final Properties properties;

    @Autowired
    public ContactStrategyProxy(WebClient webClient, Properties properties) {
        this.webClient = webClient;
        this.properties = properties;
    }


    public String sendSmsRequest(CSRequestModel requestModel) throws NotificationException {
        CCATLogger.DEBUG_LOGGER.info("Send sms to contact strategy with request {}" , requestModel);
        String response = null;
        try {
            CCATLogger.DEBUG_LOGGER.info("Start call CS " + requestModel.getUrl());
            Mono<String> responseAsync = webClient.get()
                    .uri(requestModel.getUrl(), requestModel.getVariables())
                    .retrieve()
                    .bodyToMono(String.class);
            response = responseAsync.block(Duration.ofMillis(properties.getReadTimeOut()));
            CCATLogger.INTERFACE_LOGGER.debug("Response: " + response);
            CCATLogger.DEBUG_LOGGER.info("Send sms to contact strategy with request successfully ");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while calling CS " + requestModel, ex);
            CCATLogger.ERROR_LOGGER.error("Error while calling CS " + requestModel, ex);
            throw new NotificationException(ErrorCodes.ERROR.SYSTEM_UNREACHABLE, "Contact strategy");
        }
        return response;
    }
}
