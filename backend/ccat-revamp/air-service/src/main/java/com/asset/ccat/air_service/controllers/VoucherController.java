package com.asset.ccat.air_service.controllers;

import com.asset.ccat.air_service.defines.Defines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.exceptions.AIRVoucherException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.requests.customer_care.voucher.CheckVoucherNumberRequest;
import com.asset.ccat.air_service.models.requests.customer_care.voucher.GetVoucherDetailsRequest;
import com.asset.ccat.air_service.models.requests.customer_care.voucher.UpdateVoucherStateRequest;
import com.asset.ccat.air_service.models.requests.customer_care.voucher.VoucherBasedRefillRequest;
import com.asset.ccat.air_service.models.responses.BaseResponse;
import com.asset.ccat.air_service.models.responses.customer_care.voucher.CheckVoucherNumberResponse;
import com.asset.ccat.air_service.models.responses.customer_care.voucher.GetVoucherDetailsResponse;
import com.asset.ccat.air_service.services.VoucherService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Defines.ContextPaths.VOUCHER)
public class VoucherController {

    @Autowired
    private VoucherService voucherService;


    @PostMapping(value = Defines.WEB_ACTIONS.GET)
    public BaseResponse<GetVoucherDetailsResponse> getVoucherDetails(@RequestBody GetVoucherDetailsRequest request) throws AIRException, AIRServiceException, AIRVoucherException {
        CCATLogger.DEBUG_LOGGER.info("Received Get Voucher Details Request [" + request + "]");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        GetVoucherDetailsResponse voucherDetailsResponse = voucherService.getVoucherDetails(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Voucher Details Request Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                voucherDetailsResponse);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateVoucherState(@RequestBody UpdateVoucherStateRequest request) throws AIRException, AIRServiceException, AIRVoucherException {

        CCATLogger.DEBUG_LOGGER.info("Received Update Voucher State Request [" + request + "]");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        voucherService.updateVoucherState(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Voucher State Request Successfully");

        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                null);
    }

    @PostMapping(value = Defines.ContextPaths.VOUCHER_BASED_REFILL + Defines.WEB_ACTIONS.SUBMIT)
    public BaseResponse submitVoucherBasedRefill(@RequestBody VoucherBasedRefillRequest request) throws AIRException, AIRServiceException {

        CCATLogger.DEBUG_LOGGER.info("Received Submit Voucher Based Refill Request [" + request + "]");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        voucherService.submitVoucherBasedRefill(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Submit Voucher Based Refill Request Successfully");

        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.CHECK)
    public BaseResponse<CheckVoucherNumberResponse> checkVoucherNumber(@RequestBody CheckVoucherNumberRequest request) throws AIRException, AIRServiceException, AIRVoucherException {
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Check Voucher Number Request [" + request + "]");
        CheckVoucherNumberResponse response = voucherService.checkVoucherNumber(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Check Voucher Number Request Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                response);
    }
}
