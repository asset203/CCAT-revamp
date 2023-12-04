package com.asset.ccat.ci_service.proxy;

import com.asset.ccat.ci_service.defines.ErrorCodes;
import com.asset.ccat.ci_service.exceptions.CIServiceException;
import com.asset.ccat.ci_service.logger.CCATLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;

/**
 * @author wael.mohamed
 */
@Component
public class ServiceClassProxy {

    @Autowired
    WebClient webClient;

    public String serviceClassConversion(String urlRequest) throws CIServiceException {
        CCATLogger.DEBUG_LOGGER.debug("[ ServiceClassesProxy -> serviceClassConversion() ] Started successfully.");
        String response = null;
        long executionTime;
        long start = 0;
        try {
            start = System.currentTimeMillis();
            CCATLogger.DEBUG_LOGGER.info("Start call CI " + urlRequest);
            CCATLogger.INTERFACE_LOGGER.debug("Requested Paramter: " + urlRequest);
            Mono<String> responseAsync = webClient.get()
                    .uri(urlRequest)
                    .header(HttpHeaders.CONTENT_TYPE, "text/xml")
                    .retrieve()
                    .bodyToMono(String.class);
            response = responseAsync.block();
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.debug("Response: " + response);
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
            CCATLogger.DEBUG_LOGGER.debug("[ ServiceClassesProxy -> serviceClassConversion() ] response. " + response);
        } catch (WebClientException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while calling CI " + urlRequest);
            CCATLogger.ERROR_LOGGER.error("Error while calling CI " + urlRequest, ex);
            throw new CIServiceException(ErrorCodes.ERROR.UNREACHABLE_EXTERNAL_SERVICE);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while calling CI " + urlRequest);
            CCATLogger.ERROR_LOGGER.error("Error while calling CI " + urlRequest, ex);
            throw new CIServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
        CCATLogger.DEBUG_LOGGER.debug("[ ServiceClassesProxy -> serviceClassConversion() ] End successfully.");
        return response;
    }
}
