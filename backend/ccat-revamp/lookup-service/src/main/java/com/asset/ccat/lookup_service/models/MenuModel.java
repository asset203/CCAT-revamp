package com.asset.ccat.lookup_service.models;

/**
 *
 * @author marwa.elshawarby
 */
public class MenuModel {

    private Integer menuId;
    private String label;
    private String routerLink;
    private String icon;

    public MenuModel() {
    }

    public MenuModel(String label, String routerLink) {
        this.label = label;
        this.routerLink = routerLink;
    }

    public MenuModel(String label, String routerLink, String icon) {
        this.label = label;
        this.routerLink = routerLink;
        this.icon = icon;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getRouterLink() {
        return routerLink;
    }

    public void setRouterLink(String routerLink) {
        this.routerLink = routerLink;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
