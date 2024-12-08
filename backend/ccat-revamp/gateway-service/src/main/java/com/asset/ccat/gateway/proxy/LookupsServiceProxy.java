package com.asset.ccat.gateway.proxy;

import static java.util.stream.Collectors.toList;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.admin.DisconnectionCodeModel;
import com.asset.ccat.gateway.models.admin.ReasonActivityModel;
import com.asset.ccat.gateway.models.admin.vip.PageModel;
import com.asset.ccat.gateway.models.customer_care.LkOfferModel;
import com.asset.ccat.gateway.models.customer_care.service_offering_lookup_models.ServiceOfferingPlanBitDetailsModel;
import com.asset.ccat.gateway.models.ods_models.ODSActivityHeader;
import com.asset.ccat.gateway.models.requests.GetBarringReasonRequest;
import com.asset.ccat.gateway.models.requests.lookup.GetSmsActionParamMapRequest;
import com.asset.ccat.gateway.models.requests.lookup.GetSmsActionsRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.customer_care.GetBarringReasonResponse;
import com.asset.ccat.gateway.models.responses.lookup.GetODSActivityHeaderResponse;
import com.asset.ccat.gateway.models.responses.lookup.GetSmsActionParamMapResponse;
import com.asset.ccat.gateway.models.responses.lookup.GetSmsActionsResponse;
import com.asset.ccat.gateway.models.shared.*;
import com.asset.ccat.gateway.models.users.LkFeatureModel;
import com.asset.ccat.gateway.models.users.LkHLRProfileModel;
import com.asset.ccat.gateway.models.users.LkMonetaryLimitModel;
import com.asset.ccat.gateway.models.users.MenuItem;

import java.time.Duration;
import java.util.*;
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
 * @author marwa.elshawarby
 */
@Component
public class LookupsServiceProxy {

    @Autowired
    WebClient webClient;
    @Autowired
    Properties properties;

