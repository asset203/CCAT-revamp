
package com.asset.ccat.balance_dispute_service.dto.responses;


public class BaseResponse<T> {

    private Integer statusCode;
    private String statusMessage;
    private Integer severity;
    private String requestId;
    private T payload;

    public BaseResponse() {
    }

    public BaseResponse(Integer statusCode, String statusMessage, Integer severity, T payload) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.severity = severity;
        this.payload = payload;
    }

    public BaseResponse(Integer statusCode, String statusMessage, Integer severity, String requestId, T payload) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.severity = severity;
        this.requestId = requestId;
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

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
