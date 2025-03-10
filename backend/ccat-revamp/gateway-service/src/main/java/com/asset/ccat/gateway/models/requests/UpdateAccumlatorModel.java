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
public class UpdateAccumlatorModel {

    private Integer adjustmentMethod;
    private Integer id;
    private Long adjustmentAmount;
    private Long startDate;
    private Boolean isReset;

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

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Boolean getIsReset() {
        return isReset;
    }

    public void setIsReset(Boolean isReset) {
        this.isReset = isReset;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UpdateAccumlatorModel{" +
                "adjustmentMethod=" + adjustmentMethod +
                ", id=" + id +
                ", adjustmentAmount=" + adjustmentAmount +
                ", startDate=" + startDate +
                ", isReset=" + isReset +
                '}';
    }
}
