package com.asset.ccat.gateway.services.admin;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.customer_care.prepaidVBP.PrepaidCheckSubscriptionRequest;
import com.asset.ccat.gateway.models.requests.customer_care.prepaidVBP.PrepaidVBPSubscriptionRequest;
import com.asset.ccat.gateway.models.requests.customer_care.prepaidVBP.PrepaidVBPUnsubscriptionRequest;
import com.asset.ccat.gateway.models.responses.customer_care.PrepaidVBPCheckResponse;
import com.asset.ccat.gateway.proxy.PrepaidVBPProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PrepaidVBPService {
    @Autowired
    private PrepaidVBPProxy prepaidVBPProxy;

    public void subscribeService(PrepaidVBPSubscriptionRequest prepaidVBPSubscriptionRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Start Calling SubscribeService");
        prepaidVBPSubscriptionRequest.setTransactionAmount(prepaidVBPSubscriptionRequest.getTransactionAmount().multiply(BigDecimal.valueOf(100)));
        prepaidVBPProxy.prepaidVBSubscription(prepaidVBPSubscriptionRequest);
        CCATLogger.DEBUG_LOGGER.info("End Calling SubscribeService");
    }
    public void unsubscribeService(PrepaidVBPUnsubscriptionRequest prepaidVBPUnsubscriptionRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Start Calling UnsubscribeService");
        prepaidVBPProxy.prepaidVBPUnsubscription(prepaidVBPUnsubscriptionRequest);
        CCATLogger.DEBUG_LOGGER.info("End Calling UnsubscribeService");

    }
    public PrepaidVBPCheckResponse checkSubscribeService(PrepaidCheckSubscriptionRequest prepaidCheckSubscriptionRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Start Calling checkSubscribeService");
        Boolean response = prepaidVBPProxy.prepaidVBPCheckSubscription(prepaidCheckSubscriptionRequest);
        CCATLogger.DEBUG_LOGGER.info("End Calling checkSubscribeService");
        return new PrepaidVBPCheckResponse(response);
    }

}
