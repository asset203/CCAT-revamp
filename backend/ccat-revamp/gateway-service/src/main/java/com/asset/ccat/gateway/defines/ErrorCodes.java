/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.defines;

/**
 * @author Mahmoud Shehab
 */
public class ErrorCodes {

    public static class SUCCESS {

        public static final int SUCCESS = 0;
        public static final int NO_DATA = 1;
    }

    public static class ERROR {

        public static final int INVALID_USERNAME_OR_PASSWORD = -100;
        public static final int EXPIRED_TOKEN = -101;
        public static final int NOT_AUTHORIZED = -102;
        public static final int INVALID_TOKEN = -103;
        public static final int UNKNOWN_ERROR = -104;
        public static final int NO_DATA_FOUND = -105;
        public static final int INTERNAL_SERVICE_UNREACHABLE = -106;

        public static final int SOURCE_SAME_DESTINATION = -107;
        public static final int DESTINATION_SC_NOT_ELIGIBLE = -108;
        public static final int SC_NOT_MIGRATABLE = -109;

        public static final int LOG_FOOTPRINT_FAILED = -110;
        public static final int RABBITMQ_INIT_FAILED = -111;

        public static final int UNSUPPORTED_FILE_TYPE = -112;
        public static final int PARSING_FAILED = -113;
        public static final int IS_LOCKED = -114;

        public static final int DUPLICATE_MSISDN = -115;
        public static final int LOG_TX_ADJUSTMENT_FAILED = -116;
        public static final int LOG_TX_LOGIN_FAILED = -117;

        public static final int EXPORT_FAILED = -118;
        public static final int SUMMARY_FAILED = -119;
        public static final int SUBSCRIBER_OWNER_CONFLICT = -120;
        public static final int MISSING_MSISDN = -121;
        public static final int VIP_NOT_ELIGIBLE = -122;
        public static final int SUBSCRIBER_IS_UNLOCKED = -123;


    }

    public static class WARNING {

        public static final int MISSING_FIELD = 2;
        public static final int INVALID_INPUT = 3;
        public static final int DUPLICATED_DATA = 4;
        public static final int MUST_BE_MATCHED = 5;
        public static final int EMPTY_FILE = 6;
        public static final int MAX_FILE_UPLOAD_SIZE_EXCEEDED = 7;
        public static final int ALREADY_EXISTED= 8;
        public static final int NO_CHANGE_DETECTED= 9;
        public static final int NO_PARAMETERS_PARSED= 10;

    }

    public static class USER_MANAGEMENT_SERVICE_CODES{
        public static final int INVALID_USERNAME_OR_PASSWORD = -400;
        public static final int LDAP_AUTH_FAILED = -407;


    }
}
