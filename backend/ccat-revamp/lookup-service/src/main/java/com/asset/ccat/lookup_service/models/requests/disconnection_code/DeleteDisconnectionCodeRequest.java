package com.asset.ccat.lookup_service.models.requests.disconnection_code;

import com.asset.ccat.lookup_service.models.requests.BaseRequest;

/**
 *
 * @author nour.ihab
 */
public class DeleteDisconnectionCodeRequest extends BaseRequest {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
