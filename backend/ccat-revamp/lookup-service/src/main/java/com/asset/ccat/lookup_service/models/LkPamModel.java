/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.models;

/**
 *
 * @author wael.mohamed
 */
public class LkPamModel {

    private Integer id;
    private String description;
    private Integer pamTypeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer pamClassId) {
        this.id = pamClassId;
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
