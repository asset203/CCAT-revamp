package com.asset.ccat.air_service.proxy;

import com.asset.ccat.air_service.annotation.LogExecutionTime;
import com.asset.ccat.air_service.configurations.Properties;
import com.asset.ccat.air_service.defines.Defines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.*;
import com.asset.ccat.air_service.models.customer_care.service_offering_details_models.lookup_models.ServiceOfferingBitLookupModel;
import com.asset.ccat.air_service.models.customer_care.service_offering_details_models.lookup_models.ServiceOfferingPlanBitLookupModel;
import com.asset.ccat.air_service.models.requests.AddBarringReasonRequest;
import com.asset.ccat.air_service.models.requests.DeleteBarringReasonRequest;
import com.asset.ccat.air_service.models.responses.BaseResponse;
import com.asset.ccat.air_service.models.responses.offer.OfferResponse;
import com.asset.ccat.air_service.models.responses.offer.OfferStateResponse;
import com.asset.ccat.air_service.models.responses.offer.OfferTypeResponse;
import com.asset.ccat.air_service.models.shared.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Mahmoud Shehab
 */
@Component
public class LookupProxy {

    @Autowired
    WebClient webClient;

    @Autowired
    Properties properties;

    @LogExecutionTime
    public List<AIRServer> getAirServer() throws AIRServiceException {
        List<AIRServer> list = null;
        try {
            String URI = properties.getLookupsServiceUrls()
                    + Defines.ContextPaths.LOOKUPS
                    + Defines.ContextPaths.CACHED_LOOKUPS
                    + Defines.ContextPaths.AIR_SERVERS;
            CCATLogger.DEBUG_LOGGER.info("Calling Lookups With URI : " + URI);
            Mono<BaseResponse<AIRServer[]>> responseAsync = webClient.get()
                    .uri(URI)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<AIRServer[]>>() {
                    }).log();
            BaseResponse<AIRServer[]> response = responseAsync.block();
            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    list = Arrays.stream(response.getPayload()).collect(Collectors.toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving Air Server Lookup " + response);
                    throw new AIRServiceException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR, response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.debug("Response: " + list);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving air server ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving air server ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.LOOKUP_SERVER_UNREACHABLE);
        }
        return list;
    }

    @LogExecutionTime
    public List<VoucherServerModel> getVoucherServers() throws AIRServiceException {
        List<VoucherServerModel> list = null;
        try {
            String URI = properties.getLookupsServiceUrls()
                    + Defines.ContextPaths.LOOKUPS
                    + Defines.ContextPaths.CACHED_LOOKUPS
                    + Defines.ContextPaths.VOUCHER_SERVERS;
            CCATLogger.DEBUG_LOGGER.info("Calling Lookups With URI : " + URI);
            Mono<BaseResponse<VoucherServerModel[]>> responseAsync = webClient.get()
                    .uri(URI)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<VoucherServerModel[]>>() {
                    }).log();
            BaseResponse<VoucherServerModel[]> response = responseAsync.block();
            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    list = Arrays.stream(response.getPayload()).collect(Collectors.toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving voucher servers " + response);
                    throw new AIRServiceException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR, response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.debug("Response: " + list);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving voucher servers ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving voucher servers ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.LOOKUP_SERVER_UNREACHABLE);
        }
        return list;
    }

    @LogExecutionTime
    public List<ServiceClassModel> getServiceClasses() throws AIRServiceException {
        List<ServiceClassModel> list = null;
        try {
            String URI = properties.getLookupsServiceUrls()
                    + Defines.ContextPaths.LOOKUPS
                    + Defines.ContextPaths.CACHED_LOOKUPS
                    + Defines.ContextPaths.SERVICE_CLASSES;
            CCATLogger.DEBUG_LOGGER.info("Calling Lookups With URI : " + URI);
            Mono<BaseResponse<ServiceClassModel[]>> responseAsync = webClient.get()
                    .uri(URI)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<ServiceClassModel[]>>() {
                    }).log();
            BaseResponse<ServiceClassModel[]> response = responseAsync.block();
            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    list = Arrays.stream(response.getPayload()).collect(Collectors.toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving ServiceClasses " + response);
                    throw new AIRServiceException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR, response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.debug("Response: " + list);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving ServiceClasses ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving ServiceClasses ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.LOOKUP_SERVER_UNREACHABLE);
        }
        return list;
    }

    @LogExecutionTime
    public List<OfferResponse> getOffers() throws AIRServiceException {
        List<OfferResponse> list = new ArrayList<>();
        try {
            String uri = properties.getLookupsServiceUrls()
                    + Defines.ContextPaths.LOOKUPS
                    + Defines.ContextPaths.CACHED_LOOKUPS
                    + Defines.ContextPaths.OFFERS;
            CCATLogger.DEBUG_LOGGER.info("Calling Lookups With URI : {}", uri);

            Flux<OfferResponse> responseFlux = webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToFlux(OfferResponse.class)  // Stream the response
                    .log();

            responseFlux
                    .collectList()
                    .doOnNext(list::addAll)
                    .block();
            CCATLogger.DEBUG_LOGGER.debug("Lookup Response: {}", list);

            if (list.isEmpty())
                throw new AIRServiceException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR, "No offers found");
            return list;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while retrieving offers ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving offers ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.LOOKUP_SERVER_UNREACHABLE);
        }
    }

    @LogExecutionTime
    public List<OfferStateResponse> getOfferStates() throws AIRServiceException {
        List<OfferStateResponse> list = null;
        try {
            String URI = properties.getLookupsServiceUrls()
                    + Defines.ContextPaths.LOOKUPS
                    + Defines.ContextPaths.CACHED_LOOKUPS
                    + Defines.ContextPaths.OFFERS_STATES;
            CCATLogger.DEBUG_LOGGER.info("Calling Lookups With URI : " + URI);
            Mono<BaseResponse<OfferStateResponse[]>> responseAsync = webClient.get()
                    .uri(URI)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<OfferStateResponse[]>>() {
                    }).log();
            BaseResponse<OfferStateResponse[]> response = responseAsync.block();
            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    list = Arrays.stream(response.getPayload()).collect(Collectors.toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving Offer States " + response);
                    throw new AIRServiceException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR, response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.debug("Response: " + list);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving offers states ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving offers states ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.LOOKUP_SERVER_UNREACHABLE);
        }
        return list;
    }

    @LogExecutionTime
    public List<OfferTypeResponse> getOfferTypes() throws AIRServiceException {
        List<OfferTypeResponse> list = null;
        try {
            String URI = properties.getLookupsServiceUrls()
                    + Defines.ContextPaths.LOOKUPS
                    + Defines.ContextPaths.CACHED_LOOKUPS
                    + Defines.ContextPaths.OFFERS_TYPES;
            CCATLogger.DEBUG_LOGGER.info("Calling Lookups With URI : " + URI);
            Mono<BaseResponse<OfferTypeResponse[]>> responseAsync = webClient.get()
                    .uri(URI)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<OfferTypeResponse[]>>() {
                    }).log();
            BaseResponse<OfferTypeResponse[]> response = responseAsync.block();
            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    list = Arrays.stream(response.getPayload()).collect(Collectors.toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving Offer Types " + response);
                    throw new AIRServiceException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR, response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.debug("Response: " + list);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving offers types ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving offers types ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.LOOKUP_SERVER_UNREACHABLE);
        }

        return list;
    }

    @LogExecutionTime
    public List<LkPamModel> getPamClass() throws AIRServiceException {
        List<LkPamModel> list = null;
        try {
            String URI = properties.getLookupsServiceUrls()
                    + Defines.ContextPaths.LOOKUPS
                    + Defines.ContextPaths.CACHED_LOOKUPS
                    + Defines.ContextPaths.PAM_CLASS;
            CCATLogger.DEBUG_LOGGER.info("Calling Lookups With URI : " + URI);
            Mono<BaseResponse<LkPamModel[]>> responseAsync = webClient.get()
                    .uri(URI)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<LkPamModel[]>>() {
                    }).log();
            BaseResponse<LkPamModel[]> response = responseAsync.block();
            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    list = Arrays.stream(response.getPayload()).collect(Collectors.toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving Pam Classes " + response);
                    throw new AIRServiceException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR, response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.debug("Response: " + list);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving Pam Classes ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving Pam Classes ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.LOOKUP_SERVER_UNREACHABLE);
        }

        return list;
    }

    @LogExecutionTime
    public List<LkPamModel> getPamSchedule() throws AIRServiceException {
        List<LkPamModel> list = null;
        try {
            String URI = properties.getLookupsServiceUrls()
                    + Defines.ContextPaths.LOOKUPS
                    + Defines.ContextPaths.CACHED_LOOKUPS
                    + Defines.ContextPaths.PAM_SCHEDULE;
            CCATLogger.DEBUG_LOGGER.info("Calling Lookups With URI : " + URI);
            Mono<BaseResponse<LkPamModel[]>> responseAsync = webClient.get()
                    .uri(URI)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<LkPamModel[]>>() {
                    }).log();
            BaseResponse<LkPamModel[]> response = responseAsync.block();
            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    list = Arrays.stream(response.getPayload()).collect(Collectors.toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving Pam Schedule " + response);
                    throw new AIRServiceException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR, response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.debug("Response: " + list);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving Pam Schedule ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving Pam Schedule ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.LOOKUP_SERVER_UNREACHABLE);
        }
        return list;
    }

    @LogExecutionTime
    public List<LookupModel> getLookups(String name) throws AIRServiceException {
        List<LookupModel> list = null;
        try {
            String URI = properties.getLookupsServiceUrls()
                    + Defines.ContextPaths.LOOKUPS
                    + Defines.ContextPaths.CACHED_LOOKUPS
                    + Defines.WEB_ACTIONS.GET + "?lookup-name=" + name;
            CCATLogger.DEBUG_LOGGER.info("Calling Lookups With URI : " + URI);
            Mono<BaseResponse<LookupModel[]>> responseAsync = webClient.get()
                    .uri(URI)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<LookupModel[]>>() {
                    })
                    .log();
            BaseResponse<LookupModel[]> response = responseAsync.block();
            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    list = Arrays.stream(response.getPayload()).collect(Collectors.toList());
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving cached lookups " + response);
                    throw new AIRServiceException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR, response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.debug("Response: " + list);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving cached lookups " + name);
            CCATLogger.ERROR_LOGGER.error("Error while retrieving cached lookups " + name, ex);
            throw new AIRServiceException(ErrorCodes.ERROR.LOOKUP_SERVER_UNREACHABLE);
        }
        return list;
    }

    @LogExecutionTime
    public HashMap<Integer, List<ServiceClassModel>> getProfilesServiceClasses() throws AIRServiceException {
        HashMap<Integer, List<ServiceClassModel>> map = null;
        try {
            String URI = properties.getLookupsServiceUrls()
                    + Defines.ContextPaths.LOOKUPS
                    + Defines.ContextPaths.CACHED_LOOKUPS
                    + Defines.ContextPaths.PROFILES_SERVICE_CLASSES;
            CCATLogger.DEBUG_LOGGER.info("Calling Lookups With URI : " + URI);
            Mono<BaseResponse<HashMap<Integer, List<ServiceClassModel>>>> responseAsync = webClient.get()
                    .uri(URI)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<HashMap<Integer, List<ServiceClassModel>>>>() {
                    }).log();
            BaseResponse<HashMap<Integer, List<ServiceClassModel>>> response = responseAsync.block();
            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS) && Objects.nonNull(response.getPayload())) {
                    map = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving profiles service classes " + response);
                    throw new AIRServiceException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR, response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.debug("Response: " + map);
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving profiles service classes  ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving profiles service classes  ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.LOOKUP_SERVER_UNREACHABLE);
        }
        return map;
    }

    @LogExecutionTime
    public HashMap<Integer, ServiceOfferingPlan> getServiceOfferingPlansWithBits() throws AIRServiceException {
        HashMap<Integer, ServiceOfferingPlan> map = null;
        try {
            String URI = properties.getLookupsServiceUrls()
                    + Defines.ContextPaths.LOOKUPS
                    + Defines.ContextPaths.CACHED_LOOKUPS
                    + Defines.ContextPaths.SERVICE_OFFERING_PLANS_WITH_BITS;
            CCATLogger.DEBUG_LOGGER.info("Calling Lookups With URI : " + URI);
            Mono<BaseResponse<HashMap<Integer, ServiceOfferingPlan>>> responseAsync = webClient.get()
                    .uri(URI)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<HashMap<Integer, ServiceOfferingPlan>>>() {
                    }).log();
            BaseResponse<HashMap<Integer, ServiceOfferingPlan>> response = responseAsync.block();
            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS) && Objects.nonNull(response.getPayload())) {
                    map = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving service offering plans with bits " + response);
                    throw new AIRServiceException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR, response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.debug("Response: " + map);
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving service offering plans with bits  ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving service offering plans with bits  ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.LOOKUP_SERVER_UNREACHABLE);
        }
        return map;
    }

    @LogExecutionTime
    public HashMap<Integer, HashMap<Integer, DedicatedAccount>> getDedicatedAccountsMap() throws AIRServiceException {
        HashMap<Integer, HashMap<Integer, DedicatedAccount>> map = null;
        try {
            String URI = properties.getLookupsServiceUrls()
                    + Defines.ContextPaths.LOOKUPS
                    + Defines.ContextPaths.CACHED_LOOKUPS
                    + Defines.ContextPaths.DEDICATED_ACCOUNTS_CACHE;
            CCATLogger.DEBUG_LOGGER.info("Calling Lookups With URI : " + URI);
            Mono<BaseResponse<HashMap<Integer, HashMap<Integer, DedicatedAccount>>>> responseAsync = webClient.get()
                    .uri(URI)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<HashMap<Integer, HashMap<Integer, DedicatedAccount>>>>() {
                    }).log();
            BaseResponse<HashMap<Integer, HashMap<Integer, DedicatedAccount>>> response = responseAsync.block();
            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS) && Objects.nonNull(response.getPayload())) {
                    map = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving dedicated accounts map " + response);
                    throw new AIRServiceException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR, response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.debug("Response: " + map);
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving dedicated accounts map  ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving dedicated accounts map  ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.LOOKUP_SERVER_UNREACHABLE);
        }
        return map;
    }

    @LogExecutionTime
    public void addBarringReason(AddBarringReasonRequest request) throws AIRServiceException {
        CCATLogger.DEBUG_LOGGER.debug("Started adding barring reason from lookup service.");
        try {
            String URI = properties.getLookupsServiceUrls()
                    + Defines.ContextPaths.LOOKUPS
                    + Defines.ContextPaths.BARRING_REASON
                    + Defines.WEB_ACTIONS.ADD;
            CCATLogger.DEBUG_LOGGER.info("Calling Lookups With URI : " + URI);
            Mono<BaseResponse> responseAsync = webClient
                    .post()
                    .uri(URI)
                    .body(BodyInserters.fromValue(request))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            BaseResponse response = responseAsync.block();
            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    CCATLogger.DEBUG_LOGGER.debug("Adding barring reason request returned successfully.");
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("LookupServiceProxy -> addBarringReason() Ended  with "
                            + "| Exception [ statusCode : " + response.getStatusCode()
                            + ", statusMessage : " + response.getStatusMessage() + "].");
                    CCATLogger.DEBUG_LOGGER.info("Error while addBarringReason barring reason " + response);
                    throw new AIRServiceException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR, response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while adding barring reason from [lookup-service] ");
            CCATLogger.ERROR_LOGGER.error("Error while adding barring reason from [lookup-service] ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.LOOKUP_SERVER_UNREACHABLE);
        }
    }

    @LogExecutionTime
    public void deleteBarringReason(DeleteBarringReasonRequest request) throws AIRServiceException {
        CCATLogger.DEBUG_LOGGER.debug("Started adding barring reason from lookup service.");
        try {
            String URI = properties.getLookupsServiceUrls()
                    + Defines.ContextPaths.LOOKUPS
                    + Defines.ContextPaths.BARRING_REASON
                    + Defines.WEB_ACTIONS.DELETE;
            CCATLogger.DEBUG_LOGGER.info("Calling Lookups With URI : " + URI);
            Mono<BaseResponse> responseAsync = webClient
                    .post()
                    .uri(URI)
                    .body(BodyInserters.fromValue(request))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            BaseResponse response = responseAsync.block();
            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    CCATLogger.DEBUG_LOGGER.debug("Deleting barring reason request returned successfully.");
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("LookupServiceProxy -> deleteBarringReason() Ended  with "
                            + "| Exception [ statusCode : " + response.getStatusCode()
                            + ", statusMessage : " + response.getStatusMessage() + "].");
                    CCATLogger.DEBUG_LOGGER.info("Error while delete BarringReason " + response);
                    throw new AIRServiceException(response.getStatusCode(), Defines.SEVERITY.ERROR, response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while deleting barring reason from [lookup-service] ");
            CCATLogger.ERROR_LOGGER.error("Error while deleting barring reason from [lookup-service] ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.LOOKUP_SERVER_UNREACHABLE);
        }
    }

    @LogExecutionTime
    public HashMap<Integer, CommunitiesModel> getCommunities() throws AIRServiceException {
        HashMap<Integer, CommunitiesModel> map = null;
        try {
            String URI = properties.getLookupsServiceUrls()
                    + Defines.ContextPaths.LOOKUPS
                    + Defines.ContextPaths.CACHED_LOOKUPS
                    + Defines.ContextPaths.LK_COMMUNITIES;
            CCATLogger.DEBUG_LOGGER.info("Calling Lookups With URI : " + URI);
            Mono<BaseResponse<HashMap<Integer, CommunitiesModel>>> responseAsync = webClient.get()
                    .uri(URI)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<HashMap<Integer, CommunitiesModel>>>() {
                    }).log();
            BaseResponse<HashMap<Integer, CommunitiesModel>> response = responseAsync.block();
            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS) && Objects.nonNull(response.getPayload())) {
                    map = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving communities " + response);
                    throw new AIRServiceException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR, response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.debug("Response: " + map);
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving communities ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving communities", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.LOOKUP_SERVER_UNREACHABLE);
        }
        return map;
    }

    @LogExecutionTime
    public HashMap<Integer, FafPlanModel> getFafPlans() throws AIRServiceException {
        HashMap<Integer, FafPlanModel> map = null;
        try {
            String URI = properties.getLookupsServiceUrls()
                    + Defines.ContextPaths.LOOKUPS
                    + Defines.ContextPaths.CACHED_LOOKUPS
                    + Defines.ContextPaths.LK_FAF_PLANS;
            CCATLogger.DEBUG_LOGGER.info("Calling Lookups With URI : " + URI);
            Mono<BaseResponse<HashMap<Integer, FafPlanModel>>> responseAsync = webClient.get()
                    .uri(URI)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<HashMap<Integer, FafPlanModel>>>() {
                    }).log();
            BaseResponse<HashMap<Integer, FafPlanModel>> response = responseAsync.block();
            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS) && Objects.nonNull(response.getPayload())) {
                    map = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving faf plans " + response);
                    throw new AIRServiceException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR, response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.debug("Response: " + map);
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving faf plans ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving faf plans", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.LOOKUP_SERVER_UNREACHABLE);
        }
        return map;
    }

    @LogExecutionTime
    public List<RestrictionModel> getFafWhiteList() throws AIRServiceException {
        List<RestrictionModel> fafWhiteList = null;
        try {
            String URI = properties.getLookupsServiceUrls()
                    + Defines.ContextPaths.LOOKUPS
                    + Defines.ContextPaths.CACHED_LOOKUPS
                    + Defines.ContextPaths.LK_FAF_WHITE_LIST;
            CCATLogger.DEBUG_LOGGER.info("Calling Lookups With URI : " + URI);
            Mono<BaseResponse<List<RestrictionModel>>> res = webClient.get()
                    .uri(URI)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<List<RestrictionModel>>>() {
                    }).log();
            BaseResponse<List<RestrictionModel>> response = res.block();
            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    fafWhiteList = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving faf white list " + response);
                    throw new AIRServiceException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR, response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.debug("response is [" + fafWhiteList + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving fafWhiteList ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving fafWhiteList ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.LOOKUP_SERVER_UNREACHABLE);
        }
        return fafWhiteList;
    }

    @LogExecutionTime
    public List<RestrictionModel> getFafBlackList() throws AIRServiceException {
        List<RestrictionModel> fafBlackList = null;
        try {
            String URI = properties.getLookupsServiceUrls()
                    + Defines.ContextPaths.LOOKUPS
                    + Defines.ContextPaths.CACHED_LOOKUPS
                    + Defines.ContextPaths.LK_FAF_BLACK_LIST;
            CCATLogger.DEBUG_LOGGER.info("Calling Lookups With URI : " + URI);
            Mono<BaseResponse<List<RestrictionModel>>> res = webClient.get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.ContextPaths.LOOKUPS
                            + Defines.ContextPaths.CACHED_LOOKUPS
                            + Defines.ContextPaths.LK_FAF_BLACK_LIST)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<List<RestrictionModel>>>() {
                    }).log();
            BaseResponse<List<RestrictionModel>> response = res.block();
            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    fafBlackList = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving faf black list " + response);
                    throw new AIRServiceException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR, response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.debug("response is [" + fafBlackList + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving fafBlackList ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving fafBlackList ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.LOOKUP_SERVER_UNREACHABLE);
        }
        return fafBlackList;
    }

    @LogExecutionTime
    public HashMap<Integer, ServiceOfferingBitLookupModel> getBitsLookup() throws AIRServiceException {
        HashMap<Integer, ServiceOfferingBitLookupModel> bitsLK = new HashMap<>();
        try {
            String URI = properties.getLookupsServiceUrls() +
                    Defines.ContextPaths.LOOKUPS
                    + Defines.ContextPaths.CACHED_LOOKUPS
                    + Defines.LOOKUPS.SERVICE_OFFERING_BITS_DETAILS
                    + Defines.WEB_ACTIONS.GET;
            CCATLogger.DEBUG_LOGGER.info("Calling Lookups With URI : " + URI);
            Mono<BaseResponse<HashMap<Integer, ServiceOfferingBitLookupModel>>> responseAsync =
                    WebClient.create(properties.getLookupsServiceUrls())
                            .get()
                            .uri(URI)
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .bodyToMono(new ParameterizedTypeReference<BaseResponse<HashMap<Integer, ServiceOfferingBitLookupModel>>>() {
                            }).log();
            BaseResponse<HashMap<Integer, ServiceOfferingBitLookupModel>> response = responseAsync.block();
            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS) && Objects.nonNull(response.getPayload())) {
                    bitsLK = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving bits lookup " + response);
                    throw new AIRServiceException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR, response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.debug("response is [" + bitsLK + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving getBitsLookup ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving getBitsLookup ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.LOOKUP_SERVER_UNREACHABLE);
        }
        return bitsLK;
    }

    @LogExecutionTime
    public HashMap<Integer, ServiceOfferingPlanBitLookupModel> getServiceOfferingPlans() throws AIRServiceException {
        HashMap<Integer, ServiceOfferingPlanBitLookupModel> plansLK = new HashMap<>();
        try {
            String URI = properties.getLookupsServiceUrls()
                    + Defines.ContextPaths.LOOKUPS
                    + Defines.ContextPaths.CACHED_LOOKUPS
                    + Defines.LOOKUPS.SERVICE_OFFERING_PLANS_BIT_DETAILS
                    + Defines.WEB_ACTIONS.GET;
            CCATLogger.DEBUG_LOGGER.info("Calling Lookups With URI : " + URI);
            Mono<BaseResponse<HashMap<Integer, ServiceOfferingPlanBitLookupModel>>> res = webClient.get()
                    .uri(URI)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<HashMap<Integer, ServiceOfferingPlanBitLookupModel>>>() {
                    }).log();
            BaseResponse<HashMap<Integer, ServiceOfferingPlanBitLookupModel>> response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS) && Objects.nonNull(response.getPayload())) {
                    plansLK = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving service offering plans lookup " + response);
                    throw new AIRServiceException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR, response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.debug("response is [" + plansLK + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving getServiceOfferingPlans ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving getServiceOfferingPlans ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.LOOKUP_SERVER_UNREACHABLE);
        }
        return plansLK;
    }

    @LogExecutionTime
    public HashMap<Integer, SuperFlexLookupModel> getSuperFlexAddonOffers() throws AIRServiceException {
        HashMap<Integer, SuperFlexLookupModel> offers = new HashMap<>();
        try {
            String URI = properties.getLookupsServiceUrls()
                    + Defines.ContextPaths.LOOKUPS
                    + Defines.ContextPaths.CACHED_LOOKUPS
                    + Defines.LOOKUPS.SUPER_FLEX_ADDON_OFFERS
                    + Defines.WEB_ACTIONS.GET;
            CCATLogger.DEBUG_LOGGER.info("Calling Lookups With URI : " + URI);
            Mono<BaseResponse<HashMap<Integer, SuperFlexLookupModel>>> res = webClient.get()
                    .uri(URI)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<HashMap<Integer, SuperFlexLookupModel>>>() {
                    }).log();
            BaseResponse<HashMap<Integer, SuperFlexLookupModel>> response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (Objects.nonNull(response)) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    offers = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving super flex addon offers from lookup " + response);
                    throw new AIRServiceException(response.getStatusCode(), Defines.SEVERITY.ERROR, response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.debug("response is [" + offers + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving SuperFlex AddonOffers ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving getSuperFlex AddonOffers ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.LOOKUP_SERVER_UNREACHABLE);
        }
        return offers;
    }

    @LogExecutionTime
    public HashMap<Integer, AccountGroupWithBitsModel> retrieveAccountGroupsWithBits() throws AIRServiceException {
        CCATLogger.DEBUG_LOGGER.debug("Started retrieving Account Groups With Bit Details lookup from lookup service.");
        HashMap<Integer, AccountGroupWithBitsModel> resultMap = null;
        try {
            String URI = properties.getLookupsServiceUrls()
                    + Defines.ContextPaths.LOOKUPS
                    + Defines.ContextPaths.CACHED_LOOKUPS
                    + Defines.LOOKUPS.ACCOUNT_GROUPS_WITH_BITS
                    + Defines.WEB_ACTIONS.GET;
            CCATLogger.DEBUG_LOGGER.info("Calling Lookups With URI : " + URI);
            Mono<BaseResponse<HashMap<Integer, AccountGroupWithBitsModel>>> responseAsync =
                    WebClient.create(properties.getLookupsServiceUrls())
                            .get()
                            .uri(URI)
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .bodyToMono(new ParameterizedTypeReference<BaseResponse<HashMap<Integer, AccountGroupWithBitsModel>>>() {
                            }).log();
            BaseResponse<HashMap<Integer, AccountGroupWithBitsModel>> response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    resultMap = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("retrieveAccountGroupsWithBits() Ended  with "
                            + "| Exception [ statusCode : " + response.getStatusCode() + ", statusMessage : " + response.getStatusMessage() + "].");
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving Account Groups With Bit Details lookup " + response);
                    throw new AIRServiceException(response.getStatusCode(), Defines.SEVERITY.ERROR, response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.debug("response is [" + resultMap + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving Account Groups With Bit Details lookup from [lookup-service] ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving Account Groups With Bit Details lookup from [lookup-service] ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.LOOKUP_SERVER_UNREACHABLE, "lookup-service");
        }
        return resultMap;
    }

    @LogExecutionTime
    public List<AccountGroupBitDescModel> getAccountGroupBitsLookup() throws AIRServiceException {
        List<AccountGroupBitDescModel> resultMap = null;
        try {
            String URI = properties.getLookupsServiceUrls()
                    + Defines.ContextPaths.LOOKUPS
                    + Defines.ContextPaths.CACHED_LOOKUPS
                    + Defines.LOOKUPS.ACCOUNT_GROUP_BITS_LK
                    + Defines.WEB_ACTIONS.GET;
            CCATLogger.DEBUG_LOGGER.info("Calling Lookups With URI : " + URI);
            Mono<BaseResponse<List<AccountGroupBitDescModel>>> responseAsync =
                    WebClient.create(properties.getLookupsServiceUrls())
                            .get()
                            .uri(URI)
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .bodyToMono(new ParameterizedTypeReference<BaseResponse<List<AccountGroupBitDescModel>>>() {
                            }).log();
            BaseResponse<List<AccountGroupBitDescModel>> response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (Objects.nonNull(response)) {
                if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
                    CCATLogger.DEBUG_LOGGER.debug("Retrieving Account Group bits returned successfully.");
                    resultMap = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("getAccountGroupBitsLookup() Ended  with "
                            + "| Exception [ statusCode : " + response.getStatusCode() + ", statusMessage : " + response.getStatusMessage() + "].");
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving Account Groups With Bit Details lookup " + response);
                    throw new AIRServiceException(response.getStatusCode(), Defines.SEVERITY.ERROR, response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.debug("response is [" + resultMap + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving Account Group bits lookup from [lookup-service] ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving Account Group bits lookup from [lookup-service] ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.LOOKUP_SERVER_UNREACHABLE, "lookup-service");
        }
        return resultMap;
    }
}
