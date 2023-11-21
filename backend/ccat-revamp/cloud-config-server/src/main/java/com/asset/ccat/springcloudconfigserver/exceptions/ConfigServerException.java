/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.springcloudconfigserver.exceptions;

/**
 * @author Mahmoud Shehab
 */
public class ConfigServerException extends Exception {

    protected int errorCode;
    protected int errorSeverity;
    protected String[] args;

    public ConfigServerException(int errorCode) {
        this.errorCode = errorCode;
    }

    public ConfigServerException(int errorCode, int errorSeverity) {
        this.errorCode = errorCode;
        this.errorSeverity = errorSeverity;
    }

    public ConfigServerException(int errorCode, int errorSeverity, String... args) {
        this.errorCode = errorCode;
        this.errorSeverity = errorSeverity;
        this.args = args;
    }

    public ConfigServerException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ConfigServerException(int errorCode, String message, String... args) {
        super(message);
        this.errorCode = errorCode;
        this.args = args;
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

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

}
