package com.asset.ccat.gateway.models.requests.admin.sms_template_cs;

import com.asset.ccat.gateway.models.requests.BaseRequest;
import com.asset.ccat.gateway.models.shared.SmsTemplateModel;

public class UpdateTemplateRequest extends BaseRequest {

    private SmsTemplateModel smsTemplate;

    public SmsTemplateModel getSmsTemplate() {
        return smsTemplate;
    }

    public void setSmsTemplate(SmsTemplateModel smsTemplate) {
        this.smsTemplate = smsTemplate;
    }

    @Override
    public String toString() {
        return "UpdateTemplateRequest{" +
                "smsTemplate=" + smsTemplate +
                '}';
    }
}
