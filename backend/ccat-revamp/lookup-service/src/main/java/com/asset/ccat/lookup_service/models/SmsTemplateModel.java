package com.asset.ccat.lookup_service.models;

import java.util.List;


public class SmsTemplateModel {
    private Integer id;
    private Integer templateId;
    private Integer languageId;

    private String templateText;


    private Integer csTemplateId;


    private Integer templateStatus;

    private String templateParameters;
    private String actionName;

    public SmsTemplateModel() {
    }

    public SmsTemplateModel(Integer id, Integer templateId, Integer languageId, String templateText, Integer csTemplateId, Integer templateStatus, String templateParameters, String actionName) {
        this.id = id;
        this.templateId = templateId;
        this.languageId = languageId;
        this.templateText = templateText;
        this.csTemplateId = csTemplateId;
        this.templateStatus = templateStatus;
        this.templateParameters = templateParameters;
        this.actionName = actionName;
    }

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


    public Integer getTemplateStatus() {
        return templateStatus;
    }

    public void setTemplateStatus(Integer templateStatus) {
        this.templateStatus = templateStatus;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    @Override
    public String toString() {
        return "SmsTemplateModel{" +
                "id=" + id +
                ", templateId=" + templateId +
                ", languageId=" + languageId +
                ", templateText='" + templateText + '\'' +
                ", csTemplateId=" + csTemplateId +
                ", templateStatus=" + templateStatus +
                ", templateParameters='" + templateParameters + '\'' +
                ", actionName='" + actionName + '\'' +
                '}';
    }
}
