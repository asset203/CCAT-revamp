/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.models.responses.transaction;

import com.asset.ccat.lookup_service.models.TransactionLink;
import java.util.List;

/**
 *
 * @author wael.mohamed
 */
public class GetAllTransactionLinkesResponse {

    private List<TransactionLink> transactionLinkes;

    public GetAllTransactionLinkesResponse() {
    }

    public GetAllTransactionLinkesResponse(List<TransactionLink> transactionTypes) {
        this.transactionLinkes = transactionTypes;
    }

    public List<TransactionLink> getTransactionLinkes() {
        return transactionLinkes;
    }

    public void setTransactionLinkes(List<TransactionLink> transactionLinkes) {
        this.transactionLinkes = transactionLinkes;
    }

}
