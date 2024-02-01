package com.asset.ccat.gateway.controllers.customer_care;

import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.customer_care.pam_information.AddPamInformationRequest;
import com.asset.ccat.gateway.models.requests.customer_care.pam_information.DeletePamInformationRequest;
import com.asset.ccat.gateway.models.requests.customer_care.pam_information.EvaluatePamInformationRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.PamInformationService;
import com.asset.ccat.gateway.validators.customer_care.PamInformationValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author nour.ihab
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = Defines.ContextPaths.PAM_INFORMATION)
public class PamInformationController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PamInformationValidator pamInformationValidator;
    @Autowired
    private PamInformationService pamInformationService;


    @LogFootprint
    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse addPamInformation(HttpServletRequest req,
                                          @RequestBody AddPamInformationRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Add Pam Information Request [" + request + "]");
//        pamInformationValidator.validateAddPamInfo(request);
        pamInformationService.addPamInfo(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Add Pam Information Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }


    @LogFootprint
    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse evaluatePamInformation(HttpServletRequest req,
                                               @RequestBody EvaluatePamInformationRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Evaluate Pam Information Request [" + request + "]");
        pamInformationValidator.validateEvaluatePamInfo(request);
        pamInformationService.evaluatePamInfo(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Evaluate Pam Information Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }


    @LogFootprint
    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse deletePamInformation(HttpServletRequest req,
                                             @RequestBody DeletePamInformationRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Delete Pam Information Request [" + request + "]");
        pamInformationValidator.validateDeletePamInfo(request);
        pamInformationService.deletePamInfo(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Delete Pam Information Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }
}
