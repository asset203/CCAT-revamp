/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.models;

/**
 *
 * @author Mahmoud Shehab
 */
public class BaseResponse<T> {

    private Integer statusCode;
    private String statusMessage;
    private Integer severity;
    private T payload;

    public BaseResponse() {
    }

    public BaseResponse(Integer statusCode, String statusMessage, Integer severity, T payload) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.severity = severity;
        this.payload = payload;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Integer getSeverity() {
        return severity;
    }

    public void setSeverity(Integer severity) {
        this.severity = severity;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

}
