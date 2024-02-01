package com.asset.ccat.gateway.models.requests.admin.pam_admin;

import com.asset.ccat.gateway.models.requests.BaseRequest;

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
