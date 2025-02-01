package com.asset.ccat.gateway.controllers.customer_care;

import com.asset.ccat.gateway.annotation.SubscriberOwnership;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.customer_care.ServiceOfferingPlanModel;
import com.asset.ccat.gateway.models.requests.customer_care.service_offering_plans.GetServiceOfferingsRequest;
import com.asset.ccat.gateway.models.requests.customer_care.service_offering_plans.UpdateServiceOfferingRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.customer_care.GetAllServiceOfferingsResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.ServiceOfferingDetailsService;
import com.asset.ccat.gateway.validators.customer_care.ServiceOfferingValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping(Defines.ContextPaths.SERVICE_OFFERING)
public class ServiceOfferingController {

    private final JwtTokenUtil jwtTokenUtil;
    private final ServiceOfferingDetailsService serviceOfferingDetailsService;
    private final ServiceOfferingValidator serviceOfferingValidator;

    public ServiceOfferingController(JwtTokenUtil jwtTokenUtil, ServiceOfferingDetailsService serviceOfferingDetailsService, ServiceOfferingValidator serviceOfferingValidator) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.serviceOfferingDetailsService = serviceOfferingDetailsService;
        this.serviceOfferingValidator = serviceOfferingValidator;
    }


    @SubscriberOwnership
    @PostMapping(Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllServiceOfferingsResponse> getAllPlans(@RequestBody GetServiceOfferingsRequest request) throws GatewayException {
        HashMap<String, Object> tokenData = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokenData.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokenData.get(Defines.SecurityKeywords.USERNAME).toString();
        Integer userId = Integer.parseInt(tokenData.get(Defines.SecurityKeywords.USER_ID).toString());
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setSessionId(sessionId);
        request.setRequestId(UUID.randomUUID().toString());
        request.setUserId(userId);
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Service Offerings Request [" + request + "]");
        serviceOfferingValidator.validateGetServiceOfferingRequest(request);
        List<ServiceOfferingPlanModel> planModelList = serviceOfferingDetailsService.getAllServiceOfferingPlansWithBitDetails(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Service Offerings Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                new GetAllServiceOfferingsResponse(planModelList));
    }

    @SubscriberOwnership
    @PostMapping(Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse<String> updatePlan(@RequestBody UpdateServiceOfferingRequest request) throws GatewayException {
        HashMap<String, Object> tokenData = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokenData.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokenData.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        Integer userId = Integer.parseInt(tokenData.get(Defines.SecurityKeywords.USER_ID).toString());
        request.setUsername(username);
        request.setSessionId(sessionId);
        request.setRequestId(UUID.randomUUID().toString());
        request.setUserId(userId);
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Service Offerings Request [" + request + "]");
        serviceOfferingValidator.validateUpdateServiceOfferingRequest(request);
        serviceOfferingDetailsService.updateServiceOffering(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Service Offerings Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }
}
