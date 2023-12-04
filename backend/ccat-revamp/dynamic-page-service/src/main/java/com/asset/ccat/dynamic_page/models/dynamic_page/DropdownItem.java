package com.asset.ccat.dynamic_page.models.dynamic_page;

public class DropdownItem {

    private String label;
    private String value;

    public DropdownItem() {
    }

    public DropdownItem(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
