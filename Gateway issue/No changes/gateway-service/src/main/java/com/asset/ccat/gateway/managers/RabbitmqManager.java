package com.asset.ccat.gateway.managers;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.rabbitmq.client.util.RabbitmqUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author marwa.elshawarby
 */
@Component
public class RabbitmqManager {

    @Autowired
    private RabbitmqUtil rabbitmqUtil;

    public void init() throws GatewayException {
        try {
            CCATLogger.DEBUG_LOGGER.debug("Start creating footprint queue...");
            // create footprint queue
            rabbitmqUtil.createNewQueue(Defines.RABBIT_MQ.FOOTPRINT_QUEUE, Defines.RABBIT_MQ.FOOTPRINT_QUEUE);
            CCATLogger.DEBUG_LOGGER.debug("Created footprint queue successfully ...");
            CCATLogger.DEBUG_LOGGER.debug("Start creating tx_adjustment queue...");
            rabbitmqUtil.createNewQueue(Defines.RABBIT_MQ.TX_ADJUSTMENT_QUEUE, Defines.RABBIT_MQ.TX_ADJUSTMENT_QUEUE);
            CCATLogger.DEBUG_LOGGER.debug("Created tx_adjustment queue successfully ...");
            CCATLogger.DEBUG_LOGGER.debug("Start creating tx_login queue...");
            rabbitmqUtil.createNewQueue(Defines.RABBIT_MQ.TX_LOGIN_QUEUE, Defines.RABBIT_MQ.TX_LOGIN_QUEUE);
            CCATLogger.DEBUG_LOGGER.debug("Created tx_login queue successfully ...");
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Rabbitmq init method failed with error: " + ex.getMessage());
            CCATLogger.ERROR_LOGGER.error("Rabbitmq init method failed", ex);
            throw new GatewayException(ErrorCodes.ERROR.RABBITMQ_INIT_FAILED, Defines.SEVERITY.FATAL);
        }

    }

}
