package com.asset.ccat.gateway.models.dss;

public class DssFlagModel {
    private String flagName;
    private Integer flagValue;
    private String pageName;

    public String getFlagName() {
        return flagName;
    }

    public void setFlagName(String flagName) {
        this.flagName = flagName;
    }

    public Integer getFlagValue() {
        return flagValue;
    }

    public void setFlagValue(Integer flagValue) {
        this.flagValue = flagValue;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    @Override
    public String toString() {
        return "FlagModel{" +
                "flagName='" + flagName + '\'' +
                ", flagValue=" + flagValue +
                ", pageName='" + pageName + '\'' +
                '}';
    }
}