package com.asset.ccat.gateway.models.admin.vip;

public class PageModel {
    private Integer menuId;
    private Integer featureId;
    private String label;
    private String url;

    private boolean isVipPage;

    public boolean isVipPage() {
        return isVipPage;
    }

    public void setVipPage(boolean vipPage) {
        isVipPage = vipPage;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Integer featureId) {
        this.featureId = featureId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "VIPPageModel{" +
                "menuId=" + menuId +
                ", featureId=" + featureId +
                ", label='" + label + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
