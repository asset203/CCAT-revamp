package com.asset.ccat.gateway.models.users;

/**
 * @author nour.ihab
 */
public class BusinessPlanModel {

    private Integer businessPlanId;
    private Integer businessPlanCode;
    private String businessPlanName;
    private Integer hlrProfileId;
    private Integer ServiceClassId;
    private Integer isDeleted;

    public Integer getBusinessPlanId() {
        return businessPlanId;
    }

    public void setBusinessPlanId(Integer businessPlanId) {
        this.businessPlanId = businessPlanId;
    }

    public Integer getBusinessPlanCode() {
        return businessPlanCode;
    }

    public void setBusinessPlanCode(Integer businessPlanCode) {
        this.businessPlanCode = businessPlanCode;
    }

    public String getBusinessPlanName() {
        return businessPlanName;
    }

    public void setBusinessPlanName(String businessPlanName) {
        this.businessPlanName = businessPlanName;
    }

    public Integer getHlrProfileId() {
        return hlrProfileId;
    }

    public void setHlrProfileId(Integer hlrProfileId) {
        this.hlrProfileId = hlrProfileId;
    }

    public Integer getServiceClassId() {
        return ServiceClassId;
    }

    public void setServiceClassId(Integer ServiceClassId) {
        this.ServiceClassId = ServiceClassId;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "BusinessPlanModel{" +
                "businessPlanId=" + businessPlanId +
                ", businessPlanCode=" + businessPlanCode +
                ", businessPlanName='" + businessPlanName + '\'' +
                ", hlrProfileId=" + hlrProfileId +
                ", ServiceClassId=" + ServiceClassId +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
