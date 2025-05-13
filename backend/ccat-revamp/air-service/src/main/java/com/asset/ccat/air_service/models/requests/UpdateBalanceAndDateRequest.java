/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.models.requests;

import com.asset.ccat.air_service.converter.DateConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Mahmoud Shehab
 */
public class UpdateBalanceAndDateRequest extends BaseRequest {

    private String msisdn;
    private BigDecimal adjustmentAmount;
    private Integer adjustmentMethod;
    private Integer supervisionFeePeriod;
    private Integer supervisionFeePeriodOld;

    @JsonDeserialize(converter = DateConverter.class)
    private Date supervisionFeeDate;
    private Integer serviceFeePeriod;
    private Integer serviceFeePeriodOld;

    @JsonDeserialize(converter = DateConverter.class)
    private Date serviceFeeDate;
    private String transactionType;
    private String transactionCode;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public BigDecimal getAdjustmentAmount() {
        return adjustmentAmount;
    }

    public void setAdjustmentAmount(BigDecimal adjustmentAmount) {
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

    public Integer getSupervisionFeePeriodOld() {
        return supervisionFeePeriodOld;
    }

    public void setSupervisionFeePeriodOld(Integer supervisionFeePeriodOld) {
        this.supervisionFeePeriodOld = supervisionFeePeriodOld;
    }

    public Date getSupervisionFeeDate() {
        return supervisionFeeDate;
    }

    public void setSupervisionFeeDate(Date supervisionFeeDate) {
        this.supervisionFeeDate = supervisionFeeDate;
    }

    public Integer getServiceFeePeriod() {
        return serviceFeePeriod;
    }

    public void setServiceFeePeriod(Integer serviceFeePeriod) {
        this.serviceFeePeriod = serviceFeePeriod;
    }

    public Integer getServiceFeePeriodOld() {
        return serviceFeePeriodOld;
    }

    public void setServiceFeePeriodOld(Integer serviceFeePeriodOld) {
        this.serviceFeePeriodOld = serviceFeePeriodOld;
    }

    public Date getServiceFeeDate() {
        return serviceFeeDate;
    }

    public void setServiceFeeDate(Date serviceFeeDate) {
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
                "msisdn='" + msisdn + '\'' +
                ", adjustmentAmount=" + adjustmentAmount +
                ", adjustmentMethod=" + adjustmentMethod +
                ", supervisionFeePeriod=" + supervisionFeePeriod +
                ", supervisionFeePeriodOld=" + supervisionFeePeriodOld +
                ", supervisionFeeDate=" + supervisionFeeDate +
                ", serviceFeePeriod=" + serviceFeePeriod +
                ", serviceFeePeriodOld=" + serviceFeePeriodOld +
                ", serviceFeeDate=" + serviceFeeDate +
                ", transactionType='" + transactionType + '\'' +
                ", transactionCode='" + transactionCode + '\'' +
                '}';
    }
}
