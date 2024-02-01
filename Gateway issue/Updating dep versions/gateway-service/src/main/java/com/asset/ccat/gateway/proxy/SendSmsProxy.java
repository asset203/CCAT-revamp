package com.asset.ccat.gateway.proxy;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.customer_care.AccountGroupModel;
import com.asset.ccat.gateway.models.requests.SendSMSRequest;
import com.asset.ccat.gateway.models.requests.customer_care.account_group.GetAccountGroupsRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class SendSmsProxy {
    @Autowired
    private WebClient webClient;

    @Autowired
    private Properties properties;


    @LogExecutionTime
    public void sendSMS(SendSMSRequest request) throws GatewayException {

        CCATLogger.DEBUG_LOGGER.debug("Started SendSmsProxy - sendSMS()");
        BaseResponse<AccountGroupModel> response;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.info("Start sending sms");
            Mono<BaseResponse<AccountGroupModel>> responseAsync = webClient.post()
                    .uri(properties.getNotificationServiceUrls()
                            + Defines.NOTIFICATION_SERVICE.CONTEXT_PATH
                            + Defines.NOTIFICATION_SERVICE.SEND_SMS
                            + Defines.WEB_ACTIONS.SEND
                    )
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<AccountGroupModel>>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while sending sms" + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while sending sms" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while sending sms ");
            CCATLogger.ERROR_LOGGER.error("Error while sending sms", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "notification-service[" + properties.getLookupsServiceUrls() + "]");
        }
        CCATLogger.DEBUG_LOGGER.debug("Ended SMSProxy - sendSMS()");
        CCATLogger.DEBUG_LOGGER.info("Finished sending SMS FROM NOTIFICATION SERVICE service");

    }

}
