/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.models.requests.offer;

import com.asset.ccat.air_service.models.requests.BaseRequest;

/**
 *
 * @author wael.mohamed
 */
public class OfferRequest extends BaseRequest {

    private String msisdn;
    private OfferModelRequest offer;

    public OfferRequest() {
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public OfferModelRequest getOffer() {
        return offer;
    }

    public void setOffer(OfferModelRequest offer) {
        this.offer = offer;
    }

    @Override
    public String toString() {
        return "OfferRequest{" +
                "msisdn='" + msisdn + '\'' +
                ", offer=" + offer +
                '}';
    }
}
