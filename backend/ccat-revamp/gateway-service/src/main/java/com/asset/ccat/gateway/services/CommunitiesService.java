package com.asset.ccat.gateway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.CommunitiesRequest;
import com.asset.ccat.gateway.models.requests.GetCommunitiesRequest;
import com.asset.ccat.gateway.models.responses.customer_care.GetCommunitiesResponse;
import com.asset.ccat.gateway.proxy.CommunitiesProxy;

@Component
public class CommunitiesService {

    @Autowired
    CommunitiesProxy communitiesProxy;

    public void updateCommunities(CommunitiesRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER
                .info("Start serving update communities request for subscriber [" + request.getMsisdn() + "] ");
        CCATLogger.DEBUG_LOGGER.debug("Calling CommunitiesProxy - updateCommunities()");
        communitiesProxy.updateCommunities(request);
        CCATLogger.DEBUG_LOGGER.info(
                "Finished serving updateCommunities request for subscriber [" + request.getMsisdn() + "] sucessfullly");
    }

    public GetCommunitiesResponse getCommunities(GetCommunitiesRequest request) throws GatewayException {
        // TODO Auto-generated method stub
        CCATLogger.DEBUG_LOGGER
                .info("Start serving get communities request for subscriber [" + request.getMsisdn() + "] ");
        CCATLogger.DEBUG_LOGGER.debug("Calling CommunitiesProxy - getCommunities()");
        GetCommunitiesResponse res = communitiesProxy.getCommunities(request);
        CCATLogger.DEBUG_LOGGER.info(
                "Finished serving getCommunities request for subscriber [" + request.getMsisdn() + "] successfully");
        return res;
    }
}
