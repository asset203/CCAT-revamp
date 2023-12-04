/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Mahmoud Shehab
 */
public class OfferModel implements Serializable {

    private Integer offerId;
    private String offerDesc;
    /*
     * Offer type will be one of the below values
     * 0 --> Account Offer
     * 1 --> Multi User Identification Offer
     * 2 --> Timer
     */
    private Integer offerTypeId;
    private String offerType;
    private Date startDate;
    private Date expiryDate;

    /*
     * Offer state will be one of the below values
     * 0 --> Enabled offer state
     * 1 --> Disabled offer state
     */
    private Integer offerStateId;
    private String offerState;

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

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getOfferStateId() {
        return offerStateId;
    }

    public void setOfferStateId(Integer offerStateId) {
        this.offerStateId = offerStateId;
    }

    public String getOfferState() {
        return offerState;
    }

    public void setOfferState(String offerState) {
        this.offerState = offerState;
    }

    @Override
    public String toString() {
        return "OfferModel{" +
                "offerId=" + offerId +
                ", offerDesc='" + offerDesc + '\'' +
                ", offerTypeId=" + offerTypeId +
                ", offerType='" + offerType + '\'' +
                ", startDate=" + startDate +
                ", expiryDate=" + expiryDate +
                ", offerStateId=" + offerStateId +
                ", offerState='" + offerState + '\'' +
                '}';
    }
}
