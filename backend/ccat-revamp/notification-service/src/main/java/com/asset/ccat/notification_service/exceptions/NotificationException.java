package com.asset.ccat.notification_service.exceptions;


public class NotificationException extends Exception {

    protected int errorCode;
    protected int errorSeverity;
    protected String message;

    protected String[] args;




    public NotificationException(int errorCode) {
        this.errorCode = errorCode;
    }

    public NotificationException(int errorCode, int errorSeverity) {
        this.errorCode = errorCode;
        this.errorSeverity = errorSeverity;
    }
    public NotificationException(int errorCode, String message, String... args) {
        super(message);
        this.errorCode = errorCode;
        this.args = args;
    }
    public  NotificationException(int errorCode, int errorSeverity, String message) {

        this.errorCode = errorCode;
        this.errorSeverity = errorSeverity;
        this.message = message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
