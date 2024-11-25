/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.requests.admin.transaction;

import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 *
 * @author wael.mohamed
 */
public class UpdateTransactionLinkDescriptionRequest extends BaseRequest {

    private Integer typeId;
    private Integer codeId;
    private String description;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getCodeId() {
        return codeId;
    }

    public void setCodeId(Integer codeId) {
        this.codeId = codeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
