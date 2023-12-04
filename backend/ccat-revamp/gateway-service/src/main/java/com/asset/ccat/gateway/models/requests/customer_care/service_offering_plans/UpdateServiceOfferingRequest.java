package com.asset.ccat.gateway.models.requests.customer_care.service_offering_plans;

import com.asset.ccat.gateway.models.customer_care.ServiceOfferingPlanModel;
import com.asset.ccat.gateway.models.requests.SubscriberRequest;

public class UpdateServiceOfferingRequest extends SubscriberRequest {

    private ServiceOfferingPlanModel currentServiceOffering;
    private ServiceOfferingPlanModel newServiceOffering;

    public UpdateServiceOfferingRequest() {
    }

    public UpdateServiceOfferingRequest(ServiceOfferingPlanModel currentServiceOffering, ServiceOfferingPlanModel newServiceOffering) {
        this.currentServiceOffering = currentServiceOffering;
        this.newServiceOffering = newServiceOffering;
    }

    public ServiceOfferingPlanModel getCurrentServiceOffering() {
        return currentServiceOffering;
    }

    public void setCurrentServiceOffering(ServiceOfferingPlanModel currentServiceOffering) {
        this.currentServiceOffering = currentServiceOffering;
    }

    public ServiceOfferingPlanModel getNewServiceOffering() {
        return newServiceOffering;
    }

    public void setNewServiceOffering(ServiceOfferingPlanModel newServiceOffering) {
        this.newServiceOffering = newServiceOffering;
    }

    @Override
    public String toString() {
        return "UpdateServiceOfferingRequest{" +
                "currentServiceOffering=" + currentServiceOffering +
                ", newServiceOffering=" + newServiceOffering +
                ", msisdn='" + msisdn + '\'' +
                '}';
    }
}
