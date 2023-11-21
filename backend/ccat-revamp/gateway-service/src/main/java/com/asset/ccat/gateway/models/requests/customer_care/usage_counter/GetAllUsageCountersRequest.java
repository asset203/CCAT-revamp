package com.asset.ccat.gateway.models.requests.customer_care.usage_counter;

import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author nour.ihab
 */
public class GetAllUsageCountersRequest extends BaseRequest {

    private String msisdn;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    @Override
    public String toString() {
        return "GetAllUsageCountersRequest{" +
                "msisdn='" + msisdn + '\'' +
                '}';
    }
}
