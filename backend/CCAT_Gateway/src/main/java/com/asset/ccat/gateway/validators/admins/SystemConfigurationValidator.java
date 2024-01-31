package com.asset.ccat.gateway.validators.admins;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.admin.system_config.UpdateConfigurationsRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SystemConfigurationValidator {
    public void validateUpdateSystemConfig(UpdateConfigurationsRequest updateRequest) throws GatewayException {
        if (Objects.isNull(updateRequest.getConfigurations()) || updateRequest.getConfigurations().isEmpty()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "configurations");
        }
    }
}
