/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.history_service.models.requests.notepad;

import com.asset.ccat.history_service.models.requests.BaseRequest;
import org.springframework.stereotype.Component;

/**
 *
 * @author wael.mohamed
 */
@Component
public class AddNotePadRequest extends BaseRequest {

    private String msisdn;
    private Integer userId;
    private String entry;
    private String pageName;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    @Override
    public String toString() {
        return "AddNotePadRequest{" +
                "msisdn='" + msisdn + '\'' +
                ", userId=" + userId +
                ", entry='" + entry + '\'' +
                ", pageName='" + pageName + '\'' +
                '}';
    }
}
