package com.asset.ccat.notification_service.defines;

public class Defines {

    public static final int CS_ARABIC_LANGUEGE_ID = 1;
    public static final int CCAT_ARABIC_LANGUAGE_ID = 1;
    public static final int CS_ENGLISH_LANGUAGE_ID = 0;
    public static final int CCAT_ENGLISH_LANGUAGE_ID = 2;

    public static class ContextPaths {

        public static final String SMS_TEMPLATE_CS = "/sms-template-cs";
        public static final String SMS_ACTIONS = "/sms-actions";
        public static final String SMS_ACTION_PARAM_MAP = "/sms-action-param-map";
        public static final String SEND_SMS = "/send-sms";

    }

    public static class LOOKUP_SERVICE {

        public static final String CONTEXT_PATH = "/lookup-service";
        public static final String LOOKUPS = "/lookups";
        public static final String CACHED_LOOKUPS = "/cached-lookups";
        public static final String USER = "/user";
        public static final String NOTEPAD = "/notepad";
        public static final String LANGUAGE = "/language";
        public static final String SERVICE_CLASSES = "/service-classes";

        public static final String SEQUENCE = "/sequence";
        public static final String DED_ACCOUNT = "/ded-account";
        public static final String ACCUMULATOR = "/accumulator";
        public static final String BARRING_REASON = "/barring-reason";
        //Lookup requests paths
        public static final String FEATURES = "/features";
        public static final String MENUS = "/menus";
        public static final String MONETARY_LIMITS = "/monetary-limits";
        public static final String OFFERS = "/offers";
        public static final String HLR_PROFILES = "/hlr-profiles";
        public static final String ACCOUNT_GROUPS = "/account-groups";
        public static final String SMS_ACTIONS = "/sms-actions";
        public static final String SMS_ACTION_PARAM_MAP = "/sms-action-param-map";
        public static final String ACCOUNT_GROUPS_BITS_DESCRIPTION = "/account-groups-bits-description";

        public static final String SERVICE_OFFERING_DESCRIPTION = "/service-offering-description";
        public static final String COMMUNITIES = "/communities";

        public static final String SERVICE_OFFERING_PLANS = "/service-offering-plans";

        public static final String CALL_ACTIVITY_ADMIN = "/call-activity-admin";
        public static final String SERVICE_OFFERING = "/service-offering";

        public static final String SO_SC_DESC = "/so-sc-desc";

        public static final String BUSINESS_PLANS = "/business-plans";
        public static final String FAF_PLANS = "/faf-plans";
        public static final String FAF_WHITE_LIST = "/faf-white-list";
        public static final String FAF_BLACK_LIST = "/faf-black-list";
        public static final String ACTION_TYPES = "/action-types";
        public static final String ACTION_NAMES = "/action-names";
        public static final String FOOTPRINT_PAGES = "/footprint-pages";
        public static final String MARED_CARDS = "/mared-cards";

        public static final String TRANSACTIONS = "/transactions";
        public static final String TYPE = "/type";
        public static final String CODE = "/code";

        public static final String LINK = "/link";
        public static final String LINK_DESCRIPTION = "/link-description";
        public static final String TRANSACTION_ADMIN = "/transaction-admin";

        public static final String PAMS = "/pams";
        public static final String PAM_CLASS = "/pam-class";
        public static final String PAM_SERVICE = "/pam-service";
        public static final String PAM_PERIOD = "/pam-period";
        public static final String PAM_PRIORITY = "/pam-priority";
        public static final String PAM_SCHEDULE = "/pam-schedule";
        public static final String DISCONNECTION_CODE = "/disconnection-code";
        public static final String CALL_ACTIVITIES = "/call-activities";
        public static final String SMS_TEMPLATE_CS = "/sms-template-cs";
        public static final String SMS_TEMPLATE = "/sms-templates";
        public static final String SERVICE_OFFERING_PLANS_BIT_DETAILS = "/service-offering-plans-bit-details";
        public static final String SERVICE_OFFERING_BITS_DETAILS = "/service-offering-bits-details";
        public static final String ACCOUNT_GROUPS_WITH_BITS = "/account-groups-with-bits";
    }

    public static class WEB_ACTIONS {

        public static final String GET = "/get";
        public static final String GET_ALL = "/get-all";
        public static final String ADD = "/add";

        public static final String SEND = "/send";

        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        public static final String UPLOAD = "/upload";
        public static final String DOWNLOAD = "/download";
        public static final String EXPORT = "/export";
        public static final String IMPORT = "/import";
    }

    public static class SEVERITY {
        public static final Integer CLEAR = 0;
        public static final Integer VALIDATION = 1;
        public static final Integer ERROR = 2;
        public static final Integer FATAL = 3;
    }

    public static class CONTACT_STRATEGY_PARAMETER {
        public static final String ORIGINATOR_MSISDN = "OriginatorMSISDN";
        public static final String DESTINATION_MSISDN = "DestinationMSISDN";
        public static final String MESSAGE_TEXT = "MessageText";
        public static final String LANGUAGE = "Language";
        public static final String SYSTEM_NAME = "SystemName";
        public static final String SYSTEM_PASSWORD = "SystemPassword";
        public static final String MESSAGE_TYPE = "MessageType";
        public static final String ORIGINATOR_TYPE = "OriginatorType";
        public static final String DO_NOT_APPLY = "DoNotApply";
        public static final String TEMPLATES_PARAMETERS = "TemplatesParameters";
        public static final String TEMPLATES_IDS = "TemplatesIds";

    }
}
