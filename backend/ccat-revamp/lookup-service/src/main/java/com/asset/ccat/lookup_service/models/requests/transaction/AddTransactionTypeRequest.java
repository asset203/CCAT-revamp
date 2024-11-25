/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.models.requests.transaction;

import com.asset.ccat.lookup_service.models.requests.BaseRequest;
import java.util.List;

/**
 *
 * @author wael.mohamed
 */
public class AddTransactionTypeRequest extends BaseRequest {

    private String name;
    private String value;
    private String description;
    private List<Integer> ccFeatures;

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
