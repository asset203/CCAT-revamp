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

        public static final String S_TX_SUBSCRIBER_ADJUSTMENTS = "S_TX_SUBSCRIBER_ADJUSTMENTS";
        public static final String S_TX_USER_FOOTPRINT = "S_TX_USER_FOOTPRINT";
    }

    public static class H_NOTEPAD_ENTRIES {

        public static final String TABLE_NAME = "H_NOTEPAD_ENTRIES";
        public static final String ENTRY_ID = "ENTRY_ID";
        public static final String ENTRY_DATE = "ENTRY_DATE";
        public static final String MSISDN = "MSISDN";
        public static final String MSISDN_MOD_X = "MSISDN_MOD_X";
        public static final String NOTEPAD_ENTRY = "NOTEPAD_ENTRY";
        public static final String NOTEPAD_USERNAME = "USER_NAME";
        public static final String USER_ID = "USER_ID";
        public static final String PAGE_NAME = "PAGE_NAME";
    }

    public static class TX_USER_FOOTPRINT {

        public static final String TABLE_NAME = "TX_USER_FOOTPRINT";

        public static final String ID = "ID";
        public static final String ACTION_TIME = "ACTION_TIME";
        public static final String CREATED_BY = "CREATED_BY";
        public static final String REQUEST_ID = "REQUEST_ID";
        public static final String PAGE_NAME = "PAGE_NAME";
        public static final String TAB_NAME = "TAB_NAME";
        public static final String ACTION_NAME = "ACTION_NAME";
        public static final String ACTION_TYPE = "ACTION_TYPE";
        public static final String USERNAME = "USERNAME";
        public static final String PROFILE_NAME = "PROFILE_NAME";
        public static final String MSISDN = "MSISDN";
        public static final String STATUS = "STATUS";
        public static final String ERROR_MESSAGE = "ERROR_MESSAGE";
        public static final String ERROR_CODE = "ERROR_CODE";
        public static final String SESSION_ID = "SESSION_ID";
        public static final String MACHINE_NAME = "MACHINE_NAME";
        public static final String SEND_SMS = "SEND_SMS";
    }

    public static class TX_USER_FOOTPRINT_DETAILS {

        public static final String TABLE_NAME = "TX_USER_FOOTPRINT_DETAILS";


        public static final String REQUEST_ID = "REQUEST_ID";
        public static final String PARAM_NAME = "PARAM_NAME";
        public static final String OLD_VALUE = "OLD_VAL";
        public static final String NEW_VALUE = "NEW_VAL";
    }

    public static class TX_LOGIN {

        public static final String TABLE_NAME = "TX_LOGIN";
        public static final String ID = "TX_LOGIN_ID";
        public static final String MACHINE_NAME = "MACHINE_NAME";
        public static final String DOMAIN_NAME = "DOMAIN_NAME";
        public static final String STATUS = "STATUS";
        public static final String MESSAGE = "MESSAGE";
        public static final String USER_ID = "USER_ID";
        public static final String LOGIN_TIME = "LOGIN_TIME";

    }

    public static class TX_SUBSCRIBER_ADJUSTMENTS {

        public static final String TABLE_NAME = "TX_SUBSCRIBER_ADJUSTMENTS";
        public static final String ID = "ID";
        public static final String MSISDN = "MSISDN";
        public static final String MSISDN_MOD_X = "MSISDN_MOD_X";
        public static final String ADJ_DATE = "ADJ_DATE";

    }
}
