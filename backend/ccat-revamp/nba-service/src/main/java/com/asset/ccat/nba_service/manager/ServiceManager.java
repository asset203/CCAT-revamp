/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.nba_service.manager;

import com.asset.ccat.nba_service.logger.CCATLogger;
import com.asset.ccat.nba_service.cache.LookupsCache;
import com.asset.ccat.nba_service.configurations.Properties;
import com.asset.ccat.nba_service.exceptions.NBAException;
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
    Properties Properties;

    @Autowired
    LookupsCache lookupsCache;

    @Autowired
    DataSourceManager dataSourceManager;

    @EventListener
    public void startupEvent(ContextRefreshedEvent event) throws Exception {

        dataSourceManager.init();
//        lookupsCache.init();

    }
}
