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
public class AIRServiceException extends Exception {

    protected int errorCode;
    protected int errorSeverity;
    protected String args;

    public AIRServiceException(int errorCode, int errorSeverity) {
        this.errorCode = errorCode;
        this.errorSeverity = errorSeverity;
    }

    public AIRServiceException(int errorCode, String args) {
        this.errorCode = errorCode;
        this.args = args;
    }

    public AIRServiceException(int errorCode, int errorSeverity, String message) {
        super(message);
        this.errorCode = errorCode;
        this.errorSeverity = errorSeverity;
    }

    public AIRServiceException(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorSeverity() {
        return errorSeverity;
    }

    public void setErrorSeverity(int errorSeverity) {
        this.errorSeverity = errorSeverity;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

}
