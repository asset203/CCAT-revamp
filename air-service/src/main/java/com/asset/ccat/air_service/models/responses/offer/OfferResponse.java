/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.models.responses.offer;

import java.io.Serializable;

/**
 *
 * @author Mahmoud Shehab
 */
public class OfferResponse implements Serializable {

    private Integer offerId;
    private String offerDesc;
    private Integer offerTypeId;
    private String offerType;

    /*
     * Offer state will be one of the below values
     * 0 --> Enabled offer state
     * 1 --> Disabled offer state
     */
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
