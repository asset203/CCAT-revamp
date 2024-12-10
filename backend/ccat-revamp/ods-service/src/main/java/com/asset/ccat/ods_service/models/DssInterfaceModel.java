package com.asset.ccat.ods_service.models;


import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

import java.util.List;

public class DssInterfaceModel {
    private String pageName;
    private String spName;
    private String spInputParams;
    private String spOutputParams;
    private List<SqlParameter> inputParameters;
    private List<SqlOutParameter> outputParameters;

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

    public String getSpOutputParams() {
        return spOutputParams;
    }

    public void setSpOutputParams(String spOutputParams) {
        this.spOutputParams = spOutputParams;
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
