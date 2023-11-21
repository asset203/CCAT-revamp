package com.asset.ccat.lookup_service.models.requests.air_servers;

import com.asset.ccat.lookup_service.models.AIRServer;
import com.asset.ccat.lookup_service.models.requests.BaseRequest;

/**
 * @author Assem.Hassan
 */
public class AddAirServerRequest extends BaseRequest {

    private AIRServer airServer;

    public AddAirServerRequest() {
    }

    public AddAirServerRequest(AIRServer airServer) {
        this.airServer = airServer;
    }

    public AIRServer getAirServer() {
        return airServer;
    }

    public void setAirServer(AIRServer airServer) {
        this.airServer = airServer;
    }
}
