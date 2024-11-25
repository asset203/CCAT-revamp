/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.balance_dispute_service.dto.users;

/**
 *
 * @author wael.mohamed
 */
public class LkProfileLimit {

    private Integer limitId;
    private String limitName;
    private Float value;

    public Integer getLimitId() {
        return limitId;
    }

    public void setLimitId(Integer limitId) {
        this.limitId = limitId;
    }

    public String getLimitName() {
        return limitName;
    }

    public void setLimitName(String limitName) {
        this.limitName = limitName;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

}
