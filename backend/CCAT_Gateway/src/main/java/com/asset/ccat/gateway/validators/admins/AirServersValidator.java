package com.asset.ccat.gateway.validators.admins;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.admin.air_servers.AddAirServerRequest;
import com.asset.ccat.gateway.models.requests.admin.air_servers.DeleteAirServerRequest;
import com.asset.ccat.gateway.models.requests.admin.air_servers.UpdateAirServerRequest;
import com.asset.ccat.gateway.models.requests.admin.ods_nodes.AddODSNodeRequest;
import com.asset.ccat.gateway.models.requests.admin.ods_nodes.DeleteODSNodeRequest;
import com.asset.ccat.gateway.models.requests.admin.ods_nodes.UpdateODSNodeRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Assem.hassan
 */
@Component
public class AirServersValidator {


    public void validateAirServer(AddAirServerRequest request) throws GatewayException {
        if (Objects.isNull(request.getAirServer().getUrl())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "url");
        } else if (Objects.isNull(request.getAirServer().getAgentName())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "agentName");
        } else if (Objects.isNull(request.getAirServer().getHost())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "host");
        } else if (Objects.isNull(request.getAirServer().getAuthorization())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "authorization");
        } else if (Objects.isNull(request.getAirServer().getIsDown())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "isDown");
        }
    }

    public void validateUpdateAirServer(UpdateAirServerRequest request) throws GatewayException {

        if (Objects.isNull(request.getAirServer().getId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "id");
        } else if (Objects.isNull(request.getAirServer().getUrl())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "url");
        } else if (Objects.isNull(request.getAirServer().getAgentName())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "agentName");
        } else if (Objects.isNull(request.getAirServer().getHost())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "host");
        } else if (Objects.isNull(request.getAirServer().getAuthorization())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "authorization");
        } else if (Objects.isNull(request.getAirServer().getIsDown())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "isDown");
        }
    }

    public void validateDeleteAirServer(DeleteAirServerRequest request) throws GatewayException {
        if (Objects.isNull(request.getAirServerId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "airServerId");
        }
    }
}
