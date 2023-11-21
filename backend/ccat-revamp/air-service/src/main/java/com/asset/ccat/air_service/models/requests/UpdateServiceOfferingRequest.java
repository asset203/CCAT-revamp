package com.asset.ccat.air_service.models.requests;

import com.asset.ccat.air_service.models.customer_care.service_offering_details_models.ServiceOfferingPlanModel;

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
}
