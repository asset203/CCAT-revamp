package com.asset.ccat.ods_service.controllers;

import com.asset.ccat.ods_service.defines.Defines;
import com.asset.ccat.ods_service.defines.ErrorCodes;
import com.asset.ccat.ods_service.exceptions.ODSException;
import com.asset.ccat.ods_service.models.requests.DSSReportRequest;
import com.asset.ccat.ods_service.models.responses.BaseResponse;
import com.asset.ccat.ods_service.models.responses.DSSResponseModel;
import com.asset.ccat.ods_service.services.VodafoneOneRedeemService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping(value = Defines.ContextPaths.DSS_REPORT)
public class VodafoneOneRedeemController {

    @Autowired
    private VodafoneOneRedeemService vodafoneOneRedeemService;

    @PostMapping(value = Defines.ContextPaths.VODAFONE_ONE_REDEEM + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<DSSResponseModel> getVodafoneOneRedeemReport(@RequestBody DSSReportRequest request) throws ODSException, SQLException {
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        DSSResponseModel resposne = vodafoneOneRedeemService.getReport(request);
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, resposne);
    }
}
