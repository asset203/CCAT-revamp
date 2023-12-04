package com.asset.ccat.procedureservice.controllers;

import com.asset.ccat.procedureservice.defines.Defines;
import com.asset.ccat.procedureservice.defines.ErrorCodes;
import com.asset.ccat.procedureservice.dto.responses.payment_gateway_voucher.GetPaymentGatewayVoucherResponse;
import com.asset.ccat.procedureservice.exceptions.ProcedureException;
import com.asset.ccat.procedureservice.logger.CCATLogger;
import com.asset.ccat.procedureservice.dto.requests.payment_gateway_voucher.PaymentGatewayVoucherRequest;
import com.asset.ccat.procedureservice.dto.responses.BaseResponse;
import com.asset.ccat.procedureservice.services.VoucherService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(Defines.ContextPaths.VOUCHER)
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping(value = Defines.ContextPaths.PAYMENT_GATEWAY_VOUCHER+Defines.WEB_ACTIONS.GET)
    public BaseResponse<GetPaymentGatewayVoucherResponse> getPaymentGatewayVoucher(@RequestBody PaymentGatewayVoucherRequest request) throws ProcedureException {
        CCATLogger.DEBUG_LOGGER.debug("VoucherController -> getPaymentGatewayVoucher() : Started");
        CCATLogger.DEBUG_LOGGER.debug("VoucherController -> getPaymentGatewayVoucher() Request : { "+request+" }");

        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());

        GetPaymentGatewayVoucherResponse getPaymentGatewayVoucherResponse = voucherService.getPaymentGatewayVoucher(request);
        BaseResponse<GetPaymentGatewayVoucherResponse> response = new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,"success" , Defines.SEVERITY.CLEAR ,request.getRequestId(),getPaymentGatewayVoucherResponse);
        CCATLogger.DEBUG_LOGGER.debug("VoucherController -> getPaymentGatewayVoucher() : Ended Successfully");

        return response;
    }
}
