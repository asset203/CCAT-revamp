package com.asset.ccat.gateway.models.requests.customer_care.service_offering_plans;

import com.asset.ccat.gateway.models.requests.SubscriberRequest;

public class GetServiceOfferingsRequest extends SubscriberRequest {
    private Integer serviceClassId;
    private String profileSOB;

    public GetServiceOfferingsRequest() {
    }

    public GetServiceOfferingsRequest(Integer serviceClassId, String profileSOB) {
        this.serviceClassId = serviceClassId;
        this.profileSOB = profileSOB;
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
        return "GetServiceOfferingsRequest{" +
                "msisdn='" + msisdn + '\'' +
                ", serviceClassId=" + serviceClassId +
                '}';
    }
}
