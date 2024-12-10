package com.asset.ccat.ods_service.controllers;

import com.asset.ccat.ods_service.defines.Defines;
import com.asset.ccat.ods_service.defines.ErrorCodes;
import com.asset.ccat.ods_service.exceptions.ODSException;
import com.asset.ccat.ods_service.logger.CCATLogger;
import com.asset.ccat.ods_service.models.requests.ContractBillRequest;
import com.asset.ccat.ods_service.models.responses.BaseResponse;
import com.asset.ccat.ods_service.models.responses.DSSResponseModel;
import com.asset.ccat.ods_service.services.ContractBillService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping(value = Defines.ContextPaths.DSS_REPORT)
public class ContractBillController {

    private final ContractBillService contractBillService;
    @Autowired
    public ContractBillController(ContractBillService contractBillService) {
        this.contractBillService = contractBillService;
    }

    @PostMapping(value = Defines.ContextPaths.CONTRACT_BILL + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<DSSResponseModel> getContractBillReport(@RequestBody ContractBillRequest request) throws ODSException, SQLException {
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.debug("Start getting contract bill for MSISDN = {} -- NumberOfBills = {}, ReportType = {}", request.getMsisdn(), request.getNumOfBill(), request.getReportType());
        DSSResponseModel result = contractBillService.getReport(request);
        CCATLogger.DEBUG_LOGGER.debug("Get contract bill request ended.");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, result);
    }
}
