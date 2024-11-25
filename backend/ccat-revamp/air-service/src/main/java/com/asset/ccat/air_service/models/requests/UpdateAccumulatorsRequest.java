package com.asset.ccat.air_service.models.requests;

import java.util.List;

/**
 * @author Mahmoud Shehab
 */
public class UpdateAccumulatorsRequest extends BaseRequest {

    private String msisdn;
    private String transactionType;
    private String transactionCode;
    private List<UpdateAccumulatorModel> list;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public List<UpdateAccumulatorModel> getList() {
        return list;
    }

    public void setList(List<UpdateAccumulatorModel> list) {
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
