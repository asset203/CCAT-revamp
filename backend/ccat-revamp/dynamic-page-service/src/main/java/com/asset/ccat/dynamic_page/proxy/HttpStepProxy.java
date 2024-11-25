package com.asset.ccat.dynamic_page.proxy;

import com.asset.ccat.dynamic_page.configurations.Properties;
import com.asset.ccat.dynamic_page.constants.HTTPMethodType;
import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.models.dynamic_page.HTTPRequestWrapperModel;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

@Component
public class HttpStepProxy {

    private final WebClient webClient;

    private final Properties properties;

    public HttpStepProxy(WebClient webClient, Properties properties) {
        this.webClient = webClient;
        this.properties = properties;
    }

    public String sendHTTPRequest(HTTPRequestWrapperModel request) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started HttpStepProxy - sendHTTPRequest()");
        CCATLogger.DEBUG_LOGGER.info("Start sending HTTP " + request.getHttpMethod() + " request");
        String response;
        if (request.getHttpMethod() == HTTPMethodType.GET) {
            CCATLogger.DEBUG_LOGGER.debug("Start sending HTTP get request");
            response = sendGetRequest(request);

        } else {
            CCATLogger.DEBUG_LOGGER.debug("Start sending HTTP post request");
            response = sendPostRequest(request);
        }
        CCATLogger.DEBUG_LOGGER.info("Finished sending HTTP request");
        CCATLogger.DEBUG_LOGGER.debug("Ended HttpStepProxy - sendHTTPRequest()");
        return response;
    }

    private String sendGetRequest(HTTPRequestWrapperModel request) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started HttpStepProxy - sendGetRequest()");
        String response;
        try {
            Mono<String> responseAsync = webClient.get()
                    .uri(request.getUrl())
                    .headers(httpHeaders -> {
//                        httpHeaders.add("Content-Type", "application/json");
                        if (request.getHeaders() != null && !request.getHeaders().isEmpty()) {
                            request.getHeaders().forEach(map -> {
                                if (!map.entrySet().iterator().hasNext()) {
                                    return;
                                }
                                Map.Entry entry = (Map.Entry) map.entrySet().iterator().next();
                                httpHeaders.add((String) entry.getKey(), (String) entry.getKey());
                            });
                        }
                    })
                    .retrieve()
                    .bodyToMono(String.class);
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while sending HTTP get request [" + request.getUrl() + "]");
            CCATLogger.DEBUG_LOGGER.error("Error while sending HTTP get request [" + request.getUrl() + "]", ex);
            throw new DynamicPageException(ErrorCodes.ERROR.HTTP_CALL_FAILED, Defines.SEVERITY.ERROR, ex.getMessage());
        }
        CCATLogger.DEBUG_LOGGER.debug("Ended HttpStepProxy - sendGetRequest()");
        return response;
    }

    private String sendPostRequest(HTTPRequestWrapperModel request) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Start sending HTTP post request");
        String response;
        try {
            Mono<String> responseAsync = webClient.post()
                    .uri(request.getUrl())
                    .headers(httpHeaders -> {
//                        httpHeaders.add("Content-Type", "application/json");
                        if (request.getHeaders() != null && !request.getHeaders().isEmpty()) {
                            request.getHeaders().forEach(map -> {
                                if (!map.entrySet().iterator().hasNext()) {
                                    return;
                                }
                                Map.Entry entry = (Map.Entry) map.entrySet().iterator().next();
                                httpHeaders.add((String) entry.getKey(), (String) entry.getKey());
                            });
                        }
                    })
                    .body(BodyInserters.fromValue(request.getRequestBody()))
                    .retrieve()
                    .bodyToMono(String.class);
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while sending HTTP post request [" + request.getUrl() + "]");
            CCATLogger.DEBUG_LOGGER.error("Error while sending HTTP post request [" + request.getUrl() + "]", ex);
            throw new DynamicPageException(ErrorCodes.ERROR.HTTP_CALL_FAILED, Defines.SEVERITY.ERROR, ex.getMessage());
        }
        CCATLogger.DEBUG_LOGGER.debug("Finished sending HTTP post request");
        return response;
    }
}
