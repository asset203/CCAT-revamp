/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.dynamic_page.defines;

/**
 * @author Mahmoud Shehab
 */
public class DatabaseStructs {

    public static final class ADM_PROFILE_FEATURES {

        public static final String TABLE_NAME = "ADM_PROFILE_FEATURES";
        public static final String PROFILE_ID = "PROFILE_ID";
        public static final String FEATURE_ID = "FEATURE_ID";
        public static final String ORDERING = "ORDERING";

    }

    public static final class LK_FEATURES {

        public static final String TABLE_NAME = "LK_FEATURES";
        public static final String ID = "ID";
        public static final String NAME = "NAME";
        public static final String PAGE_NAME = "PAGE_NAME";
        public static final String TYPE = "TYPE";
        public static final String URL = "URL";
        public static final String LABEL = "LABEL";
        public static final String ICON = "ICON";
        public static final String MENU_ID = "MENU_ID";
    }

    public static final class LK_MENUS {

        public static final String TABLE_NAME = "LK_MENUS";
        public static final String TABLE_ALIAS = "m";
        public static final String SEQ = "MENU_SEQ";
        public static final String MENU_ID = "MENU_ID";
        public static final String LABEL = "LABEL";
        public static final String PARENT_ID = "PARENT_ID";
        public static final String ROUTER = "ROUTER";
        public static final String ORDERING = "ORDERING";
        public static final String ICON = "ICON";
    }

    public static final class DYN_PAGES {

        public static final String TABLE_NAME = "DYN_PAGES";
        public static final String ID = "PAGE_ID";
        public static final String PAGE_NAME = "PAGE_NAME";
        public static final String PRIVILEGE_ID = "PRIVILEGE_ID";
        public static final String PRIVILEGE_NAME = "PRIVILEGE_NAME";
        public static final String REQUIRE_SUBSCRIBER = "REQUIRE_SUBSCRIBER";
        public static final String IS_DELETED = "IS_DELETED";
        public static final String CREATION_DATE = "CREATION_DATE";
        public static final String SEQUENCE_NAME = "SEQ_DYN_PAGES";

    }

    public static final class DYN_PAGES_STEPS {

        public static final String TABLE_NAME = "DYN_PAGES_STEPS";
        public static final String ID = "STEP_ID";
        public static final String PAGE_ID = "PAGE_ID";
        public static final String STEP_TYPE = "STEP_TYPE";
        public static final String STEP_NAME = "STEP_NAME";
        public static final String STEP_ORDER = "STEP_ORDER";
        public static final String IS_DELETED = "IS_DELETED";
        public static final String IS_HIDDEN = "IS_HIDDEN";
        public static final String SEQUENCE_NAME = "SEQ_DYN_PAGES_STEPS";

    }

    public static final class DYN_STEP_SP_CONFIGURATION {

        public static final String TABLE_NAME = "DYN_STEP_SP_CONFIGURATION";
        public static final String ID = "CONFIG_ID";
        public static final String STEP_ID = "STEP_ID";
        public static final String DB_URL = "DB_URL";
        public static final String DB_USERNAME = "DB_USERNAME";
        public static final String DB_PASSWORD = "DB_PASSWORD";
        public static final String PROCEDURE_NAME = "PROCEDURE_NAME";
        public static final String MAX_RECORDS = "MAX_RECORDS";
        public static final String SUCCESS_CODE = "SUCCESS_CODE";
        public static final String IS_DELETED = "IS_DELETED";
        public static final String EXTRA_CONFIGURATIONS = "EXTRA_CONFIGURATIONS";
        public static final String SEQUENCE_NAME = "SEQ_DYN_STEP_SP_CONFIGURATION";

    }

    public static final class DYN_STEP_SP_PARAMETERS {

        public static final String TABLE_NAME = "DYN_STEP_SP_PARAMETERS";
        public static final String ID = "PARAM_ID";
        public static final String CONFIG_ID = "CONFIG_ID";
        public static final String PARAMETER_NAME = "PARAMETER_NAME";
        public static final String PARAMETER_DATA_TYPE = "PARAMETER_DATA_TYPE";
        public static final String PARAMETER_TYPE = "PARAMETER_TYPE";
        public static final String PARAMETER_ORDER = "PARAMETER_ORDER";
        public static final String DISPLAY_ORDER = "DISPLAY_ORDER";
        public static final String INPUT_METHOD = "INPUT_METHOD";
        public static final String DEFAULT_VALUE = "DEFAULT_VALUE";
        public static final String IS_RESPONSE_CODE = "IS_RESPONSE_CODE";
        public static final String IS_RESPONSE_DESCRIPTION = "IS_RESPONSE_DESCRIPTION";
        public static final String DISPLAY_NAME = "DISPLAY_NAME";
        public static final String IS_HIDDEN = "IS_HIDDEN";
        public static final String SOURCE_STEP_PARAMETER_ID = "SOURCE_STEP_PARAMETER_ID";
        public static final String SOURCE_STEP_PARAMETER_NAME = "SOURCE_STEP_PARAMETER_NAME";
        public static final String IS_DELETED = "IS_DELETED";
        public static final String SEQUENCE_NAME = "SEQ_DYN_STEP_SP_PARAMETERS";
        public static final String DATE_FORMAT = "DATE_FORMAT";
        public static final String STATIC_DATA = "STATIC_DATA";
    }

