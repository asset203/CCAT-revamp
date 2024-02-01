package com.asset.ccat.gateway.models.requests.admin.air_servers;

import com.asset.ccat.gateway.models.admin.AIRServer;
import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author Assem.Hassan
 */
public class UpdateAirServerRequest extends BaseRequest {

    private AIRServer airServer;

    public UpdateAirServerRequest() {
    }

    public UpdateAirServerRequest(AIRServer airServer) {
        this.airServer = airServer;
    }

    public AIRServer getAirServer() {
        return airServer;
    }

    public void setAirServer(AIRServer airServer) {
        this.airServer = airServer;
    }

    @Override
    public String toString() {
        return "UpdateAirServerRequest{" +
                "airServer=" + airServer +
                '}';
    }
}
