package com.asset.ccat.gateway.controllers.admins;

import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.marquee.*;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.marquee.GetAllEs2alnyMarqueeResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.MarqueeService;
import com.asset.ccat.gateway.validators.admins.Es2alnyMarqueeValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author nour.ihab
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = Defines.ContextPaths.MARQUEES)
public class Es2alnyMarqueeController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private Es2alnyMarqueeValidator es2alnyMarqueeValidator;
    @Autowired
    private MarqueeService marqueeService;

    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllEs2alnyMarqueeResponse> getAllMarguees(HttpServletRequest req,
                                                                     @RequestBody GetAllEs2alnyMarqueeRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All Es2alny Marquee Request [" + request + "]");
        GetAllEs2alnyMarqueeResponse response = marqueeService.getAllMarquees(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All Es2alny Marquee Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    @LogFootprint
    public ResponseEntity<BaseResponse> createMarquee(HttpServletRequest req,
                                                      @RequestBody CreateMarqueeRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Create Es2alny Marquee Request [" + request + "]");
        es2alnyMarqueeValidator.validateCreateMarquee(request);
        marqueeService.addMarquee(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Create Es2alny Marquee Request Successfully!!");

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null), HttpStatus.OK);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE_ALL)
    @LogFootprint
    public ResponseEntity<BaseResponse> updateAllMarquees(HttpServletRequest req,
                                                          @RequestBody UpdateAllMarqueesRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update All Marquees Request [" + request + "]");
        es2alnyMarqueeValidator.validateUpdateAllMarquees(request);
        marqueeService.updateAllMarquees(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update All Marquees Request Successfully!!");

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null), HttpStatus.OK);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    @LogFootprint
    public ResponseEntity<BaseResponse> updateMarquee(HttpServletRequest req,
                                                      @RequestBody UpdateMarqueesRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Marquees Request [" + request + "]");
        es2alnyMarqueeValidator.validateUpdateMarquee(request);
        marqueeService.updateMarquee(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Marquees Request Successfully!!");

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null), HttpStatus.OK);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    @LogFootprint
    public ResponseEntity<BaseResponse> DeleteMarquee(HttpServletRequest req,
                                                      @RequestBody DeleteMarqueeRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Delete Marquee Request [" + request + "]");
        es2alnyMarqueeValidator.validateDeleteMarquee(request);
        marqueeService.deleteMarqueeById(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Delete Marquee Request Successfully!!");

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null), HttpStatus.OK);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE_ALL)
    @LogFootprint
    public ResponseEntity<BaseResponse> DeleteAllMarquees(HttpServletRequest req,
                                                          @RequestBody DeleteAllMarqueesRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Delete All Marquees Request [" + request + "]");
        marqueeService.deleteAllMarquee(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Delete All Marquees Request Successfully!!");

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null), HttpStatus.OK);
    }
}
