/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.models.requests;

import java.util.List;

/**
 *
 * @author Mahmoud Shehab
 */
public class UpdateDedicatedBalanceRequest extends BaseRequest {

    private String msisdn;
    private String transactionType;
    private String transactionCode;
    private List<UpdateDedicatedAccount> list;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public List<UpdateDedicatedAccount> getList() {
        return list;
    }

    public void setList(List<UpdateDedicatedAccount> list) {
        this.list = list;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    @Override
    public String toString() {
        return "UpdateDedicatedBalanceRequest{" +
                "msisdn='" + msisdn + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", transactionCode='" + transactionCode + '\'' +
                ", list=" + list +
                '}';
    }
}
