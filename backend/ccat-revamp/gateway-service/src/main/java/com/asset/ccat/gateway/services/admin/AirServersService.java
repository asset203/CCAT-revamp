package com.asset.ccat.gateway.services.admin;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.models.requests.admin.air_servers.*;
import com.asset.ccat.gateway.models.responses.admin.air_servers.GetAirServerResponse;
import com.asset.ccat.gateway.models.responses.admin.air_servers.GetAllAirServersResponse;
import com.asset.ccat.gateway.proxy.admin.AirServersProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Assem.Hassan
 */
@Service
public class AirServersService {

    @Autowired
    private AirServersProxy airServersProxy;

    public GetAllAirServersResponse getAllAirServers(GetAllAirServersRequest request) throws GatewayException {
        return airServersProxy.getAllAirServers(request);
    }

    public GetAirServerResponse getAirServer(GetAirServerRequest request) throws GatewayException {
        return airServersProxy.getAirServer(request);
    }

    public void addAirServer(AddAirServerRequest request) throws GatewayException {
        airServersProxy.addAirServer(request);
    }

    public void updateAirServer(UpdateAirServerRequest request) throws GatewayException {
        airServersProxy.updateAirServer(request);
    }

    public void deleteAirServer(DeleteAirServerRequest request) throws GatewayException {
        airServersProxy.deleteAirServer(request);
    }
}
