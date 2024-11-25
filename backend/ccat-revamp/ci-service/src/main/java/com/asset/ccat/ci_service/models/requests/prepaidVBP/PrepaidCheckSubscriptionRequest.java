package com.asset.ccat.ci_service.models.requests.prepaidVBP;

import com.asset.ccat.ci_service.models.requests.SubscriberRequest;

public class PrepaidCheckSubscriptionRequest extends SubscriberRequest {
    public PrepaidCheckSubscriptionRequest() {
    }

    @Override
    public String toString() {
        return "PrepaidCheckSubscriptionRequest{} , msisdn= "+getMsisdn();
    }
}
