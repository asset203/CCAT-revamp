package com.asset.ccat.nba_service.models.requests.tibco.sendSMS;

public class Category {
    public String value;
    public String listHierarchyId;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getListHierarchyId() {
        return listHierarchyId;
    }

    public void setListHierarchyId(String listHierarchyId) {
        this.listHierarchyId = listHierarchyId;
    }
}
