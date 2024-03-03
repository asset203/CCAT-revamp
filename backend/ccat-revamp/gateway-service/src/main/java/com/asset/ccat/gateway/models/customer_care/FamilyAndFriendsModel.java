package com.asset.ccat.gateway.models.customer_care;

public class FamilyAndFriendsModel {
    private String id;
    private String number;
    private String ind;
    private String plan;
    private String owner;

    public FamilyAndFriendsModel() {
    }

    public FamilyAndFriendsModel(String id, String number, String ind, String plan, String owner) {
        this.id = id;
        this.number = number;
        this.ind = ind;
        this.plan = plan;
        this.owner = owner;
    }

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
}
