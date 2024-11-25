package com.asset.ccat.gateway.models.requests.customer_care.flex_share;

import com.asset.ccat.gateway.models.requests.SubscriberRequest;

public class GetFlexShareStatesRequest extends SubscriberRequest {
    @Override
    public String toString() {
        return "GetFlexShareStatesRequest{" +
                "msisdn='" + msisdn + '\'' +
                '}';
    }
}
