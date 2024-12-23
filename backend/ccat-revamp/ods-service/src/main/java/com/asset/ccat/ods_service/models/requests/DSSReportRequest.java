/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.ods_service.models.requests;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 *
 * @author wael.mohamed
 */
public class DSSReportRequest extends SubscriberRequest {

    private Long dateFrom;
    private Long dateTo;

    private Integer flag;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    public Long getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Long dateFrom) {
        this.dateFrom = dateFrom;
    }

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    public Long getDateTo() {
        return dateTo;
    }

    public void setDateTo(Long dateTo) {
        this.dateTo = dateTo;
    }


    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "DSSReportRequest{" +
                "dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", flag=" + flag +
                '}';
    }
}
