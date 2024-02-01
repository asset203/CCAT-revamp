package com.asset.ccat.gateway.models.requests.customer_care.language;

import com.asset.ccat.gateway.models.requests.SubscriberRequest;

/**
 * @author nour.ihab
 */
public class UpdateLanguageRequest extends SubscriberRequest {

    private Integer languageId;

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    @Override
    public String toString() {
        return "UpdateLanguageRequest{" +
                "languageId=" + languageId +
                ", msisdn='" + msisdn + '\'' +
                '}';
    }
}
