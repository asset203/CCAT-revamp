/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.models;

import java.io.Serializable;

/**
 *
 * @author wael.mohamed
 */
public class OfferStateModel implements Serializable {

    /*
     * Offer state will be one of the below values
     * 0 --> Enabled offer state
     * 1 --> Disabled offer state
     */
    private Integer stateId;
    private String stateDesc;

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public String getStateDesc() {
        return stateDesc;
    }

    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }

}
