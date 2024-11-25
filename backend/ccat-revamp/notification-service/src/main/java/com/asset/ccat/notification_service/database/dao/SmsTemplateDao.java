package com.asset.ccat.notification_service.database.dao;


import com.asset.ccat.notification_service.utils.Utils;
import com.asset.ccat.notification_service.configurations.Properties;
import com.asset.ccat.notification_service.database.extractors.SmsTemplateCSExtractor;
import com.asset.ccat.notification_service.defines.DatabaseStructs;
import com.asset.ccat.notification_service.defines.Defines;
import com.asset.ccat.notification_service.defines.ErrorCodes;
import com.asset.ccat.notification_service.exceptions.NotificationException;
import com.asset.ccat.notification_service.logger.CCATLogger;
import com.asset.ccat.notification_service.models.SmsTemplateModel;
import com.asset.ccat.notification_service.models.requests.sms_template_cs.AddTemplateRequest;
import com.asset.ccat.notification_service.models.requests.sms_template_cs.DeleteTemplateRequest;
import com.asset.ccat.notification_service.models.requests.sms_template_cs.UpdateTemplateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * @author Assem.Hassan
 */
@Repository
public class SmsTemplateDao {

    @Autowired
    private Utils utils;

    @Autowired
    private Properties properties;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SmsTemplateCSExtractor smsTemplateCsExtractor;

    private String getAllSmsTemplateQuery;
    private String insertSmsTemplateQuery;
    private String updateSmsTemplateQuery;
    private String deleteSmsTemplateQuery;


