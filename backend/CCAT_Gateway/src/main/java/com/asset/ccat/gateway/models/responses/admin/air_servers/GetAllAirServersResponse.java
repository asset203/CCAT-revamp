package com.asset.ccat.gateway.models.responses.admin.air_servers;

import com.asset.ccat.gateway.models.admin.AIRServer;

import java.util.List;

/**
 * @author Assem.Hassan
 */
public class GetAllAirServersResponse {

    private List<AIRServer> airServerList;

    public GetAllAirServersResponse() {
    }

    public GetAllAirServersResponse(List<AIRServer> airServerList) {
        this.airServerList = airServerList;
    }

    public List<AIRServer> getAirServerList() {
        return airServerList;
    }

    public void setAirServerList(List<AIRServer> airServerList) {
        this.airServerList = airServerList;
    }
}
