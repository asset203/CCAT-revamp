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
public class UpdateAccumulatorModel {

    private Integer adjustmentMethod;
    private Integer id;
    private Long adjustmentAmount;

    @JsonDeserialize(converter = DateConverter.class)
    private Date startDate;
    private Boolean isReset;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
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

    public Boolean getIsDateEdited() {
        return isDateEdited;
    }

    public void setIsDateEdited(Boolean isDateEdited) {
        this.isDateEdited = isDateEdited;
    }
    @Override
    public String toString() {
        return "UpdateAccumulatorModel{" +
                "adjustmentMethod=" + adjustmentMethod +
                ", id=" + id +
                ", adjustmentAmount=" + adjustmentAmount +
                ", startDate=" + startDate +
                ", isReset=" + isReset +
                ", isDateEdited=" + isDateEdited +
                '}';
    }
}
