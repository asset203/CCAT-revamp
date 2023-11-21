package com.asset.ccat.history_service.tasks;

import com.asset.ccat.history_service.executors.FootprintExecutor;
import com.asset.ccat.history_service.logger.CCATLogger;
import com.asset.ccat.rabbitmq.models.FootprintModel;
import org.springframework.stereotype.Component;
import org.springframework.amqp.utils.SerializationUtils;

/**
 *
 * @author marwa.elshawarby
 */
@Component
public class FootprintDequeueTask {

    private final FootprintExecutor footprintExecutor;

    public FootprintDequeueTask(FootprintExecutor footprintExecutor) {
        this.footprintExecutor = footprintExecutor;
    }

    private void handleReceivedFootprint(byte[] body) {
        try {
            FootprintModel footprint = (FootprintModel) SerializationUtils.deserialize(body);
            footprintExecutor.execute(footprint);
        } catch (Throwable th) {
            CCATLogger.DEBUG_LOGGER.error("An exception caught in footprint dequeuer task connection to rabbitmq " + th.getMessage());
            CCATLogger.ERROR_LOGGER.error("An exception caught in footprint dequeuer task connection to rabbitmq ", th);
        }
    }

}
