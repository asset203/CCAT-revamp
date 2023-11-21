package com.asset.ccat.air_service.models.requests.customer_care;

import com.asset.ccat.air_service.models.customer_care.AddUsageModel;
import com.asset.ccat.air_service.models.requests.BaseRequest;

/**
 *
 * @author nour.ihab
 */
public class AddUsageCountersRequest extends BaseRequest {

    private AddUsageModel addUsage;

    public AddUsageModel getAddUsage() {
        return addUsage;
    }

    public void setAddUsage(AddUsageModel addUsage) {
        this.addUsage = addUsage;
    }

}
