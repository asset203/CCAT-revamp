/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;

/**
 * @author Mahmoud Shehab
 */
public class SubscriberAccountModel {

    @JsonIgnore
    private String Id;
    private String subscriberNumber;
    private String currentPlan;
    private Float balance;
    private String currency;
    private ServiceClassModel serviceClass;
    private String status;
    private String barringStatus;
    private String language;
    private Integer languageId;
    private Date activationDate;
    private Date supervisionFeePeriodExpiryDate;
    private String supervisionFeePeriod;
    private Date serviceFeePeriodExpiryDate;
    private String serviceFeePeriod;
    private String creditClearance;
    private String serviceRemoval;
    private Date refillBarredUntil;
    private Date negativeBalanceBarDate;
    private String maxServicePeriodExpiry;
    private String maxSupervesionPeriodExpiry;
    private String originalServiceClass;
    private String promotionPlan;
    private Boolean isNegativeBalBarring;
    private List<PamInformationModel> pams;
    private Boolean isTempBlocked;
    //    private String accountGroupName;
    private Integer accountGroupNumber;

    private String maxSupervisionPeriod;
    private String maxServiceFeePeriod;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getSubscriberNumber() {
        return subscriberNumber;
    }

    public void setSubscriberNumber(String subscriberNumber) {
        this.subscriberNumber = subscriberNumber;
    }

    public String getCurrentPlan() {
        return currentPlan;
    }

    public void setCurrentPlan(String currentPlan) {
        this.currentPlan = currentPlan;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public ServiceClassModel getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(ServiceClassModel serviceClass) {
        this.serviceClass = serviceClass;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBarringStatus() {
        return barringStatus;
    }

    public void setBarringStatus(String barringStatus) {
        this.barringStatus = barringStatus;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    public Date getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate;
    }

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    public Date getSupervisionFeePeriodExpiryDate() {
        return supervisionFeePeriodExpiryDate;
    }

    public void setSupervisionFeePeriodExpiryDate(Date supervisionFeePeriodExpiryDate) {
        this.supervisionFeePeriodExpiryDate = supervisionFeePeriodExpiryDate;
    }

    public String getSupervisionFeePeriod() {
        return supervisionFeePeriod;
    }

    public void setSupervisionFeePeriod(String supervisionFeePeriod) {
        this.supervisionFeePeriod = supervisionFeePeriod;
    }

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    public Date getServiceFeePeriodExpiryDate() {
        return serviceFeePeriodExpiryDate;
    }

    public void setServiceFeePeriodExpiryDate(Date serviceFeePeriodExpiryDate) {
        this.serviceFeePeriodExpiryDate = serviceFeePeriodExpiryDate;
    }

    public String getServiceFeePeriod() {
        return serviceFeePeriod;
    }

    public void setServiceFeePeriod(String serviceFeePeriod) {
        this.serviceFeePeriod = serviceFeePeriod;
    }

    public String getCreditClearance() {
        return creditClearance;
    }

    public void setCreditClearance(String creditClearance) {
        this.creditClearance = creditClearance;
    }

    public String getServiceRemoval() {
        return serviceRemoval;
    }

    public void setServiceRemoval(String serviceRemoval) {
        this.serviceRemoval = serviceRemoval;
    }

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    public Date getRefillBarredUntil() {
        return refillBarredUntil;
    }

    public void setRefillBarredUntil(Date refillBarredUntil) {
        this.refillBarredUntil = refillBarredUntil;
    }

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    public Date getNegativeBalanceBarDate() {
        return negativeBalanceBarDate;
    }

    public void setNegativeBalanceBarDate(Date negativeBalanceBarDate) {
        this.negativeBalanceBarDate = negativeBalanceBarDate;
    }

    public String getMaxServicePeriodExpiry() {
        return maxServicePeriodExpiry;
    }

    public void setMaxServicePeriodExpiry(String maxServicePeriodExpiry) {
        this.maxServicePeriodExpiry = maxServicePeriodExpiry;
    }

    public String getMaxSupervesionPeriodExpiry() {
        return maxSupervesionPeriodExpiry;
    }

    public void setMaxSupervesionPeriodExpiry(String maxSupervesionPeriodExpiry) {
        this.maxSupervesionPeriodExpiry = maxSupervesionPeriodExpiry;
    }

    public String getOriginalServiceClass() {
        return originalServiceClass;
    }

    public void setOriginalServiceClass(String originalServiceClass) {
        this.originalServiceClass = originalServiceClass;
    }

    public String getPromotionPlan() {
        return promotionPlan;
    }

    public void setPromotionPlan(String promotionPlan) {
        this.promotionPlan = promotionPlan;
    }

    public Boolean getIsNegativeBalBarring() {
        return isNegativeBalBarring;
    }

    public void setIsNegativeBalBarring(Boolean isNegativeBalBarring) {
        this.isNegativeBalBarring = isNegativeBalBarring;
    }

    public List<PamInformationModel> getPams() {
        return pams;
    }

    public void setPams(List<PamInformationModel> pams) {
        this.pams = pams;
    }

    public Boolean getIsTempBlocked() {
        return isTempBlocked;
    }

    public void setIsTempBlocked(Boolean tempBlocked) {
        isTempBlocked = tempBlocked;
    }

//    public String getAccountGroupName() {
//        return accountGroupName;
//    }
//
//    public void setAccountGroupName(String accountGroupName) {
//        this.accountGroupName = accountGroupName;
//    }

    public Integer getAccountGroupNumber() {
        return accountGroupNumber;
    }

    public void setAccountGroupNumber(Integer accountGroupNumber) {
        this.accountGroupNumber = accountGroupNumber;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public String getMaxSupervisionPeriod() {
        return maxSupervisionPeriod;
    }

    public void setMaxSupervisionPeriod(String maxSupervisionPeriod) {
        this.maxSupervisionPeriod = maxSupervisionPeriod;
    }

    public String getMaxServiceFeePeriod() {
        return maxServiceFeePeriod;
    }

    public void setMaxServiceFeePeriod(String maxServiceFeePeriod) {
        this.maxServiceFeePeriod = maxServiceFeePeriod;
    }

    @Override
    public String toString() {
        return "SubscriberAccountModel{" +
                "Id='" + Id + '\'' +
                ", subscriberNumber='" + subscriberNumber + '\'' +
                ", currentPlan='" + currentPlan + '\'' +
                ", balance=" + balance +
                ", currency='" + currency + '\'' +
                ", serviceClass=" + serviceClass +
                ", status='" + status + '\'' +
                ", barringStatus='" + barringStatus + '\'' +
                ", language='" + language + '\'' +
                ", languageId=" + languageId +
                ", activationDate=" + activationDate +
                '}';
    }
}
