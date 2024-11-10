package com.asset.ccat.gateway.validators.customer_care;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.admin.batch_install_disconnect.BatchInstallRequest;
import com.asset.ccat.gateway.models.requests.customer_care.advanced.DisconnectSubscriberRequest;
import com.asset.ccat.gateway.models.requests.customer_care.advanced.InstallSubscriberRequest;
import com.asset.ccat.gateway.util.GatewayUtil;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author marwa.elshawarby
 */
@Component
public class AdvancedValidator {

    @Autowired
    private GatewayUtil gatewayUtil;

    public void validateSubscriberInstall(InstallSubscriberRequest installSubscriberRequest) throws GatewayException {
        if (Objects.isNull(installSubscriberRequest.getSubscriberMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "subscriberMsisdn");
        } else if (!gatewayUtil.isMsisdnValid(installSubscriberRequest.getSubscriberMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MUST_BE_MATCHED, "msisdn pattern");
        } else if (Objects.isNull(installSubscriberRequest.getBusinessPlanId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "getBusinessPlanId");
        }
    }

    public void validateSubscriberDisconnect(DisconnectSubscriberRequest disconnectSubscriberRequest) throws GatewayException {
        if (Objects.isNull(disconnectSubscriberRequest.getSubscriberMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "subscriberMsisdn");
        } else if (!gatewayUtil.isMsisdnValid(disconnectSubscriberRequest.getSubscriberMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MUST_BE_MATCHED, "msisdn pattern");
        } else if (Objects.isNull(disconnectSubscriberRequest.getDisconnectReasonId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "disconnectionReasonId");
        }
    }

}
