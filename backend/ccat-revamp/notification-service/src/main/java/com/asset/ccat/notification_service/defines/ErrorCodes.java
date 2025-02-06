package com.asset.ccat.notification_service.defines;

public class ErrorCodes {

    public static class SUCCESS {

        public final static int SUCCESS = 0;
    }

    public static class ERROR {

        public final static int INTERNAL_SERVICE_UNREACHABLE = -3001;
        public final static int DATABASE_ERROR = -3003;
        public final static int GENERAL_ERROR = -3004;
        public final static int NO_DATA_FOUND = -3005;
        public final static int SYSTEM_UNREACHABLE = -3006;
        public final static int DELETE_FAILED = -3007;
        public final static int NO_TEMPLATE_FOUND = -3008;

        public final static int INVALID_PARAMETER = -3009;

        public final static int SMS_PARAMETERS_PARSING_FAILURE = -3010;
        public final static int SMS_TEMPLATE_ALREADY_EXISTS = -3011;

    }
}
