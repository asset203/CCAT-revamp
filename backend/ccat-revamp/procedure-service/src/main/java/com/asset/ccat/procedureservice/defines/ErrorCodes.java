package com.asset.ccat.procedureservice.defines;


public class ErrorCodes {

    public static class SUCCESS {

        public final static int SUCCESS = 0;
    }

    public static class ERROR {

        public final static int EXPIRED_TOKEN = -2001;
        public final static int NOT_AUTHORIZED = -2002;
        public final static int INVALID_TOKEN = -2003;
        public final static int UNKNOWN_ERROR = -2005;
        public final static int DATABASE_ERROR = -2004;

    }

    public static class FLEX_SHARE_ERRORS {
        public final static int PROCESS_FAILURE = 1;
    }
    public static class FLEX_SHARE_SUCCESS {
        public final static Integer SUCCESS = 1;
    }



}
