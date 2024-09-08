/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.proxy;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.LoginRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.LoginWrapperModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author Mahmoud Shehab
 */
@Component
public class UserManagementServiceProxy {

    @Autowired
    WebClient webClient;

    @Autowired
    Properties properties;

    @LogExecutionTime
    public LoginWrapperModel userLogin(LoginRequest loginRequest) throws GatewayException {
        LoginWrapperModel response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "password:" + loginRequest.getPassword()
                    + ", username:" + loginRequest.getUsername()
                    + "]");
            //"http://localhost:8081/user-management-service/login"
            Mono<BaseResponse<LoginWrapperModel>> res = webClient.post()
                    .uri(properties.getUserManagementUrls()
                            + Defines.ContextPaths.USER_MANAGEMENT_CONTEXT_PATH
                            + Defines.WEB_ACTIONS.LOGIN)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(loginRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<LoginWrapperModel>>() {
                    });

            BaseResponse<LoginWrapperModel> result = res.block();
            if (result.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                CCATLogger.DEBUG_LOGGER.info("Error while login " + result);
                throw new GatewayException(result.getStatusCode(), result.getStatusMessage());
            } else {
                response = result.getPayload();
            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while login");
            CCATLogger.ERROR_LOGGER.error("Error while login ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "user-service");
        }
        return response;
    }

}