/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.history_service.models.requests;

import java.util.Date;

/**
 *
 * @author wael.mohamed
 */
public class AddAdjustmentTransactionRequest extends SubscriberRequest {

    private Date adjustmentDate;

    public Date getAdjustmentDate() {
        return adjustmentDate;
    }

    public void setAdjustmentDate(Date adjustmentDate) {
        this.adjustmentDate = adjustmentDate;
    }

}
