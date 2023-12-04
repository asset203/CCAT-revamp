package com.asset.ccat.gateway.validators.customer_care;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.customer_care.language.UpdateLanguageRequest;
import java.util.Objects;
import org.springframework.stereotype.Component;

/**
 *
 * @author nour.ihab
 */
@Component
public class LanguageValidation {

    public void validateUpdateLanguage(UpdateLanguageRequest updateLanguageRequest) throws GatewayException {
        if (Objects.isNull(updateLanguageRequest.getLanguageId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "languageId");
        } else if (Objects.isNull(updateLanguageRequest.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        } else if (Objects.isNull(updateLanguageRequest.getUsername())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "username");
        }
    }
}
