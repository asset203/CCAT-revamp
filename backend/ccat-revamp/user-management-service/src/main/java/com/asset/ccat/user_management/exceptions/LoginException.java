package com.asset.ccat.user_management.exceptions;

/**
 * @author Mayar Ezz el-Din
 */
public class LoginException extends UserManagementException{

    public LoginException(int errorCode) {
        super(errorCode);
    }

    public LoginException(int errorCode, int errorSeverity) {
        super(errorCode, errorSeverity);
    }

    public LoginException(int errorCode, int errorSeverity, String args) {
        super(errorCode, errorSeverity, args);
    }
}
