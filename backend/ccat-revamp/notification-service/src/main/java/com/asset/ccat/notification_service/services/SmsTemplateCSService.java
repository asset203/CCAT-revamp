package com.asset.ccat.notification_service.services;

import com.asset.ccat.notification_service.utils.Utils;
import com.asset.ccat.notification_service.database.dao.SmsTemplateDao;
import com.asset.ccat.notification_service.defines.Defines;
import com.asset.ccat.notification_service.defines.ErrorCodes;
import com.asset.ccat.notification_service.exceptions.NotificationException;
import com.asset.ccat.notification_service.logger.CCATLogger;
import com.asset.ccat.notification_service.models.SMSActionModel;
import com.asset.ccat.notification_service.models.SmsTemplateModel;
import com.asset.ccat.notification_service.models.SmsTemplateParamModel;
import com.asset.ccat.notification_service.models.requests.sms_template_cs.AddTemplateRequest;
import com.asset.ccat.notification_service.models.requests.sms_template_cs.DeleteTemplateRequest;
import com.asset.ccat.notification_service.models.requests.sms_template_cs.UpdateTemplateRequest;
import com.asset.ccat.notification_service.models.responses.sms_template_cs.GetAllSmsTemplatesResponse;
import com.asset.ccat.notification_service.proxy.SmsTemplateCSProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.asset.ccat.notification_service.configurations.Properties;

import java.util.*;

/**
 * @author Assem.Hassan
 */
@Service
public class SmsTemplateCSService {
    @Autowired
    private Utils utils;

    @Autowired
    private Properties properties;

    @Autowired
    private SmsTemplateDao smsTemplateDao;

    @Autowired
    private SmsTemplateCSProxy smsTemplateCSProxy;


    public List<SmsTemplateModel> listSmsTemplates() throws NotificationException {
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving all SmsTemplate from the lookup service.");
        List<SmsTemplateModel> smsTemplateList = smsTemplateCSProxy.getAllSmsTemplates();
        if (Objects.isNull(smsTemplateList) || smsTemplateList.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.error("No SmsTemplate Were Found!");
            throw new NotificationException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR, "listSmsTemplates");
        }
        CCATLogger.DEBUG_LOGGER.debug("Done retrieving all SmsTemplate with size[{}]", smsTemplateList.size());

