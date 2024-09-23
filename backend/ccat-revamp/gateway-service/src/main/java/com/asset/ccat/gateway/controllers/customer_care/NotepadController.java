package com.asset.ccat.gateway.controllers.customer_care;

import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.customer_care.notepad.CreateNotePadRequest;
import com.asset.ccat.gateway.models.requests.customer_care.notepad.DeleteNotePadRequest;
import com.asset.ccat.gateway.models.requests.customer_care.notepad.GetAllNotePadRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.customer_care.GetAllNotePadResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.NotePadService;
import com.asset.ccat.gateway.validators.customer_care.NotepadValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

/**
 * @author nour.ihab
 */
@RestController
@RequestMapping(path = Defines.ContextPaths.NOTEPAD)
public class NotepadController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private NotePadService notePadService;
    @Autowired
    private NotepadValidator notepadValidator;


    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    @CrossOrigin(origins = "*")
    public BaseResponse<GetAllNotePadResponse> getAllNotePad(HttpServletRequest req,
                                                             @RequestBody GetAllNotePadRequest request) throws AuthenticationException, GatewayException {
        GetAllNotePadResponse getAllNotePadsResponse = new GetAllNotePadResponse();
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All NotePad Request [" + request + "]");
        getAllNotePadsResponse = notePadService.getAllNotePad(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All NotePad Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                getAllNotePadsResponse);
    }


    @CrossOrigin(origins = "*")
    @LogFootprint
    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse createNotePad(HttpServletRequest req,
                                      @RequestBody CreateNotePadRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        String userId = tokendata.get(Defines.SecurityKeywords.USER_ID).toString();
        String profileName = tokendata.get(Defines.SecurityKeywords.PROFILE_NAME).toString();
        Optional.ofNullable(request.getFootprintModel()).ifPresent(footprintModel ->
                request.getFootprintModel().setProfileName(profileName));
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        request.setUserId(Integer.valueOf(userId));
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Create NotePad Request [" + request + "]");
        notepadValidator.validateCreateNotePad(request);
        notePadService.addNotePad(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Create NotePad Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }


    @CrossOrigin(origins = "*")
    @LogFootprint
    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse deleteNotePad(HttpServletRequest req,
                                      @RequestBody DeleteNotePadRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        String profileName = tokendata.get(Defines.SecurityKeywords.PROFILE_NAME).toString();
        Optional.ofNullable(request.getFootprintModel()).ifPresent(footprintModel ->
                request.getFootprintModel().setProfileName(profileName));
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Delete NotePad Request [" + request + "]");
        notepadValidator.validateDeleteNotePad(request);
        notePadService.deleteNotePad(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Delete NotePad Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }
}
