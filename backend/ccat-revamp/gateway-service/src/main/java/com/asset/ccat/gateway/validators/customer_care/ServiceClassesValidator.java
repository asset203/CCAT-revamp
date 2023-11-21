package com.asset.ccat.gateway.validators.customer_care;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.service_class.ServiceClassRequest;

import java.util.Objects;

import org.springframework.stereotype.Component;

/**
 * @author nour.ihab
 */
@Component
public class ServiceClassesValidator {

    public void validateUpdateServiceClass(ServiceClassRequest serviceClassRequest) throws GatewayException {
        if (Objects.isNull(serviceClassRequest.getCurrentServiceClass().getCiPackageName())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "currentServiceClass-ciPackageName");
        } else if (Objects.isNull(serviceClassRequest.getCurrentServiceClass().getCiServiceName())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "currentServiceClass-ciServiceName");
        } else if (Objects.isNull(serviceClassRequest.getCurrentServiceClass().getCode())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "currentServiceClass-code");
        } else if (Objects.isNull(serviceClassRequest.getCurrentServiceClass().getIsAllowedMigration())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "currentServiceClass-isAllowedMigration");
        } else if (Objects.isNull(serviceClassRequest.getCurrentServiceClass().getIsCiConversion())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "currentServiceClass-isCiConversion");
        } else if (Objects.isNull(serviceClassRequest.getCurrentServiceClass().getName())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "currentServiceClass-name");
        } else if (Objects.isNull(serviceClassRequest.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msidn");
        } else if (Objects.isNull(serviceClassRequest.getNewServiceClass().getCiPackageName())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "newServiceClass-ciPackageName");
        } else if (Objects.isNull(serviceClassRequest.getNewServiceClass().getCiServiceName())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "newServiceClass-ciServiceName");
        } else if (Objects.isNull(serviceClassRequest.getNewServiceClass().getCode())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "newServiceClass-code");
        } else if (Objects.isNull(serviceClassRequest.getNewServiceClass().getIsAllowedMigration())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "newServiceClass-isAllowedMigration");
        } else if (Objects.isNull(serviceClassRequest.getNewServiceClass().getIsCiConversion())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "newServiceClass-isCiConversion");
        } else if (Objects.isNull(serviceClassRequest.getNewServiceClass().getName())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "newServiceClass-name");
        }
    }
}
