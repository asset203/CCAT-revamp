/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.defines;

/**
 * @author Mahmoud Shehab
 */
public class DatabaseStructs {

  public static final String SERVICE_CLASS_ID = "SERVICE_CLASS_ID";
  public static final String DESCRIPTION = "DESCRIPTION";
  public static final String NAME = "NAME";

  public static final class ADM_SO_BITS_SC_DESCRIPTION {

    public final static String TABLE_NAME = "ADM_SO_BITS_SC_DESCRIPTION";
    public final static String BIT_POSITION = "BIT_POSITION";
    public final static String SERVICE_CLASS_ID = "SERVICE_CLASS_ID";
    public final static String DESCRIPTION = "DESCRIPTION";
  }

  public static final class SEQUENCE {

    //        public final static String S_ADM_SERVICE_CLASS = "S_ADM_SERVICE_CLASSES";
    public final static String S_ADM_BUSINESS_PLANS = "S_ADM_BUSINESS_PLANS";
    public final static String S_LK_DISCONNECTION_CODES = "S_LK_DISCONNECTION_CODES";
    public final static String S_ADM_TX_TYPES = "S_ADM_TX_TYPES";
    public final static String S_ADM_TX_CODES = "S_ADM_TX_CODES";

    public final static String S_SMS_TEMPLATE = "S_SMS_TEMPLATE";

    public final static String S_LK_AIR_SERVERS = "S_LK_AIR_SERVERS";
    public final static String S_FLEX_SHARE_HISTORY_NODES = "S_FLEX_SHARE_HISTORY_NODES";

  }

  public static final class LK_DISCONNECTION_CODES {

    public final static String TABLE_NAME = "LK_DISCONNECTION_CODES";
    public final static String ID = "ID";
    public final static String NAME = "NAME";
    public final static String CODE_ID = "CODE_ID";
    public final static String IS_DELETED = "IS_DELETED";
    public final static String CREATION_DATE = "CREATION_DATE";
  }

  public static final class ADM_SERVICE_CLASSES {

    public final static String TABLE_NAME = "ADM_SERVICE_CLASSES";
    public final static String NAME = "NAME";
    public final static String CODE = "CODE";
    public final static String PREACTIVATION_ALLOWED = "PREACTIVATION_ALLOWED";
    public final static String IS_DELETED = "IS_DELETED";
    public final static String IS_GRANDFATHER = "IS_GRANDFATHER";
    public final static String IS_CI_CONVERSION = "IS_CI_CONVERSION";
    public final static String CI_SERVICE_NAME = "CI_SERVICE_NAME";
    public final static String CI_PACKAGE_NAME = "CI_PACKAGE_NAME";
    public final static String IS_ALLOW_MIGRATION = "IS_ALLOW_MIGRATION";
    public final static String TYPE = "TYPE";
    public final static String HAS_EMPTY_LIMIT = "HAS_EMPTY_LIMIT";
    public final static String DEFAULT_LIMIT = "DEFAULT_LIMIT";
    public final static String CUSTOM_LIMIT = "CUSTOM_LIMIT";
  }

  public static final class ADM_BUSINESS_PLANS {

    public static final String TABLE_NAME = "ADM_BUSINESS_PLANS";
    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String CODE = "CODE";
    public static final String SERVICE_CLASS_ID = "SERVICE_CLASS_ID";
    public static final String HLR_ID = "HLR_ID";
    public static final String IS_DELETED = "IS_DELETED";
    public static final String ACTIVATION_PLAN = "ACTIVATION_PLAN";
  }

  public static final class ADM_SERVICE_CLASS_MIGRATION {

    public static final String TABLE_NAME = "ADM_SERVICE_CLASS_MIGRATION";
    public static final String SERVICE_CLASS_ID = "SERVICE_CLASS_ID";
    public static final String DESTINATION_SERVICE_CLASS_ID = "DESTINATION_SERVICE_CLASS_ID";
  }

  public static final class LK_REFILL_PAYMENT_PROFILE {

