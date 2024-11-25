package com.asset.ccat.gateway.validators.admins;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.admin.flex_share_history.AddFlexShareHistoryNodeRequest;
import com.asset.ccat.gateway.models.requests.admin.flex_share_history.DeleteFlexShareHistoryNodeRequest;
import com.asset.ccat.gateway.models.requests.admin.flex_share_history.UpdateFlexShareHistoryNodeRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Assem.hassan
 */
@Component
public class FlexShareHistoryNodesValidator {


    public void validateFlexShareHistoryNode(AddFlexShareHistoryNodeRequest request) throws GatewayException {
        if (Objects.isNull(request.getFlexShareHistoryNode().getAddress())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "address");
        } else if (Objects.isNull(request.getFlexShareHistoryNode().getSchema())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "schema");
        } else if (Objects.isNull(request.getFlexShareHistoryNode().getPort())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "port");
        } else if (Objects.isNull(request.getFlexShareHistoryNode().getUsername())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "username");
        } else if (Objects.isNull(request.getFlexShareHistoryNode().getPassword())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "password");
        }
    }

    public void validateUpdateFlexShareHistoryNode(UpdateFlexShareHistoryNodeRequest request) throws GatewayException {

        if (Objects.isNull(request.getFlexShareHistoryNode().getAddress())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "address");
        } else if (Objects.isNull(request.getFlexShareHistoryNode().getSchema())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "schema");
        } else if (Objects.isNull(request.getFlexShareHistoryNode().getPort())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "port");
        } else if (Objects.isNull(request.getFlexShareHistoryNode().getUsername())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "username");
        }
    }

    public void validateDeleteFlexShareHistoryNode(DeleteFlexShareHistoryNodeRequest request) throws GatewayException {
        if (Objects.isNull(request.getFlexShareHistoryNodeId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "flexShareHistoryNodeId");
        }
    }
}
