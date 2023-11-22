--------------------------------------------------------
--  File created - Wednesday-November-22-2023   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table ADM_SMS_TEMPLATE
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_SMS_TEMPLATE"
  ("ID" NUMBER(20,0), 
  "TEMPLATE_ID" NUMBER(20,0),
  "TEMPLATE_TEXT" VARCHAR2(4000),
  "LANGUAGE_ID" NUMBER(1,0), 
  "TEMPLATE_STATUS" NUMBER(1,0) DEFAULT 1, 
  "CS_TEMPLATE_ID" NUMBER, 
  "TEMPLATE_PARAMETERS" VARCHAR2(4000))
--------------------------------------------------------
--  DDL for Table DYN_STEP_SP_CONFIGURATION
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."DYN_STEP_SP_CONFIGURATION" ("CONFIG_ID" NUMBER(10,0), "STEP_ID" NUMBER(10,0), "DB_URL" VARCHAR2(500), "DB_USERNAME" VARCHAR2(500), "DB_PASSWORD" VARCHAR2(100), "PROCEDURE_NAME" VARCHAR2(100), "MAX_RECORDS" NUMBER, "SUCCESS_CODE" VARCHAR2(100), "IS_DELETED" NUMBER(1,0) DEFAULT 0, "EXTRA_CONFIGURATIONS" VARCHAR2(4000))
--------------------------------------------------------
--  DDL for Table LK_LANGUAGES
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_LANGUAGES" ("ID" NUMBER(10,0), "NAME" VARCHAR2(100))
--------------------------------------------------------
--  DDL for Table NBA_DUMMY_TABLE_BK
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."NBA_DUMMY_TABLE_BK" ("GIFTID" VARCHAR2(20), "GIFTSEQID" VARCHAR2(20), "GIFTDESCRIPTION" VARCHAR2(2000), "GIFTSTATUS" VARCHAR2(20), "TIMEOFASSIGNMENT" VARCHAR2(20), "TIMEOFASSIGNMENTEXPIRY" VARCHAR2(20), "TIMEOFRDEMPTION" VARCHAR2(20), "GIFTNODAYS" VARCHAR2(20), "GIFTSHORTCODE" VARCHAR2(20), "GIFTUNITS" VARCHAR2(20), "GIFTFEES" VARCHAR2(20), "GIFTPARAM1" VARCHAR2(20), "GIFTPARAM2" VARCHAR2(20), "GIFTPARAM3" VARCHAR2(20), "GIFTPARAM4" VARCHAR2(20), "GIFTPARAM5" VARCHAR2(20), "GIFTPARAM6" VARCHAR2(20), "GIFTPARAM7" VARCHAR2(20), "GIFTPARAM8" VARCHAR2(20), "GIFTPARAM9" VARCHAR2(20), "GIFTPARAM10" VARCHAR2(20), "GIFTPARAM11" VARCHAR2(2020), "GIFTPARAM13" VARCHAR2(2000), "GIFTPARAM21" VARCHAR2(20), "GIFTPARAM15" VARCHAR2(20), "GIFTPARAM16" VARCHAR2(20), "GIFTPARAM18" VARCHAR2(20), "GIFTPARAM17" VARCHAR2(20), "GIFTPARAM19" VARCHAR2(20), "GIFTPARAM20" VARCHAR2(20), "GIFTPARAM14" VARCHAR2(20), "GIFTPARAM22" VARCHAR2(20), "GIFTTYPE" VARCHAR2(20))
--------------------------------------------------------
--  DDL for Table TX_SUBSCRIBER_ADJUSTMENTS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."TX_SUBSCRIBER_ADJUSTMENTS" (
  "ID" NUMBER(16,0), "MSISDN" VARCHAR2(13), "MSISDN_MOD_X" NUMBER(2,0), "ADJ_DATE" DATE)
  PARTITION BY RANGE (ADJ_DATE)
INTERVAL (NUMTODSINTERVAL(1, 'DAY'))
(
    PARTITION VALUES LESS THAN (TIMESTAMP '2023-01-01 00:00:00')
);
--------------------------------------------------------
--  DDL for Table ODS_NODES
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ODS_NODES" ("ID" NUMBER(6,0) GENERATED ALWAYS AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE , "ADDRESS" VARCHAR2(2000), "PORT" NUMBER(5,0), "USER_NAME" VARCHAR2(1000), "PASSWORD" VARCHAR2(2000), "NUMBER_OF_SESSIONS" NUMBER(5,0), "CONCURRENT_CALLS" NUMBER(5,0), "CONNECTION_TIMEOUT" NUMBER(6,0), "EXTRA_CONF" VARCHAR2(2000), "SCHEMA" VARCHAR2(1000))
--------------------------------------------------------
--  DDL for Table ADM_SERVICE_OFFERING_PLANS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_SERVICE_OFFERING_PLANS" ("PLAN_ID" NUMBER(6,0), "NAME" VARCHAR2(50), "IS_DELETED" NUMBER(1,0) DEFAULT 0)
--------------------------------------------------------
--  DDL for Table ADM_PROMOTION_PLANS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_PROMOTION_PLANS" ("ID" NUMBER(6,0), "PLAN_ID" VARCHAR2(100), "NAME" VARCHAR2(50), "IS_DEFAULT" NUMBER(1,0) DEFAULT 0, "IS_DELETED" NUMBER(1,0) DEFAULT 0)
--------------------------------------------------------
--  DDL for Table LK_SMS_TEMPLATE_PARAM
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_SMS_TEMPLATE_PARAM" ("PARAMETER_ID" NUMBER(20,0), "SMS_ACTION_ID" NUMBER(20,0), "PARAMETER_NAME" VARCHAR2(200))
--------------------------------------------------------
--  DDL for Table LK_FLEX_OFFER_TYPE
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_FLEX_OFFER_TYPE" ("TYPE_ID" NUMBER, "TYPE_NAME" VARCHAR2(10 CHAR))
--------------------------------------------------------
--  DDL for Table ADM_TX_CODES
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_TX_CODES" ("ID" NUMBER(5,0), "NAME" VARCHAR2(100), "VALUE" VARCHAR2(50), "IS_DEFAULT" NUMBER(1,0) DEFAULT 0, "IS_DELETED" NUMBER(1,0) DEFAULT 0, "DESCRIPTION" VARCHAR2(100))
--------------------------------------------------------
--  DDL for Table LK_VOUCHER_SERVERS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_VOUCHER_SERVERS" ("URL" VARCHAR2(1000), "AGENT_NAME" VARCHAR2(100), "HOST" VARCHAR2(100), "AUTHORIZATION" VARCHAR2(100), "SERVER_INDEX" NUMBER, "VOUCHER_SERIAL_LENGTH" NUMBER, "CAPABILITY_VALUE" VARCHAR2(500))
--------------------------------------------------------
--  DDL for Table DYN_STEP_SP_CURSOR_MAPPING
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."DYN_STEP_SP_CURSOR_MAPPING" ("MAPPING_ID" NUMBER(10,0), "PARAMETER_ID" NUMBER(10,0), "ACTUAL_COLUMN_NAME" VARCHAR2(30), "DISPLAY_COLUMN_NAME" VARCHAR2(100), "IS_DELETED" NUMBER(1,0) DEFAULT 0)
--------------------------------------------------------
--  DDL for Table LK_BOOLEAN
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_BOOLEAN" ("BOOLEAN_ID" NUMBER(1,0), "BOOLEAN_VALUE" VARCHAR2(20))
--------------------------------------------------------
--  DDL for Table LK_UNIT_TYPE_DESC
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_UNIT_TYPE_DESC" ("ID" NUMBER, "NAME" VARCHAR2(200))
--------------------------------------------------------
--  DDL for Table LK_FEATURES
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_FEATURES" ("ID" NUMBER(10,0), "NAME" VARCHAR2(100), "PAGE_NAME" VARCHAR2(100), "TYPE" NUMBER(*,0), "URL" VARCHAR2(100), "DESCRIPTION" VARCHAR2(100), "LABEL" VARCHAR2(100), "ICON" VARCHAR2(100))
--------------------------------------------------------
--  DDL for Table ADM_FAF_INDICATORS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_FAF_INDICATORS" ("ID" NUMBER(6,0), "INDICATOR_ID" NUMBER(6,0), "INDICATOR_NAME" VARCHAR2(50), "IS_DELETED" NUMBER(1,0) DEFAULT 0, "MAPPED_INDICATOR_ID" NUMBER(6,0))
--------------------------------------------------------
--  DDL for Table ADM_FAF_BLACK_RESTRICTIONS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_FAF_BLACK_RESTRICTIONS" ("RESTRICTION_ID" NUMBER(6,0), "NUMBER_PATTERN" VARCHAR2(18), "DESCRIPTION" VARCHAR2(50), "IS_DELETED" NUMBER(1,0) DEFAULT 0)
--------------------------------------------------------
--  DDL for Table ADM_PROFILES
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_PROFILES" ("PROFILE_ID" NUMBER(6,0), "PROFILE_NAME" VARCHAR2(100), "IS_DELETED" NUMBER(1,0) DEFAULT 0, "IS_FOOTPRINT_REQUIRED" NUMBER(1,0) DEFAULT 1, "SESSION_LIMIT" NUMBER(6,0), "ADJUSTMENTS_LIMITED" NUMBER(1,0), "CREATION_DATE" TIMESTAMP (6))
--------------------------------------------------------
--  DDL for Table LK_DISCONNECTION_CODES
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_DISCONNECTION_CODES" ("ID" NUMBER(20,0), "CODE_ID" NUMBER(20,0), "NAME" VARCHAR2(50), "IS_DELETED" NUMBER(1,0) DEFAULT 0, "CREATION_DATE" TIMESTAMP (6) DEFAULT SYSTIMESTAMP)
--------------------------------------------------------
--  DDL for Table ADM_SERVICE_CLASS_ACC
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_SERVICE_CLASS_ACC" ("SERVICE_CLASS_ID" NUMBER(6,0), "DESCRIPTION" NVARCHAR2(50), "ACC_ID" NUMBER(2,0))
--------------------------------------------------------
--  DDL for Table DYN_PAGES_STEPS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."DYN_PAGES_STEPS" ("STEP_ID" NUMBER, "PAGE_ID" NUMBER, "STEP_TYPE" NUMBER(1,0), "STEP_NAME" VARCHAR2(100), "STEP_ORDER" NUMBER(6,0), "IS_DELETED" NUMBER(1,0) DEFAULT 0, "IS_HIDDEN" NUMBER(1,0) DEFAULT 0)
--------------------------------------------------------
--  DDL for Table BK_SYSTEM_PROPERTIES
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."BK_SYSTEM_PROPERTIES" ("APPLICATION" VARCHAR2(100), "PROFILE" VARCHAR2(100), "LABEL" VARCHAR2(100), "VALUE" VARCHAR2(1000), "CODE" VARCHAR2(50), "DESCRIPTION" VARCHAR2(1000), "TYPE" NUMBER(1,0))
--------------------------------------------------------
--  DDL for Table ADM_ACCOUNT_GROUP_BITS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_ACCOUNT_GROUP_BITS" ("BIT_POSITION" NUMBER(2,0), "BIT_NAME" VARCHAR2(50))
--------------------------------------------------------
--  DDL for Table ODS_H_ACC_ACTIVITY_D
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ODS_H_ACC_ACTIVITY_D" ("ACTIVITY_ID" NUMBER(10,0), "COLUMN_IDX" NUMBER(10,0), "COLUMN_KEY" VARCHAR2(100), "DISPLAY_NAME" VARCHAR2(100), "IS_DELETED" NUMBER, "DETAIL_TYPE" VARCHAR2(100), "DISPLAY_ORDER" NUMBER(10,0))
--------------------------------------------------------
--  DDL for Table LK_FOOTPRINT_PAGE_INFO
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_FOOTPRINT_PAGE_INFO" ("PAGE_ID" NUMBER, "METHOD_NAME" VARCHAR2(100), "ACTION_NAME" VARCHAR2(100), "ACTION_TYPE" VARCHAR2(100))
--------------------------------------------------------
--  DDL for Table DYN_STEP_SP_PARAMETERS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."DYN_STEP_SP_PARAMETERS" ("PARAM_ID" NUMBER(10,0), "CONFIG_ID" NUMBER(10,0), "PARAMETER_NAME" VARCHAR2(100), "PARAMETER_DATA_TYPE" NUMBER(10,0), "PARAMETER_TYPE" NUMBER(10,0), "PARAMETER_ORDER" NUMBER(10,0), "INPUT_METHOD" NUMBER(10,0), "DEFAULT_VALUE" VARCHAR2(4000), "IS_RESPONSE_CODE" NUMBER(1,0), "IS_RESPONSE_DESCRIPTION" NUMBER(1,0), "DISPLAY_NAME" VARCHAR2(100), "IS_HIDDEN" NUMBER(1,0), "SOURCE_STEP_PARAMETER_ID" NUMBER(10,0), "IS_DELETED" NUMBER(1,0) DEFAULT 0, "DISPLAY_ORDER" NUMBER(6,0), "DATE_FORMAT" VARCHAR2(1080), "STATIC_DATA" VARCHAR2(1080), "SOURCE_STEP_PARAMETER_NAME" VARCHAR2(100))
--------------------------------------------------------
--  DDL for Table ADM_FAF_PLANS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_FAF_PLANS" ("PLAN_ID" NUMBER(6,0), "NAME" VARCHAR2(50), "INDICATOR_ID" NUMBER(6,0), "IS_DELETED" NUMBER(1,0) DEFAULT 0)
--------------------------------------------------------
--  DDL for Table LK_AIR_SERVERS_BK
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_AIR_SERVERS_BK" ("URL" VARCHAR2(1000), "AGENT_NAME" VARCHAR2(100), "HOST" VARCHAR2(100), "AUTHORIZATION" VARCHAR2(100), "IS_DOWN" NUMBER(1,0), "ID" NUMBER(1,0), "CAPABILITY_VALUE" VARCHAR2(500))
--------------------------------------------------------
--  DDL for Table SMS_Q
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."SMS_Q" 
	(
	"SMS_ID" NUMBER(20,0), 
	"ENTRY_DATE" DATE,
	"MSISDN" VARCHAR2(12),
	"SMS_TEXT" VARCHAR2(300),
	"TEMPLATE_ID" NUMBER(20,0),
	"LANG_ID" NUMBER(1,0),
	"GATEWAY_ID" NUMBER(1,0),
	"PRIORITY_ID" NUMBER(1,0) DEFAULT 0,
	"CS_TEMPLATES_ID" NUMBER,
	"CS_TEMPLATES_PARAMETERS" VARCHAR2(4000))
