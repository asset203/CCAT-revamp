/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.requests.service_class;

import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 *
 * @author wael.mohamed
 */
public class UpdateServiceClassRequest extends BaseRequest {

    private String msisdn;
    private Integer newServiceClassId;
    private Integer currentServiceClassId;

    public UpdateServiceClassRequest() {
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Integer getNewServiceClassId() {
        return newServiceClassId;
    }

    public void setNewServiceClassId(Integer newServiceClassId) {
        this.newServiceClassId = newServiceClassId;
    }

    public Integer getCurrentServiceClassId() {
        return currentServiceClassId;
    }

    public void setCurrentServiceClassId(Integer currentServiceClassId) {
        this.currentServiceClassId = currentServiceClassId;
    }

    @Override
    public String toString() {
        return "UpdateServiceClassRequest{" +
                "msisdn='" + msisdn + '\'' +
                ", newServiceClassId=" + newServiceClassId +
                ", currentServiceClassId=" + currentServiceClassId +
                '}';
    }
}
