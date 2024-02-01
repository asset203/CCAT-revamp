package com.asset.ccat.gateway.validators.customer_care;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.customer_care.history.GetSubscriberActivitiesRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AccountHistoryValidator {

    public void validateGetSubscriberActivities(GetSubscriberActivitiesRequest request) throws GatewayException {
        if (Objects.isNull(request.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        } else if (Objects.isNull(request.getFetchCount())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "fetchCount");
        } else if (Objects.isNull(request.getOffset())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "offset");
        } else if (request.getIsGetAll() != null && request.getIsGetAll()) {
            if (Objects.isNull(request.getDateFrom())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "dateFrom");
            } else if (Objects.isNull(request.getDateTo())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "dateTo");
            }
        }
    }
}
