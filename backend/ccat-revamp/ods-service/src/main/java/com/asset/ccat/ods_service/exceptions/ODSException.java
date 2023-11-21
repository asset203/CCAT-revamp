package com.asset.ccat.ods_service.exceptions;

/**
 *
 * @author nour.ihab
 */
public class ODSException extends Exception {

    protected int errorCode;
    protected int errorSeverity;
    protected String[] args;

    public ODSException(int errorCode, int errorSeverity) {
        this.errorCode = errorCode;
        this.errorSeverity = errorSeverity;
    }

    public ODSException(int errorCode, String[] args) {
        this.errorCode = errorCode;
        this.args = args;
    }

    public ODSException(int errorCode, int errorSeverity, String message) {
        super(message);
        this.errorCode = errorCode;
        this.errorSeverity = errorSeverity;
    }

    public ODSException(int errorCode) {
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

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

}
