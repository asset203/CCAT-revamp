package com.asset.ccat.lookup_service.models.responses.disconnection_codes;

import com.asset.ccat.lookup_service.models.DisconnectionCodeModel;
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
