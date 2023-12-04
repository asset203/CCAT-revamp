package com.asset.ccat.simulators_service.exceptions;

/**
 * @author Assem.Hassan
 */
public class SimulatorsException extends Exception {

  protected int errorCode;
  protected int errorSeverity;
  protected String args;

  public SimulatorsException(int errorCode, int errorSeverity) {
    this.errorCode = errorCode;
    this.errorSeverity = errorSeverity;
  }

  public SimulatorsException(int errorCode, String args) {
    this.errorCode = errorCode;
    this.args = args;
  }

  public SimulatorsException(int errorCode, int errorSeverity, String message) {
    super(message);
    this.errorCode = errorCode;
    this.errorSeverity = errorSeverity;
  }

  public SimulatorsException(int errorCode) {
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
