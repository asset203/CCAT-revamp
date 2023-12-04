package com.asset.ccat.lookup_service.models.requests.disconnection_code;

import com.asset.ccat.lookup_service.models.requests.BaseRequest;

/**
 *
 * @author nour.ihab
 */
public class CreateDisconnectionCodeRequest extends BaseRequest {

    private Integer code;
    private String description;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
