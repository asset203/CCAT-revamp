package com.asset.ccat.ci_service.proxy;

import com.asset.ccat.ci_service.configurations.Properties;
import com.asset.ccat.ci_service.defines.ErrorCodes;
import com.asset.ccat.ci_service.exceptions.CIServiceException;
import com.asset.ccat.ci_service.logger.CCATLogger;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class ChargingInterfaceProxy {


    private final WebClient webClient;

    private final Properties properties;

    public ChargingInterfaceProxy(WebClient webClient, Properties properties) {
        this.webClient = webClient;
        this.properties = properties;
    }


    public String chargingRequest(String urlRequest, String callerService) throws CIServiceException {
        CCATLogger.DEBUG_LOGGER.debug("[ ChargingProxy -> chargingRequest() for "+callerService+" ] Started successfully.");
        String response = null;
        try {
            CCATLogger.DEBUG_LOGGER.info("Start call CI " + urlRequest);
            Mono<String> responseAsync = webClient.get()
                    .uri(urlRequest)
                    .retrieve()
                    .bodyToMono(String.class);
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            CCATLogger.INTERFACE_LOGGER.debug("Response: " + response);
            CCATLogger.DEBUG_LOGGER.debug("[ ChargingProxy -> chargingRequest() for "+callerService+" ] response. " + response);
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while calling CI " + urlRequest,ex);
            CCATLogger.ERROR_LOGGER.error("Error while calling CI " + urlRequest, ex);
            throw new CIServiceException(ErrorCodes.ERROR.UNREACHABLE_EXTERNAL_SERVICE);
        }
        CCATLogger.DEBUG_LOGGER.debug("[ ChargingProxy -> chargingRequest() for "+callerService+" ] End successfully.");
        return response;
    }
}
