/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.controllers.nba;

import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.models.nba.AcceptGiftRequest;
import com.asset.ccat.gateway.models.nba.GiftModel;
import com.asset.ccat.gateway.models.nba.RejectGiftRequest;
import com.asset.ccat.gateway.models.nba.SendGiftRequest;
import com.asset.ccat.gateway.models.requests.nba.GetAllGiftsRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.NBAService;
import com.asset.ccat.gateway.validators.customer_care.NBAValidator;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mahmoud Shehab
 */
@RestController
@RequestMapping(Defines.ContextPaths.NBA)
public class NBAController {

  @Autowired
  NBAValidator nbaValidator;
  @Autowired
  NBAService nbaService;
  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @CrossOrigin(origins = "*")
  @RequestMapping(value = Defines.WEB_ACTIONS.GET_ALL, method = RequestMethod.POST)
  public BaseResponse<List<GiftModel>> getGifts(HttpServletRequest req,
      @RequestBody GetAllGiftsRequest getAllGiftsRequest)
      throws AuthenticationException, Exception {
    HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(
        getAllGiftsRequest.getToken());
    String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
    String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
    getAllGiftsRequest.setUsername(username);
    getAllGiftsRequest.setRequestId(UUID.randomUUID().toString());
    getAllGiftsRequest.setSessionId(sessionId);
    ThreadContext.put("sessionId", sessionId);
    ThreadContext.put("requestId", getAllGiftsRequest.getRequestId());
    List<GiftModel> list = nbaService.getAllGifts(getAllGiftsRequest);

    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0, getAllGiftsRequest.getRequestId(),
        list);
  }

  @CrossOrigin(origins = "*")
  @LogFootprint
  @RequestMapping(value = Defines.WEB_ACTIONS.ACCEPT, method = RequestMethod.POST)
  public BaseResponse acceptGifts(HttpServletRequest req,
      @RequestBody AcceptGiftRequest acceptGiftRequest) throws AuthenticationException, Exception {
    HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(
        acceptGiftRequest.getToken());
    String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
    String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
    acceptGiftRequest.setUsername(username);
    acceptGiftRequest.setRequestId(UUID.randomUUID().toString());
    acceptGiftRequest.setSessionId(sessionId);
    ThreadContext.put("sessionId", sessionId);
    ThreadContext.put("requestId", acceptGiftRequest.getRequestId());
    nbaValidator.validateAcceptRequest(acceptGiftRequest);
    nbaService.acceptGift(acceptGiftRequest);

    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0, acceptGiftRequest.getRequestId(),
        null);
  }


  @CrossOrigin(origins = "*")
  @RequestMapping(value = Defines.WEB_ACTIONS.SEND, method = RequestMethod.POST)
  public BaseResponse sendSms(HttpServletRequest req,
      @RequestBody SendGiftRequest sendGiftRequest) throws AuthenticationException, Exception {
    HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(
        sendGiftRequest.getToken());
    String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
    String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
    sendGiftRequest.setUsername(username);
    sendGiftRequest.setRequestId(UUID.randomUUID().toString());
    sendGiftRequest.setSessionId(sessionId);
    ThreadContext.put("sessionId", sessionId);
    ThreadContext.put("requestId", sendGiftRequest.getRequestId());
    nbaValidator.validateSendSmsRequest(sendGiftRequest);
    nbaService.sendSmsGift(sendGiftRequest);

    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0, sendGiftRequest.getRequestId(),
        null);
  }


  @CrossOrigin(origins = "*")
  @LogFootprint
  @RequestMapping(value = Defines.WEB_ACTIONS.REJECT, method = RequestMethod.POST)
  public BaseResponse rejectGift(HttpServletRequest req,
      @RequestBody RejectGiftRequest rejectGiftRequest) throws AuthenticationException, Exception {
    HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(
        rejectGiftRequest.getToken());
    String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
    String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
    rejectGiftRequest.setUsername(username);
    rejectGiftRequest.setRequestId(UUID.randomUUID().toString());
    rejectGiftRequest.setSessionId(sessionId);
    ThreadContext.put("sessionId", sessionId);
    ThreadContext.put("requestId", rejectGiftRequest.getRequestId());
    nbaValidator.validateRejectRequest(rejectGiftRequest);
    nbaService.rejectGift(rejectGiftRequest);

    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0, rejectGiftRequest.getRequestId(),
        null);
  }
}
