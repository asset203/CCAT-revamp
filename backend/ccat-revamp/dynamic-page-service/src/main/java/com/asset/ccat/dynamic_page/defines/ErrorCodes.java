/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.dynamic_page.defines;

/**
 * @author Mahmoud Shehab
 */
public class ErrorCodes {

    public static class SUCCESS {

        public final static int SUCCESS = 0;
    }

    public static class ERROR {

        public final static int INVALID_USERNAME_OR_PASSWORD = -1000;
        public final static int EXPIRED_TOKEN = -1001;
        public final static int NOT_AUTHORIZED = -1002;
        public final static int INVALID_TOKEN = -1003;
        public final static int DATABASE_ERROR = -1004;
        public final static int UNKNOWN_ERROR = -1005;
        public final static int MISSING_FIELD = -1006;
        public final static int ADD_FAILED = -1007;
        public final static int UPDATE_FAILED = -1008;
        public final static int DELETE_FAILED = -1009;
        public final static int INVALID_INPUT = -1010;
        public final static int NO_DYNAMIC_PAGES_FOUND = -1011;
        public final static int DB_CONN_TEST_FAIL = -1012;
        public final static int DYNAMIC_PAGE_NOT_FOUND = -1013;
        public final static int RETRIEVAL_FAILED = -1014;
        public final static int PARSE_PARAMETERS_QUERY_FAILED = -1015;
        public final static int DECRYPTION_FAILED = -1016;
        public final static int ENCRYPTION_FAILED = -1017;
        public final static int DELETE_PROHIBITED = -1018;
        public final static int UNSUPPORTED_OPERATION_TYPE = -1019;
        public final static int SP_CALL_FAILED = -1020;
        public final static int HTTP_CALL_FAILED = -1021;
        public final static int RESPONSE_MAPPING_FAILED = -1022;
        public final static int INVALID_RESPONSE = -1023;
        public final static int SP_RESPONSE_EXTRACTING_FAILED = -1024;
        public final static int STEP_NOT_FOUND = -1025;
        public final static int STEP_EXECUTION_FAILED = -1026;
        public final static int HTTP_METHOD_NOT_SUPPORTED = -1027;
        public final static int HTTP_CONTENT_TYPE_NOT_SUPPORTED = -1028;
        public final static int PARSE_HTTP_REQUEST_BODY_FAILED = -1029;
        public final static int PARSE_HTTP_RESPONSE_BODY_FAILED = -1030;
        public final static int HTTP_RESPONSE_TYPE_NOT_SUPPORTED = -1031;
        public final static int HTTP_RESPONSE_FORM_NOT_SUPPORTED = -1032;
        public final static int INVALID_XPATH_EXPRESSION = -1033;

        public final static int EXPORTING_DYNAMIC_PAGE_DATA_ERROR = -1034;

    }

    public static class WARNING {
        public final static int DY_NO_PARAMETERS_PARSED = -2;
    }

}
