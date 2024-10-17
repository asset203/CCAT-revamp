package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * Gathers footprint data from four main sources:
 * 1. Requests received (msisdn, sendSms).
 * 2. Cached data (retrieved from lookupsCache for page, action, and type).
 * 3. Token data (session, username, profile data, machine name).
 * 4. System's response (error code, error message, status and requestId).
 */

public interface FootprintPopulationService {
    void populateRequestData(BaseRequest request);
    void populateResponseData(Object response, Throwable throwable);
    void populateTokenData(String token) throws GatewayException;
    void populateCachedData(String controllerName, String methodName);
}
