package com.asset.ccat.gateway.models.requests.admin.pam_admin;

import com.asset.ccat.gateway.models.admin.PamAdminstirationModel;
import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author nour.ihab
 */
public class AddPamRequest extends BaseRequest {

    private PamAdminstirationModel pam;

    public PamAdminstirationModel getPam() {
        return pam;
    }

    public void setPam(PamAdminstirationModel pam) {
        this.pam = pam;
    }

    @Override
    public String toString() {
        return "AddPamRequest{" +
                "pam=" + pam +
                '}';
    }
}
