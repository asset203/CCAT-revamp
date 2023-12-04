/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.exceptions;

/**
 *
 * @author Mahmoud Shehab
 */
public class AIRException extends Exception {

    protected int errorCode;

    public AIRException(int errorCode) {
        this.errorCode = errorCode;
    }

    public AIRException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
