package com.asset.ccat.gateway.validators.admins;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.admin.sms_template_cs.AddTemplateRequest;
import com.asset.ccat.gateway.models.requests.admin.sms_template_cs.DeleteTemplateRequest;
import com.asset.ccat.gateway.models.requests.admin.sms_template_cs.UpdateTemplateRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Assem.hassan
 */
@Component
public class SmsTemplateCSValidator {


    public void validateSmsTemplate(AddTemplateRequest request) throws GatewayException {
        if (Objects.isNull(request.getSmsTemplate().getTemplateId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "templateId");
        } else if (Objects.isNull(request.getSmsTemplate().getLanguageId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "languageId");
        } else if (Objects.isNull(request.getSmsTemplate().getCsTemplateId()) &&
                Objects.isNull(request.getSmsTemplate().getTemplateText())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "csTemplateId and templateText");
        }
    }

    public void validateUpdateSmsTemplate(UpdateTemplateRequest request) throws GatewayException {

        if (Objects.isNull(request.getSmsTemplate().getId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "Id");
        } else if (Objects.isNull(request.getSmsTemplate().getTemplateId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "templateId");
        } else if (Objects.isNull(request.getSmsTemplate().getLanguageId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "languageId");
        } else if (Objects.isNull(request.getSmsTemplate().getCsTemplateId()) &&
                Objects.isNull(request.getSmsTemplate().getTemplateText())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "csTemplateId and templateText");
        }
    }

    public void validateDeleteSmsTemplate(DeleteTemplateRequest request) throws GatewayException {
        if (Objects.isNull(request.getId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "Id");
        } else if (Objects.isNull(request.getTemplateId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "templateId");
        }
    }
}
