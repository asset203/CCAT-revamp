package com.asset.ccat.notification_service.models.requests;



import java.util.HashMap;

public class SendSMSRequest extends SubscriberRequest {
    private Integer templateLanguageId;
    private String actionName;
    private HashMap<String, String> templateParam;

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

    public HashMap<String, String> getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(HashMap<String, String> templateParam) {
        this.templateParam = templateParam;
    }

    @Override
    public String toString() {
        return "SendSMSRequest{" +
                "templateLanguageId=" + templateLanguageId +
                ", actionName='" + actionName + '\'' +
                ", templateParamMap=" + templateParam +
                ", msisdn='" + msisdn + '\'' +
                '}';
    }
}

