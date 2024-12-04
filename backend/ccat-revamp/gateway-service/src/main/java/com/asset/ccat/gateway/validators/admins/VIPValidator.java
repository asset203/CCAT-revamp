package com.asset.ccat.gateway.validators.admins;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.util.GatewayUtil;
import org.springframework.stereotype.Component;

@Component
public class VIPValidator {
    private final GatewayUtil gatewayUtil;

    public VIPValidator(GatewayUtil gatewayUtil) {
        this.gatewayUtil = gatewayUtil;
    }

    public void validateMSISDN(String msisdn) throws GatewayValidationException {
        if(msisdn == null || msisdn.isEmpty())
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        if(!gatewayUtil.isMsisdnValid(msisdn))
            throw new GatewayValidationException(ErrorCodes.WARNING.INVALID_INPUT, "msisdn format");
    }

    public String formatMSISDN(String msisdn){
        if (msisdn.startsWith("020")) {
            return msisdn.substring(2);
        } else if (msisdn.startsWith("201")) {
            return msisdn.substring(2);
        } else if (msisdn.startsWith("01")) {
            return msisdn.substring(1);
        }
        return msisdn;
    }
}
