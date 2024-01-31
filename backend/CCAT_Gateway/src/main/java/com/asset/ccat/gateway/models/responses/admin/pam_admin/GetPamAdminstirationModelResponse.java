package com.asset.ccat.gateway.models.responses.admin.pam_admin;

import com.asset.ccat.gateway.models.admin.PamAdminstirationModel;

/**
 *
 * @author nour.ihab
 */
public class GetPamAdminstirationModelResponse {

    private PamAdminstirationModel pam;

    public PamAdminstirationModel getPam() {
        return pam;
    }

    public void setPam(PamAdminstirationModel pam) {
        this.pam = pam;
    }

}
