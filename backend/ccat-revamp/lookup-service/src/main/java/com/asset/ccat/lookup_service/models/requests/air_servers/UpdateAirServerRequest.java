package com.asset.ccat.lookup_service.models.requests.air_servers;

import com.asset.ccat.lookup_service.models.AIRServer;
import com.asset.ccat.lookup_service.models.ods_models.ODSNodeModel;
import com.asset.ccat.lookup_service.models.requests.BaseRequest;

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
}
