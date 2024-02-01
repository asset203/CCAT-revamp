/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.validators.customer_care;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.nba.AcceptGiftRequest;
import com.asset.ccat.gateway.models.nba.RejectGiftRequest;
import com.asset.ccat.gateway.models.nba.SendGiftRequest;
import java.util.Objects;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahmoud Shehab
 */
@Component
public class NBAValidator {

    public void validateAcceptRequest(AcceptGiftRequest acceptGiftRequest) throws GatewayException {
        if (acceptGiftRequest.getMsisdn() == null || acceptGiftRequest.getMsisdn().isBlank()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        } else if (Objects.isNull(acceptGiftRequest.getGiftShortCode())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "gift short code");
        }
    }
    
    public void validateSendSmsRequest(SendGiftRequest sendGiftRequest) throws GatewayException {
        if (sendGiftRequest.getMsisdn() == null || sendGiftRequest.getMsisdn().isBlank()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        } else if (Objects.isNull(sendGiftRequest.getGiftSeqId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "gift seq Id");
        }
    }
    
     public void validateRejectRequest(RejectGiftRequest rejectGiftRequest) throws GatewayException {
        if (rejectGiftRequest.getMsisdn() == null || rejectGiftRequest.getMsisdn().isBlank()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        } else if (Objects.isNull(rejectGiftRequest.getGiftSeqId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "gift seq Id");
        }
    }

}
