/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.models.requests.offer;

import com.asset.ccat.air_service.models.requests.BaseRequest;

/**
 * @author wael.mohamed
 */
public class GetOfferRequest extends BaseRequest {

    private String msisdn;

    public GetOfferRequest() {
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    @Override
    public String toString() {
        return "GetOfferRequest{" +
                "msisdn='" + msisdn + '\'' +
                '}';
    }
}
