/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.customer_care;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

/**
 *
 * @author Mahmoud Shehab
 */
public class DedicatedAccount {

    private String id;
    private String description;
    private String description2;
    private String units;
    private Float ratingFactor;
    private Float balance;
    private String balancePT;
    private Date expiryDate;

    private Integer unitType;
    private String unitTypeDesc;

    public String getBalancePT() {
        return balancePT;
    }

    public void setBalancePT(String balancePT) {
        this.balancePT = balancePT;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Float getRatingFactor() {
        return ratingFactor;
    }

    public void setRatingFactor(Float ratingFactor) {
        this.ratingFactor = ratingFactor;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public String getUnitTypeDesc() {
        return unitTypeDesc;
    }

    public void setUnitTypeDesc(String unitTypeDesc) {
        this.unitTypeDesc = unitTypeDesc;
    }

    public Integer getUnitType() {
        return unitType;
    }

    public void setUnitType(Integer unitType) {
        this.unitType = unitType;
    }

}
