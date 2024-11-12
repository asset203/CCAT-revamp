package com.asset.ccat.air_service.models.requests.customer_care;

import com.asset.ccat.air_service.models.requests.BaseRequest;

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
