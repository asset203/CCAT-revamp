package com.asset.ccat.gateway.models.shared;

public class FootprintAttributesModel {
    private String requestId;
    private String sessionId;
    private String actionType;
    private String actionName;
    private String footprintStatus;
    private String errorCode;
    private String errorMessage;

    public FootprintAttributesModel() {
    }

    public FootprintAttributesModel(String requestId, String sessionId, String actionType, String actionName, String footprintStatus, String errorCode, String errorMessage) {
        this.requestId = requestId;
        this.sessionId = sessionId;
        this.actionType = actionType;
        this.actionName = actionName;
        this.footprintStatus = footprintStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getFootprintStatus() {
        return footprintStatus;
    }

    public void setFootprintStatus(String footprintStatus) {
        this.footprintStatus = footprintStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
