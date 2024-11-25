package com.asset.ccat.ods_service.defines;

/**
 *
 * @author Mahmoud Shehab
 */
public class ErrorCodes {

    public static class SUCCESS {

        public final static int SUCCESS = 0;
    }

    public static class ERROR {

        public final static int EXPIRED_TOKEN = -801;
        public final static int NOT_AUTHORIZED = -802;
        public final static int INVALID_TOKEN = -803;
        public final static int UNKNOWN_ERROR = -804;
        public final static int DATABASE_ERROR = -805;
        public final static int NO_DATA_FOUND = -806;
        public final static int UNREACHABLE_LOOKUPS_SERVICE = -807;
        public final static int DECRYPTION_FAILED = -808;
        public final static int ENCRYPTION_FAILED = -809;
        public final static int INVALID_DATA_SOURCE = -810;

    }

}
