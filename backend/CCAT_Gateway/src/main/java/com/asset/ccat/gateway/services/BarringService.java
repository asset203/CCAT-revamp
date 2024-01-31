package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.BarringRequest;
import com.asset.ccat.gateway.models.requests.GetBarringReasonRequest;
import com.asset.ccat.gateway.models.requests.SubscriberRequest;
import com.asset.ccat.gateway.models.responses.customer_care.GetBarringReasonResponse;
import com.asset.ccat.gateway.proxy.BarringProxy;
import com.asset.ccat.gateway.proxy.LookupsServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BarringService {
    @Autowired
    private BarringProxy barringProxy;

    @Autowired
    private LookupsServiceProxy lookupsServiceProxy;

    public void barTemporaryBlocked(BarringRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Start serving barTemporaryBlocked request for subscriber [" + request.getMsisdn() + "] ");
        CCATLogger.DEBUG_LOGGER.debug("Calling BarringProxy - barTemporaryBlocked()");
        barringProxy.barTemporaryBlocked(request);
        CCATLogger.DEBUG_LOGGER.info("Finished serving barTemporaryBlocked request for subscriber [" + request.getMsisdn() + "] sucessfullly");

    }

    public void unbarTemporaryBlocked(BarringRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Start serving unbarTemporaryBlocked request for subscriber [" + request.getMsisdn() + "] ");
        CCATLogger.DEBUG_LOGGER.debug("Calling BarringProxy - unbarTemporaryBlocked()");
        barringProxy.unbarTemporaryBlocked(request);
        CCATLogger.DEBUG_LOGGER.info("Finished serving unbarTemporaryBlocked request for subscriber [" + request.getMsisdn() + "] sucessfullly");
    }

    public void unbarRefillBarring(BarringRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Start serving unbarRefillBarring request for subscriber [" + request.getMsisdn() + "] ");
        CCATLogger.DEBUG_LOGGER.debug("Calling BarringProxy - unbarRefillBarring()");
        barringProxy.unbarRefillBarring(request);
        CCATLogger.DEBUG_LOGGER.info("Finished serving unbarRefillBarring request for subscriber [" + request.getMsisdn() + "] sucessfullly");

    }

    public GetBarringReasonResponse getBarringReason(GetBarringReasonRequest getBarringReasonRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Start serving getBarringReason request for subscriber [" + getBarringReasonRequest.getMsisdn() + "] ");
        CCATLogger.DEBUG_LOGGER.debug("Calling LookupServiceProxy - getBarringReason()");
        GetBarringReasonResponse res = lookupsServiceProxy.getBarringReason(getBarringReasonRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished serving getBarringReason request for subscriber [" + getBarringReasonRequest.getMsisdn() + "] sucessfullly");
        return res;
    }
}
