package com.asset.ccat.gateway.validators.customer_care;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.offer.DeleteOfferRequest;
import com.asset.ccat.gateway.models.requests.offer.GetOfferRequest;
import com.asset.ccat.gateway.models.requests.offer.OfferRequest;
import java.util.Objects;
import org.springframework.stereotype.Component;

/**
 *
 * @author nour.ihab
 */
@Component
public class OfferValidator {

    public void validateGetAllOffers(GetOfferRequest getOfferRequest) throws GatewayException {
        if (Objects.isNull(getOfferRequest.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        }
    }

    public void validateOffer(OfferRequest addOfferRequest) throws GatewayException {
        if (Objects.isNull(addOfferRequest.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        } else if (Objects.isNull(addOfferRequest.getOffer().getOfferDesc())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "offerDesc");
        } else if (Objects.isNull(addOfferRequest.getOffer().getOfferId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "offerId");
        } else if (Objects.isNull(addOfferRequest.getOffer().getOfferType())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "offerType");
        } else if (Objects.isNull(addOfferRequest.getOffer().getOfferTypeId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "offerTypeId");
        }

    }

    public void validateDeleteOffer(DeleteOfferRequest deleteOfferRequest) throws GatewayException {
        if (Objects.isNull(deleteOfferRequest.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        } else if (Objects.isNull(deleteOfferRequest.getOfferId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "offerId");
        }
    }

}
