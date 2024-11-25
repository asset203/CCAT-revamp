package com.asset.ccat.ci_service.proxy;

import com.asset.ccat.ci_service.configurations.Properties;
import com.asset.ccat.ci_service.defines.Defines;
import com.asset.ccat.ci_service.defines.ErrorCodes;
import com.asset.ccat.ci_service.exceptions.CIServiceException;
import com.asset.ccat.ci_service.logger.CCATLogger;
import com.asset.ccat.ci_service.models.dtos.CICodesRenewalsValue;
import com.asset.ccat.ci_service.models.responses.BaseResponse;

import java.time.Duration;
import java.util.*;

import com.asset.ccat.ci_service.models.shared.SuperFlexLookupModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;

/**
 * @author marwa.elshawarby
 */
@Component
public class LookupProxy {

    @Autowired
    WebClient webClient;

    @Autowired
    Properties properties;

    public HashMap<String, String> getMainProductTypes() throws CIServiceException {
        HashMap<String, String> list = null;
        long executionTime;
        long start = 0;
        try {
            start = System.currentTimeMillis();
            Mono<BaseResponse<HashMap<String, String>>> responseAsync = webClient.get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUPS.CONTEXT_PATH
                            + Defines.ContextPaths.CACHED_LOOKUPS
                            + Defines.LOOKUPS.LK_MAIN_PRODUCT_TYPES)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<HashMap<String, String>>>() {
                    }).log();

            BaseResponse<HashMap<String, String>> response = responseAsync.block();
            if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                list = response.getPayload() == null ? new HashMap<>() : response.getPayload();
            } else {
                CCATLogger.DEBUG_LOGGER.info("Error while retrieving mainProductTypes " + response);
                throw new CIServiceException(ErrorCodes.ERROR.NO_DATA_FOUND);
            }
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.debug("Response: " + list);
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (CIServiceException ex) {
            throw ex;
        } catch (WebClientException ex) {
            CCATLogger.DEBUG_LOGGER.error("WebClient Exception occured while retrieving mainProductTypes ");
            CCATLogger.ERROR_LOGGER.error("WebClient Exception occured while retrieving mainProductTypes ", ex);
            throw new CIServiceException(ErrorCodes.ERROR.LOOKUP_SERVER_UNREACHABLE);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Unknown exception occured while retrieving mainProductTypes ");
            CCATLogger.ERROR_LOGGER.error("Unknown exception occured while retrieving mainProductTypes ", ex);
            throw new CIServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
        return list;
    }

    public Map<Integer, CICodesRenewalsValue> retrievePrepaidVBPCodesRenewalsValues() throws CIServiceException {
        CCATLogger.DEBUG_LOGGER.debug("[ lookupProxy -> retrievePrepaidVBPCodesRenewalsValues() ] response. ] Started successfully.");
        Map<Integer, CICodesRenewalsValue> renewalsValueList = new HashMap<>();
        String URI = properties.getLookupsServiceUrls()
                + Defines.LOOKUPS.CONTEXT_PATH
                + Defines.ContextPaths.CACHED_LOOKUPS
                + Defines.CI_PREPAID_VBP.CODES_RENEWAL_VALUE;
        try {
            CCATLogger.DEBUG_LOGGER.info("Start call Lookup retrievePrepaidVBPCodesRenewalsValues " + URI);
            Mono<BaseResponse<Map<Integer, CICodesRenewalsValue>>> responseAsync = webClient.get()
                    .uri(URI)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<Map<Integer, CICodesRenewalsValue>>>() {
                    }).log();

            BaseResponse<Map<Integer, CICodesRenewalsValue>> response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                CCATLogger.DEBUG_LOGGER.info("Error while calling Lookup retrievePrepaidVBPCodesRenewalsValues" + response);
                CCATLogger.DEBUG_LOGGER.error("Error while calling Lookup retrievePrepaidVBPCodesRenewalsValues " + response);
                throw new CIServiceException(response.getStatusCode(), response.getSeverity(), response.getStatusMessage());
            }
            renewalsValueList = response.getPayload();
            CCATLogger.INTERFACE_LOGGER.debug("Response: " + response);
            CCATLogger.DEBUG_LOGGER.debug("[ lookupProxy -> retrievePrepaidVBPCodesRenewalsValues() ] response. " + response);
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while calling Lookup retrievePrepaidVBPCodesRenewalsValues " + URI, ex);
            CCATLogger.ERROR_LOGGER.error("Error while calling Lookup retrievePrepaidVBPCodesRenewalsValues " + URI, ex);
            throw new CIServiceException(ErrorCodes.ERROR.UNREACHABLE_EXTERNAL_SERVICE);
        }
        CCATLogger.DEBUG_LOGGER.debug("[ lookupProxy -> retrievePrepaidVBPCodesRenewalsValues() ] End successfully.");

        return renewalsValueList;
    }

    public HashMap<Integer,SuperFlexLookupModel> getSuperFlexThresholdOffers() throws CIServiceException {
        HashMap<Integer,SuperFlexLookupModel> offers = new HashMap<>();
        CCATLogger.DEBUG_LOGGER.debug("LookupProxy -> getSuperFlexThresholdOffers() : Started ");
        try {
            String uri = properties.getLookupsServiceUrls()
                    + Defines.LOOKUPS.CONTEXT_PATH
                    + Defines.ContextPaths.CACHED_LOOKUPS
                    + Defines.LOOKUPS.SUPER_FLEX_THRESHOLD_OFFERS
                    + Defines.WEB_ACTIONS.GET;
            CCATLogger.DEBUG_LOGGER.debug("LookupProxy -> getSuperFlexThresholdOffers() : start calling Lookup service with URI " + uri);
            Mono<BaseResponse<HashMap<Integer,SuperFlexLookupModel>>> res = webClient.get()
                    .uri(uri)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<HashMap<Integer,SuperFlexLookupModel>>>() {
                    }).log();
            BaseResponse<HashMap<Integer,SuperFlexLookupModel>> response = res.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (Objects.nonNull(response)) {
                if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                    offers = response.getPayload();
                } else {
                    CCATLogger.DEBUG_LOGGER.info("Error while retrieving super flex threshold offers lookup " + response);
                    throw new CIServiceException(response.getStatusCode(), Defines.SEVERITY.ERROR, response.getStatusMessage());
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + offers + "]");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while retrieving getSuperFlexThresholdOffers ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving getSuperFlexThresholdOffers ", ex);
            throw new CIServiceException(ErrorCodes.ERROR.LOOKUP_SERVER_UNREACHABLE);
        }
        return offers;
    }
}
