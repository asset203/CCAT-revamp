package com.asset.ccat.gateway.models.requests.customer_care.usage_counter;


import com.asset.ccat.gateway.models.requests.BaseRequest;

import java.util.List;

/**
 * @author marwa.elshawarby
 */
public class DeleteUsageThresholdsRequest extends BaseRequest {

    private String msisdn;
    private List<Integer> usageThresholdsIds;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public List<Integer> getUsageThresholdsIds() {
        return usageThresholdsIds;
    }

    public void setUsageThresholdsIds(List<Integer> usageThresholdsIds) {
        this.usageThresholdsIds = usageThresholdsIds;
    }

    @Override
    public String toString() {
        return "DeleteUsageThresholdsRequest{" +
                "msisdn='" + msisdn + '\'' +
                ", usageThresholdsIds=" + usageThresholdsIds +
                '}';
    }
}
