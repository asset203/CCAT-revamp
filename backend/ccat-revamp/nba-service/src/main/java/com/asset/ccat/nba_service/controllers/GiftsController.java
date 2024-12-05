/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.nba_service.controllers;

import com.asset.ccat.nba_service.defines.Defines;
import com.asset.ccat.nba_service.defines.ErrorCodes;
import com.asset.ccat.nba_service.logger.CCATLogger;
import com.asset.ccat.nba_service.models.requests.AcceptGiftRequest;
import com.asset.ccat.nba_service.models.requests.GiftModel;
import com.asset.ccat.nba_service.models.requests.RejectGiftRequest;
import com.asset.ccat.nba_service.models.requests.SendGiftRequest;
import com.asset.ccat.nba_service.models.requests.SubscriberRequest;
import com.asset.ccat.nba_service.models.responses.BaseResponse;
import com.asset.ccat.nba_service.services.AcceptGiftService;
import com.asset.ccat.nba_service.services.GetNBAGiftsService;
import com.asset.ccat.nba_service.services.RejectGiftService;
import com.asset.ccat.nba_service.services.SendSmsGiftService;
import java.net.InetAddress;
import java.util.List;
import javax.security.sasl.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mahmoud Shehab
 */
@RestController
@RequestMapping(Defines.ContextPaths.GIFTS)
public class GiftsController {

  @Autowired
  Environment environment;

  @Autowired
  GetNBAGiftsService nBAService;

  @Autowired
  RejectGiftService rejectGiftService;

  @Autowired
  AcceptGiftService acceptGiftService;

  @Autowired
  SendSmsGiftService sendSmsService;

  @RequestMapping(value = Defines.WEB_ACTIONS.GET_ALL, method = RequestMethod.POST)
  public BaseResponse<List<GiftModel>> getAllGifts(HttpServletRequest req,
      @RequestBody SubscriberRequest subscriberRequest) throws AuthenticationException, Exception {
    ThreadContext.put("sessionId", subscriberRequest.getSessionId());
    ThreadContext.put("requestId", subscriberRequest.getRequestId());
    List<GiftModel> response = nBAService.getAllGifts(subscriberRequest);
    CCATLogger.INTERFACE_LOGGER.debug(
        "IP => " + InetAddress.getLocalHost().getHostAddress() + environment.getProperty(
            "server.port"));

    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        response);
  }

  @RequestMapping(value = Defines.ContextPaths.REJECT, method = RequestMethod.POST)
  public BaseResponse rejectGifts(HttpServletRequest req,
      @RequestBody RejectGiftRequest rejectGiftRequest) throws AuthenticationException, Exception {
    ThreadContext.put("sessionId", rejectGiftRequest.getSessionId());
    ThreadContext.put("requestId", rejectGiftRequest.getRequestId());
    rejectGiftService.rejectService(rejectGiftRequest);
    CCATLogger.INTERFACE_LOGGER.debug(
        "IP => " + InetAddress.getLocalHost().getHostAddress() + environment.getProperty(
            "server.port"));

    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        null);
  }

  @RequestMapping(value = Defines.ContextPaths.ACCEPT, method = RequestMethod.POST)
  public BaseResponse acceptGift(HttpServletRequest req,
      @RequestBody AcceptGiftRequest acceptGiftRequest) throws AuthenticationException, Exception {
    ThreadContext.put("sessionId", acceptGiftRequest.getSessionId());
    ThreadContext.put("requestId", acceptGiftRequest.getRequestId());
    acceptGiftService.acceptGift(acceptGiftRequest);
    CCATLogger.INTERFACE_LOGGER.info(
        "IP => " + InetAddress.getLocalHost().getHostAddress() + environment.getProperty(
            "server.port"));

    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        null);
  }

  @RequestMapping(value = Defines.ContextPaths.SEND, method = RequestMethod.POST)
  public BaseResponse acceptGift(HttpServletRequest req,
      @RequestBody SendGiftRequest sendGiftRequest) throws AuthenticationException, Exception {
    ThreadContext.put("sessionId", sendGiftRequest.getSessionId());
    ThreadContext.put("requestId", sendGiftRequest.getRequestId());
    sendSmsService.sendGift(sendGiftRequest);
    CCATLogger.INTERFACE_LOGGER.info(
        "IP => " + InetAddress.getLocalHost().getHostAddress() + environment.getProperty(
            "server.port"));

    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0, null);
  }
}
