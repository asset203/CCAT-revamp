/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.shared;

/**
 *
 * @author Mahmoud Shehab
 */
public class ADMTransactionType {

    private Integer id;
    private String value;
    private String name;
//    private Boolean isDefault;
//    private Boolean isDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Boolean getIsDefault() {
//        return isDefault;
//    }
//
//    public void setIsDefault(Boolean isDefault) {
//        this.isDefault = isDefault;
//    }
//
//    public Boolean getIsDeleted() {
//        return isDeleted;
//    }
//
//    public void setIsDeleted(Boolean isDeleted) {
//        this.isDeleted = isDeleted;
//    }

}
