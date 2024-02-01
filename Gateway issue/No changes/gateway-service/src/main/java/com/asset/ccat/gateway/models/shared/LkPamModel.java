package com.asset.ccat.gateway.models.shared;

/**
 *
 * @author nour.ihab
 */
public class LkPamModel {

    private Integer id;
    private String description;
    private Integer pamTypeId;

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

    public Integer getPamTypeId() {
        return pamTypeId;
    }

    public void setPamTypeId(Integer pamTypeId) {
        this.pamTypeId = pamTypeId;
    }

}
