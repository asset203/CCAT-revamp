/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.ods_service.models.requests.transaction;

import com.asset.ccat.ods_service.models.requests.BaseRequest;

/**
 *
 * @author wael.mohamed
 */
public class GetAllTransactionLinkRequest extends BaseRequest {

    private Integer typeId;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
}
