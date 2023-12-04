package com.asset.ccat.notification_service.database.dao;


import com.asset.ccat.notification_service.configurations.Properties;
import com.asset.ccat.notification_service.defines.Defines;
import com.asset.ccat.notification_service.defines.ErrorCodes;
import com.asset.ccat.notification_service.exceptions.NotificationException;
import com.asset.ccat.notification_service.logger.CCATLogger;

import com.asset.ccat.notification_service.models.requests.SendSMSRequest;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

@Repository
public class SmsDao {


    private final JdbcTemplate jdbcTemplate;

    private final Properties properties;

    public SmsDao(JdbcTemplate jdbcTemplate, Properties properties) {
        this.jdbcTemplate = jdbcTemplate;
        this.properties = properties;
    }


    public void sendSms(SendSMSRequest request) throws NotificationException, SQLException {
        CCATLogger.DEBUG_LOGGER.debug("msisdn: " + request.getMsisdn()
                + " Starting SMSDAO - sendSMS for ActionName " + request.getActionName() + " ,templateLangId= " + request.getTemplateLanguageId());

        try {
            Integer defaultSMSLanguage = properties.getDefaultLanguage();
            String procedureName = properties.getSendSmsProcedureName();

            Integer ccatLangId = request.getTemplateLanguageId();
            if (request.getTemplateLanguageId() != null) {
                CCATLogger.DEBUG_LOGGER.debug("Template Language ID sent Not Null, Start Mapping to ccat Language");

            } else {
                CCATLogger.DEBUG_LOGGER.debug("Template Language ID is Null, default value will be used default= " + defaultSMSLanguage);
                ccatLangId = defaultSMSLanguage;
            }
            OracleCallableStatement clst = null;

            Connection con = jdbcTemplate.getDataSource().getConnection().unwrap(OracleConnection.class);

            //    CallableStatement cs = con.prepareCall("{call SEND_SMS(?,?,?)}");
            DBObject msisdnParam = new DBObject("msisdn", request.getMsisdn());
            DBObject actionNameParam = new DBObject("Action_Name", request.getActionName());
            DBObject templateLangParam = new DBObject("template_lang", String.valueOf(ccatLangId));
            DBObject[] smsInfo = {msisdnParam, actionNameParam, templateLangParam};
            ArrayDescriptor smsInfoDescriptor = ArrayDescriptor.createDescriptor("DB_ARRAY", con);
            ARRAY smsInfoArray = new ARRAY(smsInfoDescriptor, con, smsInfo);
            //Prepare Parameters to send to procedure
            ArrayList<DBObject> templateParametersList = new ArrayList<DBObject>();
            for (Map.Entry<String, String> entry : request.getTemplateParamMap().entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                DBObject param = new DBObject(key, value);
                templateParametersList.add(param);
            }
            ArrayDescriptor templateInfoDescriptor = ArrayDescriptor.createDescriptor("DB_ARRAY", con);
            ARRAY templateInfoArray = new ARRAY(templateInfoDescriptor, con, templateParametersList.toArray());

            clst = (OracleCallableStatement) con.prepareCall("{CALL " + procedureName + "(?,?,?)}");
            clst.setArray(1, smsInfoArray);
            clst.setArray(2, templateInfoArray);
            clst.registerOutParameter(3, OracleTypes.NUMERIC);
            clst.executeUpdate();
            int eCode = clst.getInt(3);
            CCATLogger.DEBUG_LOGGER.debug("msisdn: " + request.getMsisdn() + " SMSDAO - insertSMS procedure returned eCode [" + eCode + "] - ");
            if (eCode != ErrorCodes.SUCCESS.SUCCESS) {
                String errorMessage = "Failed to insertSMS due to an error [Error code : " + eCode + "] ";
                throw new NotificationException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, errorMessage);
            }
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.debug("Exception in sendSMS \n" + ex);
            CCATLogger.ERROR_LOGGER.error("Exception in senSMS ", ex);
            throw new NotificationException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }

    }





}

