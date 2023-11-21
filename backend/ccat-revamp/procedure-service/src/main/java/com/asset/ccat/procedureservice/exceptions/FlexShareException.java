package com.asset.ccat.procedureservice.exceptions;

public class FlexShareException extends Exception {

    protected int errorCode;
    protected int errorSeverity;
    protected String[] args;

    public FlexShareException(int errorCode) {
        this.errorCode = errorCode;
    }

    public FlexShareException(int errorCode, int errorSeverity) {
        this.errorCode = errorCode;
        this.errorSeverity = errorSeverity;
    }

    public FlexShareException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public FlexShareException(int errorCode, String message, String... args) {
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
