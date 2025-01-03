package com.asset.ccat.ci_service.controllers;

import com.asset.ccat.ci_service.defines.Defines;
import com.asset.ccat.ci_service.defines.ErrorCodes;
import com.asset.ccat.ci_service.exceptions.CIException;
import com.asset.ccat.ci_service.exceptions.CIServiceException;
import com.asset.ccat.ci_service.logger.CCATLogger;
import com.asset.ccat.ci_service.models.requests.ServiceClassConversionRequest;
import com.asset.ccat.ci_service.models.responses.BaseResponse;
import com.asset.ccat.ci_service.services.ServiceClassService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wael.mohamed
 */
@RestController
@RequestMapping(path = Defines.ContextPaths.SERVICE_CLASSES)
public class ServiceClassController {

    @Autowired
    ServiceClassService serviceClassService;

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse<String> updateServiceClass(HttpServletRequest httpRequest,
            @RequestBody ServiceClassConversionRequest request) throws CIServiceException, CIException {
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Service Classes conversion request Started with request ={} ", request);
        serviceClassService.serviceClassConversion(request);
        CCATLogger.DEBUG_LOGGER.info("Service Classes conversion request Ended");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, null);
    }

}
