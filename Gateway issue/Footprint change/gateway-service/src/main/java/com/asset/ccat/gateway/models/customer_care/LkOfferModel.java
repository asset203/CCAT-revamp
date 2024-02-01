/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.customer_care;

import java.io.Serializable;

/**
 * @author wael.mohamed
 */
public class LkOfferModel implements Serializable {

    private Integer offerId;
    private String offerDesc;
    private Boolean isDropDownEnabled;
    /*
     * Offer type will be one of the below values
     * 0 --> Account Offer
     * 1 --> Multi User Identification Offer
     * 2 --> Timer
     */
    private Integer offerTypeId;
    private String offerType;

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

    public Integer getOfferTypeId() {
        return offerTypeId;
    }

    public void setOfferTypeId(Integer offerTypeId) {
        this.offerTypeId = offerTypeId;
    }

    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

}
