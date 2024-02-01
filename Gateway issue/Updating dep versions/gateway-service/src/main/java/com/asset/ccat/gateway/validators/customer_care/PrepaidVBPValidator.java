package com.asset.ccat.gateway.validators.customer_care;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.mappers.CheckLimitMapper;
import com.asset.ccat.gateway.models.requests.customer_care.prepaidVBP.PrepaidCheckSubscriptionRequest;
import com.asset.ccat.gateway.models.requests.customer_care.prepaidVBP.PrepaidVBPSubscriptionRequest;
import com.asset.ccat.gateway.models.requests.customer_care.prepaidVBP.PrepaidVBPUnsubscriptionRequest;
import com.asset.ccat.gateway.services.CustomerBalancesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PrepaidVBPValidator {
    @Autowired
    private CustomerBalancesService service;
    @Autowired
    private CheckLimitMapper mapper;
    public void validateSubscribe(PrepaidVBPSubscriptionRequest prepaidVBPSubscriptionRequest) throws GatewayException {
        if (Objects.isNull(prepaidVBPSubscriptionRequest.getTransactionType())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "Transaction type");
        }else if (Objects.isNull(prepaidVBPSubscriptionRequest.getTransactionCode())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "Transaction code");
        }else if (Objects.isNull(prepaidVBPSubscriptionRequest.getTransactionAmount())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "Transaction amount");
        }
        CCATLogger.DEBUG_LOGGER.info("Starting Check Limit for Prepaid-VBP subscribe request ["+prepaidVBPSubscriptionRequest+" ]");
        service.checkLimit(mapper.mapFrom(prepaidVBPSubscriptionRequest));
        CCATLogger.DEBUG_LOGGER.info("Ending Check Limit Prepaid-VBP subscribe request ["+prepaidVBPSubscriptionRequest+" ]");

    }
    public void validateUnsubscribe(PrepaidVBPUnsubscriptionRequest prepaidVBPUnsubscriptionRequest) throws GatewayException {
        if (Objects.isNull(prepaidVBPUnsubscriptionRequest.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "Transaction type");
        }
    }
    public void validateCheckSubscribe(PrepaidCheckSubscriptionRequest prepaidCheckSubscriptionRequest) throws GatewayException {
        if (Objects.isNull(prepaidCheckSubscriptionRequest.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "Transaction type");
        }
    }
}
