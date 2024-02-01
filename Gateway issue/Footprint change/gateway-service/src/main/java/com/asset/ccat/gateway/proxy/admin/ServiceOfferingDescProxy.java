package com.asset.ccat.gateway.proxy.admin;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.service_offering_description.GetAllServiceOfferingDescriptionRequest;
import com.asset.ccat.gateway.models.requests.admin.service_offering_description.UpdateServiceOfferingBitDescRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.service_offering_description.GetAllServiceOfferingDescriptionResponse;
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
public class ServiceOfferingDescProxy {

    @Autowired
    WebClient webClient;

    @Autowired
    Properties properties;


    @LogExecutionTime
    public GetAllServiceOfferingDescriptionResponse getAllServiceOfferingDesc(GetAllServiceOfferingDescriptionRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Started ServiceOfferingDescProxy - getAllServiceOfferingDesc()");
        BaseResponse<GetAllServiceOfferingDescriptionResponse> response = null;
        GetAllServiceOfferingDescriptionResponse getAllServiceOfferingDescriptionResponse = null;
        long start = 0;
        long executionTime;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");


            Mono<BaseResponse<GetAllServiceOfferingDescriptionResponse>> responseAsync = webClient.post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.SERVICE_OFFERING_DESCRIPTION
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetAllServiceOfferingDescriptionResponse>>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            ;


            if (response != null) {

                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {

                    getAllServiceOfferingDescriptionResponse = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving service offering description list " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving service offering  bits description list " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving service offering  bits  description list ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving service offering  bits description list ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }

        return getAllServiceOfferingDescriptionResponse;
    }

    @LogExecutionTime
    public BaseResponse updateServiceOfferingDesc(UpdateServiceOfferingBitDescRequest updateRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Started ServiceOfferingDescProxy - updateServiceOfferingDesc()");
        BaseResponse response = null;
        long start = 0;
        long executionTime;

        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + updateRequest + "]");

            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getLookupsServiceUrls() +
                            Defines.LOOKUP_SERVICE.CONTEXT_PATH +
                            Defines.LOOKUP_SERVICE.SERVICE_OFFERING_DESCRIPTION +
                            Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(updateRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {

                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Updating Service Offering bits Desc " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Updating Service Offering bits Desc " + response);
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
            CCATLogger.DEBUG_LOGGER.info("Error while Updating Service Offering bits Desc  ");
            CCATLogger.ERROR_LOGGER.error("Error while Updating Service Offering bits Desc  ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return response;
    }


}
