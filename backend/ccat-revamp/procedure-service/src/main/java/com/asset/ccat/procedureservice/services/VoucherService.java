package com.asset.ccat.procedureservice.services;

import com.asset.ccat.procedureservice.database.dao.VoucherDao;
import com.asset.ccat.procedureservice.dto.models.PaymentGatewayVoucherModel;
import com.asset.ccat.procedureservice.dto.requests.payment_gateway_voucher.PaymentGatewayVoucherRequest;
import com.asset.ccat.procedureservice.dto.responses.payment_gateway_voucher.GetPaymentGatewayVoucherResponse;
import com.asset.ccat.procedureservice.exceptions.ProcedureException;
import com.asset.ccat.procedureservice.logger.CCATLogger;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    private final VoucherDao voucherDAO;
    public VoucherService(VoucherDao voucherDAO) {
        this.voucherDAO = voucherDAO;}

    public GetPaymentGatewayVoucherResponse getPaymentGatewayVoucher(PaymentGatewayVoucherRequest request) throws ProcedureException {
        CCATLogger.DEBUG_LOGGER.debug("VoucherService -> getPaymentGatewayVoucher() : Started");
        PaymentGatewayVoucherModel paymentGatewayVoucherModel = voucherDAO.getPaymentGatewayVoucher(request.getVoucherSerialNumber());
        GetPaymentGatewayVoucherResponse response = new GetPaymentGatewayVoucherResponse();
        response.setMsisdn(paymentGatewayVoucherModel.getMsisdn());
        response.setBillingNumber(paymentGatewayVoucherModel.getBillingNumber());
        CCATLogger.DEBUG_LOGGER.debug("VoucherService -> getPaymentGatewayVoucher() : Ended Successfully");
        return response;
    }
}
