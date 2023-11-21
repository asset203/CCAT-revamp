package com.asset.ccat.notification_service.utils;

import com.asset.ccat.notification_service.configurations.Properties;
import com.asset.ccat.notification_service.defines.Defines;
import com.asset.ccat.notification_service.defines.ErrorCodes;
import com.asset.ccat.notification_service.exceptions.NotificationException;
import com.asset.ccat.notification_service.logger.CCATLogger;
import com.asset.ccat.notification_service.models.SmsTemplateParamModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.*;

@Component
public class Utils {

    @Autowired
    private Properties properties;


    public String decodeString(String arg_String, String srcLanguage, String destinationLanguage) {
        String encodedString = arg_String;
        try {
            if (Objects.nonNull(arg_String) && arg_String.isBlank()) {
                encodedString = new String(arg_String.getBytes(srcLanguage), destinationLanguage);
            }
        } catch (UnsupportedEncodingException e) {
            CCATLogger.DEBUG_LOGGER.error("Failed to decode string", e);
        }
        return encodedString;
    }

    public String replacePlaceholders(String originalText, HashMap<String, String> templateParamMap, String[] placeholders) {
        String replacedText = originalText;
        for (Map.Entry<String, String> entry : templateParamMap.entrySet()) {
            replacedText = replacedText.replace("$" + entry.getKey() + "$", entry.getValue());
        }
        return replacedText;
    }

    public List<SmsTemplateParamModel> smsParametersParser(String parametersString) throws NotificationException {
        try {
            List<SmsTemplateParamModel> parametersList = new ArrayList<>();
            if(Objects.nonNull(parametersString)){
                String[] splitParameters = parametersString.split(",");
                for(String parameter : splitParameters){
                    String sequenceId = parameter.split("=")[0].replace("$","");
                    String parameterName = parameter.split("=")[1].replace("$","");
                    SmsTemplateParamModel smsTemplateParamModel =  new SmsTemplateParamModel(null,parameterName,Integer.valueOf(sequenceId));
                    parametersList.add(smsTemplateParamModel);
                }
            }
            return parametersList;
        }catch (Exception ex){
            CCATLogger.DEBUG_LOGGER.error("smsParameter Parsing Failed : "+ex.getMessage());
            throw new NotificationException(ErrorCodes.ERROR.SMS_PARAMETERS_PARSING_FAILURE, Defines.SEVERITY.ERROR);
        }

    }
}
