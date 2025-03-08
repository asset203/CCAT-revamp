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
public class UpdateDedicatedAccount {

    private Integer id;
    private Integer adjustmentMethod;
    private Long adjustmentAmount;
    private Float balance;
    private Long expiryDate;
    private String unitType;
    private Boolean isDateEdited;

    public Integer getAdjustmentMethod() {
        return adjustmentMethod;
    }

    public void setAdjustmentMethod(Integer adjustmentMethod) {
        this.adjustmentMethod = adjustmentMethod;
    }

    public Long getAdjustmentAmount() {
        return adjustmentAmount;
    }

    public void setAdjustmentAmount(Long adjustmentAmount) {
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

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }
}
