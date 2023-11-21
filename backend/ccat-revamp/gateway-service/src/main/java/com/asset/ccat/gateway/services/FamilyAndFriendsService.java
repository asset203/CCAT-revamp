package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.customer_care.FamilyAndFriendsModel;
import com.asset.ccat.gateway.models.requests.SubscriberRequest;
import com.asset.ccat.gateway.models.requests.customer_care.family_and_friends.AddFamilyAndFriendsRequest;
import com.asset.ccat.gateway.models.requests.customer_care.family_and_friends.DeleteFamilyAndFriendsRequest;
import com.asset.ccat.gateway.models.requests.customer_care.family_and_friends.UpdateFamilyAndFriendsRequest;
import com.asset.ccat.gateway.models.responses.customer_care.GetFamilyAndFriendsListResponse;
import com.asset.ccat.gateway.proxy.FamilyAndFriendsProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FamilyAndFriendsService {

    @Autowired
    private FamilyAndFriendsProxy familyAndFriendsProxy;

    public GetFamilyAndFriendsListResponse getFAFList(SubscriberRequest subscriberRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started FamilyAndFriendsService - getFAFList()");
        CCATLogger.DEBUG_LOGGER.info("Start serving get family and friends list request for subscriber [" + subscriberRequest.getMsisdn() + "]");
        CCATLogger.DEBUG_LOGGER.info("Finished serving get family and friends list request for subscriber [" + subscriberRequest.getMsisdn() + "]");
        List<FamilyAndFriendsModel> fafList = familyAndFriendsProxy.getFafList(subscriberRequest);
        CCATLogger.DEBUG_LOGGER.debug("Ending FamilyAndFriendsService - getFAFList()");
        return new GetFamilyAndFriendsListResponse(fafList);
    }


    public void addFAFList(AddFamilyAndFriendsRequest addFamilyAndFriendsRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started FamilyAndFriendsService - addFAFList()");
        CCATLogger.DEBUG_LOGGER.info("Start serving add family and friends request for subscriber [" + addFamilyAndFriendsRequest.getMsisdn() + "]");
        familyAndFriendsProxy.addFafList(addFamilyAndFriendsRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished serving add family and friends request for subscriber [" + addFamilyAndFriendsRequest.getMsisdn() + "]");
        CCATLogger.DEBUG_LOGGER.debug("Ending FamilyAndFriendsService - addFAFList()");
    }

    public void updateFAFList(UpdateFamilyAndFriendsRequest updateFamilyAndFriendsRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started FamilyAndFriendsService - updateFAFList()");
        CCATLogger.DEBUG_LOGGER.info("Start serving update family and friends request for subscriber [" + updateFamilyAndFriendsRequest.getMsisdn() + "]");
        familyAndFriendsProxy.updateFafList(updateFamilyAndFriendsRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished serving update family and friends request for subscriber [" + updateFamilyAndFriendsRequest.getMsisdn() + "]");
        CCATLogger.DEBUG_LOGGER.debug("Ending FamilyAndFriendsService - updateFAFList()");
    }

    public void deleteFAFList(DeleteFamilyAndFriendsRequest deleteFamilyAndFriendsRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started FamilyAndFriendsService - deleteFAFList()");
        CCATLogger.DEBUG_LOGGER.info("Start serving delete family and friends request for subscriber [" + deleteFamilyAndFriendsRequest.getMsisdn() + "]");
        familyAndFriendsProxy.deleteFafList(deleteFamilyAndFriendsRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished serving delete family and friends request for subscriber [" + deleteFamilyAndFriendsRequest.getMsisdn() + "]");
        CCATLogger.DEBUG_LOGGER.debug("Ending FamilyAndFriendsService - deleteFAFList()");
    }
}
