package com.asset.ccat.gateway.controllers.customer_care;

import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.annotation.SubscriberOwnership;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.offer.DeleteOfferRequest;
import com.asset.ccat.gateway.models.requests.offer.GetOfferRequest;
import com.asset.ccat.gateway.models.requests.offer.OfferRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.GetAllOffersResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.OfferService;
import com.asset.ccat.gateway.validators.customer_care.OfferValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

/**
 * @author wael.mohamed
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = Defines.AIR_SERVICE.OFFERS)
public class OfferController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private OfferService offerService;
    @Autowired
    private OfferValidator offerValidator;

    @SubscriberOwnership
    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllOffersResponse> getOffers(@RequestBody GetOfferRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Get Offers request started with body = {}", request);
        offerValidator.validateGetAllOffers(request);
        GetAllOffersResponse offers = offerService.getOffers(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Offer Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                (offers));
    }


    @LogFootprint
    @SubscriberOwnership
    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse<String> addOffer(@RequestBody OfferRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.debug("Add Offer request started with body = {}", request);
        offerValidator.validateOffer(request);
        offerService.addOffer(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Add Offer Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }


    @LogFootprint
    @SubscriberOwnership
    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse<String> updateOffer(@RequestBody OfferRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Offer Request [{}]", request);
        offerValidator.validateOffer(request);
        offerService.updateOffer(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Offer Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }


    @LogFootprint
    @SubscriberOwnership
    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse<String> deleteOffer(@RequestBody DeleteOfferRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Delete Offer Request [{}]", request);
        offerValidator.validateDeleteOffer(request);
        offerService.deleteOffer(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Delete Offer Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }
}