    @LogExecutionTime
    public List<LkFeatureModel> getFeaturesLookup() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started retrieving features lookup from lookup service.");
        List<LkFeatureModel> list = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            Mono<BaseResponse<LkFeatureModel[]>> responseAsync = webClient
                    .get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                            + Defines.LOOKUP_SERVICE.FEATURES)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<LkFeatureModel[]>>() {
                    }).log();

            BaseResponse<LkFeatureModel[]> response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    CCATLogger.DEBUG_LOGGER.debug("Retrieving features request returned successfully.");
                    list = Arrays.stream(response.getPayload()).collect(Collectors.toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("LookupServiceProxy -> getFeaturesLookup() Ended  with "
                            + "| Exception [ statusCode : " + response.getStatusCode() + ", statusMessage : " + response.getStatusMessage() + "].");
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving features lookup " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
                CCATLogger.DEBUG_LOGGER.debug("LookupServiceProxy -> getFeaturesLookup() Ended successfully.");

            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + list + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving features lookup from [lookup-service] ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving features lookup from [lookup-service] ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service");
        }
        return list;
    }

    @LogExecutionTime
    public List<LkMonetaryLimitModel> getMonetaryLimitsLookup() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started retrieving monetary limits lookup from lookup service.");
        List<LkMonetaryLimitModel> list = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            Mono<BaseResponse<LkMonetaryLimitModel[]>> responseAsync = webClient
                    .get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                            + Defines.LOOKUP_SERVICE.MONETARY_LIMITS)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<LkMonetaryLimitModel[]>>() {
                    }).log();

            BaseResponse<LkMonetaryLimitModel[]> response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    CCATLogger.DEBUG_LOGGER.debug("Retrieving monetary limits request returned successfully.");
                    list = Arrays.stream(response.getPayload()).collect(Collectors.toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("LookupServiceProxy -> getMonetaryLimitsLookup() Ended  with "
                            + "| Exception [ statusCode : " + response.getStatusCode() + ", statusMessage : " + response.getStatusMessage() + "].");
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving monetary limits lookup " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
                CCATLogger.DEBUG_LOGGER.debug("LookupServiceProxy -> getMonetaryLimitsLookup() Ended successfully.");
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + list + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving monetary limits lookup from [lookup-service] ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving monetary limits lookup from [lookup-service] ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service");
        }
        return list;
    }

    @LogExecutionTime
    public List<MenuItem> getMenusLookup() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started retrieving menus lookup from lookup service.");
        List<MenuItem> list = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            Mono<BaseResponse<MenuItem[]>> responseAsync = webClient
                    .get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                            + Defines.LOOKUP_SERVICE.MENUS)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<MenuItem[]>>() {
                    }).log();

            BaseResponse<MenuItem[]> response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    CCATLogger.DEBUG_LOGGER.debug("Retrieving menus request returned successfully.");
                    list = Arrays.stream(response.getPayload()).collect(Collectors.toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("LookupServiceProxy -> getMenusLookup() Ended  with "
                            + "| Exception [ statusCode : " + response.getStatusCode() + ", statusMessage : " + response.getStatusMessage() + "].");
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving menus lookup " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
                CCATLogger.DEBUG_LOGGER.debug("LookupServiceProxy -> getMenusLookup() Ended successfully.");
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + list + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving menus lookup from [lookup-service] ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving menus lookup from [lookup-service] ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service");
        }
        return list;
    }

    @LogExecutionTime
    public List<LkOfferModel> getOffersLookup() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started retrieving offers lookup from lookup service.");
        List<LkOfferModel> list = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            Mono<BaseResponse<LkOfferModel[]>> responseAsync = webClient
                    .get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                            + Defines.LOOKUP_SERVICE.OFFERS)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<LkOfferModel[]>>() {
                    }).log();

            BaseResponse<LkOfferModel[]> response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    CCATLogger.DEBUG_LOGGER.debug("Retrieving offers request returned successfully.");
                    list = Arrays.stream(response.getPayload()).collect(Collectors.toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("getOffersLookup() Ended  with "
                            + "| Exception [ statusCode : " + response.getStatusCode() + ", statusMessage : " + response.getStatusMessage() + "].");
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving offers lookup " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
                CCATLogger.DEBUG_LOGGER.debug("Ended successfully.");
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + list + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving offers lookup from [lookup-service] ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving offers lookup from [lookup-service] ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service");
        }
        return list;
    }

    @LogExecutionTime
    public List<LkHLRProfileModel> getHLRProfilesLookup() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started retrieving HLRProfiles lookup from lookup service.");
        List<LkHLRProfileModel> list = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            Mono<BaseResponse<LkHLRProfileModel[]>> responseAsync = webClient
                    .get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                            + Defines.LOOKUP_SERVICE.HLR_PROFILES)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<LkHLRProfileModel[]>>() {
                    }).log();

            BaseResponse<LkHLRProfileModel[]> response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    CCATLogger.DEBUG_LOGGER.debug("Retrieving HLRProfiles request returned successfully.");
                    list = Arrays.stream(response.getPayload()).collect(Collectors.toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("getHLRProfilesLookup() Ended  with "
                            + "| Exception [ statusCode : " + response.getStatusCode() + ", statusMessage : " + response.getStatusMessage() + "].");
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving HLRProfiles lookup " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
                CCATLogger.DEBUG_LOGGER.debug("Ended successfully.");
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + list + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving HLRProfiles lookup from [lookup-service] ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving HLRProfiles lookup from [lookup-service] ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service");
        }
        return list;
    }

    @LogExecutionTime
    public List<ADMTransactionType> getTransactionTypes(Integer featureId) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started retrieving HLRProfiles lookup from lookup service.");
        List<ADMTransactionType> list = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            Mono<BaseResponse<ADMTransactionType[]>> responseAsync = WebClient.create(properties.getLookupsServiceUrls())
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path(Defines.LOOKUP_SERVICE.CONTEXT_PATH
                                    + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                                    + Defines.LOOKUP_SERVICE.TRANSACTIONS
                                    + Defines.LOOKUP_SERVICE.TYPE
                                    + Defines.WEB_ACTIONS.GET)
                            .queryParam("featureId", featureId).build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<ADMTransactionType[]>>() {
                    }).log();

            BaseResponse<ADMTransactionType[]> response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    CCATLogger.DEBUG_LOGGER.debug("Retrieving HLRProfiles request returned successfully.");
                    list = Arrays.stream(response.getPayload()).collect(Collectors.toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("getHLRProfilesLookup() Ended  with "
                            + "| Exception [ statusCode : " + response.getStatusCode() + ", statusMessage : " + response.getStatusMessage() + "].");
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving HLRProfiles lookup " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
                CCATLogger.DEBUG_LOGGER.debug("Ended successfully.");
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + list + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving HLRProfiles lookup from [lookup-service] ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving HLRProfiles lookup from [lookup-service] ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service");
        }
        return list;
    }

    @LogExecutionTime
    public List<ADMTransactionCode> getTransactionCodes(Integer typeId) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started retrieving HLRProfiles lookup from lookup service.");
        List<ADMTransactionCode> list = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + typeId + "]");
            Mono<BaseResponse<ADMTransactionCode[]>> responseAsync = WebClient.create(properties.getLookupsServiceUrls())
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path(Defines.LOOKUP_SERVICE.CONTEXT_PATH
                                    + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                                    + Defines.LOOKUP_SERVICE.TRANSACTIONS
                                    + Defines.LOOKUP_SERVICE.CODE
                                    + Defines.WEB_ACTIONS.GET)
                            .queryParam("typeId", typeId).build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<ADMTransactionCode[]>>() {
                    }).log();

            BaseResponse<ADMTransactionCode[]> response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    CCATLogger.DEBUG_LOGGER.debug("Retrieving HLRProfiles request returned successfully.");
                    list = Arrays.stream(response.getPayload()).collect(Collectors.toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("getHLRProfilesLookup() Ended  with "
                            + "| Exception [ statusCode : " + response.getStatusCode() + ", statusMessage : " + response.getStatusMessage() + "].");
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving HLRProfiles lookup " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
                CCATLogger.DEBUG_LOGGER.debug("Ended successfully.");
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + list + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving HLRProfiles lookup from [lookup-service] ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving HLRProfiles lookup from [lookup-service] ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service");
        }
        return list;
    }

    @LogExecutionTime
    public List<LkPamModel> getAllPamClasses() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getAllPamClasses - call lookup-service");
        List<LkPamModel> pamList = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();

            CCATLogger.DEBUG_LOGGER.debug("Calling The Lookup-Service....");
            Mono<BaseResponse<LkPamModel[]>> res = webClient
                    .get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                            + Defines.LOOKUP_SERVICE.PAM_CLASS)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<LkPamModel[]>>() {
                    }).log();
            BaseResponse<LkPamModel[]> response = res.block();
            if (response != null) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    pamList = Arrays.stream(response.getPayload()).collect(Collectors.toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving PamClasses " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving PamClasses " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + pamList + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving PamClasses ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving PamClasses ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return pamList;
    }

    @LogExecutionTime
    public List<LkPamModel> getAllPamServices() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getAllPamServices - call lookup-service");
        List<LkPamModel> pamList = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();

            CCATLogger.DEBUG_LOGGER.debug("Calling The Lookup-Service....");
            Mono<BaseResponse<LkPamModel[]>> res = webClient
                    .get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                            + Defines.LOOKUP_SERVICE.PAM_SERVICE)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<LkPamModel[]>>() {
                    }).log();
            BaseResponse<LkPamModel[]> response = res.block();
            if (response != null) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    pamList = Arrays.stream(response.getPayload()).collect(Collectors.toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving PamServices " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving PamServices " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + pamList + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving PamServices ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving PamServices ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return pamList;
    }

    @LogExecutionTime
    public List<LkPamModel> getAllPamPeriods() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getAllPamPeriods - call lookup-service");
        List<LkPamModel> pamList = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();

            CCATLogger.DEBUG_LOGGER.debug("Calling The Lookup-Service....");
            Mono<BaseResponse<LkPamModel[]>> res = webClient
                    .get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                            + Defines.LOOKUP_SERVICE.PAM_PERIOD)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<LkPamModel[]>>() {
                    }).log();
            BaseResponse<LkPamModel[]> response = res.block();
            if (response != null) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    pamList = Arrays.stream(response.getPayload()).collect(Collectors.toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving PamPeriods " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving PamPeriods " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + pamList + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving PamPeriods ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving PamPeriods ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return pamList;
    }

    @LogExecutionTime
    public List<LkPamModel> getAllPamPriorites() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getAllPamPriorites - call lookup-service");
        List<LkPamModel> pamList = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.DEBUG_LOGGER.debug("Calling The Lookup-Service....");
            Mono<BaseResponse<LkPamModel[]>> res = webClient
                    .get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                            + Defines.LOOKUP_SERVICE.PAM_PRIORITY)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<LkPamModel[]>>() {
                    }).log();
            BaseResponse<LkPamModel[]> response = res.block();
            if (response != null) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    pamList = Arrays.stream(response.getPayload()).collect(Collectors.toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving PamPriorites " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving PamPriorites " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + pamList + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving PamPriorites ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving PamPriorites ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return pamList;
    }

    @LogExecutionTime
    public List<LkPamModel> getAllPamSchedules() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getAllPamSchedules - call lookup-service");
        List<LkPamModel> pamList = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.DEBUG_LOGGER.debug("Calling The Lookup-Service....");
            Mono<BaseResponse<LkPamModel[]>> res = webClient
                    .get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                            + Defines.LOOKUP_SERVICE.PAM_SCHEDULE)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<LkPamModel[]>>() {
                    }).log();
            BaseResponse<LkPamModel[]> response = res.block();
            if (response != null) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    pamList = Arrays.stream(response.getPayload()).collect(Collectors.toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving PamSchedules " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving PamSchedules " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + pamList + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving PamSchedules ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving PamSchedules ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service[" + properties.getLookupsServiceUrls() + "]");
        }
        return pamList;
    }

    @LogExecutionTime
    public List<DisconnectionCodeModel> getAllDisconnectionCodes() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getAllDisconnectionCodes - call lookup-service");
        List<DisconnectionCodeModel> disconnectionCodes = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.DEBUG_LOGGER.debug("Calling The lookup-service....");
            Mono<BaseResponse<DisconnectionCodeModel[]>> res = webClient.get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                            + Defines.LOOKUP_SERVICE.DISCONNECTION_CODE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<DisconnectionCodeModel[]>>() {
                    }).log();
            BaseResponse<DisconnectionCodeModel[]> response = res.block();
            if (response != null) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    disconnectionCodes = Arrays.stream(response.getPayload()).collect(toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving DisconnectionCodes " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving DisconnectionCodes " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + disconnectionCodes + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving DisconnectionCodes ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving DisconnectionCodes ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service [" + properties.getLookupsServiceUrls() + "]");
        }
        return disconnectionCodes;
    }

    @LogExecutionTime
    public List<AccountGroupModel> getAllAccountGroups() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getAllAccountGroups - call lookup-service");
        List<AccountGroupModel> accountGroups = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.DEBUG_LOGGER.debug("Calling The lookup-service....");
            Mono<BaseResponse<List<AccountGroupModel>>> res = webClient.get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                            + Defines.LOOKUP_SERVICE.ACCOUNT_GROUPS)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<List<AccountGroupModel>>>() {
                    }).log();
            BaseResponse<List<AccountGroupModel>> response = res.block();
            if (response != null) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    accountGroups = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving AccountGroups " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving AccountGroups " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + accountGroups + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving AccountGroups ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving AccountGroups ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service [" + properties.getLookupsServiceUrls() + "]");
        }
        return accountGroups;
    }

    @LogExecutionTime
    public List<ServiceOfferingPlanModel> getAllServiceOfferingPlans() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getAllServiceOfferingPlans - call lookup-service");
        List<ServiceOfferingPlanModel> serviceOfferingPlans = null;
        try {
            CCATLogger.DEBUG_LOGGER.debug("Calling The lookup-service....");
            Mono<BaseResponse<ServiceOfferingPlanModel[]>> res = webClient.get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                            + Defines.LOOKUP_SERVICE.SERVICE_OFFERING_PLANS)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<ServiceOfferingPlanModel[]>>() {
                    }).log();
            BaseResponse<ServiceOfferingPlanModel[]> response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    serviceOfferingPlans = Arrays.stream(response.getPayload()).collect(toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving serviceOfferings " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving serviceOfferings " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + serviceOfferingPlans + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving serviceOfferings ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving serviceOfferings ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service [" + properties.getLookupsServiceUrls() + "]");
        }
        return serviceOfferingPlans;
    }

    public HashMap<Integer, CommunitiesModel> getAllCommunities() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getAllCommunities - call lookup-service");
        HashMap<Integer, CommunitiesModel> communities = null;
        try {
            Mono<BaseResponse<HashMap<Integer, CommunitiesModel>>> res = webClient.get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                            + Defines.LOOKUP_SERVICE.COMMUNITIES)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<HashMap<Integer, CommunitiesModel>>>() {
                    }).log();
            BaseResponse<HashMap<Integer, CommunitiesModel>> response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    communities = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving communities " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving communities " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + communities + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving communities ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving communities ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service [" + properties.getLookupsServiceUrls() + "]");
        }
        return communities;
    }

    public HashMap<Integer, FafPlanModel> getAllFafPlans() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getAllFafPlans - call lookup-service");
        HashMap<Integer, FafPlanModel> fafPlans = null;
        try {
            Mono<BaseResponse<HashMap<Integer, FafPlanModel>>> res = webClient.get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                            + Defines.LOOKUP_SERVICE.FAF_PLANS)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<HashMap<Integer, FafPlanModel>>>() {
                    }).log();
            BaseResponse<HashMap<Integer, FafPlanModel>> response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    fafPlans = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving all faf plans " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + fafPlans + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving fafPlans ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving fafPlans ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service [" + properties.getLookupsServiceUrls() + "]");
        }
        return fafPlans;
    }

    public List<FafIndicatorModel> getAllFafIndicators() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getAllFafIndicators - call lookup-service");
        List<FafIndicatorModel> fafIndicators = null;
        try {
            Mono<BaseResponse<List<FafIndicatorModel>>> res = webClient.get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                            + Defines.LOOKUP_SERVICE.FAF_INDICATORS)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<List<FafIndicatorModel>>>() {
                    }).log();
            BaseResponse<List<FafIndicatorModel>> response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    fafIndicators = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving all faf plans " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + fafIndicators + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving fafPlans ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving fafPlans ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service [" + properties.getLookupsServiceUrls() + "]");
        }
        return fafIndicators;
    }

    public HashMap<Integer, String> getActionTypes() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getActionTypes - call lookup-service");
        HashMap<Integer, String> actionTypes = null;
        try {
            Mono<BaseResponse<HashMap<Integer, String>>> res = webClient.get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                            + Defines.LOOKUP_SERVICE.ACTION_TYPES)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<HashMap<Integer, String>>>() {
                    }).log();
            BaseResponse<HashMap<Integer, String>> response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    actionTypes = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving action types " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving action types " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + actionTypes + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving action types ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving action types ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service [" + properties.getLookupsServiceUrls() + "]");
        }
        return actionTypes;
    }

    public HashMap<Integer, String> getActionNames() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getActionNames - call lookup-service");
        HashMap<Integer, String> actionNames = null;
        try {
            Mono<BaseResponse<HashMap<Integer, String>>> res = webClient.get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                            + Defines.LOOKUP_SERVICE.ACTION_NAMES)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<HashMap<Integer, String>>>() {
                    }).log();
            BaseResponse<HashMap<Integer, String>> response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    actionNames = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving action names " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving action names " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + actionNames + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving action names ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving action names ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service [" + properties.getLookupsServiceUrls() + "]");
        }
        return actionNames;
    }

    public HashMap<String, FootPrintPageModel> getFootPrintPages() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting footPrintPages - call lookup-service");
        HashMap<String, FootPrintPageModel> footPrintPagesMap = null;
        try {
            BaseResponse<HashMap<String, FootPrintPageModel>> response = webClient.get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                            + Defines.LOOKUP_SERVICE.FOOTPRINT_PAGES)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<HashMap<String, FootPrintPageModel>>>() {
                    })
                    .block();
            if (response != null) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    footPrintPagesMap = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving footPrintPages" + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while retrieving footPrintPages" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving footPrintPages ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving footPrintPages ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service [" + properties.getLookupsServiceUrls() + "]");
        }
        return footPrintPagesMap;
    }

    public Map<String, Boolean> getAppPages() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Start calling lookup-service");
        try {
            BaseResponse<Map<String, Boolean>> response = webClient.get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                            + Defines.LOOKUP_SERVICE.PAGE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<Map<String, Boolean>>>() {
                    })
                    .block();
            if (response != null) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS))
                    return response.getPayload();
                else
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage());
            }
            return Collections.emptyMap();
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while retrieving app pages ", ex);
            CCATLogger.ERROR_LOGGER.error("Error while retrieving app pages ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service [" + properties.getLookupsServiceUrls() + "]");
        }
    }

    public List<String> getVIPSubscribers() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Start calling lookup-service");
        try {
            BaseResponse<List<String>> response = webClient.get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                            + Defines.LOOKUP_SERVICE.MSISDN)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<List<String>>>() {
                    })
                    .block();
            if (response != null) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS))
                    return response.getPayload();
                else
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage());
            }
            return null;
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while retrieving app pages ", ex);
            CCATLogger.ERROR_LOGGER.error("Error while retrieving app pages ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service [" + properties.getLookupsServiceUrls() + "]");
        }
    }

    @LogExecutionTime
    public List<LookupModel> getMaredCards() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started retrieving maredCards lookup from lookup service.");
        List<LookupModel> list = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            Mono<BaseResponse<LookupModel[]>> responseAsync = webClient
                    .get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                            + Defines.LOOKUP_SERVICE.MARED_CARDS)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<LookupModel[]>>() {
                    }).log();

            BaseResponse<LookupModel[]> response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    CCATLogger.DEBUG_LOGGER.debug("Retrieving maredCards request returned successfully.");
                    list = Arrays.stream(response.getPayload()).collect(Collectors.toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("LookupServiceProxy -> getMaredCards() Ended  with "
                            + "| Exception [ statusCode : " + response.getStatusCode() + ", statusMessage : " + response.getStatusMessage() + "].");
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving maredCards lookup " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
                CCATLogger.DEBUG_LOGGER.debug("LookupServiceProxy -> getMaredCards() Ended successfully.");
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + list + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving maredCards lookup from [lookup-service] ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving maredCards lookup from [lookup-service] ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service");
        }
        return list;
    }

    @LogExecutionTime
    public GetBarringReasonResponse getBarringReason(GetBarringReasonRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started retrieving barring reason from lookup service.");
        GetBarringReasonResponse res = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            Mono<BaseResponse<GetBarringReasonResponse>> responseAsync = webClient
                    .post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.BARRING_REASON
                            + Defines.WEB_ACTIONS.GET)
                    .body(BodyInserters.fromValue(request))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetBarringReasonResponse>>() {
                    }).log();

            BaseResponse<GetBarringReasonResponse> response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    CCATLogger.DEBUG_LOGGER.debug("Retrieving barring reason request returned successfully.");
                    res = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("LookupServiceProxy -> getBarringReason() Ended  with "
                            + "| Exception [ statusCode : " + response.getStatusCode() + ", statusMessage : " + response.getStatusMessage() + "].");
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving barring reason " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
                CCATLogger.DEBUG_LOGGER.debug("LookupServiceProxy -> getBarringReason() Ended successfully.");
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + res + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving barring reason from [lookup-service] ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving barring reason from [lookup-service] ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service");
        }
        return res;
    }

    @LogExecutionTime
    public List<ReasonActivityModel> getCallActivities(String activitiesKey) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started retrieving call activities lookup from lookup service.");
        List<ReasonActivityModel> list = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.info("request is [" + activitiesKey + "]");
            Mono<BaseResponse<List<ReasonActivityModel>>> responseAsync = WebClient.create(properties.getLookupsServiceUrls())
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path(Defines.LOOKUP_SERVICE.CONTEXT_PATH
                                    + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                                    + Defines.LOOKUP_SERVICE.CALL_ACTIVITIES
                                    + Defines.WEB_ACTIONS.GET)
                            .queryParam("activitiesKey", activitiesKey).build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<List<ReasonActivityModel>>>() {
                    }).log();

            BaseResponse<List<ReasonActivityModel>> response = responseAsync.block();
            if (response != null) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    CCATLogger.DEBUG_LOGGER.debug("Retrieving Call Activities request returned successfully.");
                    list = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("getCallActivities() Ended  with "
                            + "| Exception [ statusCode : " + response.getStatusCode() + ", statusMessage : " + response.getStatusMessage() + "].");
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving Call Activities lookup " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
                CCATLogger.DEBUG_LOGGER.debug("Ended successfully.");
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + list + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving Call Activities lookup from [lookup-service] ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving Call Activities lookup from [lookup-service] ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service");
        }
        return list;
    }

    @LogExecutionTime
    public HashMap<Integer, ServiceOfferingPlanBitDetailsModel> retrieveServiceOfferingPlansWithBitsDetails() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started retrieving Service Offering Plans With Bit Details lookup from lookup service.");
        HashMap<Integer, ServiceOfferingPlanBitDetailsModel> resultMap = null;
        try {
            String URI = properties.getLookupsServiceUrls() +
                    Defines.LOOKUP_SERVICE.CONTEXT_PATH
                    + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                    + Defines.LOOKUP_SERVICE.SERVICE_OFFERING_PLANS_BIT_DETAILS
                    + Defines.WEB_ACTIONS.GET;
            CCATLogger.DEBUG_LOGGER.debug("Started Calling Retrieve Service Offering Plans With Bit Details lookup from lookup service With URI :" + URI);

            Mono<BaseResponse<HashMap<Integer, ServiceOfferingPlanBitDetailsModel>>> responseAsync = WebClient.create(properties.getLookupsServiceUrls())
                    .get()
                    .uri(URI)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<HashMap<Integer, ServiceOfferingPlanBitDetailsModel>>>() {
                    }).log();

            BaseResponse<HashMap<Integer, ServiceOfferingPlanBitDetailsModel>> response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            CCATLogger.DEBUG_LOGGER.debug("Calling Retrieve Service Offering Plans With Bit Details lookup from lookup service Ended Successfully");

            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    CCATLogger.DEBUG_LOGGER.debug("Retrieving Service Offering Plans With Bit Details returned successfully.");
                    resultMap = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("retrieveServiceOfferingPlansWithBitsDetails() Ended  with "
                            + "| Exception [ statusCode : " + response.getStatusCode() + ", statusMessage : " + response.getStatusMessage() + "].");
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving Service Offering Plans With Bit Details lookup " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
                CCATLogger.DEBUG_LOGGER.debug("Ended successfully.");
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + resultMap + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving Service Offering Plans With Bit Details lookup from [lookup-service] ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving Service Offering Plans With Bit Details lookup from [lookup-service] ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service");
        }
        return resultMap;
    }


    @LogExecutionTime
    public GetSmsActionsResponse getSmsActions(GetSmsActionsRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started retrieving SmsActions from lookup service.");
        long start = 0;
        long executionTime;
        GetSmsActionsResponse getSmsActionsResponse = null;
        try {
            start = System.currentTimeMillis();
            Mono<BaseResponse<List<SmsActionModel>>> responseAsync = webClient
                    .get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                            + Defines.LOOKUP_SERVICE.SMS_ACTIONS
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<List<SmsActionModel>>>() {
                    }).log();

            BaseResponse<List<SmsActionModel>> response = responseAsync.block();
            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    CCATLogger.DEBUG_LOGGER.debug("Retrieving SmsActions request returned successfully.");
                    getSmsActionsResponse = new GetSmsActionsResponse();
                    getSmsActionsResponse.setSmsActions(response.getPayload());
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("LookupServiceProxy -> getSmsActions() Ended  with "
                            + "| Exception [ statusCode : " + response.getStatusCode() + ", statusMessage : " + response.getStatusMessage() + "].");
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving SmsActions " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
                CCATLogger.DEBUG_LOGGER.debug("LookupServiceProxy -> getSmsActions() Ended successfully.");
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response.getPayload() + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving SmsActions from [lookup-service] ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving SmsActions from [lookup-service] ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service");
        }
        return getSmsActionsResponse;
    }

    @LogExecutionTime
    public GetSmsActionParamMapResponse getSmsActionParamMap(GetSmsActionParamMapRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started retrieving SmsActionParamMap from lookup service.");
        GetSmsActionParamMapResponse res = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            Mono<BaseResponse<HashMap<Integer, List<SmsTemplateParamModel>>>> responseAsync = webClient
                    .get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                            + Defines.LOOKUP_SERVICE.SMS_ACTION_PARAM_MAP
                            + Defines.WEB_ACTIONS.GET)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<HashMap<Integer, List<SmsTemplateParamModel>>>>() {
                    }).log();

            BaseResponse<HashMap<Integer, List<SmsTemplateParamModel>>> response = responseAsync.block();
            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    CCATLogger.DEBUG_LOGGER.debug("Retrieving SmsActionParamMap request returned successfully.");
                    res = new GetSmsActionParamMapResponse();
                    res.setActionParamMap(response.getPayload());
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("LookupServiceProxy -> getSmsActionParamMap() Ended  with "
                            + "| Exception [ statusCode : " + response.getStatusCode() + ", statusMessage : " + response.getStatusMessage() + "].");
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving SmsActionParamMap " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
                CCATLogger.DEBUG_LOGGER.debug("LookupServiceProxy -> getSmsActionParamMap() Ended successfully.");
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + res + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving SmsActionParamMap from [lookup-service] ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving SmsActionParamMap from [lookup-service] ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service");
        }
        return res;
    }

    @LogExecutionTime
    public HashMap<Integer, AccountGroupWithBitsModel> getAccountGroupsWithBits() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started retrieving Account Groups With Bit Details lookup from lookup service.");
        HashMap<Integer, AccountGroupWithBitsModel> resultMap = null;
        try {
            String URI = properties.getLookupsServiceUrls() +
                    Defines.LOOKUP_SERVICE.CONTEXT_PATH
                    + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                    + Defines.LOOKUP_SERVICE.ACCOUNT_GROUPS_WITH_BITS
                    + Defines.WEB_ACTIONS.GET;
            CCATLogger.DEBUG_LOGGER.debug("Started Calling Retrieve Account Groups With Bit Details lookup from lookup service With URI :" + URI);

            Mono<BaseResponse<HashMap<Integer, AccountGroupWithBitsModel>>> responseAsync = WebClient.create(properties.getLookupsServiceUrls())
                    .get()
                    .uri(URI)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<HashMap<Integer, AccountGroupWithBitsModel>>>() {
                    }).log();

            BaseResponse<HashMap<Integer, AccountGroupWithBitsModel>> response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            CCATLogger.DEBUG_LOGGER.debug("Calling Retrieve Account Groups With Bit Details lookup from lookup service Ended Successfully");

            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    CCATLogger.DEBUG_LOGGER.debug("Retrieving Account Groups With Bit Details returned successfully.");
                    resultMap = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("retrieveAccountGroupsWithBits() Ended  with "
                            + "| Exception [ statusCode : " + response.getStatusCode() + ", statusMessage : " + response.getStatusMessage() + "].");
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving Account Groups With Bit Details lookup " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
                CCATLogger.DEBUG_LOGGER.debug("Ended successfully.");
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + resultMap + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving Account Groups With Bit Details lookup from [lookup-service] ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving Account Groups With Bit Details lookup from [lookup-service] ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service");
        }
        return resultMap;
    }


    @LogExecutionTime
    public GetODSActivityHeaderResponse getODSActivityHeader() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started retrieving ODSActivityHeaderMap from lookup service.");
        GetODSActivityHeaderResponse res = null;
        long start = 0;
        long executionTime;
        try {
            start = System.currentTimeMillis();
            Mono<BaseResponse<HashMap<Integer, ODSActivityHeader>>> responseAsync = webClient
                    .get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + Defines.LOOKUP_SERVICE.CACHED_LOOKUPS
                            + Defines.LOOKUP_SERVICE.ODS
                            + Defines.LOOKUP_SERVICE.ACTIVITIES_HEADERS
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<HashMap<Integer, ODSActivityHeader>>>() {
                    }).log();

            BaseResponse<HashMap<Integer, ODSActivityHeader>> response = responseAsync.block();
            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    CCATLogger.DEBUG_LOGGER.debug("Retrieving ODSActivityHeaderMap request returned successfully.");
                    res = new GetODSActivityHeaderResponse();
                    res.setOdsActivityHeaderMap(response.getPayload());
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("LookupServiceProxy -> getODSActivityHeader() Ended  with "
                            + "| Exception [ statusCode : " + response.getStatusCode() + ", statusMessage : " + response.getStatusMessage() + "].");
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving ODSActivityHeaderMap " + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
                CCATLogger.DEBUG_LOGGER.debug("LookupServiceProxy -> getODSActivityHeader() Ended successfully.");
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + res + "]");
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving ODSActivityHeaderMap from [lookup-service] ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving ODSActivityHeaderMap from [lookup-service] ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "lookup-service");
        }
        return res;
    }
}
