package com.asset.ccat.balance_dispute_service.defines;


public class ErrorCodes {

    public static class SUCCESS {

        public final static int SUCCESS = 0;
    }

    public static class ERROR {
        public final static int NO_REPORTS_FOUND = -2000;
        public final static int EXPIRED_TOKEN = -2001;
        public final static int NOT_AUTHORIZED = -2002;
        public final static int INVALID_TOKEN = -2003;
        public final static int DATABASE_ERROR = -2004;
        public final static int UNKNOWN_ERROR = -2005;
        public final static int CANNOT_GENERATE_TOKEN = -2005;

        public static final int INTERNAL_SERVICE_UNREACHABLE = -2006;
        public final static int EXPORT_FAILED = -2007;
        public final static int NO_USAGE_FOUND = -2008;
    }

    public static class FLEX_SHARE_ERRORS {
        public final static int PROCESS_FAILURE = 1;
    }
    public static class FLEX_SHARE_SUCCESS {
        public final static Integer SUCCESS = 1;
    }



}