    public static final String TABLE_NAME = "LK_REFILL_PAYMENT_PROFILE";
    public static final String REFILL_PROFILE_NAME = "REFILL_PROFILE_NAME";
    public final static String AMOUNT_FROM = "AMOUNT_FROM";
    public final static String AMOUNT_TO = "AMOUNT_TO";
  }

  public static final class ADM_OFFERS {

    public static final String TABLE_NAME = "ADM_OFFERS";
    public static final String OFFER_ID = "OFFER_ID";
    public final static String OFFER_DESC = "OFFER_DESC";
    public final static String OFFER_TYPE_ID = "OFFER_TYPE_ID";
    public final static String IS_DROP_DOWN_ENABLED = "IS_DROP_DOWN_ENABLED";
  }

  public static final class LK_OFFERS_TYPES {

    public static final String TABLE_NAME = "LK_OFFERS_TYPES";
    public static final String TYPE_ID = "TYPE_ID";
    public final static String TYPE_DESC = "TYPE_DESC";
  }

  public static final class LK_OFFERS_STATES {

    public static final String TABLE_NAME = "LK_OFFERS_STATES";
    public static final String STATE_ID = "STATE_ID";
    public final static String STATE_DESC = "STATE_DESC";
  }

  public static final class ADM_SERVICE_CLASS_ACC {

    public final static String TABLE_NAME = "ADM_SERVICE_CLASS_ACC";
    public final static String SERVICE_CLASS_ID = "SERVICE_CLASS_ID";
    public final static String DESCRIPTION = "DESCRIPTION";
    public final static String ACC_ID = "ACC_ID";
  }

  public static final class ADM_SERVICE_CLASS_DA {

    public final static String TABLE_NAME = "ADM_SERVICE_CLASS_DA";
    public final static String SERVICE_CLASS_ID = "SERVICE_CLASS_ID";
    public final static String DESCRIPTION = "DESCRIPTION";
    public final static String DA_DESC = "DA_DESC";
    public final static String DA_ID = "DA_ID";
    public final static String RATING_FACTOR = "RATING_FACTOR";
  }

  public static final class ADM_PROMOTION_PLANS {

    public final static String TABLE_NAME = "ADM_PROMOTION_PLANS";
    public final static String ID = "ID";
    public final static String PLAN_ID = "PLAN_ID";
    public final static String NAME = "NAME";
    public final static String IS_DEFAULT = "IS_DEFAULT";
    public final static String IS_DELETED = "IS_DELETED";

  }

  public static final class ADM_TX_TYPES {

    public final static String TABLE_NAME = "ADM_TX_TYPES";
    public final static String ID = "ID";
    public final static String VALUE = "VALUE";
    public final static String NAME = "NAME";
    public final static String DESCRIPTION = "NAME";
    public final static String IS_DEFAULT = "IS_DEFAULT";
    public final static String IS_DELETED = "IS_DELETED";

  }

  public static final class ADM_TX_FEATURE_TYPES {

    public final static String TABLE_NAME = "ADM_TX_FEATURE_TYPES";
    public final static String TYPE_ID = "TYPE_ID";
    public final static String FEATURE_ID = "FEATURE_ID";

  }

  public static final class LK_MENUS {

    public static final String TABLE_NAME = "LK_MENUS";
    public static final String TABLE_ALIAS = "MENUS";
    public static final String MENU_ID = "MENU_ID";
    public static final String LABEL = "LABEL";
    public static final String PARENT_ID = "PARENT_ID";
    public static final String ROUTER = "ROUTER";
    public static final String ORDERING = "ORDERING";
    public static final String ICON = "ICON";
  }

  public static final class LK_MONETARY_LIMITS {

    public static final String TABLE_NAME = "LK_MONETARY_LIMITS";
    public static final String LIMIT_ID = "LIMIT_ID";
    public static final String LIMIT_NAME = "LIMIT_NAME";
    public static final String DEFAULT_VALUE = "DEFAULT_VALUE";
  }

  public static final class LK_FEATURES {

    public static final String TABLE_NAME = "LK_FEATURES";
    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String PAGE_NAME = "PAGE_NAME";
    public static final String TYPE = "\"TYPE\"";
    public static final String URL = "URL";
    public static final String MENU_ID = "MENU_ID";
    public static final String LABEL = "MENU_ID";
  }

