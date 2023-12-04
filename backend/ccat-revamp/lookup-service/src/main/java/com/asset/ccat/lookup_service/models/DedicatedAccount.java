/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.models;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author wael.mohamed
 */
public class DedicatedAccount implements Serializable{

    private Integer daID;
    private String description;
    private Float ratingFactor;

    public DedicatedAccount() {
    }

    public DedicatedAccount(Integer daID, String description, Float ratingFactor) {
        this.daID = daID;
        this.description = description;
        this.ratingFactor = ratingFactor;
    }

    public Integer getDaID() {
        return daID;
    }

    public void setDaID(Integer daID) {
        this.daID = daID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getRatingFactor() {
        return ratingFactor;
    }

    public void setRatingFactor(Float ratingFactor) {
        this.ratingFactor = ratingFactor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.daID);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final DedicatedAccount other = (DedicatedAccount) obj;
        return Objects.equals(this.daID, other.daID);
    }
}
