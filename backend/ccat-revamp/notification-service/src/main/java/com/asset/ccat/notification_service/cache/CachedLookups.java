package com.asset.ccat.notification_service.cache;

import com.asset.ccat.notification_service.models.SMSActionModel;
import com.asset.ccat.notification_service.models.SmsTemplateModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CachedLookups {

    private List<SmsTemplateModel> templates;
    private List<SMSActionModel> smsActions;

    public List<SMSActionModel> getSmsActions() {
        return smsActions;
    }

    public void setSmsActions(List<SMSActionModel> smsActions) {
        this.smsActions = smsActions;
    }

    public List<SmsTemplateModel> getTemplates() {
        return templates;
    }

    public void setTemplates(List<SmsTemplateModel> templates) {
        this.templates = templates;
    }
}
