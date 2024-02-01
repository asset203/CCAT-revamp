package com.asset.ccat.gateway.models.users;

import java.util.List;

/**
 *
 * @author nour.ihab
 */
public class MenuModel {

    private String label;
    private String icon;
    private List<MenuItem> items;

    public MenuModel() {
    }

    public MenuModel(String label, List<MenuItem> items) {
        this.label = label;
        this.items = items;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
