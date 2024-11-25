/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.models.requests.business_plan;

import com.asset.ccat.lookup_service.models.BusinessPlanModel;
import com.asset.ccat.lookup_service.models.requests.BaseRequest;

/**
 *
 * @author wael.mohamed
 */
public class UpdateBusinessPlanRequest extends BaseRequest {

    private BusinessPlanModel businessPlan;

    public BusinessPlanModel getBusinessPlan() {
        return businessPlan;
    }

    public void setBusinessPlan(BusinessPlanModel businessPlan) {
        this.businessPlan = businessPlan;
    }

}
