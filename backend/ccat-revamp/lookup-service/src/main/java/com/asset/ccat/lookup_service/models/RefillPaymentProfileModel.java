/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author wael.mohamed
 */
public class RefillPaymentProfileModel {

    
    private String refillProfileId;
    private String refillProfileName;
    private Float amountFrom;
    private Float amountTo;

    @JsonProperty("profileId")
    public String getRefillProfileId() {
        return refillProfileId;
    }

    public void setRefillProfileId(String refillProfileId) {
        this.refillProfileId = refillProfileId;
    }

    @JsonProperty("profileName")
    public String getRefillProfileName() {
        return refillProfileName;
    }

    public void setRefillProfileName(String refillProfileName) {
        this.refillProfileName = refillProfileName;
    }

    public Float getAmountFrom() {
        return amountFrom;
    }

    public void setAmountFrom(Float amountFrom) {
        this.amountFrom = amountFrom;
    }

    public Float getAmountTo() {
        return amountTo;
    }

    public void setAmountTo(Float amountTo) {
        this.amountTo = amountTo;
    }

}
