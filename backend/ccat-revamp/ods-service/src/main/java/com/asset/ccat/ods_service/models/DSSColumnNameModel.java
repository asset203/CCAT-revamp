/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.ods_service.models;

/**
 *
 * @author wael.mohamed
 */
public class DSSColumnNameModel {

    private String dssPageName;
    private String columnName;
    private String displayName;

    public String getDssPageName() {
        return dssPageName;
    }

    public void setDssPageName(String dssPageName) {
        this.dssPageName = dssPageName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}
