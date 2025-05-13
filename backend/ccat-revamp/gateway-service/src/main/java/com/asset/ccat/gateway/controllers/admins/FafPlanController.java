package com.asset.ccat.gateway.controllers.admins;

import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.admin_faf_plan.AddAdminFafPlanRequest;
import com.asset.ccat.gateway.models.requests.admin.admin_faf_plan.DeleteAdminFafPlanRequest;
import com.asset.ccat.gateway.models.requests.admin.admin_faf_plan.GetAllAdminFafPlansRequest;
import com.asset.ccat.gateway.models.requests.admin.admin_faf_plan.UpdateAdminFafPlanRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.admin_faf_plan.GetAllAdminFafPlansResponse;
import com.asset.ccat.gateway.models.shared.FafPlanModel;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.admin.AdminFafPlanService;
import com.asset.ccat.gateway.validators.admins.AdminFafPlanValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author mohamed.metwaly
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = Defines.ContextPaths.FAF_PLAN)
public class FafPlanController {
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    AdminFafPlanService adminFafPlanService;
    @Autowired
    AdminFafPlanValidator adminFafPlanValidator;

    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllAdminFafPlansResponse> getAllAdminFafPlans(@RequestBody GetAllAdminFafPlansRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All Admin Faf Plans Request [" + request + "]");
        List<FafPlanModel> plans = this.adminFafPlanService.getAllAdminFafPlans(request);
        GetAllAdminFafPlansResponse payload = new GetAllAdminFafPlansResponse(plans);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All Admin Faf Plans Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                payload);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    @LogFootprint
    public BaseResponse addAdminFafPlan(@RequestBody AddAdminFafPlanRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Add Admin Faf Plans Request [" + request + "]");
        this.adminFafPlanValidator.addAdminFafPlanValidator(request);
        this.adminFafPlanService.addFafPlan(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Add Admin Faf Plans Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    @LogFootprint
    public BaseResponse updateAdminFafPlan(@RequestBody UpdateAdminFafPlanRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Admin Faf Plans Request [" + request + "]");
        this.adminFafPlanValidator.updateAdminFafPlanValidator(request);
        adminFafPlanService.updateFafPlan(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Admin Faf Plans Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);

    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    @LogFootprint
    public BaseResponse deleteAdminFafPlan(@RequestBody DeleteAdminFafPlanRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Delete Admin Faf Plans Request [" + request + "]");
        this.adminFafPlanValidator.deleteAdminFafPlanValidator(request);
        adminFafPlanService.deleteFafPlan(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Delete Admin Faf Plans Request Successfully!!");


        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }
}
