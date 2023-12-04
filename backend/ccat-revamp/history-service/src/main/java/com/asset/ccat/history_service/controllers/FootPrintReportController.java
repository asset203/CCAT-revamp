package com.asset.ccat.history_service.controllers;


import com.asset.ccat.history_service.defines.Defines;
import com.asset.ccat.history_service.defines.ErrorCodes;
import com.asset.ccat.history_service.exceptions.HistoryException;
import com.asset.ccat.history_service.models.requests.foot_print.GetFootPrintReportRequest;
import com.asset.ccat.history_service.models.responses.BaseResponse;
import com.asset.ccat.history_service.models.responses.foot_print.GetFootPrintReportResponse;
import com.asset.ccat.history_service.services.FootPrintReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Assem.Hassan
 */
@RestController
@RequestMapping(path = Defines.ContextPaths.ADMIN_FOOTPRINT_REPORT)
public class FootPrintReportController {


    @Autowired
    private FootPrintReportService footPrintReportService;


    @PostMapping(value = Defines.WEB_ACTIONS.GET)
    public BaseResponse<GetFootPrintReportResponse> getFootPrintReport(@RequestBody GetFootPrintReportRequest getFootPrintReportRequest) throws HistoryException {
        GetFootPrintReportResponse getFootPrintReportResponse = footPrintReportService
                .getFootPrintReport(getFootPrintReportRequest);

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success", Defines.SEVERITY.CLEAR,
                getFootPrintReportResponse);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.EXPORT)
    public BaseResponse<GetFootPrintReportResponse> exportFootPrintReport(@RequestBody GetFootPrintReportRequest getFootPrintReportRequest) throws HistoryException {
        GetFootPrintReportResponse getFootPrintReportResponse = footPrintReportService
                .getFootPrintReport(getFootPrintReportRequest);

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success", Defines.SEVERITY.CLEAR,
                getFootPrintReportResponse);
    }
}
