/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.defines;

/**
 * @author Mahmoud Shehab
 */
public class Defines {

    public static class ContextPaths {

        public static final String BASE_CONTEXT_PATH = "/ccat";
        public static final String CACHED_LOOKUPS = "/cached-lookups";
        public static final String LOOKUPS = "/lookups";
        public static final String AIR_SERVERS = "/air-servers";
        public static final String REGIONS = "/regions";
        public static final String PAGE = "/page";
        public static final String BD_DETAILS_CONFIG = "/bd-details-config";
        public static final String VOUCHER_SERVERS = "/voucher-servers";
        public static final String SERVICE_CLASSES = "/service-classes";
        public static final String BUSINESS_PLANS = "/business-plans";
        public static final String PROMOTION_PLANS = "/promotion-plans";
        public static final String CI_PREPAID_VBP = "/ci-prepaid-vbp";
        public static final String VIP = "/vip";

        public static final String SERVICE_OFFERING_DESCRIPTION = "/service-offering-description";

        public static final String ACCOUNT_GROUPS_BITS_DESCRIPTION = "/account-groups-bits-description";
        public static final String LANGUAGE = "/language";
        public static final String ELIGIBLE_SERVICE_CLASSES = "eligible-service-classes";
        public static final String REFILL_PROFILES = "/refill-profiles";
        public static final String OFFERS = "/offers";
        public static final String OFFERS_STATES = "/offers-states";
        public static final String OFFERS_TYPES = "/offers-types";
        public static final String FEATURES = "/features";
        public static final String MENUS = "/menus";
        public static final String MONETARY_LIMITS = "/monetary-limits";
        public static final String HLR_PROFILES = "/hlr-profiles";
        public static final String TRANSACTIONS = "/transactions";
        public static final String TYPE = "/type";
        public static final String CODE = "/code";
        public static final String LINK = "/link";
        public static final String LINK_DESCRIPTION = "/link-description";
        public static final String TRANSACTION_ADMIN = "/transaction-admin";
        public static final String DSS_COLUMNS = "/dss-columns";
        public static final String BT_STATUS = "/bt-status";

        public static final String PAM_CLASS = "/pam-class";
        public static final String PAM_SERVICE = "/pam-service";
        public static final String PAM_PERIOD = "/pam-period";
        public static final String PAM_PRIORITY = "/pam-priority";
        public static final String PAM_SCHEDULE = "/pam-schedule";

        public static final String DISCONNECTION_CODE = "/disconnection-code";
        public static final String ACCOUNT_GROUPS = "/account-groups";
        public static final String ACCOUNT_GROUPS_WITH_BITS = "/account-groups-with-bits";
        public static final String ACCOUNT_GROUP_BITS_LK = "/account-group-bits-lk";
        public static final String PROFILES_SERVICE_CLASSES = "/profiles-service-classes";
        public static final String SERVICE_OFFERING_PLANS = "/service-offering-plans";
        public static final String SERVICE_OFFERING_PLANS_BIT_DETAILS = "/service-offering-plans-bit-details";
        public static final String SERVICE_OFFERING_BITS_DETAILS = "/service-offering-bits-details";

        public static final String SO_SC_DESC = "/so-sc-desc";
        public static final String SERVICE_OFFERING_PLANS_WITH_BITS = "/service-offering-plans-with-bits";
        public static final String CALL_ACTIVITY_ADMIN = "/call-activity-admin";
        public static final String LK_MAIN_PRODUCT_TYPES = "/lk-main-product-types";
        public static final String LK_ACCOUNT_FLAGS = "/lk-account-flags";
        public static final String ODS = "/ods";
        public static final String ODS_NODES = "/ods-nodes";
        public static final String DSS_NODES = "/dss-nodes";
        public static final String FLEX_SHARE_HISTORY_NODES = "/flex-history-nodes";
        public static final String ACTIVITIES = "/activities";
        public static final String ACTIVITIES_HEADERS = "/activities-headers";
        public static final String ACTIVITIES_HEADERS_MAPPING = "/activities-header-mapping";
        public static final String ACTIVITIES_DETAILS_MAPPING = "/activities-details-mapping";
        public static final String DEDICATED_ACCOUNTS = "/dedicated-accounts-map";

        public static final String COMMUNITIES = "/communities";

        public static final String FAF_PLANS = "/faf-plans";
        public static final String FAF_WHITE_LIST = "/faf-white-list";
        public static final String FAF_BLACK_LIST = "/faf-black-list";

        public static final String FOOTPRINT_PAGES = "/footprint-pages";
        public static final String MARED_CARDS = "/mared-cards";

        public static final String BARRING_REASON = "/barring-reason";
        public static final String CODES_RENEWAL_VALUE = "/codes-renewal-value";
        public static final String CALL_ACTIVITIES = "/call-activities";
        public static final String SMS_ACTIONS = "/sms-actions";

        public static final String SMS_TEMPLATES = "/sms-templates";
        public static final String SMS_ACTION_PARAM_MAP = "/sms-action-param-map";

        public static final String SUPER_FLEX_ADDON_OFFERS = "/super-flex-addon-offers";
        public static final String SUPER_FLEX_THRESHOLD_OFFERS = "/super-flex-threshold-offers";

        public static final String VIP_MSISDN = "/msisdn";
        public static final String VIP_PAGE = "/page";

    }

    public static class SEQUENCE {

        public static final String CONTEXT_PATH = "/sequence";
        public static final String DED_ACCOUNT = "/ded-account";
        public static final String ACCUMULATOR = "/accumulator";
    }

    public static class WEB_ACTIONS {

        public static final String GET = "/get";
        public static final String GET_ALL = "/get-all";
        public static final String GET_DELETED = "/get-deleted";
        public static final String ADD = "/add";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        public static final String UPLOAD = "/upload";
        public static final String DOWNLOAD = "/download";
        public static final String EXPORT = "/export";
        public static final String IMPORT = "/import";
    }

    public static class SecurityKeywords {

        public static final String USERNAME = "username";
        public static final String PREFIX = "prefix";
    }

    public static class SEVERITY {

        public static final Integer CLEAR = 0;
        public static final Integer VALIDATION = 1;
        public static final Integer ERROR = 2;
        public static final Integer FATAL = 3;
    }

    public static class LOOKUPS {

        public static final String UNIT_TYPES = "LK_UNIT_TYPE_DESC";
        public static final String LK_LANGUAGES = "LK_LANGUAGES";
        public final static String LK_USAGE_COUNTER_DESC = "LK_USAGE_COUNTER_DESC";
        public final static String LK_PAM_CLASSES = "LK_PAM_CLASSES";
        public final static String LK_AIR_SERVERS = "LK_AIR_SERVERS";
        public final static String LK_REGIONS= "LK_REGIONS";
        public final static String LK_VOUCHER_SERVERS = "LK_VOUCHER_SERVERS";
        public final static String LK_MAREDCARD_LIST = "LK_MAREDCARD_LIST";
        public final static String ADM_COMMUNITIES = "ADM_COMMUNITIES";
        public final static String AGENT_NAME = "AGENT_NAME";
    }

}
