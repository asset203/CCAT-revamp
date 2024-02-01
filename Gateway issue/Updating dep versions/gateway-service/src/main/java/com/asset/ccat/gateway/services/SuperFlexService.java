package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.SubscriberRequest;
import com.asset.ccat.gateway.models.requests.customer_care.super_flex.ActivateAddonRequest;
import com.asset.ccat.gateway.models.requests.customer_care.super_flex.ActivateThresholdRequest;
import com.asset.ccat.gateway.models.responses.customer_care.super_flex.GetMIThresholdResponse;
import com.asset.ccat.gateway.models.responses.customer_care.super_flex.GetOptInAddonsResponse;
import com.asset.ccat.gateway.proxy.SuperFlexProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SuperFlexService {

    @Autowired
    private SuperFlexProxy superFlexProxy;


    public GetOptInAddonsResponse getOptinAddons(SubscriberRequest request) throws GatewayException {

        CCATLogger.DEBUG_LOGGER.debug("Started SuperFlexService - getOptinAddons()");
        CCATLogger.DEBUG_LOGGER.info("Start serving getOptinAddons request for subscriber [" + request.getMsisdn() + "] ");
        GetOptInAddonsResponse response = superFlexProxy.getOptinAddons(request);
        CCATLogger.DEBUG_LOGGER.info("Finished serving getOptinAddons request for subscriber [" + request.getMsisdn() + "] sucessfullly");
        CCATLogger.DEBUG_LOGGER.debug("Ended SuperFlexService - getOptinAddons()");
        return response;
    }


    public void activateAddon(ActivateAddonRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started SuperFlexService - activateAddon()");
        CCATLogger.DEBUG_LOGGER.info("Start serving activateAddon request for subscriber [" + request.getMsisdn() + "] ");
        superFlexProxy.activateAddon(request);
        CCATLogger.DEBUG_LOGGER.info("Finished serving activateAddon request for subscriber [" + request.getMsisdn() + "] sucessfullly");
        CCATLogger.DEBUG_LOGGER.debug("Ended SuperFlexService - activateAddon()");
    }


    public void deactivateAddon(SubscriberRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started SuperFlexService - deactivateAddon()");
        CCATLogger.DEBUG_LOGGER.info("Start serving deactivateAddon request for subscriber [" + request.getMsisdn() + "] ");
        superFlexProxy.deactivateAddon(request);
        CCATLogger.DEBUG_LOGGER.info("Finished serving deactivateAddon request for subscriber [" + request.getMsisdn() + "] sucessfullly");
        CCATLogger.DEBUG_LOGGER.debug("Ended SuperFlexService - deactivateAddon()");
    }


    public GetMIThresholdResponse getMiThreshold(SubscriberRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started SuperFlexService - getMiThreshold()");
        CCATLogger.DEBUG_LOGGER.info("Start serving getMiThreshold request for subscriber [" + request.getMsisdn() + "] ");
        GetMIThresholdResponse response = superFlexProxy.getMiThreshold(request);
        CCATLogger.DEBUG_LOGGER.info("Finished serving getMiThreshold request for subscriber [" + request.getMsisdn() + "] sucessfullly");
        CCATLogger.DEBUG_LOGGER.debug("Ended SuperFlexService - getMiThreshold()");
        return response;
    }

    public void activateThreshold(ActivateThresholdRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started SuperFlexService - activateThreshold()");
        CCATLogger.DEBUG_LOGGER.info("Start serving activateThreshold request for subscriber [" + request.getMsisdn() + "] ");
        superFlexProxy.activateThreshold(request);
        CCATLogger.DEBUG_LOGGER.info("Finished serving activateThreshold request for subscriber [" + request.getMsisdn() + "] sucessfullly");
        CCATLogger.DEBUG_LOGGER.debug("Ended SuperFlexService - activateThreshold()");

    }

    public void deactivateThreshold(SubscriberRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started SuperFlexService - deactivateThreshold()");
        CCATLogger.DEBUG_LOGGER.info("Start serving deactivateThreshold request for subscriber [" + request.getMsisdn() + "] ");
        superFlexProxy.deactivateThreshold(request);
        CCATLogger.DEBUG_LOGGER.info("Finished serving deactivateThreshold request for subscriber [" + request.getMsisdn() + "] sucessfullly");
        CCATLogger.DEBUG_LOGGER.debug("Ended SuperFlexService - deactivateThreshold()");
    }

    public void stopMIThreshold(SubscriberRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started SuperFlexService - stopMIThreshold()");
        CCATLogger.DEBUG_LOGGER.info("Start serving stopMIThreshold request for subscriber [" + request.getMsisdn() + "] ");
        superFlexProxy.stopMIThreshold(request);
        CCATLogger.DEBUG_LOGGER.info("Finished serving stopMIThreshold request for subscriber [" + request.getMsisdn() + "] sucessfullly");
        CCATLogger.DEBUG_LOGGER.debug("Ended SuperFlexService - stopMIThreshold()");

    }

    public void deactivateStopMI(SubscriberRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started SuperFlexService - deactivateStopMI()");
        CCATLogger.DEBUG_LOGGER.info("Start serving deactivateStopMI request for subscriber [" + request.getMsisdn() + "] ");
        superFlexProxy.deactivateStopMI(request);
        CCATLogger.DEBUG_LOGGER.info("Finished serving deactivateStopMI request for subscriber [" + request.getMsisdn() + "] sucessfullly");
        CCATLogger.DEBUG_LOGGER.debug("Ended SuperFlexService - deactivateStopMI()");

    }
}
