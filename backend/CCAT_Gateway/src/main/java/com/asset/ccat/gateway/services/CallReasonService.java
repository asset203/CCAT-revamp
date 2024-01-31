package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.customer_care.CallReasonModel;
import com.asset.ccat.gateway.models.requests.customer_care.call_reasons.AddCallReasonRequest;
import com.asset.ccat.gateway.models.requests.customer_care.call_reasons.CheckCallReasonRequest;
import com.asset.ccat.gateway.models.requests.customer_care.call_reasons.UpdateCallReasonRequest;
import com.asset.ccat.gateway.models.responses.customer_care.AddCallReasonResponse;
import com.asset.ccat.gateway.proxy.CallReasonProxy;
import org.springframework.stereotype.Service;

@Service
public class CallReasonService {

    private final CallReasonProxy callReasonProxy;

    public CallReasonService(CallReasonProxy callReasonProxy) {
        this.callReasonProxy = callReasonProxy;
    }

    public AddCallReasonResponse addCallReason(AddCallReasonRequest addCallReasonRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("CallReasonService -> addCallReason() : Started");
        CallReasonModel callReason =  callReasonProxy.addCallReason(addCallReasonRequest);
        AddCallReasonResponse response = new AddCallReasonResponse(callReason);
        CCATLogger.DEBUG_LOGGER.debug("CallReasonService -> addCallReason() : Ended Successfully ");
        return response;
    }

    public void updateCallReason(UpdateCallReasonRequest updateCallReasonRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("CallReasonService -> updateCallReason() : Started");
        callReasonProxy.updateCallReason(updateCallReasonRequest);
        CCATLogger.DEBUG_LOGGER.debug("CallReasonService -> updateCallReason() : Ended Successfully ");
    }
    public CallReasonModel checkCallReason(CheckCallReasonRequest checkCallReasonRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("CallReasonService -> checkCallReason() : Started");
        CallReasonModel callReasonModel = callReasonProxy.checkCallReason(checkCallReasonRequest);
        CCATLogger.DEBUG_LOGGER.debug("CallReasonService -> checkCallReason() : Ended Successfully ");
        return callReasonModel;
    }
}
