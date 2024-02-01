package com.asset.ccat.gateway.models.requests.offer;

import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author wael.mohamed
 */
public class DeleteOfferRequest extends BaseRequest {

    private String msisdn;
    private Integer offerId;

    public DeleteOfferRequest() {
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }

    @Override
    public String toString() {
        return "DeleteOfferRequest{" +
                "msisdn='" + msisdn + '\'' +
                ", offerId=" + offerId +
                '}';
    }
}
