package com.asset.ccat.balance_dispute_service.defines;


public class Defines {

  public static class ContextPaths {

    public static final String BASE_CONTEXT_PATH = "/ccat";
    public static final String LOOKUPS = "/lookup-service";
    public static final String CONFIGURATIONS = "/configurations";
    public static final String CACHED_LOOKUPS = "/cached-lookups";
    public static final String BALANCE_DISPUTE = "/balance-dispute";
    public static final String BALANCE_DISPUTE_MAPPER_SERVICE = "/balance-dispute-mapper-service";
    public static final String BALANCE_DISPUTE_REPORT = "/bd-report";
    public static final String TODAY_DATA_USAGE = "/today-data-usage";

  }

  public static class WEB_ACTIONS {

    public static final String GET = "/get";
    public static final String GET_ALL = "/get-all";
    public static final String ADD = "/add";
    public static final String UPDATE = "/update";
    public static final String DELETE = "/delete";
    public static final String UPLOAD = "/upload";
    public static final String DOWNLOAD = "/download";
    public static final String Export = "/export";

    public static final String INQUIRY = "/inquiry";
    public static final String MAP = "/map";
  }

  public static class SecurityKeywords {

    public static final String USERNAME = "username";
    public static final String PREFIX = "prefix";

    public static final String SESSION_ID = "sessionId";
    public static final String PROFILE_ROLE = "profile";
    public static final String PROFILE_ID = "profileId";
    public static final String USER_ID = "userId";

  }

  public static class SEVERITY {

    public static final Integer CLEAR = 0;
    public static final Integer VALIDATION = 1;
    public static final Integer ERROR = 2;
    public static final Integer FATAL = 3;
  }

  public static class STORED_FUNCTION_PARAMETERS {

    public static final String MSISDN = "$MSISDN$";
    public static final String CO_ID = "$CO_ID$";
    public static final String CUSTOMER_ID = "$CUSTOMER_ID$";
    public static final String DATE1 = "$DATE1$";
    public static final String DATE2 = "$DATE2$";
    public static final String TYPE = "$ACTION_TYPE$";
  }

  public static class STORED_FUNCTION_NAMES {

    public static final Integer GET_MOC_PRE_FN_RA_NEW4 = 1;
    public static final Integer GET_ADJ_FN_DWH_TST_ADJUSTMENT = 2;
    public static final Integer GET_ADJ_FN_DWH_TST_RECHARGES = 3;
    public static final Integer GET_ADJ_FN_DWH_TST_PAYMENT = 4;
    public static final Integer GET_ADJ_FN_DWH_TST_DEDICATION = 5;
  }

  public static class SERIALIZER_KEYS {

    public static final String SUMMARY_SHEET_NODE_KEY = "balanceSummarySheet";
    public static final String USAGE_SHEET_NODE_KEY = "usageSummarySheet";
    public static final String DETAILS_NODE_KEY = "details";
  }

  public static class SUMMARY_TOTALS_KEYS {

    public static final String DA_ADJ_CREDIT = "daAdjustmentsTtlCredit";
    public static final String DA_BONUS_ADJ_CREDIT = "daBonusAdjTtlCredit";
    public static final String MB_RECHARGES_CREDIT = "mbRechargesTtlCredit";
    public static final String MB_PAYMENTS_CREDIT = "mbPaymentsTtlCredit";
    public static final String MB_ADJ_CREDIT = "mbAdjustmentsTtlCredit";
    public static final String DA_ADJ_DEBIT = "daAdjustmentsTtlDebit";
    public static final String DA_USAGE_DEBIT = "daUsgTtlDebit";
    public static final String MB_ADJ_DEBIT = "mbAdjustmentsTtlDebit";
    public static final String MB_USAGE_DEBIT = "mbUsgTtlDebit";
    public static final String MOBILE_TELEPHONY = "mobileTelephonyTotal";
  }

  public static class DETAILS_TOTALS_KEYS {

    public static final String MB_CREDIT = "totalMBCredit";
    public static final String MB_DEBIT = "totalMBDebit";
    public static final String DA_CREDIT = "totalDACredit";
    public static final String DA_DEBIT = "totalDADebit";
    public static final String AMOUNT_CREDIT = "totalAmountCredit";
    public static final String AMOUNT_DEBIT = "totalAmountDebit";
    public static final String TOTAL_DURATION = "totalDuration";
    public static final String TOTAL_COST = "totalCost";
    public static final String TOTAL_ACTUAL_SECONDS = "totalActualSeconds";
    public static final String FREE_SMS = "totalFreeSms";
    public static final String INTERNET_USAGE = "totalInternetUsage";
  }

  public static class BALANCE_DISPUTE {

    public static final String DATE_FORMAT_PATTERN = "dd-MM-yyyy";
    public static final String DB_CALCULATION_FILE_XLSM = "BD_calculator.xlsm";
    public static final String BALANCE_DISPUTE_CSV_FILE_NAME = "Balance-Dispute-New.csv";
    public static final String PARTIAL_CDRS = "PARTIAL_CDRS";
    public static final String ERROR_CODE = "ERROR_CODE";
    public static final String CSV_FILE_HEADLINE = "Transaction Details New";
  }

  public static class CSV_HEADERS {

    public static final String DATE_AND_TIME = "Date& Time";
    public static final String TOTAL_ACTUAL_SEC = "Total Actual Seconds";
    public static final String BALANCE_BEFORE = "Balance Before";
    public static final String BALANCE_AFTER = "Balance After";
    public static final String INTERNET_USAGE = "Internet Usage (in MB)";
    public static final String OTHER_PARTY_NO = "Other Party Number";
    public static final String DESTINATION = "Destination";
    public static final String DA_BEFORE = "DA Before";
    public static final String DA_AFTER = "DA After";
    public static final String CHARGING_SOURCE = "Charging Source";
    public static final String CHARGING_AMOUNT = "Charging Amount";
  }

  public static class CSV_COLUMNS {

    public static final String DATE = "Date";
    public static final String TIME = "Time";
    public static final String TOTAL_ACTUAL_SEC = "Total Actual Seconds";
    public static final String BALANCE_BEFORE = "Balance Before";
    public static final String BALANCE_AFTER = "Balance After";
    public static final String INTERNET_USAGE = "Internet Usage (in KB)";
    public static final String OTHER_PARTY_NO = "Other Party Number";
    public static final String RATING_GROUP = "Rating Group";
    public static final String DA_BEFORE = "DA Before";
    public static final String DA_AFTER = "DA After";
    public static final String CHARGING_SOURCE = "Charging Source";
    public static final String CHARGING_AMOUNT = "Charging Amount";
    public static final String COMMA = ",,,,,,,,,";

  }
}
