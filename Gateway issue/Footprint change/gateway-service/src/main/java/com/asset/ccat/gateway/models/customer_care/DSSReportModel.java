/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.customer_care;

import java.util.List;
import java.util.Map;

/**
 * @author wael.mohamed
 */
public class DSSReportModel {

    private Integer externalCode;
    private String externalDescription;
    private Map<Integer, String> headers;
    private List<Map<Integer, String>> data;
    private Integer totalNumberOfActivities;


    public Integer getExternalCode() {
        return externalCode;
    }

    public void setExternalCode(Integer externalCode) {
        this.externalCode = externalCode;
    }

    public String getExternalDescription() {
        return externalDescription;
    }

    public void setExternalDescription(String externalDescription) {
        this.externalDescription = externalDescription;
    }

    public Map<Integer, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<Integer, String> headers) {
        this.headers = headers;
    }

    public List<Map<Integer, String>> getData() {
        return data;
    }

    public void setData(List<Map<Integer, String>> data) {
        this.data = data;
    }

    public Integer getTotalNumberOfActivities() {
        return totalNumberOfActivities;
    }

    public void setTotalNumberOfActivities(Integer totalNumberOfActivities) {
        this.totalNumberOfActivities = totalNumberOfActivities;
    }

}
