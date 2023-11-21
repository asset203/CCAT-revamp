package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.customer_care.advanced.DisconnectSubscriberRequest;
import com.asset.ccat.gateway.models.requests.customer_care.advanced.InstallSubscriberRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.proxy.AdvancedProxy;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.data.redis.serializer.RedisSerializationContext.java;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 *
 * @author marwa.elshawarby
 */
@Component
public class AdvancedService {

    @Autowired
    private AdvancedProxy advancedProxy;

    @Autowired
    private Properties properties;

    public BaseResponse installSubscriber(InstallSubscriberRequest req) throws GatewayException {
        try {
            CCATLogger.DEBUG_LOGGER.debug("Start processing install subscriber request");
            Mono<BaseResponse> respMono = advancedProxy.installSubscriber(req);
            BaseResponse response = respMono.block(Duration.ofMillis(properties.getResponseTimeout())); // TODO take from configuration
//            if (response != null) {
//                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
//                    installSubscriberResponse = response.getPayload();
//                    CCATLogger.info("Install subscriber air request returned successfully with respose " + response);
//                } else {
//                    CCATLogger.info("Installing subscriber air request failed with response | resp: " + response);
//                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage());
//                }
//            }
            CCATLogger.DEBUG_LOGGER.debug("Install subscriber request processed successfully");
            return response;
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while installing Subscriber | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Error while installing Subscriber", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "air-service[" + properties.getAirServiceUrls() + "]");
        }

    }

    public BaseResponse disconnectSubscriber(DisconnectSubscriberRequest req) throws GatewayException {
        try {
            CCATLogger.DEBUG_LOGGER.debug("Start processing disconnect subscriber request");
            req.setIsBatch(false);
            Mono<BaseResponse> respMono = advancedProxy.disconnectSubscriber(req);
            BaseResponse response = respMono.block(Duration.ofMillis(properties.getResponseTimeout())); // TODO take from configuration
            CCATLogger.DEBUG_LOGGER.debug("Disconnect subscriber request processed successfully");
            return response;
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while disconnecting Subscriber | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Error while disconnecting Subscriber", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "air-service[" + properties.getAirServiceUrls() + "]");
        }

    }

}
