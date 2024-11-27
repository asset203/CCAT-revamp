package com.asset.ccat.gateway.cache;

import java.util.HashMap;

import com.asset.ccat.gateway.logger.CCATLogger;
import jakarta.annotation.PostConstruct;

import com.asset.ccat.gateway.models.shared.FootPrintPageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.services.LookupsService;

@Component
public class LookupsCache {

    @Autowired
    LookupsService lookupsService;

    private HashMap<String, FootPrintPageModel> footPrintPages;


    @PostConstruct
    public void init() throws GatewayException {
        try {
            CCATLogger.DEBUG_LOGGER.debug("Start getting footprint pages...");
            Thread.sleep(5000);
            CCATLogger.DEBUG_LOGGER.debug("Start getting footprint pages...");
            footPrintPages = lookupsService.getFootPrintPages();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
            throw new RuntimeException("Initialization was interrupted", e);
        }
    }

    public HashMap<String, FootPrintPageModel> getFootPrintPages() {
        return footPrintPages;
    }

    public void setFootPrintPages(HashMap<String, FootPrintPageModel> footPrintPages) {
        this.footPrintPages = footPrintPages;
    }
}
