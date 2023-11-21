package com.asset.ccat.air_service.proxy;

import com.asset.ccat.air_service.annotation.LogExecutionTime;
import com.asset.ccat.air_service.configurations.Properties;
import com.asset.ccat.air_service.defines.Defines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.requests.UpdateLimitRequest;
import com.asset.ccat.air_service.models.responses.BaseResponse;
import org.apache.http.HttpHeaders;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Objects;

@Component
public class UserLimitsProxy {
    private final Properties properties;
    private final WebClient webClient;

    public UserLimitsProxy(Properties properties, WebClient webClient) {
        this.properties = properties;
        this.webClient = webClient;
    }

    @LogExecutionTime
    public BaseResponse updateLimit(UpdateLimitRequest request) throws AIRServiceException {
        BaseResponse response = null;
        try {
            String URI = properties.getUserManagementUrls()
                    + Defines.ContextPaths.USER_MANAGEMENT_CONTEXT_PATH
                    + Defines.ContextPaths.LIMITS
                    + Defines.WEB_ACTIONS.UPDATE;
            CCATLogger.DEBUG_LOGGER.info("Calling The User Management With URI : " + URI);
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            Mono<BaseResponse> res = webClient.post()
                    .uri(URI)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (Objects.nonNull(response)) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while checking limits " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while checking limits " + response);
                    throw new AIRServiceException(response.getStatusCode(), response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.debug("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Updating limits ");
            CCATLogger.ERROR_LOGGER.error("Error while Updating limits", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.LOOKUP_SERVER_UNREACHABLE, "user-management-service[" + properties.getUserManagementUrls() + "]");
        }
        return response;
    }

}
