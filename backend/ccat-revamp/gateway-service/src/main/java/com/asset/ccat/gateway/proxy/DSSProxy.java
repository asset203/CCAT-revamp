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
import com.asset.ccat.gateway.models.customer_care.DSSReportModel;
import com.asset.ccat.gateway.models.requests.customer_care.history.DSSReportRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
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
 * @author wael.mohamed
 */
@Component
public class DSSProxy {

    @Autowired
    WebClient webClient;

    @Autowired
    Properties properties;

    @LogExecutionTime
    public DSSReportModel getTrafficBehaviorReport(DSSReportRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getTrafficBehaviorReport - call ods serivce");
        DSSReportModel reportModel = null;
        BaseResponse<DSSReportModel> response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "msisdn:" + request.getMsisdn()
                    + ", btivr:" + request.getBtivr()
                    + ", dateFrom:" + request.getDateFrom()
                    + ", dateTo:" + request.getDateTo()
                    + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The ODS Service....");
            Mono<BaseResponse<DSSReportModel>> res = webClient.post()
                    .uri(properties.getOdsServiceUrls()
                            + Defines.ODS_SERVICE.CONTEXT_PATH
                            + Defines.ODS_SERVICE.DSS_REPORT
                            + Defines.ODS_SERVICE.TRAFFIC_BEHAVIOR
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<DSSReportModel>>() {
                    }).log();
            response = res.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    reportModel = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while Retriving TrafficBehaviorReport " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Retriving TrafficBehaviorReport " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }

            CCATLogger.INTERFACE_LOGGER.info("response is [" + "externalDescription:" + reportModel.getExternalDescription()
                    + ", externalCode:" + reportModel.getExternalCode()
                    + ", headers:" + reportModel.getHeaders()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving TrafficBehaviorReport ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving TrafficBehaviorReport", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "ods-service [" + properties.getOdsServiceUrls() + "]");
        }
        return reportModel;
    }

    @LogExecutionTime
    public DSSReportModel getBtiVrReport(DSSReportRequest request) throws GatewayException {

        CCATLogger.DEBUG_LOGGER.info("Starting getBtiVrReport - call ods serivce");
        DSSReportModel reportModel = null;
        BaseResponse<DSSReportModel> response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "msisdn:" + request.getMsisdn()
                    + ", btivr:" + request.getBtivr()
                    + ", dateFrom:" + request.getDateFrom()
                    + ", dateTo:" + request.getDateTo()
                    + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The ODS Service....");
            Mono<BaseResponse<DSSReportModel>> res = webClient.post()
                    .uri(properties.getOdsServiceUrls()
                            + Defines.ODS_SERVICE.CONTEXT_PATH
                            + Defines.ODS_SERVICE.DSS_REPORT
                            + Defines.ODS_SERVICE.BTIVR_REPORT
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<DSSReportModel>>() {
                    }).log();
            response = res.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    reportModel = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while Retriving BtiVrReport " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Retriving BtiVrReport " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "externalDescription:" + reportModel.getExternalDescription()
                    + ", externalCode:" + reportModel.getExternalCode()
                    + ", headers:" + reportModel.getHeaders()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving BtiVrReport ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving BtiVrReport", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "ods-service [" + properties.getOdsServiceUrls() + "]");
        }
        return reportModel;

    }

    @LogExecutionTime
    public DSSReportModel getUSSDReport(DSSReportRequest request) throws GatewayException {

        CCATLogger.DEBUG_LOGGER.info("Starting getUSSDReport - call ods serivce");
        DSSReportModel reportModel = null;
        BaseResponse<DSSReportModel> response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "msisdn:" + request.getMsisdn()
                    + ", btivr:" + request.getBtivr()
                    + ", dateFrom:" + request.getDateFrom()
                    + ", dateTo:" + request.getDateTo()
                    + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The ODS Service....");
            Mono<BaseResponse<DSSReportModel>> res = webClient.post()
                    .uri(properties.getOdsServiceUrls()
                            + Defines.ODS_SERVICE.CONTEXT_PATH
                            + Defines.ODS_SERVICE.DSS_REPORT
                            + Defines.ODS_SERVICE.USSD_REPORT
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<DSSReportModel>>() {
                    }).log();
            response = res.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    reportModel = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while Retriving USSDReport " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Retriving USSDReport " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "externalDescription:" + reportModel.getExternalDescription()
                    + ", externalCode:" + reportModel.getExternalCode()
                    + ", headers:" + reportModel.getHeaders()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving USSDReport ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving USSDReport", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "ods-service [" + properties.getOdsServiceUrls() + "]");
        }
        return reportModel;
    }

}
