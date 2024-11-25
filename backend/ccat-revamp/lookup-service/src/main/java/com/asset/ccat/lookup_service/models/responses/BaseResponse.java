package com.asset.ccat.lookup_service.models.responses;

import com.asset.ccat.lookup_service.models.shared.ServiceInfo;

/**
 *
 * @author Mahmoud Shehab
 */
public class BaseResponse<T> {

    private Integer statusCode;
    private String statusMessage;
    private Integer severity;
    private ServiceInfo serviceInfo;
    private T payload;

    public BaseResponse() {
    }

    public BaseResponse(Integer statusCode, String statusMessage, Integer severity, T payload) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.severity = severity;
        this.payload = payload;
    }

    public BaseResponse(Integer statusCode, String statusMessage, Integer severity, ServiceInfo serviceInfo, T payload) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.severity = severity;
        this.serviceInfo = serviceInfo;
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

    public ServiceInfo getServiceInfo() {
        return serviceInfo;
    }

    public void setServiceInfo(ServiceInfo serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

}
