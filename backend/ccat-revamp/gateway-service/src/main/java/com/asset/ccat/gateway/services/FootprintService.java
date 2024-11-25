package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.rabbitmq.models.FootprintModel;
import com.asset.rabbitmq.client.util.RabbitmqUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author marwa.elshawarby
 */
@Component
public class FootprintService {

    @Autowired
    private RabbitmqUtil rabbitmqUtil;

    public void enqueueFootprint(FootprintModel footprint) throws GatewayException {
        try {
            CCATLogger.DEBUG_LOGGER.debug("Start enqueuing footprint to rabbitmq");
            rabbitmqUtil.publishMsgToQueue(Defines.RABBIT_MQ.EXCHANGE_NAME, Defines.RABBIT_MQ.FOOTPRINT_QUEUE, footprint);
            CCATLogger.DEBUG_LOGGER.debug("Finished enqueuing footprint");
        }catch (RuntimeException ex) {
        	CCATLogger.ERROR_LOGGER.error("Failed to enqueue footprint", ex);
        }
        catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to enqueue footprint with error: " + ex.getMessage());
            CCATLogger.ERROR_LOGGER.error("Failed to enqueue footprint", ex);
            throw new GatewayException(ErrorCodes.ERROR.LOG_FOOTPRINT_FAILED, Defines.SEVERITY.ERROR);
        }
    }

}
