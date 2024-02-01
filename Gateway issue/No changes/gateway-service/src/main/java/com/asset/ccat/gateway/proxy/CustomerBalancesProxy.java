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
import com.asset.ccat.gateway.models.customer_care.AccumulatorModel;
import com.asset.ccat.gateway.models.customer_care.DedicatedAccount;
import com.asset.ccat.gateway.models.requests.*;
import com.asset.ccat.gateway.models.requests.admin.user.CheckLimitRequest;
import com.asset.ccat.gateway.models.requests.admin.user.UpdateLimitRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;

import java.time.Duration;
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
 * @author Mahmoud Shehab
 */
@Component
public class CustomerBalancesProxy {

    @Autowired
    Properties properties;

    @Autowired
    WebClient webClient;

    @LogExecutionTime
    public List<DedicatedAccount> getDedicatedAccounts(GetDedicatedAccountsRequest request) throws GatewayException {
        List<DedicatedAccount> model = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "msisdn:" + request.getMsisdn()
                    + "]");
            Mono<BaseResponse<DedicatedAccount[]>> responseAsync = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.ContextPaths.AIR_SERVICE_CONTEXT_PATH
                            + Defines.ContextPaths.BALANCE_AND_DATE
                            + Defines.WEB_ACTIONS.GET)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<DedicatedAccount[]>>() {
                    }).log();
            BaseResponse<DedicatedAccount[]> response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    model = Arrays.stream(response.getPayload()).collect(Collectors.toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving getDedicatedAccounts " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + model + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving getDedicatedAccounts ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving getDedicatedAccounts ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "air-service[" + properties.getAirServiceUrls() + "]");
        }
        return model;
    }

    @LogExecutionTime
    public List<AccumulatorModel> getAccumulators(SubscriberRequest subscriberRequest) throws GatewayException {
        List<AccumulatorModel> model = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + subscriberRequest.getMsisdn() + "]");
            Mono<BaseResponse<AccumulatorModel[]>> responseAsync = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.ContextPaths.AIR_SERVICE_CONTEXT_PATH
                            + Defines.ContextPaths.ACCUMULATORS
                            + Defines.WEB_ACTIONS.GET)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(subscriberRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<AccumulatorModel[]>>() {
                    }).log();
            BaseResponse<AccumulatorModel[]> response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    model = Arrays.stream(response.getPayload()).collect(Collectors.toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving AccumulatorModel " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + model + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving AccumulatorModel ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving AccumulatorModel ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "air-service[" + properties.getAirServiceUrls() + "]");
        }
        return model;
    }

    @LogExecutionTime
    public void updateBalanceAndDates(UpdateBalanceAndDateRequest balanceAndDateRequest) throws GatewayException {
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "msisdn: " + balanceAndDateRequest.getMsisdn()
                    + ", transactionCode: " + balanceAndDateRequest.getTransactionCode()
                    + ", transactionType: " + balanceAndDateRequest.getTransactionType()
                    + ", adjusmentAmount: " + balanceAndDateRequest.getAdjustmentAmount()
                    + ", adjusmentMethod: " + balanceAndDateRequest.getAdjustmentMethod()
                    + ", serviceFeeDate: " + balanceAndDateRequest.getServiceFeeDate()
                    + ", serviceFeePeriod: " + balanceAndDateRequest.getServiceFeePeriod()
                    + ", serviceFeePeriodOld: " + balanceAndDateRequest.getServiceFeePeriodOld()
                    + ", supervisionFeeDate: " + balanceAndDateRequest.getSupervisionFeeDate()
                    + "]");
            Mono<BaseResponse> responseAsync = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.ContextPaths.AIR_SERVICE_CONTEXT_PATH
                            + Defines.ContextPaths.BALANCE_AND_DATE
                            + Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(balanceAndDateRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while update Balance and date " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while update Balance and date " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving AccumulatorModel ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving AccumulatorModel ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "air-service");
        }
    }

    @LogExecutionTime
    public void updateDedicatedAccounts(UpdateDedicatedBalanceRequest dedicatedBalanceRequest) throws GatewayException {
        BaseResponse response = null;
        try {
            for (var dedicatedAccounts : dedicatedBalanceRequest.getList()) {
                CCATLogger.INTERFACE_LOGGER.info("request is [" + "msisdn: " + dedicatedBalanceRequest.getMsisdn()
                        + ", transactionCode: " + dedicatedBalanceRequest.getTransactionCode()
                        + ", transactionType: " + dedicatedBalanceRequest.getTransactionType()
                        + ",dedicatedAccounts ["
                        + ", adjusmentAmount: " + dedicatedAccounts.getAdjustmentAmount()
                        + ", adjusmentMethod: " + dedicatedAccounts.getAdjustmentMethod()
                        + ", expiryDate: " + dedicatedAccounts.getExpiryDate()
                        + ", id: " + dedicatedAccounts.getId()
                        + ", idDateEdited: " + dedicatedAccounts.getIsDateEdited()
                        + ", unitType: " + dedicatedAccounts.getUnitType()
                        + "]"
                        + "]");
            }
            Mono<BaseResponse> responseAsync = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.ContextPaths.AIR_SERVICE_CONTEXT_PATH
                            + Defines.ContextPaths.DEDICATED_ACCOUNTS
                            + Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(dedicatedBalanceRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while update Dedicated accounts " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Dedicated accounts " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while update Dedicated accounts  ");
            CCATLogger.ERROR_LOGGER.error("Error while update Dedicated accounts ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "air-service");
        }
    }

    @LogExecutionTime
    public void updateAccumulators(UpdateAccumulatorsRequest updateAccumulatorsRequest) throws GatewayException {
        BaseResponse response = null;
        try {
            for (var accumulators : updateAccumulatorsRequest.getList()) {
                CCATLogger.INTERFACE_LOGGER.info("request is [" + "msisdn: " + updateAccumulatorsRequest.getMsisdn()
                        + ", transactionCode: " + updateAccumulatorsRequest.getTransactionCode()
                        + ", transactionType: " + updateAccumulatorsRequest.getTransactionType()
                        + ",accumulators ["
                        + ", adjusmentAmount: " + accumulators.getAdjustmentAmount()
                        + ", adjusmentMethod: " + accumulators.getAdjustmentMethod()
                        + ", expiryDate: " + accumulators.getIsReset()
                        + ", id: " + accumulators.getId()
                        + ", idDateEdited: " + accumulators.getStartDate()
                        + "]"
                        + "]");
            }
            Mono<BaseResponse> responseAsync = webClient.post()
                    .uri(properties.getAirServiceUrls()
                            + Defines.ContextPaths.AIR_SERVICE_CONTEXT_PATH
                            + Defines.ContextPaths.ACCUMULATORS
                            + Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(updateAccumulatorsRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while update Dedicated accounts " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Dedicated accounts " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while update Dedicated accounts  ");
            CCATLogger.ERROR_LOGGER.error("Error while update Dedicated accounts ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "air-service");
        }
    }

    @LogExecutionTime
    public BaseResponse checkLimit(CheckLimitRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting Check Limits - call user management serivce");
        request.setBalance(0.0f);
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "actionType: " + request.getActionType()
                    + ", amount: " + request.getAmount()
                    + ", userId: " + request.getUserId()
                    + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The User Management....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getUserManagementUrls()
                            + Defines.ContextPaths.USER_MANAGEMENT_CONTEXT_PATH
                            + Defines.ContextPaths.LIMITS
                            + Defines.WEB_ACTIONS.CHECK)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while checking limits " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while checking limits " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while checking limits ");
            CCATLogger.ERROR_LOGGER.error("Error while checking limits", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "user-management-service[" + properties.getUserManagementUrls() + "]");
        }
        return response;
    }


    @LogExecutionTime
    public BaseResponse updateLimit(UpdateLimitRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info(" CustomerBalancesProxy -> updateLimit() Starting Update Limits - call user management serivce");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request);
            CCATLogger.DEBUG_LOGGER.debug("Calling The User Management....");
            String URI = properties.getUserManagementUrls()
                    + Defines.ContextPaths.USER_MANAGEMENT_CONTEXT_PATH
                    + Defines.ContextPaths.LIMITS
                    + Defines.WEB_ACTIONS.UPDATE;
            CCATLogger.DEBUG_LOGGER.debug("CustomerBalancesProxy -> updateLimit() Calling The User Management With URI : "+URI);
            Mono<BaseResponse> res = webClient.post()
                    .uri(URI)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while checking limits " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while checking limits " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Updating limits ");
            CCATLogger.ERROR_LOGGER.error("Error while Updating limits", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "user-management-service[" + properties.getUserManagementUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse checkTransactionLimit(SubscriberRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting Check TransactionLimit - call history serivce");
        BaseResponse response = null;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request.getMsisdn() + "]");
            CCATLogger.DEBUG_LOGGER.debug("Calling The History Service....");
            Mono<BaseResponse> res = webClient.post()
                    .uri(properties.getHistoryServiceUrls()
                            + Defines.ContextPaths.HISTORY_SERVICE_CONTEXT_PATH
                            + Defines.ContextPaths.SUBSCRIBER_ADJUSTMENT
                            + Defines.WEB_ACTIONS.CHECK)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while checking TransactionLimit " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while checking TransactionLimit " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while checking TransactionLimit ");
            CCATLogger.ERROR_LOGGER.error("Error while checking TransactionLimit", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "history-service[" + properties.getHistoryServiceUrls() + "]");
        }
        return response;
    }
}
