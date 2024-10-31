package com.asset.ccat.gateway.controllers.customer_care;

import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.annotation.SubscriberOwnership;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.customer_care.customer_dynamic_page.ExecuteDynamicPageStepRequest;
import com.asset.ccat.gateway.models.requests.customer_care.customer_dynamic_page.ViewDynamicPageRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.customer_care.ExecuteDynamicPageStepResponse;
import com.asset.ccat.gateway.models.responses.customer_care.ViewDynamicPageResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.DynamicPageService;
import com.asset.ccat.gateway.validators.customer_care.CustomerDynamicPageValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping(value = Defines.ContextPaths.CUSTOMER_DYNAMIC_PAGE)
public class CustomerDynamicPagesController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private CustomerDynamicPageValidator customerDynamicPageValidator;
    @Autowired
    private DynamicPageService dynamicPageService;

    @LogFootprint
    @PostMapping(value = Defines.WEB_ACTIONS.VIEW)
    public BaseResponse<ViewDynamicPageResponse> viewDynamicPage(@RequestBody ViewDynamicPageRequest request) throws GatewayException {
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
        CCATLogger.DEBUG_LOGGER.info("Received View Dynamic Page Request [" + request + "]");
        customerDynamicPageValidator.validateViewDynamicPage(request);
        ViewDynamicPageResponse page = dynamicPageService.viewDynamicPage(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving View Dynamic Page Request Successfully!!");


        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                page);
    }

    @SubscriberOwnership
    @PostMapping(value = Defines.ContextPaths.STEP + Defines.WEB_ACTIONS.EXECUTE)
    public BaseResponse<ExecuteDynamicPageStepResponse> executeStep(@RequestBody ExecuteDynamicPageStepRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Execute Dynamic Page Step Request [" + request + "]");
        customerDynamicPageValidator.validateExecuteStepRequest(request);
        ExecuteDynamicPageStepResponse response = dynamicPageService.executeDynamicPageStep(request);
        CCATLogger.DEBUG_LOGGER.info("Received Execute Dynamic Page Step Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                response);
    }
}
