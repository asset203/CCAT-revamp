package com.asset.ccat.ci_service.controllers;


import com.asset.ccat.ci_service.defines.Defines;
import com.asset.ccat.ci_service.defines.ErrorCodes;
import com.asset.ccat.ci_service.exceptions.CIException;
import com.asset.ccat.ci_service.exceptions.CIServiceException;
import com.asset.ccat.ci_service.logger.CCATLogger;
import com.asset.ccat.ci_service.models.requests.flex_share.GetFlexShareEligibilityRequest;
import com.asset.ccat.ci_service.models.responses.BaseResponse;
import com.asset.ccat.ci_service.models.responses.flex_share.GetFlexShareEligibilityResponse;
import com.asset.ccat.ci_service.services.FlexShareService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(Defines.ContextPaths.FLEX_SHARE)
public class FlexShareController {

    private final FlexShareService flexShareService;

    public FlexShareController(FlexShareService flexShareService) {
        this.flexShareService = flexShareService;
    }

    @PostMapping(Defines.FLEX_SHARE_ACTIONS.ELIGIBILITY+ Defines.WEB_ACTIONS.GET)
    public BaseResponse<GetFlexShareEligibilityResponse> getFlexShareEligibility
            (@RequestBody GetFlexShareEligibilityRequest request) throws CIServiceException, CIException {
        CCATLogger.DEBUG_LOGGER.info("Starting FlexShare Controller At CI service - getFlexShareEligibility()");
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        CCATLogger.DEBUG_LOGGER.info(" Flex-share getFlexShareEligibility request [" + request + "]");
        GetFlexShareEligibilityResponse response = flexShareService.getFlexShareEligibility(request);
        CCATLogger.DEBUG_LOGGER.info("FlexShare Controller At CI service - getFlexShareEligibility() Ended Successfully");
        BaseResponse<GetFlexShareEligibilityResponse> baseResponse = new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, response);
        return baseResponse;
    }
}
