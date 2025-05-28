package com.asset.ccat.notification_service.services;


import com.asset.ccat.notification_service.cache.CachedLookups;
import com.asset.ccat.notification_service.configurations.Properties;
import com.asset.ccat.notification_service.defines.Defines;
import com.asset.ccat.notification_service.defines.ErrorCodes;
import com.asset.ccat.notification_service.exceptions.NotificationException;
import com.asset.ccat.notification_service.logger.CCATLogger;
import com.asset.ccat.notification_service.models.CSRequestModel;
import com.asset.ccat.notification_service.models.SmsTemplateModel;
import com.asset.ccat.notification_service.models.requests.SendSMSRequest;
import com.asset.ccat.notification_service.proxy.ContactStrategyProxy;
import com.asset.ccat.notification_service.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ContactStrategyService {

    private final Properties properties;
    private final SmsTemplateCSService smsTemplateCSService;
    private final ContactStrategyProxy contactStrategyProxy;
    private final CachedLookups cachedLookups;
    private final Utils utils;

    public ContactStrategyService(Properties properties, SmsTemplateCSService smsTemplateCSService, ContactStrategyProxy contactStrategyProxy, CachedLookups cachedLookups, Utils utils) {
        this.properties = properties;
        this.smsTemplateCSService = smsTemplateCSService;
        this.contactStrategyProxy = contactStrategyProxy;
        this.cachedLookups = cachedLookups;
        this.utils = utils;
    }

    public void sendSms(SendSMSRequest request) throws NotificationException {
        CSRequestModel requestModel = generateCSRequestModel(request);
        contactStrategyProxy.sendSmsRequest(requestModel);
    }

    private CSRequestModel generateCSRequestModel(SendSMSRequest request) throws NotificationException {
        CSRequestModel requestModel = new CSRequestModel();
        Map<String, Object> attributes = new HashMap<>();
        requestModel.setUrl(properties.getCsUrl());

        attributes.put(Defines.CONTACT_STRATEGY_PARAMETER.ORIGINATOR_MSISDN, properties.getSmsOriginator());
        attributes.put(Defines.CONTACT_STRATEGY_PARAMETER.ORIGINATOR_TYPE, properties.getOriginatorType());
        attributes.put(Defines.CONTACT_STRATEGY_PARAMETER.LANGUAGE, request.getTemplateLanguageId());
        attributes.put(Defines.CONTACT_STRATEGY_PARAMETER.DESTINATION_MSISDN, "20" + request.getMsisdn());
        attributes.put(Defines.CONTACT_STRATEGY_PARAMETER.SYSTEM_NAME, properties.getServiceName());
        attributes.put(Defines.CONTACT_STRATEGY_PARAMETER.SYSTEM_PASSWORD, properties.getServicePassword());
        attributes.put(Defines.CONTACT_STRATEGY_PARAMETER.MESSAGE_TYPE, properties.getMessageType());
        attributes.put(Defines.CONTACT_STRATEGY_PARAMETER.DO_NOT_APPLY, properties.getDoNotApply());

        setLanguageId(request, attributes);
        setMessageTemplateParameter(request, attributes);

        requestModel.setVariables(attributes);

        return requestModel;
    }

    private Boolean isArabicLanguage(String ccatLanguageId, Map<String, String> ccatLanguages) {
        for (Map.Entry<String, String> entry : ccatLanguages.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(ccatLanguageId)) {
               // return "Arabic".equals(entry.getKey());
                CCATLogger.DEBUG_LOGGER.debug("Language key: " + entry.getKey() + ", value: " + entry.getValue());
                return "Arabic".equals(entry.getValue());
            }
        }
        return false;
    }

    private void setLanguageId(SendSMSRequest request, Map<String, Object> attributes) throws NotificationException {
        Map<String, String> languages = smsTemplateCSService.getAllLanguages();
        Boolean isArabic = isArabicLanguage(request.getTemplateLanguageId() + "", languages);
        if (isArabic) {
            attributes.put(Defines.CONTACT_STRATEGY_PARAMETER.LANGUAGE, properties.getCsArabicLanguageId());
        } else {
            attributes.put(Defines.CONTACT_STRATEGY_PARAMETER.LANGUAGE, properties.getCsEnglishLanguageId());
        }

    }

    private void setMessageTemplateParameter(SendSMSRequest request, Map<String, Object> attributes) throws NotificationException {
        List<SmsTemplateModel> templates = smsTemplateCSService.listSmsTemplates();
//                cachedLookups.getTemplates();
//        Optional<SmsTemplateModel> optionalModel = templates.stream().filter(smsTemplateModel ->
//                        smsTemplateModel.getActionName().equals(request.getActionName())
//                        && smsTemplateModel.getLanguageId().equals(request.getTemplateLanguageId())).findFirst();
        SmsTemplateModel smsTemplateModel = null;
        CCATLogger.DEBUG_LOGGER.debug("templates size = {}", templates != null ? templates.size() : 0);
        for (SmsTemplateModel smsTemplate : templates){
            CCATLogger.DEBUG_LOGGER.debug("Temp's actionName = {} --- req action name = {}", smsTemplate.getActionName(), request.getActionName());
            if(request.getActionName().toLowerCase().equals(smsTemplate.getActionName().toLowerCase())
            &&request.getTemplateLanguageId().equals(smsTemplate.getLanguageId())){
                smsTemplateModel = smsTemplate;
                break;
            }
        }

        if (Objects.nonNull(smsTemplateModel)) {
            if (smsTemplateModel.getCsTemplateId() == null || smsTemplateModel.getCsTemplateId() == 0) {
                //String smsText = utils.replacePlaceholders(smsTemplateModel.getTemplateText(), sms);
                attributes.put(Defines.CONTACT_STRATEGY_PARAMETER.MESSAGE_TEXT, smsTemplateModel.getTemplateText());
            } else {
                attributes.put(Defines.CONTACT_STRATEGY_PARAMETER.TEMPLATES_IDS, smsTemplateModel.getCsTemplateId());
                attributes.put(Defines.CONTACT_STRATEGY_PARAMETER.TEMPLATES_PARAMETERS, "");
            }
        } else {
            throw new NotificationException(ErrorCodes.ERROR.NO_TEMPLATE_FOUND);
        }
    }
}
