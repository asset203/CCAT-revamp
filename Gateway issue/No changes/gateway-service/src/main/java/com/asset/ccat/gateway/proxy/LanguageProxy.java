package com.asset.ccat.gateway.proxy;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.customer_care.LanguageModel;
import com.asset.ccat.gateway.models.requests.customer_care.language.UpdateLanguageRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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
 * @author nour.ihab
 */
@Component
public class LanguageProxy {

    @Autowired
    Properties properties;

    @Autowired
    WebClient webClient;

    @LogExecutionTime
    public BaseResponse updateLanguage(UpdateLanguageRequest updateLanguageRequest) throws GatewayException {
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "msidn:" + updateLanguageRequest.getMsisdn()
                    + ", langaugeId:" + updateLanguageRequest.getLanguageId()
                    + "]");
            CCATLogger.DEBUG_LOGGER.info("Start Updating Langauges");
            Mono<BaseResponse> responseAsync = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.ContextPaths.AIR_SERVICE_CONTEXT_PATH
                            + Defines.ContextPaths.LANGUAGE
                            + Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(updateLanguageRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while update Langauge " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while update Langauge" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving Updated Languages ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving Updated Languages ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "air-service[" + properties.getAirServiceUrls() + "]");
        }
        return response;

    }

    @LogExecutionTime
    public List<LanguageModel> getAllLangauges() throws GatewayException {
        List<LanguageModel> getAllLanguageList = null;
        try {
            CCATLogger.DEBUG_LOGGER.info("Start retrieving Lanaguges");

            Mono<BaseResponse<LanguageModel[]>> responseAsync = webClient.get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                            + Defines.LOOKUP_SERVICE.LANGUAGE)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<LanguageModel[]>>() {
                    }).log();

            BaseResponse<LanguageModel[]> response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    getAllLanguageList = Arrays.stream(response.getPayload()).collect(Collectors.toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving getServiceClasses " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            for (var lanagauge : getAllLanguageList) {
                CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                        + ", statusCode: " + response.getStatusCode()
                        + ", payload: " + response.getPayload()
                        + ", payload:["
                        + ", langauges:["
                        + ", key: " + lanagauge.getKey()
                        + ", value: " + lanagauge.getValue()
                        + "]"
                        + "]"
                        + "]");
            }

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving  Languages ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving  Languages ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service");
        }
        return getAllLanguageList;

    }
}
