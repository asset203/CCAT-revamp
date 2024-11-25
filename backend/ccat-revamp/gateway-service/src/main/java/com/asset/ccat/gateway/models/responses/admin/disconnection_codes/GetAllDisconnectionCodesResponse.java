package com.asset.ccat.gateway.models.responses.admin.disconnection_codes;

import com.asset.ccat.gateway.models.admin.DisconnectionCodeModel;
import java.util.List;

/**
 *
 * @author nour.ihab
 */
public class GetAllDisconnectionCodesResponse {

    private List<DisconnectionCodeModel> disconnectionCodes;

    public GetAllDisconnectionCodesResponse() {
    }

    public GetAllDisconnectionCodesResponse(List<DisconnectionCodeModel> disconnectionCodes) {
        this.disconnectionCodes = disconnectionCodes;
    }

    public List<DisconnectionCodeModel> getDisconnectionCodes() {
        return disconnectionCodes;
    }

    public void setDisconnectionCodes(List<DisconnectionCodeModel> disconnectionCodes) {
        this.disconnectionCodes = disconnectionCodes;
    }

}
