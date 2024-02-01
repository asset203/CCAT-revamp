package com.asset.ccat.gateway.validators.admins;


import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;

import com.asset.ccat.gateway.models.requests.admin.service_offering_description.UpdateServiceOfferingBitDescRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ServiceOfferingDescValidator {

    public void validateUpdateServiceOfferingDesc(UpdateServiceOfferingBitDescRequest updateServiceOfferingBitDescRequest) throws GatewayValidationException {

        if (Objects.isNull(updateServiceOfferingBitDescRequest.getServiceOffering())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "serviceOffering");
        }
        if (Objects.isNull(updateServiceOfferingBitDescRequest.getServiceOffering().getBitPosition())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "serviceOfferingBitPosition");
        }
        if (Objects.isNull(updateServiceOfferingBitDescRequest.getServiceOffering().getBitName())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "serviceOfferingBitName");
        }
    }
}
