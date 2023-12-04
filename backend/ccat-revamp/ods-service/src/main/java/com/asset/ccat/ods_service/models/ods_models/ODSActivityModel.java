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
public class ODSActivityModel {

    private Integer activityId;
    private String activityName;
    private String tableType;
    private Integer drIdIdx;
    private Integer balanceIdx;
    private Integer expDateIdx;
    private Integer daAmountIdx;
    private Integer adjActionIdx;
    private Integer isNewFormatIdx;

    public Integer getActivityId() {
        return activityId;
    }

    public ODSActivityModel withActivityId(Integer activityId) {
        this.activityId = activityId;
        return this;
    }

    public String getActivityName() {
        return activityName;
    }

    public ODSActivityModel withActivityName(String activityName) {
        this.activityName = activityName;
        return this;
    }

    public String getTableType() {
        return tableType;
    }

    public ODSActivityModel withTableType(String tableType) {
        this.tableType = tableType;
        return this;
    }

    public Integer getDrIdIdx() {
        return drIdIdx;
    }

    public ODSActivityModel withDrIdIdx(Integer drIdIdx) {
        this.drIdIdx = drIdIdx;
        return this;
    }

    public Integer getBalanceIdx() {
        return balanceIdx;
    }

    public ODSActivityModel withBalanceIdx(Integer balanceIdx) {
        this.balanceIdx = balanceIdx;
        return this;
    }

    public Integer getExpDateIdx() {
        return expDateIdx;
    }

    public ODSActivityModel withExpDateIdx(Integer expDateIdx) {
        this.expDateIdx = expDateIdx;
        return this;
    }

    public Integer getDaAmountIdx() {
        return daAmountIdx;
    }

    public ODSActivityModel withDaAmountIdx(Integer daAmountIdx) {
        this.daAmountIdx = daAmountIdx;
        return this;
    }

    public Integer getAdjActionIdx() {
        return adjActionIdx;
    }

    public ODSActivityModel withAdjActionIdx(Integer adjActionIdx) {
        this.adjActionIdx = adjActionIdx;
        return this;
    }

    public Integer getIsNewFormatIdx() {
        return isNewFormatIdx;
    }

    public ODSActivityModel withIsNewFormatIdx(Integer isNewFormatIdx) {
        this.isNewFormatIdx = isNewFormatIdx;
        return this;
    }

}