        return smsTemplateList;
    }

    public List<SMSActionModel> listSmsActions() throws NotificationException {
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving all SmsActions from lookup.");
        List<SMSActionModel> smsActions = smsTemplateCSProxy.getAllSmsActions();
        if (Objects.isNull(smsActions) || smsActions.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.error("No SmsActions Were Found!");
            throw new NotificationException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR, "listSmsActions");
        }
        CCATLogger.DEBUG_LOGGER.debug("Done retrieving all SmsActions with size[" + smsActions.size() + "].");

        return smsActions;
    }

    public GetAllSmsTemplatesResponse getAllSmsTemplate() throws NotificationException {
        CCATLogger.DEBUG_LOGGER.debug("SmsTemplateCSService - getAllSmsTemplate()");
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving all SmsTemplate.");
        List<SmsTemplateModel> smsTemplateList = smsTemplateDao.getAllSmsTemplate();
        if (Objects.isNull(smsTemplateList) || smsTemplateList.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.error("No SmsTemplate Were Found!");
            throw new NotificationException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR, "getAllSmsTemplate");
        }
        CCATLogger.DEBUG_LOGGER.debug("Done retrieving all SmsTemplate with size[" + smsTemplateList.size() + "].");

        return new GetAllSmsTemplatesResponse(smsTemplateList);
    }

    public void addSmsTemplate(AddTemplateRequest request) throws NotificationException {
        CCATLogger.DEBUG_LOGGER.debug("SmsTemplateCSService - addSmsTemplate()");
        boolean isAdded;
        if (isSmsTemplateExist(request)) {
            CCATLogger.DEBUG_LOGGER.error("Failed to add SmsTemplate, SMS Template already exists!");
            throw new NotificationException(ErrorCodes.ERROR.SMS_TEMPLATE_ALREADY_EXISTS, Defines.SEVERITY.ERROR, "addSmsTemplate");
        } else {
            String templateText = request.getSmsTemplate().getTemplateText();
            String templateParameters = request.getSmsTemplate().getTemplateParameters();
            if(request.getSmsTemplate().getCsTemplateId() == null || request.getSmsTemplate().getCsTemplateId()!=0){
                templateParameters = setTemplateParameters(request.getSmsTemplate().getParameterList());
            }
            if (Objects.isNull(templateText) || templateText.isEmpty()) {
                templateText = templateParameters;
            }
            request.getSmsTemplate().setTemplateText(prepareSmsText(templateText));
            request.getSmsTemplate().setTemplateParameters(templateParameters);
            isAdded = smsTemplateDao.addSmsTemplate(request);
            if (!isAdded) {
                CCATLogger.DEBUG_LOGGER.error("Failed to Add SmsTemplate.");
                throw new NotificationException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, "addSmsTemplate");
            }
            CCATLogger.DEBUG_LOGGER.debug("Done Adding SmsTemplate");
        }
    }

    public void updateSmsTemplate(UpdateTemplateRequest request) throws NotificationException {
        CCATLogger.DEBUG_LOGGER.debug("SmsTemplateCSService - updateSmsTemplate()");
        boolean isUpdated;
        if (isSmsTemplateExist(request)) {
            CCATLogger.DEBUG_LOGGER.error("Failed to Update SmsTemplate, SMS Template already exists!");
            throw new NotificationException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, "updateSmsTemplate");
        } else {
            String templateText = request.getSmsTemplate().getTemplateText();

            String templateParameters = request.getSmsTemplate().getTemplateParameters();
            if(request.getSmsTemplate().getCsTemplateId()!=0){
                templateParameters = setTemplateParameters(request.getSmsTemplate().getParameterList());
            }
            if (Objects.isNull(templateText) || templateText.isEmpty()) {
                templateText = templateParameters;
            }
            request.getSmsTemplate().setTemplateText(prepareSmsText(templateText));
            request.getSmsTemplate().setTemplateParameters(templateParameters);
            isUpdated = smsTemplateDao.updateSmsTemplate(request);
            if (!isUpdated) {
                CCATLogger.DEBUG_LOGGER.error("Failed to Update SmsTemplate.");
                throw new NotificationException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, "updateSmsTemplate");
            }
            CCATLogger.DEBUG_LOGGER.debug("Done Updating SmsTemplate");
        }
    }

    public void deleteSmsTemplate(DeleteTemplateRequest request) throws NotificationException {
        CCATLogger.DEBUG_LOGGER.debug("SmsTemplateCSService - deleteSmsTemplate");
        CCATLogger.DEBUG_LOGGER.debug("Start deleting SmsTemplate with ID[" + request.getId() + "].");
        boolean isDeleted = smsTemplateDao.deleteSmsTemplate(request);
        if (!isDeleted) {
            CCATLogger.DEBUG_LOGGER.error("Failed to delete SmsTemplate.");
            throw new NotificationException(ErrorCodes.ERROR.DELETE_FAILED, Defines.SEVERITY.ERROR, "deleteSmsTemplate");
        }
        CCATLogger.DEBUG_LOGGER.debug("Done deleting SmsTemplate with ID[" + request.getId() + "].");
    }

    private String setTemplateParameters(List<SmsTemplateParamModel> parameterList) throws NotificationException {
        StringBuilder templateParams = new StringBuilder();
        try {
            if (checkParamsValidation(parameterList)) {
                for (SmsTemplateParamModel paramModel : parameterList) {
                    templateParams.append("$")
                            .append(paramModel.getSequenceId())
                            .append("$").append("=$")
                            .append(paramModel.getParameterName())
                            .append("$,");
                }
            }
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: setTemplateParameters() ");
            CCATLogger.ERROR_LOGGER.error("error while executing: setTemplateParameters() ", ex);
            throw new NotificationException(ErrorCodes.ERROR.INVALID_PARAMETER, Defines.SEVERITY.ERROR, ex.getMessage());
        }
        return templateParams.toString();
    }

    private boolean checkParamsValidation(List<SmsTemplateParamModel> parameterList) throws NotificationException {
        boolean isValid = true;
        String errorMessage;
        Set<Integer> list = new HashSet<>();
        if(parameterList.isEmpty()){
            CCATLogger.DEBUG_LOGGER.error("error while executing: setTemplateParameters() ");
            throw new NotificationException(ErrorCodes.ERROR.INVALID_PARAMETER, Defines.SEVERITY.ERROR);
        }
        for (SmsTemplateParamModel paramModel : parameterList) {
            if (paramModel.getSequenceId() > parameterList.size() || paramModel.getSequenceId().intValue()<= 0 ) {
                errorMessage = "Error in Parameters, Parameters are greater than or less than size.";
                throw new NotificationException(ErrorCodes.ERROR.INVALID_PARAMETER, Defines.SEVERITY.ERROR, errorMessage);
            }
            if (!list.add(paramModel.getSequenceId())) {
                errorMessage = "Error in Parameters, Parameters are duplicated.";
                throw new NotificationException(ErrorCodes.ERROR.INVALID_PARAMETER, Defines.SEVERITY.ERROR, errorMessage);
            }
        }
        return isValid;
    }

    private boolean isSmsTemplateExist(AddTemplateRequest request) throws NotificationException {
        boolean isExist = false;
        List<SmsTemplateModel> alreadyExistedList = smsTemplateDao.getSmsTemplatesForValidation();
        SmsTemplateModel requestModel = request.getSmsTemplate();
        for (SmsTemplateModel smsTemplate : alreadyExistedList) {
            if (smsTemplate.getTemplateId().equals(requestModel.getCsTemplateId()) &&
                    smsTemplate.getLanguageId().equals(requestModel.getLanguageId()) &&
                    smsTemplate.getCsTemplateId().equals(requestModel.getCsTemplateId())) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    private boolean isSmsTemplateExist(UpdateTemplateRequest request) throws NotificationException {
        boolean isExist = false;
        List<SmsTemplateModel> alreadyExistedList = smsTemplateDao.getSmsTemplatesForValidation();
        SmsTemplateModel requestModel = request.getSmsTemplate();
        for (SmsTemplateModel smsTemplate : alreadyExistedList) {
            if (smsTemplate.getTemplateId().equals(requestModel.getCsTemplateId()) &&
                    smsTemplate.getLanguageId().equals(requestModel.getLanguageId()) &&
                    smsTemplate.getCsTemplateId().equals(requestModel.getCsTemplateId())) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    private String prepareSmsText(String smsText) {
        smsText = smsText.replaceAll("'", "''");
        smsText = utils.decodeString(smsText, properties.getSmsSrcEndcoding(), properties.getSmsDestEndcoding());

        return smsText;
    }

    public Map<String, String> getAllLanguages() throws NotificationException {
        return smsTemplateCSProxy.getAllLanguages();
    }
}
