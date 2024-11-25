package com.asset.ccat.air_service.models.responses.customer_care;

import com.asset.ccat.air_service.models.customer_care.UsageCountersModel;
import java.util.List;

/**
 *
 * @author nour.ihab
 */
public class GetAllUsageCountersResponse {

    private List<UsageCountersModel> UsageCountersList;

    public GetAllUsageCountersResponse(List<UsageCountersModel> UsageCountersList) {
        this.UsageCountersList = UsageCountersList;
    }

    public List<UsageCountersModel> getUsageCountersList() {
        return UsageCountersList;
    }

    public void setUsageCountersList(List<UsageCountersModel> UsageCountersList) {
        this.UsageCountersList = UsageCountersList;
    }

}