    public List<SmsTemplateModel> getAllSmsTemplate() throws NotificationException {
        try {

            if (Objects.isNull(getAllSmsTemplateQuery)) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT * ")
                        .append(" FROM ")
                        .append(DatabaseStructs.ADM_SMS_TEMPLATE.TABLE_NAME)
                        .append(" INNER JOIN ")
                        .append(DatabaseStructs.LK_LANGUAGES.TABLE_NAME)
                        .append(" ON ")
                        .append(DatabaseStructs.ADM_SMS_TEMPLATE.TABLE_NAME).append(".")
                        .append(DatabaseStructs.ADM_SMS_TEMPLATE.LANGUAGE_ID)
                        .append("=")
                        .append(DatabaseStructs.LK_LANGUAGES.TABLE_NAME).append(".")
                        .append(DatabaseStructs.LK_LANGUAGES.ID)
                        .append(" INNER JOIN ")
                        .append(DatabaseStructs.LK_SMS_ACTION.TABLE_NAME)
                        .append(" ON ")
                        .append(DatabaseStructs.ADM_SMS_TEMPLATE.TABLE_NAME).append(".")
                        .append(DatabaseStructs.ADM_SMS_TEMPLATE.TEMPLATE_ID)
                        .append("=")
                        .append(DatabaseStructs.LK_SMS_ACTION.TABLE_NAME).append(".")
                        .append(DatabaseStructs.LK_SMS_ACTION.SMS_ACTION_ID)
                        .append(" ORDER BY ")
                        .append(DatabaseStructs.ADM_SMS_TEMPLATE.TABLE_NAME).append(".")
                        .append(DatabaseStructs.ADM_SMS_TEMPLATE.TEMPLATE_ID)
                        .append(",")
                        .append(DatabaseStructs.ADM_SMS_TEMPLATE.TABLE_NAME).append(".")
                        .append(DatabaseStructs.ADM_SMS_TEMPLATE.LANGUAGE_ID);

                getAllSmsTemplateQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("getAllSmsTemplate = " + getAllSmsTemplateQuery);

            return jdbcTemplate.query(getAllSmsTemplateQuery, smsTemplateCsExtractor);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.debug("Exception in getAllSmsTemplate \n" + ex);
            CCATLogger.ERROR_LOGGER.error("Exception in getAllSmsTemplate ", ex);
            throw new NotificationException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    public boolean addSmsTemplate(AddTemplateRequest request) throws NotificationException {
        boolean isAdded;
        try {
            if (Objects.isNull(insertSmsTemplateQuery)) {
                StringBuilder query = new StringBuilder("");
                query.append("INSERT INTO ").append(DatabaseStructs.ADM_SMS_TEMPLATE.TABLE_NAME)
                        .append("(")
                        .append(DatabaseStructs.ADM_SMS_TEMPLATE.ID).append(", ")
                        .append(DatabaseStructs.ADM_SMS_TEMPLATE.TEMPLATE_ID).append(", ")
                        .append(DatabaseStructs.ADM_SMS_TEMPLATE.LANGUAGE_ID).append(", ")
                        .append(DatabaseStructs.ADM_SMS_TEMPLATE.CS_TEMPLATE_ID).append(", ")
                        .append(DatabaseStructs.ADM_SMS_TEMPLATE.TEMPLATE_TEXT).append(", ")
                        .append(DatabaseStructs.ADM_SMS_TEMPLATE.TEMPLATE_PARAMETERS)
                        .append(") VALUES (")
                        .append(DatabaseStructs.SEQUENCE.S_SMS_TEMPLATE)
                        .append(".NEXTVAL,?,?,?,?,?)");
                insertSmsTemplateQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("insertSmsTemplateQuery = " + insertSmsTemplateQuery);
            isAdded = jdbcTemplate.update(insertSmsTemplateQuery,
                    request.getSmsTemplate().getTemplateId(),
                    request.getSmsTemplate().getLanguageId(),
                    request.getSmsTemplate().getCsTemplateId(),
                    request.getSmsTemplate().getTemplateText(),
                    request.getSmsTemplate().getTemplateParameters()) != 0;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.debug("Exception in addSmsTemplate \n" + ex);
            CCATLogger.ERROR_LOGGER.error("Exception in addSmsTemplate ", ex);
            throw new NotificationException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
        return isAdded;
    }

    public boolean updateSmsTemplate(UpdateTemplateRequest request) throws NotificationException {
        boolean isUpdated;
        try {
            if (Objects.isNull(updateSmsTemplateQuery)) {
                StringBuilder query = new StringBuilder("");
                query.append("UPDATE ").append(DatabaseStructs.ADM_SMS_TEMPLATE.TABLE_NAME)
                        .append(" SET ")
                        .append(DatabaseStructs.ADM_SMS_TEMPLATE.TEMPLATE_ID).append(" = ? ,")
                        .append(DatabaseStructs.ADM_SMS_TEMPLATE.LANGUAGE_ID).append(" = ? ,")
                        .append(DatabaseStructs.ADM_SMS_TEMPLATE.CS_TEMPLATE_ID).append(" = ? ,")
                        .append(DatabaseStructs.ADM_SMS_TEMPLATE.TEMPLATE_TEXT).append(" = ? ,")
                        .append(DatabaseStructs.ADM_SMS_TEMPLATE.TEMPLATE_PARAMETERS).append(" = ? ")
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_SMS_TEMPLATE.ID).append(" = ? ");
                updateSmsTemplateQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("insertSmsTemplateQuery = " + updateSmsTemplateQuery);
            isUpdated = jdbcTemplate.update(updateSmsTemplateQuery,
                    request.getSmsTemplate().getTemplateId(),
                    request.getSmsTemplate().getLanguageId(),
                    request.getSmsTemplate().getCsTemplateId(),
                    request.getSmsTemplate().getTemplateText(),
                    request.getSmsTemplate().getTemplateParameters(),
                    request.getSmsTemplate().getId()) != 0;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.debug("Exception in updateSmsTemplate \n" + ex);
            CCATLogger.ERROR_LOGGER.error("Exception in updateSmsTemplate ", ex);
            throw new NotificationException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }

        return isUpdated;
    }

    public boolean deleteSmsTemplate(DeleteTemplateRequest request) throws NotificationException {
        boolean isDeleted;
        try {
            if (Objects.isNull(deleteSmsTemplateQuery)) {
                StringBuilder query = new StringBuilder();
                query.append("DELETE FROM ")
                        .append(DatabaseStructs.ADM_SMS_TEMPLATE.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_SMS_TEMPLATE.ID).append(" = ? ")
                        .append(" AND ")
                        .append(DatabaseStructs.ADM_SMS_TEMPLATE.TEMPLATE_ID).append(" = ?");
                deleteSmsTemplateQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("deleteSmsTemplateQuery = " + deleteSmsTemplateQuery);
            isDeleted = jdbcTemplate.update(deleteSmsTemplateQuery, request.getId(), request.getTemplateId()) != 0;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.debug("Exception in deleteSmsTemplate \n" + ex);
            CCATLogger.ERROR_LOGGER.error("Exception in deleteSmsTemplate ", ex);
            throw new NotificationException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
        return isDeleted;
    }

    public List<SmsTemplateModel> getSmsTemplatesForValidation() throws NotificationException {
        try {
            StringBuilder query = new StringBuilder("");
            query.append("SELECT * ")
                    .append(" FROM ")
                    .append(DatabaseStructs.ADM_SMS_TEMPLATE.TABLE_NAME);
            CCATLogger.DEBUG_LOGGER.debug("getSmsTemplatesForValidationQuery = " + query);

            return jdbcTemplate.query(query.toString(), new BeanPropertyRowMapper<>(SmsTemplateModel.class));
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.debug("Exception in getSmsTemplatesForValidation \n" + ex);
            CCATLogger.ERROR_LOGGER.error("Exception in getSmsTemplatesForValidation ", ex);
            throw new NotificationException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }
}
