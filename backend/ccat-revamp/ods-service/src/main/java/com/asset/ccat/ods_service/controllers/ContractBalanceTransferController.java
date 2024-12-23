package com.asset.ccat.ods_service.controllers;

import com.asset.ccat.ods_service.defines.Defines;
import com.asset.ccat.ods_service.defines.ErrorCodes;
import com.asset.ccat.ods_service.exceptions.ODSException;
import com.asset.ccat.ods_service.logger.CCATLogger;
import com.asset.ccat.ods_service.models.requests.DSSReportRequest;
import com.asset.ccat.ods_service.models.responses.BaseResponse;
import com.asset.ccat.ods_service.models.responses.DSSResponseModel;
import com.asset.ccat.ods_service.services.ContractBalanceTransferService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping(value = Defines.ContextPaths.DSS_REPORT)
public class ContractBalanceTransferController {
    private final ContractBalanceTransferService contractBalanceTransferService;

    public ContractBalanceTransferController(ContractBalanceTransferService contractBalanceTransferService) {
        this.contractBalanceTransferService = contractBalanceTransferService;
    }

    @PostMapping(value = Defines.ContextPaths.CONTRACT_BALANCE_TRANSFER + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<DSSResponseModel> getContractBillReport(@RequestBody DSSReportRequest request) throws ODSException, SQLException {
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.debug("Start getting Contract Balance for MSISDN = {} -- DateFrom = {}, DateTo = {}", request.getMsisdn(), request.getDateFrom(), request.getDateTo());
        DSSResponseModel result = contractBalanceTransferService.getReport(request);
        CCATLogger.DEBUG_LOGGER.debug("Get Contract Balance request ended.");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, result);
    }
}
