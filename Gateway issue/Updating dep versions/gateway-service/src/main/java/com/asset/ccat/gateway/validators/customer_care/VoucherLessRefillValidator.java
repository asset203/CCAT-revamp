package com.asset.ccat.gateway.validators.customer_care;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.customer_care.voucher_refill.SubmitVoucherlessRefillRequest;
import java.util.Objects;
import org.springframework.stereotype.Component;

/**
 *
 * @author nour.ihab
 */
@Component
public class VoucherLessRefillValidator {

    public void validateSubmitVoucherlessRefill(SubmitVoucherlessRefillRequest submitVoucherlessRefillRequest) throws GatewayException {
        if (Objects.isNull(submitVoucherlessRefillRequest.getAmount())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "amount");
        } else if (Objects.isNull(submitVoucherlessRefillRequest.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "misdin");
        } else if (Objects.isNull(submitVoucherlessRefillRequest.getPaymentProfileId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "paymentProfileId");
        }
    }
}
