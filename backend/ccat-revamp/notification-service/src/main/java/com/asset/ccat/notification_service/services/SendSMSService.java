package com.asset.ccat.notification_service.services;


import com.asset.ccat.notification_service.configurations.Properties;
import com.asset.ccat.notification_service.database.dao.SmsDao;
import com.asset.ccat.notification_service.defines.ErrorCodes;
import com.asset.ccat.notification_service.exceptions.NotificationException;
import com.asset.ccat.notification_service.logger.CCATLogger;
import com.asset.ccat.notification_service.models.requests.SendSMSRequest;
import org.springframework.stereotype.Service;

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

    public void sendSMS(SendSMSRequest request) throws NotificationException {
        CCATLogger.DEBUG_LOGGER.debug("SendSMSService - sendSMS");
        CCATLogger.DEBUG_LOGGER.info("Start sending Sms ");
        try {
            if (properties.getCsSmsIntegration() == 1)
                contactStrategyService.sendSms(request);
            else
                smsDao.sendSms(request);
        } catch (NotificationException ex){
            throw ex;
        }catch (Exception e) {
            throw new NotificationException(ErrorCodes.ERROR.DATABASE_ERROR);
        }
        CCATLogger.DEBUG_LOGGER.debug("Done sending Sms");
        CCATLogger.DEBUG_LOGGER.info(" SMS Sent Successfully");
    }
}

