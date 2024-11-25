package com.asset.ccat.notification_service.services;


import com.asset.ccat.notification_service.configurations.Properties;
import com.asset.ccat.notification_service.database.dao.SmsDao;
import com.asset.ccat.notification_service.defines.ErrorCodes;
import com.asset.ccat.notification_service.exceptions.NotificationException;
import com.asset.ccat.notification_service.logger.CCATLogger;
import com.asset.ccat.notification_service.models.requests.SendSMSRequest;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class SendSMSService {

    private final SmsDao smsDao;
    private final ContactStrategyService contactStrategyService;
    private final Properties properties;

    public SendSMSService(SmsDao smsDao, ContactStrategyService contactStrategyService, Properties properties) {
        this.smsDao = smsDao;
        this.contactStrategyService = contactStrategyService;
        this.properties = properties;
    }

    public void sendSMS(SendSMSRequest request) throws NotificationException, SQLException {
        CCATLogger.DEBUG_LOGGER.debug("CS SMS Integration flag = {}", properties.getCsSmsIntegration());
        if (properties.getCsSmsIntegration() == 1)
            contactStrategyService.sendSms(request);
        else
            smsDao.sendSms(request);
        CCATLogger.DEBUG_LOGGER.debug("Done sending Sms");
    }
}

