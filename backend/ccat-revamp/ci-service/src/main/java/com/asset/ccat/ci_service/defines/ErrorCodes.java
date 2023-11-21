package com.asset.ccat.ci_service.defines;

/**
 *
 * @author Mahmoud Shehab
 */
public class ErrorCodes {

    public static class SUCCESS {

        public final static int SUCCESS = 0;
    }

    public static class ERROR {

        public final static int NO_COMMANDS_FOUND = -500;
        public final static int EXPIRED_TOKEN = -501;
        public final static int NOT_AUTHORIZED = -502;
        public final static int INVALID_TOKEN = -503;
        public final static int UNKNOWN_ERROR = -505;
        public final static int AIR_RESPONSE_NULL = -506;
        public final static int INVALID_AIR_RESPONSE = -507;
        public final static int SUBSCRIBER_NOT_FOUND = -508;
        public final static int NO_DATA_FOUND = -509;
        public final static int CI_UNREACHABLE = -510;
        public final static int ERROR_WHILE_PARSING_REQUEST = -511;
        public final static int UNREACHABLE_EXTERNAL_SERVICE = -512;
        public final static int SC_FRIENDS_MIGRATION_FAILURE = -513;
        public final static int SC_MIGRATION_FROM_7EXTRA_MB_FAILURE = -514;
        public final static int SC_MIGRATION_FROM_7EXTRA_FAILURE = -515;
        public final static int SC_MIGRATION_FROM_7BOKRA_FAILURE = -516;
        public final static int SC_DA_CLEARANCE_FAILURE = -517;
        public final static int LOOKUP_SERVER_UNREACHABLE = -518;
        public final static int INVALID_CI_RESPONSE = -519;
        public final static int EMPTY_CI_RESPONSE = -520;
        public final static int USER_MANAGEMENT_UNREACHABLE = -521;
        public final static int MISSING_CONFIGURATION = -522;

    }

}