  public static final class ADM_PROFILE_FEATURES {

    public static final String TABLE_NAME = "ADM_PROFILE_FEATURES";
    public static final String PROFILE_ID = "PROFILE_ID";
    public static final String FEATURE_ID = "FEATURE_ID";
  }

  public static final class LK_SMS_ACTION {

    public static final String TABLE_NAME = "LK_SMS_ACTION";
    public static final String SMS_ACTION_ID = "SMS_ACTION_ID";
    public static final String ACTION_NAME = "ACTION_NAME";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String CODE = "CODE";
  }

  public static final class ADM_SMS_TEMPLATE {

    public static final String TABLE_NAME = "ADM_SMS_TEMPLATE";
    public static final String ID = "ID";
    public static final String TEMPLATE_ID = "TEMPLATE_ID";
    public static final String TEMPLATE_TEXT = "TEMPLATE_TEXT";
    public static final String LANGUAGE_ID = "LANGUAGE_ID";
    public static final String TEMPLATE_STATUS = "TEMPLATE_STATUS";
    public static final String CS_TEMPLATE_ID = "CS_TEMPLATE_ID";
    public static final String TEMPLATE_PARAMETERS = "TEMPLATE_PARAMETERS";


  }

  public static final class LK_SMS_TEMPLATE_PARAM {

    public static final String TABLE_NAME = "LK_SMS_TEMPLATE_PARAM";
    public static final String PARAMETER_ID = "PARAMETER_ID";
    public static final String PARAMETER_NAME = "PARAMETER_NAME";
    public static final String SMS_ACTION_ID = "SMS_ACTION_ID";
  }

  public static final class ADM_HLR_PROFILES {

    public static final String TABLE_NAME = "ADM_HLR_PROFILES";
    public static final String HLR_ID = "HLR_ID";
    public static final String CODE = "CODE";
    public static final String NAME = "NAME";
  }

  public static final class ADM_TX_CODES {

    public final static String TABLE_NAME = "ADM_TX_CODES";
    public final static String ID = "ID";
    public final static String VALUE = "VALUE";
    public final static String NAME = "NAME";
    public final static String DESCRIPTION = "DESCRIPTION";
    public final static String IS_DEFAULT = "IS_DEFAULT";
    public final static String IS_DELETED = "IS_DELETED";
  }

  public static final class ADM_TX_LINKS {

    public final static String TABLE_NAME = "ADM_TX_LINKS";
    public final static String TX_TYPE_ID = "TX_TYPE_ID";
    public final static String TX_CODE_ID = "TX_CODE_ID";
    public final static String DESCRIPTION = "DESCRIPTION";
  }

  public static final class LK_PAM_CLASSES {

    public final static String TABLE_NAME = "LK_PAM_CLASSES";
    public final static String PAM_CLASS_ID = "ID";
    public final static String PAM_CLASS_DESC = "NAME";
  }

  public static final class LK_PAM_SERVICES {

    public final static String TABLE_NAME = "LK_PAM_SERVICES";
    public final static String PAM_SERVICE_ID = "ID";
    public final static String PAM_SERVICE_DESC = "NAME";
  }

  public static final class LK_PAM_PERIOD {

    public final static String TABLE_NAME = "LK_PAM_PERIOD";
    public final static String PAM_PERIOD_ID = "ID";
    public final static String PAM_PERIOD_DESC = "NAME";
  }

  public static final class LK_PAM_PRIORITY {

    public final static String TABLE_NAME = "LK_PAM_PRIORITY";
    public final static String PAM_PRIORITY_ID = "ID";
    public final static String PAM_PRIORITY_DESC = "NAME";
  }

  public static final class LK_PAM_SCHEDULES {

    public final static String TABLE_NAME = "LK_PAM_SCHEDULES";
    public final static String PAM_SCHEDULE_ID = "ID";
    public final static String PAM_SCHEDULE_DESC = "NAME";
  }

  public static final class ADM_ACCOUNT_GROUPS {

