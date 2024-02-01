package com.asset.ccat.gateway.models.admin;

import com.asset.ccat.gateway.constants.PamType;

/**
 *
 * @author nour.ihab
 */
public class PamAdminstirationModel {

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

}
