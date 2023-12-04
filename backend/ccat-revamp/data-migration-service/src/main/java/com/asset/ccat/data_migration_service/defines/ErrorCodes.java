package com.asset.ccat.data_migration_service.defines;

public class ErrorCodes {

    public static class SUCCESS {

        public final static int SUCCESS = 0;
    }

    public static class ERROR {

        public final static int INVALID_PARAMETER = -3002;
        public final static int DATABASE_ERROR = -3003;
        public final static int GENERAL_ERROR = -3004;
        public final static int NO_DATA_FOUND = -3005;
        public final static int NO_FILES_FOUND = -3005;
        public final static int UNKNOWN_ERROR = -405;
        public final static int PARSING_FAILED = -417;
        public final static int MISSING_FIELD = -418;
        public final static int INVALID_INPUT = -419;
        public final static int UNSUPPORTED_FILE_TYPE = -420;
        public final static int EMPTY_FILE = -421;
        public final static int UNSUPPORTED_OPERATION_TYPE = -422;
        public final static int WRITING_FAILED = -423;
    }
}
