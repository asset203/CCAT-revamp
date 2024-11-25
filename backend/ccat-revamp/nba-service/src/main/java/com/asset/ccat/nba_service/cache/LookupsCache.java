/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.nba_service.cache;

import com.asset.ccat.nba_service.exceptions.NBAException;
import com.asset.ccat.nba_service.logger.CCATLogger;
import com.asset.ccat.nba_service.models.shared.NBAServer;
import com.asset.ccat.nba_service.services.LookupsService;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahmoud Shehab
 */
@Component
public class LookupsCache {

    private List<NBAServer> airServer;

    private final ReentrantLock reentrantLock = new ReentrantLock();

    @Autowired
    LookupsService lookupService;

    public void init() throws NBAException {
        airServer = lookupService.getNBAServers();

    }

    public void setAirServer(List<NBAServer> airServer) {
        try {
            reentrantLock.lock();
            this.airServer = airServer;
        } catch (Exception exception) {
            CCATLogger.INTERFACE_LOGGER.info("Exception while set nba servers");
            CCATLogger.INTERFACE_LOGGER.error("Exception while set nba servers", exception);
        } finally {
            reentrantLock.unlock();
        }
    }

    public NBAServer getAirServer() {
        try {
            reentrantLock.lock();
            Random random = new java.util.Random();
            int randomPos = random.nextInt(airServer.size());
            return airServer.get(randomPos);
        } catch (Exception exception) {
            CCATLogger.INTERFACE_LOGGER.info("Exception while get air server");
            CCATLogger.INTERFACE_LOGGER.error("Exception while get air servers", exception);
        } finally {
            reentrantLock.unlock();
        }
        return null;
    }
}
