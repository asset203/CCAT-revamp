package com.asset.ccat.nba_service.models.responses.tibco.getAllGifts;

import java.util.ArrayList;
import java.util.List;

public class CustomerMarketingProduct {

    public List<Id> id;

    //public Id id;
    public String desc;
    public int type;
    public ArrayList<Category> category;
    public ValidityPeriod validityPeriod;
    public Details details;
    public Parts parts;

//    public Id getId() {
//        return id;
//    }
//
//    public void setId(Id id) {
//        this.id = id;
//    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
