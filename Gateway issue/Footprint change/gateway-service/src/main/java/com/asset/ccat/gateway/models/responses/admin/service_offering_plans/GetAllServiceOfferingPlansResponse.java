package com.asset.ccat.gateway.models.responses.admin.service_offering_plans;

import com.asset.ccat.gateway.models.admin.ServiceOfferingPlanNameModel;

import java.util.List;

public class GetAllServiceOfferingPlansResponse {


    private List<ServiceOfferingPlanNameModel> serviceOfferingPlans;

    public GetAllServiceOfferingPlansResponse() {
    }

    public GetAllServiceOfferingPlansResponse(List<ServiceOfferingPlanNameModel> serviceOfferingPlans) {
        this.serviceOfferingPlans = serviceOfferingPlans;
    }

    public List<ServiceOfferingPlanNameModel> getServiceOfferingPlans() {
        return serviceOfferingPlans;
    }

    public void setServiceOfferingPlans(List<ServiceOfferingPlanNameModel> serviceOfferingPlans) {
        this.serviceOfferingPlans = serviceOfferingPlans;
    }

    @Override
    public String toString() {
        return "GetAllServiceOfferingPlansResponse{" +
                "serviceOfferingPlans=" + serviceOfferingPlans +
                '}';
    }
}
