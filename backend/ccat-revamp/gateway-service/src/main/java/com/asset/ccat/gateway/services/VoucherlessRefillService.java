package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.models.customer_care.PaymentProfileModel;
import com.asset.ccat.gateway.models.requests.customer_care.voucher_refill.SubmitVoucherlessRefillRequest;
import com.asset.ccat.gateway.proxy.VoucherlessRefillProxy;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nour.ihab
 */
@Service
public class VoucherlessRefillService {

    @Autowired
    VoucherlessRefillProxy voucherlessRefillProxy;

    public List<PaymentProfileModel> getAllPaymentsProfiles() throws GatewayException {
        List<PaymentProfileModel> paymentProfileModel = voucherlessRefillProxy.getAllPaymentsProfiles();
        return paymentProfileModel;
    }

    public void submitVoucherlessRefill(SubmitVoucherlessRefillRequest submitVoucherlessRefillRequest) throws GatewayException {
        voucherlessRefillProxy.submitVoucherlessRefill(submitVoucherlessRefillRequest);
    }

}
