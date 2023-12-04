/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.history_service.defines;

/**
 * @author wael.mohamed
 */
public class DBStructs {

    public static final class SEQUENCE {

        public final static String S_TX_SUBSCRIBER_ADJUSTMENTS = "S_TX_SUBSCRIBER_ADJUSTMENTS";
        public final static String S_TX_USER_FOOTPRINT = "S_TX_USER_FOOTPRINT";
    }

    public static class H_NOTEPAD_ENTRIES {

        public final static String TABLE_NAME = "H_NOTEPAD_ENTRIES";
        public final static String ENTRY_ID = "ENTRY_ID";
        public final static String ENTRY_DATE = "ENTRY_DATE";
        public final static String MSISDN = "MSISDN";
        public final static String MSISDN_MOD_X = "MSISDN_MOD_X";
        public final static String NOTEPAD_ENTRY = "NOTEPAD_ENTRY";
        public final static String NOTEPAD_USERNAME = "USER_NAME";
        public final static String USER_ID = "USER_ID";
    }

    public static class TX_USER_FOOTPRINT {

        public final static String TABLE_NAME = "TX_USER_FOOTPRINT";

        public final static String ID = "ID";
        public final static String ACTION_TIME = "ACTION_TIME";
        public final static String CREATED_BY = "CREATED_BY";
        public final static String REQUEST_ID = "REQUEST_ID";
        public final static String PAGE_NAME = "PAGE_NAME";
        public final static String TAB_NAME = "TAB_NAME";
        public final static String ACTION_NAME = "ACTION_NAME";
        public final static String ACTION_TYPE = "ACTION_TYPE";
        public final static String USERNAME = "USERNAME";
        public final static String PROFILE_NAME = "PROFILE_NAME";
        public final static String MSISDN = "MSISDN";
        public final static String STATUS = "STATUS";
        public final static String ERROR_MESSAGE = "ERROR_MESSAGE";
        public final static String ERROR_CODE = "ERROR_CODE";
        public final static String SESSION_ID = "SESSION_ID";
        public final static String MACHINE_NAME = "MACHINE_NAME";
    }

    public static class TX_USER_FOOTPRINT_DETAILS {

        public final static String TABLE_NAME = "TX_USER_FOOTPRINT_DETAILS";


        public final static String REQUEST_ID = "REQUEST_ID";
        public final static String PARAM_NAME = "PARAM_NAME";
        public final static String OLD_VALUE = "OLD_VAL";
        public final static String NEW_VALUE = "NEW_VAL";
    }

    public static class TX_LOGIN {

        public final static String TABLE_NAME = "TX_LOGIN";
        public final static String ID = "TX_LOGIN_ID";
        public final static String MACHINE_NAME = "MACHINE_NAME";
        public final static String DOMAIN_NAME = "DOMAIN_NAME";
        public final static String STATUS = "STATUS";
        public final static String MESSAGE = "MESSAGE";
        public final static String USER_ID = "USER_ID";
        public final static String LOGIN_TIME = "LOGIN_TIME";

    }

    public static class TX_SUBSCRIBER_ADJUSTMENTS {

        public final static String TABLE_NAME = "TX_SUBSCRIBER_ADJUSTMENTS";
        public final static String ID = "ID";
        public final static String MSISDN = "MSISDN";
        public final static String MSISDN_MOD_X = "MSISDN_MOD_X";
        public final static String ADJ_DATE = "ADJ_DATE";

    }
}
