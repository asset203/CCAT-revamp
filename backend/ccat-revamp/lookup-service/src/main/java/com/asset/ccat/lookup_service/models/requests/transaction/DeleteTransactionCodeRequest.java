/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.models.requests.transaction;

import com.asset.ccat.lookup_service.models.requests.BaseRequest;

/**
 *
 * @author wael.mohamed
 */
public class DeleteTransactionCodeRequest extends BaseRequest {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
