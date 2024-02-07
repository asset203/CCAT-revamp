package com.asset.ccat.rabbitmq.models;

import java.io.Serializable;

public class FootprintDetailsModel implements Serializable {

    private static final long serialVersionUID = -5494296903270958120L;

    private String requestId;
    private String paramName;
    private String oldValue;
    private String newValue;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

}
