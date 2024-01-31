package com.asset.ccat.gateway.models.responses.customer_care;

import com.asset.ccat.gateway.models.customer_care.PaymentProfileModel;
import java.util.List;

/**
 *
 * @author nour.ihab
 */
public class GetAllPaymentProfilesResponse {

    List<PaymentProfileModel> paymentProfiles;

    public List<PaymentProfileModel> getPaymentProfiles() {
        return paymentProfiles;
    }

    public void setPaymentProfiles(List<PaymentProfileModel> paymentProfiles) {
        this.paymentProfiles = paymentProfiles;
    }

}
