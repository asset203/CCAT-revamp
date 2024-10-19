package com.asset.ccat.air_service.controllers;

import com.asset.ccat.air_service.defines.Defines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.requests.offer.DeleteOfferRequest;
import com.asset.ccat.air_service.models.requests.offer.GetOfferRequest;
import com.asset.ccat.air_service.models.requests.offer.OfferRequest;
import com.asset.ccat.air_service.models.responses.BaseResponse;
import com.asset.ccat.air_service.models.responses.offer.GetAllOffersResponse;
import com.asset.ccat.air_service.services.OfferService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author wael.mohamed
 */
@RestController
@RequestMapping(value = Defines.ContextPaths.OFFERS)
public class OfferController {

    @Autowired
    Environment environment;

    @Autowired
    private OfferService offerService;

    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllOffersResponse> getOffers(@RequestBody GetOfferRequest offerRequest) throws AIRServiceException, AIRException, UnknownHostException {
        ThreadContext.put("sessionId", offerRequest.getSessionId());
        ThreadContext.put("requestId", offerRequest.getRequestId());
        CCATLogger.DEBUG_LOGGER.debug("Get Offers request started for MSISDN = {} ", offerRequest.getMsisdn());
        GetAllOffersResponse response = offerService.getOffers(offerRequest);
        CCATLogger.DEBUG_LOGGER.debug("Get Offers request Ended. for MSISDN = {} ", offerRequest.getMsisdn());
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse<String> addOffer(@RequestBody OfferRequest offerRequest) throws AIRServiceException, UnknownHostException {
        ThreadContext.put("sessionId", offerRequest.getSessionId());
        ThreadContext.put("requestId", offerRequest.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Add Offer Request started with body= [{}]", offerRequest);
        offerService.addAndUpdateOffer(offerRequest);
        CCATLogger.DEBUG_LOGGER.info("Add offer request ended.");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse<String> updateOffer(@RequestBody OfferRequest offerRequest) throws AIRServiceException, UnknownHostException {
        ThreadContext.put("sessionId", offerRequest.getSessionId());
        ThreadContext.put("requestId", offerRequest.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Update Offer Request started with body= [{}]", offerRequest);
        offerService.addAndUpdateOffer(offerRequest);
        CCATLogger.DEBUG_LOGGER.info("Update offer request ended.");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse<String> deleteOffer(@RequestBody DeleteOfferRequest offerRequest) throws AIRServiceException, AIRException, UnknownHostException {
        ThreadContext.put("sessionId", offerRequest.getSessionId());
        ThreadContext.put("requestId", offerRequest.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Delete Offer request started with body = {}", offerRequest);
        offerService.deleteOffer(offerRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving delete Offer Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, null);
    }

}
