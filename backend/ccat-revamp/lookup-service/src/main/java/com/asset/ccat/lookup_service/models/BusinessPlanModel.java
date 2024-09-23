/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.models;

/**
 *
 * @author wael.mohamed
 */
public class BusinessPlanModel {

    private Integer businessPlanId;
    private Integer businessPlanCode;
    private String businessPlanName;
    private Integer serviceClassId;
    private Integer hlrProfileId;
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

    public Integer getServiceClassId() {
        return serviceClassId;
    }

    public void setServiceClassId(Integer serviceClassId) {
        this.serviceClassId = serviceClassId;
    }

    public Integer getHlrProfileId() {
        return hlrProfileId;
    }

    public void setHlrProfileId(Integer hlrProfileId) {
        this.hlrProfileId = hlrProfileId;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}
