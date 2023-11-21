package com.asset.ccat.gateway.validators.admins;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.admin.locking_admin.LockingAdministrationRequest;
import com.asset.ccat.gateway.models.requests.admin.locking_admin.UnlockingAdministrationRequest;
import java.util.Objects;
import org.springframework.stereotype.Component;

/**
 *
 * @author nour.ihab
 */
@Component
public class LockingAdministrationValidator {

    public void validateUnlockAdmin(UnlockingAdministrationRequest unlockingAdministrationRequest) throws GatewayException {
        if (Objects.isNull(unlockingAdministrationRequest.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        } else if (Objects.isNull(unlockingAdministrationRequest.getUsername())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "username");
        }
    }

    public void validatelockAdmin(LockingAdministrationRequest unlockingAdministrationRequest) throws GatewayException {
        if (Objects.isNull(unlockingAdministrationRequest.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        } else if (Objects.isNull(unlockingAdministrationRequest.getUsername())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "username");
        } else if (Objects.isNull(unlockingAdministrationRequest.getDate())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "date");
        }
    }
}
