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
public class DatabaseStructs {

    public static final class ADM_USERS {

        public static final String TABLE_NAME = "ADM_USERS";
        public static final String TABLE_ALIAS = "USERS";
        public static final String USER_ID = "USER_ID";
        public static final String NT_ACCOUNT = "NT_ACCOUNT";
        public static final String PROFILE_ID = "PROFILE_ID";
        public static final String IS_DELETED = "IS_DELETED";
        public static final String SOURCE = "SOURCE";
        public static final String SESSION_COUNTER = "SESSION_COUNTER";
        public static final String THEME_ID = "THEME_ID";
        public static final String CREATION_DATE = "CREATION_DATE";
        public static final String MODIFICATION_DATE = "MODIFICATION_DATE";
        public static final String LAST_LOGIN = "LAST_LOGIN";

        public static final String SEQUENCE_NAME = "SEQ_USERS";

    }

    public static final class ADM_PROFILES {

        public static final String TABLE_NAME = "ADM_PROFILES";
        public static final String TABLE_ALIAS = "PROFILES";
        public static final String PROFILE_ID = "PROFILE_ID";
        public static final String PROFILE_NAME = "PROFILE_NAME";
        public static final String IS_DELETED = "IS_DELETED";
        public static final String IS_FOOTPRINT_REQUIRED = "IS_FOOTPRINT_REQUIRED";
        public static final String SESSION_LIMIT = "SESSION_LIMIT";
        public static final String ADJUSTMENTS_LIMITED = "ADJUSTMENTS_LIMITED";
        public static final String CREATION_DATE = "CREATION_DATE";

        public static final String SEQUENCE_NAME = "SEQ_PROFILES";
    }

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

    }

    public static final class TX_USER_MONETARY_TOTALS {

        public static final String TABLE_NAME = "TX_USER_MONETARY_TOTALS";
        public static final String LIMIT_ID = "LIMIT_ID";
        public static final String USER_ID = "USER_ID";
        public static final String CURR_DATE = "CURR_DATE";
        public static final String CURR_VALUE = "CURR_VALUE";
    }

    public static final class LK_MONETARY_LIMITS {

        public static final String TABLE_NAME = "LK_MONETARY_LIMITS";
        public static final String LIMIT_ID = "LIMIT_ID";
        public static final String LIMIT_NAME = "LIMIT_NAME";
        public static final String DEFAULT_VALUE = "DEFAULT_VALUE";

    }

    public static final class ADM_PROFILE_MONETARY_LIMIT {

        public static final String TABLE_NAME = "ADM_PROFILE_MONETARY_LIMIT";
        public static final String PROFILE_ID = "PROFILE_ID";
        public static final String LIMIT_ID = "LIMIT_ID";
        public static final String VALUE = "VALUE";

    }

    public static final class ADM_SERVICE_CLASSES {

        public static final String TABLE_NAME = "ADM_SERVICE_CLASSES";
        public static final String NAME = "NAME";
        public static final String CODE = "CODE";
        public static final String MAX_BALANCE = "MAX_BALANCE";
        public static final String MAX_DA_BALANCE = "MAX_DA_BALANCE";
        public static final String MAX_SUPERVISION = "MAX_SUPERVISION";
        public static final String MAX_SERVICE_FEE = "MAX_SERVICE_FEE";
        public static final String PREACTIVATION_ALLOWED = "PREACTIVATION_ALLOWED";
        public static final String IS_DELETED = "IS_DELETED";
        public static final String IS_SD = "IS_SD";
        public static final String IS_CI_CONVERSION = "IS_CI_CONVERSION";
        public static final String CI_SERVICE_NAME = "CI_SERVICE_NAME";
        public static final String CI_PACKAGE_NAME = "CI_PACKAGE_NAME";
        public static final String IS_ALLOW_MIGRATION = "IS_ALLOW_MIGRATION";
        public static final String TYPE = "TYPE";
        public static final String ARABIC_NAME = "ARABIC_NAME";
        public static final String HAS_EMPTY_LIMIT = "HAS_EMPTY_LIMIT";
        public static final String DEFAULT_LIMIT = "DEFAULT_LIMIT";
        public static final String CUSTOM_LIMIT = "CUSTOM_LIMIT";

    }

    public static final class ADM_PROFILE_SERVICE_CLASS {

        public static final String TABLE_NAME = "ADM_PROFILE_SERVICE_CLASS";
        public static final String PROFILE_ID = "PROFILE_ID";
        public static final String SERVICE_CLASS_ID = "SERVICE_CLASS_ID";
    }

    public static final class ADM_MARQUEE_DATA {

        public static final String TABLE_NAME = "ADM_MARQUEE_DATA";
        public static final String ID = "ID";
        public static final String TITLE = "TITLE";
        public static final String DESCRIPTION = "DESCRIPTION";
        public static final String CREATION_DATE = "CREATION_DATE";
    }

    public static final class TX_CALL_REASONS {
        public static final String TABLE_NAME = "TX_CALL_REASONS";
        public static final String USER_ID = "USER_ID";
        public static final String USER_NAME = "USER_NAME";
        public static final String MSISDN = "MSISDN";
        public static final String MSISDN_LAST_DIGIT = "MSISDN_LAST_DIGIT";
        public static final String ENTRY_DATE = "ENTRY_DATE";
        public static final String DIRECTION = "DIRECTION";
        public static final String FAMILY = "FAMILY";
        public static final String TYPE = "TYPE";
        public static final String REASON = "REASON";
        public static final String CALL_REASON_ID = "CALL_REASON_ID";
        public static final String SEQUENCE_NAME = "SEQ_TX_CALL_REASONS";

    }

    public static final class ADM_PROFILE_SOB {
        public static final String TABLE_NAME = "ADM_PROFILE_SOB";
        public static final String PROFILE_ID = "PROFILE_ID";
        public static final String SERVICE_OFFERING_BITS = "SERVICE_OFFERING_BITS";
    }

}
