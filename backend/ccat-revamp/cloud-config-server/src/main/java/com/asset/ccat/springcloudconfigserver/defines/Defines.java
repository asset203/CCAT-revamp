package com.asset.ccat.springcloudconfigserver.defines;

public class Defines {

    public static final class ContextPaths{
        public static final String SYSTEM_CONFIGURATIONS = "system-configurations";

    }
    public static class WEB_ACTIONS {

        public static final String LOGIN = "/login";
        public static final String GET = "/get";
        public static final String GET_ALL = "/get-all";
        public static final String ADD = "/add";
        public static final String UPDATE = "/update";
        public static final String UPDATE_ALL = "/update-all";
        public static final String DELETE = "/delete";
        public static final String DELETE_ALL = "/delete-all";
    }

    public static class SecurityKeywords {

        public static final String USERNAME = "username";
        public static final String PREFIX = "prefix";
        public static final String SESSION_ID = "sessionId";
        public static final String PROFILE_ROLE = "profile";
        public static final String USER_ID = "userId";
        public static final String PROFILE_ID = "profileId";
    }

    public static class SEVERITY {

        public static final Integer CLEAR = 0;
        public static final Integer VALIDATION = 1;
        public static final Integer ERROR = 2;
        public static final Integer FATAL = 3;
    }
}
