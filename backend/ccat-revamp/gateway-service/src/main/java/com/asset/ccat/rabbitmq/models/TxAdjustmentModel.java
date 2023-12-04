/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.rabbitmq.models;

import java.io.Serializable;

/**
 *
 * @author wael.mohamed
 */
public class TxAdjustmentModel implements Serializable {

    private String msisdn;

    public TxAdjustmentModel() {
    }

    public TxAdjustmentModel(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }
}
