package com.asset.ccat.gateway.models.responses.customer_care;

import com.asset.ccat.gateway.models.customer_care.UsageCountersModel;

/**
 *
 * @author nour.ihab
 */
public class GetUsageCountersResponse {

    private UsageCountersModel usageCounters;

    public UsageCountersModel getUsageCounters() {
        return usageCounters;
    }

    public void setUsageCounters(UsageCountersModel usageCounters) {
        this.usageCounters = usageCounters;
    }

}
