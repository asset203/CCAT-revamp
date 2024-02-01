package com.asset.ccat.gateway.validators.customer_care;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.customer_care.notepad.CreateNotePadRequest;
import com.asset.ccat.gateway.models.requests.customer_care.notepad.DeleteNotePadRequest;
import java.util.Objects;
import org.springframework.stereotype.Component;

/**
 *
 * @author nour.ihab
 */
@Component
public class NotepadValidator {

    public void validateCreateNotePad(CreateNotePadRequest createNotePadRequest) throws GatewayException {

        if (Objects.isNull(createNotePadRequest.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        } else if (Objects.isNull(createNotePadRequest.getEntry())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "entry");
        }
    }

    public void validateDeleteNotePad(DeleteNotePadRequest deleteNotePadRequest) throws GatewayException {
        if (Objects.isNull(deleteNotePadRequest.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        }
    }

}
