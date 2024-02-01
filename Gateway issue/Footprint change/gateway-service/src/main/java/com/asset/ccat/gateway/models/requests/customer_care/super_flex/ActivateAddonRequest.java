package com.asset.ccat.gateway.models.requests.customer_care.super_flex;

import com.asset.ccat.gateway.models.requests.SubscriberRequest;

public class ActivateAddonRequest extends SubscriberRequest {

    private String packageId;

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    @Override
    public String toString() {
        return "ActivateAddonRequest{" +
                "msisdn='" + msisdn + '\'' +
                ", packageId='" + packageId + '\'' +
                '}';
    }
}
