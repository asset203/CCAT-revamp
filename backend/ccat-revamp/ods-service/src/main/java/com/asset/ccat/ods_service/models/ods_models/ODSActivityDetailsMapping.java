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
public class ODSActivityDetailsMapping {

    private Integer activityId;
    private Integer columnIdx;
    private String columnKey;
    private String displayName;
    private String detailType;

    public Integer getActivityId() {
        return activityId;
    }

    public ODSActivityDetailsMapping withActivityId(Integer activityId) {
        this.activityId = activityId;
        return this;
    }

    public Integer getColumnIdx() {
        return columnIdx;
    }

    public ODSActivityDetailsMapping withColumnIdx(Integer columnIdx) {
        this.columnIdx = columnIdx;
        return this;

    }

    public String getColumnKey() {
        return columnKey;
    }

    public ODSActivityDetailsMapping withColumnKey(String columnKey) {
        this.columnKey = columnKey;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ODSActivityDetailsMapping withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getDetailType() {
        return detailType;
    }

    public ODSActivityDetailsMapping withDetailType(String detailType) {
        this.detailType = detailType;
        return this;
    }
}
