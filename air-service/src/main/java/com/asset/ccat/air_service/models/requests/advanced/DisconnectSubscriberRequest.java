/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.models.requests.advanced;

import com.asset.ccat.air_service.models.requests.BaseRequest;

/**
 *
 * @author Mahmoud Shehab
 */
public class DisconnectSubscriberRequest extends BaseRequest {

    private String subscriberMsisdn;
    private Integer disconnectReasonId;
    private Boolean disconnectFromCharging;
    private Boolean isBatch;
    private Boolean validateDisconnection;

    public String getSubscriberMsisdn() {
        return subscriberMsisdn;
    }

    public void setSubscriberMsisdn(String subscriberMsisdn) {
        this.subscriberMsisdn = subscriberMsisdn;
    }

    public Integer getDisconnectReasonId() {
        return disconnectReasonId;
    }

    public void setDisconnectReasonId(Integer disconnectReasonId) {
        this.disconnectReasonId = disconnectReasonId;
    }

    public Boolean getDisconnectFromCharging() {
        return disconnectFromCharging;
    }

    public void setDisconnectFromCharging(Boolean disconnectFromCharging) {
        this.disconnectFromCharging = disconnectFromCharging;
    }

    public Boolean getIsBatch() {
        return isBatch;
    }

    public void setIsBatch(Boolean isBatch) {
        this.isBatch = isBatch;
    }

    public Boolean getValidateDisconnection() {
        return validateDisconnection;
    }

    public void setValidateDisconnection(Boolean validateDisconnection) {
        this.validateDisconnection = validateDisconnection;
    }

    @Override
    public String toString() {
        return "DisconnectSubscriberRequest{" +
                "subscriberMsisdn='" + subscriberMsisdn + '\'' +
                ", disconnectReasonId=" + disconnectReasonId +
                ", disconnectFromCharging=" + disconnectFromCharging +
                ", isBatch=" + isBatch +
                ", validateDisconnection=" + validateDisconnection +
                '}';
    }
}
