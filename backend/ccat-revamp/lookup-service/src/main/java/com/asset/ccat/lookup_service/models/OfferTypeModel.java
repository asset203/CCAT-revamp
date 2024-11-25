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
public class OfferTypeModel {

    /*
     * Offer type will be one of the below values
     * 0 --> Account Offer
     * 1 --> Multi User Identification Offer
     * 2 --> Timer
     */
    private Integer TypeId;
    private String TypeDesc;

    public Integer getTypeId() {
        return TypeId;
    }

    public void setTypeId(Integer TypeId) {
        this.TypeId = TypeId;
    }

    public String getTypeDesc() {
        return TypeDesc;
    }

    public void setTypeDesc(String TypeDesc) {
        this.TypeDesc = TypeDesc;
    }

}
