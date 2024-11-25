package com.asset.ccat.ci_service.models.requests.super_flex;

import com.asset.ccat.ci_service.models.requests.SubscriberRequest;

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
