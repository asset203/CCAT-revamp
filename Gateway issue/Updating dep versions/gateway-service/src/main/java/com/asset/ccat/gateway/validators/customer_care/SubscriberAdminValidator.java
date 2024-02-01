/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.validators.customer_care;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.SubscriberRequest;
import com.asset.ccat.gateway.util.GatewayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahmoud Shehab
 */
@Component
public class SubscriberAdminValidator {

    @Autowired
    GatewayUtil gatewayUtil;

    public void validateGetAccount(SubscriberRequest balanceAndDateRequest) throws GatewayException {
        if (balanceAndDateRequest.getMsisdn() == null
                || balanceAndDateRequest.getMsisdn().isBlank()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        } else if (!gatewayUtil.isNumeric(balanceAndDateRequest.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.INVALID_INPUT, "msisdn");
        }
    }

}
