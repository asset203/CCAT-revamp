package com.asset.ccat.simulators_service.defines;

public class Defines {

    private Defines() {
    }

    public static class ContextPaths {
        public static final String AIR_SERVERS = "/air-servers";
        public static final String CI_SERVERS = "/ci-servers";
    }

    public static class WEB_ACTIONS {

        public static final String GET = "/get";
        public static final String GET_ALL = "/get-all";
        public static final String ADD = "/add";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        public static final String UPLOAD = "/upload";
        public static final String DOWNLOAD = "/download";
        public static final String SUBMIT = "/submit";
        public static final String CHECK = "/check";
    }
    public static class SEVERITY {
        public static final Integer CLEAR = 0;
        public static final Integer VALIDATION = 1;
        public static final Integer ERROR = 2;
        public static final Integer FATAL = 3;
    }
}
