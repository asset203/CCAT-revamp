package com.asset.ccat.nba_service.models.responses.tibco.getAllGifts;

import java.util.ArrayList;
import java.util.List;

public class CustomerMarketingProduct {
    private List<Id> id;
    private String desc;
    private String type;
    private ArrayList<Category> category;
    private String status;
    private ValidityPeriod validityPeriod;
    private Details details;
    private Parts parts;


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Category> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<Category> category) {
        this.category = category;
    }

    public ValidityPeriod getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(ValidityPeriod validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public Parts getParts() {
        return parts;
    }

    public void setParts(Parts parts) {
        this.parts = parts;
    }

    public List<Id> getId() {
        return id;
    }

    public void setId(List<Id> id) {
        this.id = id;
    }
}
