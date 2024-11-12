/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.defines;

/**
 *
 * @author Mahmoud Shehab
 */
public class ErrorCodes {

    public static class SUCCESS {

        public final static int SUCCESS = 0;
    }

    public static class ERROR {

        public final static int NO_COMMANDS_FOUND = -200;
        public final static int EXPIRED_TOKEN = -201;
        public final static int NOT_AUTHORIZED = -202;
        public final static int INVALID_TOKEN = -203;
        public final static int UNKNOWN_ERROR = -205;
        public final static int AIR_RESPONSE_NULL = -206;
        public final static int INVALID_AIR_RESPONSE = -207;
        public final static int SUBSCRIBER_NOT_FOUND = -208;
        public final static int NO_DATA_FOUND = -209;
        public final static int UNREACHABLE_AIR = -210;
        public final static int ERROR_WHILE_PARSING_REQUEST = -211;
        public final static int UNREACHABLE_CI = -212;
        public final static int SUBSCRIBER_ALREADY_INSTALLED = -213;
        public final static int ERROR_PARSING_RESPONSE = -214;

        public final static int BID_DISCONNECT_ERR_SC = -215;
        public final static int BID_DISCONNECT_ERR_BAL = -216;
        public final static int BID_DISCONNECT_ERR_SC_AND_BAL = -217;
        public final static int BID_INSTALL_ERR_MNP_NO_RANGE = -218;
        
        public final static int LOOKUP_SERVER_UNREACHABLE = -219;
        public final static int SERVICE_CLASS_NOT_EXIST_IN_PROFILE = -220;

        // voucher error codes
        public final static int OS_SERIAL_NUMBER_FAILURE = -221;
        public final static int OS_VOUCHER_NUMBER_FAILURE = -222;

        // family and friends error codes
        public final static int NUMBER_IS_BLACK_LISTED = -223;
        public final static int NUMBER_IS_NOT_WHITE_LISTED = -224;
        public final static int USER_MANAGEMENT_UNREACHABLE = -225;
    }
    
     public static class WARNING {

        public final static int MISSING_FIELD = 2;
        public final static int INVALID_INPUT = 3;
        public final static int DUPLICATED_DATA = 4;
        public final static int MUST_BE_MATCHED = 5;
        public final static int EMPTY_FILE = 6;
        public final static int MAX_FILE_UPLOAD_SIZE_EXCEEDED = 7;

    }
}
