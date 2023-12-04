package com.asset.ccat.gateway.models.admin;

/**
 *
 * @author marwa.elshawarby
 */
public class FailedMsisdnModel {

    private String msisdn;
    private Integer errorCode;
    private String errorMessage;

    public FailedMsisdnModel() {
    }

    public FailedMsisdnModel(String msisdn, Integer errorCode, String errorMessage) {
        this.msisdn = msisdn;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
