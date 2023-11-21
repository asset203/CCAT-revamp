package com.asset.ccat.air_service.exceptions;

public class AIRVoucherException extends Exception{

    protected int errorCode;

    public AIRVoucherException(int errorCode) {
        this.errorCode = errorCode;
    }

    public AIRVoucherException(int errorCode, String message) {
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
