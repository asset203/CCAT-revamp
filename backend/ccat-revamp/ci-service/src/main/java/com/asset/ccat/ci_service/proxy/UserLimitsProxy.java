package com.asset.ccat.ci_service.proxy;

import com.asset.ccat.ci_service.configurations.Properties;
import com.asset.ccat.ci_service.defines.Defines;
import com.asset.ccat.ci_service.defines.ErrorCodes;
import com.asset.ccat.ci_service.exceptions.CIServiceException;
import com.asset.ccat.ci_service.logger.CCATLogger;
import com.asset.ccat.ci_service.models.requests.UpdateLimitRequest;
import com.asset.ccat.ci_service.models.responses.BaseResponse;
import org.apache.http.HttpHeaders;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class UserLimitsProxy {

    private final Properties properties;
    private final WebClient webClient;

    public UserLimitsProxy(Properties properties, WebClient webClient) {
        this.properties = properties;
        this.webClient = webClient;
    }


    public BaseResponse updateLimit(UpdateLimitRequest request) throws CIServiceException {
        CCATLogger.DEBUG_LOGGER.info(" UserLimitsProxy -> updateLimit() Starting Update Limits - call user management serivce");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request);
            CCATLogger.DEBUG_LOGGER.debug("Calling The User Management....");
            String URI = properties.getUserManagementUrls()
                    + Defines.ContextPaths.USER_MANAGEMENT_CONTEXT_PATH
                    + Defines.ContextPaths.LIMITS
                    + Defines.WEB_ACTIONS.UPDATE;
            CCATLogger.DEBUG_LOGGER.debug("UserLimitsProxy -> updateLimit() Calling The User Management With URI : "+URI);
            Mono<BaseResponse> res = webClient.post()
                    .uri(URI)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while checking limits " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while checking limits " + response);
                    throw new CIServiceException(response.getStatusCode(), response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Updating limits ");
            CCATLogger.ERROR_LOGGER.error("Error while Updating limits", ex);
            throw new CIServiceException(ErrorCodes.ERROR.USER_MANAGEMENT_UNREACHABLE, "user-management-service[" + properties.getUserManagementUrls() + "]");
        }
        return response;
    }

}
