package com.asset.ccat.lookup_service.models.requests.pam_admin;

import com.asset.ccat.lookup_service.models.LkPamModel;
import com.asset.ccat.lookup_service.models.requests.BaseRequest;

/**
 *
 * @author nour.ihab
 */
public class AddPamRequest extends BaseRequest {

    private LkPamModel pam;

    public LkPamModel getPam() {
        return pam;
    }

    public void setPam(LkPamModel pam) {
        this.pam = pam;
    }

}
