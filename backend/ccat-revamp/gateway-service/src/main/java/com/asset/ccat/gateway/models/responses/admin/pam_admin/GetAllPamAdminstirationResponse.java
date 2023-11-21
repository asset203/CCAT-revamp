package com.asset.ccat.gateway.models.responses.admin.pam_admin;

import com.asset.ccat.gateway.models.admin.PamAdminstirationModel;
import java.util.List;

/**
 *
 * @author nour.ihab
 */
public class GetAllPamAdminstirationResponse {

    private List<PamAdminstirationModel> pams;

    public GetAllPamAdminstirationResponse() {
    }

    public GetAllPamAdminstirationResponse(List<PamAdminstirationModel> pams) {
        this.pams = pams;
    }

    public List<PamAdminstirationModel> getPams() {
        return pams;
    }

    public void setPams(List<PamAdminstirationModel> pams) {
        this.pams = pams;
    }

}
