package com.asset.ccat.gateway.proxy.admin.vip;

import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.admin.vip.VIPListsResponse;
import com.asset.ccat.gateway.models.admin.vip.VIPUpdatePagesRequest;
import com.asset.ccat.gateway.models.requests.admin.vip.VIPRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class VIPLookupProxy {

    private final WebClient webClient;
    private final Properties properties;

    @Autowired
    public VIPLookupProxy(WebClient webClient, Properties properties) {
        this.webClient = webClient;
        this.properties = properties;
    }

    public BaseResponse<VIPListsResponse> getVIPs(VIPRequest getVipRequest) throws GatewayException {
        String uri = buildUri(Defines.LOOKUP_SERVICE.VIP, Defines.WEB_ACTIONS.GET_ALL);
        CCATLogger.DEBUG_LOGGER.debug("Fetching VIPs with request: {}", getVipRequest);
        return performPostRequest(uri, getVipRequest);
    }

    public void addVIPMsisdn(VIPRequest addVipRequest) throws GatewayException {
        String uri = buildUri(Defines.LOOKUP_SERVICE.VIP, Defines.LOOKUP_SERVICE.MSISDN, Defines.WEB_ACTIONS.ADD);
        CCATLogger.DEBUG_LOGGER.debug("Adding VIP MSISDN with request: {}", addVipRequest);
        performPostRequest(uri, addVipRequest);
    }

    public void deleteVIPMsisdn(VIPRequest deleteVipRequest) throws GatewayException {
        String uri = buildUri(Defines.LOOKUP_SERVICE.VIP, Defines.LOOKUP_SERVICE.MSISDN, Defines.WEB_ACTIONS.DELETE);
        CCATLogger.DEBUG_LOGGER.debug("Deleting VIP MSISDN with request: {}", deleteVipRequest);
        performPostRequest(uri, deleteVipRequest);
    }

    public void syncVIPPages(VIPUpdatePagesRequest vipRequest) throws GatewayException {
        String uri = buildUri(Defines.LOOKUP_SERVICE.VIP, Defines.LOOKUP_SERVICE.PAGE, Defines.WEB_ACTIONS.UPDATE);
        CCATLogger.DEBUG_LOGGER.debug("Syncing VIP pages with request: {}", vipRequest);
        performPostRequest(uri, vipRequest);
    }

    private <T> BaseResponse<T> performPostRequest(String uri, Object requestBody) throws GatewayException {
        try {
            CCATLogger.DEBUG_LOGGER.debug("Performing POST request to URI: {}, with body: {}", uri, requestBody);
            BaseResponse response = webClient.post()
                    .uri(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();

            CCATLogger.DEBUG_LOGGER.debug("Received response: {}", response);

            if (response == null || response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                String errorMessage = response != null ? response.getStatusMessage() : "Unknown error";
                throw new GatewayException(response != null ? response.getStatusCode() : ErrorCodes.ERROR.UNKNOWN_ERROR, errorMessage);
            }
            return response;
        } catch (GatewayException ex) {
            throw ex;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception occurred during WebClient call to URI: {}", uri, ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "Failed to reach lookup-service");
        }
    }

    /**
     * Builds a full URI for the lookup service.
     *
     * @param pathSegments the path segments to append
     * @return the full URI as a string
     */
    private String buildUri(String... pathSegments) {
        StringBuilder uriBuilder = new StringBuilder(properties.getLookupsServiceUrls())
                .append(Defines.LOOKUP_SERVICE.CONTEXT_PATH);
        for (String segment : pathSegments)
            uriBuilder.append(segment);

        return uriBuilder.toString();
    }
}
