package com.asset.ccat.gateway.models.requests.admin.air_servers;


import com.asset.ccat.gateway.models.admin.AIRServer;
import com.asset.ccat.gateway.models.requests.BaseRequest;

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

    @Override
    public String toString() {
        return "AddAirServerRequest{" +
                "airServer=" + airServer +
                '}';
    }
}
