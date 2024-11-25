package com.asset.ccat.gateway.cache;

import java.util.HashMap;

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
        footPrintPages = lookupsService.getFootPrintPages();
    }

    public HashMap<String, FootPrintPageModel> getFootPrintPages() {
        return footPrintPages;
    }

    public void setFootPrintPages(HashMap<String, FootPrintPageModel> footPrintPages) {
        this.footPrintPages = footPrintPages;
    }
}
