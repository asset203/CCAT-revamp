package com.asset.ccat.gateway.proxy.admin;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.account_groups_bits_desc.GetAllAccountGroupsBitsDescRequest;
import com.asset.ccat.gateway.models.requests.admin.account_groups_bits_desc.UpdateAccountGroupsBitsDescRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.account_groups_bits_desc.GetAllAccountGroupsBitsDescResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class AccountGroupsBitsDescProxy {

    @Autowired
    WebClient webClient;

    @Autowired
    Properties properties;


    @LogExecutionTime
    public GetAllAccountGroupsBitsDescResponse getAllAccountGroupsBitsDesc(GetAllAccountGroupsBitsDescRequest getAllAccountGroupsBitsDescRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Started AccountGroupsBitsDescProxy - getAllAccountGroupsBitsDesc()");
        BaseResponse<GetAllAccountGroupsBitsDescResponse> response = null;
        GetAllAccountGroupsBitsDescResponse getAllAccountGroupsBitsDescResponse = null;
        long start = 0;
        long executionTime;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + getAllAccountGroupsBitsDescRequest + "]");


            Mono<BaseResponse<GetAllAccountGroupsBitsDescResponse>> responseAsync = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.ACCOUNT_GROUPS_BITS_DESCRIPTION
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(getAllAccountGroupsBitsDescRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetAllAccountGroupsBitsDescResponse>>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));



            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("result: "+ response);
                    getAllAccountGroupsBitsDescResponse = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving account groups bits description list " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving  account groups bits description list " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving account groups bits description list ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving account groups bits description list ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }

        return getAllAccountGroupsBitsDescResponse;
    }


    @LogExecutionTime
    public BaseResponse updateAccountGroupBitDesc(UpdateAccountGroupsBitsDescRequest updateAccountGroupsBitsDescRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Started AccountGroupsBitsDescProxy - updateAccountGroupBitDesc");
        BaseResponse response = null;
        long start = 0;
        long executionTime;

        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + updateAccountGroupsBitsDescRequest + "]");

            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls() +
                            Defines.LOOKUP_SERVICE.CONTEXT_PATH +
                            Defines.LOOKUP_SERVICE.ACCOUNT_GROUPS_BITS_DESCRIPTION +
                            Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(updateAccountGroupsBitsDescRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {

                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Updating Account groups bits Desc " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Updating Account groups bits Desc " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "executed in " + executionTime + "ms"
                    + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Updating Account groups bits Desc ");
            CCATLogger.ERROR_LOGGER.error("Error while Updating Account groups bits Desc ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }


}
