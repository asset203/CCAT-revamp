package com.asset.ccat.dynamic_page.controllers;

import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.models.dynamic_page.HttpParameterModel;
import com.asset.ccat.dynamic_page.models.requests.ParseHttpParametersRequest;
import com.asset.ccat.dynamic_page.models.requests.dynamic_page.ResponseParameterParsingRequest;
import com.asset.ccat.dynamic_page.models.responses.BaseResponse;
import com.asset.ccat.dynamic_page.models.responses.dynamic_page.ResponseParameterParsingResponse;
import com.asset.ccat.dynamic_page.services.HttpManagementService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = Defines.ContextPaths.HTTP_MANAGEMENT)
public class HttpManagementController {

    private final HttpManagementService httpManagementService;

    public HttpManagementController(HttpManagementService httpManagementService) {
        this.httpManagementService = httpManagementService;
    }

    @PostMapping(value = Defines.ContextPaths.PARSE_QUERY)
    public BaseResponse<List<HttpParameterModel>> parseQuery(
            @RequestBody ParseHttpParametersRequest request) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started HttpManagementController - parseQuery()");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received parseQuery Request");
        List<HttpParameterModel> params = httpManagementService.parseHttpParametersQuery(request);
        CCATLogger.DEBUG_LOGGER.info("parseQuery request finished successfully");
        CCATLogger.DEBUG_LOGGER.debug("Ending HttpManagementController - parseQuery()");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "success", 0,
                params);
    }

    @PostMapping(value = Defines.ContextPaths.PARSE_RESPONSE_PARAMETERS)
    public BaseResponse<ResponseParameterParsingResponse> parseResponseHttpParameters(@RequestBody ResponseParameterParsingRequest request) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started HttpManagementController - parseResponseHttpParameters()");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Parse Response Parameters Request");
        ResponseParameterParsingResponse params = httpManagementService.parseResponseHttpParameters(request);
        CCATLogger.DEBUG_LOGGER.info("Parse Response Parameters finished successfully");
        CCATLogger.DEBUG_LOGGER.debug("Ending HttpManagementController - parseResponseHttpParameters()");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "success", 0,
                params);
    }
}
