package com.asset.ccat.gateway.validators.customer_care;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.customer_care.usage_counter.*;

import java.util.Objects;
import org.springframework.stereotype.Component;

/**
 *
 * @author nour.ihab
 */
@Component
public class UsageCountersValidator {

    public void validateGetAllUsageCounters(GetAllUsageCountersRequest getAllUsageCountersRequest) throws GatewayException {
        if (Objects.isNull(getAllUsageCountersRequest.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        }
    }

    public void validateAddUsageCounters(AddUsageCountersRequest addUsageCountersRequest) throws GatewayException {
        if (Objects.isNull(addUsageCountersRequest.getId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "id");
        } else if (Objects.isNull(addUsageCountersRequest.getUsageTypeId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "usageTypeId");
        } else if (Objects.isNull(addUsageCountersRequest.getValue())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "value");
        } else if (Objects.isNull(addUsageCountersRequest.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        }
    }

    public void validateAddUsageCountersAndThresholds(AddUsageCountersAndThresholdsRequest addUsageCountersAndThresholdsRequest) throws GatewayException {
        if (Objects.isNull(addUsageCountersAndThresholdsRequest.getId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "id");
        } else if (Objects.isNull(addUsageCountersAndThresholdsRequest.getUsageTypeId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "usageTypeId");
        } else if (Objects.isNull(addUsageCountersAndThresholdsRequest.getValue())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "value");
        } else if (Objects.isNull(addUsageCountersAndThresholdsRequest.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        } else if (Objects.isNull(addUsageCountersAndThresholdsRequest.getThresholds()) || addUsageCountersAndThresholdsRequest.getThresholds().isEmpty()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "thresholds");
        }
    }

    public void validateUpdateUsageCounters(UpdateUsageCountersRequest updateUsageCountersRequest) throws GatewayException {
        if (Objects.isNull(updateUsageCountersRequest.getId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "id");
        } else if (Objects.isNull(updateUsageCountersRequest.getCounterValue()) && Objects.isNull(updateUsageCountersRequest.getMonetaryValue1())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "value");
        } else if (Objects.isNull(updateUsageCountersRequest.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        }
    }

    public void validateUpdateUsageCountersAndThresholds(UpdateUsageCountersAndThresholdsRequest updateUsageCountersAndThresholdsRequest) throws GatewayException {
        if (Objects.isNull(updateUsageCountersAndThresholdsRequest.getId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "id");
        } else if (Objects.isNull(updateUsageCountersAndThresholdsRequest.getCounterValue()) && Objects.isNull(updateUsageCountersAndThresholdsRequest.getMonetaryValue1())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "value");
        } else if (Objects.isNull(updateUsageCountersAndThresholdsRequest.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        } else if (Objects.isNull(updateUsageCountersAndThresholdsRequest.getThresholds()) || updateUsageCountersAndThresholdsRequest.getThresholds().isEmpty()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "thresholds");
        }
    }

    public void validateDeleteUsageThresholds(DeleteUsageThresholdsRequest deleteUTRequest) throws GatewayException {
        if (Objects.isNull(deleteUTRequest.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        } else if (Objects.isNull(deleteUTRequest.getUsageThresholdsIds()) || deleteUTRequest.getUsageThresholdsIds().isEmpty()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "usageThresholdsIds");
        }
    }
}
