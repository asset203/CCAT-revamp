package com.asset.ccat.gateway.models.requests.admin.pam_admin;

import com.asset.ccat.gateway.constants.PamType;
import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author nour.ihab
 */
public class DeletePamRequest extends BaseRequest {

    private Integer id;
    private PamType pamTypeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PamType getPamTypeId() {
        return pamTypeId;
    }

    public void setPamTypeId(PamType pamTypeId) {
        this.pamTypeId = pamTypeId;
    }

    @Override
    public String toString() {
        return "DeletePamRequest{" +
                "id=" + id +
                ", pamTypeId=" + pamTypeId +
                '}';
    }
}
