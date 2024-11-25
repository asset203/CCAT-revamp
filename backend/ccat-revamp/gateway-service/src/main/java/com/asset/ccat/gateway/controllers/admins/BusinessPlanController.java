package com.asset.ccat.gateway.controllers.admins;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.business_plan.*;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.business_plan.GetAllBusinessPlansResponse;
import com.asset.ccat.gateway.models.responses.admin.business_plan.GetBusinessPlanResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.admin.BusinessPlanService;
import com.asset.ccat.gateway.validators.admins.BusinessPlanValidator;
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
@RequestMapping(path = Defines.ContextPaths.BUSINESS_PLAN)
public class BusinessPlanController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private BusinessPlanValidator businessPlanValidator;
    @Autowired
    private BusinessPlanService businessPlanService;

    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllBusinessPlansResponse> getAllBusinessPlans(HttpServletRequest req,
                                                                         @RequestBody GetAllBusinessPlansRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All Business Plans Request [" + request + "]");
        GetAllBusinessPlansResponse getAllBusinessPlansResponse = businessPlanService.getAllBusinessPlans(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All Business Plans Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                getAllBusinessPlansResponse);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.GET)
    public BaseResponse<GetBusinessPlanResponse> getBusinessPlan(HttpServletRequest req,
                                                                 @RequestBody GetBusinessPlanRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Business Plan Request [" + request + "]");
        businessPlanValidator.validateGetBusinessPlan(request);
        GetBusinessPlanResponse getBusinessPlanResponse = businessPlanService.getBusinessPlansById(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Business Plan Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                getBusinessPlanResponse);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public ResponseEntity<BaseResponse> updateBusinessPlan(HttpServletRequest req,
                                                           @RequestBody UpdateBusinessPlanRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Business Plan Request [" + request + "]");
        businessPlanValidator.validateUpdateBusinessPlan(request);
        businessPlanService.updateBusinessPlan(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Business Plan Request Successfully!!");

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null), HttpStatus.OK);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public ResponseEntity<BaseResponse> DeleteBusinessPlan(HttpServletRequest req,
                                                           @RequestBody DeleteBusinessPlanRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Delete Business Plan Request [" + request + "]");
        businessPlanValidator.validateDeleteBusinessPlan(request);
        businessPlanService.deleteBusinessPlanById(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Delete Business Plan Request Successfully!!");

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null), HttpStatus.OK);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public ResponseEntity<BaseResponse> addBusinessPlan(HttpServletRequest req,
                                                        @RequestBody AddBusinessPlanRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Add Business Plan Request [" + request + "]");
        businessPlanValidator.validateAddBusinessPlan(request);
        businessPlanService.addBusinessPlan(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Add Business Plan Request Successfully!!");

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null), HttpStatus.OK);
    }
}
