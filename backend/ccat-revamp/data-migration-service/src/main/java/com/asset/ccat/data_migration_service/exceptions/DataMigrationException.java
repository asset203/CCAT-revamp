package com.asset.ccat.data_migration_service.exceptions;

/**
 * @author Assem.Hassan
 */
public class DataMigrationException extends Exception {

    protected int errorCode;
    protected int errorSeverity;
    protected String message;

    protected String[] args;


    public DataMigrationException(int errorCode) {
        this.errorCode = errorCode;
    }

    public DataMigrationException(int errorCode, int errorSeverity) {
        this.errorCode = errorCode;
        this.errorSeverity = errorSeverity;
    }

    public DataMigrationException(int errorCode, String message, String... args) {
        super(message);
        this.errorCode = errorCode;
        this.args = args;
    }

    public DataMigrationException(int errorCode, int errorSeverity, String message) {

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