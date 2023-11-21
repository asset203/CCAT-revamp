package com.asset.ccat.ci_service.exceptions;

public class CIException extends Exception{
    protected int errorCode;

    public CIException(int errorCode) {
        this.errorCode = errorCode;
    }

    public CIException(int errorCode, String message) {
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
