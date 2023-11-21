package com.asset.ccat.nba_service.models.requests.tibco.redeemGift;

import java.util.ArrayList;

public class CustomerMarketingProduct {
    public ArrayList<Id> id;
    public String type;
    public ArrayList<Category> category;
    public Parts parts;

    public CustomerMarketingProduct() {
    }

    public CustomerMarketingProduct(ArrayList<Id> id, String type, ArrayList<Category> category, Parts parts) {
        this.id = id;
        this.type = type;
        this.category = category;
        this.parts = parts;
    }

    public ArrayList<Id> getId() {
        return id;
    }

    public void setId(ArrayList<Id> id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Category> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<Category> category) {
        this.category = category;
    }

    public Parts getParts() {
        return parts;
    }

    public void setParts(Parts parts) {
        this.parts = parts;
    }

    @Override
    public String toString() {
        return "CustomerMarketingProduct{" +
            "id=" + id +
            ", type='" + type + '\'' +
            ", category=" + category +
            ", parts=" + parts +
            '}';
    }
}

