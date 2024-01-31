package com.asset.ccat.gateway.models.responses.admin.service_offering_plans;

import com.asset.ccat.gateway.models.admin.ServiceOfferingPlanBitModel;

import java.util.List;

public class GetAllServiceOfferingPlanBitsResponse {


    private Integer planId;

    private String planName;

    private List<ServiceOfferingPlanBitModel> planBits;

    public GetAllServiceOfferingPlanBitsResponse() {
    }

    public GetAllServiceOfferingPlanBitsResponse(Integer planId, String planName, List<ServiceOfferingPlanBitModel> planBits) {
        this.planId = planId;
        this.planName = planName;
        this.planBits = planBits;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public List<ServiceOfferingPlanBitModel> getPlanBits() {
        return planBits;
    }

    public void setPlanBits(List<ServiceOfferingPlanBitModel> planBits) {
        this.planBits = planBits;
    }

    @Override
    public String toString() {
        return "GetServiceOfferingPlanBitsResponse{" +
                "planId=" + planId +
                ", planName='" + planName + '\'' +
                ", planBits=" + planBits +
                '}';
    }
}
