package com.asset.ccat.gateway.models.requests;

import java.util.List;

/**
 * @author Mahmoud Shehab
 */
public class UpdateAccumulatorsRequest extends BaseRequest {

    private String msisdn;
    private String transactionType;
    private String transactionCode;
    private List<UpdateAccumlatorModel> list;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public List<UpdateAccumlatorModel> getList() {
        return list;
    }

    public void setList(List<UpdateAccumlatorModel> list) {
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
        return "UpdateAccumulatorsRequest{" +
                "msisdn='" + msisdn + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", transactionCode='" + transactionCode + '\'' +
                ", list=" + list +
                '}';
    }
}
