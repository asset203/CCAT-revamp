package com.asset.ccat.gateway.validators.admins;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.admin.ods_nodes.AddODSNodeRequest;
import com.asset.ccat.gateway.models.requests.admin.ods_nodes.DeleteODSNodeRequest;
import com.asset.ccat.gateway.models.requests.admin.ods_nodes.UpdateODSNodeRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Assem.hassan
 */
@Component
public class ODSNodesValidator {


    public void validateODSNode(AddODSNodeRequest request) throws GatewayException {
        if (Objects.isNull(request.getOdsNode().getAddress())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "address");
        } else if (Objects.isNull(request.getOdsNode().getSchema())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "schema");
        } else if (Objects.isNull(request.getOdsNode().getPort())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "port");
        } else if (Objects.isNull(request.getOdsNode().getUserName())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "userName");
        } else if (Objects.isNull(request.getOdsNode().getPassword())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "password");
        }
    }

    public void validateUpdateODSNode(UpdateODSNodeRequest request) throws GatewayException {

        if (Objects.isNull(request.getOdsNode().getAddress())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "address");
        } else if (Objects.isNull(request.getOdsNode().getSchema())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "schema");
        } else if (Objects.isNull(request.getOdsNode().getPort())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "port");
        } else if (Objects.isNull(request.getOdsNode().getUserName())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "userName");
        }
    }

    public void validateDeleteODSNode(DeleteODSNodeRequest request) throws GatewayException {
        if (Objects.isNull(request.getOdsNodeId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "odsNodeID");
        }
    }
}
