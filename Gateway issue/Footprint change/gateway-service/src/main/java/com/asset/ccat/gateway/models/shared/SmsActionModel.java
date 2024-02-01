package com.asset.ccat.gateway.models.shared;

public class SmsActionModel {

    private Integer smsActionId;
    private String actionName;
    private String description;
    private String code;

    public SmsActionModel() {
    }

    public SmsActionModel(Integer actionId, String actionName, String description, String code) {
        this.smsActionId = actionId;
        this.actionName = actionName;
        this.description = description;
        this.code = code;
    }

    public Integer getSmsActionId() {
        return smsActionId;
    }

    public void setSmsActionId(Integer smsActionId) {
        this.smsActionId = smsActionId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
