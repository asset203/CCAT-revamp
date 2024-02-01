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

        public final static int SUCCESS = 0;
        public final static int NO_DATA = 1;
    }

    public static class ERROR {

        public final static int INVALID_USERNAME_OR_PASSWORD = -100;
        public final static int EXPIRED_TOKEN = -101;
        public final static int NOT_AUTHORIZED = -102;
        public final static int INVALID_TOKEN = -103;
        public final static int UNKNOWN_ERROR = -104;
        public final static int NO_DATA_FOUND = -105;
        public final static int INTERNAL_SERVICE_UNREACHABLE = -106;

        public final static int SOURCE_SAME_DESTINATION = -107;
        public final static int DESTINATION_SC_NOT_ELIGIBLE = -108;
        public final static int SC_NOT_MIGRATABLE = -109;

        public final static int LOG_FOOTPRINT_FAILED = -110;
        public final static int RABBITMQ_INIT_FAILED = -111;

        public final static int UNSUPPORTED_FILE_TYPE = -112;
        public final static int PARSING_FAILED = -113;
        public final static int IS_LOCKED = -114;

        public final static int DUPLICATE_MSISDN = -115;
        public final static int LOG_TX_ADJUSTMENT_FAILED = -116;
        public final static int LOG_TX_LOGIN_FAILED = -117;

        public final static int EXPORT_FAILED = -118;
        public final static int SUMMARY_FAILED = -119;

    }

    public static class WARNING {

        public final static int MISSING_FIELD = 2;
        public final static int INVALID_INPUT = 3;
        public final static int DUPLICATED_DATA = 4;
        public final static int MUST_BE_MATCHED = 5;
        public final static int EMPTY_FILE = 6;
        public final static int MAX_FILE_UPLOAD_SIZE_EXCEEDED = 7;
        public final static int ALREADY_EXISTED= 8;
        public final static int NO_CHANGE_DETECTED= 9;
        public final static int NO_PARAMETERS_PARSED= 10;

    }
}
