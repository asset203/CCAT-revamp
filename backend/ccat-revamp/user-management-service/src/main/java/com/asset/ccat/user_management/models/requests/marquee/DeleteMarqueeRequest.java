/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.user_management.models.requests.marquee;

import com.asset.ccat.user_management.models.requests.BaseRequest;

/**
 *
 * @author wael.mohamed
 */
public class DeleteMarqueeRequest extends BaseRequest {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
