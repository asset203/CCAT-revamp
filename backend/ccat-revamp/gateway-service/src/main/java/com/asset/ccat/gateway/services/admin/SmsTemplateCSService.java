package com.asset.ccat.gateway.services.admin;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.models.requests.admin.sms_template_cs.AddTemplateRequest;
import com.asset.ccat.gateway.models.requests.admin.sms_template_cs.DeleteTemplateRequest;
import com.asset.ccat.gateway.models.requests.admin.sms_template_cs.GetAllTemplatesRequest;
import com.asset.ccat.gateway.models.requests.admin.sms_template_cs.UpdateTemplateRequest;
import com.asset.ccat.gateway.models.responses.admin.sms_template_cs.GetAllSmsTemplatesResponse;
import com.asset.ccat.gateway.proxy.admin.SmsTemplateCSProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Assem.Hassan
 */
@Service
public class SmsTemplateCSService {

    @Autowired
    SmsTemplateCSProxy smsTemplateProxy;

    public GetAllSmsTemplatesResponse getAllSmsTemplates(GetAllTemplatesRequest request) throws GatewayException {
        return smsTemplateProxy.getAllSmsTemplates(request);
    }

    public void addSmsTemplates(AddTemplateRequest request) throws GatewayException {
        smsTemplateProxy.addSmsTemplates(request);
    }

    public void updateSmsTemplates(UpdateTemplateRequest request) throws GatewayException {
        smsTemplateProxy.updateSmsTemplates(request);
    }

    public void deleteSmsTemplates(DeleteTemplateRequest request) throws GatewayException {
        smsTemplateProxy.deleteSmsTemplates(request);
    }
}