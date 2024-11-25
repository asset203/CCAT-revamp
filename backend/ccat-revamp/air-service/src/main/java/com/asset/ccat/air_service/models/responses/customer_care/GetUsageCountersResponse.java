package com.asset.ccat.air_service.models.responses.customer_care;

import com.asset.ccat.air_service.models.customer_care.UsageCountersModel;

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
