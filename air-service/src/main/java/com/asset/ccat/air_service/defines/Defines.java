/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.defines;

/**
 * @author Mahmoud Shehab
 */
public class Defines {

    public static class ContextPaths {

        public static final String BASE_CONTEXT_PATH = "/ccat";
        public static final String LOOKUPS = "/lookup-service";
        public static final String CACHED_LOOKUPS = "/cached-lookups";
        public static final String AIR_SERVERS = "/air-servers";
        public static final String VOUCHER_SERVERS = "/voucher-servers";
        public static final String CONFIGURATIONS = "/configurations";
        public static final String ACCOUNT_DETAILS = "/account-details";
        public static final String PREPAID_PROFILE = "/prepaid-profile";
        public static final String SERVICE_CLASSES = "/service-classes";
        public static final String OFFERS = "/offers";
        public static final String OFFERS_STATES = "/offers-states";
        public static final String OFFERS_TYPES = "/offers-types";
        public static final String PROMOTION_PLANS = "/promotion-plans";
        public static final String BALANCE_AND_DATE = "/balance-and-date";
        public static final String ACCUMULATORS = "/accumulators";
        public static final String DEDICATED_ACCOUNTS = "/dedicated-accounts";
        public static final String UPDATE_LANGUAGE = "/language";
        public static final String VOUCHERLESS_REFILL = "/voucherless-refill";
        public static final String PAM_CLASS = "/pam-class";
        public static final String PAM_SCHEDULE = "/pam-schedule";
        public static final String PAM_INFORMATION = "/pam-information";
        public static final String ADVANCED = "/advanced";
        public static final String PROFILES_SERVICE_CLASSES = "/profiles-service-classes";
        public static final String SERVICE_OFFERING_PLANS_WITH_BITS = "/service-offering-plans-with-bits";
        public static final String LK_COMMUNITIES = "/communities";
        public static final String LK_FAF_PLANS = "/faf-plans";
        public static final String LK_FAF_WHITE_LIST = "/faf-white-list";
        public static final String LK_FAF_BLACK_LIST = "/faf-black-list";
        public static final String USAGE_COUNTERS = "/usage-counters";
        public static final String USAGE_THRESHOLDS = "/usage-thresholds";
        public static final String DEDICATED_ACCOUNTS_CACHE = "/dedicated-accounts-map";
        public static final String BARRING = "/barring";
        public static final String BAR = "/bar";
        public static final String UNBAR = "/unbar";
        public static final String UNBAR_REFILL_BARRING = "/unbar-refill-barring";
        public static final String COMMUNITIES = "communities";
        public static final String VOUCHER = "/voucher";
        public static final String VOUCHER_BASED_REFILL = "/voucher-based-refill";
        public static final String FAMILY_AND_FRIENDS = "/family-and-friends";
        public static final String BARRING_REASON = "/barring-reason";
        public static final String USER_MANAGEMENT_CONTEXT_PATH = "/user-management-service";
        public static final String LIMITS = "/limits";

        public static final String SUPER_FLEX = "/super-flex";
        public static final String SUPER_FLEX_ADDONS = "/addons";

        public static final String SERVICE_OFFERING = "/service-offering";
        public static final String ACCOUNT_GROUP = "/account-group";

    }

    public static class WEB_ACTIONS {

        public static final String GET = "/get";
        public static final String GET_ALL = "/get-all";
        public static final String ADD = "/add";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        public static final String UPLOAD = "/upload";
        public static final String DOWNLOAD = "/download";
        public static final String SUBMIT = "/submit";
        public static final String CHECK = "/check";
    }

    public static class SecurityKeywords {

        public static final String USERNAME = "username";
        public static final String PREFIX = "prefix";
        public static final String SESSION_ID = "sessionId";
        public static final String PROFILE_ROLE = "profile";
        public static final String USER_ID = "userId";
        public static final String PROFILE_ID = "profileId";
    }

    public static class SEVERITY {

        public static final int CLEAR = 0;
        public static final int VALIDATION = 1;
        public static final int ERROR = 2;
        public static final int FATAL = 3;
    }

    public static class AIR_DEFINES {

        public static final String INFINITY_DATE_AIR = "99991231T12:00:00+0000";
        public static final String INFINITY_DATE_CC = "99991231";
        public static final String INFINITY_DATE_CC_DDMM = "31/12/9999";
    }

    public static class LOOKUPS {

        public static final String UNIT_TYPES = "LK_UNIT_TYPE_DESC";
        public static final String LK_LANGUAGES = "LK_LANGUAGES";
        public final static String LK_USAGE_COUNTER_DESC = "LK_USAGE_COUNTER_DESC";
        public final static String LK_PAM_CLASSES = "LK_PAM_CLASSES";

        public static final String SERVICE_OFFERING_PLANS_BIT_DETAILS = "/service-offering-plans-bit-details";
        public static final String SERVICE_OFFERING_BITS_DETAILS = "/service-offering-bits-details";

        public static final String SUPER_FLEX_ADDON_OFFERS = "/super-flex-addon-offers";
        public static final String ACCOUNT_GROUPS_WITH_BITS = "/account-groups-with-bits";
        public static final String ACCOUNT_GROUP_BITS_LK = "/account-group-bits-lk";

    }

}
