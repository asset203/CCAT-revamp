/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.nba_service.services;

import com.asset.ccat.nba_service.exceptions.NBAException;
import com.asset.ccat.nba_service.models.shared.NBAServer;
import com.asset.ccat.nba_service.proxy.LookupProxy;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mahmoud Shehab
 */
@Service
public class LookupsService {

    @Autowired
    LookupProxy configServerProxy;

    public List<NBAServer> getNBAServers() throws NBAException {
        return configServerProxy.getAirServer();
    }

  
}
