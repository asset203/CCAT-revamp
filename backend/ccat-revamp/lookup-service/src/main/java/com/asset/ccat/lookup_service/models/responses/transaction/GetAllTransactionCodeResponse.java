/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.models.responses.transaction;

import com.asset.ccat.lookup_service.models.TransactionCode;
import java.util.List;

/**
 *
 * @author wael.mohamed
 */
public class GetAllTransactionCodeResponse {

    private List<TransactionCode> transactionCodes;

    public GetAllTransactionCodeResponse() {
    }

    public GetAllTransactionCodeResponse(List<TransactionCode> transactionCodes) {
        this.transactionCodes = transactionCodes;
    }

    public List<TransactionCode> getTransactionCodes() {
        return transactionCodes;
    }

    public void setTransactionCodes(List<TransactionCode> transactionCodes) {
        this.transactionCodes = transactionCodes;
    }

}
