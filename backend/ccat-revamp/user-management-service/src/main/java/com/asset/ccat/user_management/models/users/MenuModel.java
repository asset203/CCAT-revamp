package com.asset.ccat.user_management.models.users;

import java.util.List;

/**
 *
 * @author marwa.elshawarby
 */
public class MenuModel {

    private String label;
    private List<MenuItem> items;
    private String icon;

    public MenuModel() {
    }

    public MenuModel(List<MenuItem> items) {
        this.items = items;
    }

    public MenuModel(String label, List<MenuItem> items) {
        this.label = label;
        this.items = items;
    }

    public MenuModel(String label, String icon, List<MenuItem> items) {
        this.label = label;
        this.items = items;
        this.icon = icon;
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
