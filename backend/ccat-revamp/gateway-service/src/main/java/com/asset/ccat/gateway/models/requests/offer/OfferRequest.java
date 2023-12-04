package com.asset.ccat.gateway.models.requests.offer;

import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
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
