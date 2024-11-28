package com.asset.usagesimulator.managers;

import com.asset.usagesimulator.loggers.AppLogger;
import com.asset.usagesimulator.services.BonusAndDedicatedService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AppManager {
    private final BonusAndDedicatedService badService;

    public AppManager(BonusAndDedicatedService badService) {
        this.badService = badService;
    }

    @EventListener
    public void init(ContextRefreshedEvent event) throws JsonProcessingException {
        String msisdn = "1005871416";
        AppLogger.DEBUG_LOGGER.debug("Start insertion of BonusAndDedicatedAdjustment records of MSISDN = {}", msisdn);
        badService.insertBADRecord(msisdn);
        AppLogger.DEBUG_LOGGER.debug("End BonusAndDedicatedAdjustment");
    }
}
