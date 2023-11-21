/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.user_management.defines;

/**
 *
 * @author Mahmoud Shehab
 */
public class Defines {

    public static class ContextPaths {

        public static final String BASE_CONTEXT_PATH = "/ccat";
        public static final String LOGIN = "/login";
        public static final String CONFIGURATIONS = "/configurations";
        public static final String SUBSCRIBER_ACCOUNT = "/subscriber-account";
        public static final String SUBSCRIBER_MAIN_PRODUCT = "/main-product";
        public static final String USER = "/user";
        public static final String PROFILE = "/profile";
        public static final String MARQUEES = "/marquees";
        public static final String LIMITS = "/limits";
        public static final String PROFILE_USERS = "/profile-users";
        public static final String USERS_PROFILES = "/users-profile";
        public static final String PROFILE_FEATURES = "/profile-features";
        public static final String USER_PRIVILEGE = "/user-privilege";

        public static final String CALL_REASON = "/call-reason";
    }

    public static class WEB_ACTIONS {

        public static final String GET = "/get";
        public static final String GET_ALL = "/get-all";
        public static final String ADD = "/add";
        public static final String UPDATE = "/update";
        public static final String UPDATE_ALL = "/update-all";
        public static final String DELETE = "/delete";
        public static final String DELETE_ALL = "/delete-all";
        public static final String UPLOAD = "/upload";
        public static final String DOWNLOAD = "/download";
        public static final String CHECK = "/check";
    }

    public static class SecurityKeywords {

        public static final String USERNAME = "username";
        public static final String PREFIX = "prefix";
        public static final String SESSION_ID = "sessionId";
        public static final String PROFILE_ROLE = "profile";
        public static final String PROFILE_ID = "profileId";
        public static final String USER_ID = "userId";
    }

    public static class SEVERITY {

        public static final Integer CLEAR = 0;
        public static final Integer VALIDATION = 1;
        public static final Integer ERROR = 2;
        public static final Integer FATAL = 3;
    }

    public static class FILE {

        public static class USER {

            public static class HEADERS {

                public static final String NT_ACCOUNT = "ntAccount";
                public static final String BILLING_ACCOUNT = "billingAccount";
                public static final String PROFILE_NAME = "profileName";

            }
        }
    }
}
