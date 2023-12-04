/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author Mahmoud Shehab
 */
public class OfferModel implements Serializable {

    private Integer offerId;
    private String offerDesc;
    private Boolean isDropDownEnabled;
    /*
     * Offer type will be one of the below values
     * 0 --> Account Offer
     * 1 --> Multi User Identification Offer
     * 2 --> Timer
     */
    private Integer TypeId;
    private String TypeDesc;

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }

    public String getOfferDesc() {
        return offerDesc;
    }

    public void setOfferDesc(String offerDesc) {
        this.offerDesc = offerDesc;
    }

    public Boolean getIsDropDownEnabled() {
        return isDropDownEnabled;
    }

    public void setIsDropDownEnabled(Boolean isDropDownEnabled) {
        this.isDropDownEnabled = isDropDownEnabled;
    }

    @JsonProperty("offerTypeId")
    public Integer getTypeId() {
        return TypeId;
    }

    public void setTypeId(Integer TypeId) {
        this.TypeId = TypeId;
    }

    @JsonProperty("offerType")
    public String getTypeDesc() {
        return TypeDesc;
    }

    public void setTypeDesc(String TypeDesc) {
        this.TypeDesc = TypeDesc;
    }

}
