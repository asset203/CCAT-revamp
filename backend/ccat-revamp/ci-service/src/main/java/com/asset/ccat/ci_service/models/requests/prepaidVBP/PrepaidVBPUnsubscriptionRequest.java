package com.asset.ccat.ci_service.models.requests.prepaidVBP;
import com.asset.ccat.ci_service.models.requests.SubscriberRequest;

public class PrepaidVBPUnsubscriptionRequest extends SubscriberRequest {
    public PrepaidVBPUnsubscriptionRequest() {
    }

    @Override
    public String toString() {
        return "PrepaidVBPUnsubscriptionRequest{}, msisdn= "+getMsisdn();
    }
}
