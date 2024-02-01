package com.asset.ccat.gateway.models.requests.customer_care.call_reasons;

import com.asset.ccat.gateway.models.requests.SubscriberRequest;

public class AddCallReasonRequest extends SubscriberRequest {
    @Override
    public String toString() {
        return "AddCallReasonRequest{" +
                "msisdn='" + msisdn + '\'' +
                "userName='" + getUsername() + '\'' +
                "userId='" + getUserId() + '\'' +
                '}';
    }
}
