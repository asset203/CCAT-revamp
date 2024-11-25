package com.asset.ccat.history_service.exceptions;

/**
 *
 * @author wael.mohamed
 */
public class HistoryException extends Exception {

    protected int errorCode;
    protected int errorSeverity;
    protected String args;

    public HistoryException(int errorCode, int errorSeverity) {
        this.errorCode = errorCode;
        this.errorSeverity = errorSeverity;
    }

    public HistoryException(int errorCode, String args) {
        this.errorCode = errorCode;
        this.args = args;
    }

    public HistoryException(int errorCode, int errorSeverity, String message) {
        super(message);
        this.errorCode = errorCode;
        this.errorSeverity = errorSeverity;
    }

    public HistoryException(int errorCode) {
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
