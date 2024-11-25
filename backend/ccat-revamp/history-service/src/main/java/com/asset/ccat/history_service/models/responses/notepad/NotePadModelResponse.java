/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.history_service.models.responses.notepad;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;

/**
 *
 * @author wael.mohamed
 */
public class NotePadModelResponse {

    private Date date;
    private String note;
    private String operator;

    public NotePadModelResponse() {
    }

    public NotePadModelResponse(Date date, String note, String operator) {
        this.date = date;
        this.note = note;
        this.operator = operator;
    }

    @JsonFormat(shape = JsonFormat.Shape.NUMBER , timezone = "UTC")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

//    public void setDate(Date date) {
//        this.date = date.getTime() / 1000;
//    }
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

}
