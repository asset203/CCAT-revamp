package com.asset.ccat.balancedisputemapperservice.proxy;

import com.asset.ccat.balancedisputemapperservice.annotation.LogExecutionTime;
import com.asset.ccat.balancedisputemapperservice.configurations.Properties;
import com.asset.ccat.balancedisputemapperservice.defines.Defines;
import com.asset.ccat.balancedisputemapperservice.defines.ErrorCodes;
import com.asset.ccat.balancedisputemapperservice.exceptions.BalanceDisputeException;
import com.asset.ccat.balancedisputemapperservice.loggers.CCATLogger;
import com.asset.ccat.balancedisputemapperservice.models.requests.CheckUserPrivilegeRequest;
import com.asset.ccat.balancedisputemapperservice.models.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author marwa.elshawarby
 */

@Component
public class UserManagmentProxy {
    WebClient webClient;
    Properties properties;

    @Autowired
    public UserManagmentProxy(WebClient webClient, Properties properties) {
        this.webClient = webClient;
        this.properties = properties;
    }

    @LogExecutionTime
    public Boolean userHasPrivilege(CheckUserPrivilegeRequest request) throws BalanceDisputeException {
        Boolean check = null;
        try {
            String URI = properties.getUserManagementUrls()
                    + Defines.ContextPaths.USER_MANAGEMENT
                    + Defines.ContextPaths.USER
                    + Defines.ContextPaths.USER_PRIVILEGE
                    + Defines.WEB_ACTIONS.CHECK;
            CCATLogger.DEBUG_LOGGER.info("Calling user management service With URI : " + URI);
            Mono<BaseResponse<Boolean>> responseAsync = webClient.post()
                    .uri(URI)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<Boolean>>() {
                    }).log();
            BaseResponse<Boolean> response = responseAsync.block();
            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    check = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info(
                            "Error checking for user privilege " + response);
                    return null;
                }
            }
            CCATLogger.INTERFACE_LOGGER.debug("Response: " + check);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while checking user privilege ");
            CCATLogger.ERROR_LOGGER.error("Error while checking user privilege ", ex);
            throw new BalanceDisputeException(ErrorCodes.ERROR.USER_MANAGEMENT_SERVICE_UNREACHABLE,
                    null, "BD-Mapper-Proxy[" + properties.getUserManagementUrls() + "]");
        }
        return check;
    }
}
