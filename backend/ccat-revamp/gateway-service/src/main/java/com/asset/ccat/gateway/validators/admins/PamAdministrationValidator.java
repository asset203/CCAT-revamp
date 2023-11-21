package com.asset.ccat.gateway.validators.admins;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.admin.pam_admin.AddPamRequest;
import com.asset.ccat.gateway.models.requests.admin.pam_admin.DeletePamRequest;
import com.asset.ccat.gateway.models.requests.admin.pam_admin.GetPamRequest;
import com.asset.ccat.gateway.models.requests.admin.pam_admin.UpdatePamRequest;
import java.util.Objects;
import org.springframework.stereotype.Component;

/**
 *
 * @author nour.ihab
 */
@Component
public class PamAdministrationValidator {

    public void validateGetPam(GetPamRequest getPamRequest) throws GatewayException {
        if (Objects.isNull(getPamRequest.getId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "id");
        }
    }

    public void validateUpdatePam(UpdatePamRequest updatePamRequest) throws GatewayException {
        if (Objects.isNull(updatePamRequest.getId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "id");
        } else if (Objects.isNull(updatePamRequest.getDescription())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "description");
        }
    }

    public void validateAddPam(AddPamRequest addPamRequest) throws GatewayException {
        if (Objects.isNull(addPamRequest.getPam().getId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "id");
        } else if (Objects.isNull(addPamRequest.getPam().getDescription())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "description");
        } else if (Objects.isNull(addPamRequest.getPam().getPamTypeId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "pamTypeId");
        }
    }

    public void validateDeletePam(DeletePamRequest deletePamRequest) throws GatewayException {
        if (Objects.isNull(deletePamRequest.getId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "id");
        } else if (Objects.isNull(deletePamRequest.getPamTypeId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "pamTypeId");
        }
    }

}
