/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.models.requests;

import com.asset.ccat.air_service.converter.DateConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Date;

/**
 *
 * @author Mahmoud Shehab
 */
public class UpdateDedicatedAccount {

    private Integer id;
    private Integer adjustmentMethod;
    private Float adjustmentAmount;
    private Float balance;

    @JsonDeserialize(converter = DateConverter.class)
    private Date expiryDate;
    private Boolean isDateEdited;
    private String unitType;

    public Integer getAdjustmentMethod() {
        return adjustmentMethod;
    }

    public void setAdjustmentMethod(Integer adjustmentMethod) {
        this.adjustmentMethod = adjustmentMethod;
    }

    public Float getAdjustmentAmount() {
        return adjustmentAmount;
    }

    public void setAdjustmentAmount(Float adjustmentAmount) {
        this.adjustmentAmount = adjustmentAmount;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public Boolean getIsDateEdited() {
        return isDateEdited;
    }

    public void setIsDateEdited(Boolean isDateEdited) {
        this.isDateEdited = isDateEdited;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "UpdateDedicatedAccount{" +
                "id=" + id +
                ", adjustmentMethod=" + adjustmentMethod +
                ", adjustmentAmount=" + adjustmentAmount +
                ", expiryDate=" + expiryDate +
                ", isDateEdited=" + isDateEdited +
                ", unitType='" + unitType + '\'' +
                '}';
    }
}
