package com.asset.ccat.air_service.services;

import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.requests.SubscriberRequest;
import com.asset.ccat.air_service.models.requests.offer.GetOfferRequest;
import com.asset.ccat.air_service.models.responses.customer_care.GetOptInAddonsResponse;
import com.asset.ccat.air_service.models.responses.offer.GetAllOffersResponse;
import com.asset.ccat.air_service.models.shared.SuperFlexLookupModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class SuperFlexService {

    @Autowired
    private OfferService offerService;
    @Autowired
    private LookupsService lookupsService;

    public GetOptInAddonsResponse getOptInAddons(SubscriberRequest request) throws AIRServiceException, AIRException {
        try {
            CCATLogger.DEBUG_LOGGER.debug("Start retrieving subscribed offers for [" + request.getMsisdn() + "]");
            GetOfferRequest getOfferRequest = new GetOfferRequest();
            getOfferRequest.setRequestId(request.getRequestId());
            getOfferRequest.setSessionId(request.getSessionId());
            getOfferRequest.setUsername(request.getUsername());
            getOfferRequest.setUserId(request.getUserId());
            getOfferRequest.setToken(request.getToken());
            getOfferRequest.setMsisdn(request.getMsisdn());
            GetAllOffersResponse allOffers = offerService.getOffers(getOfferRequest);
            CCATLogger.DEBUG_LOGGER.debug("Start retrieving super flex offers lookup");
            HashMap<Integer, SuperFlexLookupModel> offersLookup = lookupsService.getSuperFlexAddonOffers();
            List<String> optinOffersNames = new ArrayList<>();
            CCATLogger.DEBUG_LOGGER.debug("Start creating optin offers names list");
            if (allOffers.getOffers() != null && !allOffers.getOffers().isEmpty()) {
                if (offersLookup != null && !offersLookup.isEmpty()) {
                    CCATLogger.DEBUG_LOGGER.debug("Start mapping subscribed offers names to lookup");
                    allOffers.getOffers().forEach(offer -> {
                        // if lookup name found use it else use id
                        if (offersLookup.containsKey(offer.getOfferId())) {
                            SuperFlexLookupModel lkModel = offersLookup.get(offer.getOfferId());
                            String name = lkModel.getName() == null || lkModel.getName().isEmpty() ?
                                    lkModel.getOfferID().toString() : lkModel.getName();
                            optinOffersNames.add(name);
                        }
                    });
                } else {
                    // if no lookup found use originally retrieved id from air
                    allOffers.getOffers().forEach(offer -> {
                        optinOffersNames.add(offer.getOfferId().toString());
                    });
                }
            }

            return new GetOptInAddonsResponse(optinOffersNames, new ArrayList<>(offersLookup.values()));
        } catch (AIRServiceException | AIRException ex) {
            CCATLogger.DEBUG_LOGGER.debug("Get optin offers ended with air/air-service exception");
            throw ex;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Get optin offers ended with unknown error exception ");
            CCATLogger.DEBUG_LOGGER.debug("Get optin offers ended with unknown error exception[" + ex.getMessage() + "]");
            CCATLogger.DEBUG_LOGGER.error("Get optin offers ended with unknown error exception[" + ex.getMessage() + "]", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }
}
