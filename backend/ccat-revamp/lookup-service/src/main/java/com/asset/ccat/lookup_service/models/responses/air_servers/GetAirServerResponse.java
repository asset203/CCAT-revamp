package com.asset.ccat.lookup_service.models.responses.air_servers;

import com.asset.ccat.lookup_service.models.AIRServer;

/**
 * @author Assem.Hassan
 */
public class GetAirServerResponse {

    private AIRServer airServer;

    public GetAirServerResponse() {
    }

    public GetAirServerResponse(AIRServer airServer) {
        this.airServer = airServer;
    }

    public AIRServer getDssNodesModel() {
        return airServer;
    }

    public void setDssNodesModel(AIRServer dssNodeModel) {
        this.airServer = dssNodeModel;
    }
}
