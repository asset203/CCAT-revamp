/*
 * To change this license headers, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.ods_service.models.responses;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author wael.mohamed
 */
public class DSSResponseModel {

    private Integer externalCode;
    private String externalDescription;
    private HashMap<Integer, String> headers;
    private List<HashMap<Integer, String>> data;
    private boolean exceedsAllowed;

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

    public HashMap<Integer, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<Integer, String> headers) {
        this.headers = headers;
    }

    public List<HashMap<Integer, String>> getData() {
        return data;
    }

    public void setData(List<HashMap<Integer, String>> data) {
        this.data = data;
    }

    public boolean isExceedsAllowed() {
        return exceedsAllowed;
    }

    public void setExceedsAllowed(boolean exceedsAllowed) {
        this.exceedsAllowed = exceedsAllowed;
    }

}