    public static final String TABLE_NAME = "ADM_ACCOUNT_GROUPS";
    public static final String ID = "ID";
    public final static String GROUP_ID = "GROUP_ID";
    public final static String NAME = "NAME";
    public final static String IS_DELETED = "IS_DELETED";
  }

  public static final class ADM_COMMUNITIES {

    public static final String TABLE_NAME = "ADM_COMMUNITIES";
    public final static String COMMUNITY_ID = "COMMUNITY_ID";
    public final static String COMMUNITY_DESCRIPTION = "COMMUNITY_DESCRIPTION";
    public final static String IS_DELETED = "IS_DELETED";
  }

  public static final class ADM_SERVICE_OFFERING_PLANS {

    public static final String TABLE_NAME = "ADM_SERVICE_OFFERING_PLANS";
    public static final String ID = "ID";
    public final static String PLAN_ID = "PLAN_ID";
    public final static String NAME = "NAME";
    public final static String IS_DELETED = "IS_DELETED";
  }

  public static final class ADM_SO_SC_DESCRIPTION {

    public static final String TABLE_NAME = "ADM_SO_SC_DESCRIPTION";
    public final static String PLAN_ID = "PLAN_ID";
    public final static String SERVICE_CLASS_ID = "SERVICE_CLASS_ID";
    public final static String DESCRIPTION = "DESCRIPTION";
    public final static String DESCRIPTION_ALIAS = "PLAN_DESCRIPTION";


  }

  public static final class ADM_SERVICE_OFFERING_BITS {

    public static final String TABLE_NAME = "ADM_SERVICE_OFFERING_BITS";
    public final static String BIT_POSITION = "BIT_POSITION";
    public final static String BIT_NAME = "BIT_NAME";
  }

  public static final class ADM_ACCOUNT_GROUP_BITS {

    public static final String TABLE_NAME = "ADM_ACCOUNT_GROUP_BITS";
    public final static String BIT_POSITION = "BIT_POSITION";
    public final static String BIT_NAME = "BIT_NAME";
  }

  public static final class ADM_AG_BITS_SC_DESCRIPTION {

    public static final String TABLE_NAME = "ADM_AG_BITS_SC_DESCRIPTION";
    public final static String BIT_POSITION = "BIT_POSITION";
    public final static String SERVICE_CLASS_ID = "SERVICE_CLASS_ID";
    public final static String DESCRIPTION = "DESCRIPTION";
  }

  public static final class ADM_SERVICE_OFFG_PLAN_BITS {

    public static final String TABLE_NAME = "ADM_SERVICE_OFFG_PLAN_BITS";
    public final static String BIT_POSITION = "BIT_POSITION";
    public final static String PLAN_ID = "PLAN_ID";
    public final static String IS_ENABLED = "IS_ENABLED";
  }

  public static final class ADM_REASON_ACTIVITY {

    public static final String TABLE_NAME = "ADM_REASON_ACTIVITY";

    public static final String S_ADM_REASON_ACTIVITY = "S_ADM_REASON_ACTIVITY";
    public final static String ACTIVITY_ID = "ACTIVITY_ID";
    public final static String ACTIVITY_NAME = "ACTIVITY_NAME";
    public final static String PARENT_ID = "PARENT_ID";
    public final static String ACTIVITY_TYPE = "ACTIVITY_TYPE";
    public final static String IS_ACTIVE = "IS_ACTIVE";
  }

  public static final class LK_MAIN_PRODUCT_TYPES {

    public static final String TABLE_NAME = "LK_MAIN_PRODUCT_TYPES";
    public static final String TYPE = "TYPE";
    public static final String DISPLAY_NAME = "DISPLAY_NAME";
  }

  public static final class LK_VOUCHER_SERVERS {

    public static final String TABLE_NAME = "LK_VOUCHER_SERVERS";
    public static final String VOUCHER_SERIAL_LENGTH = "VOUCHER_SERIAL_LENGTH";
    public static final String SERVER_INDEX = "SERVER_INDEX";
  }

  public static final class LK_ACCOUNT_FLAGS {

