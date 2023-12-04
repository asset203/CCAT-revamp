package com.asset.ccat.gateway.models.requests.admin.locking_admin;

import com.asset.ccat.gateway.models.requests.SubscriberRequest;

/**
 * @author wael.mohamed
 */
public class LockingAdministrationRequest extends SubscriberRequest {

    private Long date;

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "LockingAdministrationRequest{" +
                "date=" + date +
                '}';
    }
}
