package com.asset.ccat.lookup_service.models.requests.service_offering_plans;


import com.asset.ccat.lookup_service.models.requests.BaseRequest;

public class UpdateServiceClassPlanDescriptionRequest extends BaseRequest {


    private Integer serviceClassId;

    private Integer planId;

    private String description;

    public UpdateServiceClassPlanDescriptionRequest(Integer classId, Integer planId, String description) {
        this.serviceClassId = classId;
        this.planId = planId;
        this.description = description;
    }

    public UpdateServiceClassPlanDescriptionRequest() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getServiceClassId() {
        return serviceClassId;
    }

    public void setClassId(Integer classId) {
        this.serviceClassId = classId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    @Override
    public String toString() {
        return "UpdateServiceClassPlanDescriptionRequest{" +
                "classId=" + serviceClassId +
                ", planId=" + planId +
                ", classDesc='" + description + '\'' +
                '}';
    }
}

