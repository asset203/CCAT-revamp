package com.asset.ccat.gateway.proxy;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.customer_care.voucher.*;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.customer_care.CheckVoucherNumberResponse;
import com.asset.ccat.gateway.models.responses.customer_care.GetPaymentGatewayVoucherResponse;
import com.asset.ccat.gateway.models.responses.customer_care.GetVoucherDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class VoucherProxy {

    @Autowired
    private WebClient webClient;

    @Autowired
    private Properties properties;

    @LogExecutionTime
    public GetVoucherDetailsResponse getVoucherDetails(GetVoucherDetailsRequest request) throws GatewayException {
        BaseResponse<GetVoucherDetailsResponse> response;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "msisdn:" + request.getMsisdn()
                    + ", voucherSerialNumber:" + request.getVoucherSerialNumber()
                    + ", serverId:" + request.getServerId()
                    + "]");
            CCATLogger.DEBUG_LOGGER.info("Start getting voucher details");
            Mono<BaseResponse<GetVoucherDetailsResponse>> responseAsync = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.ContextPaths.AIR_SERVICE_CONTEXT_PATH
                            + Defines.AIR_SERVICE.VOUCHER
                            + Defines.WEB_ACTIONS.GET)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetVoucherDetailsResponse>>() {
                    }).log();
            response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while getting voucher details " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while getting voucher details" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while getting voucher details ");
            CCATLogger.ERROR_LOGGER.error("Error while getting voucher details ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "air-service[" + properties.getAirServiceUrls() + "]");
        }
        return response.getPayload();
    }

    @LogExecutionTime
    public void updateVoucherState(UpdateVoucherStateRequest request) throws GatewayException {
        BaseResponse response;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "msisdn:" + request.getMsisdn()
                    + ", voucherNumber:" + request.getVoucherSerialNumber()
                    + ", currentState:" + request.getCurrentState()
                    + ", newState:" + request.getNewState()
                    + ", serverId:" + request.getServerId()
                    + "]");
            CCATLogger.DEBUG_LOGGER.info("Start updating voucher state");
            Mono<BaseResponse> responseAsync = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.ContextPaths.AIR_SERVICE_CONTEXT_PATH
                            + Defines.AIR_SERVICE.VOUCHER
                            + Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while updating voucher state " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while updating voucher state" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while updating voucher state");
            CCATLogger.ERROR_LOGGER.error("Error while updating voucher state ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "air-service[" + properties.getAirServiceUrls() + "]");
        }
    }

    @LogExecutionTime
    public void submitVoucherBasedRefill(VoucherBasedRefillRequest request) throws GatewayException {
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "msisdn:" + request.getMsisdn()
                    + ", voucherNumber:" + request.getVoucherNumber()
                    + ", isMaredCared:" + request.getIsMaredCard()
                    + ", selectedMaredCared:" + request.getSelectedMaredCard()
                    + "]");
            CCATLogger.DEBUG_LOGGER.info("Start sending air service request - submit voucher based refill");
            Mono<BaseResponse> responseAsync = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.ContextPaths.AIR_SERVICE_CONTEXT_PATH
                            + Defines.AIR_SERVICE.VOUCHER
                            + Defines.AIR_SERVICE.VOUCHER_BASED_REFILL
                            + Defines.WEB_ACTIONS.SUBMIT)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while sending air service request - submit voucher based refill " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while sending air service request - submit voucher based refill " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + "]");
            CCATLogger.DEBUG_LOGGER.info("Finished sending air service request - submit voucher based refill");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while sending air service request - submit voucher based refill");
            CCATLogger.ERROR_LOGGER.error("Error while sending air service request - submit voucher based refill ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "air-service[" + properties.getAirServiceUrls() + "]");
        }
    }

    @LogExecutionTime
    public CheckVoucherNumberResponse checkVoucherNumber(CheckVoucherNumberRequest request) throws GatewayException {
        BaseResponse<CheckVoucherNumberResponse> response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            CCATLogger.DEBUG_LOGGER.info("Start sending air service request - check voucher number");
            Mono<BaseResponse<CheckVoucherNumberResponse>> responseAsync = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.ContextPaths.AIR_SERVICE_CONTEXT_PATH
                            + Defines.AIR_SERVICE.VOUCHER
                            + Defines.WEB_ACTIONS.CHECK)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<CheckVoucherNumberResponse>>() {
                    }).log();
            response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while sending air service request - check voucher number " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while sending air service request - check voucher number " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
            CCATLogger.DEBUG_LOGGER.info("Finished sending air service request - check voucher number");
            return response.getPayload();

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while sending air service request - check voucher number");
            CCATLogger.ERROR_LOGGER.error("Error while sending air service request - check voucher number ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "air-service[" + properties.getAirServiceUrls() + "]");
        }
    }

    @LogExecutionTime
    public GetPaymentGatewayVoucherResponse getPaymentGatewayVoucher(GetPaymentGatewayVoucherRequest request) throws GatewayException {
        BaseResponse<GetPaymentGatewayVoucherResponse> response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");

            String URI = properties.getProcedureServiceUrls()
                    + Defines.ContextPaths.PROCEDURE_SERVICE_CONTEXT_PATH
                    + Defines.PROCEDURE_SERVICE.VOUCHER
                    + Defines.PROCEDURE_SERVICE.PAYMENT_GATEWAY_VOUCHER
                    + Defines.WEB_ACTIONS.GET;
            CCATLogger.DEBUG_LOGGER.info("Start calling procedure service request - get payment gateway voucher on URI : "+URI);
            Mono<BaseResponse<GetPaymentGatewayVoucherResponse>> responseAsync = webClient.post()
                    .uri(URI)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetPaymentGatewayVoucherResponse>>() {
                    }).log();
            response = responseAsync.block();
            if (Objects.nonNull(response)) {
                if (!response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    CCATLogger.DEBUG_LOGGER.info("Error while sending procedure service request - get payment gateway voucher " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while sending procedure service request - get payment gateway voucher " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");
            CCATLogger.DEBUG_LOGGER.info("Finished sending procedure service request - get payment gateway voucher ");
            return response.getPayload();

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while sending procedure service request - get payment gateway voucher ");
            CCATLogger.ERROR_LOGGER.error("Error while sending procedure service request - get payment gateway voucher  ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "procedure-service[" + properties.getProcedureServiceUrls() + "]");
        }
    }

}

