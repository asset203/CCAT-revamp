/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.requests.admin.transaction;

import com.asset.ccat.gateway.models.requests.BaseRequest;
import java.util.List;

/**
 *
 * @author wael.mohamed
 */
public class UpdateTransactionTypeRequest extends BaseRequest {

    private Integer id;
    private String name;
    private String value;
    private String description;
    List<Integer> ccFeatures;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getCcFeatures() {
        return ccFeatures;
    }

    public void setCcFeatures(List<Integer> ccFeatures) {
        this.ccFeatures = ccFeatures;
    }
}