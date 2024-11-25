package com.asset.ccat.balance_dispute_service.exceptions;

public class BalanceDisputeException extends Exception {

    protected int errorCode;
    protected int errorSeverity;
    protected String[] args;

    public BalanceDisputeException(int errorCode) {
        this.errorCode = errorCode;
    }

    public BalanceDisputeException(int errorCode, int errorSeverity) {
        this.errorCode = errorCode;
        this.errorSeverity = errorSeverity;
    }

    public BalanceDisputeException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BalanceDisputeException(int errorCode, String message, String... args) {
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
