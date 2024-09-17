/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.nba_service.controllers;

import com.asset.ccat.nba_service.defines.Defines;
import com.asset.ccat.nba_service.defines.ErrorCodes;
import com.asset.ccat.nba_service.exceptions.NBAException;
import com.asset.ccat.nba_service.logger.CCATLogger;
import com.asset.ccat.nba_service.models.requests.AcceptGiftRequest;
import com.asset.ccat.nba_service.models.requests.GiftModel;
import com.asset.ccat.nba_service.models.requests.SendGiftRequest;
import com.asset.ccat.nba_service.models.requests.SubscriberRequest;
import com.asset.ccat.nba_service.models.responses.BaseResponse;
import com.asset.ccat.nba_service.services.TibcoNbaGiftsService;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mahmoud Shehab
 */
@RestController
@RequestMapping(Defines.ContextPaths.TIBCO_GIFTS)
public class TIBCOGiftsController {

  private final Environment environment;
  private final TibcoNbaGiftsService tibcoNbaGiftsService;

  public TIBCOGiftsController(Environment environment, TibcoNbaGiftsService tibcoNbaGiftsService) {
    this.environment = environment;
    this.tibcoNbaGiftsService = tibcoNbaGiftsService;
  }

  @PostMapping(Defines.WEB_ACTIONS.GET_ALL)
  public BaseResponse<List<GiftModel>> getAllGifts(@RequestBody SubscriberRequest subscriberRequest) throws NBAException, UnknownHostException {
    ThreadContext.put("sessionId", subscriberRequest.getSessionId());
    ThreadContext.put("requestId", subscriberRequest.getRequestId());
    CCATLogger.DEBUG_LOGGER.debug("GetAllGifts request started for MSISDN = {}", subscriberRequest.getMsisdn());
    List<GiftModel> response = tibcoNbaGiftsService.getAllTibcoGifts(subscriberRequest);
    CCATLogger.INTERFACE_LOGGER.debug("IP: {}{}", InetAddress.getLocalHost().getHostAddress(), environment.getProperty("server.port"));
    CCATLogger.DEBUG_LOGGER.debug("GetAllGifts request ended.");

    ThreadContext.remove("sessionId");
    ThreadContext.remove("requestId");
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        response);
  }

  @PostMapping(Defines.ContextPaths.ACCEPT)
  public BaseResponse acceptGift(@RequestBody AcceptGiftRequest acceptGiftRequest) throws IOException, NBAException {
    ThreadContext.put("sessionId", acceptGiftRequest.getSessionId());
    ThreadContext.put("requestId", acceptGiftRequest.getRequestId());
    CCATLogger.DEBUG_LOGGER.debug("Redeem tibco gift request started with request = {}", acceptGiftRequest);
    tibcoNbaGiftsService.redeemOffer(acceptGiftRequest);
    CCATLogger.INTERFACE_LOGGER.debug("IP: {}{}", InetAddress.getLocalHost().getHostAddress(), environment.getProperty("server.port"));
    CCATLogger.DEBUG_LOGGER.debug("Redeem tibco gift request ended.");
    ThreadContext.remove("sessionId");
    ThreadContext.remove("requestId");
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        null);
  }

  @PostMapping(Defines.ContextPaths.SEND)
  public BaseResponse sendSMSOffer(@RequestBody SendGiftRequest sendGiftRequest) throws IOException, NBAException {
    ThreadContext.put("sessionId", sendGiftRequest.getSessionId());
    ThreadContext.put("requestId", sendGiftRequest.getRequestId());
    tibcoNbaGiftsService.sendSMSGift(sendGiftRequest);
    CCATLogger.INTERFACE_LOGGER.debug("IP: {}{}", InetAddress.getLocalHost().getHostAddress(), environment.getProperty("server.port"));
    ThreadContext.remove("sessionId");
    ThreadContext.remove("requestId");
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        null);
  }
}
