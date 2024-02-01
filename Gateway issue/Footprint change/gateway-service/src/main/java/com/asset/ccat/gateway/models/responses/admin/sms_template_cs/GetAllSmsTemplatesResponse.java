package com.asset.ccat.gateway.models.responses.admin.sms_template_cs;

import com.asset.ccat.gateway.models.shared.SmsTemplateModel;

import java.util.List;

public class GetAllSmsTemplatesResponse {

    private List<SmsTemplateModel> smsTemplates;

    public GetAllSmsTemplatesResponse() {
    }

    public GetAllSmsTemplatesResponse(List<SmsTemplateModel> smsTemplates) {
        this.smsTemplates = smsTemplates;
    }

    public List<SmsTemplateModel> getSmsTemplates() {
        return smsTemplates;
    }

    public void setSmsTemplates(List<SmsTemplateModel> smsTemplates) {
        this.smsTemplates = smsTemplates;
    }

    @Override
    public String toString() {
        return "GetAllSmsTemplatesResponse{" +
                "smsTemplates=" + smsTemplates +
                '}';
    }
}
