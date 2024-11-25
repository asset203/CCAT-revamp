package com.asset.ccat.gateway.models.responses.lookup;

import com.asset.ccat.gateway.models.admin.PamTypesModel;
import java.util.List;

/**
 *
 * @author nour.ihab
 */
public class GetAllPamsTypeLKResponse {

    private List<PamTypesModel> pamTypes;

    public GetAllPamsTypeLKResponse() {
    }

    public GetAllPamsTypeLKResponse(List<PamTypesModel> pamTypes) {
        this.pamTypes = pamTypes;
    }

    public List<PamTypesModel> getPamTypes() {
        return pamTypes;
    }

    public void setPamTypes(List<PamTypesModel> pamTypes) {
        this.pamTypes = pamTypes;
    }

}
