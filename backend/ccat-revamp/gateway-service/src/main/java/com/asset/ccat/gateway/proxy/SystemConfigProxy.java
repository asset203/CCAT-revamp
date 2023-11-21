package com.asset.ccat.gateway.proxy;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.system_config.GetAllConfigurationsRequest;
import com.asset.ccat.gateway.models.requests.admin.system_config.UpdateConfigurationsRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.system_config.GetAllConfigurationsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class SystemConfigProxy {

    @Autowired
    WebClient webClient;

    @Autowired
    Properties properties;

    @LogExecutionTime
    public GetAllConfigurationsResponse getAllConfigs(GetAllConfigurationsRequest request) throws GatewayException {
        GetAllConfigurationsResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request get all configurations for profile " + request.getProfile());
            Mono<BaseResponse<GetAllConfigurationsResponse>> res = webClient.post()
                    .uri(properties.getCloudConfigUrls()
                            + Defines.CONFIG_SERVER.SYSTEM_CONFIG
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request)).retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetAllConfigurationsResponse>>() {
                    });

            BaseResponse<GetAllConfigurationsResponse> result = res.block();
            if (result.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                CCATLogger.DEBUG_LOGGER.info("Error while getting system configurations" + result);
                throw new GatewayException(result.getStatusCode(), result.getStatusMessage());
            } else {
                response = result.getPayload();
            }

        }
        //TODO multi catch for runtime exception and gateway exception
        catch (GatewayException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while getting system configurations");
            throw new GatewayException(ErrorCodes.ERROR.UNKNOWN_ERROR, "cloud-config-server");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while getting system configurations");
            CCATLogger.ERROR_LOGGER.error("Error while getting system configurations ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "cloud-config-server");
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.info("Error while getting system configurations");
            CCATLogger.DEBUG_LOGGER.error("Error while getting system configurations", e);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "cloud-config-server");
        }
        return response;
    }

    @LogExecutionTime
    public void updateSystemConfigurations(UpdateConfigurationsRequest request) throws GatewayException {
        try {
            CCATLogger.DEBUG_LOGGER.info("Starting updateSystemConfigurations - call Config Server");
            CCATLogger.INTERFACE_LOGGER.info("request update configurations for profile " + request.getProfile());
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getCloudConfigUrls()
                            + Defines.CONFIG_SERVER.SYSTEM_CONFIG
                            + Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request)).retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    });

            BaseResponse result = res.block();
            if (result.getStatusCode() != null && result.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                CCATLogger.DEBUG_LOGGER.info("Error while updating system configurations" + result);
                throw new GatewayException(result.getStatusCode(), result.getStatusMessage());
            } else {
                CCATLogger.DEBUG_LOGGER.info("Updated system configurations successfully");
            }
            CCATLogger.DEBUG_LOGGER.info("Finished updateSystemConfigurations - call Config Server");

        } catch (GatewayException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while getting system configurations");
            throw ex;
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while updating system configurations");
            CCATLogger.ERROR_LOGGER.error("Error while updating system configurations ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "cloud-config-server");
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.info("Error while updating system configurations");
            CCATLogger.DEBUG_LOGGER.error("Error while updating system configurations", e);
            throw new GatewayException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }
}
