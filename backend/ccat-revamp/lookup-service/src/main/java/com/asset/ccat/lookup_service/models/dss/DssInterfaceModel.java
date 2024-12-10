package com.asset.ccat.lookup_service.models.dss;


public class DssInterfaceModel {
    private String pageName;
    private String spName;
    private String spInputParams;

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public String getSpInputParams() {
        return spInputParams;
    }

    public void setSpInputParams(String spInputParams) {
        this.spInputParams = spInputParams;
    }

    @Override
    public String toString() {
        return "DssInterfaceModel{" +
                "pageName='" + pageName + '\'' +
                ", spName='" + spName + '\'' +
                ", spInputParams=" + spInputParams +
                '}';
    }
}

