/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.dynamic_page.exceptions;

/**
 * @author Mahmoud Shehab
 */
public class DynamicPageException extends Exception {

    protected int errorCode;
    protected int errorSeverity;
    protected String[] args;

    public DynamicPageException(int errorCode) {
        this.errorCode = errorCode;
    }

    public DynamicPageException(int errorCode, int errorSeverity) {
        this.errorCode = errorCode;
        this.errorSeverity = errorSeverity;
    }

    public DynamicPageException(int errorCode, String errorMsg, int errorSeverity) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorSeverity = errorSeverity;
    }

    public DynamicPageException(int errorCode, int errorSeverity, String... args) {
        this.errorCode = errorCode;
        this.errorSeverity = errorSeverity;
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
