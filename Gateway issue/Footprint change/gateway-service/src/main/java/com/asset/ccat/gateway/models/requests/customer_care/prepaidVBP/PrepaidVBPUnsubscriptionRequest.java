package com.asset.ccat.gateway.models.requests.customer_care.prepaidVBP;


import com.asset.ccat.gateway.models.requests.SubscriberRequest;

public class PrepaidVBPUnsubscriptionRequest extends SubscriberRequest {
    public PrepaidVBPUnsubscriptionRequest() {
    }

    @Override
    public String toString() {
        return "PrepaidVBPUnsubscriptionRequest{" +
                "msisdn='" + msisdn + '\'' +
                '}';
    }
}
