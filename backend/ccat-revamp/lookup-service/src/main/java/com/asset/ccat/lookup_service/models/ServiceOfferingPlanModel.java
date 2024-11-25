package com.asset.ccat.lookup_service.models;

import java.util.List;

public class ServiceOfferingPlanModel {

    private Integer planId;

    private String planName;


    private List<SOServiceClassDescriptionModel> soServiceClassDescriptions;

    private List<ServiceOfferingPlanBitModel> serviceOfferingPlanBits;


    public ServiceOfferingPlanModel() {
    }

    public ServiceOfferingPlanModel(Integer planId, String planName, List<SOServiceClassDescriptionModel> soServiceClassDescriptions, List<ServiceOfferingPlanBitModel> serviceOfferingPlanBits) {
        this.planId = planId;
        this.planName = planName;
        this.soServiceClassDescriptions = soServiceClassDescriptions;
        this.serviceOfferingPlanBits = serviceOfferingPlanBits;
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

    public List<SOServiceClassDescriptionModel> getSoServiceClassDescriptions() {
        return soServiceClassDescriptions;
    }

    public void setSoServiceClassDescriptions(List<SOServiceClassDescriptionModel> soServiceClassDescriptions) {
        this.soServiceClassDescriptions = soServiceClassDescriptions;
    }

    public List<ServiceOfferingPlanBitModel> getServiceOfferingPlanBits() {
        return serviceOfferingPlanBits;
    }

    public void setServiceOfferingPlanBits(List<ServiceOfferingPlanBitModel> serviceOfferingPlanBits) {
        this.serviceOfferingPlanBits = serviceOfferingPlanBits;
    }

    @Override
    public String toString() {
        return "ServiceOfferingPlanModel{" +
                "planId=" + planId +
                ", planName='" + planName + '\'' +
                ", soServiceClassDescriptions=" + soServiceClassDescriptions +
                ", serviceOfferingPlanBits=" + serviceOfferingPlanBits +
                '}';
    }
}

