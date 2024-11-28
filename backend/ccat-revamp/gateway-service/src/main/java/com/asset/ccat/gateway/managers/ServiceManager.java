/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.managers;

import com.asset.ccat.gateway.cache.LookupsCache;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.services.LookupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahmoud Shehab
 */
@Component
public class ServiceManager {

    @Autowired
    LookupsCache lookupsCache;

    @Autowired
    LookupsService lookupsService;

    @Autowired
    RabbitmqManager rabbitmqConfig;

    @EventListener
    public void startupEvent(ContextRefreshedEvent event) {
        try {
            CCATLogger.DEBUG_LOGGER.info("Start rabbitmq setup...");
            rabbitmqConfig.init();
            lookupsCache.setFootPrintPages(lookupsService.getFootPrintPages());
            CCATLogger.DEBUG_LOGGER.info("Rabbitmq setup finished successfully.");
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("ERROR while start gateway service ", ex);
            CCATLogger.ERROR_LOGGER.error("ERROR while start gateway service ", ex);
        }

    }
}
