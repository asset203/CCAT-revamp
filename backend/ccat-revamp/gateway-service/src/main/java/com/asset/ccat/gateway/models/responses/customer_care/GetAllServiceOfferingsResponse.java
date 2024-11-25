package com.asset.ccat.gateway.models.responses.customer_care;

import com.asset.ccat.gateway.models.customer_care.ServiceOfferingPlanModel;

import java.util.List;

public class GetAllServiceOfferingsResponse {

    List<ServiceOfferingPlanModel> serviceOfferings;

    public GetAllServiceOfferingsResponse() {
    }

    public GetAllServiceOfferingsResponse(List<ServiceOfferingPlanModel> serviceOfferings) {
        this.serviceOfferings = serviceOfferings;
    }

    public List<ServiceOfferingPlanModel> getServiceOfferings() {
        return serviceOfferings;
    }

    public void setServiceOfferings(List<ServiceOfferingPlanModel> serviceOfferings) {
        this.serviceOfferings = serviceOfferings;
    }
}
