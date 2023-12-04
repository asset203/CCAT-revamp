package com.asset.ccat.ci_service.defines;

/**
 *
 * @author Mahmoud Shehab
 */
public class Defines {

    public static class ContextPaths {

        public static final String BASE_CONTEXT_PATH = "/ccat";
        public static final String LOOKUPS = "/lookups";
        public static final String CACHED_LOOKUPS = "/cached-lookups";
        public static final String AIR_SERVERS = "/air-servers";
        public static final String CONFIGURATIONS = "/configurations";
        public static final String ACCOUNT_DETAILS = "/account-details";
        public static final String PREPAID_PROFILE = "/prepaid-profile";
        public static final String MAIN_PRODUCTS = "/main-products";
        public static final String SERVICE_CLASSES = "/service-classes";
        public static final String PROMOTION_PLANS = "/promotion-plans";
        public static final String BALANCE_AND_DATE = "/balance-and-date";
        public static final String ACCUMULATORS = "/accumulators";
        public static final String DEDICATED_ACCOUNTS = "/dedicated-accounts";
        public static final String PREPAID_VBP = "/prepaid-vbp";
        public static final String USER_MANAGEMENT_CONTEXT_PATH = "/user-management-service";
        public static final String LIMITS = "/limits";
        public static final String FLEX_SHARE = "/flex-share";
        public static final String SUPER_FLEX = "/super-flex";
        public static final String SUPER_FLEX_ADDONS= "/addons";
        public static final String SUPER_FLEX_THRESHOLDS= "/thresholds";
        public static final String SUPER_FLEX_STOP_MI= "/stop-mi";

    }

    public static class WEB_ACTIONS {

        public static final String GET = "/get";
        public static final String GET_ALL = "/get-all";
        public static final String ADD = "/add";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        public static final String UPLOAD = "/upload";
        public static final String DOWNLOAD = "/download";
        public static final String CHECK_MIGRATION_TO_FRIEND = "/check-migration-to-friend";
        public static final String CHECK_MIGRATION_FROM_HAKAWY = "/check-migration-from-hakawy";
        public static final String SUBSCRIBE = "/subscribe";
        public static final String UNSUBSCRIBE = "/unsubscribe";
        public static final String CHECK = "/check";
        public static final String ACTIVATE = "/activate";
        public static final String DEACTIVATE = "/deactivate";
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

    public static class AIR_DEFINES {

        public static final String INFINITY_DATE_AIR = "99991231T12:00:00+0000";
        public static final String INFINITY_DATE_CC = "99991231";
        public static final String INFINITY_DATE_CC_DDMM = "31/12/9999";
    }

    public static class LOOKUPS {

        public static final String CONTEXT_PATH = "/lookup-service";
        public static final String UNIT_TYPES = "LK_UNIT_TYPE_DESC";
        public static final String LK_LANGUAGES = "LK_LANGUAGES";
        public final static String LK_USAGE_COUNTER_DESC = "LK_USAGE_COUNTER_DESC";
        public final static String LK_PAM_CLASSES = "LK_PAM_CLASSES";
        public static final String LK_MAIN_PRODUCT_TYPES = "/lk-main-product-types";
        public static final String SUPER_FLEX_THRESHOLD_OFFERS = "/super-flex-threshold-offers";
    }
    public static class CI_PREPAID_VBP  {
        public static final String CONTEXT_PATH = "/ci-prepaid-vbp";
        public static final String CODES_RENEWAL_VALUE = "/codes-renewal-value";

    }

    public static class FLEX_SHARE_ACTIONS  {
        public static final String ELIGIBILITY = "/eligibility";

    }

}
