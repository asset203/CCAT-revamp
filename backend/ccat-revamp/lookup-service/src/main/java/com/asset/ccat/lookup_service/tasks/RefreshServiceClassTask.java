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
public class RefreshServiceClassTask implements Runnable {

    private final LookupManager lookupManager;

    public RefreshServiceClassTask(LookupManager lookupManager) {
        this.lookupManager = lookupManager;
    }

    @Override
    public void run() {
        CCATLogger.DEBUG_LOGGER.info("Start refresh Task");
        lookupManager.refreshServiceClassLookups();
    }

}
