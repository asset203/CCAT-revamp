package com.asset.ccat.gateway.models.requests.admin.pam_admin;

import com.asset.ccat.gateway.constants.PamType;
import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author nour.ihab
 */
public class UpdatePamRequest extends BaseRequest {

    private Integer id;
    private String description;
    private PamType pamTypeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PamType getPamTypeId() {
        return pamTypeId;
    }

    public void setPamTypeId(PamType pamTypeId) {
        this.pamTypeId = pamTypeId;
    }

    @Override
    public String toString() {
        return "UpdatePamRequest{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", pamTypeId=" + pamTypeId +
                '}';
    }
}
