package com.asset.ccat.gateway.models.users;

/**
 * @author nour.ihab
 */
public class MenuItem {

    private Integer menuId;
    private String label;
    private String routerLink;
    private String icon;
    private Boolean isDynamicMenu;

    public MenuItem() {
    }

    public MenuItem(String label, String routerLink) {
        this.label = label;
        this.routerLink = routerLink;
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

    public Boolean getDynamicMenu() {
        return isDynamicMenu;
    }

    public void setDynamicMenu(Boolean dynamicMenu) {
        isDynamicMenu = dynamicMenu;
    }

    @Override
    public String toString() {
        return "Menu ID: " + menuId +
                ", Menu Label: " + label;
    }
}
