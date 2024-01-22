/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.models;

import java.io.Serializable;

/**
 *
 * @author Mahmoud Shehab
 */
public class FamilyAndFriendsModel implements Serializable {

    private String id;
    private String number;
    private String ind;
    private String plan;
    private String owner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getInd() {
        return ind;
    }

    public void setInd(String ind) {
        this.ind = ind;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }


    @Override
    public String toString() {
        return "FamilyAndFriendsModel{" +
                "id='" + id + '\'' +
                ", number='" + number + '\'' +
                ", ind='" + ind + '\'' +
                ", plan='" + plan + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }
}
