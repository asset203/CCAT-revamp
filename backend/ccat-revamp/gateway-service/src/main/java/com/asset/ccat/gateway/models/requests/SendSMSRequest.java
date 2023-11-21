package com.asset.ccat.gateway.models.requests;

import java.util.HashMap;

public class SendSMSRequest extends SubscriberRequest {


    private Integer templateLanguageId;
    private String actionName;
    private HashMap<String, String> templateParamMap;

    public Integer getTemplateLanguageId() {
        return templateLanguageId;
    }

    public void setTemplateLanguageId(Integer templateLanguageId) {
        this.templateLanguageId = templateLanguageId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public HashMap<String, String> getTemplateParamMap() {
        return templateParamMap;
    }

    public void setTemplateParamMap(HashMap<String, String> templateParamMap) {
        this.templateParamMap = templateParamMap;
    }

    @Override
    public String toString() {
        return "SendSMSRequest{" +
                "templateLanguageId=" + templateLanguageId +
                ", actionName='" + actionName + '\'' +
                ", templateParamMap=" + templateParamMap +
                ", msisdn='" + msisdn + '\'' +
                '}';
    }
}
