package com.asset.ccat.gateway.models.shared;

import java.util.HashMap;

public class FootPrintPageModel {

    private Integer pageId;
    private String pageName;
    private String controllerName;
    private HashMap<String, FootPrintActionModel> footprintPageInfoMap;

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getControllerName() {
        return controllerName;
    }

    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }

    public HashMap<String, FootPrintActionModel> getFootprintPageInfoMap() {
        return footprintPageInfoMap;
    }

    public void setFootprintPageInfoMap(HashMap<String, FootPrintActionModel> footprintPageInfoMap) {
        this.footprintPageInfoMap = footprintPageInfoMap;
    }
}
