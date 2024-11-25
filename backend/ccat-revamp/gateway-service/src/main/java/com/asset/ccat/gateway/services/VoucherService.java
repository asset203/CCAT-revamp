package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.customer_care.voucher.*;
import com.asset.ccat.gateway.models.responses.customer_care.CheckVoucherNumberResponse;
import com.asset.ccat.gateway.models.responses.customer_care.GetPaymentGatewayVoucherResponse;
import com.asset.ccat.gateway.models.responses.customer_care.GetVoucherDetailsResponse;
import com.asset.ccat.gateway.proxy.VoucherProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    @Autowired
    private VoucherProxy voucherProxy;

    public GetVoucherDetailsResponse getVoucherDetails(GetVoucherDetailsRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Start serving Get Voucher Details request");
        CCATLogger.DEBUG_LOGGER.debug("Calling VoucherProxy- getVoucherDetails()");

        return voucherProxy.getVoucherDetails(request);
    }

    public void updateVoucherState(UpdateVoucherStateRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Start serving update voucher state request");
        CCATLogger.DEBUG_LOGGER.debug("Calling VoucherProxy- updateVoucherState()");
        voucherProxy.updateVoucherState(request);
        CCATLogger.DEBUG_LOGGER.info("Finished serving update voucher state request successfully");
    }

    public void submitVoucherBasedRefill(VoucherBasedRefillRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Start serving submit voucher based refill request");
        CCATLogger.DEBUG_LOGGER.debug("Calling VoucherProxy- submitVoucherBasedRefill()");
        voucherProxy.submitVoucherBasedRefill(request);
        CCATLogger.DEBUG_LOGGER.info("Finished serving submit voucher based refill request successfully");
    }

    public CheckVoucherNumberResponse checkVoucherNumber(CheckVoucherNumberRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Start serving check voucher number request");
        CCATLogger.DEBUG_LOGGER.debug("Calling VoucherProxy- checkVoucherNumber()");
        CheckVoucherNumberResponse resp = voucherProxy.checkVoucherNumber(request);
        CCATLogger.DEBUG_LOGGER.info("Finished serving check voucher number request successfully");
        return resp;
    }
    public GetPaymentGatewayVoucherResponse getPaymentGatewayVoucher(GetPaymentGatewayVoucherRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Start serving get payment gateway voucher request");
        CCATLogger.DEBUG_LOGGER.debug("Calling VoucherProxy- getPaymentGatewayVoucher()");
        GetPaymentGatewayVoucherResponse response = voucherProxy.getPaymentGatewayVoucher(request);
        CCATLogger.DEBUG_LOGGER.info("Finished serving get payment gateway voucher request successfully");
        return response;
    }
}
