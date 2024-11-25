package com.asset.ccat.history_service.defines;

/**
 *
 * @author wael.mohamed
 */
public class ErrorCodes {

    public static class SUCCESS {

        public final static int SUCCESS = 0;
    }

    public static class ERROR {

        public final static int UNKNOWN_ERROR = -600;
        public final static int EXPIRED_TOKEN = -601;
        public final static int NOT_AUTHORIZED = -602;
        public final static int INVALID_TOKEN = -603;
        public final static int NO_DATA_FOUND = -604;
        public final static int RESPONSE_NULL = -605;
        public final static int NO_COMMANDS_FOUND = -606;
        public final static int MISSING_FIELD = -607;
        public final static int DATABASE_ERROR = -608;
        public final static int MAX_NO_OF_TRANSACTIONS_REACHED = -609;
    }

    public static class WARN {

        public final static int USER_NAME_NOT_SET = -1;
        public final static int REQUIRED_FIELD = -2;
        public final static int INVALID_FIELD = -3;

    }

}
