package com.asset.ccat.lookup_service.models.requests.business_plan;

import com.asset.ccat.lookup_service.models.requests.BaseRequest;

/**
 * @author Assem.Hassan
 */
public class DeleteBusinessPlanRequest extends BaseRequest {

    private Integer businessPlanId;


    public Integer getBusinessPlanId() {
        return businessPlanId;
    }

    public void setBusinessPlanId(Integer businessPlanId) {
        this.businessPlanId = businessPlanId;
    }
}
