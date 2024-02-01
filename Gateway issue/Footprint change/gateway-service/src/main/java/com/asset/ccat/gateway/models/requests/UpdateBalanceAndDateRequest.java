/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.requests;

/**
 *
 * @author Mahmoud Shehab
 */
public class UpdateBalanceAndDateRequest extends SubscriberRequest {
  
    private Float adjustmentAmount;
    private Integer adjustmentMethod;
    private Integer supervisionFeePeriod;
    private Integer supervisionFeePeriodOld;
    private Long supervisionFeeDate;
    private Integer serviceFeePeriod;
    private Integer serviceFeePeriodOld;
    private Long serviceFeeDate;
    private String transactionType;
    private String transactionCode;


    public Float getAdjustmentAmount() {
        return adjustmentAmount;
    }

    public void setAdjustmentAmount(Float adjustmentAmount) {
        this.adjustmentAmount = adjustmentAmount;
    }

    public Integer getAdjustmentMethod() {
        return adjustmentMethod;
    }

    public void setAdjustmentMethod(Integer adjustmentMethod) {
        this.adjustmentMethod = adjustmentMethod;
    }

    public Integer getSupervisionFeePeriod() {
        return supervisionFeePeriod;
    }

    public void setSupervisionFeePeriod(Integer supervisionFeePeriod) {
        this.supervisionFeePeriod = supervisionFeePeriod;
    }

    public Long getSupervisionFeeDate() {
        return supervisionFeeDate;
    }

    public void setSupervisionFeeDate(Long supervisionFeeDate) {
        this.supervisionFeeDate = supervisionFeeDate;
    }

    public Integer getServiceFeePeriod() {
        return serviceFeePeriod;
    }

    public void setServiceFeePeriod(Integer serviceFeePeriod) {
        this.serviceFeePeriod = serviceFeePeriod;
    }

    public Long getServiceFeeDate() {
        return serviceFeeDate;
    }

    public Integer getSupervisionFeePeriodOld() {
        return supervisionFeePeriodOld;
    }

    public void setSupervisionFeePeriodOld(Integer supervisionFeePeriodOld) {
        this.supervisionFeePeriodOld = supervisionFeePeriodOld;
    }

    public Integer getServiceFeePeriodOld() {
        return serviceFeePeriodOld;
    }

    public void setServiceFeePeriodOld(Integer serviceFeePeriodOld) {
        this.serviceFeePeriodOld = serviceFeePeriodOld;
    }

    public void setServiceFeeDate(Long serviceFeeDate) {
        this.serviceFeeDate = serviceFeeDate;
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
        return "UpdateBalanceAndDateRequest{" +
                "adjustmentAmount=" + adjustmentAmount +
                ", adjustmentMethod=" + adjustmentMethod +
                ", supervisionFeePeriod=" + supervisionFeePeriod +
                ", supervisionFeePeriodOld=" + supervisionFeePeriodOld +
                ", supervisionFeeDate=" + supervisionFeeDate +
                ", serviceFeePeriod=" + serviceFeePeriod +
                ", serviceFeePeriodOld=" + serviceFeePeriodOld +
                ", serviceFeeDate=" + serviceFeeDate +
                ", transactionType='" + transactionType + '\'' +
                ", transactionCode='" + transactionCode + '\'' +
                ", msisdn='" + msisdn + '\'' +
                '}';
    }
}
