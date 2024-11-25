package com.asset.ccat.air_service.models.requests.customer_care;

import com.asset.ccat.air_service.models.requests.SubscriberRequest;

public class GetCurrentServiceOfferingPlan extends SubscriberRequest {

    private Integer serviceClassId;
    private String profileSOB;

    public GetCurrentServiceOfferingPlan(Integer serviceClassId, String profileSOB) {
        this.serviceClassId = serviceClassId;
        this.profileSOB = profileSOB;
    }

    public GetCurrentServiceOfferingPlan(String msisdn, Integer serviceClassId, String profileSOB) {
        super(msisdn);
        this.serviceClassId = serviceClassId;
        this.profileSOB = profileSOB;
    }

    public GetCurrentServiceOfferingPlan() {
    }

    public Integer getServiceClassId() {
        return serviceClassId;
    }

    public void setServiceClassId(Integer serviceClassId) {
        this.serviceClassId = serviceClassId;
    }

    public String getProfileSOB() {
        return profileSOB;
    }

    public void setProfileSOB(String profileSOB) {
        this.profileSOB = profileSOB;
    }

    @Override
    public String toString() {
        return "GetCurrentServiceOfferingPlan{" +
                "serviceClassId=" + serviceClassId +
                '}';
    }
}
