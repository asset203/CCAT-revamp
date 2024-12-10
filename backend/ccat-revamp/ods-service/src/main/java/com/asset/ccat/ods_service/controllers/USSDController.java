/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.ods_service.controllers;

import com.asset.ccat.ods_service.defines.Defines;
import com.asset.ccat.ods_service.defines.ErrorCodes;
import com.asset.ccat.ods_service.exceptions.ODSException;
import com.asset.ccat.ods_service.logger.CCATLogger;
import com.asset.ccat.ods_service.models.requests.DSSReportRequest;
import com.asset.ccat.ods_service.models.responses.BaseResponse;
import com.asset.ccat.ods_service.models.responses.DSSResponseModel;
import com.asset.ccat.ods_service.services.USSDService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author wael.mohamed
 */
@RestController
@RequestMapping(value = Defines.ContextPaths.DSS_REPORT)
public class USSDController {

    private final USSDService uSSDService;

    public USSDController(USSDService uSSDService) {
        this.uSSDService = uSSDService;
    }

    @PostMapping(value = Defines.ContextPaths.USSD_REPORT + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<DSSResponseModel> getUSSDReport(@RequestBody DSSReportRequest request) throws ODSException {
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.debug("Get USSD Report request started with body = {}", request);
        DSSResponseModel response = uSSDService.getReport(request);
        CCATLogger.DEBUG_LOGGER.debug("Get USSD Report request ended.");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, response);
    }

}