PARTITION BY RANGE (ENTRY_DATE)
INTERVAL (NUMTODSINTERVAL(1, 'DAY'))
(
    PARTITION VALUES LESS THAN (TIMESTAMP '2023-01-01 00:00:00')
);
--------------------------------------------------------
--  DDL for Table LK_PAM_SCHEDULES
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_PAM_SCHEDULES" ("ID" NUMBER, "NAME" VARCHAR2(200))
--------------------------------------------------------
--  DDL for Table ADM_PROFILE_SERVICE_CLASS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_PROFILE_SERVICE_CLASS" ("SERVICE_CLASS_ID" NUMBER(6,0), "PROFILE_ID" NUMBER(6,0))
--------------------------------------------------------
--  DDL for Table TX_USER_FOOTPRINT
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."TX_USER_FOOTPRINT" (
  "ID" NUMBER(16,0), 
  "REQUEST_ID" VARCHAR2(100), 
  "PAGE_NAME" VARCHAR2(100), 
  "TAB_NAME" VARCHAR2(100), 
  "ACTION_NAME" VARCHAR2(100),
  "ACTION_TYPE" VARCHAR2(100),
  "ACTION_TIME" TIMESTAMP (6) DEFAULT systimestamp, 
  "USERNAME" VARCHAR2(100),
  "PROFILE_NAME" VARCHAR2(100), 
  "MSISDN" VARCHAR2(100),
  "STATUS" VARCHAR2(100),
  "ERROR_MESSAGE" VARCHAR2(100),
  "ERROR_CODE" VARCHAR2(100), 
  "SESSION_ID" VARCHAR2(100), 
  "CREATED_BY" NUMBER(6,0),
  "MACHINE_NAME" VARCHAR2(100))
  PARTITION BY RANGE (ACTION_TIME)
INTERVAL (NUMTODSINTERVAL(1, 'DAY'))
(
    PARTITION VALUES LESS THAN (TIMESTAMP '2023-01-01 00:00:00')
);
--------------------------------------------------------
--  DDL for Table LK_REFILL_PAYMENT_PROFILE
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_REFILL_PAYMENT_PROFILE" ("REFILL_PROFILE_ID" VARCHAR2(50), "REFILL_PROFILE_NAME" VARCHAR2(100), "AMOUNT_FROM" NUMBER(12,2), "AMOUNT_TO" NUMBER(12,2))
--------------------------------------------------------
--  DDL for Table H_NOTEPAD_ENTRIES
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."H_NOTEPAD_ENTRIES" (
	"ENTRY_DATE" DATE DEFAULT sysdate, 
	"MSISDN" VARCHAR2(13), 
	"MSISDN_MOD_X" NUMBER(2,0), 
	"USER_ID" NUMBER(6,0), 
	"NOTEPAD_ENTRY" VARCHAR2(4000), 
	"USER_NAME" VARCHAR2(200)) 
	PARTITION BY RANGE (ENTRY_DATE)
INTERVAL (NUMTODSINTERVAL(1, 'DAY'))
(
    PARTITION VALUES LESS THAN (TIMESTAMP '2023-01-01 00:00:00')
);
--------------------------------------------------------
--  DDL for Table TX_LOGIN
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."TX_LOGIN" (
  "TX_LOGIN_ID" NUMBER(16,0) 
	GENERATED ALWAYS AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 
		INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE , 
	"MACHINE_NAME" VARCHAR2(100),
	"DOMAIN_NAME" VARCHAR2(100), 
	"LOGIN_TIME" TIMESTAMP (6) 
	DEFAULT SYSTIMESTAMP, 
	"STATUS" NUMBER(1,0) DEFAULT 0, 
	"MESSAGE" VARCHAR2(200), "USER_ID" NUMBER(6,0))
