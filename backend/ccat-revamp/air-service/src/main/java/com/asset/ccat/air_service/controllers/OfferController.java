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
        CCATLogger.DEBUG_LOGGER.info("Received Get Offers Request [" + offerRequest + "]");
        ThreadContext.put("sessionId", offerRequest.getSessionId());
        ThreadContext.put("requestId", offerRequest.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("IP => " + InetAddress.getLocalHost().getHostAddress() + environment.getProperty("server.port"));
        GetAllOffersResponse response = offerService.getOffers(offerRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Offers Request Successfully with size [" + response.getOffers().size() + "]");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse addOffer(@RequestBody OfferRequest offerRequest) throws AIRServiceException, AIRException, UnknownHostException {
        CCATLogger.DEBUG_LOGGER.info("Received Add Offer Request [" + offerRequest + "]");
        ThreadContext.put("sessionId", offerRequest.getSessionId());
        ThreadContext.put("requestId", offerRequest.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("IP => " + InetAddress.getLocalHost().getHostAddress() + environment.getProperty("server.port"));
        offerService.addAndUpdateOffer(offerRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Add Offers Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateOffer(@RequestBody OfferRequest offerRequest) throws AIRServiceException, AIRException, UnknownHostException {
        CCATLogger.DEBUG_LOGGER.info("Received Update Offer Request [" + offerRequest + "]");
        ThreadContext.put("sessionId", offerRequest.getSessionId());
        ThreadContext.put("requestId", offerRequest.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("IP => " + InetAddress.getLocalHost().getHostAddress() + environment.getProperty("server.port"));
        offerService.addAndUpdateOffer(offerRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Offer Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse deleteOffer(@RequestBody DeleteOfferRequest offerRequest) throws AIRServiceException, AIRException, UnknownHostException {
        CCATLogger.DEBUG_LOGGER.info("Received Delete Offer Request [" + offerRequest + "]");
        ThreadContext.put("sessionId", offerRequest.getSessionId());
        ThreadContext.put("requestId", offerRequest.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("IP => " + InetAddress.getLocalHost().getHostAddress() + environment.getProperty("server.port"));
        offerService.deleteOffer(offerRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving delete Offer Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, null);
    }

}
