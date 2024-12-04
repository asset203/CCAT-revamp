package com.asset.ccat.lookup_service.models;

import com.asset.ccat.lookup_service.constants.FeatureType;

public class PageModel {
    private Integer menuId;
    private Integer featureId;
    private String label;
    private String url;
    private FeatureType type;

    private boolean isVipPage;

    public boolean getIsVipPage() {
        return isVipPage;
    }

    public void setIsVipPage(boolean isVipPage) {
        this.isVipPage = isVipPage;
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

    public FeatureType getType() {
        return type;
    }

    public void setType(FeatureType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PageModel{" +
                "menuId=" + menuId +
                ", featureId=" + featureId +
                ", label='" + label + '\'' +
                ", url='" + url + '\'' +
                ", isVipPage=" + isVipPage +
                '}';
    }
}
