/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.ods_service.tasks;

import com.asset.ccat.ods_service.logger.CCATLogger;
import com.asset.ccat.ods_service.managers.EngineManager;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahmoud Shehab
 */
@Component
public class RefreshLookupsTask implements Runnable {

    private final EngineManager lookupManager;

    public RefreshLookupsTask(EngineManager lookupManager) {
        this.lookupManager = lookupManager;
    }

    @Override
    public void run() {
        CCATLogger.DEBUG_LOGGER.info("Start refresh Task");
    }

}