PARTITION BY RANGE (LOGIN_TIME)
INTERVAL (NUMTODSINTERVAL(1, 'DAY'))
(
    PARTITION VALUES LESS THAN (TIMESTAMP '2023-01-01 00:00:00')
);
--------------------------------------------------------
--  DDL for Table LK_OFFERS_STATES
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_OFFERS_STATES" ("STATE_ID" NUMBER, "STATE_DESC" VARCHAR2(200))
--------------------------------------------------------
--  DDL for Table LK_ACCUMULATORS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_ACCUMULATORS" ("ACC_ID" NUMBER(2,0))
--------------------------------------------------------
--  DDL for Table LK_MAREDCARD_LIST
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_MAREDCARD_LIST" ("NAME" VARCHAR2(100), "ID" VARCHAR2(100))
--------------------------------------------------------
--  DDL for Table LK_CC_FEATURES
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_CC_FEATURES" ("CC_FEATURE_ID" NUMBER(10,0), "CC_FEATURE_NAME" VARCHAR2(100), "HAS_TRX_TYPE" NUMBER(1,0), "PAGE_NAME" VARCHAR2(100))
--------------------------------------------------------
--  DDL for Table TX_CALL_REASONS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."TX_CALL_REASONS" (
	"USER_ID" NUMBER(6,0),
	"USER_NAME" VARCHAR2(50),
	"MSISDN" VARCHAR2(20), 
	"MSISDN_LAST_DIGIT" NUMBER, 
	"ENTRY_DATE" DATE DEFAULT sysdate, 
	"DIRECTION" VARCHAR2(50), 
	"FAMILY" VARCHAR2(50), 
	"TYPE" VARCHAR2(50),
	"REASON" VARCHAR2(50), 
	"CALL_REASON_ID" NUMBER)  
	PARTITION BY RANGE ("ENTRY_DATE") INTERVAL (NUMTODSINTERVAL(1, 'DAY'))  SUBPARTITION BY LIST ("MSISDN_LAST_DIGIT")  (PARTITION "P0"  VALUES LESS THAN (TO_DATE(' 2022-06-07 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))  ( SUBPARTITION "S0"  VALUES (0) ,  SUBPARTITION "S1"  VALUES (1) ,  SUBPARTITION "S2"  VALUES (2) ,  SUBPARTITION "S3"  VALUES (3) ,  SUBPARTITION "S4"  VALUES (4) ,  SUBPARTITION "S5"  VALUES (5) ,  SUBPARTITION "S6"  VALUES (6) ,  SUBPARTITION "S7"  VALUES (7) ,  SUBPARTITION "S8"  VALUES (8) ,  SUBPARTITION "S9"  VALUES (9) ) )
--------------------------------------------------------
--  DDL for Table ADM_BUSINESS_PLANS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_BUSINESS_PLANS" ("ID" NUMBER(6,0), "NAME" VARCHAR2(50), "CODE" NUMBER(4,0), "SERVICE_CLASS_ID" NUMBER(6,0), "HLR_ID" NUMBER(6,0), "IS_DELETED" NUMBER(1,0) DEFAULT 0, "ACTIVATION_PLAN" NUMBER DEFAULT 0)
--------------------------------------------------------
--  DDL for Table TEST_FILE_DATA
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."TEST_FILE_DATA" ("ID" NUMBER, "NAME" VARCHAR2(15), "MOBILE" VARCHAR2(12), "MAIL" VARCHAR2(20), "CREATEDAT" TIMESTAMP (6) DEFAULT systimestamp)
--------------------------------------------------------
--  DDL for Table ODS_H_ACC_HEADERS_MAPPING_BK
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ODS_H_ACC_HEADERS_MAPPING_BK" ("ACTIVITY_ID" NUMBER(10,0), "HEADER_ID" VARCHAR2(100), "COLUMN_IDX" NUMBER(10,0), "IS_STATIC" NUMBER(10,0), "CUSTOM_FORMAT" VARCHAR2(100), "DEFAULT_VALUE" VARCHAR2(100), "IS_DELETED" NUMBER, "PRE_CONDITIONS" VARCHAR2(500), "PRE_CONDITIONS_VALUE" VARCHAR2(500))
--------------------------------------------------------
--  DDL for Table ADM_AG_BITS_SC_DESCRIPTION
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_AG_BITS_SC_DESCRIPTION" ("BIT_POSITION" NUMBER(2,0), "SERVICE_CLASS_ID" NUMBER(6,0), "DESCRIPTION" VARCHAR2(200))
--------------------------------------------------------
--  DDL for Table LK_PAM_SERVICES
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_PAM_SERVICES" ("ID" NUMBER, "NAME" VARCHAR2(200))
--------------------------------------------------------
--  DDL for Table ADM_TX_TYPES
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_TX_TYPES" ("ID" NUMBER(5,0), "NAME" VARCHAR2(100), "VALUE" VARCHAR2(50), "IS_DEFAULT" NUMBER(1,0) DEFAULT 0, "IS_DELETED" NUMBER(1,0) DEFAULT 0)
--------------------------------------------------------
--  DDL for Table LK_ACCOUNT_FLAGS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_ACCOUNT_FLAGS" ("ACCOUNT_FLAG_CODE" VARCHAR2(20), "ACCOUNT_FLAG_NAME" VARCHAR2(100))
--------------------------------------------------------
--  DDL for Table ADM_REASON_ACTIVITY
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_REASON_ACTIVITY" ("ACTIVITY_ID" NUMBER, "ACTIVITY_TYPE" VARCHAR2(20), "PARENT_ID" NUMBER, "ACTIVITY_NAME" VARCHAR2(50), "IS_ACTIVE" NUMBER)
--------------------------------------------------------
--  DDL for Table BALANCE_DISPUTE_INTERFACE
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."BALANCE_DISPUTE_INTERFACE" ("SP_NAME" VARCHAR2(4000), "ID" NUMBER, "INPUT_PARAMETERS" VARCHAR2(4000), "MAX_NO_PARAMETER" NUMBER)
--------------------------------------------------------
--  DDL for Table ADM_SMS_GATEWAYS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_SMS_GATEWAYS" ("ID" NUMBER(10,0), "PRC_NAME" VARCHAR2(200), "PRC_SHARE" NUMBER(2,0), "ENTRY_DATE" DATE, "STATUS_ID" NUMBER(1,0), "IS_SHARED" NUMBER(1,0) DEFAULT 1, "APP_ID" VARCHAR2(50) DEFAULT 'CCAT', "ORIG_MSISDN" VARCHAR2(50) DEFAULT 'Vodafone', "MSG_TYPE" NUMBER(1,0) DEFAULT 0, "ORIG_TYPE" NUMBER(1,0) DEFAULT 2)
--------------------------------------------------------
--  DDL for Table LK_USAGE_COUNTER_DESC
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_USAGE_COUNTER_DESC" ("ID" NUMBER(10,0), "NAME" VARCHAR2(200))
--------------------------------------------------------
--  DDL for Table ADM_PROFILE_MONETARY_LIMIT
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_PROFILE_MONETARY_LIMIT" ("PROFILE_ID" NUMBER(6,0), "LIMIT_ID" NUMBER(2,0), "VALUE" NUMBER(10,3))
--------------------------------------------------------
--  DDL for Table ADM_TX_FEATURE_TYPES
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_TX_FEATURE_TYPES" ("FEATURE_ID" NUMBER(3,0), "TYPE_ID" NUMBER(5,0))
--------------------------------------------------------
--  DDL for Table LK_BT_TRANSACTION_STATUS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_BT_TRANSACTION_STATUS" ("CODE" VARCHAR2(20), "NAME" VARCHAR2(100))
--------------------------------------------------------
--  DDL for Table LK_MONETARY_LIMITS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_MONETARY_LIMITS" ("LIMIT_ID" NUMBER(2,0), "LIMIT_NAME" VARCHAR2(100), "DEFAULT_VALUE" NUMBER(10,3))
--------------------------------------------------------
--  DDL for Table LK_FLEX_OFFERS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_FLEX_OFFERS" ("ID" NUMBER, "NAME" VARCHAR2(20 CHAR), "DESCRIPTION" VARCHAR2(200 CHAR), "TYPE_ID" NUMBER, "PACKAGE_ID" VARCHAR2(20 CHAR), "OFFER_ID" NUMBER)
--------------------------------------------------------
--  DDL for Table LK_MAIN_PRODUCT_TYPES
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_MAIN_PRODUCT_TYPES" ("TYPE" VARCHAR2(50), "DISPLAY_NAME" VARCHAR2(50))
--------------------------------------------------------
--  DDL for Table LK_FOOTPRINT_PAGES
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_FOOTPRINT_PAGES" ("PAGE_ID" NUMBER, "PAGE_NAME" VARCHAR2(100), "CONTROLLER_NAME" VARCHAR2(100))
--------------------------------------------------------
--  DDL for Table LK_SMS_GATEWAYS_STATUS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_SMS_GATEWAYS_STATUS" ("STATUS_ID" NUMBER(1,0), "STATUS_NAME" VARCHAR2(200))
--------------------------------------------------------
--  DDL for Table ADM_HLR_PROFILES
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_HLR_PROFILES" ("HLR_ID" NUMBER(6,0), "CODE" NUMBER(6,0), "NAME" VARCHAR2(100))
--------------------------------------------------------
--  DDL for Table DYN_PAGES
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."DYN_PAGES" ("PAGE_ID" NUMBER, "PAGE_NAME" VARCHAR2(100), "PRIVILEGE_ID" NUMBER, "REQUIRE_SUBSCRIBER" NUMBER(1,0) DEFAULT 0, "IS_DELETED" NUMBER(1,0) DEFAULT 0, "CREATION_DATE" DATE DEFAULT sysdate, "PRIVILEGE_NAME" VARCHAR2(100))
--------------------------------------------------------
--  DDL for Table ADM_TX_CODES_RENEWAL_VALUE
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_TX_CODES_RENEWAL_VALUE" ("ID" NUMBER, "CODE_ID" NUMBER, "RENEWALS_VALUE" NUMBER)
--------------------------------------------------------
--  DDL for Table ADM_SYSTEM_PROPERTIES
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_SYSTEM_PROPERTIES" ("APPLICATION" VARCHAR2(100), "PROFILE" VARCHAR2(100), "LABEL" VARCHAR2(100), "VALUE" VARCHAR2(1000), "CODE" VARCHAR2(50), "DESCRIPTION" VARCHAR2(1000), "TYPE" NUMBER(1,0))
--------------------------------------------------------
--  DDL for Table ADM_USERS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_USERS" ("USER_ID" NUMBER(6,0), "NT_ACCOUNT" VARCHAR2(100), "PROFILE_ID" NUMBER(6,0), "IS_DELETED" NUMBER(1,0) DEFAULT 0, "IS_ACTIVE" NUMBER(1,0) DEFAULT 1, "SOURCE" VARCHAR2(100), "SESSION_COUNTER" NUMBER(6,0) DEFAULT 0, "THEME_ID" VARCHAR2(100) DEFAULT 'DEFAULT', "BILLING_ACCOUNT" VARCHAR2(200), "CREATION_DATE" DATE DEFAULT sysdate, "MODIFICATION_DATE" DATE DEFAULT sysdate, "LAST_LOGIN" DATE DEFAULT NULL)
--------------------------------------------------------
--  DDL for Table ADM_SO_SC_DESCRIPTION
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_SO_SC_DESCRIPTION" ("PLAN_ID" NUMBER(10,0), "SERVICE_CLASS_ID" NUMBER(6,0), "DESCRIPTION" VARCHAR2(200))
--------------------------------------------------------
--  DDL for Table TX_USER_FOOTPRINT_DETAILS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."TX_USER_FOOTPRINT_DETAILS" (
	  "REQUEST_ID" VARCHAR2(200), 
	  "PARAM_NAME" VARCHAR2(200), 
	  "OLD_VAL" VARCHAR2(200), 
	  "NEW_VAL" VARCHAR2(200))
--------------------------------------------------------
--  DDL for Table ADM_FAF_WHITE_RESTRICTIONS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_FAF_WHITE_RESTRICTIONS" ("RESTRICTION_ID" NUMBER(6,0), "NUMBER_PATTERN" VARCHAR2(18), "DESCRIPTION" VARCHAR2(50), "IS_DELETED" NUMBER(1,0) DEFAULT 0)
--------------------------------------------------------
--  DDL for Table LK_PAM_CLASSES
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_PAM_CLASSES" ("ID" NUMBER(10,0), "NAME" VARCHAR2(200))
--------------------------------------------------------
--  DDL for Table SMS_ERROR
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."SMS_ERROR" (
	"SMS_ERROR_ID" NUMBER(12,0), 
	"ENTRY_DATE" TIMESTAMP (6) DEFAULT SYSTIMESTAMP,
	"MSISDN" VARCHAR2(50), 
	"ERROR_MESSAGE" VARCHAR2(4000), 
	"ERROR_CODE" VARCHAR2(200), 
	"PROCESSED" VARCHAR2(1), 
	"PROCESSED_DATE" TIMESTAMP (6), 
	"PROCEDURE_NAME" VARCHAR2(50))
	PARTITION BY RANGE (ENTRY_DATE)
INTERVAL (NUMTODSINTERVAL(1, 'DAY'))
(
    PARTITION VALUES LESS THAN (TIMESTAMP '2023-01-01 00:00:00')
);
--------------------------------------------------------
--  DDL for Table ADM_SERVICE_CLASSES
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_SERVICE_CLASSES" ("NAME" VARCHAR2(50), "CODE" NUMBER(6,0), "PREACTIVATION_ALLOWED" NUMBER(1,0) DEFAULT 0, "IS_DELETED" NUMBER(1,0) DEFAULT 0, "IS_CI_CONVERSION" NUMBER(1,0), "CI_SERVICE_NAME" VARCHAR2(100), "CI_PACKAGE_NAME" VARCHAR2(100), "IS_ALLOW_MIGRATION" NUMBER(5,0) DEFAULT 1, "TYPE" VARCHAR2(200 CHAR), "HAS_EMPTY_LIMIT" NUMBER(1,0), "DEFAULT_LIMIT" VARCHAR2(100), "CUSTOM_LIMIT" VARCHAR2(100), "IS_GRANDFATHER" NUMBER(10,0))
--------------------------------------------------------
--  DDL for Table LK_PAM_PRIORITY
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_PAM_PRIORITY" ("ID" NUMBER, "NAME" VARCHAR2(200))
--------------------------------------------------------
--  DDL for Table ODS_H_ACC_HEADERS_MAPPING
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ODS_H_ACC_HEADERS_MAPPING" ("ACTIVITY_ID" NUMBER(10,0), "HEADER_ID" VARCHAR2(100), "COLUMN_IDX" NUMBER(10,0), "IS_STATIC" NUMBER(10,0), "CUSTOM_FORMAT" VARCHAR2(100), "DEFAULT_VALUE" VARCHAR2(100), "IS_DELETED" NUMBER, "PRE_CONDITIONS" VARCHAR2(500), "PRE_CONDITIONS_VALUE" VARCHAR2(500))
--------------------------------------------------------
--  DDL for Table LK_BALANCE_DISPUTE_DETAILS_CONFIGURATION
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_BALANCE_DISPUTE_DETAILS_CONFIGURATION" ("COLUMN_NAME" VARCHAR2(100), "DISPLAY_NAME" VARCHAR2(100), "COLUMN_ORDER" NUMBER(3,0), "PRIVILEGE_ID" NUMBER(6,0))
--------------------------------------------------------
--  DDL for Table LK_SMS_ACTION
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_SMS_ACTION" ("SMS_ACTION_ID" NUMBER(20,0), "ACTION_NAME" VARCHAR2(200), "DESCRIPTION" VARCHAR2(200), "CODE" VARCHAR2(200))
--------------------------------------------------------
--  DDL for Table LK_MENUS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_MENUS" ("MENU_ID" NUMBER(6,0), "LABEL" VARCHAR2(100), "PARENT_ID" NUMBER(6,0), "ROUTER" VARCHAR2(100), "ORDERING" NUMBER(6,0), "ICON" VARCHAR2(100))
--------------------------------------------------------
--  DDL for Table ADM_TX_LINKS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_TX_LINKS" ("TX_TYPE_ID" NUMBER(5,0), "TX_CODE_ID" NUMBER(5,0), "DESCRIPTION" VARCHAR2(500))
--------------------------------------------------------
--  DDL for Table TX_USED_VOUCHERS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."TX_USED_VOUCHERS" (
	"VOUCHER_SERIAL_NUMBER" NUMBER(20,0), 
	"AGENT" VARCHAR2(50), 
	"BATCH_ID" VARCHAR2(50), 
	"CURRENCY" VARCHAR2(50),
	"EXPIRY_DATE" VARCHAR2(50),
	"OPERATOR_ID" VARCHAR2(50),
	"RESPONSE_CODE" VARCHAR2(10), 
	"SUBSCRIBER_ID" VARCHAR2(50), 
	"TIMESTAMP" VARCHAR2(50), 
	"STATE" VARCHAR2(20) DEFAULT 'Used', 
	"VALUE" VARCHAR2(50), 
	"VOUCHER_GROUP" VARCHAR2(20),
	"RECHARGE_SOURCE" VARCHAR2(50), 
	"ACTIVATION_CODE" VARCHAR2(50))
PARTITION BY RANGE (EXPIRY_DATE)
INTERVAL (NUMTODSINTERVAL(1, 'DAY'))
(
    PARTITION VALUES LESS THAN (TIMESTAMP '2023-01-01 00:00:00')
);
--------------------------------------------------------
--  DDL for Table DYN_STEP_HTTP_PARAMETERS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."DYN_STEP_HTTP_PARAMETERS" ("PARAM_ID" NUMBER(10,0), "CONFIG_ID" NUMBER(10,0), "PARAMETER_NAME" VARCHAR2(100), "PARAMETER_DATA_TYPE" NUMBER(10,0), "PARAMETER_TYPE" NUMBER(10,0), "PARAMETER_ORDER" NUMBER(10,0), "INPUT_METHOD" NUMBER(10,0), "DEFAULT_VALUE" VARCHAR2(4000), "IS_RESPONSE_CODE" NUMBER(1,0), "IS_RESPONSE_DESCRIPTION" NUMBER(1,0), "DISPLAY_NAME" VARCHAR2(100), "IS_HIDDEN" NUMBER(1,0), "SOURCE_STEP_PARAMETER_ID" NUMBER(10,0), "IS_DELETED" NUMBER(1,0) DEFAULT 0, "DISPLAY_ORDER" NUMBER(6,0), "DATE_FORMAT" VARCHAR2(2000), "STATIC_DATA" VARCHAR2(4000), "SOURCE_STEP_PARAMETER_NAME" VARCHAR2(100), "XPATH" VARCHAR2(4000), "JSON_PATH" VARCHAR2(4000))
--------------------------------------------------------
--  DDL for Table LK_SMS_PRIORITIES
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_SMS_PRIORITIES" ("PRIORITY_ID" NUMBER, "PRIORITY_NAME" VARCHAR2(50))
--------------------------------------------------------
--  DDL for Table ETOPUP_TRANSACTIONS_TEST
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ETOPUP_TRANSACTIONS_TEST" ("TRANSFER_DATE" DATE, "TRANSACTION_WAY" VARCHAR2(32), "TRANSACTION_TYPE" VARCHAR2(32), "TRANSITION_CATEGORY" VARCHAR2(32), "CHANNEL_TYPE" VARCHAR2(32), "SENDER_MSISDN" VARCHAR2(32), "RECEIVER_MSISDN" VARCHAR2(32), "SENDER_CATEGORY" VARCHAR2(32), "RECEIVER_CATEGORY" VARCHAR2(32), "TRANSFER_AMOUNT" NUMBER, "PRODUCT" VARCHAR2(32), "REQUEST_SOURCE" VARCHAR2(32))
--------------------------------------------------------
--  DDL for Table LK_REGIONS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_REGIONS" ("REGION" VARCHAR2(500), "REGION_DESCRIPTION" VARCHAR2(500))
--------------------------------------------------------
--  DDL for Table DYN_STEP_HTTP_RESPONSE_MAPPING
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."DYN_STEP_HTTP_RESPONSE_MAPPING" ("MAPPING_ID" NUMBER, "PARAMETER_ID" NUMBER, "HEADER_NAME" VARCHAR2(1080), "IS_DELETED" NUMBER DEFAULT 0, "HEADER_DISPLAY_NAME" VARCHAR2(1080))
--------------------------------------------------------
--  DDL for Table LK_FEATURES_TYPE
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_FEATURES_TYPE" ("ID" NUMBER(10,0), "NAME" VARCHAR2(100))
--------------------------------------------------------
--  DDL for Table ADM_SO_BITS_SC_DESCRIPTION
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_SO_BITS_SC_DESCRIPTION" ("BIT_POSITION" NUMBER(2,0), "SERVICE_CLASS_ID" NUMBER(6,0), "DESCRIPTION" VARCHAR2(200))
--------------------------------------------------------
--  DDL for Table TX_CUSTOMERS_BARRINGS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."TX_CUSTOMERS_BARRINGS" ("ENTRY_DATE" DATE DEFAULT sysdate, "MSISDN" VARCHAR2(13), "MSISDN_MOD_X" NUMBER(2,0), "BARRING_TYPE" NUMBER(2,0), "REASON" VARCHAR2(500), "REQUESTED_BY" VARCHAR2(500), "USER_ID" NUMBER(6,0))  PARTITION BY LIST ("MSISDN_MOD_X")  (PARTITION "P0"  VALUES (0) , PARTITION "P1"  VALUES (1) , PARTITION "P2"  VALUES (2) , PARTITION "P3"  VALUES (3) , PARTITION "P4"  VALUES (4) , PARTITION "P5"  VALUES (5) , PARTITION "P6"  VALUES (6) , PARTITION "P7"  VALUES (7) , PARTITION "P8"  VALUES (8) , PARTITION "P9"  VALUES (9) )
--------------------------------------------------------
--  DDL for Table LK_DED_ACCOUNTS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_DED_ACCOUNTS" ("DA_ID" NUMBER(2,0))
--------------------------------------------------------
--  DDL for Table LK_PAM_PERIOD
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_PAM_PERIOD" ("ID" VARCHAR2(200), "NAME" VARCHAR2(200))
--------------------------------------------------------
--  DDL for Table ADM_PROFILE_MENUS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_PROFILE_MENUS" ("PROFILE_ID" NUMBER(6,0), "MENU_ID" NUMBER(6,0))
--------------------------------------------------------
--  DDL for Table TEST_VOUCHERS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."TEST_VOUCHERS" ("ID" NUMBER GENERATED BY DEFAULT AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE , "SERIAL_NUMBER" VARCHAR2(5), "REQUEST_ID" RAW(16), "CREATEDAT" TIMESTAMP (6) DEFAULT systimestamp, "AMOUNT" FLOAT(126), "PERCENTAGE" NUMBER)
--------------------------------------------------------
--  DDL for Table FLEX_SHARE_HISTORY_NODES
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."FLEX_SHARE_HISTORY_NODES" ("ID" NUMBER(6,0), "ADDRESS" VARCHAR2(2000), "PORT" NUMBER(5,0), "USERNAME" VARCHAR2(1000), "PASSWORD" VARCHAR2(2000), "NUMBER_OF_SESSIONS" NUMBER(5,0), "CONCURRENT_CALLS" NUMBER(5,0), "CONNECTION_TIMEOUT" NUMBER(6,0), "EXTRA_CONF" VARCHAR2(2000), "SCHEMA" VARCHAR2(1000))
--------------------------------------------------------
--  DDL for Table ADM_SERVICE_CLASS_DA
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_SERVICE_CLASS_DA" ("SERVICE_CLASS_ID" NUMBER(6,0), "DESCRIPTION" NVARCHAR2(50), "DA_ID" NUMBER(2,0), "RATING_FACTOR" FLOAT(10))
--------------------------------------------------------
--  DDL for Table ADM_PROFILE_FEATURES
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_PROFILE_FEATURES" ("PROFILE_ID" NUMBER(6,0), "FEATURE_ID" NUMBER(6,0), "ORDERING" NUMBER(6,0))
--------------------------------------------------------
--  DDL for Table ADM_PROFILE_SOB
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_PROFILE_SOB" ("PROFILE_ID" NUMBER(6,0), "SERVICE_OFFERING_BITS" VARCHAR2(100) DEFAULT '-1')
--------------------------------------------------------
--  DDL for Table ADM_OFFERS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_OFFERS" ("OFFER_ID" NUMBER, "OFFER_DESC" VARCHAR2(200), "OFFER_TYPE_ID" NUMBER, "IS_DROP_DOWN_ENABLED" NUMBER(1,0))
--------------------------------------------------------
--  DDL for Table TX_USER_MONETARY_TOTALS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."TX_USER_MONETARY_TOTALS" (
	"CURR_DATE" DATE DEFAULT SYSDATE,
	"USER_ID" NUMBER(6,0), 
	"LIMIT_ID" NUMBER(2,0), 
	"CURR_VALUE" NUMBER(10,3))
	PARTITION BY RANGE (CURR_DATE)
INTERVAL (NUMTODSINTERVAL(1, 'DAY'))
(
    PARTITION VALUES LESS THAN (TIMESTAMP '2023-01-01 00:00:00')
);
--------------------------------------------------------
--  DDL for Table ADM_COMMUNITIES
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_COMMUNITIES" ("COMMUNITY_ID" NUMBER(8,0), "COMMUNITY_DESCRIPTION" VARCHAR2(50), "IS_DELETED" NUMBER(1,0) DEFAULT 0)
--------------------------------------------------------
--  DDL for Table DSS_NODES
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."DSS_NODES" ("ID" NUMBER(6,0) GENERATED ALWAYS AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE , "ADDRESS" VARCHAR2(2000), "PORT" NUMBER(5,0), "USER_NAME" VARCHAR2(1000), "PASSWORD" VARCHAR2(2000), "NUMBER_OF_SESSIONS" NUMBER(5,0), "CONCURRENT_CALLS" NUMBER(5,0), "CONNECTION_TIMEOUT" NUMBER(6,0), "EXTRA_CONF" VARCHAR2(2000), "SCHEMA" VARCHAR2(1000))
--------------------------------------------------------
--  DDL for Table TESTT_VOUCHERS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."TESTT_VOUCHERS" ("ID" NUMBER GENERATED BY DEFAULT AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE , "SERIAL_NUMBER" VARCHAR2(5), "REQUEST_ID" RAW(16), "CREATEDAT" TIMESTAMP (6) DEFAULT systimestamp, "AMOUNT" FLOAT(126), "PERCENTAGE" NUMBER)
--------------------------------------------------------
--  DDL for Table DYN_STEP_HTTP_CONFIGURATION
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."DYN_STEP_HTTP_CONFIGURATION" ("CONFIG_ID" NUMBER, "STEP_ID" NUMBER, "HTTP_URL" VARCHAR2(4000), "HEADERS" VARCHAR2(4000), "MAX_RECORDS" NUMBER, "SUCCESS_CODE" VARCHAR2(1080), "IS_DELETED" NUMBER DEFAULT 0, "HTTP_METHOD" NUMBER, "REQUEST_CONTENT_TYPE" NUMBER DEFAULT NULL, "RESPONSE_CONTENT_TYPE" NUMBER, "RESPONSE_FORM" NUMBER, "REQUEST_BODY" VARCHAR2(4000), "KEY_VALUE_DELIMITER" VARCHAR2(1080), "MAIN_DELIMITER" VARCHAR2(1080))
--------------------------------------------------------
--  DDL for Table LK_OFFERS_TYPES
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_OFFERS_TYPES" ("TYPE_ID" NUMBER, "TYPE_DESC" VARCHAR2(200))
--------------------------------------------------------
--  DDL for Table DSS_COLUMNS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."DSS_COLUMNS" ("DSS_PAGENAME" VARCHAR2(100), "COLUMN_NAME" VARCHAR2(100), "DISPLAYNAME" VARCHAR2(100))
--------------------------------------------------------
--  DDL for Table ADM_ACCOUNT_GROUPS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_ACCOUNT_GROUPS" ("GROUP_ID" NUMBER(6,0), "NAME" VARCHAR2(100), "IS_DELETED" NUMBER(1,0) DEFAULT 0)
--------------------------------------------------------
--  DDL for Table LK_DATASOURCE_TYPE
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_DATASOURCE_TYPE" ("ID" NUMBER(1,0), "NAME" VARCHAR2(100))
--------------------------------------------------------
--  DDL for Table ODS_H_ACC_ACTIVITIES
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ODS_H_ACC_ACTIVITIES" ("ACTIVITY_ID" NUMBER(10,0), "ACTIVITY_NAME" VARCHAR2(100), "TABLE_TYPE" VARCHAR2(100), "DR_ID_IDX" NUMBER(10,0), "BALANCE_IDX" NUMBER(10,0), "EXP_DATE_IDX" NUMBER(10,0), "DA_AMOUNT_IDX" NUMBER(10,0), "ADJ_ACTION_IDX" NUMBER(10,0), "IS_NEW_FORMAT_IDX" NUMBER(10,0), "IS_DELETED" NUMBER)
--------------------------------------------------------
--  DDL for Table ADM_MARQUEE_DATA
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_MARQUEE_DATA" ("ID" NUMBER(6,0) GENERATED ALWAYS AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE , "TITLE" VARCHAR2(500), "DESCRIPTION" VARCHAR2(1000), "CREATION_DATE" TIMESTAMP (6) DEFAULT SYSTIMESTAMP)
--------------------------------------------------------
--  DDL for Table SIM_UCIP_ACCOUNT_DETAILS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."SIM_UCIP_ACCOUNT_DETAILS" ("SUBSCRIBER" VARCHAR2(300))
--------------------------------------------------------
--  DDL for Table ADM_SERVICE_OFFG_PLAN_BITS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_SERVICE_OFFG_PLAN_BITS" ("PLAN_ID" NUMBER(6,0), "BIT_POSITION" NUMBER(2,0), "IS_ENABLED" NUMBER(1,0))
--------------------------------------------------------
--  DDL for Table LK_AIR_SERVERS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."LK_AIR_SERVERS" ("URL" VARCHAR2(1000), "AGENT_NAME" VARCHAR2(100), "HOST" VARCHAR2(100), "AUTHORIZATION" VARCHAR2(100), "IS_DOWN" NUMBER(1,0) DEFAULT 0, "ID" NUMBER(1,0), "CAPABILITY_VALUE" VARCHAR2(500))
--------------------------------------------------------
--  DDL for Table ODS_H_ACC_HEADERS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ODS_H_ACC_HEADERS" ("HEADER_ID" NUMBER(10,0), "HEADER_NAME" VARCHAR2(100), "HEADER_TYPE" VARCHAR2(100), "DISPLAY_NAME" VARCHAR2(100), "IS_DELETED" NUMBER, "ORDER" NUMBER)
--------------------------------------------------------
--  DDL for Table ADM_SERVICE_OFFERING_BITS
--------------------------------------------------------

  CREATE TABLE "CCAT_DEV3"."ADM_SERVICE_OFFERING_BITS" ("BIT_POSITION" NUMBER(2,0), "BIT_NAME" VARCHAR2(50))
--------------------------------------------------------
--  DDL for Sequence SEQ_DYN_PAGES_STEPS
--------------------------------------------------------

   CREATE SEQUENCE  "CCAT_DEV3"."SEQ_DYN_PAGES_STEPS"  MINVALUE 1 MAXVALUE 999999 INCREMENT BY 1 START WITH 395 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL
--------------------------------------------------------
--  DDL for Sequence SEQ_USERS
--------------------------------------------------------

   CREATE SEQUENCE  "CCAT_DEV3"."SEQ_USERS"  MINVALUE 1 MAXVALUE 999999 INCREMENT BY 1 START WITH 523 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL
--------------------------------------------------------
--  DDL for Sequence SEQ_DYN_STEP_HTTP_RESPONSE_MAPPING
--------------------------------------------------------

   CREATE SEQUENCE  "CCAT_DEV3"."SEQ_DYN_STEP_HTTP_RESPONSE_MAPPING"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 121 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL
--------------------------------------------------------
--  DDL for Sequence S_TX_SUBSCRIBER_ADJUSTMENTS
--------------------------------------------------------

   CREATE SEQUENCE  "CCAT_DEV3"."S_TX_SUBSCRIBER_ADJUSTMENTS"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 463 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL
--------------------------------------------------------
--  DDL for Sequence SEQ_TX_CALL_REASONS
--------------------------------------------------------

   CREATE SEQUENCE  "CCAT_DEV3"."SEQ_TX_CALL_REASONS"  MINVALUE 1 MAXVALUE 999999 INCREMENT BY 1 START WITH 2181 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL
--------------------------------------------------------
--  DDL for Sequence SEQ_DYN_STEP_SP_PARAMETERS
--------------------------------------------------------

   CREATE SEQUENCE  "CCAT_DEV3"."SEQ_DYN_STEP_SP_PARAMETERS"  MINVALUE 1 MAXVALUE 999999 INCREMENT BY 1 START WITH 423 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL
--------------------------------------------------------
--  DDL for Sequence SEQ_DYN_PAGES
--------------------------------------------------------

   CREATE SEQUENCE  "CCAT_DEV3"."SEQ_DYN_PAGES"  MINVALUE 1 MAXVALUE 999999 INCREMENT BY 1 START WITH 242 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL
--------------------------------------------------------
--  DDL for Sequence SEQ_PROFILES
--------------------------------------------------------

   CREATE SEQUENCE  "CCAT_DEV3"."SEQ_PROFILES"  MINVALUE 1 MAXVALUE 999999 INCREMENT BY 1 START WITH 503 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL
--------------------------------------------------------
--  DDL for Sequence SEQ_DYN_STEP_HTTP_CONFIGURATION
--------------------------------------------------------

   CREATE SEQUENCE  "CCAT_DEV3"."SEQ_DYN_STEP_HTTP_CONFIGURATION"  MINVALUE 1 MAXVALUE 999999 INCREMENT BY 1 START WITH 261 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL
--------------------------------------------------------
--  DDL for Sequence S_TX_USER_FOOTPRINT
--------------------------------------------------------

   CREATE SEQUENCE  "CCAT_DEV3"."S_TX_USER_FOOTPRINT"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 119379 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL
--------------------------------------------------------
--  DDL for Sequence SEQ_DYN_STEP_HTTP_PARAMETERS
--------------------------------------------------------

   CREATE SEQUENCE  "CCAT_DEV3"."SEQ_DYN_STEP_HTTP_PARAMETERS"  MINVALUE 1 MAXVALUE 999999 INCREMENT BY 1 START WITH 561 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL
--------------------------------------------------------
--  DDL for Sequence SEQ_DYN_STEP_SP_CONFIGURATION
--------------------------------------------------------

   CREATE SEQUENCE  "CCAT_DEV3"."SEQ_DYN_STEP_SP_CONFIGURATION"  MINVALUE 1 MAXVALUE 999999 INCREMENT BY 1 START WITH 215 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL
--------------------------------------------------------
--  DDL for Sequence SEQ_DYN_STEP_SP_CURSOR_MAPPING
--------------------------------------------------------

   CREATE SEQUENCE  "CCAT_DEV3"."SEQ_DYN_STEP_SP_CURSOR_MAPPING"  MINVALUE 1 MAXVALUE 999999 INCREMENT BY 1 START WITH 422 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL
--------------------------------------------------------
--  DDL for Sequence S_SMSQ
--------------------------------------------------------

   CREATE SEQUENCE  "CCAT_DEV3"."S_SMSQ"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 22473297 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL
--------------------------------------------------------
--  DDL for Sequence S_SMSERRQ
--------------------------------------------------------

   CREATE SEQUENCE  "CCAT_DEV3"."S_SMSERRQ"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 22503517 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL
--------------------------------------------------------
--  DDL for Sequence S_SMS_TEMPLATE
--------------------------------------------------------

   CREATE SEQUENCE  "CCAT_DEV3"."S_SMS_TEMPLATE"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 22469997 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL
--------------------------------------------------------
--  DDL for Sequence S_LK_DISCONNECTION_CODES
--------------------------------------------------------

   CREATE SEQUENCE  "CCAT_DEV3"."S_LK_DISCONNECTION_CODES"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 144 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL
--------------------------------------------------------
--  DDL for Sequence S_LK_AIR_SERVERS
--------------------------------------------------------

   CREATE SEQUENCE  "CCAT_DEV3"."S_LK_AIR_SERVERS"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 22 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL
--------------------------------------------------------
--  DDL for Sequence S_FLEX_SHARE_HISTORY_NODES
--------------------------------------------------------

   CREATE SEQUENCE  "CCAT_DEV3"."S_FLEX_SHARE_HISTORY_NODES"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 62 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL
--------------------------------------------------------
--  DDL for Sequence S_ADM_TX_TYPES
--------------------------------------------------------

   CREATE SEQUENCE  "CCAT_DEV3"."S_ADM_TX_TYPES"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 129 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL
--------------------------------------------------------
--  DDL for Sequence S_ADM_TX_CODES
--------------------------------------------------------

   CREATE SEQUENCE  "CCAT_DEV3"."S_ADM_TX_CODES"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 92 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL
--------------------------------------------------------
--  DDL for Sequence S_ADM_SERVICE_CLASSES
--------------------------------------------------------

   CREATE SEQUENCE  "CCAT_DEV3"."S_ADM_SERVICE_CLASSES"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 2124 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL
--------------------------------------------------------
--  DDL for Sequence S_ADM_REASON_ACTIVITY
--------------------------------------------------------

   CREATE SEQUENCE  "CCAT_DEV3"."S_ADM_REASON_ACTIVITY"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 466 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL
--------------------------------------------------------
--  DDL for Sequence S_ADM_BUSINESS_PLANS
--------------------------------------------------------

   CREATE SEQUENCE  "CCAT_DEV3"."S_ADM_BUSINESS_PLANS"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 345 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL
-- Unable to render SEQUENCE DDL for object CCAT_DEV3.ISEQ$$_1143802 with DBMS_METADATA attempting internal generator.
CREATE SEQUENCE ISEQ$$_1143802 INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20
-- Unable to render SEQUENCE DDL for object CCAT_DEV3.ISEQ$$_1132543 with DBMS_METADATA attempting internal generator.
CREATE SEQUENCE ISEQ$$_1132543 INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20
-- Unable to render SEQUENCE DDL for object CCAT_DEV3.ISEQ$$_1132533 with DBMS_METADATA attempting internal generator.
CREATE SEQUENCE ISEQ$$_1132533 INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20
-- Unable to render SEQUENCE DDL for object CCAT_DEV3.ISEQ$$_1132530 with DBMS_METADATA attempting internal generator.
CREATE SEQUENCE ISEQ$$_1132530 INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20
-- Unable to render SEQUENCE DDL for object CCAT_DEV3.ISEQ$$_1131989 with DBMS_METADATA attempting internal generator.
CREATE SEQUENCE ISEQ$$_1131989 INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20
-- Unable to render SEQUENCE DDL for object CCAT_DEV3.ISEQ$$_1131986 with DBMS_METADATA attempting internal generator.
CREATE SEQUENCE ISEQ$$_1131986 INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20
-- Unable to render SEQUENCE DDL for object CCAT_DEV3.ISEQ$$_1131983 with DBMS_METADATA attempting internal generator.
CREATE SEQUENCE ISEQ$$_1131983 INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20
-- Unable to render SEQUENCE DDL for object CCAT_DEV3.ISEQ$$_1125520 with DBMS_METADATA attempting internal generator.
CREATE SEQUENCE ISEQ$$_1125520 INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20
-- Unable to render SEQUENCE DDL for object CCAT_DEV3.ISEQ$$_1125522 with DBMS_METADATA attempting internal generator.
CREATE SEQUENCE ISEQ$$_1125522 INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20
-- Unable to render SEQUENCE DDL for object CCAT_DEV3.ISEQ$$_1125479 with DBMS_METADATA attempting internal generator.
CREATE SEQUENCE ISEQ$$_1125479 INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20
-- Unable to render SEQUENCE DDL for object CCAT_DEV3.ISEQ$$_1125457 with DBMS_METADATA attempting internal generator.
CREATE SEQUENCE ISEQ$$_1125457 INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20
--------------------------------------------------------
--  DDL for Sequence ISEQ$$_1112413
--------------------------------------------------------

   CREATE SEQUENCE  "CCAT_DEV3"."ISEQ$$_1112413"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL
--------------------------------------------------------
--  DDL for Sequence ISEQ$$_1112411
--------------------------------------------------------

   CREATE SEQUENCE  "CCAT_DEV3"."ISEQ$$_1112411"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL
--------------------------------------------------------
--  DDL for Sequence ISEQ$$_1105382
--------------------------------------------------------

   CREATE SEQUENCE  "CCAT_DEV3"."ISEQ$$_1105382"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL
--------------------------------------------------------
--  DDL for Sequence ISEQ$$_1105380
--------------------------------------------------------

   CREATE SEQUENCE  "CCAT_DEV3"."ISEQ$$_1105380"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL
--------------------------------------------------------
--  DDL for Sequence ISEQ$$_1102353
--------------------------------------------------------

   CREATE SEQUENCE  "CCAT_DEV3"."ISEQ$$_1102353"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL
--------------------------------------------------------
--  DDL for Sequence ISEQ$$_1074402
--------------------------------------------------------

   CREATE SEQUENCE  "CCAT_DEV3"."ISEQ$$_1074402"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL
--------------------------------------------------------
--  DDL for Sequence HIBERNATE_SEQUENCE
--------------------------------------------------------

   CREATE SEQUENCE  "CCAT_DEV3"."HIBERNATE_SEQUENCE"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL
--------------------------------------------------------
--  DDL for Index SMS_TEMPLT_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."SMS_TEMPLT_PK" ON "CCAT_DEV3"."ADM_SMS_TEMPLATE" ("ID")
--------------------------------------------------------
--  DDL for Index DYN_STEP_SP_CONFIGURATION_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."DYN_STEP_SP_CONFIGURATION_PK" ON "CCAT_DEV3"."DYN_STEP_SP_CONFIGURATION" ("CONFIG_ID")
--------------------------------------------------------
--  DDL for Index ADM_SERVICE_OFFERING_PLANS_UK1
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."ADM_SERVICE_OFFERING_PLANS_UK1" ON "CCAT_DEV3"."ADM_SERVICE_OFFERING_PLANS" ("NAME", "PLAN_ID")
--------------------------------------------------------
--  DDL for Index ADM_PROM_PLAN_DEFAULT_INDX
--------------------------------------------------------

  CREATE INDEX "CCAT_DEV3"."ADM_PROM_PLAN_DEFAULT_INDX" ON "CCAT_DEV3"."ADM_PROMOTION_PLANS" ("IS_DEFAULT")
--------------------------------------------------------
--  DDL for Index ADM_PROM_PLAN_DEL_INDX
--------------------------------------------------------

  CREATE INDEX "CCAT_DEV3"."ADM_PROM_PLAN_DEL_INDX" ON "CCAT_DEV3"."ADM_PROMOTION_PLANS" ("IS_DELETED")
--------------------------------------------------------
--  DDL for Index PK_ADM_PROMOTION_PLANS
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."PK_ADM_PROMOTION_PLANS" ON "CCAT_DEV3"."ADM_PROMOTION_PLANS" ("ID")
--------------------------------------------------------
--  DDL for Index SMS_PARAM_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."SMS_PARAM_PK" ON "CCAT_DEV3"."LK_SMS_TEMPLATE_PARAM" ("PARAMETER_ID", "SMS_ACTION_ID")
--------------------------------------------------------
--  DDL for Index LK_FLEX_OFFER_TYPE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."LK_FLEX_OFFER_TYPE_PK" ON "CCAT_DEV3"."LK_FLEX_OFFER_TYPE" ("TYPE_ID")
--------------------------------------------------------
--  DDL for Index PK_ADM_TX_CODES
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."PK_ADM_TX_CODES" ON "CCAT_DEV3"."ADM_TX_CODES" ("ID")
--------------------------------------------------------
--  DDL for Index ADM_TX_CODES_DEF_INDX
--------------------------------------------------------

  CREATE INDEX "CCAT_DEV3"."ADM_TX_CODES_DEF_INDX" ON "CCAT_DEV3"."ADM_TX_CODES" ("IS_DEFAULT")
--------------------------------------------------------
--  DDL for Index ADM_TX_CODES_DEL_INDX
--------------------------------------------------------

  CREATE INDEX "CCAT_DEV3"."ADM_TX_CODES_DEL_INDX" ON "CCAT_DEV3"."ADM_TX_CODES" ("IS_DELETED")
--------------------------------------------------------
--  DDL for Index LK_VOUCHER_SERVERS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."LK_VOUCHER_SERVERS_PK" ON "CCAT_DEV3"."LK_VOUCHER_SERVERS" ("SERVER_INDEX", "VOUCHER_SERIAL_LENGTH")
--------------------------------------------------------
--  DDL for Index DYN_STEP_SP_CURSOR_MAPPING_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."DYN_STEP_SP_CURSOR_MAPPING_PK" ON "CCAT_DEV3"."DYN_STEP_SP_CURSOR_MAPPING" ("MAPPING_ID")
--------------------------------------------------------
--  DDL for Index PK_LK_BOOLEAN
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."PK_LK_BOOLEAN" ON "CCAT_DEV3"."LK_BOOLEAN" ("BOOLEAN_ID")
--------------------------------------------------------
--  DDL for Index LK_FEATURES_NEW_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."LK_FEATURES_NEW_PK" ON "CCAT_DEV3"."LK_FEATURES" ("ID")
--------------------------------------------------------
--  DDL for Index ADM_PROFILES_DEL_INDX
--------------------------------------------------------

  CREATE INDEX "CCAT_DEV3"."ADM_PROFILES_DEL_INDX" ON "CCAT_DEV3"."ADM_PROFILES" ("IS_DELETED")
--------------------------------------------------------
--  DDL for Index PK_ADM_PROFILES
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."PK_ADM_PROFILES" ON "CCAT_DEV3"."ADM_PROFILES" ("PROFILE_ID")
--------------------------------------------------------
--  DDL for Index ADM_PROFILES_UK1
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."ADM_PROFILES_UK1" ON "CCAT_DEV3"."ADM_PROFILES" ("PROFILE_NAME")
--------------------------------------------------------
--  DDL for Index ADM_DIS_CODE_DEL_INDX
--------------------------------------------------------

  CREATE INDEX "CCAT_DEV3"."ADM_DIS_CODE_DEL_INDX" ON "CCAT_DEV3"."LK_DISCONNECTION_CODES" ("IS_DELETED")
--------------------------------------------------------
--  DDL for Index PK_LK_DISCONNECTION_CODES
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."PK_LK_DISCONNECTION_CODES" ON "CCAT_DEV3"."LK_DISCONNECTION_CODES" ("ID")
--------------------------------------------------------
--  DDL for Index PK_ADM_SERVICE_CLASS_ACC
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."PK_ADM_SERVICE_CLASS_ACC" ON "CCAT_DEV3"."ADM_SERVICE_CLASS_ACC" ("SERVICE_CLASS_ID", "ACC_ID")
--------------------------------------------------------
--  DDL for Index DYN_PAGES_STEPS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."DYN_PAGES_STEPS_PK" ON "CCAT_DEV3"."DYN_PAGES_STEPS" ("STEP_ID")
--------------------------------------------------------
--  DDL for Index DYN_STEP_SP_PARAMETERS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."DYN_STEP_SP_PARAMETERS_PK" ON "CCAT_DEV3"."DYN_STEP_SP_PARAMETERS" ("PARAM_ID")
--------------------------------------------------------
--  DDL for Index SMSQ_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."SMSQ_PK" ON "CCAT_DEV3"."SMS_Q" ("SMS_ID")
--------------------------------------------------------
--  DDL for Index LK_PAM_SCHEDULES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."LK_PAM_SCHEDULES_PK" ON "CCAT_DEV3"."LK_PAM_SCHEDULES" ("ID", "NAME")
--------------------------------------------------------
--  DDL for Index PK_LK_REFILL_PAYMENT_PROFILE
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."PK_LK_REFILL_PAYMENT_PROFILE" ON "CCAT_DEV3"."LK_REFILL_PAYMENT_PROFILE" ("REFILL_PROFILE_ID")
--------------------------------------------------------
--  DDL for Index H_NOTEPAD_ENTRIES_MSISDN_NDC
--------------------------------------------------------

  CREATE INDEX "CCAT_DEV3"."H_NOTEPAD_ENTRIES_MSISDN_NDC" ON "CCAT_DEV3"."H_NOTEPAD_ENTRIES" ("MSISDN")
--------------------------------------------------------
--  DDL for Index PK_TX_LOGIN
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."PK_TX_LOGIN" ON "CCAT_DEV3"."TX_LOGIN" ("TX_LOGIN_ID")
--------------------------------------------------------
--  DDL for Index TX_LOGIN_UID_INDX
--------------------------------------------------------

  CREATE INDEX "CCAT_DEV3"."TX_LOGIN_UID_INDX" ON "CCAT_DEV3"."TX_LOGIN" ("USER_ID")
--------------------------------------------------------
--  DDL for Index LK_OFFERS_STATES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."LK_OFFERS_STATES_PK" ON "CCAT_DEV3"."LK_OFFERS_STATES" ("STATE_ID", "STATE_DESC")
--------------------------------------------------------
--  DDL for Index PK_LK_ACCUMULATORS
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."PK_LK_ACCUMULATORS" ON "CCAT_DEV3"."LK_ACCUMULATORS" ("ACC_ID")
--------------------------------------------------------
--  DDL for Index LK_CC_FEATURES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."LK_CC_FEATURES_PK" ON "CCAT_DEV3"."LK_CC_FEATURES" ("CC_FEATURE_ID")
--------------------------------------------------------
--  DDL for Index TX_CALL_REASONS_USER_ID_IDX
--------------------------------------------------------

  CREATE INDEX "CCAT_DEV3"."TX_CALL_REASONS_USER_ID_IDX" ON "CCAT_DEV3"."TX_CALL_REASONS" ("USER_ID")  LOCAL (PARTITION "P0" NOCOMPRESS  ( SUBPARTITION "S0" ,  SUBPARTITION "S1" ,  SUBPARTITION "S2" ,  SUBPARTITION "S3" ,  SUBPARTITION "S4" ,  SUBPARTITION "S5" ,  SUBPARTITION "S6" ,  SUBPARTITION "S7" ,  SUBPARTITION "S8" ,  SUBPARTITION "S9" ) )
--------------------------------------------------------
--  DDL for Index ADM_BUSINESS_PAN_DEL_INDX
--------------------------------------------------------

  CREATE INDEX "CCAT_DEV3"."ADM_BUSINESS_PAN_DEL_INDX" ON "CCAT_DEV3"."ADM_BUSINESS_PLANS" ("IS_DELETED")
--------------------------------------------------------
--  DDL for Index ADM_BUSINESS_PLAN_ACTIV_INDX
--------------------------------------------------------

  CREATE INDEX "CCAT_DEV3"."ADM_BUSINESS_PLAN_ACTIV_INDX" ON "CCAT_DEV3"."ADM_BUSINESS_PLANS" ("ACTIVATION_PLAN")
--------------------------------------------------------
--  DDL for Index ADM_BUSINESS_PLAN_HLR_INDX
--------------------------------------------------------

  CREATE INDEX "CCAT_DEV3"."ADM_BUSINESS_PLAN_HLR_INDX" ON "CCAT_DEV3"."ADM_BUSINESS_PLANS" ("HLR_ID")
--------------------------------------------------------
--  DDL for Index ADM_BUSINESS_PLAN_SCID_INDX
--------------------------------------------------------

  CREATE INDEX "CCAT_DEV3"."ADM_BUSINESS_PLAN_SCID_INDX" ON "CCAT_DEV3"."ADM_BUSINESS_PLANS" ("SERVICE_CLASS_ID")
--------------------------------------------------------
--  DDL for Index PK_ADM_BUSINESS_PLANS
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."PK_ADM_BUSINESS_PLANS" ON "CCAT_DEV3"."ADM_BUSINESS_PLANS" ("ID")
--------------------------------------------------------
--  DDL for Index ADM_BUSINESS_PLANS_UK1
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."ADM_BUSINESS_PLANS_UK1" ON "CCAT_DEV3"."ADM_BUSINESS_PLANS" ("NAME", "CODE")
--------------------------------------------------------
--  DDL for Index BIT_SC_PK_1
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."BIT_SC_PK_1" ON "CCAT_DEV3"."ADM_AG_BITS_SC_DESCRIPTION" ("BIT_POSITION", "SERVICE_CLASS_ID")
--------------------------------------------------------
--  DDL for Index LK_PAM_SERVICES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."LK_PAM_SERVICES_PK" ON "CCAT_DEV3"."LK_PAM_SERVICES" ("ID", "NAME")
--------------------------------------------------------
--  DDL for Index PK_ADM_TX_TYPES
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."PK_ADM_TX_TYPES" ON "CCAT_DEV3"."ADM_TX_TYPES" ("ID")
--------------------------------------------------------
--  DDL for Index ADM_TX_TYPES_DEF_INDX
--------------------------------------------------------

  CREATE INDEX "CCAT_DEV3"."ADM_TX_TYPES_DEF_INDX" ON "CCAT_DEV3"."ADM_TX_TYPES" ("IS_DEFAULT")
--------------------------------------------------------
--  DDL for Index ADM_TX_TYPES_DEL_INDX
--------------------------------------------------------

  CREATE INDEX "CCAT_DEV3"."ADM_TX_TYPES_DEL_INDX" ON "CCAT_DEV3"."ADM_TX_TYPES" ("IS_DELETED")
--------------------------------------------------------
--  DDL for Index PK_LK_ACCOUNT_FLAGS
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."PK_LK_ACCOUNT_FLAGS" ON "CCAT_DEV3"."LK_ACCOUNT_FLAGS" ("ACCOUNT_FLAG_CODE")
--------------------------------------------------------
--  DDL for Index SMSGATEWAY_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."SMSGATEWAY_PK" ON "CCAT_DEV3"."ADM_SMS_GATEWAYS" ("ID")
--------------------------------------------------------
--  DDL for Index PK_LK_BT_TRANSACTION_STATUS
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."PK_LK_BT_TRANSACTION_STATUS" ON "CCAT_DEV3"."LK_BT_TRANSACTION_STATUS" ("CODE")
--------------------------------------------------------
--  DDL for Index LK_FLEX_OFFERS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."LK_FLEX_OFFERS_PK" ON "CCAT_DEV3"."LK_FLEX_OFFERS" ("ID")
--------------------------------------------------------
--  DDL for Index GATEWAYS_STATUS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."GATEWAYS_STATUS_PK" ON "CCAT_DEV3"."LK_SMS_GATEWAYS_STATUS" ("STATUS_ID")
--------------------------------------------------------
--  DDL for Index PK_ADM_HLR_PROFILES
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."PK_ADM_HLR_PROFILES" ON "CCAT_DEV3"."ADM_HLR_PROFILES" ("HLR_ID")
--------------------------------------------------------
--  DDL for Index DYN_PAGES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."DYN_PAGES_PK" ON "CCAT_DEV3"."DYN_PAGES" ("PAGE_ID")
--------------------------------------------------------
--  DDL for Index ADM_USERS_THEME_INDX
--------------------------------------------------------

  CREATE INDEX "CCAT_DEV3"."ADM_USERS_THEME_INDX" ON "CCAT_DEV3"."ADM_USERS" ("THEME_ID")
--------------------------------------------------------
--  DDL for Index ADM_USERS_UK1
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."ADM_USERS_UK1" ON "CCAT_DEV3"."ADM_USERS" ("NT_ACCOUNT")
--------------------------------------------------------
--  DDL for Index ADM_USERS_ACTIVE_INDX
--------------------------------------------------------

  CREATE INDEX "CCAT_DEV3"."ADM_USERS_ACTIVE_INDX" ON "CCAT_DEV3"."ADM_USERS" ("IS_ACTIVE")
--------------------------------------------------------
--  DDL for Index ADM_USERS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."ADM_USERS_PK" ON "CCAT_DEV3"."ADM_USERS" ("USER_ID")
--------------------------------------------------------
--  DDL for Index ADM_USERS_PROFILE_INDX
--------------------------------------------------------

  CREATE INDEX "CCAT_DEV3"."ADM_USERS_PROFILE_INDX" ON "CCAT_DEV3"."ADM_USERS" ("PROFILE_ID")
--------------------------------------------------------
--  DDL for Index SMSERROR_PK1
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."SMSERROR_PK1" ON "CCAT_DEV3"."SMS_ERROR" ("SMS_ERROR_ID")
--------------------------------------------------------
--  DDL for Index ADM_SERVICE_CLASSES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."ADM_SERVICE_CLASSES_PK" ON "CCAT_DEV3"."ADM_SERVICE_CLASSES" ("CODE")
--------------------------------------------------------
--  DDL for Index LK_PAM_PRIORITY_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."LK_PAM_PRIORITY_PK" ON "CCAT_DEV3"."LK_PAM_PRIORITY" ("ID", "NAME")
--------------------------------------------------------
--  DDL for Index SMS_ACTION_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."SMS_ACTION_PK" ON "CCAT_DEV3"."LK_SMS_ACTION" ("SMS_ACTION_ID")
--------------------------------------------------------
--  DDL for Index LK_MENUS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."LK_MENUS_PK" ON "CCAT_DEV3"."LK_MENUS" ("MENU_ID")
--------------------------------------------------------
--  DDL for Index PK_ADM_TX_LINKS
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."PK_ADM_TX_LINKS" ON "CCAT_DEV3"."ADM_TX_LINKS" ("TX_TYPE_ID", "TX_CODE_ID")
--------------------------------------------------------
--  DDL for Index TX_USED_VOUCHERS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."TX_USED_VOUCHERS_PK" ON "CCAT_DEV3"."TX_USED_VOUCHERS" ("VOUCHER_SERIAL_NUMBER")
--------------------------------------------------------
--  DDL for Index SMSPRIOITIES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."SMSPRIOITIES_PK" ON "CCAT_DEV3"."LK_SMS_PRIORITIES" ("PRIORITY_ID")
--------------------------------------------------------
--  DDL for Index LK_REGIONS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."LK_REGIONS_PK" ON "CCAT_DEV3"."LK_REGIONS" ("REGION")
--------------------------------------------------------
--  DDL for Index LK_REGIONS_U01
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."LK_REGIONS_U01" ON "CCAT_DEV3"."LK_REGIONS" ("REGION_DESCRIPTION")
--------------------------------------------------------
--  DDL for Index PK_LK_DED_ACCOUNTS
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."PK_LK_DED_ACCOUNTS" ON "CCAT_DEV3"."LK_DED_ACCOUNTS" ("DA_ID")
--------------------------------------------------------
--  DDL for Index LK_PAM_PERIOD_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."LK_PAM_PERIOD_PK" ON "CCAT_DEV3"."LK_PAM_PERIOD" ("ID", "NAME")
--------------------------------------------------------
--  DDL for Index SYS_C0032570
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."SYS_C0032570" ON "CCAT_DEV3"."TEST_VOUCHERS" ("ID")
--------------------------------------------------------
--  DDL for Index SYS_C0032571
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."SYS_C0032571" ON "CCAT_DEV3"."TEST_VOUCHERS" ("SERIAL_NUMBER")
--------------------------------------------------------
--  DDL for Index SYS_C0032572
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."SYS_C0032572" ON "CCAT_DEV3"."TEST_VOUCHERS" ("REQUEST_ID")
--------------------------------------------------------
--  DDL for Index ADM_DATASOURCES_NODES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."ADM_DATASOURCES_NODES_PK" ON "CCAT_DEV3"."FLEX_SHARE_HISTORY_NODES" ("ID")
--------------------------------------------------------
--  DDL for Index PK_ADM_SERVICE_CLASS_DA
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."PK_ADM_SERVICE_CLASS_DA" ON "CCAT_DEV3"."ADM_SERVICE_CLASS_DA" ("DA_ID", "SERVICE_CLASS_ID")
--------------------------------------------------------
--  DDL for Index ENTITY11PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."ENTITY11PK" ON "CCAT_DEV3"."ADM_PROFILE_FEATURES" ("PROFILE_ID", "FEATURE_ID")
--------------------------------------------------------
--  DDL for Index LK_OFFERS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."LK_OFFERS_PK" ON "CCAT_DEV3"."ADM_OFFERS" ("OFFER_ID", "OFFER_DESC")
--------------------------------------------------------
--  DDL for Index SYS_C0033125
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."SYS_C0033125" ON "CCAT_DEV3"."TESTT_VOUCHERS" ("ID")
--------------------------------------------------------
--  DDL for Index SYS_C0033126
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."SYS_C0033126" ON "CCAT_DEV3"."TESTT_VOUCHERS" ("REQUEST_ID")
--------------------------------------------------------
--  DDL for Index SYS_C0033127
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."SYS_C0033127" ON "CCAT_DEV3"."TESTT_VOUCHERS" ("AMOUNT")
--------------------------------------------------------
--  DDL for Index LK_OFFERS_TYPES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."LK_OFFERS_TYPES_PK" ON "CCAT_DEV3"."LK_OFFERS_TYPES" ("TYPE_ID", "TYPE_DESC")
--------------------------------------------------------
--  DDL for Index DSS_COLUMNS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."DSS_COLUMNS_PK" ON "CCAT_DEV3"."DSS_COLUMNS" ("DSS_PAGENAME", "COLUMN_NAME")
--------------------------------------------------------
--  DDL for Index LK_DATASOURCE_TYPE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."LK_DATASOURCE_TYPE_PK" ON "CCAT_DEV3"."LK_DATASOURCE_TYPE" ("ID")
--------------------------------------------------------
--  DDL for Index PK_ADM_MARQUEE_DATA
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCAT_DEV3"."PK_ADM_MARQUEE_DATA" ON "CCAT_DEV3"."ADM_MARQUEE_DATA" ("ID")
--------------------------------------------------------
--  Constraints for Table ADM_SMS_TEMPLATE
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ADM_SMS_TEMPLATE" ADD CONSTRAINT "SMS_TEMPLT_PK" PRIMARY KEY ("ID") USING INDEX "CCAT_DEV3"."SMS_TEMPLT_PK"  ENABLE
  ALTER TABLE "CCAT_DEV3"."ADM_SMS_TEMPLATE" MODIFY ("ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_SMS_TEMPLATE" MODIFY ("TEMPLATE_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_SMS_TEMPLATE" MODIFY ("TEMPLATE_TEXT" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_SMS_TEMPLATE" MODIFY ("LANGUAGE_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_SMS_TEMPLATE" MODIFY ("TEMPLATE_STATUS" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table DYN_STEP_SP_CONFIGURATION
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."DYN_STEP_SP_CONFIGURATION" MODIFY ("CONFIG_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_STEP_SP_CONFIGURATION" MODIFY ("STEP_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_STEP_SP_CONFIGURATION" MODIFY ("DB_URL" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_STEP_SP_CONFIGURATION" MODIFY ("DB_USERNAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_STEP_SP_CONFIGURATION" MODIFY ("DB_PASSWORD" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_STEP_SP_CONFIGURATION" MODIFY ("PROCEDURE_NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_STEP_SP_CONFIGURATION" MODIFY ("MAX_RECORDS" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_STEP_SP_CONFIGURATION" MODIFY ("SUCCESS_CODE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_STEP_SP_CONFIGURATION" ADD CONSTRAINT "DYN_STEP_SP_CONFIGURATION_PK" PRIMARY KEY ("CONFIG_ID") USING INDEX  ENABLE
  ALTER TABLE "CCAT_DEV3"."DYN_STEP_SP_CONFIGURATION" MODIFY ("IS_DELETED" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table LK_LANGUAGES
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_LANGUAGES" MODIFY ("ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_LANGUAGES" MODIFY ("NAME" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table TX_SUBSCRIBER_ADJUSTMENTS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."TX_SUBSCRIBER_ADJUSTMENTS" MODIFY ("ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."TX_SUBSCRIBER_ADJUSTMENTS" MODIFY ("MSISDN" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."TX_SUBSCRIBER_ADJUSTMENTS" MODIFY ("MSISDN_MOD_X" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."TX_SUBSCRIBER_ADJUSTMENTS" MODIFY ("ADJ_DATE" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table ODS_NODES
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ODS_NODES" MODIFY ("ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ODS_NODES" MODIFY ("ADDRESS" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ODS_NODES" MODIFY ("USER_NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ODS_NODES" MODIFY ("PASSWORD" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ODS_NODES" MODIFY ("NUMBER_OF_SESSIONS" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ODS_NODES" MODIFY ("CONCURRENT_CALLS" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ODS_NODES" MODIFY ("CONNECTION_TIMEOUT" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ODS_NODES" MODIFY ("EXTRA_CONF" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table ADM_SERVICE_OFFERING_PLANS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ADM_SERVICE_OFFERING_PLANS" ADD CONSTRAINT "ADM_SERVICE_OFFERING_PLANS_UK1" UNIQUE ("NAME", "PLAN_ID") USING INDEX  ENABLE
  ALTER TABLE "CCAT_DEV3"."ADM_SERVICE_OFFERING_PLANS" MODIFY ("PLAN_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_SERVICE_OFFERING_PLANS" MODIFY ("IS_DELETED" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table ADM_PROMOTION_PLANS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ADM_PROMOTION_PLANS" MODIFY ("ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_PROMOTION_PLANS" MODIFY ("PLAN_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_PROMOTION_PLANS" MODIFY ("NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_PROMOTION_PLANS" MODIFY ("IS_DEFAULT" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_PROMOTION_PLANS" MODIFY ("IS_DELETED" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table LK_SMS_TEMPLATE_PARAM
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_SMS_TEMPLATE_PARAM" ADD CONSTRAINT "SMS_PARAM_PK" PRIMARY KEY ("PARAMETER_ID", "SMS_ACTION_ID") USING INDEX "CCAT_DEV3"."SMS_PARAM_PK"  ENABLE
  ALTER TABLE "CCAT_DEV3"."LK_SMS_TEMPLATE_PARAM" MODIFY ("PARAMETER_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_SMS_TEMPLATE_PARAM" MODIFY ("SMS_ACTION_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_SMS_TEMPLATE_PARAM" MODIFY ("PARAMETER_NAME" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table LK_FLEX_OFFER_TYPE
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_FLEX_OFFER_TYPE" ADD CONSTRAINT "LK_FLEX_OFFER_TYPE_PK" PRIMARY KEY ("TYPE_ID") USING INDEX "CCAT_DEV3"."LK_FLEX_OFFER_TYPE_PK"  ENABLE
--------------------------------------------------------
--  Constraints for Table ADM_TX_CODES
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ADM_TX_CODES" MODIFY ("ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_TX_CODES" MODIFY ("NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_TX_CODES" MODIFY ("VALUE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_TX_CODES" MODIFY ("IS_DEFAULT" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_TX_CODES" MODIFY ("IS_DELETED" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table LK_VOUCHER_SERVERS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_VOUCHER_SERVERS" ADD CONSTRAINT "LK_VOUCHER_SERVERS_PK" PRIMARY KEY ("SERVER_INDEX", "VOUCHER_SERIAL_LENGTH") USING INDEX "CCAT_DEV3"."LK_VOUCHER_SERVERS_PK"  ENABLE
  ALTER TABLE "CCAT_DEV3"."LK_VOUCHER_SERVERS" MODIFY ("URL" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_VOUCHER_SERVERS" MODIFY ("AGENT_NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_VOUCHER_SERVERS" MODIFY ("HOST" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_VOUCHER_SERVERS" MODIFY ("AUTHORIZATION" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_VOUCHER_SERVERS" MODIFY ("SERVER_INDEX" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table DYN_STEP_SP_CURSOR_MAPPING
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."DYN_STEP_SP_CURSOR_MAPPING" MODIFY ("MAPPING_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_STEP_SP_CURSOR_MAPPING" MODIFY ("PARAMETER_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_STEP_SP_CURSOR_MAPPING" ADD CONSTRAINT "DYN_STEP_SP_CURSOR_MAPPING_PK" PRIMARY KEY ("MAPPING_ID") USING INDEX  ENABLE
  ALTER TABLE "CCAT_DEV3"."DYN_STEP_SP_CURSOR_MAPPING" MODIFY ("IS_DELETED" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table LK_BOOLEAN
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_BOOLEAN" MODIFY ("BOOLEAN_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_BOOLEAN" MODIFY ("BOOLEAN_VALUE" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table LK_UNIT_TYPE_DESC
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_UNIT_TYPE_DESC" MODIFY ("ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_UNIT_TYPE_DESC" MODIFY ("NAME" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table LK_FEATURES
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_FEATURES" MODIFY ("ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_FEATURES" MODIFY ("NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_FEATURES" ADD CONSTRAINT "LK_FEATURES_NEW_PK" PRIMARY KEY ("ID") USING INDEX "CCAT_DEV3"."LK_FEATURES_NEW_PK"  ENABLE
--------------------------------------------------------
--  Constraints for Table ADM_FAF_INDICATORS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ADM_FAF_INDICATORS" MODIFY ("ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_FAF_INDICATORS" MODIFY ("IS_DELETED" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table ADM_FAF_BLACK_RESTRICTIONS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ADM_FAF_BLACK_RESTRICTIONS" MODIFY ("RESTRICTION_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_FAF_BLACK_RESTRICTIONS" MODIFY ("NUMBER_PATTERN" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_FAF_BLACK_RESTRICTIONS" MODIFY ("DESCRIPTION" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_FAF_BLACK_RESTRICTIONS" MODIFY ("IS_DELETED" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table ADM_PROFILES
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ADM_PROFILES" ADD CONSTRAINT "ADM_PROFILES_UK1" UNIQUE ("PROFILE_NAME") USING INDEX  ENABLE
  ALTER TABLE "CCAT_DEV3"."ADM_PROFILES" MODIFY ("PROFILE_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_PROFILES" MODIFY ("PROFILE_NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_PROFILES" MODIFY ("IS_DELETED" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_PROFILES" MODIFY ("IS_FOOTPRINT_REQUIRED" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_PROFILES" ADD CONSTRAINT "ADM_PROFILES_PK" PRIMARY KEY ("PROFILE_ID") USING INDEX "CCAT_DEV3"."PK_ADM_PROFILES"  ENABLE
--------------------------------------------------------
--  Constraints for Table LK_DISCONNECTION_CODES
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_DISCONNECTION_CODES" MODIFY ("ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_DISCONNECTION_CODES" MODIFY ("CODE_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_DISCONNECTION_CODES" MODIFY ("NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_DISCONNECTION_CODES" MODIFY ("IS_DELETED" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_DISCONNECTION_CODES" MODIFY ("CREATION_DATE" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table ADM_SERVICE_CLASS_ACC
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ADM_SERVICE_CLASS_ACC" MODIFY ("SERVICE_CLASS_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_SERVICE_CLASS_ACC" MODIFY ("ACC_ID" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table DYN_PAGES_STEPS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."DYN_PAGES_STEPS" MODIFY ("STEP_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_PAGES_STEPS" MODIFY ("PAGE_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_PAGES_STEPS" MODIFY ("STEP_TYPE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_PAGES_STEPS" MODIFY ("STEP_NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_PAGES_STEPS" MODIFY ("STEP_ORDER" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_PAGES_STEPS" ADD CONSTRAINT "DYN_PAGES_STEPS_PK" PRIMARY KEY ("STEP_ID") USING INDEX  ENABLE
  ALTER TABLE "CCAT_DEV3"."DYN_PAGES_STEPS" MODIFY ("IS_DELETED" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_PAGES_STEPS" MODIFY ("IS_HIDDEN" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table BK_SYSTEM_PROPERTIES
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."BK_SYSTEM_PROPERTIES" MODIFY ("APPLICATION" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."BK_SYSTEM_PROPERTIES" MODIFY ("PROFILE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."BK_SYSTEM_PROPERTIES" MODIFY ("LABEL" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."BK_SYSTEM_PROPERTIES" MODIFY ("VALUE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."BK_SYSTEM_PROPERTIES" MODIFY ("CODE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."BK_SYSTEM_PROPERTIES" MODIFY ("DESCRIPTION" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table DYN_STEP_SP_PARAMETERS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."DYN_STEP_SP_PARAMETERS" MODIFY ("DISPLAY_ORDER" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_STEP_SP_PARAMETERS" MODIFY ("PARAM_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_STEP_SP_PARAMETERS" MODIFY ("CONFIG_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_STEP_SP_PARAMETERS" MODIFY ("PARAMETER_NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_STEP_SP_PARAMETERS" MODIFY ("PARAMETER_DATA_TYPE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_STEP_SP_PARAMETERS" MODIFY ("PARAMETER_TYPE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_STEP_SP_PARAMETERS" MODIFY ("PARAMETER_ORDER" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_STEP_SP_PARAMETERS" ADD CONSTRAINT "DYN_STEP_SP_PARAMETERS_PK" PRIMARY KEY ("PARAM_ID") USING INDEX  ENABLE
  ALTER TABLE "CCAT_DEV3"."DYN_STEP_SP_PARAMETERS" MODIFY ("IS_DELETED" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table ADM_FAF_PLANS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ADM_FAF_PLANS" MODIFY ("PLAN_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_FAF_PLANS" MODIFY ("INDICATOR_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_FAF_PLANS" MODIFY ("IS_DELETED" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table LK_AIR_SERVERS_BK
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_AIR_SERVERS_BK" MODIFY ("URL" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_AIR_SERVERS_BK" MODIFY ("AGENT_NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_AIR_SERVERS_BK" MODIFY ("HOST" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_AIR_SERVERS_BK" MODIFY ("AUTHORIZATION" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_AIR_SERVERS_BK" MODIFY ("IS_DOWN" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table SMS_Q
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."SMS_Q" ADD CONSTRAINT "SMSQ_PK" PRIMARY KEY ("SMS_ID") USING INDEX "CCAT_DEV3"."SMSQ_PK"  ENABLE
  ALTER TABLE "CCAT_DEV3"."SMS_Q" MODIFY ("SMS_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."SMS_Q" MODIFY ("ENTRY_DATE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."SMS_Q" MODIFY ("MSISDN" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."SMS_Q" MODIFY ("SMS_TEXT" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."SMS_Q" MODIFY ("TEMPLATE_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."SMS_Q" MODIFY ("LANG_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."SMS_Q" MODIFY ("PRIORITY_ID" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table LK_PAM_SCHEDULES
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_PAM_SCHEDULES" MODIFY ("ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_PAM_SCHEDULES" MODIFY ("NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_PAM_SCHEDULES" ADD CONSTRAINT "LK_PAM_SCHEDULES_PK" PRIMARY KEY ("ID", "NAME") USING INDEX "CCAT_DEV3"."LK_PAM_SCHEDULES_PK"  ENABLE
--------------------------------------------------------
--  Constraints for Table ADM_PROFILE_SERVICE_CLASS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ADM_PROFILE_SERVICE_CLASS" MODIFY ("SERVICE_CLASS_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_PROFILE_SERVICE_CLASS" MODIFY ("PROFILE_ID" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table TX_USER_FOOTPRINT
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."TX_USER_FOOTPRINT" MODIFY ("ID" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table LK_REFILL_PAYMENT_PROFILE
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_REFILL_PAYMENT_PROFILE" MODIFY ("REFILL_PROFILE_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_REFILL_PAYMENT_PROFILE" MODIFY ("REFILL_PROFILE_NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_REFILL_PAYMENT_PROFILE" MODIFY ("AMOUNT_FROM" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_REFILL_PAYMENT_PROFILE" MODIFY ("AMOUNT_TO" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table H_NOTEPAD_ENTRIES
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."H_NOTEPAD_ENTRIES" MODIFY ("ENTRY_DATE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."H_NOTEPAD_ENTRIES" MODIFY ("MSISDN" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."H_NOTEPAD_ENTRIES" MODIFY ("MSISDN_MOD_X" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."H_NOTEPAD_ENTRIES" MODIFY ("USER_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."H_NOTEPAD_ENTRIES" MODIFY ("NOTEPAD_ENTRY" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."H_NOTEPAD_ENTRIES" MODIFY ("USER_NAME" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table TX_LOGIN
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."TX_LOGIN" MODIFY ("TX_LOGIN_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."TX_LOGIN" MODIFY ("LOGIN_TIME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."TX_LOGIN" MODIFY ("STATUS" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."TX_LOGIN" MODIFY ("MESSAGE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."TX_LOGIN" MODIFY ("USER_ID" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table LK_OFFERS_STATES
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_OFFERS_STATES" MODIFY ("STATE_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_OFFERS_STATES" MODIFY ("STATE_DESC" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table LK_ACCUMULATORS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_ACCUMULATORS" MODIFY ("ACC_ID" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table LK_MAREDCARD_LIST
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_MAREDCARD_LIST" MODIFY ("NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_MAREDCARD_LIST" MODIFY ("ID" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table LK_CC_FEATURES
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_CC_FEATURES" MODIFY ("CC_FEATURE_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_CC_FEATURES" MODIFY ("CC_FEATURE_NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_CC_FEATURES" ADD CONSTRAINT "LK_CC_FEATURES_PK" PRIMARY KEY ("CC_FEATURE_ID") USING INDEX  ENABLE
--------------------------------------------------------
--  Constraints for Table TX_CALL_REASONS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."TX_CALL_REASONS" MODIFY ("USER_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."TX_CALL_REASONS" MODIFY ("USER_NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."TX_CALL_REASONS" MODIFY ("MSISDN" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."TX_CALL_REASONS" MODIFY ("MSISDN_LAST_DIGIT" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."TX_CALL_REASONS" MODIFY ("ENTRY_DATE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."TX_CALL_REASONS" MODIFY ("CALL_REASON_ID" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table ADM_BUSINESS_PLANS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ADM_BUSINESS_PLANS" MODIFY ("ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_BUSINESS_PLANS" MODIFY ("NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_BUSINESS_PLANS" MODIFY ("CODE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_BUSINESS_PLANS" MODIFY ("SERVICE_CLASS_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_BUSINESS_PLANS" MODIFY ("HLR_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_BUSINESS_PLANS" MODIFY ("IS_DELETED" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_BUSINESS_PLANS" ADD CONSTRAINT "ADM_BUSINESS_PLANS_PK" PRIMARY KEY ("ID") USING INDEX "CCAT_DEV3"."PK_ADM_BUSINESS_PLANS"  ENABLE
  ALTER TABLE "CCAT_DEV3"."ADM_BUSINESS_PLANS" ADD CONSTRAINT "ADM_BUSINESS_PLANS_UK1" UNIQUE ("NAME", "CODE") USING INDEX  ENABLE
--------------------------------------------------------
--  Constraints for Table ODS_H_ACC_HEADERS_MAPPING_BK
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ODS_H_ACC_HEADERS_MAPPING_BK" MODIFY ("ACTIVITY_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ODS_H_ACC_HEADERS_MAPPING_BK" MODIFY ("HEADER_ID" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table ADM_AG_BITS_SC_DESCRIPTION
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ADM_AG_BITS_SC_DESCRIPTION" MODIFY ("BIT_POSITION" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_AG_BITS_SC_DESCRIPTION" MODIFY ("SERVICE_CLASS_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_AG_BITS_SC_DESCRIPTION" MODIFY ("DESCRIPTION" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table LK_PAM_SERVICES
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_PAM_SERVICES" MODIFY ("ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_PAM_SERVICES" MODIFY ("NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_PAM_SERVICES" ADD CONSTRAINT "LK_PAM_SERVICES_PK" PRIMARY KEY ("ID", "NAME") USING INDEX "CCAT_DEV3"."LK_PAM_SERVICES_PK"  ENABLE
--------------------------------------------------------
--  Constraints for Table ADM_TX_TYPES
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ADM_TX_TYPES" MODIFY ("ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_TX_TYPES" MODIFY ("NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_TX_TYPES" MODIFY ("VALUE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_TX_TYPES" MODIFY ("IS_DEFAULT" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_TX_TYPES" MODIFY ("IS_DELETED" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table LK_ACCOUNT_FLAGS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_ACCOUNT_FLAGS" MODIFY ("ACCOUNT_FLAG_CODE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_ACCOUNT_FLAGS" MODIFY ("ACCOUNT_FLAG_NAME" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table ADM_SMS_GATEWAYS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ADM_SMS_GATEWAYS" MODIFY ("APP_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_SMS_GATEWAYS" MODIFY ("ORIG_MSISDN" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_SMS_GATEWAYS" ADD CONSTRAINT "SMSGATEWAY_PK" PRIMARY KEY ("ID") USING INDEX "CCAT_DEV3"."SMSGATEWAY_PK"  ENABLE
  ALTER TABLE "CCAT_DEV3"."ADM_SMS_GATEWAYS" MODIFY ("ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_SMS_GATEWAYS" MODIFY ("PRC_NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_SMS_GATEWAYS" MODIFY ("PRC_SHARE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_SMS_GATEWAYS" MODIFY ("ENTRY_DATE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_SMS_GATEWAYS" MODIFY ("STATUS_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_SMS_GATEWAYS" MODIFY ("IS_SHARED" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_SMS_GATEWAYS" MODIFY ("MSG_TYPE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_SMS_GATEWAYS" MODIFY ("ORIG_TYPE" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table LK_USAGE_COUNTER_DESC
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_USAGE_COUNTER_DESC" MODIFY ("ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_USAGE_COUNTER_DESC" MODIFY ("NAME" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table ADM_PROFILE_MONETARY_LIMIT
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ADM_PROFILE_MONETARY_LIMIT" MODIFY ("PROFILE_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_PROFILE_MONETARY_LIMIT" MODIFY ("LIMIT_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_PROFILE_MONETARY_LIMIT" MODIFY ("VALUE" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table LK_BT_TRANSACTION_STATUS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_BT_TRANSACTION_STATUS" MODIFY ("CODE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_BT_TRANSACTION_STATUS" MODIFY ("NAME" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table LK_MONETARY_LIMITS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_MONETARY_LIMITS" MODIFY ("LIMIT_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_MONETARY_LIMITS" MODIFY ("LIMIT_NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_MONETARY_LIMITS" MODIFY ("DEFAULT_VALUE" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table LK_FLEX_OFFERS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_FLEX_OFFERS" ADD CONSTRAINT "LK_FLEX_OFFERS_PK" PRIMARY KEY ("ID") USING INDEX "CCAT_DEV3"."LK_FLEX_OFFERS_PK"  ENABLE
  ALTER TABLE "CCAT_DEV3"."LK_FLEX_OFFERS" MODIFY ("OFFER_ID" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table LK_MAIN_PRODUCT_TYPES
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_MAIN_PRODUCT_TYPES" MODIFY ("TYPE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_MAIN_PRODUCT_TYPES" MODIFY ("DISPLAY_NAME" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table LK_SMS_GATEWAYS_STATUS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_SMS_GATEWAYS_STATUS" ADD CONSTRAINT "GATEWAYS_STATUS_PK" PRIMARY KEY ("STATUS_ID") USING INDEX "CCAT_DEV3"."GATEWAYS_STATUS_PK"  ENABLE
  ALTER TABLE "CCAT_DEV3"."LK_SMS_GATEWAYS_STATUS" MODIFY ("STATUS_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_SMS_GATEWAYS_STATUS" MODIFY ("STATUS_NAME" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table ADM_HLR_PROFILES
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ADM_HLR_PROFILES" MODIFY ("HLR_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_HLR_PROFILES" MODIFY ("CODE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_HLR_PROFILES" MODIFY ("NAME" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table DYN_PAGES
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."DYN_PAGES" MODIFY ("PRIVILEGE_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_PAGES" MODIFY ("PRIVILEGE_NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_PAGES" MODIFY ("PAGE_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_PAGES" MODIFY ("PAGE_NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_PAGES" ADD CONSTRAINT "DYN_PAGES_PK" PRIMARY KEY ("PAGE_ID") USING INDEX  ENABLE
  ALTER TABLE "CCAT_DEV3"."DYN_PAGES" MODIFY ("IS_DELETED" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table ADM_SYSTEM_PROPERTIES
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ADM_SYSTEM_PROPERTIES" MODIFY ("APPLICATION" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_SYSTEM_PROPERTIES" MODIFY ("PROFILE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_SYSTEM_PROPERTIES" MODIFY ("LABEL" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_SYSTEM_PROPERTIES" MODIFY ("VALUE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_SYSTEM_PROPERTIES" MODIFY ("CODE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_SYSTEM_PROPERTIES" MODIFY ("DESCRIPTION" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table ADM_USERS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ADM_USERS" MODIFY ("USER_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_USERS" MODIFY ("NT_ACCOUNT" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_USERS" MODIFY ("PROFILE_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_USERS" MODIFY ("IS_DELETED" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_USERS" MODIFY ("IS_ACTIVE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_USERS" MODIFY ("SESSION_COUNTER" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_USERS" MODIFY ("THEME_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_USERS" ADD CONSTRAINT "ADM_USERS_PK" PRIMARY KEY ("USER_ID") USING INDEX "CCAT_DEV3"."ADM_USERS_PK"  ENABLE
  ALTER TABLE "CCAT_DEV3"."ADM_USERS" ADD CONSTRAINT "ADM_USERS_UK1" UNIQUE ("NT_ACCOUNT") USING INDEX "CCAT_DEV3"."ADM_USERS_UK1"  ENABLE
--------------------------------------------------------
--  Constraints for Table ADM_FAF_WHITE_RESTRICTIONS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ADM_FAF_WHITE_RESTRICTIONS" MODIFY ("RESTRICTION_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_FAF_WHITE_RESTRICTIONS" MODIFY ("NUMBER_PATTERN" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_FAF_WHITE_RESTRICTIONS" MODIFY ("DESCRIPTION" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_FAF_WHITE_RESTRICTIONS" MODIFY ("IS_DELETED" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table LK_PAM_CLASSES
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_PAM_CLASSES" MODIFY ("ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_PAM_CLASSES" MODIFY ("NAME" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table SMS_ERROR
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."SMS_ERROR" ADD CONSTRAINT "SMSERROR_PK1" PRIMARY KEY ("SMS_ERROR_ID") USING INDEX "CCAT_DEV3"."SMSERROR_PK1"  ENABLE
  ALTER TABLE "CCAT_DEV3"."SMS_ERROR" MODIFY ("SMS_ERROR_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."SMS_ERROR" MODIFY ("ENTRY_DATE" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table ADM_SERVICE_CLASSES
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ADM_SERVICE_CLASSES" MODIFY ("NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_SERVICE_CLASSES" MODIFY ("IS_DELETED" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_SERVICE_CLASSES" MODIFY ("CODE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_SERVICE_CLASSES" ADD CONSTRAINT "ADM_SERVICE_CLASSES_PK" PRIMARY KEY ("CODE") USING INDEX  ENABLE
--------------------------------------------------------
--  Constraints for Table LK_PAM_PRIORITY
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_PAM_PRIORITY" MODIFY ("ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_PAM_PRIORITY" MODIFY ("NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_PAM_PRIORITY" ADD CONSTRAINT "LK_PAM_PRIORITY_PK" PRIMARY KEY ("ID", "NAME") USING INDEX "CCAT_DEV3"."LK_PAM_PRIORITY_PK"  ENABLE
--------------------------------------------------------
--  Constraints for Table ODS_H_ACC_HEADERS_MAPPING
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ODS_H_ACC_HEADERS_MAPPING" MODIFY ("ACTIVITY_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ODS_H_ACC_HEADERS_MAPPING" MODIFY ("HEADER_ID" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table LK_BALANCE_DISPUTE_DETAILS_CONFIGURATION
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_BALANCE_DISPUTE_DETAILS_CONFIGURATION" MODIFY ("COLUMN_NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_BALANCE_DISPUTE_DETAILS_CONFIGURATION" MODIFY ("DISPLAY_NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_BALANCE_DISPUTE_DETAILS_CONFIGURATION" MODIFY ("COLUMN_ORDER" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_BALANCE_DISPUTE_DETAILS_CONFIGURATION" MODIFY ("PRIVILEGE_ID" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table LK_SMS_ACTION
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_SMS_ACTION" ADD CONSTRAINT "SMS_ACTION_PK" PRIMARY KEY ("SMS_ACTION_ID") USING INDEX "CCAT_DEV3"."SMS_ACTION_PK"  ENABLE
  ALTER TABLE "CCAT_DEV3"."LK_SMS_ACTION" MODIFY ("SMS_ACTION_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_SMS_ACTION" MODIFY ("ACTION_NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_SMS_ACTION" MODIFY ("CODE" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table LK_MENUS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_MENUS" MODIFY ("MENU_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_MENUS" MODIFY ("LABEL" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_MENUS" MODIFY ("PARENT_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_MENUS" MODIFY ("ORDERING" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_MENUS" ADD CONSTRAINT "LK_MENUS_PK" PRIMARY KEY ("MENU_ID") USING INDEX "CCAT_DEV3"."LK_MENUS_PK"  ENABLE
--------------------------------------------------------
--  Constraints for Table ADM_TX_LINKS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ADM_TX_LINKS" MODIFY ("TX_TYPE_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_TX_LINKS" MODIFY ("TX_CODE_ID" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table TX_USED_VOUCHERS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."TX_USED_VOUCHERS" MODIFY ("VOUCHER_SERIAL_NUMBER" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table DYN_STEP_HTTP_PARAMETERS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."DYN_STEP_HTTP_PARAMETERS" MODIFY ("PARAM_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_STEP_HTTP_PARAMETERS" MODIFY ("CONFIG_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_STEP_HTTP_PARAMETERS" MODIFY ("PARAMETER_NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_STEP_HTTP_PARAMETERS" MODIFY ("PARAMETER_DATA_TYPE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_STEP_HTTP_PARAMETERS" MODIFY ("PARAMETER_TYPE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_STEP_HTTP_PARAMETERS" MODIFY ("PARAMETER_ORDER" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_STEP_HTTP_PARAMETERS" MODIFY ("IS_DELETED" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_STEP_HTTP_PARAMETERS" MODIFY ("DISPLAY_ORDER" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table LK_SMS_PRIORITIES
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_SMS_PRIORITIES" ADD CONSTRAINT "SMSPRIOITIES_PK" PRIMARY KEY ("PRIORITY_ID") USING INDEX "CCAT_DEV3"."SMSPRIOITIES_PK"  ENABLE
--------------------------------------------------------
--  Constraints for Table LK_REGIONS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_REGIONS" ADD CONSTRAINT "LK_REGIONS_PK" PRIMARY KEY ("REGION") USING INDEX "CCAT_DEV3"."LK_REGIONS_PK"  ENABLE
--------------------------------------------------------
--  Constraints for Table LK_FEATURES_TYPE
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_FEATURES_TYPE" MODIFY ("ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_FEATURES_TYPE" MODIFY ("NAME" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table ADM_SO_BITS_SC_DESCRIPTION
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ADM_SO_BITS_SC_DESCRIPTION" MODIFY ("BIT_POSITION" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_SO_BITS_SC_DESCRIPTION" MODIFY ("SERVICE_CLASS_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_SO_BITS_SC_DESCRIPTION" MODIFY ("DESCRIPTION" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table TX_CUSTOMERS_BARRINGS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."TX_CUSTOMERS_BARRINGS" MODIFY ("ENTRY_DATE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."TX_CUSTOMERS_BARRINGS" MODIFY ("MSISDN_MOD_X" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."TX_CUSTOMERS_BARRINGS" MODIFY ("BARRING_TYPE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."TX_CUSTOMERS_BARRINGS" MODIFY ("REASON" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."TX_CUSTOMERS_BARRINGS" MODIFY ("REQUESTED_BY" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."TX_CUSTOMERS_BARRINGS" MODIFY ("USER_ID" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table LK_DED_ACCOUNTS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_DED_ACCOUNTS" MODIFY ("DA_ID" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table LK_PAM_PERIOD
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_PAM_PERIOD" MODIFY ("ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_PAM_PERIOD" MODIFY ("NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_PAM_PERIOD" ADD CONSTRAINT "LK_PAM_PERIOD_PK" PRIMARY KEY ("ID", "NAME") USING INDEX "CCAT_DEV3"."LK_PAM_PERIOD_PK"  ENABLE
--------------------------------------------------------
--  Constraints for Table ADM_PROFILE_MENUS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ADM_PROFILE_MENUS" MODIFY ("PROFILE_ID" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table TEST_VOUCHERS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."TEST_VOUCHERS" MODIFY ("ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."TEST_VOUCHERS" MODIFY ("SERIAL_NUMBER" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."TEST_VOUCHERS" MODIFY ("REQUEST_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."TEST_VOUCHERS" MODIFY ("CREATEDAT" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."TEST_VOUCHERS" MODIFY ("AMOUNT" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."TEST_VOUCHERS" MODIFY ("PERCENTAGE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."TEST_VOUCHERS" ADD PRIMARY KEY ("ID") USING INDEX  ENABLE
  ALTER TABLE "CCAT_DEV3"."TEST_VOUCHERS" ADD UNIQUE ("SERIAL_NUMBER") USING INDEX  ENABLE
  ALTER TABLE "CCAT_DEV3"."TEST_VOUCHERS" ADD UNIQUE ("REQUEST_ID") USING INDEX  ENABLE
--------------------------------------------------------
--  Constraints for Table FLEX_SHARE_HISTORY_NODES
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."FLEX_SHARE_HISTORY_NODES" MODIFY ("ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."FLEX_SHARE_HISTORY_NODES" MODIFY ("USERNAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."FLEX_SHARE_HISTORY_NODES" MODIFY ("PASSWORD" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."FLEX_SHARE_HISTORY_NODES" ADD CONSTRAINT "ADM_DATASOURCES_NODES_PK" PRIMARY KEY ("ID") USING INDEX  ENABLE
--------------------------------------------------------
--  Constraints for Table ADM_SERVICE_CLASS_DA
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ADM_SERVICE_CLASS_DA" MODIFY ("SERVICE_CLASS_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_SERVICE_CLASS_DA" MODIFY ("DA_ID" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table ADM_PROFILE_FEATURES
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ADM_PROFILE_FEATURES" MODIFY ("PROFILE_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_PROFILE_FEATURES" MODIFY ("FEATURE_ID" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table ADM_PROFILE_SOB
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ADM_PROFILE_SOB" MODIFY ("PROFILE_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_PROFILE_SOB" MODIFY ("SERVICE_OFFERING_BITS" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table ADM_OFFERS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ADM_OFFERS" MODIFY ("OFFER_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_OFFERS" MODIFY ("OFFER_DESC" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_OFFERS" MODIFY ("IS_DROP_DOWN_ENABLED" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table TX_USER_MONETARY_TOTALS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."TX_USER_MONETARY_TOTALS" MODIFY ("CURR_DATE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."TX_USER_MONETARY_TOTALS" MODIFY ("USER_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."TX_USER_MONETARY_TOTALS" MODIFY ("LIMIT_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."TX_USER_MONETARY_TOTALS" MODIFY ("CURR_VALUE" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table DSS_NODES
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."DSS_NODES" MODIFY ("ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DSS_NODES" MODIFY ("ADDRESS" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DSS_NODES" MODIFY ("USER_NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DSS_NODES" MODIFY ("PASSWORD" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DSS_NODES" MODIFY ("NUMBER_OF_SESSIONS" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DSS_NODES" MODIFY ("CONCURRENT_CALLS" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DSS_NODES" MODIFY ("CONNECTION_TIMEOUT" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DSS_NODES" MODIFY ("EXTRA_CONF" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table TESTT_VOUCHERS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."TESTT_VOUCHERS" MODIFY ("ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."TESTT_VOUCHERS" ADD UNIQUE ("ID") USING INDEX  ENABLE
  ALTER TABLE "CCAT_DEV3"."TESTT_VOUCHERS" ADD UNIQUE ("REQUEST_ID") USING INDEX  ENABLE
  ALTER TABLE "CCAT_DEV3"."TESTT_VOUCHERS" ADD UNIQUE ("AMOUNT") USING INDEX  ENABLE
--------------------------------------------------------
--  Constraints for Table DYN_STEP_HTTP_CONFIGURATION
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."DYN_STEP_HTTP_CONFIGURATION" MODIFY ("CONFIG_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DYN_STEP_HTTP_CONFIGURATION" MODIFY ("STEP_ID" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table LK_OFFERS_TYPES
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_OFFERS_TYPES" MODIFY ("TYPE_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_OFFERS_TYPES" MODIFY ("TYPE_DESC" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table DSS_COLUMNS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."DSS_COLUMNS" MODIFY ("DSS_PAGENAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DSS_COLUMNS" MODIFY ("COLUMN_NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DSS_COLUMNS" MODIFY ("DISPLAYNAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."DSS_COLUMNS" ADD CONSTRAINT "DSS_COLUMNS_PK" PRIMARY KEY ("DSS_PAGENAME", "COLUMN_NAME") USING INDEX "CCAT_DEV3"."DSS_COLUMNS_PK"  ENABLE
--------------------------------------------------------
--  Constraints for Table ADM_ACCOUNT_GROUPS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ADM_ACCOUNT_GROUPS" MODIFY ("GROUP_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_ACCOUNT_GROUPS" MODIFY ("NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_ACCOUNT_GROUPS" MODIFY ("IS_DELETED" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table LK_DATASOURCE_TYPE
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_DATASOURCE_TYPE" MODIFY ("ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_DATASOURCE_TYPE" MODIFY ("NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_DATASOURCE_TYPE" ADD CONSTRAINT "LK_DATASOURCE_TYPE_PK" PRIMARY KEY ("ID") USING INDEX  ENABLE
--------------------------------------------------------
--  Constraints for Table ODS_H_ACC_ACTIVITIES
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ODS_H_ACC_ACTIVITIES" MODIFY ("ACTIVITY_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ODS_H_ACC_ACTIVITIES" MODIFY ("ACTIVITY_NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ODS_H_ACC_ACTIVITIES" MODIFY ("TABLE_TYPE" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table ADM_MARQUEE_DATA
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ADM_MARQUEE_DATA" MODIFY ("ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_MARQUEE_DATA" MODIFY ("CREATION_DATE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_MARQUEE_DATA" MODIFY ("TITLE" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_MARQUEE_DATA" MODIFY ("DESCRIPTION" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table SIM_UCIP_ACCOUNT_DETAILS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."SIM_UCIP_ACCOUNT_DETAILS" MODIFY ("SUBSCRIBER" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table ADM_SERVICE_OFFG_PLAN_BITS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ADM_SERVICE_OFFG_PLAN_BITS" MODIFY ("PLAN_ID" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_SERVICE_OFFG_PLAN_BITS" MODIFY ("BIT_POSITION" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_SERVICE_OFFG_PLAN_BITS" MODIFY ("IS_ENABLED" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table LK_AIR_SERVERS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."LK_AIR_SERVERS" MODIFY ("URL" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_AIR_SERVERS" MODIFY ("AGENT_NAME" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_AIR_SERVERS" MODIFY ("HOST" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_AIR_SERVERS" MODIFY ("AUTHORIZATION" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."LK_AIR_SERVERS" MODIFY ("IS_DOWN" NOT NULL ENABLE)
--------------------------------------------------------
--  Constraints for Table ADM_SERVICE_OFFERING_BITS
--------------------------------------------------------

  ALTER TABLE "CCAT_DEV3"."ADM_SERVICE_OFFERING_BITS" MODIFY ("BIT_POSITION" NOT NULL ENABLE)
  ALTER TABLE "CCAT_DEV3"."ADM_SERVICE_OFFERING_BITS" MODIFY ("BIT_NAME" NOT NULL ENABLE)
