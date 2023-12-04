/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.tasks;

import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.managers.LookupManager;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahmoud Shehab
 */
@Component
public class RefreshLookupsTask implements Runnable {

    private final LookupManager lookupManager;

    public RefreshLookupsTask(LookupManager lookupManager) {
        this.lookupManager = lookupManager;
    }

    @Override
    public void run() {
        try {
            CCATLogger.DEBUG_LOGGER.info("Start refresh Task");
            lookupManager.refreshLookups();
            CCATLogger.DEBUG_LOGGER.info("Finished refresh Task");
        } catch (Throwable th) {
            CCATLogger.DEBUG_LOGGER.error("Unknown Error Occurred In Refresh Task");
            CCATLogger.ERROR_LOGGER.error("Unknown Error Occurred In Refresh Task", th);
        }
    }

}
