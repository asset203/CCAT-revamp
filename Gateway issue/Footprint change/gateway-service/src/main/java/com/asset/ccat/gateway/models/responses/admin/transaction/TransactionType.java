/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.responses.admin.transaction;

import java.util.List;

/**
 *
 * @author wael.mohamed
 */
public class TransactionType {

    private Integer id;
    private String name;
    private String value;
    private List<Integer> ccFeatures;

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

    public List<Integer> getCcFeatures() {
        return ccFeatures;
    }

    public void setCcFeatures(List<Integer> ccFeatures) {
        this.ccFeatures = ccFeatures;
    }

}
