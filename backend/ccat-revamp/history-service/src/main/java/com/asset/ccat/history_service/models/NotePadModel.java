/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.history_service.models;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author wael.mohamed
 */
/**
 *
 * @author wael.mohamed
 */
public class NotePadModel implements Serializable {

    private Date entryDate;
    private String msisdn;
    private int msisdnModX;
    private Integer userId;
    private String notepadEntry;
    private String userName;

    private String pageName;

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public int getMsisdnModX() {
        return msisdnModX;
    }

    public void setMsisdnModX(int msisdnModX) {
        this.msisdnModX = msisdnModX;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNotepadEntry() {
        return notepadEntry;
    }

    public void setNotepadEntry(String notepadEntry) {
        this.notepadEntry = notepadEntry;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    @Override
    public String toString() {
        return "NotePadModel{" +
                "entryDate=" + entryDate +
                ", msisdn='" + msisdn + '\'' +
                ", msisdnModX=" + msisdnModX +
                ", userId=" + userId +
                ", notepadEntry='" + notepadEntry + '\'' +
                ", userName='" + userName + '\'' +
                ", pageName='" + pageName + '\'' +
                '}';
    }
}
