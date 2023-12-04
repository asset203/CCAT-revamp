/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.dynamic_page.defines;

/**
 *
 * @author Mahmoud Shehab
 */
public class Defines {

    public static class ContextPaths {

        public static final String BASE_CONTEXT_PATH = "/ccat";
        public static final String DYNAMIC_PAGE = "/dynamic-pages";
        public static final String DYNAMIC_PAGE_STEP = "/dynamic-page-step";
        public static final String DATABASE_MANAGEMENT = "/database-management";
        public static final String TEST_DB_CONNECTION = "/test-db-connection";
        public static final String PARSE_QUERY = "/parse-query";
        public static final String PARSE_RESPONSE_PARAMETERS = "/parse-response-parameters";

        public static final String HTTP_MANAGEMENT = "/http-management";
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
        public static final String VIEW = "/view";
        public static final String EXECUTE = "/execute";
        public static final String IMPORT = "/import";
        public static final String EXPORT = "/export";

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
}
