/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.customer_care;

/**
 *
 * @author Mahmoud Shehab
 */
public class ProductQuotaModel {

    private String quotaName;
    private String quotaType;
    private String quotaUnit;
    private String maxQuota;
    private String remainingQuota;
    private String consumedQuota;
    private String location;

    public String getQuotaName() {
        return quotaName;
    }

    public void setQuotaName(String quotaName) {
        this.quotaName = quotaName;
    }

    public String getQuotaType() {
        return quotaType;
    }

    public void setQuotaType(String quotaType) {
        this.quotaType = quotaType;
    }

    public String getQuotaUnit() {
        return quotaUnit;
    }

    public void setQuotaUnit(String quotaUnit) {
        this.quotaUnit = quotaUnit;
    }

    public String getMaxQuota() {
        return maxQuota;
    }

    public void setMaxQuota(String maxQuota) {
        this.maxQuota = maxQuota;
    }

    public String getRemainingQuota() {
        return remainingQuota;
    }

    public void setRemainingQuota(String remainingQuota) {
        this.remainingQuota = remainingQuota;
    }

    public String getConsumedQuota() {
        return consumedQuota;
    }

    public void setConsumedQuota(String consumedQuota) {
        this.consumedQuota = consumedQuota;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
