/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.requests.customer_care.advanced;

import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 *
 * @author Mahmoud Shehab
 */
public class InstallSubscriberRequest extends BaseRequest {

    private String subscriberMsisdn;
    private String organization;
    private Integer languageId;
    private Integer accountGroupId;
    private Integer serviceOfferingId;
    private Boolean temporeryBlocked;
    private Integer serviceClassId;
    private Integer businessPlanId;


    public String getSubscriberMsisdn() {
        return subscriberMsisdn;
    }

    public void setSubscriberMsisdn(String subscriberMsisdn) {
        this.subscriberMsisdn = subscriberMsisdn;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Boolean getTemporeryBlocked() {
        return temporeryBlocked;
    }

    public void setTemporeryBlocked(Boolean temporeryBlocked) {
        this.temporeryBlocked = temporeryBlocked;
    }

    public Integer getAccountGroupId() {
        return accountGroupId;
    }

    public void setAccountGroupId(Integer accountGroupId) {
        this.accountGroupId = accountGroupId;
    }

    public Integer getServiceOfferingId() {
        return serviceOfferingId;
    }

    public void setServiceOfferingId(Integer serviceOfferingId) {
        this.serviceOfferingId = serviceOfferingId;
    }

    public Integer getBusinessPlanId() {
        return businessPlanId;
    }

    public void setBusinessPlanId(Integer businessPlanId) {
        this.businessPlanId = businessPlanId;
    }

    public Integer getServiceClassId() {
        return serviceClassId;
    }

    public void setServiceClassId(Integer serviceClassId) {
        this.serviceClassId = serviceClassId;
    }

    @Override
    public String toString() {
        return "InstallSubscriberRequest{" +
                "subscriberMsisdn='" + subscriberMsisdn + '\'' +
                ", organization='" + organization + '\'' +
                ", languageId=" + languageId +
                ", accountGroupId=" + accountGroupId +
                ", serviceOfferingId=" + serviceOfferingId +
                ", temporeryBlocked=" + temporeryBlocked +
                ", serviceClassId=" + serviceClassId +
                ", businessPlanId=" + businessPlanId +
                '}';
    }
}