    public static final String TABLE_NAME = "LK_ACCOUNT_FLAGS";
    public static final String ACCOUNT_FLAG_CODE = "ACCOUNT_FLAG_CODE";
    public static final String ACCOUNT_FLAG_NAME = "ACCOUNT_FLAG_NAME";
  }

  public static final class LK_REGIONS {

    public static final String TABLE_NAME = "LK_REGIONS";
    public static final String REGION = "REGION";
    public static final String REGION_DESCRIPTION = "REGION_DESCRIPTION";
  }

  public static final class LK_BD_DETAILS_CONFIGURATION {

    public static final String TABLE_NAME = "LK_BALANCE_DISPUTE_DETAILS_CONFIGURATION";
    public static final String COLUMN_NAME = "COLUMN_NAME";
    public static final String DISPLAY_NAME = "DISPLAY_NAME";
    public static final String COLUMN_ORDER = "COLUMN_ORDER";
    public static final String PRIVILEGE_ID = "PRIVILEGE_ID";
  }

  public static final class LK_BT_TRANSACTION_STATUS {

    public static final String TABLE_NAME = "LK_BT_TRANSACTION_STATUS";
    public static final String CODE = "CODE";
    public static final String NAME = "NAME";
  }

  public static final class ODS_H_ACC_ACTIVITIES {

    public static final String TABLE_NAME = "ODS_H_ACC_ACTIVITIES";
    public static final String ACTIVITY_ID = "ACTIVITY_ID";
    public static final String ACTIVITY_NAME = "ACTIVITY_NAME";
    public static final String TABLE_TYPE = "TABLE_TYPE";
    public static final String DR_ID_IDX = "DR_ID_IDX";
    public static final String BALANCE_IDX = "BALANCE_IDX";
    public static final String EXP_DATE_IDX = "EXP_DATE_IDX";
    public static final String DA_AMOUNT_IDX = "DA_AMOUNT_IDX";
    public static final String ADJ_ACTION_IDX = "ADJ_ACTION_IDX";
    public static final String IS_NEW_FORMAT_IDX = "IS_NEW_FORMAT_IDX";
    public static final String IS_DELETED = "IS_DELETED";
  }

  public static final class ODS_H_ACC_HEADERS {

    public static final String TABLE_NAME = "ODS_H_ACC_HEADERS";
    public static final String HEADER_ID = "HEADER_ID";
    public static final String HEADER_NAME = "HEADER_NAME";
    public static final String HEADER_TYPE = "HEADER_TYPE";
    public static final String DISPLAY_NAME = "DISPLAY_NAME";
    public static final String IS_DELETED = "IS_DELETED";
    public static final String ORDER = "ORDER";
  }

  public static final class ODS_H_ACC_HEADERS_MAPPING {

    public static final String TABLE_NAME = "ODS_H_ACC_HEADERS_MAPPING";
    public static final String ACTIVITY_ID = "ACTIVITY_ID";
    public static final String HEADER_ID = "HEADER_ID";
    public static final String COLUMN_IDX = "COLUMN_IDX";
    public static final String IS_STATIC = "IS_STATIC";
    public static final String CUSTOM_FORMAT = "CUSTOM_FORMAT";
    public static final String PRE_CONDITIONS = "PRE_CONDITIONS";
    public static final String PRE_CONDITIONS_VALUE = "PRE_CONDITIONS_VALUE";
    public static final String DEFAULT_VALUE = "DEFAULT_VALUE";
    public static final String IS_DELETED = "IS_DELETED";
  }

  public static final class ODS_H_ACC_ACTIVITY_DETAILS {

    public static final String TABLE_NAME = "ODS_H_ACC_ACTIVITY_D";
    public static final String ACTIVITY_ID = "ACTIVITY_ID";
    public static final String COLUMN_KEY = "COLUMN_KEY";
    public static final String COLUMN_IDX = "COLUMN_IDX";
    public static final String DISPLAY_NAME = "DISPLAY_NAME";
    public static final String DISPLAY_ORDER = "DISPLAY_ORDER";
    public static final String DETAIL_TYPE = "DETAIL_TYPE";
    public static final String IS_DELETED = "IS_DELETED";
  }

