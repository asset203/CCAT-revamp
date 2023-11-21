package com.asset.ccat.notification_service.defines;

/**
 * @author Assem.Hassan
 */
public class DatabaseStructs {

    public static final class SEQUENCE {
        public final static String S_SMS_TEMPLATE = "S_SMS_TEMPLATE";
    }


    public static final class LK_SMS_ACTION {
        public static final String TABLE_NAME = "LK_SMS_ACTION";
        public static final String SMS_ACTION_ID = "SMS_ACTION_ID";
        public static final String ACTION_NAME = "ACTION_NAME";
        public static final String DESCRIPTION = "DESCRIPTION";
        public static final String CODE = "CODE";
    }

    public static final class LK_SMS_TEMPLATE_PARAM {

        public static final String TABLE_NAME = "LK_SMS_TEMPLATE_PARAM";
        public static final String PARAMETER_ID = "PARAMETER_ID";
        public static final String PARAMETER_NAME = "PARAMETER_NAME";
        public static final String SMS_ACTION_ID = "SMS_ACTION_ID";
    }

    public static final class ADM_SMS_TEMPLATE {

        public static final String TABLE_NAME = "ADM_SMS_TEMPLATE";
        public static final String ID = "ID";
        public static final String TEMPLATE_ID = "TEMPLATE_ID";
        public static final String LANGUAGE_ID = "LANGUAGE_ID";
        public static final String LANGUAGE_NAME = "LANGUAGE_NAME";
        public static final String ACTION_NAME = "ACTION_NAME";
        public static final String CS_TEMPLATE_ID = "CS_TEMPLATE_ID";
        public static final String TEMPLATE_TEXT = "TEMPLATE_TEXT";
        public static final String TEMPLATE_PARAMETERS = "TEMPLATE_PARAMETERS";
    }

    public static final class LK_LANGUAGES {
        public final static String TABLE_NAME = "LK_LANGUAGES";
        public final static String ID = "ID";
        public final static String NAME = "NAME";
    }
}
