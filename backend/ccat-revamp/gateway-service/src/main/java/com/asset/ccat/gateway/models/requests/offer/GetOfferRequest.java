package com.asset.ccat.gateway.models.requests.offer;

import com.asset.ccat.gateway.models.requests.BaseRequest;

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
