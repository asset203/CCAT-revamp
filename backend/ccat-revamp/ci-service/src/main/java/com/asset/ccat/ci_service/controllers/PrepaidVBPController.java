package com.asset.ccat.ci_service.controllers;

import com.asset.ccat.ci_service.defines.Defines;
import com.asset.ccat.ci_service.defines.ErrorCodes;
import com.asset.ccat.ci_service.exceptions.CIException;
import com.asset.ccat.ci_service.exceptions.CIServiceException;
import com.asset.ccat.ci_service.logger.CCATLogger;
import com.asset.ccat.ci_service.models.requests.prepaidVBP.PrepaidCheckSubscriptionRequest;
import com.asset.ccat.ci_service.models.requests.prepaidVBP.PrepaidVBPSubscriptionRequest;
import com.asset.ccat.ci_service.models.requests.prepaidVBP.PrepaidVBPUnsubscriptionRequest;
import com.asset.ccat.ci_service.models.responses.BaseResponse;
import com.asset.ccat.ci_service.models.responses.PrepaidVBPCheckResponse;
import com.asset.ccat.ci_service.services.PrepaidVBPService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
@RequestMapping(value = Defines.ContextPaths.PREPAID_VBP)
public class PrepaidVBPController {

    @Autowired
    private PrepaidVBPService prepaidVBPService;
    @PostMapping(Defines.WEB_ACTIONS.CHECK)
    public BaseResponse<PrepaidVBPCheckResponse> checkSubscription(@RequestBody PrepaidCheckSubscriptionRequest prepaidCheckSubscriptionRequest) throws CIServiceException, CIException {
        CCATLogger.DEBUG_LOGGER.info("Starting PrepaidVBP Controller At CI service - checkSubscription()");
        ThreadContext.put("requestId", prepaidCheckSubscriptionRequest.getRequestId());
        ThreadContext.put("sessionId", prepaidCheckSubscriptionRequest.getSessionId());
        CCATLogger.DEBUG_LOGGER.info(" Prepaid-VBP checkSubscription request [" + prepaidCheckSubscriptionRequest + "]");
        PrepaidVBPCheckResponse response = prepaidVBPService.prepaidVBPCheckSubscription(prepaidCheckSubscriptionRequest);
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, response);
    }
    @PostMapping(Defines.WEB_ACTIONS.SUBSCRIBE)
    public BaseResponse<String> subscribe(@RequestBody PrepaidVBPSubscriptionRequest prepaidVBPSubscriptionRequest) throws CIServiceException, CIException {
        ThreadContext.put("requestId", prepaidVBPSubscriptionRequest.getRequestId());
        ThreadContext.put("sessionId", prepaidVBPSubscriptionRequest.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Prepaid-VBP subscription request [{}]", prepaidVBPSubscriptionRequest);
        String response = prepaidVBPService.prepaidVBPSubscription(prepaidVBPSubscriptionRequest);
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR,response);
    }

    @PostMapping(Defines.WEB_ACTIONS.UNSUBSCRIBE)
    public BaseResponse<String> unsubscribe(@RequestBody PrepaidVBPUnsubscriptionRequest prepaidVBPUnsubscriptionRequest) throws CIServiceException, CIException {
        CCATLogger.DEBUG_LOGGER.info("Starting PrepaidVBP Controller At CI service - unsubscribe()");
        ThreadContext.put("requestId", prepaidVBPUnsubscriptionRequest.getRequestId());
        ThreadContext.put("sessionId", prepaidVBPUnsubscriptionRequest.getSessionId());
        CCATLogger.DEBUG_LOGGER.info(" Prepaid-VBP unsubscription request [" + prepaidVBPUnsubscriptionRequest + "]");
        String response = prepaidVBPService.prepaidVBPUnsubscription(prepaidVBPUnsubscriptionRequest);
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR,response);

    }

}
