package com.asset.ccat.history_service.defines;

/**
 * @author Mahmoud Shehab
 */
public class Defines {

    public static class ContextPaths {

        public static final String BASE_CONTEXT_PATH = "/ccat";
        public static final String NOTEPAD = "/notepad";
        public static final String SUBSCRIBER_ADJUSTMENT = "/subscriber-adjustment";
        public static final String ADMIN_FOOTPRINT_REPORT = "/admin-footprint-report";
    }

    public static class WEB_ACTIONS {

        public static final String GET = "/get";
        public static final String GET_ALL = "/get-all";
        public static final String ADD = "/add";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        public static final String UPLOAD = "/upload";
        public static final String DOWNLOAD = "/download";
        public static final String CHECK = "/check";
        public static final String EXPORT = "/export";
    }

    public static class SecurityKeywords {

        public static final String USERNAME = "username";
        public static final String PREFIX = "prefix";
    }

    public static class SEVERITY {

        public static final Integer CLEAR = 0;
        public static final Integer VALIDATION = 1;
        public static final Integer ERROR = 2;
        public static final Integer FATAL = 3;
    }

    public static class RABBIT_MQ {

        public static final String EXCHANGE_NAME = "CCAT_Default_Exchange";
        public static final String FOOTPRINT_QUEUE = "Q_FOOTPRINT_LOG";
        public static final String TX_ADJUSTMENT_QUEUE = "TX_ADJUSTMENT_LOG";
        public static final String TX_LOGIN_QUEUE = "TX_LOGIN_LOG";
    }

}
