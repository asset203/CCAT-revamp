package com.asset.ccat.procedureservice.controllers;

import com.asset.ccat.procedureservice.constants.FlexType;
import com.asset.ccat.procedureservice.defines.Defines;
import com.asset.ccat.procedureservice.defines.ErrorCodes;
import com.asset.ccat.procedureservice.dto.requests.flex_share.FlexShareInquiryRequest;
import com.asset.ccat.procedureservice.dto.requests.flex_share.FlexShareUpdateRequest;
import com.asset.ccat.procedureservice.dto.responses.BaseResponse;
import com.asset.ccat.procedureservice.dto.responses.flex_share.FlexShareInquiryResponse;
import com.asset.ccat.procedureservice.exceptions.FlexShareException;
import com.asset.ccat.procedureservice.exceptions.ProcedureException;
import com.asset.ccat.procedureservice.logger.CCATLogger;
import com.asset.ccat.procedureservice.services.FlexShareService;
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

    @PostMapping(value = Defines.WEB_ACTIONS.INQUIRY)
    public BaseResponse<FlexShareInquiryResponse> inquiry(@RequestBody FlexShareInquiryRequest request) throws ProcedureException, FlexShareException {
        CCATLogger.DEBUG_LOGGER.debug("FlexShareController -> inquiry() : Started");
        CCATLogger.DEBUG_LOGGER.debug("FlexShareController -> inquiry() Request : { " + request + " }");

        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());

        FlexShareInquiryResponse payload = flexShareService.inquiry(request);
        BaseResponse<FlexShareInquiryResponse> response = new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "success", Defines.SEVERITY.CLEAR, request.getRequestId(), payload);
        CCATLogger.DEBUG_LOGGER.debug("FlexShareController -> inquiry() : Ended Successfully");

        return response;
    }
    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse update(@RequestBody FlexShareUpdateRequest request) throws ProcedureException, FlexShareException {
        CCATLogger.DEBUG_LOGGER.debug("FlexShareController -> update() : Started");
        CCATLogger.DEBUG_LOGGER.debug("FlexShareController -> update() Request : { " + request + " }");

        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());

        flexShareService.update(request);
        BaseResponse response = new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "success", Defines.SEVERITY.CLEAR, request.getRequestId(), null);
        CCATLogger.DEBUG_LOGGER.debug("FlexShareController -> update() : Ended Successfully");

        return response;
    }

}
