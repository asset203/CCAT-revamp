package com.asset.ccat.notification_service.controllers.admin;

import com.asset.ccat.notification_service.defines.Defines;
import com.asset.ccat.notification_service.defines.ErrorCodes;
import com.asset.ccat.notification_service.exceptions.NotificationException;
import com.asset.ccat.notification_service.logger.CCATLogger;
import com.asset.ccat.notification_service.models.SmsTemplateModel;
import com.asset.ccat.notification_service.models.requests.sms_template_cs.AddTemplateRequest;
import com.asset.ccat.notification_service.models.requests.sms_template_cs.DeleteTemplateRequest;
import com.asset.ccat.notification_service.models.requests.sms_template_cs.GetAllTemplatesRequest;
import com.asset.ccat.notification_service.models.requests.sms_template_cs.UpdateTemplateRequest;
import com.asset.ccat.notification_service.models.responses.BaseResponse;
import com.asset.ccat.notification_service.models.responses.sms_template_cs.GetAllSmsTemplatesResponse;
import com.asset.ccat.notification_service.services.SmsTemplateCSService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Assem.Hassan
 */
@RestController
@RequestMapping(value = Defines.ContextPaths.SMS_TEMPLATE_CS)
public class SmsTemplateCSController {

    @Autowired
    private SmsTemplateCSService smsTemplateCSService;

    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllSmsTemplatesResponse> getAllSmsTemplate(@RequestBody GetAllTemplatesRequest request) throws NotificationException {
        CCATLogger.DEBUG_LOGGER.info("Started Getting All SmsTemplate");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        GetAllSmsTemplatesResponse response = smsTemplateCSService.getAllSmsTemplate();
        CCATLogger.DEBUG_LOGGER.info("All SmsTemplate are Retrieved Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse<SmsTemplateModel> addSmsTemplate(@RequestBody AddTemplateRequest request) throws NotificationException {
        CCATLogger.DEBUG_LOGGER.info("Started Adding SmsTemplate");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        smsTemplateCSService.addSmsTemplate(request);
        CCATLogger.DEBUG_LOGGER.info("SmsTemplate are Added Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse<SmsTemplateModel> updateSmsTemplate(@RequestBody UpdateTemplateRequest request) throws NotificationException {
        CCATLogger.DEBUG_LOGGER.info("Started Updating SmsTemplate");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        smsTemplateCSService.updateSmsTemplate(request);
        CCATLogger.DEBUG_LOGGER.info("SmsTemplate are Updated Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse<SmsTemplateModel> deleteSmsTemplate(@RequestBody DeleteTemplateRequest request) throws NotificationException {
        CCATLogger.DEBUG_LOGGER.info("Started Deleting SmsTemplate");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        smsTemplateCSService.deleteSmsTemplate(request);
        CCATLogger.DEBUG_LOGGER.info("SmsTemplate are Deleted Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                null);
    }
}
