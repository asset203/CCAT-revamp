package com.asset.ccat.gateway.proxy;

import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.customer_care.SubscriberAccountModel;
import com.asset.ccat.gateway.models.requests.customer_care.advanced.DisconnectSubscriberRequest;
import com.asset.ccat.gateway.models.requests.customer_care.advanced.InstallSubscriberRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.customer_care.InstallSubscriberResponse;
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
 * @author marwa.elshawarby
 */
@Component
public class AdvancedProxy {

    @Autowired
    private Properties properties;

    @Autowired
    private WebClient webClient;

    public Mono<BaseResponse> installSubscriber(InstallSubscriberRequest installSubscriberRequest) {

        CCATLogger.DEBUG_LOGGER.debug("Calling air for installing subscriber");
        Mono<BaseResponse> responseAsync = webClient.post()
                .uri(properties.getAirServiceUrls()
                        + Defines.ContextPaths.AIR_SERVICE_CONTEXT_PATH
                        + Defines.ContextPaths.ADVANCED
                        + Defines.WEB_ACTIONS.ADD)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(installSubscriberRequest))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                }).log();

        CCATLogger.DEBUG_LOGGER.debug("Calling air for installing subscriber done successfully");
        return responseAsync;

    }

    public Mono<BaseResponse> disconnectSubscriber(DisconnectSubscriberRequest disconnectSubscriberRequest) throws GatewayException {

        CCATLogger.INTERFACE_LOGGER.info("request is [" + "disconnectFromCharging:" + disconnectSubscriberRequest.getDisconnectFromCharging()
                + ", subscriberMisisdn:" + disconnectSubscriberRequest.getSubscriberMsisdn()
                + ", disconnectReasonId:" + disconnectSubscriberRequest.getDisconnectReasonId()
                + ", isBatch:" + disconnectSubscriberRequest.getIsBatch()
                + ", validateDisconnection:" + disconnectSubscriberRequest.getValidateDisconnection()
                + "]");
        CCATLogger.DEBUG_LOGGER.debug("Calling air for diconnecting subscriber");
        Mono<BaseResponse> responseAsync = webClient.post()
                .uri(properties.getAirServiceUrls()
                        + Defines.ContextPaths.AIR_SERVICE_CONTEXT_PATH
                        + Defines.ContextPaths.ADVANCED
                        + Defines.WEB_ACTIONS.DELETE)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(disconnectSubscriberRequest))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                }).log();
        CCATLogger.INTERFACE_LOGGER.info("response is [" + responseAsync + "]");
        CCATLogger.DEBUG_LOGGER.debug("Calling air for disconnecting subscriber done successfully");
        return responseAsync;

    }

}
