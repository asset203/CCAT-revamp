package com.asset.ccat.gateway.controllers.admins;

import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.sms_template_cs.AddTemplateRequest;
import com.asset.ccat.gateway.models.requests.admin.sms_template_cs.DeleteTemplateRequest;
import com.asset.ccat.gateway.models.requests.admin.sms_template_cs.GetAllTemplatesRequest;
import com.asset.ccat.gateway.models.requests.admin.sms_template_cs.UpdateTemplateRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.sms_template_cs.GetAllSmsTemplatesResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.admin.SmsTemplateCSService;
import com.asset.ccat.gateway.validators.admins.SmsTemplateCSValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = Defines.ContextPaths.SMS_TEMPLATE_CS)
public class SmsTemplateCSController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private SmsTemplateCSService smsTemplateService;
    @Autowired
    private SmsTemplateCSValidator smsTemplateValidator;


    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllSmsTemplatesResponse> getAllSmsTemplates(@RequestBody GetAllTemplatesRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All SMS Templates Request [" + request + "]");
        GetAllSmsTemplatesResponse response = smsTemplateService.getAllSmsTemplates(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All SMS Templates Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    @LogFootprint
    public BaseResponse addSmsTemplates(@RequestBody AddTemplateRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Add SMS Templates Request [" + request + "]");
        smsTemplateValidator.validateSmsTemplate(request);
        smsTemplateService.addSmsTemplates(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Add SMS Templates Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    @LogFootprint
    public BaseResponse updateSmsTemplates(@RequestBody UpdateTemplateRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update SMS Templates Request [" + request + "]");
        smsTemplateValidator.validateUpdateSmsTemplate(request);
        smsTemplateService.updateSmsTemplates(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update SMS Templates Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    @LogFootprint
    public BaseResponse deleteSmsTemplates(@RequestBody DeleteTemplateRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Delete SMS Templates Request [" + request + "]");
        smsTemplateValidator.validateDeleteSmsTemplate(request);
        smsTemplateService.deleteSmsTemplates(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Delete SMS Templates Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }
}
