package com.asset.ccat.gateway.models.responses.customer_care;

import com.asset.ccat.gateway.models.customer_care.UsageCountersModel;
import java.util.List;

/**
 *
 * @author nour.ihab
 */
public class GetAllUsageCountersResponse {

    private List<UsageCountersModel> UsageCountersList;

    public List<UsageCountersModel> getUsageCountersList() {
        return UsageCountersList;
    }

    public void setUsageCountersList(List<UsageCountersModel> UsageCountersList) {
        this.UsageCountersList = UsageCountersList;
    }

}
