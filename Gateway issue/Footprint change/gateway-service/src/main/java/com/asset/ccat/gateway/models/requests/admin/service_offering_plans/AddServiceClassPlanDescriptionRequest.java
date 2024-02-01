package com.asset.ccat.gateway.models.requests.admin.service_offering_plans;

import com.asset.ccat.gateway.models.requests.BaseRequest;

public class AddServiceClassPlanDescriptionRequest extends BaseRequest {

    private Integer serviceClassId;
    private String description;
    private Integer planId;

    public AddServiceClassPlanDescriptionRequest(Integer serviceClassId, String description, Integer planId) {
        this.serviceClassId = serviceClassId;
        this.description = description;
        this.planId = planId;
    }

    public AddServiceClassPlanDescriptionRequest() {
    }

    public Integer getServiceClassId() {
        return serviceClassId;
    }

    public void setServiceClassId(Integer serviceClassId) {
        this.serviceClassId = serviceClassId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    @Override
    public String toString() {
        return "AddServiceClassPlanDescriptionRequest{" +
                "classId=" + serviceClassId +
                ", classDesc='" + description + '\'' +
                ", planId='" + planId + '\'' +
                '}';
    }
}