    public static final class DYN_STEP_SP_CURSOR_MAPPING {

        public static final String TABLE_NAME = "DYN_STEP_SP_CURSOR_MAPPING";
        public static final String ID = "MAPPING_ID";
        public static final String PARAMETER_ID = "PARAMETER_ID";
        public static final String ACTUAL_COLUMN_NAME = "ACTUAL_COLUMN_NAME";
        public static final String DISPLAY_COLUMN_NAME = "DISPLAY_COLUMN_NAME";
        public static final String IS_DELETED = "IS_DELETED";
        public static final String SEQUENCE_NAME = "SEQ_DYN_STEP_SP_CURSOR_MAPPING";
        public static final String DATE_FORMAT = "CURSOR_DATE_FORMAT";
        public static final String DATA_TYPE = "CURSOR_DATA_TYPE";
        public static final String DISPLAY_ORDER = "CURSOR_DISPLAY_ORDER";

    }


    public static final class DYN_STEP_HTTP_CONFIGURATION {

        public static final String TABLE_NAME = "DYN_STEP_HTTP_CONFIGURATION";
        public static final String ID = "CONFIG_ID";
        public static final String STEP_ID = "STEP_ID";
        public static final String HTTP_URL = "HTTP_URL";
        public static final String HEADERS = "HEADERS";
        public static final String HTTP_METHOD = "HTTP_METHOD";
        public static final String RESPONSE_FORM = "RESPONSE_FORM";
        public static final String REQUEST_CONTENT_TYPE = "REQUEST_CONTENT_TYPE";
        public static final String RESPONSE_CONTENT_TYPE = "RESPONSE_CONTENT_TYPE";
        public static final String REQUEST_BODY = "REQUEST_BODY";
        public static final String KEY_VALUE_DELIMITER = "KEY_VALUE_DELIMITER";
        public static final String MAIN_DELIMITER = "MAIN_DELIMITER";
        public static final String MAX_RECORDS = "MAX_RECORDS";
        public static final String SUCCESS_CODE = "SUCCESS_CODE";
        public static final String IS_DELETED = "IS_DELETED";
        public static final String SEQUENCE_NAME = "SEQ_DYN_STEP_HTTP_CONFIGURATION";

    }


    public static final class DYN_STEP_HTTP_PARAMETERS {

        public static final String TABLE_NAME = "DYN_STEP_HTTP_PARAMETERS";
        public static final String ID = "PARAM_ID";
        public static final String CONFIG_ID = "CONFIG_ID";
        public static final String PARAMETER_NAME = "PARAMETER_NAME";
        public static final String PARAMETER_DATA_TYPE = "PARAMETER_DATA_TYPE";
        public static final String PARAMETER_TYPE = "PARAMETER_TYPE";
        public static final String PARAMETER_ORDER = "PARAMETER_ORDER";
        public static final String DISPLAY_ORDER = "DISPLAY_ORDER";
        public static final String INPUT_METHOD = "INPUT_METHOD";
        public static final String DEFAULT_VALUE = "DEFAULT_VALUE";
        public static final String IS_RESPONSE_CODE = "IS_RESPONSE_CODE";
        public static final String IS_RESPONSE_DESCRIPTION = "IS_RESPONSE_DESCRIPTION";
        public static final String DISPLAY_NAME = "DISPLAY_NAME";
        public static final String IS_HIDDEN = "IS_HIDDEN";
        public static final String SOURCE_STEP_PARAMETER_ID = "SOURCE_STEP_PARAMETER_ID";
        public static final String SOURCE_STEP_PARAMETER_NAME = "SOURCE_STEP_PARAMETER_NAME";
        public static final String IS_DELETED = "IS_DELETED";
        public static final String SEQUENCE_NAME = "SEQ_DYN_STEP_HTTP_PARAMETERS";
        public static final String DATE_FORMAT = "DATE_FORMAT";
        public static final String STATIC_DATA = "STATIC_DATA";
        public static final String XPATH = "XPATH";
        public static final String JSON_PATH = "JSON_PATH";
    }

    public static final class DYN_STEP_HTTP_RESPONSE_MAPPING {

        public static final String TABLE_NAME = "DYN_STEP_HTTP_RESPONSE_MAPPING";
        public static final String ID = "MAPPING_ID";
        public static final String PARAMETER_ID = "PARAMETER_ID";
        public static final String HEADER_NAME = "HEADER_NAME";
        public static final String HEADER_DISPLAY_NAME = "HEADER_DISPLAY_NAME";
        public static final String IS_DELETED = "IS_DELETED";
        public static final String SEQUENCE_NAME = "SEQ_DYN_STEP_HTTP_RESPONSE_MAPPING";
    }


}
