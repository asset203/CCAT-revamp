/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.ods_service.models.ods_models;

/**
 *
 * @author Mahmoud Shehab
 */
public class ODSActivityHeader {

    private Integer headerId;
    private String headerName;
    private String headerType;
    private String displayName;

    public Integer getHeaderId() {
        return headerId;
    }

    public ODSActivityHeader withHeaderId(Integer activityId) {
        this.headerId = activityId;
        return this;
    }

    public String getHeaderName() {
        return headerName;
    }

    public ODSActivityHeader withAHeaderName(String activityName) {
        this.headerName = activityName;
        return this;
    }

    public String getHeaderType() {
        return headerType;
    }

    public ODSActivityHeader withHeaderType(String tableType) {
        this.headerType = tableType;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ODSActivityHeader withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

}
