package com.asset.ccat.lookup_service.models.requests.pam_admin;

import com.asset.ccat.lookup_service.models.requests.BaseRequest;

/**
 *
 * @author nour.ihab
 */
public class GetPamRequest extends BaseRequest {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
