package com.asset.ccat.gateway.models.responses.customer_care;

import com.asset.ccat.gateway.models.customer_care.PamInformationModel;
import java.util.List;

/**
 *
 * @author nour.ihab
 */
public class GetAllPamInformationResponse {

    private List<PamInformationModel> PamInformationList;

    public List<PamInformationModel> getPamInformationList() {
        return PamInformationList;
    }

    public void setPamInformationList(List<PamInformationModel> PamInformationList) {
        this.PamInformationList = PamInformationList;
    }

}
