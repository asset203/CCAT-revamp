package com.asset.ccat.user_management.models.users;

/**
 *
 * @author marwa.elshawarby
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

    public MenuItem(String label, String routerLink, String icon) {
        this.label = label;
        this.routerLink = routerLink;
        this.icon = icon;
        this.isDynamicMenu = routerLink.contains("dynamic-page") && label != null && !label.contains("Customer Dynamic Page");
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
}
