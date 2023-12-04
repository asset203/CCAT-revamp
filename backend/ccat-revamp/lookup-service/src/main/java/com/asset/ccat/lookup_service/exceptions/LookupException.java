package com.asset.ccat.lookup_service.exceptions;

/**
 *
 * @author nour.ihab
 */
public class LookupException extends Exception {

    protected int errorCode;
    protected int errorSeverity;
    protected String args;

    public LookupException(int errorCode) {
        this.errorCode = errorCode;
    }

    public LookupException(int errorCode, int errorSeverity) {
        this.errorCode = errorCode;
        this.errorSeverity = errorSeverity;
    }

    public  LookupException(int errorCode, int errorSeverity, String args) {

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

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

}
