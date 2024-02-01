package com.asset.ccat.gateway.proxy;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.customer_care.PaymentProfileModel;
import com.asset.ccat.gateway.models.requests.customer_care.voucher_refill.SubmitVoucherlessRefillRequest;
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
public class VoucherlessRefillProxy {

    @Autowired
    WebClient webClient;

    @Autowired
    Properties properties;

    @LogExecutionTime
    public List<PaymentProfileModel> getAllPaymentsProfiles() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getAllPaymentsProfiles - call Config Server");
        List<PaymentProfileModel> getAllPaymentProfilesResponse = null;
        try {
            CCATLogger.DEBUG_LOGGER.debug("Calling The Config Server....");
            Mono<BaseResponse<PaymentProfileModel[]>> res = webClient.get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                            + Defines.ContextPaths.REFILL_PROFILES)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<PaymentProfileModel[]>>() {
                    }).log();

            BaseResponse<PaymentProfileModel[]> response = res.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    getAllPaymentProfilesResponse = Arrays.stream(response.getPayload()).collect(Collectors.toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving PaymentsProfiles " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            for (var paymentProfiles : getAllPaymentProfilesResponse) {
                CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                        + ", statusCode: " + response.getStatusCode()
                        + ", payload: " + response.getPayload()
                        + ", payload:["
                        + ", paymentProfiles:["
                        + ", id: " + paymentProfiles.getProfileId()
                        + ", name: " + paymentProfiles.getProfileName()
                        + ", amountFrom: " + paymentProfiles.getAmountFrom()
                        + ", amountTo: " + paymentProfiles.getAmountTo()
                        + "]"
                        + "]"
                        + "]");
            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while update Dedicated accounts  ");
            CCATLogger.ERROR_LOGGER.error("Error while update Dedicated accounts ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service");
        }
        return getAllPaymentProfilesResponse;
    }

    @LogExecutionTime
    public BaseResponse submitVoucherlessRefill(SubmitVoucherlessRefillRequest submitVoucherlessRefillRequest) throws GatewayException {
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "msisdn:" + submitVoucherlessRefillRequest.getMsisdn()
                    + ", paymentProfileId:" + submitVoucherlessRefillRequest.getPaymentProfileId()
                    + ", amount:" + submitVoucherlessRefillRequest.getAmount()
                    + "]");
            CCATLogger.DEBUG_LOGGER.info("Start submitting VoucherlessRefill");
            Mono<BaseResponse> responseAsync = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.ContextPaths.AIR_SERVICE_CONTEXT_PATH
                            + Defines.ContextPaths.VOUCHERLESS_REFILL
                            + Defines.WEB_ACTIONS.SUBMIT)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(submitVoucherlessRefillRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while submitting VoucherlessRefill " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while submitting VoucherlessRefill" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving PaymentsProfiles ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving PaymentsProfiles ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "air-service[" + properties.getAirServiceUrls() + "]");
        }
        return response;

    }
}
