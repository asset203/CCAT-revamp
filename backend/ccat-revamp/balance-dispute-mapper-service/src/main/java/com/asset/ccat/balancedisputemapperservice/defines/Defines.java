package com.asset.ccat.balancedisputemapperservice.defines;

/**
 * @author Assem.Hassan
 */
public class Defines {

  public static final class ContextPaths {

    public static final String BALANCE_DISPUTE_REPORT = "/bd-report";
    public static final String LOOKUPS = "/lookup-service";
    public static final String USER_MANAGEMENT = "/user-management-service";
    public static final String USER = "/user";
    public static final String USER_PRIVILEGE = "/user-privilege";
    public static final String CACHED_LOOKUPS = "/cached-lookups";
    public static final String SERVICE_CLASSES = "/service-classes";
    public static final String REGIONS = "/regions";
    public static final String BD_DETAILS_CONFIG = "/bd-details-config";
    public static final String TODAY_DATA_USAGE = "/today-data-usage";
  }

  public static class WEB_ACTIONS {

    public static final String MAP = "/map";
    public static final String GET = "/get";
    public static final String GET_ALL = "/get-all";
    public static final String ADD = "/add";
    public static final String UPDATE = "/update";
    public static final String UPDATE_ALL = "/update-all";
    public static final String DELETE = "/delete";
    public static final String DELETE_ALL = "/delete-all";
    public static final String CHECK = "/check";
  }

  public static class SecurityKeywords {

    public static final String USERNAME = "username";
    public static final String PREFIX = "prefix";
    public static final String SESSION_ID = "sessionId";
    public static final String PROFILE_ROLE = "profile";
    public static final String USER_ID = "userId";
    public static final String PROFILE_ID = "profileId";
    public static final String PROFILE_FEATURES = "features";
  }

  public static class SEVERITY {

    public static final Integer CLEAR = 0;
    public static final Integer VALIDATION = 1;
    public static final Integer ERROR = 2;
    public static final Integer FATAL = 3;
  }

  public static class BALANCE_DISPUTE {
    public static final String DATE_FORMAT_PATTERN = "dd-MM-yyyy";
    public static final Integer BD_NUM_ACCUMULATORS = 5;
    public static String BD_BIT_IDS = "BD_BIT_IDS";
    public static String BD_BIT_IDS_SIMPLE = "BD_BIT_IDS_SIMPLE";
    public static String BD_INTERNET_PACKAGES_DELIMITER = ",";
    public static String BD_ACC_AFTER = "BD_ACC_AFTER";
    public static String BD_ACC_BEFORE_SIMPLE = "BD_ACC_BEFORE_SIMPLE";
    public static String BD_ACC_AFTER_SIMPLE = "BD_ACC_AFTER_SIMPLE";
    public static String BD_INTERNET_PACKAGES = "BD_INTERNET_PACKAGES";
    public static String BD_DETAILED_AMOUNT_DA = "BD_DETAILED_AMOUNT_DA";
    public static String BD_PAYMENT = "Payment";
    public static String BD_ADJUSTMENT = "Adjustment";
    public static String BD_RECHARGE = "Recharge";
    public static String BD_REGION_UNDEFINED = "Other";
    public static String BD_ACC_ABB = "ACC";
    public static String BD_DA_ABB = "DA";
    public static String BD_MB_ABB = "MB";
    public static int BD_ADJUSTMENT_REPORT = 2;
    public static int BD_BONUS_REPORT = 1;
    public static String BD_ACC_BEFORE = "BD_ACC_BEFORE";
    public static String BD_BONUS_REPORT_NAME = "Bonus Adjustment";
    public static String BD_ADJUSTMENT_REPORT_NAME = "Adjustment";
    public static String BD_AMOUNT_DA = "BD_AMOUNT_DA";
    public static String BD_AMOUNT_MB = "BD_AMOUNT_MB";
    public static String BD_AMOUNT_TTL = "BD_AMOUNT_TTL";
    public static String BD_USAGE = "Usage";
    public static String BD_CHARGING_SOURCE = "BD_CHARGING_SOURCE";
    public static String BD_CHARGING_AMOUNT = "BD_CHARGING_AMOUNT";
    public static String BD_CHARGING_SOURCE_SIMPLE = "BD_CHARGING_SOURCE_SIMPLE";

    public static String BD_GPRS_BASIC_SERVICE = "GPRS Basic Service";
    public static String BD_VODAFONE = "Vodafone";
    public static String BD_MOBINIL = "Mobinil";
    public static String BD_ETISALAT = "Etisalat";
    public static String BD_MOBILE = "Mobile";
    public static String BD_MOBILE_TO_ETISALAT = "Mobile to Etisalat";
    public static String BD_MOBILE_TELEPHONY = "Mobile Telephony";
    public static String BD_MOBILE_TELEPHONY_TOTAL = "Total";
    public static String BD_SMS_COUNT = "SMS Count";
    public static String BD_SHORT_MESSAGE_MO_PP = "Short message MO/PP";
    public static String PARTIAL_CDRS = "PARTIAL_CDRS";
  }

  public static class BD_FN_MAPS {

    public static String SL_GET_ADJ_FN_ADJUSTMENT = "SL_GET_ADJ_FN_ADJUSTMENT";
    public static String SL_GET_ADJ_FN_RECHARGES = "SL_GET_ADJ_FN_RECHARGES";
    public static String SL_GET_ADJ_FN_PAYMENT = "SL_GET_ADJ_FN_PAYMENT";
    public static String SL_GET_ADJ_FN_DEDICATION = "SL_GET_ADJ_FN_DEDICATION";
    public static String SL_GET_USAGE_AND_ACCUMULATORS = "SL_GET_USAGE_AND_ACCUMULATORS";
  }
}
