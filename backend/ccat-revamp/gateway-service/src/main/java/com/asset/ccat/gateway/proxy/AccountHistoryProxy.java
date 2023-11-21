package com.asset.ccat.gateway.proxy;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.redis.model.SubscriberActivityModel;
import com.asset.ccat.gateway.models.requests.customer_care.history.GetAllSubscriberActivityRequest;
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
import java.util.List;

@Component
public class AccountHistoryProxy {

    @Autowired
    Properties properties;

    @Autowired
    WebClient webClient;

    @LogExecutionTime
    public List<SubscriberActivityModel> getAllSubscriberActivity(GetAllSubscriberActivityRequest getAllSubscriberActivityRequest) throws GatewayException {
        BaseResponse<List<SubscriberActivityModel>> response = null;
        List<SubscriberActivityModel> subscriberActivityList = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + getAllSubscriberActivityRequest + "]");
            Mono<BaseResponse<List<SubscriberActivityModel>>> responseAsync = webClient.post()
                    .uri(properties.getOdsServiceUrls()
                            + Defines.ODS_SERVICE.CONTEXT_PATH
                            + Defines.ODS_SERVICE.ACCOUNT_HISTORY
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(getAllSubscriberActivityRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<List<SubscriberActivityModel>>>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    subscriberActivityList = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving subscriber activities " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving subscriber activities " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving subscriber activities ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving subscriber activities ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "ods-service[" + properties.getOdsServiceUrls() + "]");
        }

        return subscriberActivityList;
    }
}
