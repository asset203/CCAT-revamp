package com.asset.ccat.notification_service.models;

import java.util.List;



public class SmsTemplateModel {
    private Integer id;
    private Integer templateId;
    private Integer languageId;
    private String languageName;
    private String actionName;
    private Integer csTemplateId;
    private String templateText;
    private String templateParameters;
    private List<SmsTemplateParamModel> parameterList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public Integer getCsTemplateId() {
        return csTemplateId;
    }

    public void setCsTemplateId(Integer csTemplateId) {
        this.csTemplateId = csTemplateId;
    }

    public String getTemplateText() {
        return templateText;
    }

    public void setTemplateText(String templateText) {
        this.templateText = templateText;
    }

    public String getTemplateParameters() {
        return templateParameters;
    }

    public void setTemplateParameters(String templateParameters) {
        this.templateParameters = templateParameters;
    }

    public List<SmsTemplateParamModel> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<SmsTemplateParamModel> parameterList) {
        this.parameterList = parameterList;
    }

    @Override
    public String toString() {
        return "SmsTemplateModel{" +
                "id=" + id +
                ", templateId=" + templateId +
                ", languageId=" + languageId +
                ", languageName='" + languageName + '\'' +
                ", actionName='" + actionName + '\'' +
                ", csTemplateId=" + csTemplateId +
                ", templateText='" + templateText + '\'' +
                ", templateParameters='" + templateParameters + '\'' +
                ", parameterList=" + parameterList +
                '}';
    }
}
