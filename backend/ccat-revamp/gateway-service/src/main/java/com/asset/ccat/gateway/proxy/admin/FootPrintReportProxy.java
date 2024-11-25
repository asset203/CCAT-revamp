package com.asset.ccat.gateway.proxy.admin;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.foot_print.GetFootPrintReportRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.foot_print.GetFootPrintReportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class FootPrintReportProxy {
    @Autowired
    Properties properties;

    @Autowired
    WebClient webClient;

    @LogExecutionTime
    public GetFootPrintReportResponse getFootPrintReport(GetFootPrintReportRequest getFootPrintReportRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getFootPrintReport - Call History service");
        GetFootPrintReportResponse getFootPrintReportResponse = null;
        long start;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + getFootPrintReportRequest + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The History Service....");
            Mono<BaseResponse<GetFootPrintReportResponse>> res = webClient.post()
                    .uri(properties.getHistoryServiceUrls()
                            + Defines.ContextPaths.HISTORY_SERVICE_CONTEXT_PATH
                            + Defines.ContextPaths.ADMIN_FOOTPRINT_REPORT
                            + Defines.WEB_ACTIONS.GET)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(getFootPrintReportRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetFootPrintReportResponse>>() {
                    }).log();
            BaseResponse<GetFootPrintReportResponse> response = res.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    getFootPrintReportResponse = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving FootPrintReport" + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Retrieving FootPrintReport" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + getFootPrintReportResponse + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving FootPrintReport ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving FootPrintReport ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "history-service[" + properties.getHistoryServiceUrls() + "]");
        }
        return getFootPrintReportResponse;
    }


    @LogExecutionTime
    public GetFootPrintReportResponse exportFootprintReport(GetFootPrintReportRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("exportFootprintReport Started successfully.");
        GetFootPrintReportResponse getFootPrintReportResponse = null;
        try {
            Mono<BaseResponse<GetFootPrintReportResponse>> responseAsync = webClient
                    .post()
                    .uri(properties.getHistoryServiceUrls()
                            + Defines.ContextPaths.HISTORY_SERVICE_CONTEXT_PATH
                            + Defines.ContextPaths.ADMIN_FOOTPRINT_REPORT
                            + Defines.WEB_ACTIONS.EXPORT)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetFootPrintReportResponse>>() {
                    }).log();

            BaseResponse<GetFootPrintReportResponse> response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    getFootPrintReportResponse = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("exportFootprintReport() Ended  with "
                            + "| Exception [ statusCode : " + response.getStatusCode() + ", statusMessage : " + response.getStatusMessage() + "].");
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving exportFootprintReport " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage());
                }
            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving footprint tables from [History-service] ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving footprint tables from [History-service] ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "History-service");
        }
        CCATLogger.DEBUG_LOGGER.debug("exportFootprintReport() Ended successfully.");

        return getFootPrintReportResponse;
    }
}
