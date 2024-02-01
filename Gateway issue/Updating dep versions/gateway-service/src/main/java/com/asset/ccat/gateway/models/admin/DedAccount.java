/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.admin;

import java.util.Objects;

/**
 *
 * @author wael.mohamed
 */
public class DedAccount {

    private Integer daID;
    private String description;
    private Float rattingFactor;

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

    public Float getRattingFactor() {
        return rattingFactor;
    }

    public void setRattingFactor(Float rattingFactor) {
        this.rattingFactor = rattingFactor;
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
        final DedAccount other = (DedAccount) obj;
        return Objects.equals(this.daID, other.daID);
    }

    @Override
    public String toString() {
        return "DedAccount{" +
                "daID=" + daID +
                ", description='" + description + '\'' +
                ", rattingFactor=" + rattingFactor +
                '}';
    }
}
