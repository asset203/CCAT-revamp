package com.asset.ccat.gateway.models.responses.lookup;

import com.asset.ccat.gateway.models.shared.LkPamModel;
import java.util.List;

/**
 *
 * @author nour.ihab
 */
public class GetAllLookupPamResponse {

    private List<LkPamModel> pams;

    public GetAllLookupPamResponse() {
    }

    public GetAllLookupPamResponse(List<LkPamModel> pams) {
        this.pams = pams;
    }

    public List<LkPamModel> getPams() {
        return pams;
    }

    public void setPams(List<LkPamModel> pams) {
        this.pams = pams;
    }

}
