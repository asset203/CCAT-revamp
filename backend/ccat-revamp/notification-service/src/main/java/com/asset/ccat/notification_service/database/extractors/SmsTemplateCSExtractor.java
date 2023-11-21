package com.asset.ccat.notification_service.database.extractors;


import com.asset.ccat.notification_service.exceptions.NotificationException;
import com.asset.ccat.notification_service.models.SmsTemplateParamModel;
import com.asset.ccat.notification_service.utils.Utils;
import com.asset.ccat.notification_service.configurations.Properties;
import com.asset.ccat.notification_service.defines.DatabaseStructs;
import com.asset.ccat.notification_service.models.SmsTemplateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Assem.Hassan
 */
@Component
public class SmsTemplateCSExtractor implements ResultSetExtractor<List<SmsTemplateModel>> {

    @Autowired
    private Utils utils;

    @Autowired
    private Properties properties;

    @Override
    public List<SmsTemplateModel> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<SmsTemplateModel> smsTemplateList = new ArrayList<>();
        while (resultSet.next()) {
            SmsTemplateModel smsTemplateModel = new SmsTemplateModel();
            smsTemplateModel.setId(resultSet.getInt(DatabaseStructs.ADM_SMS_TEMPLATE.ID));
            smsTemplateModel.setTemplateId(resultSet.getInt(DatabaseStructs.ADM_SMS_TEMPLATE.TEMPLATE_ID));
            String smsText = resultSet.getString(DatabaseStructs.ADM_SMS_TEMPLATE.TEMPLATE_TEXT);
            smsTemplateModel.setTemplateParameters(resultSet.getString(DatabaseStructs.ADM_SMS_TEMPLATE.TEMPLATE_PARAMETERS));
            smsText = utils.decodeString(smsText, properties.getSmsDestEndcoding(), properties.getSmsSrcEndcoding());
            smsTemplateModel.setTemplateText(smsText);
            smsTemplateModel.setCsTemplateId(resultSet.getInt(DatabaseStructs.ADM_SMS_TEMPLATE.CS_TEMPLATE_ID));
            smsTemplateModel.setActionName(resultSet.getString(DatabaseStructs.LK_SMS_ACTION.ACTION_NAME));
            smsTemplateModel.setLanguageId(resultSet.getInt(DatabaseStructs.ADM_SMS_TEMPLATE.LANGUAGE_ID));
            smsTemplateModel.setLanguageName(resultSet.getString(DatabaseStructs.LK_LANGUAGES.NAME));
            List<SmsTemplateParamModel> parameterList = null;
            try {
                parameterList = utils.smsParametersParser(smsTemplateModel.getTemplateParameters());
            } catch (NotificationException e) {
                throw new RuntimeException(e);
            }
            smsTemplateModel.setParameterList(parameterList);
            smsTemplateList.add(smsTemplateModel);
        }
        return smsTemplateList;
    }
}
