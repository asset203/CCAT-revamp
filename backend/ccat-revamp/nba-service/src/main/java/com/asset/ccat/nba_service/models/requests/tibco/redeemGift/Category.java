package com.asset.ccat.nba_service.models.requests.tibco.redeemGift;

public class Category {
    public String value;
    public String listHierarchyId;

    public Category() {
    }

    public Category(String value, String listHierarchyId) {
        this.value = value;
        this.listHierarchyId = listHierarchyId;
    }

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

    @Override
    public String toString() {
        return "Category{" +
            "value='" + value + '\'' +
            ", listHierarchyId='" + listHierarchyId + '\'' +
            '}';
    }
}