  public static final class DSS_COLUMNS {

    public static final String TABLE_NAME = "DSS_COLUMNS";
    public static final String DSS_PAGE_NAME = "DSS_PAGENAME";
    public final static String COLUMN_NAME = "COLUMN_NAME";
    public final static String DISPLAY_NAME = "DISPLAYNAME";
  }

  public static final class ODS_NODES {

    public final static String TABLE_NAME = "ODS_NODES";
    public final static String ID = "ID";
    public final static String ADDRESS = "ADDRESS";
    public final static String PORT = "PORT";
    public final static String USER_NAME = "USER_NAME";
    public final static String PASSWORD = "PASSWORD";
    public final static String NUMBER_OF_SESSIONS = "NUMBER_OF_SESSIONS";
    public final static String CONCURRENT_CALLS = "CONCURRENT_CALLS";
    public final static String EXTRA_CONF = "EXTRA_CONF";
    public final static String CONNECTION_TIMEOUT = "CONNECTION_TIMEOUT";
    public final static String SCHEMA = "SCHEMA";

  }

  public static final class DSS_NODES {

    public final static String TABLE_NAME = "DSS_NODES";
    public final static String ID = "ID";
    public final static String ADDRESS = "ADDRESS";
    public final static String PORT = "PORT";
    public final static String USER_NAME = "USER_NAME";
    public final static String PASSWORD = "PASSWORD";
    public final static String NUMBER_OF_SESSIONS = "NUMBER_OF_SESSIONS";
    public final static String CONCURRENT_CALLS = "CONCURRENT_CALLS";
    public final static String EXTRA_CONF = "EXTRA_CONF";
    public final static String CONNECTION_TIMEOUT = "CONNECTION_TIMEOUT";
    public final static String SCHEMA = "SCHEMA";

  }

  public static final class FLEX_SHARE_HISTORY_NODES {

    public final static String TABLE_NAME = "FLEX_SHARE_HISTORY_NODES";
    public final static String ID = "ID";
    public final static String ADDRESS = "ADDRESS";
    public final static String PORT = "PORT";
    public final static String USERNAME = "USERNAME";
    public final static String PASSWORD = "PASSWORD";
    public final static String NUMBER_OF_SESSIONS = "NUMBER_OF_SESSIONS";
    public final static String CONCURRENT_CALLS = "CONCURRENT_CALLS";

    public final static String CONNECTION_TIMEOUT = "CONNECTION_TIMEOUT";
    public final static String EXTRA_CONF = "EXTRA_CONF";
    public final static String SCHEMA = "SCHEMA";

  }

  public static final class ADM_FAF_PLANS {

    public final static String TABLE_NAME = "ADM_FAF_PLANS";
    public final static String ID = "ID";
    public final static String PLAN_ID = "PLAN_ID";
    public final static String NAME = "NAME";
    public final static String INDICATOR_ID = "INDICATOR_ID";
    public final static String IS_DELETED = "IS_DELETED";

  }

  public static final class ADM_FAF_INDICATORS {

    public final static String TABLE_NAME = "ADM_FAF_INDICATORS";
    public final static String ID = "ID";
    public final static String INDICATOR_ID = "INDICATOR_ID";
    public final static String INDICATOR_NAME = "INDICATOR_NAME";
    public final static String MAPPED_INDICATOR_ID = "MAPPED_INDICATOR_ID";
    public final static String IS_DELETED = "IS_DELETED";

  }

  public static final class ADM_FAF_BLACK_RESTRICTIONS {

    public final static String TABLE_NAME = "ADM_FAF_BLACK_RESTRICTIONS";
    public final static String RESTRICTION_ID = "RESTRICTION_ID";
    public final static String NUMBER_PATTERN = "NUMBER_PATTERN";
    public final static String DESCRIPTION = "DESCRIPTION";
    public final static String IS_DELETED = "IS_DELETED";

  }

  public static final class ADM_FAF_WHITE_RESTRICTIONS {

