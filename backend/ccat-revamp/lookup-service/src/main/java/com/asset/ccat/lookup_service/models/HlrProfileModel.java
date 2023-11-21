/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.models;

import java.io.Serializable;

/**
 *
 * @author wael.mohamed
 */
public class HlrProfileModel implements Serializable {

    private Integer hlrId;
    private Integer code;
    private String name;

    public Integer getHlrId() {
        return hlrId;
    }

    public void setHlrId(Integer hlrId) {
        this.hlrId = hlrId;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
