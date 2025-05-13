/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.requests;

import java.math.BigDecimal;

/**
 *
 * @author Mahmoud Shehab
 */
public class UpdateDedicatedAccount {

    private Integer id;
    private Integer adjustmentMethod;
    private BigDecimal adjustmentAmount;
    private BigDecimal balance;
    private Long expiryDate;
    private String unitType;
    private Boolean isDateEdited;

    public Integer getAdjustmentMethod() {
        return adjustmentMethod;
    }

    public void setAdjustmentMethod(Integer adjustmentMethod) {
        this.adjustmentMethod = adjustmentMethod;
    }

    public BigDecimal getAdjustmentAmount() {
        return adjustmentAmount;
    }

    public void setAdjustmentAmount(BigDecimal adjustmentAmount) {
        this.adjustmentAmount = adjustmentAmount;
    }

    public Long getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Long expiryDate) {
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
