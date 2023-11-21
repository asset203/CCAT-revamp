/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Mahmoud Shehab
 */
public class AccumulatorModel implements Serializable {

    private String id;
    private Float value;
    private Date StartDate;
    private Date resetDate;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date StartDate) {
        this.StartDate = StartDate;
    }

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    public Date getResetDate() {
        return resetDate;
    }

    public void setResetDate(Date resetDate) {
        this.resetDate = resetDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "AccumulatorModel{" +
                "id='" + id + '\'' +
                ", value=" + value +
                ", StartDate=" + StartDate +
                ", resetDate=" + resetDate +
                ", description='" + description + '\'' +
                '}';
    }
}
