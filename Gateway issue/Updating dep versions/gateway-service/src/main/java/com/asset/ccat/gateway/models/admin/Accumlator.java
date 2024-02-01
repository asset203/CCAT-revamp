/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.admin;

import java.util.Objects;

/**
 * @author wael.mohamed
 */
public class Accumlator {

    private Integer accID;
    private String description;

    public Integer getAccID() {
        return accID;
    }

    public void setAccID(Integer accID) {
        this.accID = accID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.accID);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Accumlator other = (Accumlator) obj;
        return Objects.equals(this.accID, other.accID);
    }

    @Override
    public String toString() {
        return "Accumlator{" +
                "accID=" + accID +
                ", description='" + description + '\'' +
                '}';
    }
}
