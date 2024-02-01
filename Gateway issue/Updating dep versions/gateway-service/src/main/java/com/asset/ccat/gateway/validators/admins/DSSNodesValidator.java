package com.asset.ccat.gateway.validators.admins;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.admin.dss_nodes.AddDSSNodeRequest;
import com.asset.ccat.gateway.models.requests.admin.dss_nodes.DeleteDSSNodeRequest;
import com.asset.ccat.gateway.models.requests.admin.dss_nodes.UpdateDSSNodeRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Assem.hassan
 */
@Component
public class DSSNodesValidator {


    public void validateDSSNode(AddDSSNodeRequest request) throws GatewayException {
        if (Objects.isNull(request.getDssNode().getAddress())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "address");
        } else if (Objects.isNull(request.getDssNode().getSchema())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "schema");
        } else if (Objects.isNull(request.getDssNode().getPort())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "port");
        } else if (Objects.isNull(request.getDssNode().getUserName())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "userName");
        } else if (Objects.isNull(request.getDssNode().getPassword())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "password");
        }
    }

    public void validateUpdateDSSNode(UpdateDSSNodeRequest request) throws GatewayException {

        if (Objects.isNull(request.getDssNode().getAddress())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "address");
        } else if (Objects.isNull(request.getDssNode().getSchema())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "schema");
        } else if (Objects.isNull(request.getDssNode().getPort())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "port");
        } else if (Objects.isNull(request.getDssNode().getUserName())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "userName");
        }
    }

    public void validateDeleteDSSNode(DeleteDSSNodeRequest request) throws GatewayException {
        if (Objects.isNull(request.getDssNodeId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "dssNodeID");
        }
    }
}
