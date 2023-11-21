package com.asset.ccat.gateway.models.requests.admin.sms_template_cs;

import com.asset.ccat.gateway.models.requests.BaseRequest;

public class DeleteTemplateRequest extends BaseRequest {

    private Integer id;
    private Integer templateId;

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

    @Override
    public String toString() {
        return "DeleteTemplateRequest{" +
                "id=" + id +
                ", templateId=" + templateId +
                '}';
    }
}
