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
public class GetAllTransactionTypeResponse {

    private List<TransactionType> transactionTypes;

    public GetAllTransactionTypeResponse() {
    }

    public GetAllTransactionTypeResponse(List<TransactionType> transactionTypes) {
        this.transactionTypes = transactionTypes;
    }

    public List<TransactionType> getTransactionTypes() {
        return transactionTypes;
    }

    public void setTransactionTypes(List<TransactionType> transactionTypes) {
        this.transactionTypes = transactionTypes;
    }

}
