package com.asset.ccat.notification_service.models.requests.sms_template_cs;


import com.asset.ccat.notification_service.models.SmsTemplateModel;
import com.asset.ccat.notification_service.models.requests.BaseRequest;

public class AddTemplateRequest extends BaseRequest {

    private SmsTemplateModel smsTemplate;

    public SmsTemplateModel getSmsTemplate() {
        return smsTemplate;
    }

    public void setSmsTemplate(SmsTemplateModel smsTemplate) {
        this.smsTemplate = smsTemplate;
    }

    @Override
    public String toString() {
        return "AddTemplateRequest{" +
                "smsTemplate=" + smsTemplate +
                '}';
    }
}