    public final static String TABLE_NAME = "ADM_FAF_WHITE_RESTRICTIONS";
    public final static String RESTRICTION_ID = "RESTRICTION_ID";
    public final static String NUMBER_PATTERN = "NUMBER_PATTERN";
    public final static String DESCRIPTION = "DESCRIPTION";
    public final static String IS_DELETED = "IS_DELETED";

  }

  public static final class TX_CUSTOMERS_BARRINGS {

    public final static String TABLE_NAME = "TX_CUSTOMERS_BARRINGS";
    public final static String ENTRY_DATE = "ENTRY_DATE";
    public final static String MSISDN = "MSISDN";
    public final static String MSISDN_MOD_X = "MSISDN_MOD_X";
    public final static String BARRING_TYPE = "BARRING_TYPE";
    public final static String REASON = "REASON";
    public final static String REQUESTED_BY = "REQUESTED_BY";
    public final static String USER_ID = "USER_ID";
  }

  public static final class ADM_TX_CODES_RENEWAL_VALUE {

    public static final String TABLE_NAME = "ADM_TX_CODES_RENEWAL_VALUE";
    public static final String ID = "ID";
    public static final String CODE_ID = "CODE_ID";
    public static final String RENEWALS_VALUE = "RENEWALS_VALUE";
  }

  public static final class LK_FOOTPRINT_PAGES {

    public static final String TABLE_NAME = "LK_FOOTPRINT_PAGES";
    public static final String PAGE_ID = "PAGE_ID";
    public final static String PAGE_NAME = "PAGE_NAME";
    public final static String CONTROLLER_NAME = "CONTROLLER_NAME";
  }

  public static final class LK_FOOTPRINT_PAGE_INFO {

    public static final String TABLE_NAME = "LK_FOOTPRINT_PAGE_INFO";
    public static final String PAGE_ID = "PAGE_ID";
    public final static String METHOD_NAME = "METHOD_NAME";
    public final static String ACTION_TYPE = "ACTION_TYPE";
    public final static String ACTION_NAME = "ACTION_NAME";

  }

  public static final class LK_LANGUAGES {

    public final static String TABLE_NAME = "LK_LANGUAGES";
    public final static String ID = "ID";
    public final static String NAME = "NAME";
    ;
  }

  public static class LK_FLEX_OFFERS {

    public static final String TABLE_NAME = "LK_FLEX_OFFERS";
    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String TYPE_ID = "TYPE_ID";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String PACKAGE_ID = "PACKAGE_ID";
    public static final String OFFER_ID = "OFFER_ID";
  }

  public class ADM_PROFILE_SERVICE_CLASS {

    public static final String TABLE_NAME = "ADM_PROFILE_SERVICE_CLASS";
    public static final String SERVICE_CLASS_ID = "SERVICE_CLASS_ID";
    public static final String PROFILE_ID = "PROFILE_ID";
  }

  public class LK_AIR_SERVERS {

    public static final String TABLE_NAME = "LK_AIR_SERVERS";
    public static final String ID = "ID";
    public static final String URL = "URL";
    public static final String AGENT_NAME = "AGENT_NAME";
    public static final String HOST = "HOST";
    public static final String AUTHORIZATION = "AUTHORIZATION";
    public static final String IS_DOWN = "IS_DOWN";
    public static final String CAPABILITY_VALUE = "CAPABILITY_VALUE";
  }

  public class ADM_VIP_MSISDN{
    public static final String TABLE_NAME = "ADM_VIP_MSISDN";
    public static final String VIP_MSISDN = "VIP_MSISDN";
  }
  public class ADM_VIP_PAGES{
    public static final String TABLE_NAME = "ADM_VIP_PAGES";
    public static final String PAGE_ID = "PAGE_ID";
    public static final String FEATURE_ID = "FEATURE_ID";
  }


  public class DSS_FLAGS{
    public static final String TABLE_NAME = "DSS_FLAGS";
    public static final String PAGE_NAME = "PAGE_NAME";
    public static final String FLAG_NAME = "FLAG_NAME";
    public static final String FLAG_VALUE = "FLAG_VALUE";
  }
}
