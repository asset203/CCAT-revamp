package com.asset.ccat.ci_service.proxy;

import com.asset.ccat.ci_service.defines.ErrorCodes;
import com.asset.ccat.ci_service.exceptions.CIServiceException;
import com.asset.ccat.ci_service.logger.CCATLogger;
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

    private final WebClient webClient;

    public ServiceClassProxy(WebClient webClient) {
        this.webClient = webClient;
    }

    public String serviceClassConversion(String urlRequest) throws CIServiceException {
        long executionTime;
        long start = System.currentTimeMillis();
        try {
            CCATLogger.INTERFACE_LOGGER.debug("Start calling CI with URL = {}", urlRequest);
            Mono<String> responseAsync = webClient.get()
                    .uri(urlRequest)
                    .header(HttpHeaders.CONTENT_TYPE, "text/xml")
                    .retrieve()
                    .bodyToMono(String.class);
            String response = responseAsync.block();
            CCATLogger.INTERFACE_LOGGER.debug("CI-Response: {}", response);
            return response;
        } catch (WebClientException ex) {
            CCATLogger.DEBUG_LOGGER.error("WebClientException while calling CI ", ex);
            CCATLogger.ERROR_LOGGER.error("WebClientException while calling CI ", ex);
            throw new CIServiceException(ErrorCodes.ERROR.UNREACHABLE_EXTERNAL_SERVICE);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception while calling CI ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception while calling CI ", ex);
            throw new CIServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        } finally {
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("Execution time = {} ms.", executionTime);
            CCATLogger.DEBUG_LOGGER.info("Execution time = {} ms.", executionTime);
        }
    }
}
