--------------------------------------------------------
--  File created - Wednesday-November-22-2023   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table ADM_ACCOUNT_GROUP_BITS
--------------------------------------------------------

  CREATE TABLE "ADM_ACCOUNT_GROUP_BITS" 
   (	"BIT_POSITION" NUMBER(2,0), 
	"BIT_NAME" VARCHAR2(50 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_ACCOUNT_GROUPS
--------------------------------------------------------

  CREATE TABLE "ADM_ACCOUNT_GROUPS" 
   (	"GROUP_ID" NUMBER(6,0), 
	"NAME" VARCHAR2(100 BYTE), 
	"IS_DELETED" NUMBER(1,0) DEFAULT 0
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_AG_BITS_SC_DESCRIPTION
--------------------------------------------------------

  CREATE TABLE "ADM_AG_BITS_SC_DESCRIPTION" 
   (	"BIT_POSITION" NUMBER(2,0), 
	"SERVICE_CLASS_ID" NUMBER(6,0), 
	"DESCRIPTION" VARCHAR2(200 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_BUSINESS_PLANS
--------------------------------------------------------

  CREATE TABLE "ADM_BUSINESS_PLANS" 
   (	"ID" NUMBER(6,0), 
	"NAME" VARCHAR2(50 BYTE), 
	"CODE" NUMBER(4,0), 
	"SERVICE_CLASS_ID" NUMBER(6,0), 
	"HLR_ID" NUMBER(6,0), 
	"IS_DELETED" NUMBER(1,0) DEFAULT 0, 
	"ACTIVATION_PLAN" NUMBER DEFAULT 0
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_COMMUNITIES
--------------------------------------------------------

  CREATE TABLE "ADM_COMMUNITIES" 
   (	"COMMUNITY_ID" NUMBER(8,0), 
	"COMMUNITY_DESCRIPTION" VARCHAR2(50 BYTE), 
	"IS_DELETED" NUMBER(1,0) DEFAULT 0
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_FAF_BLACK_RESTRICTIONS
--------------------------------------------------------

  CREATE TABLE "ADM_FAF_BLACK_RESTRICTIONS" 
   (	"RESTRICTION_ID" NUMBER(6,0), 
	"NUMBER_PATTERN" VARCHAR2(18 BYTE), 
	"DESCRIPTION" VARCHAR2(50 BYTE), 
	"IS_DELETED" NUMBER(1,0) DEFAULT 0
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_FAF_INDICATORS
--------------------------------------------------------

  CREATE TABLE "ADM_FAF_INDICATORS" 
   (	"ID" NUMBER(6,0), 
	"INDICATOR_ID" NUMBER(6,0), 
	"INDICATOR_NAME" VARCHAR2(50 BYTE), 
	"IS_DELETED" NUMBER(1,0) DEFAULT 0, 
	"MAPPED_INDICATOR_ID" NUMBER(6,0)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_FAF_PLANS
--------------------------------------------------------

  CREATE TABLE "ADM_FAF_PLANS" 
   (	"PLAN_ID" NUMBER(6,0), 
	"NAME" VARCHAR2(50 BYTE), 
	"INDICATOR_ID" NUMBER(6,0), 
	"IS_DELETED" NUMBER(1,0) DEFAULT 0
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_FAF_WHITE_RESTRICTIONS
--------------------------------------------------------

  CREATE TABLE "ADM_FAF_WHITE_RESTRICTIONS" 
   (	"RESTRICTION_ID" NUMBER(6,0), 
	"NUMBER_PATTERN" VARCHAR2(18 BYTE), 
	"DESCRIPTION" VARCHAR2(50 BYTE), 
	"IS_DELETED" NUMBER(1,0) DEFAULT 0
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_HLR_PROFILES
--------------------------------------------------------

  CREATE TABLE "ADM_HLR_PROFILES" 
   (	"HLR_ID" NUMBER(6,0), 
	"CODE" NUMBER(6,0), 
	"NAME" VARCHAR2(100 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_MARQUEE_DATA
--------------------------------------------------------

  CREATE TABLE "ADM_MARQUEE_DATA" 
   (	"ID" NUMBER(6,0) GENERATED ALWAYS AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE , 
	"TITLE" VARCHAR2(500 BYTE), 
	"DESCRIPTION" VARCHAR2(1000 BYTE), 
	"CREATION_DATE" TIMESTAMP (6) DEFAULT SYSTIMESTAMP
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_OFFERS
--------------------------------------------------------

  CREATE TABLE "ADM_OFFERS" 
   (	"OFFER_ID" NUMBER, 
	"OFFER_DESC" VARCHAR2(200 BYTE), 
	"OFFER_TYPE_ID" NUMBER, 
	"IS_DROP_DOWN_ENABLED" NUMBER(1,0)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_PROFILE_FEATURES
--------------------------------------------------------

  CREATE TABLE "ADM_PROFILE_FEATURES" 
   (	"PROFILE_ID" NUMBER(6,0), 
	"FEATURE_ID" NUMBER(6,0), 
	"ORDERING" NUMBER(6,0)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_PROFILE_MENUS
--------------------------------------------------------

  CREATE TABLE "ADM_PROFILE_MENUS" 
   (	"PROFILE_ID" NUMBER(6,0), 
	"MENU_ID" NUMBER(6,0)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_PROFILE_MONETARY_LIMIT
--------------------------------------------------------

  CREATE TABLE "ADM_PROFILE_MONETARY_LIMIT" 
   (	"PROFILE_ID" NUMBER(6,0), 
	"LIMIT_ID" NUMBER(2,0), 
	"VALUE" NUMBER(10,3)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_PROFILE_SERVICE_CLASS
--------------------------------------------------------

  CREATE TABLE "ADM_PROFILE_SERVICE_CLASS" 
   (	"SERVICE_CLASS_ID" NUMBER(6,0), 
	"PROFILE_ID" NUMBER(6,0)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_PROFILE_SOB
--------------------------------------------------------

  CREATE TABLE "ADM_PROFILE_SOB" 
   (	"PROFILE_ID" NUMBER(6,0), 
	"SERVICE_OFFERING_BITS" VARCHAR2(100 BYTE) DEFAULT '-1'
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
   ;
--------------------------------------------------------
--  DDL for Table ADM_PROFILES
--------------------------------------------------------

  CREATE TABLE "ADM_PROFILES" 
   (	"PROFILE_ID" NUMBER(6,0), 
	"PROFILE_NAME" VARCHAR2(100 BYTE), 
	"IS_DELETED" NUMBER(1,0) DEFAULT 0, 
	"IS_FOOTPRINT_REQUIRED" NUMBER(1,0) DEFAULT 1, 
	"SESSION_LIMIT" NUMBER(6,0), 
	"ADJUSTMENTS_LIMITED" NUMBER(1,0), 
	"CREATION_DATE" TIMESTAMP (6)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_PROMOTION_PLANS
--------------------------------------------------------

  CREATE TABLE "ADM_PROMOTION_PLANS" 
   (	"ID" NUMBER(6,0), 
	"PLAN_ID" VARCHAR2(100 BYTE), 
	"NAME" VARCHAR2(50 BYTE), 
	"IS_DEFAULT" NUMBER(1,0) DEFAULT 0, 
	"IS_DELETED" NUMBER(1,0) DEFAULT 0
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_REASON_ACTIVITY
--------------------------------------------------------

  CREATE TABLE "ADM_REASON_ACTIVITY" 
   (	"ACTIVITY_ID" NUMBER, 
	"ACTIVITY_TYPE" VARCHAR2(20 BYTE), 
	"PARENT_ID" NUMBER, 
	"ACTIVITY_NAME" VARCHAR2(50 BYTE), 
	"IS_ACTIVE" NUMBER
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_SERVICE_CLASS_ACC
--------------------------------------------------------

  CREATE TABLE "ADM_SERVICE_CLASS_ACC" 
   (	"SERVICE_CLASS_ID" NUMBER(6,0), 
	"DESCRIPTION" NVARCHAR2(50), 
	"ACC_ID" NUMBER(2,0)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_SERVICE_CLASS_DA
--------------------------------------------------------

  CREATE TABLE "ADM_SERVICE_CLASS_DA" 
   (	"SERVICE_CLASS_ID" NUMBER(6,0), 
	"DESCRIPTION" NVARCHAR2(50), 
	"DA_ID" NUMBER(2,0), 
	"RATING_FACTOR" FLOAT(10)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_SERVICE_CLASSES
--------------------------------------------------------

  CREATE TABLE "ADM_SERVICE_CLASSES" 
   (	"NAME" VARCHAR2(50 BYTE), 
	"CODE" NUMBER(6,0), 
	"PREACTIVATION_ALLOWED" NUMBER(1,0) DEFAULT 0, 
	"IS_DELETED" NUMBER(1,0) DEFAULT 0, 
	"IS_CI_CONVERSION" NUMBER(1,0), 
	"CI_SERVICE_NAME" VARCHAR2(100 BYTE), 
	"CI_PACKAGE_NAME" VARCHAR2(100 BYTE), 
	"IS_ALLOW_MIGRATION" NUMBER(5,0) DEFAULT 1, 
	"TYPE" VARCHAR2(200 CHAR), 
	"HAS_EMPTY_LIMIT" NUMBER(1,0), 
	"DEFAULT_LIMIT" VARCHAR2(100 BYTE), 
	"CUSTOM_LIMIT" VARCHAR2(100 BYTE), 
	"IS_GRANDFATHER" NUMBER(10,0)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_SERVICE_OFFERING_BITS
--------------------------------------------------------

  CREATE TABLE "ADM_SERVICE_OFFERING_BITS" 
   (	"BIT_POSITION" NUMBER(2,0), 
	"BIT_NAME" VARCHAR2(50 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_SERVICE_OFFERING_PLANS
--------------------------------------------------------

  CREATE TABLE "ADM_SERVICE_OFFERING_PLANS" 
   (	"PLAN_ID" NUMBER(6,0), 
	"NAME" VARCHAR2(50 BYTE), 
	"IS_DELETED" NUMBER(1,0) DEFAULT 0
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_SERVICE_OFFG_PLAN_BITS
--------------------------------------------------------

  CREATE TABLE "ADM_SERVICE_OFFG_PLAN_BITS" 
   (	"PLAN_ID" NUMBER(6,0), 
	"BIT_POSITION" NUMBER(2,0), 
	"IS_ENABLED" NUMBER(1,0)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_SMS_GATEWAYS
--------------------------------------------------------

  CREATE TABLE "ADM_SMS_GATEWAYS" 
   (	"ID" NUMBER(10,0), 
	"PRC_NAME" VARCHAR2(200 BYTE), 
	"PRC_SHARE" NUMBER(2,0), 
	"ENTRY_DATE" DATE, 
	"STATUS_ID" NUMBER(1,0), 
	"IS_SHARED" NUMBER(1,0) DEFAULT 1, 
	"APP_ID" VARCHAR2(50 BYTE) DEFAULT 'CCAT', 
	"ORIG_MSISDN" VARCHAR2(50 BYTE) DEFAULT 'Vodafone', 
	"MSG_TYPE" NUMBER(1,0) DEFAULT 0, 
	"ORIG_TYPE" NUMBER(1,0) DEFAULT 2
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_SMS_TEMPLATE
--------------------------------------------------------

  CREATE TABLE "ADM_SMS_TEMPLATE" 
   (	"ID" NUMBER(20,0), 
	"TEMPLATE_ID" NUMBER(20,0), 
	"TEMPLATE_TEXT" VARCHAR2(4000 BYTE), 
	"LANGUAGE_ID" NUMBER(1,0), 
	"TEMPLATE_STATUS" NUMBER(1,0) DEFAULT 1, 
	"CS_TEMPLATE_ID" NUMBER, 
	"TEMPLATE_PARAMETERS" VARCHAR2(4000 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_SO_BITS_SC_DESCRIPTION
--------------------------------------------------------

  CREATE TABLE "ADM_SO_BITS_SC_DESCRIPTION" 
   (	"BIT_POSITION" NUMBER(2,0), 
	"SERVICE_CLASS_ID" NUMBER(6,0), 
	"DESCRIPTION" VARCHAR2(200 BYTE)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
   ;
--------------------------------------------------------
--  DDL for Table ADM_SO_SC_DESCRIPTION
--------------------------------------------------------

  CREATE TABLE "ADM_SO_SC_DESCRIPTION" 
   (	"PLAN_ID" NUMBER(10,0), 
	"SERVICE_CLASS_ID" NUMBER(6,0), 
	"DESCRIPTION" VARCHAR2(200 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_SYSTEM_PROPERTIES
--------------------------------------------------------

  CREATE TABLE "ADM_SYSTEM_PROPERTIES" 
   (	"APPLICATION" VARCHAR2(100 BYTE), 
	"PROFILE" VARCHAR2(100 BYTE), 
	"LABEL" VARCHAR2(100 BYTE), 
	"VALUE" VARCHAR2(1000 BYTE), 
	"CODE" VARCHAR2(50 BYTE), 
	"DESCRIPTION" VARCHAR2(1000 BYTE), 
	"TYPE" NUMBER(1,0)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_TX_CODES
--------------------------------------------------------

  CREATE TABLE "ADM_TX_CODES" 
   (	"ID" NUMBER(5,0), 
	"NAME" VARCHAR2(100 BYTE), 
	"VALUE" VARCHAR2(50 BYTE), 
	"IS_DEFAULT" NUMBER(1,0) DEFAULT 0, 
	"IS_DELETED" NUMBER(1,0) DEFAULT 0, 
	"DESCRIPTION" VARCHAR2(100 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_TX_CODES_RENEWAL_VALUE
--------------------------------------------------------

  CREATE TABLE "ADM_TX_CODES_RENEWAL_VALUE" 
   (	"ID" NUMBER, 
	"CODE_ID" NUMBER, 
	"RENEWALS_VALUE" NUMBER
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_TX_FEATURE_TYPES
--------------------------------------------------------

  CREATE TABLE "ADM_TX_FEATURE_TYPES" 
   (	"FEATURE_ID" NUMBER(3,0), 
	"TYPE_ID" NUMBER(5,0)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_TX_LINKS
--------------------------------------------------------

  CREATE TABLE "ADM_TX_LINKS" 
   (	"TX_TYPE_ID" NUMBER(5,0), 
	"TX_CODE_ID" NUMBER(5,0), 
	"DESCRIPTION" VARCHAR2(500 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_TX_TYPES
--------------------------------------------------------

  CREATE TABLE "ADM_TX_TYPES" 
   (	"ID" NUMBER(5,0), 
	"NAME" VARCHAR2(100 BYTE), 
	"VALUE" VARCHAR2(50 BYTE), 
	"IS_DEFAULT" NUMBER(1,0) DEFAULT 0, 
	"IS_DELETED" NUMBER(1,0) DEFAULT 0
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ADM_USERS
--------------------------------------------------------

  CREATE TABLE "ADM_USERS" 
   (	"USER_ID" NUMBER(6,0), 
	"NT_ACCOUNT" VARCHAR2(100 BYTE), 
	"PROFILE_ID" NUMBER(6,0), 
	"IS_DELETED" NUMBER(1,0) DEFAULT 0, 
	"IS_ACTIVE" NUMBER(1,0) DEFAULT 1, 
	"SOURCE" VARCHAR2(100 BYTE), 
	"SESSION_COUNTER" NUMBER(6,0) DEFAULT 0, 
	"THEME_ID" VARCHAR2(100 BYTE) DEFAULT 'DEFAULT', 
	"BILLING_ACCOUNT" VARCHAR2(200 BYTE), 
	"CREATION_DATE" DATE DEFAULT sysdate, 
	"MODIFICATION_DATE" DATE DEFAULT sysdate, 
	"LAST_LOGIN" DATE DEFAULT NULL
   ) 
   ;
--------------------------------------------------------
--  DDL for Table BALANCE_DISPUTE_INTERFACE
--------------------------------------------------------

  CREATE TABLE "BALANCE_DISPUTE_INTERFACE" 
   (	"SP_NAME" VARCHAR2(4000 BYTE), 
	"ID" NUMBER, 
	"INPUT_PARAMETERS" VARCHAR2(4000 BYTE), 
	"MAX_NO_PARAMETER" NUMBER
   ) 
   ;
--------------------------------------------------------
--  DDL for Table BK_SYSTEM_PROPERTIES
--------------------------------------------------------

  CREATE TABLE "BK_SYSTEM_PROPERTIES" 
   (	"APPLICATION" VARCHAR2(100 BYTE), 
	"PROFILE" VARCHAR2(100 BYTE), 
	"LABEL" VARCHAR2(100 BYTE), 
	"VALUE" VARCHAR2(1000 BYTE), 
	"CODE" VARCHAR2(50 BYTE), 
	"DESCRIPTION" VARCHAR2(1000 BYTE), 
	"TYPE" NUMBER(1,0)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table DSS_COLUMNS
--------------------------------------------------------

  CREATE TABLE "DSS_COLUMNS" 
   (	"DSS_PAGENAME" VARCHAR2(100 BYTE), 
	"COLUMN_NAME" VARCHAR2(100 BYTE), 
	"DISPLAYNAME" VARCHAR2(100 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table DSS_NODES
--------------------------------------------------------

  CREATE TABLE "DSS_NODES" 
   (	"ID" NUMBER(6,0) GENERATED ALWAYS AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE , 
	"ADDRESS" VARCHAR2(2000 BYTE), 
	"PORT" NUMBER(5,0), 
	"USER_NAME" VARCHAR2(1000 BYTE), 
	"PASSWORD" VARCHAR2(2000 BYTE), 
	"NUMBER_OF_SESSIONS" NUMBER(5,0), 
	"CONCURRENT_CALLS" NUMBER(5,0), 
	"CONNECTION_TIMEOUT" NUMBER(6,0), 
	"EXTRA_CONF" VARCHAR2(2000 BYTE), 
	"SCHEMA" VARCHAR2(1000 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table DYN_PAGES
--------------------------------------------------------

  CREATE TABLE "DYN_PAGES" 
   (	"PAGE_ID" NUMBER, 
	"PAGE_NAME" VARCHAR2(100 BYTE), 
	"PRIVILEGE_ID" NUMBER, 
	"REQUIRE_SUBSCRIBER" NUMBER(1,0) DEFAULT 0, 
	"IS_DELETED" NUMBER(1,0) DEFAULT 0, 
	"CREATION_DATE" DATE DEFAULT sysdate, 
	"PRIVILEGE_NAME" VARCHAR2(100 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table DYN_PAGES_STEPS
--------------------------------------------------------

  CREATE TABLE "DYN_PAGES_STEPS" 
   (	"STEP_ID" NUMBER, 
	"PAGE_ID" NUMBER, 
	"STEP_TYPE" NUMBER(1,0), 
	"STEP_NAME" VARCHAR2(100 BYTE), 
	"STEP_ORDER" NUMBER(6,0), 
	"IS_DELETED" NUMBER(1,0) DEFAULT 0, 
	"IS_HIDDEN" NUMBER(1,0) DEFAULT 0
   ) 
   ;
--------------------------------------------------------
--  DDL for Table DYN_STEP_HTTP_CONFIGURATION
--------------------------------------------------------

  CREATE TABLE "DYN_STEP_HTTP_CONFIGURATION" 
   (	"CONFIG_ID" NUMBER, 
	"STEP_ID" NUMBER, 
	"HTTP_URL" VARCHAR2(4000 BYTE), 
	"HEADERS" VARCHAR2(4000 BYTE), 
	"MAX_RECORDS" NUMBER, 
	"SUCCESS_CODE" VARCHAR2(1080 BYTE), 
	"IS_DELETED" NUMBER DEFAULT 0, 
	"HTTP_METHOD" NUMBER, 
	"REQUEST_CONTENT_TYPE" NUMBER DEFAULT NULL, 
	"RESPONSE_CONTENT_TYPE" NUMBER, 
	"RESPONSE_FORM" NUMBER, 
	"REQUEST_BODY" VARCHAR2(4000 BYTE), 
	"KEY_VALUE_DELIMITER" VARCHAR2(1080 BYTE), 
	"MAIN_DELIMITER" VARCHAR2(1080 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table DYN_STEP_HTTP_PARAMETERS
--------------------------------------------------------

  CREATE TABLE "DYN_STEP_HTTP_PARAMETERS" 
   (	"PARAM_ID" NUMBER(10,0), 
	"CONFIG_ID" NUMBER(10,0), 
	"PARAMETER_NAME" VARCHAR2(100 BYTE), 
	"PARAMETER_DATA_TYPE" NUMBER(10,0), 
	"PARAMETER_TYPE" NUMBER(10,0), 
	"PARAMETER_ORDER" NUMBER(10,0), 
	"INPUT_METHOD" NUMBER(10,0), 
	"DEFAULT_VALUE" VARCHAR2(4000 BYTE), 
	"IS_RESPONSE_CODE" NUMBER(1,0), 
	"IS_RESPONSE_DESCRIPTION" NUMBER(1,0), 
	"DISPLAY_NAME" VARCHAR2(100 BYTE), 
	"IS_HIDDEN" NUMBER(1,0), 
	"SOURCE_STEP_PARAMETER_ID" NUMBER(10,0), 
	"IS_DELETED" NUMBER(1,0) DEFAULT 0, 
	"DISPLAY_ORDER" NUMBER(6,0), 
	"DATE_FORMAT" VARCHAR2(2000 BYTE), 
	"STATIC_DATA" VARCHAR2(4000 BYTE), 
	"SOURCE_STEP_PARAMETER_NAME" VARCHAR2(100 BYTE), 
	"XPATH" VARCHAR2(4000 BYTE), 
	"JSON_PATH" VARCHAR2(4000 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table DYN_STEP_HTTP_RESPONSE_MAPPING
--------------------------------------------------------

  CREATE TABLE "DYN_STEP_HTTP_RESPONSE_MAPPING" 
   (	"MAPPING_ID" NUMBER, 
	"PARAMETER_ID" NUMBER, 
	"HEADER_NAME" VARCHAR2(1080 BYTE), 
	"IS_DELETED" NUMBER DEFAULT 0, 
	"HEADER_DISPLAY_NAME" VARCHAR2(1080 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table DYN_STEP_SP_CONFIGURATION
--------------------------------------------------------

  CREATE TABLE "DYN_STEP_SP_CONFIGURATION" 
   (	"CONFIG_ID" NUMBER(10,0), 
	"STEP_ID" NUMBER(10,0), 
	"DB_URL" VARCHAR2(500 BYTE), 
	"DB_USERNAME" VARCHAR2(500 BYTE), 
	"DB_PASSWORD" VARCHAR2(100 BYTE), 
	"PROCEDURE_NAME" VARCHAR2(100 BYTE), 
	"MAX_RECORDS" NUMBER, 
	"SUCCESS_CODE" VARCHAR2(100 BYTE), 
	"IS_DELETED" NUMBER(1,0) DEFAULT 0, 
	"EXTRA_CONFIGURATIONS" VARCHAR2(4000 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table DYN_STEP_SP_CURSOR_MAPPING
--------------------------------------------------------

  CREATE TABLE "DYN_STEP_SP_CURSOR_MAPPING" 
   (	"MAPPING_ID" NUMBER(10,0), 
	"PARAMETER_ID" NUMBER(10,0), 
	"ACTUAL_COLUMN_NAME" VARCHAR2(30 BYTE), 
	"DISPLAY_COLUMN_NAME" VARCHAR2(100 BYTE), 
	"IS_DELETED" NUMBER(1,0) DEFAULT 0
   ) 
   ;
--------------------------------------------------------
--  DDL for Table DYN_STEP_SP_PARAMETERS
--------------------------------------------------------

  CREATE TABLE "DYN_STEP_SP_PARAMETERS" 
   (	"PARAM_ID" NUMBER(10,0), 
	"CONFIG_ID" NUMBER(10,0), 
	"PARAMETER_NAME" VARCHAR2(100 BYTE), 
	"PARAMETER_DATA_TYPE" NUMBER(10,0), 
	"PARAMETER_TYPE" NUMBER(10,0), 
	"PARAMETER_ORDER" NUMBER(10,0), 
	"INPUT_METHOD" NUMBER(10,0), 
	"DEFAULT_VALUE" VARCHAR2(4000 BYTE), 
	"IS_RESPONSE_CODE" NUMBER(1,0), 
	"IS_RESPONSE_DESCRIPTION" NUMBER(1,0), 
	"DISPLAY_NAME" VARCHAR2(100 BYTE), 
	"IS_HIDDEN" NUMBER(1,0), 
	"SOURCE_STEP_PARAMETER_ID" NUMBER(10,0), 
	"IS_DELETED" NUMBER(1,0) DEFAULT 0, 
	"DISPLAY_ORDER" NUMBER(6,0), 
	"DATE_FORMAT" VARCHAR2(1080 BYTE), 
	"STATIC_DATA" VARCHAR2(1080 BYTE), 
	"SOURCE_STEP_PARAMETER_NAME" VARCHAR2(100 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ETOPUP_TRANSACTIONS_TEST
--------------------------------------------------------

  CREATE TABLE "ETOPUP_TRANSACTIONS_TEST" 
   (	"TRANSFER_DATE" DATE, 
	"TRANSACTION_WAY" VARCHAR2(32 BYTE), 
	"TRANSACTION_TYPE" VARCHAR2(32 BYTE), 
	"TRANSITION_CATEGORY" VARCHAR2(32 BYTE), 
	"CHANNEL_TYPE" VARCHAR2(32 BYTE), 
	"SENDER_MSISDN" VARCHAR2(32 BYTE), 
	"RECEIVER_MSISDN" VARCHAR2(32 BYTE), 
	"SENDER_CATEGORY" VARCHAR2(32 BYTE), 
	"RECEIVER_CATEGORY" VARCHAR2(32 BYTE), 
	"TRANSFER_AMOUNT" NUMBER, 
	"PRODUCT" VARCHAR2(32 BYTE), 
	"REQUEST_SOURCE" VARCHAR2(32 BYTE)
   ) 
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table FLEX_SHARE_HISTORY_NODES
--------------------------------------------------------

  CREATE TABLE "FLEX_SHARE_HISTORY_NODES" 
   (	"ID" NUMBER(6,0), 
	"ADDRESS" VARCHAR2(2000 BYTE), 
	"PORT" NUMBER(5,0), 
	"USERNAME" VARCHAR2(1000 BYTE), 
	"PASSWORD" VARCHAR2(2000 BYTE), 
	"NUMBER_OF_SESSIONS" NUMBER(5,0), 
	"CONCURRENT_CALLS" NUMBER(5,0), 
	"CONNECTION_TIMEOUT" NUMBER(6,0), 
	"EXTRA_CONF" VARCHAR2(2000 BYTE), 
	"SCHEMA" VARCHAR2(1000 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table H_NOTEPAD_ENTRIES
--------------------------------------------------------

CREATE TABLE H_NOTEPAD_ENTRIES 
(
    ENTRY_DATE DATE DEFAULT SYSDATE, 
    MSISDN VARCHAR2(13 BYTE), 
    MSISDN_MOD_X NUMBER(2,0), 
    USER_ID NUMBER(6,0), 
    NOTEPAD_ENTRY VARCHAR2(4000 BYTE), 
    USER_NAME VARCHAR2(200 BYTE)
)
PARTITION BY RANGE (ENTRY_DATE)
INTERVAL (NUMTODSINTERVAL(1, 'DAY'))
(
    PARTITION VALUES LESS THAN (TIMESTAMP '2023-01-01 00:00:00')
);

--------------------------------------------------------
--  DDL for Table LK_ACCOUNT_FLAGS
--------------------------------------------------------

  CREATE TABLE "LK_ACCOUNT_FLAGS" 
   (	"ACCOUNT_FLAG_CODE" VARCHAR2(20 BYTE), 
	"ACCOUNT_FLAG_NAME" VARCHAR2(100 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_ACCUMULATORS
--------------------------------------------------------

  CREATE TABLE "LK_ACCUMULATORS" 
   (	"ACC_ID" NUMBER(2,0)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_AIR_SERVERS
--------------------------------------------------------

  CREATE TABLE "LK_AIR_SERVERS" 
   (	"URL" VARCHAR2(1000 BYTE), 
	"AGENT_NAME" VARCHAR2(100 BYTE), 
	"HOST" VARCHAR2(100 BYTE), 
	"AUTHORIZATION" VARCHAR2(100 BYTE), 
	"IS_DOWN" NUMBER(1,0) DEFAULT 0, 
	"ID" NUMBER(1,0), 
	"CAPABILITY_VALUE" VARCHAR2(500 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_AIR_SERVERS_BK
--------------------------------------------------------

  CREATE TABLE "LK_AIR_SERVERS_BK" 
   (	"URL" VARCHAR2(1000 BYTE), 
	"AGENT_NAME" VARCHAR2(100 BYTE), 
	"HOST" VARCHAR2(100 BYTE), 
	"AUTHORIZATION" VARCHAR2(100 BYTE), 
	"IS_DOWN" NUMBER(1,0), 
	"ID" NUMBER(1,0), 
	"CAPABILITY_VALUE" VARCHAR2(500 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_BALANCE_DISPUTE_DETAILS_CONFIGURATION
--------------------------------------------------------

  CREATE TABLE "LK_BALANCE_DISPUTE_DETAILS_CONFIGURATION" 
   (	"COLUMN_NAME" VARCHAR2(100 BYTE), 
	"DISPLAY_NAME" VARCHAR2(100 BYTE), 
	"COLUMN_ORDER" NUMBER(3,0), 
	"PRIVILEGE_ID" NUMBER(6,0)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_BOOLEAN
--------------------------------------------------------

  CREATE TABLE "LK_BOOLEAN" 
   (	"BOOLEAN_ID" NUMBER(1,0), 
	"BOOLEAN_VALUE" VARCHAR2(20 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_BT_TRANSACTION_STATUS
--------------------------------------------------------

  CREATE TABLE "LK_BT_TRANSACTION_STATUS" 
   (	"CODE" VARCHAR2(20 BYTE), 
	"NAME" VARCHAR2(100 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_CC_FEATURES
--------------------------------------------------------

  CREATE TABLE "LK_CC_FEATURES" 
   (	"CC_FEATURE_ID" NUMBER(10,0), 
	"CC_FEATURE_NAME" VARCHAR2(100 BYTE), 
	"HAS_TRX_TYPE" NUMBER(1,0), 
	"PAGE_NAME" VARCHAR2(100 BYTE)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
   ;
--------------------------------------------------------
--  DDL for Table LK_DATASOURCE_TYPE
--------------------------------------------------------

  CREATE TABLE "LK_DATASOURCE_TYPE" 
   (	"ID" NUMBER(1,0), 
	"NAME" VARCHAR2(100 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_DED_ACCOUNTS
--------------------------------------------------------

  CREATE TABLE "LK_DED_ACCOUNTS" 
   (	"DA_ID" NUMBER(2,0)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_DISCONNECTION_CODES
--------------------------------------------------------

  CREATE TABLE "LK_DISCONNECTION_CODES" 
   (	"ID" NUMBER(20,0), 
	"CODE_ID" NUMBER(20,0), 
	"NAME" VARCHAR2(50 BYTE), 
	"IS_DELETED" NUMBER(1,0) DEFAULT 0, 
	"CREATION_DATE" TIMESTAMP (6) DEFAULT SYSTIMESTAMP
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_FEATURES
--------------------------------------------------------

  CREATE TABLE "LK_FEATURES" 
   (	"ID" NUMBER(10,0), 
	"NAME" VARCHAR2(100 BYTE), 
	"PAGE_NAME" VARCHAR2(100 BYTE), 
	"TYPE" NUMBER(*,0), 
	"URL" VARCHAR2(100 BYTE), 
	"DESCRIPTION" VARCHAR2(100 BYTE), 
	"LABEL" VARCHAR2(100 BYTE), 
	"ICON" VARCHAR2(100 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_FEATURES_TYPE
--------------------------------------------------------

  CREATE TABLE "LK_FEATURES_TYPE" 
   (	"ID" NUMBER(10,0), 
	"NAME" VARCHAR2(100 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_FLEX_OFFER_TYPE
--------------------------------------------------------

  CREATE TABLE "LK_FLEX_OFFER_TYPE" 
   (	"TYPE_ID" NUMBER, 
	"TYPE_NAME" VARCHAR2(10 CHAR)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_FLEX_OFFERS
--------------------------------------------------------

  CREATE TABLE "LK_FLEX_OFFERS" 
   (	"ID" NUMBER, 
	"NAME" VARCHAR2(20 CHAR), 
	"DESCRIPTION" VARCHAR2(200 CHAR), 
	"TYPE_ID" NUMBER, 
	"PACKAGE_ID" VARCHAR2(20 CHAR), 
	"OFFER_ID" NUMBER
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_FOOTPRINT_PAGE_INFO
--------------------------------------------------------

  CREATE TABLE "LK_FOOTPRINT_PAGE_INFO" 
   (	"PAGE_ID" NUMBER, 
	"METHOD_NAME" VARCHAR2(100 BYTE), 
	"ACTION_NAME" VARCHAR2(100 BYTE), 
	"ACTION_TYPE" VARCHAR2(100 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_FOOTPRINT_PAGES
--------------------------------------------------------

  CREATE TABLE "LK_FOOTPRINT_PAGES" 
   (	"PAGE_ID" NUMBER, 
	"PAGE_NAME" VARCHAR2(100 BYTE), 
	"CONTROLLER_NAME" VARCHAR2(100 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_LANGUAGES
--------------------------------------------------------

  CREATE TABLE "LK_LANGUAGES" 
   (	"ID" NUMBER(10,0), 
	"NAME" VARCHAR2(100 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_MAIN_PRODUCT_TYPES
--------------------------------------------------------

  CREATE TABLE "LK_MAIN_PRODUCT_TYPES" 
   (	"TYPE" VARCHAR2(50 BYTE), 
	"DISPLAY_NAME" VARCHAR2(50 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_MAREDCARD_LIST
--------------------------------------------------------

  CREATE TABLE "LK_MAREDCARD_LIST" 
   (	"NAME" VARCHAR2(100 BYTE), 
	"ID" VARCHAR2(100 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_MENUS
--------------------------------------------------------

  CREATE TABLE "LK_MENUS" 
   (	"MENU_ID" NUMBER(6,0), 
	"LABEL" VARCHAR2(100 BYTE), 
	"PARENT_ID" NUMBER(6,0), 
	"ROUTER" VARCHAR2(100 BYTE), 
	"ORDERING" NUMBER(6,0), 
	"ICON" VARCHAR2(100 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_MONETARY_LIMITS
--------------------------------------------------------

  CREATE TABLE "LK_MONETARY_LIMITS" 
   (	"LIMIT_ID" NUMBER(2,0), 
	"LIMIT_NAME" VARCHAR2(100 BYTE), 
	"DEFAULT_VALUE" NUMBER(10,3)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_OFFERS_STATES
--------------------------------------------------------

  CREATE TABLE "LK_OFFERS_STATES" 
   (	"STATE_ID" NUMBER, 
	"STATE_DESC" VARCHAR2(200 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_OFFERS_TYPES
--------------------------------------------------------

  CREATE TABLE "LK_OFFERS_TYPES" 
   (	"TYPE_ID" NUMBER, 
	"TYPE_DESC" VARCHAR2(200 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_PAM_CLASSES
--------------------------------------------------------

  CREATE TABLE "LK_PAM_CLASSES" 
   (	"ID" NUMBER(10,0), 
	"NAME" VARCHAR2(200 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_PAM_PERIOD
--------------------------------------------------------

  CREATE TABLE "LK_PAM_PERIOD" 
   (	"ID" VARCHAR2(200 BYTE), 
	"NAME" VARCHAR2(200 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_PAM_PRIORITY
--------------------------------------------------------

  CREATE TABLE "LK_PAM_PRIORITY" 
   (	"ID" NUMBER, 
	"NAME" VARCHAR2(200 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_PAM_SCHEDULES
--------------------------------------------------------

  CREATE TABLE "LK_PAM_SCHEDULES" 
   (	"ID" NUMBER, 
	"NAME" VARCHAR2(200 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_PAM_SERVICES
--------------------------------------------------------

  CREATE TABLE "LK_PAM_SERVICES" 
   (	"ID" NUMBER, 
	"NAME" VARCHAR2(200 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_REFILL_PAYMENT_PROFILE
--------------------------------------------------------

  CREATE TABLE "LK_REFILL_PAYMENT_PROFILE" 
   (	"REFILL_PROFILE_ID" VARCHAR2(50 BYTE), 
	"REFILL_PROFILE_NAME" VARCHAR2(100 BYTE), 
	"AMOUNT_FROM" NUMBER(12,2), 
	"AMOUNT_TO" NUMBER(12,2)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_REGIONS
--------------------------------------------------------

  CREATE TABLE "LK_REGIONS" 
   (	"REGION" VARCHAR2(500 BYTE), 
	"REGION_DESCRIPTION" VARCHAR2(500 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_SMS_ACTION
--------------------------------------------------------

  CREATE TABLE "LK_SMS_ACTION" 
   (	"SMS_ACTION_ID" NUMBER(20,0), 
	"ACTION_NAME" VARCHAR2(200 BYTE), 
	"DESCRIPTION" VARCHAR2(200 BYTE), 
	"CODE" VARCHAR2(200 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_SMS_GATEWAYS_STATUS
--------------------------------------------------------

  CREATE TABLE "LK_SMS_GATEWAYS_STATUS" 
   (	"STATUS_ID" NUMBER(1,0), 
	"STATUS_NAME" VARCHAR2(200 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_SMS_PRIORITIES
--------------------------------------------------------

  CREATE TABLE "LK_SMS_PRIORITIES" 
   (	"PRIORITY_ID" NUMBER, 
	"PRIORITY_NAME" VARCHAR2(50 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_SMS_TEMPLATE_PARAM
--------------------------------------------------------

  CREATE TABLE "LK_SMS_TEMPLATE_PARAM" 
   (	"PARAMETER_ID" NUMBER(20,0), 
	"SMS_ACTION_ID" NUMBER(20,0), 
	"PARAMETER_NAME" VARCHAR2(200 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_UNIT_TYPE_DESC
--------------------------------------------------------

  CREATE TABLE "LK_UNIT_TYPE_DESC" 
   (	"ID" NUMBER, 
	"NAME" VARCHAR2(200 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_USAGE_COUNTER_DESC
--------------------------------------------------------

  CREATE TABLE "LK_USAGE_COUNTER_DESC" 
   (	"ID" NUMBER(10,0), 
	"NAME" VARCHAR2(200 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table LK_VOUCHER_SERVERS
--------------------------------------------------------

  CREATE TABLE "LK_VOUCHER_SERVERS" 
   (	"URL" VARCHAR2(1000 BYTE), 
	"AGENT_NAME" VARCHAR2(100 BYTE), 
	"HOST" VARCHAR2(100 BYTE), 
	"AUTHORIZATION" VARCHAR2(100 BYTE), 
	"SERVER_INDEX" NUMBER, 
	"VOUCHER_SERIAL_LENGTH" NUMBER, 
	"CAPABILITY_VALUE" VARCHAR2(500 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table NBA_DUMMY_TABLE_BK
--------------------------------------------------------

  CREATE TABLE "NBA_DUMMY_TABLE_BK" 
   (	"GIFTID" VARCHAR2(20 BYTE), 
	"GIFTSEQID" VARCHAR2(20 BYTE), 
	"GIFTDESCRIPTION" VARCHAR2(2000 BYTE), 
	"GIFTSTATUS" VARCHAR2(20 BYTE), 
	"TIMEOFASSIGNMENT" VARCHAR2(20 BYTE), 
	"TIMEOFASSIGNMENTEXPIRY" VARCHAR2(20 BYTE), 
	"TIMEOFRDEMPTION" VARCHAR2(20 BYTE), 
	"GIFTNODAYS" VARCHAR2(20 BYTE), 
	"GIFTSHORTCODE" VARCHAR2(20 BYTE), 
	"GIFTUNITS" VARCHAR2(20 BYTE), 
	"GIFTFEES" VARCHAR2(20 BYTE), 
	"GIFTPARAM1" VARCHAR2(20 BYTE), 
	"GIFTPARAM2" VARCHAR2(20 BYTE), 
	"GIFTPARAM3" VARCHAR2(20 BYTE), 
	"GIFTPARAM4" VARCHAR2(20 BYTE), 
	"GIFTPARAM5" VARCHAR2(20 BYTE), 
	"GIFTPARAM6" VARCHAR2(20 BYTE), 
	"GIFTPARAM7" VARCHAR2(20 BYTE), 
	"GIFTPARAM8" VARCHAR2(20 BYTE), 
	"GIFTPARAM9" VARCHAR2(20 BYTE), 
	"GIFTPARAM10" VARCHAR2(20 BYTE), 
	"GIFTPARAM11" VARCHAR2(2020 BYTE), 
	"GIFTPARAM13" VARCHAR2(2000 BYTE), 
	"GIFTPARAM21" VARCHAR2(20 BYTE), 
	"GIFTPARAM15" VARCHAR2(20 BYTE), 
	"GIFTPARAM16" VARCHAR2(20 BYTE), 
	"GIFTPARAM18" VARCHAR2(20 BYTE), 
	"GIFTPARAM17" VARCHAR2(20 BYTE), 
	"GIFTPARAM19" VARCHAR2(20 BYTE), 
	"GIFTPARAM20" VARCHAR2(20 BYTE), 
	"GIFTPARAM14" VARCHAR2(20 BYTE), 
	"GIFTPARAM22" VARCHAR2(20 BYTE), 
	"GIFTTYPE" VARCHAR2(20 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ODS_H_ACC_ACTIVITIES
--------------------------------------------------------

  CREATE TABLE "ODS_H_ACC_ACTIVITIES" 
   (	"ACTIVITY_ID" NUMBER(10,0), 
	"ACTIVITY_NAME" VARCHAR2(100 BYTE), 
	"TABLE_TYPE" VARCHAR2(100 BYTE), 
	"DR_ID_IDX" NUMBER(10,0), 
	"BALANCE_IDX" NUMBER(10,0), 
	"EXP_DATE_IDX" NUMBER(10,0), 
	"DA_AMOUNT_IDX" NUMBER(10,0), 
	"ADJ_ACTION_IDX" NUMBER(10,0), 
	"IS_NEW_FORMAT_IDX" NUMBER(10,0), 
	"IS_DELETED" NUMBER
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ODS_H_ACC_ACTIVITY_D
--------------------------------------------------------

  CREATE TABLE "ODS_H_ACC_ACTIVITY_D" 
   (	"ACTIVITY_ID" NUMBER(10,0), 
	"COLUMN_IDX" NUMBER(10,0), 
	"COLUMN_KEY" VARCHAR2(100 BYTE), 
	"DISPLAY_NAME" VARCHAR2(100 BYTE), 
	"IS_DELETED" NUMBER, 
	"DETAIL_TYPE" VARCHAR2(100 BYTE), 
	"DISPLAY_ORDER" NUMBER(10,0)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ODS_H_ACC_HEADERS
--------------------------------------------------------

  CREATE TABLE "ODS_H_ACC_HEADERS" 
   (	"HEADER_ID" NUMBER(10,0), 
	"HEADER_NAME" VARCHAR2(100 BYTE), 
	"HEADER_TYPE" VARCHAR2(100 BYTE), 
	"DISPLAY_NAME" VARCHAR2(100 BYTE), 
	"IS_DELETED" NUMBER, 
	"ORDER" NUMBER
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ODS_H_ACC_HEADERS_MAPPING
--------------------------------------------------------

  CREATE TABLE "ODS_H_ACC_HEADERS_MAPPING" 
   (	"ACTIVITY_ID" NUMBER(10,0), 
	"HEADER_ID" VARCHAR2(100 BYTE), 
	"COLUMN_IDX" NUMBER(10,0), 
	"IS_STATIC" NUMBER(10,0), 
	"CUSTOM_FORMAT" VARCHAR2(100 BYTE), 
	"DEFAULT_VALUE" VARCHAR2(100 BYTE), 
	"IS_DELETED" NUMBER, 
	"PRE_CONDITIONS" VARCHAR2(500 BYTE), 
	"PRE_CONDITIONS_VALUE" VARCHAR2(500 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ODS_H_ACC_HEADERS_MAPPING_BK
--------------------------------------------------------

  CREATE TABLE "ODS_H_ACC_HEADERS_MAPPING_BK" 
   (	"ACTIVITY_ID" NUMBER(10,0), 
	"HEADER_ID" VARCHAR2(100 BYTE), 
	"COLUMN_IDX" NUMBER(10,0), 
	"IS_STATIC" NUMBER(10,0), 
	"CUSTOM_FORMAT" VARCHAR2(100 BYTE), 
	"DEFAULT_VALUE" VARCHAR2(100 BYTE), 
	"IS_DELETED" NUMBER, 
	"PRE_CONDITIONS" VARCHAR2(500 BYTE), 
	"PRE_CONDITIONS_VALUE" VARCHAR2(500 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table ODS_NODES
--------------------------------------------------------

  CREATE TABLE "ODS_NODES" 
   (	"ID" NUMBER(6,0) GENERATED ALWAYS AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE , 
	"ADDRESS" VARCHAR2(2000 BYTE), 
	"PORT" NUMBER(5,0), 
	"USER_NAME" VARCHAR2(1000 BYTE), 
	"PASSWORD" VARCHAR2(2000 BYTE), 
	"NUMBER_OF_SESSIONS" NUMBER(5,0), 
	"CONCURRENT_CALLS" NUMBER(5,0), 
	"CONNECTION_TIMEOUT" NUMBER(6,0), 
	"EXTRA_CONF" VARCHAR2(2000 BYTE), 
	"SCHEMA" VARCHAR2(1000 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table SIM_UCIP_ACCOUNT_DETAILS
--------------------------------------------------------

  CREATE TABLE "SIM_UCIP_ACCOUNT_DETAILS" 
   (	"SUBSCRIBER" VARCHAR2(300 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table SMS_ERROR
--------------------------------------------------------

  CREATE TABLE "SMS_ERROR" 
   (	"SMS_ERROR_ID" NUMBER(12,0), 
	"ENTRY_DATE" TIMESTAMP (6) DEFAULT SYSTIMESTAMP, 
	"MSISDN" VARCHAR2(50 BYTE), 
	"ERROR_MESSAGE" VARCHAR2(4000 BYTE), 
	"ERROR_CODE" VARCHAR2(200 BYTE), 
	"PROCESSED" VARCHAR2(1 BYTE), 
	"PROCESSED_DATE" TIMESTAMP (6), 
	"PROCEDURE_NAME" VARCHAR2(50 BYTE)
   ) PARTITION BY RANGE (ENTRY_DATE)
INTERVAL (NUMTODSINTERVAL(1, 'DAY'))
(
    PARTITION VALUES LESS THAN (TIMESTAMP '2023-01-01 00:00:00')
);

--------------------------------------------------------
--  DDL for Table SMS_Q
--------------------------------------------------------

  CREATE TABLE "SMS_Q" 
   (	"SMS_ID" NUMBER(20,0), 
	"ENTRY_DATE" DATE, 
	"MSISDN" VARCHAR2(12 BYTE), 
	"SMS_TEXT" VARCHAR2(300 BYTE), 
	"TEMPLATE_ID" NUMBER(20,0), 
	"LANG_ID" NUMBER(1,0), 
	"GATEWAY_ID" NUMBER(1,0), 
	"PRIORITY_ID" NUMBER(1,0) DEFAULT 0, 
	"CS_TEMPLATES_ID" NUMBER, 
	"CS_TEMPLATES_PARAMETERS" VARCHAR2(4000 BYTE)
   ) 
PARTITION BY RANGE (ENTRY_DATE)
INTERVAL (NUMTODSINTERVAL(1, 'DAY'))
(
    PARTITION VALUES LESS THAN (TIMESTAMP '2023-01-01 00:00:00')
);
--------------------------------------------------------
--  DDL for Table TEST_FILE_DATA
--------------------------------------------------------

  CREATE TABLE "TEST_FILE_DATA" 
   (	"ID" NUMBER, 
	"NAME" VARCHAR2(15 BYTE), 
	"MOBILE" VARCHAR2(12 BYTE), 
	"MAIL" VARCHAR2(20 BYTE), 
	"CREATEDAT" TIMESTAMP (6) DEFAULT systimestamp
   ) 
   ;
--------------------------------------------------------
--  DDL for Table TEST_VOUCHERS
--------------------------------------------------------

  CREATE TABLE "TEST_VOUCHERS" 
   (	"ID" NUMBER GENERATED BY DEFAULT AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE , 
	"SERIAL_NUMBER" VARCHAR2(5 BYTE), 
	"REQUEST_ID" RAW(16), 
	"CREATEDAT" TIMESTAMP (6) DEFAULT systimestamp, 
	"AMOUNT" FLOAT(126), 
	"PERCENTAGE" NUMBER
   ) 
   ;
--------------------------------------------------------
--  DDL for Table TESTT_VOUCHERS
--------------------------------------------------------

  CREATE TABLE "TESTT_VOUCHERS" 
   (	"ID" NUMBER GENERATED BY DEFAULT AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE , 
	"SERIAL_NUMBER" VARCHAR2(5 BYTE), 
	"REQUEST_ID" RAW(16), 
	"CREATEDAT" TIMESTAMP (6) DEFAULT systimestamp, 
	"AMOUNT" FLOAT(126), 
	"PERCENTAGE" NUMBER
   ) 
   ;
--------------------------------------------------------
--  DDL for Table TX_CALL_REASONS
--------------------------------------------------------

  CREATE TABLE "TX_CALL_REASONS" 
   (	"USER_ID" NUMBER(6,0), 
	"USER_NAME" VARCHAR2(50 BYTE), 
	"MSISDN" VARCHAR2(20 BYTE), 
	"MSISDN_LAST_DIGIT" NUMBER, 
	"ENTRY_DATE" DATE DEFAULT sysdate, 
	"DIRECTION" VARCHAR2(50 BYTE), 
	"FAMILY" VARCHAR2(50 BYTE), 
	"TYPE" VARCHAR2(50 BYTE), 
	"REASON" VARCHAR2(50 BYTE), 
	"CALL_REASON_ID" NUMBER
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
  STORAGE(
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   
  PARTITION BY RANGE ("ENTRY_DATE") INTERVAL (NUMTODSINTERVAL(1, 'DAY')) 
  SUBPARTITION BY LIST ("MSISDN_LAST_DIGIT") 
 (PARTITION "P0"  VALUES LESS THAN (TO_DATE(' 2022-06-07 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN')) 
PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
  STORAGE(
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   
 ( SUBPARTITION "S0"  VALUES (0) SEGMENT CREATION DEFERRED 
  STORAGE(
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   
 NOCOMPRESS , 
  SUBPARTITION "S1"  VALUES (1) SEGMENT CREATION DEFERRED 
  STORAGE(
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   
 NOCOMPRESS , 
  SUBPARTITION "S2"  VALUES (2) SEGMENT CREATION DEFERRED 
  STORAGE(
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   
 NOCOMPRESS , 
  SUBPARTITION "S3"  VALUES (3) SEGMENT CREATION DEFERRED 
  STORAGE(
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   
 NOCOMPRESS , 
  SUBPARTITION "S4"  VALUES (4) SEGMENT CREATION DEFERRED 
  STORAGE(
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   
 NOCOMPRESS , 
  SUBPARTITION "S5"  VALUES (5) SEGMENT CREATION DEFERRED 
  STORAGE(
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   
 NOCOMPRESS , 
  SUBPARTITION "S6"  VALUES (6) SEGMENT CREATION DEFERRED 
  STORAGE(
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   
 NOCOMPRESS , 
  SUBPARTITION "S7"  VALUES (7) SEGMENT CREATION DEFERRED 
  STORAGE(
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   
 NOCOMPRESS , 
  SUBPARTITION "S8"  VALUES (8) SEGMENT CREATION DEFERRED 
  STORAGE(
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   
 NOCOMPRESS , 
  SUBPARTITION "S9"  VALUES (9) SEGMENT CREATION DEFERRED 
  STORAGE(
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   
 NOCOMPRESS ) ) ;
--------------------------------------------------------
--  DDL for Table TX_CUSTOMERS_BARRINGS
--------------------------------------------------------

  CREATE TABLE "TX_CUSTOMERS_BARRINGS" 
   (	"ENTRY_DATE" DATE DEFAULT sysdate, 
	"MSISDN" VARCHAR2(13 BYTE), 
	"MSISDN_MOD_X" NUMBER(2,0), 
	"BARRING_TYPE" NUMBER(2,0), 
	"REASON" VARCHAR2(500 BYTE), 
	"REQUESTED_BY" VARCHAR2(500 BYTE), 
	"USER_ID" NUMBER(6,0)
   )  
   PARTITION BY RANGE (ENTRY_DATE)
INTERVAL (NUMTODSINTERVAL(1, 'DAY'))
(
    PARTITION VALUES LESS THAN (TIMESTAMP '2023-01-01 00:00:00')
);
--------------------------------------------------------
--  DDL for Table TX_LOGIN
--------------------------------------------------------

  CREATE TABLE "TX_LOGIN" 
   (	"TX_LOGIN_ID" NUMBER(16,0) GENERATED ALWAYS AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE , 
	"MACHINE_NAME" VARCHAR2(100 BYTE), 
	"DOMAIN_NAME" VARCHAR2(100 BYTE), 
	"LOGIN_TIME" TIMESTAMP (6) DEFAULT SYSTIMESTAMP, 
	"STATUS" NUMBER(1,0) DEFAULT 0, 
	"MESSAGE" VARCHAR2(200 BYTE), 
	"USER_ID" NUMBER(6,0)
   )
   ;
--------------------------------------------------------
--  DDL for Table TX_SUBSCRIBER_ADJUSTMENTS
--------------------------------------------------------

  CREATE TABLE "TX_SUBSCRIBER_ADJUSTMENTS" 
   (	"ID" NUMBER(16,0), 
	"MSISDN" VARCHAR2(13 BYTE), 
	"MSISDN_MOD_X" NUMBER(2,0), 
	"ADJ_DATE" DATE
   ) 
PARTITION BY RANGE (ADJ_DATE)
INTERVAL (NUMTODSINTERVAL(1, 'DAY'))
(
    PARTITION VALUES LESS THAN (TIMESTAMP '2023-01-01 00:00:00')
);
   
--------------------------------------------------------
--  DDL for Table TX_USED_VOUCHERS
--------------------------------------------------------

  CREATE TABLE "TX_USED_VOUCHERS" 
   (	"VOUCHER_SERIAL_NUMBER" NUMBER(20,0), 
	"AGENT" VARCHAR2(50 BYTE), 
	"BATCH_ID" VARCHAR2(50 BYTE), 
	"CURRENCY" VARCHAR2(50 BYTE), 
	"EXPIRY_DATE" VARCHAR2(50 BYTE), 
	"OPERATOR_ID" VARCHAR2(50 BYTE), 
	"RESPONSE_CODE" VARCHAR2(10 BYTE), 
	"SUBSCRIBER_ID" VARCHAR2(50 BYTE), 
	"TIMESTAMP" VARCHAR2(50 BYTE), 
	"STATE" VARCHAR2(20 BYTE) DEFAULT 'Used', 
	"VALUE" VARCHAR2(50 BYTE), 
	"VOUCHER_GROUP" VARCHAR2(20 BYTE), 
	"RECHARGE_SOURCE" VARCHAR2(50 BYTE), 
	"ACTIVATION_CODE" VARCHAR2(50 BYTE)
   ) PARTITION BY RANGE (EXPIRY_DATE)
INTERVAL (NUMTODSINTERVAL(1, 'DAY'))
(
    PARTITION VALUES LESS THAN (TIMESTAMP '2023-01-01 00:00:00')
);
   
--------------------------------------------------------
--  DDL for Table TX_USER_FOOTPRINT
--------------------------------------------------------

  CREATE TABLE "TX_USER_FOOTPRINT" 
   (	"ID" NUMBER(16,0), 
	"REQUEST_ID" VARCHAR2(100 BYTE), 
	"PAGE_NAME" VARCHAR2(100 BYTE), 
	"TAB_NAME" VARCHAR2(100 BYTE), 
	"ACTION_NAME" VARCHAR2(100 BYTE), 
	"ACTION_TYPE" VARCHAR2(100 BYTE), 
	"ACTION_TIME" TIMESTAMP (6) DEFAULT systimestamp, 
	"USERNAME" VARCHAR2(100 BYTE), 
	"PROFILE_NAME" VARCHAR2(100 BYTE), 
	"MSISDN" VARCHAR2(100 BYTE), 
	"STATUS" VARCHAR2(100 BYTE), 
	"ERROR_MESSAGE" VARCHAR2(100 BYTE), 
	"ERROR_CODE" VARCHAR2(100 BYTE), 
	"SESSION_ID" VARCHAR2(100 BYTE), 
	"CREATED_BY" NUMBER(6,0), 
	"MACHINE_NAME" VARCHAR2(100 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table TX_USER_FOOTPRINT_DETAILS
--------------------------------------------------------

  CREATE TABLE "TX_USER_FOOTPRINT_DETAILS" 
   (	"REQUEST_ID" VARCHAR2(200 BYTE), 
	"PARAM_NAME" VARCHAR2(200 BYTE), 
	"OLD_VAL" VARCHAR2(200 BYTE), 
	"NEW_VAL" VARCHAR2(200 BYTE)
   ) 
   ;
--------------------------------------------------------
--  DDL for Table TX_USER_MONETARY_TOTALS
--------------------------------------------------------

  CREATE TABLE "TX_USER_MONETARY_TOTALS" 
   (	"CURR_DATE" DATE DEFAULT SYSDATE, 
	"USER_ID" NUMBER(6,0), 
	"LIMIT_ID" NUMBER(2,0), 
	"CURR_VALUE" NUMBER(10,3)
   ) PARTITION BY RANGE (CURR_DATE)
INTERVAL (NUMTODSINTERVAL(1, 'DAY'))
(
    PARTITION VALUES LESS THAN (TIMESTAMP '2023-01-01 00:00:00')
);
--------------------------------------------------------
--  DDL for Index BIT_SC_PK_1
--------------------------------------------------------

  CREATE UNIQUE INDEX "BIT_SC_PK_1" ON "ADM_AG_BITS_SC_DESCRIPTION" ("BIT_POSITION", "SERVICE_CLASS_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index ADM_BUSINESS_PAN_DEL_INDX
--------------------------------------------------------

  CREATE INDEX "ADM_BUSINESS_PAN_DEL_INDX" ON "ADM_BUSINESS_PLANS" ("IS_DELETED") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index ADM_BUSINESS_PLAN_ACTIV_INDX
--------------------------------------------------------

  CREATE INDEX "ADM_BUSINESS_PLAN_ACTIV_INDX" ON "ADM_BUSINESS_PLANS" ("ACTIVATION_PLAN") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index ADM_BUSINESS_PLAN_HLR_INDX
--------------------------------------------------------

  CREATE INDEX "ADM_BUSINESS_PLAN_HLR_INDX" ON "ADM_BUSINESS_PLANS" ("HLR_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index ADM_BUSINESS_PLAN_SCID_INDX
--------------------------------------------------------

  CREATE INDEX "ADM_BUSINESS_PLAN_SCID_INDX" ON "ADM_BUSINESS_PLANS" ("SERVICE_CLASS_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index PK_ADM_BUSINESS_PLANS
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_ADM_BUSINESS_PLANS" ON "ADM_BUSINESS_PLANS" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index ADM_BUSINESS_PLANS_UK1
--------------------------------------------------------

  CREATE UNIQUE INDEX "ADM_BUSINESS_PLANS_UK1" ON "ADM_BUSINESS_PLANS" ("NAME", "CODE") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index PK_ADM_HLR_PROFILES
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_ADM_HLR_PROFILES" ON "ADM_HLR_PROFILES" ("HLR_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index PK_ADM_MARQUEE_DATA
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_ADM_MARQUEE_DATA" ON "ADM_MARQUEE_DATA" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index LK_OFFERS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "LK_OFFERS_PK" ON "ADM_OFFERS" ("OFFER_ID", "OFFER_DESC") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index ENTITY11PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ENTITY11PK" ON "ADM_PROFILE_FEATURES" ("PROFILE_ID", "FEATURE_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index ADM_PROFILES_DEL_INDX
--------------------------------------------------------

  CREATE INDEX "ADM_PROFILES_DEL_INDX" ON "ADM_PROFILES" ("IS_DELETED") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index PK_ADM_PROFILES
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_ADM_PROFILES" ON "ADM_PROFILES" ("PROFILE_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index ADM_PROFILES_UK1
--------------------------------------------------------

  CREATE UNIQUE INDEX "ADM_PROFILES_UK1" ON "ADM_PROFILES" ("PROFILE_NAME") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index ADM_PROM_PLAN_DEFAULT_INDX
--------------------------------------------------------

  CREATE INDEX "ADM_PROM_PLAN_DEFAULT_INDX" ON "ADM_PROMOTION_PLANS" ("IS_DEFAULT") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index ADM_PROM_PLAN_DEL_INDX
--------------------------------------------------------

  CREATE INDEX "ADM_PROM_PLAN_DEL_INDX" ON "ADM_PROMOTION_PLANS" ("IS_DELETED") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index PK_ADM_PROMOTION_PLANS
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_ADM_PROMOTION_PLANS" ON "ADM_PROMOTION_PLANS" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index PK_ADM_SERVICE_CLASS_ACC
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_ADM_SERVICE_CLASS_ACC" ON "ADM_SERVICE_CLASS_ACC" ("SERVICE_CLASS_ID", "ACC_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index PK_ADM_SERVICE_CLASS_DA
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_ADM_SERVICE_CLASS_DA" ON "ADM_SERVICE_CLASS_DA" ("DA_ID", "SERVICE_CLASS_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index ADM_SERVICE_CLASSES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ADM_SERVICE_CLASSES_PK" ON "ADM_SERVICE_CLASSES" ("CODE") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index ADM_SERVICE_OFFERING_PLANS_UK1
--------------------------------------------------------

  CREATE UNIQUE INDEX "ADM_SERVICE_OFFERING_PLANS_UK1" ON "ADM_SERVICE_OFFERING_PLANS" ("NAME", "PLAN_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index SMSGATEWAY_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SMSGATEWAY_PK" ON "ADM_SMS_GATEWAYS" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index SMS_TEMPLT_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SMS_TEMPLT_PK" ON "ADM_SMS_TEMPLATE" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index PK_ADM_TX_CODES
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_ADM_TX_CODES" ON "ADM_TX_CODES" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index ADM_TX_CODES_DEF_INDX
--------------------------------------------------------

  CREATE INDEX "ADM_TX_CODES_DEF_INDX" ON "ADM_TX_CODES" ("IS_DEFAULT") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index ADM_TX_CODES_DEL_INDX
--------------------------------------------------------

  CREATE INDEX "ADM_TX_CODES_DEL_INDX" ON "ADM_TX_CODES" ("IS_DELETED") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index PK_ADM_TX_LINKS
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_ADM_TX_LINKS" ON "ADM_TX_LINKS" ("TX_TYPE_ID", "TX_CODE_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index PK_ADM_TX_TYPES
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_ADM_TX_TYPES" ON "ADM_TX_TYPES" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index ADM_TX_TYPES_DEF_INDX
--------------------------------------------------------

  CREATE INDEX "ADM_TX_TYPES_DEF_INDX" ON "ADM_TX_TYPES" ("IS_DEFAULT") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index ADM_TX_TYPES_DEL_INDX
--------------------------------------------------------

  CREATE INDEX "ADM_TX_TYPES_DEL_INDX" ON "ADM_TX_TYPES" ("IS_DELETED") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index ADM_USERS_THEME_INDX
--------------------------------------------------------

  CREATE INDEX "ADM_USERS_THEME_INDX" ON "ADM_USERS" ("THEME_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index ADM_USERS_UK1
--------------------------------------------------------

  CREATE UNIQUE INDEX "ADM_USERS_UK1" ON "ADM_USERS" ("NT_ACCOUNT") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index ADM_USERS_ACTIVE_INDX
--------------------------------------------------------

  CREATE INDEX "ADM_USERS_ACTIVE_INDX" ON "ADM_USERS" ("IS_ACTIVE") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index ADM_USERS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ADM_USERS_PK" ON "ADM_USERS" ("USER_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index ADM_USERS_PROFILE_INDX
--------------------------------------------------------

  CREATE INDEX "ADM_USERS_PROFILE_INDX" ON "ADM_USERS" ("PROFILE_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index DSS_COLUMNS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "DSS_COLUMNS_PK" ON "DSS_COLUMNS" ("DSS_PAGENAME", "COLUMN_NAME") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index DYN_PAGES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "DYN_PAGES_PK" ON "DYN_PAGES" ("PAGE_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index DYN_PAGES_STEPS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "DYN_PAGES_STEPS_PK" ON "DYN_PAGES_STEPS" ("STEP_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index DYN_STEP_SP_CONFIGURATION_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "DYN_STEP_SP_CONFIGURATION_PK" ON "DYN_STEP_SP_CONFIGURATION" ("CONFIG_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index DYN_STEP_SP_CURSOR_MAPPING_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "DYN_STEP_SP_CURSOR_MAPPING_PK" ON "DYN_STEP_SP_CURSOR_MAPPING" ("MAPPING_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index DYN_STEP_SP_PARAMETERS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "DYN_STEP_SP_PARAMETERS_PK" ON "DYN_STEP_SP_PARAMETERS" ("PARAM_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index ADM_DATASOURCES_NODES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ADM_DATASOURCES_NODES_PK" ON "FLEX_SHARE_HISTORY_NODES" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index H_NOTEPAD_ENTRIES_MSISDN_NDC
--------------------------------------------------------

  CREATE INDEX "H_NOTEPAD_ENTRIES_MSISDN_NDC" ON "H_NOTEPAD_ENTRIES" ("MSISDN") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index PK_LK_ACCOUNT_FLAGS
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_LK_ACCOUNT_FLAGS" ON "LK_ACCOUNT_FLAGS" ("ACCOUNT_FLAG_CODE") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index PK_LK_ACCUMULATORS
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_LK_ACCUMULATORS" ON "LK_ACCUMULATORS" ("ACC_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index PK_LK_BOOLEAN
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_LK_BOOLEAN" ON "LK_BOOLEAN" ("BOOLEAN_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index PK_LK_BT_TRANSACTION_STATUS
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_LK_BT_TRANSACTION_STATUS" ON "LK_BT_TRANSACTION_STATUS" ("CODE") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index LK_CC_FEATURES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "LK_CC_FEATURES_PK" ON "LK_CC_FEATURES" ("CC_FEATURE_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
   ;
--------------------------------------------------------
--  DDL for Index LK_DATASOURCE_TYPE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "LK_DATASOURCE_TYPE_PK" ON "LK_DATASOURCE_TYPE" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index PK_LK_DED_ACCOUNTS
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_LK_DED_ACCOUNTS" ON "LK_DED_ACCOUNTS" ("DA_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index ADM_DIS_CODE_DEL_INDX
--------------------------------------------------------

  CREATE INDEX "ADM_DIS_CODE_DEL_INDX" ON "LK_DISCONNECTION_CODES" ("IS_DELETED") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index PK_LK_DISCONNECTION_CODES
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_LK_DISCONNECTION_CODES" ON "LK_DISCONNECTION_CODES" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index LK_FEATURES_NEW_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "LK_FEATURES_NEW_PK" ON "LK_FEATURES" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index LK_FLEX_OFFER_TYPE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "LK_FLEX_OFFER_TYPE_PK" ON "LK_FLEX_OFFER_TYPE" ("TYPE_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index LK_FLEX_OFFERS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "LK_FLEX_OFFERS_PK" ON "LK_FLEX_OFFERS" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index LK_MENUS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "LK_MENUS_PK" ON "LK_MENUS" ("MENU_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index LK_OFFERS_STATES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "LK_OFFERS_STATES_PK" ON "LK_OFFERS_STATES" ("STATE_ID", "STATE_DESC") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index LK_OFFERS_TYPES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "LK_OFFERS_TYPES_PK" ON "LK_OFFERS_TYPES" ("TYPE_ID", "TYPE_DESC") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index LK_PAM_PERIOD_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "LK_PAM_PERIOD_PK" ON "LK_PAM_PERIOD" ("ID", "NAME") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index LK_PAM_PRIORITY_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "LK_PAM_PRIORITY_PK" ON "LK_PAM_PRIORITY" ("ID", "NAME") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index LK_PAM_SCHEDULES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "LK_PAM_SCHEDULES_PK" ON "LK_PAM_SCHEDULES" ("ID", "NAME") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index LK_PAM_SERVICES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "LK_PAM_SERVICES_PK" ON "LK_PAM_SERVICES" ("ID", "NAME") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index PK_LK_REFILL_PAYMENT_PROFILE
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_LK_REFILL_PAYMENT_PROFILE" ON "LK_REFILL_PAYMENT_PROFILE" ("REFILL_PROFILE_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index LK_REGIONS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "LK_REGIONS_PK" ON "LK_REGIONS" ("REGION") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index LK_REGIONS_U01
--------------------------------------------------------

  CREATE UNIQUE INDEX "LK_REGIONS_U01" ON "LK_REGIONS" ("REGION_DESCRIPTION") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index SMS_ACTION_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SMS_ACTION_PK" ON "LK_SMS_ACTION" ("SMS_ACTION_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index GATEWAYS_STATUS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "GATEWAYS_STATUS_PK" ON "LK_SMS_GATEWAYS_STATUS" ("STATUS_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index SMSPRIOITIES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SMSPRIOITIES_PK" ON "LK_SMS_PRIORITIES" ("PRIORITY_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index SMS_PARAM_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SMS_PARAM_PK" ON "LK_SMS_TEMPLATE_PARAM" ("PARAMETER_ID", "SMS_ACTION_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index LK_VOUCHER_SERVERS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "LK_VOUCHER_SERVERS_PK" ON "LK_VOUCHER_SERVERS" ("SERVER_INDEX", "VOUCHER_SERIAL_LENGTH") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index SMSERROR_PK1
--------------------------------------------------------

  CREATE UNIQUE INDEX "SMSERROR_PK1" ON "SMS_ERROR" ("SMS_ERROR_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index SMSQ_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SMSQ_PK" ON "SMS_Q" ("SMS_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index SYS_C0032570
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0032570" ON "TEST_VOUCHERS" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index SYS_C0032571
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0032571" ON "TEST_VOUCHERS" ("SERIAL_NUMBER") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index SYS_C0032572
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0032572" ON "TEST_VOUCHERS" ("REQUEST_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index SYS_C0033125
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0033125" ON "TESTT_VOUCHERS" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index SYS_C0033126
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0033126" ON "TESTT_VOUCHERS" ("REQUEST_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index SYS_C0033127
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0033127" ON "TESTT_VOUCHERS" ("AMOUNT") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index TX_CALL_REASONS_USER_ID_IDX
--------------------------------------------------------

  CREATE INDEX "TX_CALL_REASONS_USER_ID_IDX" ON "TX_CALL_REASONS" ("USER_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT) LOCAL
 (PARTITION "P0" NOCOMPRESS 
PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
 ( SUBPARTITION "S0" 
  STORAGE(
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   , 
  SUBPARTITION "S1" 
  STORAGE(
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   , 
  SUBPARTITION "S2" 
  STORAGE(
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   , 
  SUBPARTITION "S3" 
  STORAGE(
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   , 
  SUBPARTITION "S4" 
  STORAGE(
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   , 
  SUBPARTITION "S5" 
  STORAGE(
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   , 
  SUBPARTITION "S6" 
  STORAGE(
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   , 
  SUBPARTITION "S7" 
  STORAGE(
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   , 
  SUBPARTITION "S8" 
  STORAGE(
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   , 
  SUBPARTITION "S9" 
  STORAGE(
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ) ) ;
--------------------------------------------------------
--  DDL for Index PK_TX_LOGIN
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_TX_LOGIN" ON "TX_LOGIN" ("TX_LOGIN_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index TX_LOGIN_UID_INDX
--------------------------------------------------------

  CREATE INDEX "TX_LOGIN_UID_INDX" ON "TX_LOGIN" ("USER_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
   ;
--------------------------------------------------------
--  DDL for Index TX_USED_VOUCHERS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "TX_USED_VOUCHERS_PK" ON "TX_USED_VOUCHERS" ("VOUCHER_SERIAL_NUMBER") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
   ;
--------------------------------------------------------
--  Constraints for Table ADM_ACCOUNT_GROUPS
--------------------------------------------------------

  ALTER TABLE "ADM_ACCOUNT_GROUPS" MODIFY ("GROUP_ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_ACCOUNT_GROUPS" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "ADM_ACCOUNT_GROUPS" MODIFY ("IS_DELETED" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ADM_AG_BITS_SC_DESCRIPTION
--------------------------------------------------------

  ALTER TABLE "ADM_AG_BITS_SC_DESCRIPTION" MODIFY ("BIT_POSITION" NOT NULL ENABLE);
  ALTER TABLE "ADM_AG_BITS_SC_DESCRIPTION" MODIFY ("SERVICE_CLASS_ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_AG_BITS_SC_DESCRIPTION" MODIFY ("DESCRIPTION" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ADM_BUSINESS_PLANS
--------------------------------------------------------

  ALTER TABLE "ADM_BUSINESS_PLANS" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_BUSINESS_PLANS" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "ADM_BUSINESS_PLANS" MODIFY ("CODE" NOT NULL ENABLE);
  ALTER TABLE "ADM_BUSINESS_PLANS" MODIFY ("SERVICE_CLASS_ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_BUSINESS_PLANS" MODIFY ("HLR_ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_BUSINESS_PLANS" MODIFY ("IS_DELETED" NOT NULL ENABLE);
  ALTER TABLE "ADM_BUSINESS_PLANS" ADD CONSTRAINT "ADM_BUSINESS_PLANS_PK" PRIMARY KEY ("ID")
  USING INDEX "PK_ADM_BUSINESS_PLANS"  ENABLE;
  ALTER TABLE "ADM_BUSINESS_PLANS" ADD CONSTRAINT "ADM_BUSINESS_PLANS_UK1" UNIQUE ("NAME", "CODE")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
    ENABLE;
--------------------------------------------------------
--  Constraints for Table ADM_FAF_BLACK_RESTRICTIONS
--------------------------------------------------------

  ALTER TABLE "ADM_FAF_BLACK_RESTRICTIONS" MODIFY ("RESTRICTION_ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_FAF_BLACK_RESTRICTIONS" MODIFY ("NUMBER_PATTERN" NOT NULL ENABLE);
  ALTER TABLE "ADM_FAF_BLACK_RESTRICTIONS" MODIFY ("DESCRIPTION" NOT NULL ENABLE);
  ALTER TABLE "ADM_FAF_BLACK_RESTRICTIONS" MODIFY ("IS_DELETED" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ADM_FAF_INDICATORS
--------------------------------------------------------

  ALTER TABLE "ADM_FAF_INDICATORS" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_FAF_INDICATORS" MODIFY ("IS_DELETED" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ADM_FAF_PLANS
--------------------------------------------------------

  ALTER TABLE "ADM_FAF_PLANS" MODIFY ("PLAN_ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_FAF_PLANS" MODIFY ("INDICATOR_ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_FAF_PLANS" MODIFY ("IS_DELETED" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ADM_FAF_WHITE_RESTRICTIONS
--------------------------------------------------------

  ALTER TABLE "ADM_FAF_WHITE_RESTRICTIONS" MODIFY ("RESTRICTION_ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_FAF_WHITE_RESTRICTIONS" MODIFY ("NUMBER_PATTERN" NOT NULL ENABLE);
  ALTER TABLE "ADM_FAF_WHITE_RESTRICTIONS" MODIFY ("DESCRIPTION" NOT NULL ENABLE);
  ALTER TABLE "ADM_FAF_WHITE_RESTRICTIONS" MODIFY ("IS_DELETED" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ADM_HLR_PROFILES
--------------------------------------------------------

  ALTER TABLE "ADM_HLR_PROFILES" MODIFY ("HLR_ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_HLR_PROFILES" MODIFY ("CODE" NOT NULL ENABLE);
  ALTER TABLE "ADM_HLR_PROFILES" MODIFY ("NAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ADM_MARQUEE_DATA
--------------------------------------------------------

  ALTER TABLE "ADM_MARQUEE_DATA" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_MARQUEE_DATA" MODIFY ("CREATION_DATE" NOT NULL ENABLE);
  ALTER TABLE "ADM_MARQUEE_DATA" MODIFY ("TITLE" NOT NULL ENABLE);
  ALTER TABLE "ADM_MARQUEE_DATA" MODIFY ("DESCRIPTION" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ADM_OFFERS
--------------------------------------------------------

  ALTER TABLE "ADM_OFFERS" MODIFY ("OFFER_ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_OFFERS" MODIFY ("OFFER_DESC" NOT NULL ENABLE);
  ALTER TABLE "ADM_OFFERS" MODIFY ("IS_DROP_DOWN_ENABLED" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ADM_PROFILE_FEATURES
--------------------------------------------------------

  ALTER TABLE "ADM_PROFILE_FEATURES" MODIFY ("PROFILE_ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_PROFILE_FEATURES" MODIFY ("FEATURE_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ADM_PROFILE_MENUS
--------------------------------------------------------

  ALTER TABLE "ADM_PROFILE_MENUS" MODIFY ("PROFILE_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ADM_PROFILE_MONETARY_LIMIT
--------------------------------------------------------

  ALTER TABLE "ADM_PROFILE_MONETARY_LIMIT" MODIFY ("PROFILE_ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_PROFILE_MONETARY_LIMIT" MODIFY ("LIMIT_ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_PROFILE_MONETARY_LIMIT" MODIFY ("VALUE" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ADM_PROFILE_SERVICE_CLASS
--------------------------------------------------------

  ALTER TABLE "ADM_PROFILE_SERVICE_CLASS" MODIFY ("SERVICE_CLASS_ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_PROFILE_SERVICE_CLASS" MODIFY ("PROFILE_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ADM_PROFILE_SOB
--------------------------------------------------------

  ALTER TABLE "ADM_PROFILE_SOB" MODIFY ("PROFILE_ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_PROFILE_SOB" MODIFY ("SERVICE_OFFERING_BITS" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ADM_PROFILES
--------------------------------------------------------

  ALTER TABLE "ADM_PROFILES" ADD CONSTRAINT "ADM_PROFILES_UK1" UNIQUE ("PROFILE_NAME")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
    ENABLE;
  ALTER TABLE "ADM_PROFILES" MODIFY ("PROFILE_ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_PROFILES" MODIFY ("PROFILE_NAME" NOT NULL ENABLE);
  ALTER TABLE "ADM_PROFILES" MODIFY ("IS_DELETED" NOT NULL ENABLE);
  ALTER TABLE "ADM_PROFILES" MODIFY ("IS_FOOTPRINT_REQUIRED" NOT NULL ENABLE);
  ALTER TABLE "ADM_PROFILES" ADD CONSTRAINT "ADM_PROFILES_PK" PRIMARY KEY ("PROFILE_ID")
  USING INDEX "PK_ADM_PROFILES"  ENABLE;
--------------------------------------------------------
--  Constraints for Table ADM_PROMOTION_PLANS
--------------------------------------------------------

  ALTER TABLE "ADM_PROMOTION_PLANS" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_PROMOTION_PLANS" MODIFY ("PLAN_ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_PROMOTION_PLANS" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "ADM_PROMOTION_PLANS" MODIFY ("IS_DEFAULT" NOT NULL ENABLE);
  ALTER TABLE "ADM_PROMOTION_PLANS" MODIFY ("IS_DELETED" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ADM_SERVICE_CLASS_ACC
--------------------------------------------------------

  ALTER TABLE "ADM_SERVICE_CLASS_ACC" MODIFY ("SERVICE_CLASS_ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_SERVICE_CLASS_ACC" MODIFY ("ACC_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ADM_SERVICE_CLASS_DA
--------------------------------------------------------

  ALTER TABLE "ADM_SERVICE_CLASS_DA" MODIFY ("SERVICE_CLASS_ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_SERVICE_CLASS_DA" MODIFY ("DA_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ADM_SERVICE_CLASSES
--------------------------------------------------------

  ALTER TABLE "ADM_SERVICE_CLASSES" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "ADM_SERVICE_CLASSES" MODIFY ("IS_DELETED" NOT NULL ENABLE);
  ALTER TABLE "ADM_SERVICE_CLASSES" MODIFY ("CODE" NOT NULL ENABLE);
  ALTER TABLE "ADM_SERVICE_CLASSES" ADD CONSTRAINT "ADM_SERVICE_CLASSES_PK" PRIMARY KEY ("CODE")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
    ENABLE;
--------------------------------------------------------
--  Constraints for Table ADM_SERVICE_OFFERING_BITS
--------------------------------------------------------

  ALTER TABLE "ADM_SERVICE_OFFERING_BITS" MODIFY ("BIT_POSITION" NOT NULL ENABLE);
  ALTER TABLE "ADM_SERVICE_OFFERING_BITS" MODIFY ("BIT_NAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ADM_SERVICE_OFFERING_PLANS
--------------------------------------------------------

  ALTER TABLE "ADM_SERVICE_OFFERING_PLANS" ADD CONSTRAINT "ADM_SERVICE_OFFERING_PLANS_UK1" UNIQUE ("NAME", "PLAN_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
    ENABLE;
  ALTER TABLE "ADM_SERVICE_OFFERING_PLANS" MODIFY ("PLAN_ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_SERVICE_OFFERING_PLANS" MODIFY ("IS_DELETED" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ADM_SERVICE_OFFG_PLAN_BITS
--------------------------------------------------------

  ALTER TABLE "ADM_SERVICE_OFFG_PLAN_BITS" MODIFY ("PLAN_ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_SERVICE_OFFG_PLAN_BITS" MODIFY ("BIT_POSITION" NOT NULL ENABLE);
  ALTER TABLE "ADM_SERVICE_OFFG_PLAN_BITS" MODIFY ("IS_ENABLED" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ADM_SMS_GATEWAYS
--------------------------------------------------------

  ALTER TABLE "ADM_SMS_GATEWAYS" MODIFY ("APP_ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_SMS_GATEWAYS" MODIFY ("ORIG_MSISDN" NOT NULL ENABLE);
  ALTER TABLE "ADM_SMS_GATEWAYS" ADD CONSTRAINT "SMSGATEWAY_PK" PRIMARY KEY ("ID")
  USING INDEX "SMSGATEWAY_PK"  ENABLE;
  ALTER TABLE "ADM_SMS_GATEWAYS" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_SMS_GATEWAYS" MODIFY ("PRC_NAME" NOT NULL ENABLE);
  ALTER TABLE "ADM_SMS_GATEWAYS" MODIFY ("PRC_SHARE" NOT NULL ENABLE);
  ALTER TABLE "ADM_SMS_GATEWAYS" MODIFY ("ENTRY_DATE" NOT NULL ENABLE);
  ALTER TABLE "ADM_SMS_GATEWAYS" MODIFY ("STATUS_ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_SMS_GATEWAYS" MODIFY ("IS_SHARED" NOT NULL ENABLE);
  ALTER TABLE "ADM_SMS_GATEWAYS" MODIFY ("MSG_TYPE" NOT NULL ENABLE);
  ALTER TABLE "ADM_SMS_GATEWAYS" MODIFY ("ORIG_TYPE" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ADM_SMS_TEMPLATE
--------------------------------------------------------

  ALTER TABLE "ADM_SMS_TEMPLATE" ADD CONSTRAINT "SMS_TEMPLT_PK" PRIMARY KEY ("ID")
  USING INDEX "SMS_TEMPLT_PK"  ENABLE;
  ALTER TABLE "ADM_SMS_TEMPLATE" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_SMS_TEMPLATE" MODIFY ("TEMPLATE_ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_SMS_TEMPLATE" MODIFY ("TEMPLATE_TEXT" NOT NULL ENABLE);
  ALTER TABLE "ADM_SMS_TEMPLATE" MODIFY ("LANGUAGE_ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_SMS_TEMPLATE" MODIFY ("TEMPLATE_STATUS" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ADM_SO_BITS_SC_DESCRIPTION
--------------------------------------------------------

  ALTER TABLE "ADM_SO_BITS_SC_DESCRIPTION" MODIFY ("BIT_POSITION" NOT NULL ENABLE);
  ALTER TABLE "ADM_SO_BITS_SC_DESCRIPTION" MODIFY ("SERVICE_CLASS_ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_SO_BITS_SC_DESCRIPTION" MODIFY ("DESCRIPTION" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ADM_SYSTEM_PROPERTIES
--------------------------------------------------------

  ALTER TABLE "ADM_SYSTEM_PROPERTIES" MODIFY ("APPLICATION" NOT NULL ENABLE);
  ALTER TABLE "ADM_SYSTEM_PROPERTIES" MODIFY ("PROFILE" NOT NULL ENABLE);
  ALTER TABLE "ADM_SYSTEM_PROPERTIES" MODIFY ("LABEL" NOT NULL ENABLE);
  ALTER TABLE "ADM_SYSTEM_PROPERTIES" MODIFY ("VALUE" NOT NULL ENABLE);
  ALTER TABLE "ADM_SYSTEM_PROPERTIES" MODIFY ("CODE" NOT NULL ENABLE);
  ALTER TABLE "ADM_SYSTEM_PROPERTIES" MODIFY ("DESCRIPTION" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ADM_TX_CODES
--------------------------------------------------------

  ALTER TABLE "ADM_TX_CODES" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_TX_CODES" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "ADM_TX_CODES" MODIFY ("VALUE" NOT NULL ENABLE);
  ALTER TABLE "ADM_TX_CODES" MODIFY ("IS_DEFAULT" NOT NULL ENABLE);
  ALTER TABLE "ADM_TX_CODES" MODIFY ("IS_DELETED" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ADM_TX_LINKS
--------------------------------------------------------

  ALTER TABLE "ADM_TX_LINKS" MODIFY ("TX_TYPE_ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_TX_LINKS" MODIFY ("TX_CODE_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ADM_TX_TYPES
--------------------------------------------------------

  ALTER TABLE "ADM_TX_TYPES" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_TX_TYPES" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "ADM_TX_TYPES" MODIFY ("VALUE" NOT NULL ENABLE);
  ALTER TABLE "ADM_TX_TYPES" MODIFY ("IS_DEFAULT" NOT NULL ENABLE);
  ALTER TABLE "ADM_TX_TYPES" MODIFY ("IS_DELETED" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ADM_USERS
--------------------------------------------------------

  ALTER TABLE "ADM_USERS" MODIFY ("USER_ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_USERS" MODIFY ("NT_ACCOUNT" NOT NULL ENABLE);
  ALTER TABLE "ADM_USERS" MODIFY ("PROFILE_ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_USERS" MODIFY ("IS_DELETED" NOT NULL ENABLE);
  ALTER TABLE "ADM_USERS" MODIFY ("IS_ACTIVE" NOT NULL ENABLE);
  ALTER TABLE "ADM_USERS" MODIFY ("SESSION_COUNTER" NOT NULL ENABLE);
  ALTER TABLE "ADM_USERS" MODIFY ("THEME_ID" NOT NULL ENABLE);
  ALTER TABLE "ADM_USERS" ADD CONSTRAINT "ADM_USERS_PK" PRIMARY KEY ("USER_ID")
  USING INDEX "ADM_USERS_PK"  ENABLE;
  ALTER TABLE "ADM_USERS" ADD CONSTRAINT "ADM_USERS_UK1" UNIQUE ("NT_ACCOUNT")
  USING INDEX "ADM_USERS_UK1"  ENABLE;
--------------------------------------------------------
--  Constraints for Table BK_SYSTEM_PROPERTIES
--------------------------------------------------------

  ALTER TABLE "BK_SYSTEM_PROPERTIES" MODIFY ("APPLICATION" NOT NULL ENABLE);
  ALTER TABLE "BK_SYSTEM_PROPERTIES" MODIFY ("PROFILE" NOT NULL ENABLE);
  ALTER TABLE "BK_SYSTEM_PROPERTIES" MODIFY ("LABEL" NOT NULL ENABLE);
  ALTER TABLE "BK_SYSTEM_PROPERTIES" MODIFY ("VALUE" NOT NULL ENABLE);
  ALTER TABLE "BK_SYSTEM_PROPERTIES" MODIFY ("CODE" NOT NULL ENABLE);
  ALTER TABLE "BK_SYSTEM_PROPERTIES" MODIFY ("DESCRIPTION" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table DSS_COLUMNS
--------------------------------------------------------

  ALTER TABLE "DSS_COLUMNS" MODIFY ("DSS_PAGENAME" NOT NULL ENABLE);
  ALTER TABLE "DSS_COLUMNS" MODIFY ("COLUMN_NAME" NOT NULL ENABLE);
  ALTER TABLE "DSS_COLUMNS" MODIFY ("DISPLAYNAME" NOT NULL ENABLE);
  ALTER TABLE "DSS_COLUMNS" ADD CONSTRAINT "DSS_COLUMNS_PK" PRIMARY KEY ("DSS_PAGENAME", "COLUMN_NAME")
  USING INDEX "DSS_COLUMNS_PK"  ENABLE;
--------------------------------------------------------
--  Constraints for Table DSS_NODES
--------------------------------------------------------

  ALTER TABLE "DSS_NODES" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "DSS_NODES" MODIFY ("ADDRESS" NOT NULL ENABLE);
  ALTER TABLE "DSS_NODES" MODIFY ("USER_NAME" NOT NULL ENABLE);
  ALTER TABLE "DSS_NODES" MODIFY ("PASSWORD" NOT NULL ENABLE);
  ALTER TABLE "DSS_NODES" MODIFY ("NUMBER_OF_SESSIONS" NOT NULL ENABLE);
  ALTER TABLE "DSS_NODES" MODIFY ("CONCURRENT_CALLS" NOT NULL ENABLE);
  ALTER TABLE "DSS_NODES" MODIFY ("CONNECTION_TIMEOUT" NOT NULL ENABLE);
  ALTER TABLE "DSS_NODES" MODIFY ("EXTRA_CONF" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table DYN_PAGES
--------------------------------------------------------

  ALTER TABLE "DYN_PAGES" MODIFY ("PRIVILEGE_ID" NOT NULL ENABLE);
  ALTER TABLE "DYN_PAGES" MODIFY ("PRIVILEGE_NAME" NOT NULL ENABLE);
  ALTER TABLE "DYN_PAGES" MODIFY ("PAGE_ID" NOT NULL ENABLE);
  ALTER TABLE "DYN_PAGES" MODIFY ("PAGE_NAME" NOT NULL ENABLE);
  ALTER TABLE "DYN_PAGES" ADD CONSTRAINT "DYN_PAGES_PK" PRIMARY KEY ("PAGE_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
    ENABLE;
  ALTER TABLE "DYN_PAGES" MODIFY ("IS_DELETED" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table DYN_PAGES_STEPS
--------------------------------------------------------

  ALTER TABLE "DYN_PAGES_STEPS" MODIFY ("STEP_ID" NOT NULL ENABLE);
  ALTER TABLE "DYN_PAGES_STEPS" MODIFY ("PAGE_ID" NOT NULL ENABLE);
  ALTER TABLE "DYN_PAGES_STEPS" MODIFY ("STEP_TYPE" NOT NULL ENABLE);
  ALTER TABLE "DYN_PAGES_STEPS" MODIFY ("STEP_NAME" NOT NULL ENABLE);
  ALTER TABLE "DYN_PAGES_STEPS" MODIFY ("STEP_ORDER" NOT NULL ENABLE);
  ALTER TABLE "DYN_PAGES_STEPS" ADD CONSTRAINT "DYN_PAGES_STEPS_PK" PRIMARY KEY ("STEP_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
    ENABLE;
  ALTER TABLE "DYN_PAGES_STEPS" MODIFY ("IS_DELETED" NOT NULL ENABLE);
  ALTER TABLE "DYN_PAGES_STEPS" MODIFY ("IS_HIDDEN" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table DYN_STEP_HTTP_CONFIGURATION
--------------------------------------------------------

  ALTER TABLE "DYN_STEP_HTTP_CONFIGURATION" MODIFY ("CONFIG_ID" NOT NULL ENABLE);
  ALTER TABLE "DYN_STEP_HTTP_CONFIGURATION" MODIFY ("STEP_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table DYN_STEP_HTTP_PARAMETERS
--------------------------------------------------------

  ALTER TABLE "DYN_STEP_HTTP_PARAMETERS" MODIFY ("PARAM_ID" NOT NULL ENABLE);
  ALTER TABLE "DYN_STEP_HTTP_PARAMETERS" MODIFY ("CONFIG_ID" NOT NULL ENABLE);
  ALTER TABLE "DYN_STEP_HTTP_PARAMETERS" MODIFY ("PARAMETER_NAME" NOT NULL ENABLE);
  ALTER TABLE "DYN_STEP_HTTP_PARAMETERS" MODIFY ("PARAMETER_DATA_TYPE" NOT NULL ENABLE);
  ALTER TABLE "DYN_STEP_HTTP_PARAMETERS" MODIFY ("PARAMETER_TYPE" NOT NULL ENABLE);
  ALTER TABLE "DYN_STEP_HTTP_PARAMETERS" MODIFY ("PARAMETER_ORDER" NOT NULL ENABLE);
  ALTER TABLE "DYN_STEP_HTTP_PARAMETERS" MODIFY ("IS_DELETED" NOT NULL ENABLE);
  ALTER TABLE "DYN_STEP_HTTP_PARAMETERS" MODIFY ("DISPLAY_ORDER" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table DYN_STEP_SP_CONFIGURATION
--------------------------------------------------------

  ALTER TABLE "DYN_STEP_SP_CONFIGURATION" MODIFY ("CONFIG_ID" NOT NULL ENABLE);
  ALTER TABLE "DYN_STEP_SP_CONFIGURATION" MODIFY ("STEP_ID" NOT NULL ENABLE);
  ALTER TABLE "DYN_STEP_SP_CONFIGURATION" MODIFY ("DB_URL" NOT NULL ENABLE);
  ALTER TABLE "DYN_STEP_SP_CONFIGURATION" MODIFY ("DB_USERNAME" NOT NULL ENABLE);
  ALTER TABLE "DYN_STEP_SP_CONFIGURATION" MODIFY ("DB_PASSWORD" NOT NULL ENABLE);
  ALTER TABLE "DYN_STEP_SP_CONFIGURATION" MODIFY ("PROCEDURE_NAME" NOT NULL ENABLE);
  ALTER TABLE "DYN_STEP_SP_CONFIGURATION" MODIFY ("MAX_RECORDS" NOT NULL ENABLE);
  ALTER TABLE "DYN_STEP_SP_CONFIGURATION" MODIFY ("SUCCESS_CODE" NOT NULL ENABLE);
  ALTER TABLE "DYN_STEP_SP_CONFIGURATION" ADD CONSTRAINT "DYN_STEP_SP_CONFIGURATION_PK" PRIMARY KEY ("CONFIG_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
    ENABLE;
  ALTER TABLE "DYN_STEP_SP_CONFIGURATION" MODIFY ("IS_DELETED" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table DYN_STEP_SP_CURSOR_MAPPING
--------------------------------------------------------

  ALTER TABLE "DYN_STEP_SP_CURSOR_MAPPING" MODIFY ("MAPPING_ID" NOT NULL ENABLE);
  ALTER TABLE "DYN_STEP_SP_CURSOR_MAPPING" MODIFY ("PARAMETER_ID" NOT NULL ENABLE);
  ALTER TABLE "DYN_STEP_SP_CURSOR_MAPPING" ADD CONSTRAINT "DYN_STEP_SP_CURSOR_MAPPING_PK" PRIMARY KEY ("MAPPING_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
    ENABLE;
  ALTER TABLE "DYN_STEP_SP_CURSOR_MAPPING" MODIFY ("IS_DELETED" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table DYN_STEP_SP_PARAMETERS
--------------------------------------------------------

  ALTER TABLE "DYN_STEP_SP_PARAMETERS" MODIFY ("DISPLAY_ORDER" NOT NULL ENABLE);
  ALTER TABLE "DYN_STEP_SP_PARAMETERS" MODIFY ("PARAM_ID" NOT NULL ENABLE);
  ALTER TABLE "DYN_STEP_SP_PARAMETERS" MODIFY ("CONFIG_ID" NOT NULL ENABLE);
  ALTER TABLE "DYN_STEP_SP_PARAMETERS" MODIFY ("PARAMETER_NAME" NOT NULL ENABLE);
  ALTER TABLE "DYN_STEP_SP_PARAMETERS" MODIFY ("PARAMETER_DATA_TYPE" NOT NULL ENABLE);
  ALTER TABLE "DYN_STEP_SP_PARAMETERS" MODIFY ("PARAMETER_TYPE" NOT NULL ENABLE);
  ALTER TABLE "DYN_STEP_SP_PARAMETERS" MODIFY ("PARAMETER_ORDER" NOT NULL ENABLE);
  ALTER TABLE "DYN_STEP_SP_PARAMETERS" ADD CONSTRAINT "DYN_STEP_SP_PARAMETERS_PK" PRIMARY KEY ("PARAM_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
    ENABLE;
  ALTER TABLE "DYN_STEP_SP_PARAMETERS" MODIFY ("IS_DELETED" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table FLEX_SHARE_HISTORY_NODES
--------------------------------------------------------

  ALTER TABLE "FLEX_SHARE_HISTORY_NODES" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "FLEX_SHARE_HISTORY_NODES" MODIFY ("USERNAME" NOT NULL ENABLE);
  ALTER TABLE "FLEX_SHARE_HISTORY_NODES" MODIFY ("PASSWORD" NOT NULL ENABLE);
  ALTER TABLE "FLEX_SHARE_HISTORY_NODES" ADD CONSTRAINT "ADM_DATASOURCES_NODES_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
    ENABLE;
--------------------------------------------------------
--  Constraints for Table H_NOTEPAD_ENTRIES
--------------------------------------------------------

  ALTER TABLE "H_NOTEPAD_ENTRIES" MODIFY ("ENTRY_DATE" NOT NULL ENABLE);
  ALTER TABLE "H_NOTEPAD_ENTRIES" MODIFY ("MSISDN" NOT NULL ENABLE);
  ALTER TABLE "H_NOTEPAD_ENTRIES" MODIFY ("MSISDN_MOD_X" NOT NULL ENABLE);
  ALTER TABLE "H_NOTEPAD_ENTRIES" MODIFY ("USER_ID" NOT NULL ENABLE);
  ALTER TABLE "H_NOTEPAD_ENTRIES" MODIFY ("NOTEPAD_ENTRY" NOT NULL ENABLE);
  ALTER TABLE "H_NOTEPAD_ENTRIES" MODIFY ("USER_NAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LK_ACCOUNT_FLAGS
--------------------------------------------------------

  ALTER TABLE "LK_ACCOUNT_FLAGS" MODIFY ("ACCOUNT_FLAG_CODE" NOT NULL ENABLE);
  ALTER TABLE "LK_ACCOUNT_FLAGS" MODIFY ("ACCOUNT_FLAG_NAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LK_ACCUMULATORS
--------------------------------------------------------

  ALTER TABLE "LK_ACCUMULATORS" MODIFY ("ACC_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LK_AIR_SERVERS
--------------------------------------------------------

  ALTER TABLE "LK_AIR_SERVERS" MODIFY ("URL" NOT NULL ENABLE);
  ALTER TABLE "LK_AIR_SERVERS" MODIFY ("AGENT_NAME" NOT NULL ENABLE);
  ALTER TABLE "LK_AIR_SERVERS" MODIFY ("HOST" NOT NULL ENABLE);
  ALTER TABLE "LK_AIR_SERVERS" MODIFY ("AUTHORIZATION" NOT NULL ENABLE);
  ALTER TABLE "LK_AIR_SERVERS" MODIFY ("IS_DOWN" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LK_AIR_SERVERS_BK
--------------------------------------------------------

  ALTER TABLE "LK_AIR_SERVERS_BK" MODIFY ("URL" NOT NULL ENABLE);
  ALTER TABLE "LK_AIR_SERVERS_BK" MODIFY ("AGENT_NAME" NOT NULL ENABLE);
  ALTER TABLE "LK_AIR_SERVERS_BK" MODIFY ("HOST" NOT NULL ENABLE);
  ALTER TABLE "LK_AIR_SERVERS_BK" MODIFY ("AUTHORIZATION" NOT NULL ENABLE);
  ALTER TABLE "LK_AIR_SERVERS_BK" MODIFY ("IS_DOWN" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LK_BALANCE_DISPUTE_DETAILS_CONFIGURATION
--------------------------------------------------------

  ALTER TABLE "LK_BALANCE_DISPUTE_DETAILS_CONFIGURATION" MODIFY ("COLUMN_NAME" NOT NULL ENABLE);
  ALTER TABLE "LK_BALANCE_DISPUTE_DETAILS_CONFIGURATION" MODIFY ("DISPLAY_NAME" NOT NULL ENABLE);
  ALTER TABLE "LK_BALANCE_DISPUTE_DETAILS_CONFIGURATION" MODIFY ("COLUMN_ORDER" NOT NULL ENABLE);
  ALTER TABLE "LK_BALANCE_DISPUTE_DETAILS_CONFIGURATION" MODIFY ("PRIVILEGE_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LK_BOOLEAN
--------------------------------------------------------

  ALTER TABLE "LK_BOOLEAN" MODIFY ("BOOLEAN_ID" NOT NULL ENABLE);
  ALTER TABLE "LK_BOOLEAN" MODIFY ("BOOLEAN_VALUE" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LK_BT_TRANSACTION_STATUS
--------------------------------------------------------

  ALTER TABLE "LK_BT_TRANSACTION_STATUS" MODIFY ("CODE" NOT NULL ENABLE);
  ALTER TABLE "LK_BT_TRANSACTION_STATUS" MODIFY ("NAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LK_CC_FEATURES
--------------------------------------------------------

  ALTER TABLE "LK_CC_FEATURES" MODIFY ("CC_FEATURE_ID" NOT NULL ENABLE);
  ALTER TABLE "LK_CC_FEATURES" MODIFY ("CC_FEATURE_NAME" NOT NULL ENABLE);
  ALTER TABLE "LK_CC_FEATURES" ADD CONSTRAINT "LK_CC_FEATURES_PK" PRIMARY KEY ("CC_FEATURE_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
    ENABLE;
--------------------------------------------------------
--  Constraints for Table LK_DATASOURCE_TYPE
--------------------------------------------------------

  ALTER TABLE "LK_DATASOURCE_TYPE" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "LK_DATASOURCE_TYPE" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "LK_DATASOURCE_TYPE" ADD CONSTRAINT "LK_DATASOURCE_TYPE_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
    ENABLE;
--------------------------------------------------------
--  Constraints for Table LK_DED_ACCOUNTS
--------------------------------------------------------

  ALTER TABLE "LK_DED_ACCOUNTS" MODIFY ("DA_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LK_DISCONNECTION_CODES
--------------------------------------------------------

  ALTER TABLE "LK_DISCONNECTION_CODES" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "LK_DISCONNECTION_CODES" MODIFY ("CODE_ID" NOT NULL ENABLE);
  ALTER TABLE "LK_DISCONNECTION_CODES" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "LK_DISCONNECTION_CODES" MODIFY ("IS_DELETED" NOT NULL ENABLE);
  ALTER TABLE "LK_DISCONNECTION_CODES" MODIFY ("CREATION_DATE" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LK_FEATURES
--------------------------------------------------------

  ALTER TABLE "LK_FEATURES" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "LK_FEATURES" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "LK_FEATURES" ADD CONSTRAINT "LK_FEATURES_NEW_PK" PRIMARY KEY ("ID")
  USING INDEX "LK_FEATURES_NEW_PK"  ENABLE;
--------------------------------------------------------
--  Constraints for Table LK_FEATURES_TYPE
--------------------------------------------------------

  ALTER TABLE "LK_FEATURES_TYPE" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "LK_FEATURES_TYPE" MODIFY ("NAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LK_FLEX_OFFER_TYPE
--------------------------------------------------------

  ALTER TABLE "LK_FLEX_OFFER_TYPE" ADD CONSTRAINT "LK_FLEX_OFFER_TYPE_PK" PRIMARY KEY ("TYPE_ID")
  USING INDEX "LK_FLEX_OFFER_TYPE_PK"  ENABLE;
--------------------------------------------------------
--  Constraints for Table LK_FLEX_OFFERS
--------------------------------------------------------

  ALTER TABLE "LK_FLEX_OFFERS" ADD CONSTRAINT "LK_FLEX_OFFERS_PK" PRIMARY KEY ("ID")
  USING INDEX "LK_FLEX_OFFERS_PK"  ENABLE;
  ALTER TABLE "LK_FLEX_OFFERS" MODIFY ("OFFER_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LK_LANGUAGES
--------------------------------------------------------

  ALTER TABLE "LK_LANGUAGES" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "LK_LANGUAGES" MODIFY ("NAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LK_MAIN_PRODUCT_TYPES
--------------------------------------------------------

  ALTER TABLE "LK_MAIN_PRODUCT_TYPES" MODIFY ("TYPE" NOT NULL ENABLE);
  ALTER TABLE "LK_MAIN_PRODUCT_TYPES" MODIFY ("DISPLAY_NAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LK_MAREDCARD_LIST
--------------------------------------------------------

  ALTER TABLE "LK_MAREDCARD_LIST" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "LK_MAREDCARD_LIST" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LK_MENUS
--------------------------------------------------------

  ALTER TABLE "LK_MENUS" MODIFY ("MENU_ID" NOT NULL ENABLE);
  ALTER TABLE "LK_MENUS" MODIFY ("LABEL" NOT NULL ENABLE);
  ALTER TABLE "LK_MENUS" MODIFY ("PARENT_ID" NOT NULL ENABLE);
  ALTER TABLE "LK_MENUS" MODIFY ("ORDERING" NOT NULL ENABLE);
  ALTER TABLE "LK_MENUS" ADD CONSTRAINT "LK_MENUS_PK" PRIMARY KEY ("MENU_ID")
  USING INDEX "LK_MENUS_PK"  ENABLE;
--------------------------------------------------------
--  Constraints for Table LK_MONETARY_LIMITS
--------------------------------------------------------

  ALTER TABLE "LK_MONETARY_LIMITS" MODIFY ("LIMIT_ID" NOT NULL ENABLE);
  ALTER TABLE "LK_MONETARY_LIMITS" MODIFY ("LIMIT_NAME" NOT NULL ENABLE);
  ALTER TABLE "LK_MONETARY_LIMITS" MODIFY ("DEFAULT_VALUE" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LK_OFFERS_STATES
--------------------------------------------------------

  ALTER TABLE "LK_OFFERS_STATES" MODIFY ("STATE_ID" NOT NULL ENABLE);
  ALTER TABLE "LK_OFFERS_STATES" MODIFY ("STATE_DESC" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LK_OFFERS_TYPES
--------------------------------------------------------

  ALTER TABLE "LK_OFFERS_TYPES" MODIFY ("TYPE_ID" NOT NULL ENABLE);
  ALTER TABLE "LK_OFFERS_TYPES" MODIFY ("TYPE_DESC" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LK_PAM_CLASSES
--------------------------------------------------------

  ALTER TABLE "LK_PAM_CLASSES" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "LK_PAM_CLASSES" MODIFY ("NAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LK_PAM_PERIOD
--------------------------------------------------------

  ALTER TABLE "LK_PAM_PERIOD" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "LK_PAM_PERIOD" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "LK_PAM_PERIOD" ADD CONSTRAINT "LK_PAM_PERIOD_PK" PRIMARY KEY ("ID", "NAME")
  USING INDEX "LK_PAM_PERIOD_PK"  ENABLE;
--------------------------------------------------------
--  Constraints for Table LK_PAM_PRIORITY
--------------------------------------------------------

  ALTER TABLE "LK_PAM_PRIORITY" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "LK_PAM_PRIORITY" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "LK_PAM_PRIORITY" ADD CONSTRAINT "LK_PAM_PRIORITY_PK" PRIMARY KEY ("ID", "NAME")
  USING INDEX "LK_PAM_PRIORITY_PK"  ENABLE;
--------------------------------------------------------
--  Constraints for Table LK_PAM_SCHEDULES
--------------------------------------------------------

  ALTER TABLE "LK_PAM_SCHEDULES" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "LK_PAM_SCHEDULES" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "LK_PAM_SCHEDULES" ADD CONSTRAINT "LK_PAM_SCHEDULES_PK" PRIMARY KEY ("ID", "NAME")
  USING INDEX "LK_PAM_SCHEDULES_PK"  ENABLE;
--------------------------------------------------------
--  Constraints for Table LK_PAM_SERVICES
--------------------------------------------------------

  ALTER TABLE "LK_PAM_SERVICES" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "LK_PAM_SERVICES" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "LK_PAM_SERVICES" ADD CONSTRAINT "LK_PAM_SERVICES_PK" PRIMARY KEY ("ID", "NAME")
  USING INDEX "LK_PAM_SERVICES_PK"  ENABLE;
--------------------------------------------------------
--  Constraints for Table LK_REFILL_PAYMENT_PROFILE
--------------------------------------------------------

  ALTER TABLE "LK_REFILL_PAYMENT_PROFILE" MODIFY ("REFILL_PROFILE_ID" NOT NULL ENABLE);
  ALTER TABLE "LK_REFILL_PAYMENT_PROFILE" MODIFY ("REFILL_PROFILE_NAME" NOT NULL ENABLE);
  ALTER TABLE "LK_REFILL_PAYMENT_PROFILE" MODIFY ("AMOUNT_FROM" NOT NULL ENABLE);
  ALTER TABLE "LK_REFILL_PAYMENT_PROFILE" MODIFY ("AMOUNT_TO" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LK_REGIONS
--------------------------------------------------------

  ALTER TABLE "LK_REGIONS" ADD CONSTRAINT "LK_REGIONS_PK" PRIMARY KEY ("REGION")
  USING INDEX "LK_REGIONS_PK"  ENABLE;
--------------------------------------------------------
--  Constraints for Table LK_SMS_ACTION
--------------------------------------------------------

  ALTER TABLE "LK_SMS_ACTION" ADD CONSTRAINT "SMS_ACTION_PK" PRIMARY KEY ("SMS_ACTION_ID")
  USING INDEX "SMS_ACTION_PK"  ENABLE;
  ALTER TABLE "LK_SMS_ACTION" MODIFY ("SMS_ACTION_ID" NOT NULL ENABLE);
  ALTER TABLE "LK_SMS_ACTION" MODIFY ("ACTION_NAME" NOT NULL ENABLE);
  ALTER TABLE "LK_SMS_ACTION" MODIFY ("CODE" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LK_SMS_GATEWAYS_STATUS
--------------------------------------------------------

  ALTER TABLE "LK_SMS_GATEWAYS_STATUS" ADD CONSTRAINT "GATEWAYS_STATUS_PK" PRIMARY KEY ("STATUS_ID")
  USING INDEX "GATEWAYS_STATUS_PK"  ENABLE;
  ALTER TABLE "LK_SMS_GATEWAYS_STATUS" MODIFY ("STATUS_ID" NOT NULL ENABLE);
  ALTER TABLE "LK_SMS_GATEWAYS_STATUS" MODIFY ("STATUS_NAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LK_SMS_PRIORITIES
--------------------------------------------------------

  ALTER TABLE "LK_SMS_PRIORITIES" ADD CONSTRAINT "SMSPRIOITIES_PK" PRIMARY KEY ("PRIORITY_ID")
  USING INDEX "SMSPRIOITIES_PK"  ENABLE;
--------------------------------------------------------
--  Constraints for Table LK_SMS_TEMPLATE_PARAM
--------------------------------------------------------

  ALTER TABLE "LK_SMS_TEMPLATE_PARAM" ADD CONSTRAINT "SMS_PARAM_PK" PRIMARY KEY ("PARAMETER_ID", "SMS_ACTION_ID")
  USING INDEX "SMS_PARAM_PK"  ENABLE;
  ALTER TABLE "LK_SMS_TEMPLATE_PARAM" MODIFY ("PARAMETER_ID" NOT NULL ENABLE);
  ALTER TABLE "LK_SMS_TEMPLATE_PARAM" MODIFY ("SMS_ACTION_ID" NOT NULL ENABLE);
  ALTER TABLE "LK_SMS_TEMPLATE_PARAM" MODIFY ("PARAMETER_NAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LK_UNIT_TYPE_DESC
--------------------------------------------------------

  ALTER TABLE "LK_UNIT_TYPE_DESC" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "LK_UNIT_TYPE_DESC" MODIFY ("NAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LK_USAGE_COUNTER_DESC
--------------------------------------------------------

  ALTER TABLE "LK_USAGE_COUNTER_DESC" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "LK_USAGE_COUNTER_DESC" MODIFY ("NAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LK_VOUCHER_SERVERS
--------------------------------------------------------

  ALTER TABLE "LK_VOUCHER_SERVERS" ADD CONSTRAINT "LK_VOUCHER_SERVERS_PK" PRIMARY KEY ("SERVER_INDEX", "VOUCHER_SERIAL_LENGTH")
  USING INDEX "LK_VOUCHER_SERVERS_PK"  ENABLE;
  ALTER TABLE "LK_VOUCHER_SERVERS" MODIFY ("URL" NOT NULL ENABLE);
  ALTER TABLE "LK_VOUCHER_SERVERS" MODIFY ("AGENT_NAME" NOT NULL ENABLE);
  ALTER TABLE "LK_VOUCHER_SERVERS" MODIFY ("HOST" NOT NULL ENABLE);
  ALTER TABLE "LK_VOUCHER_SERVERS" MODIFY ("AUTHORIZATION" NOT NULL ENABLE);
  ALTER TABLE "LK_VOUCHER_SERVERS" MODIFY ("SERVER_INDEX" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ODS_H_ACC_ACTIVITIES
--------------------------------------------------------

  ALTER TABLE "ODS_H_ACC_ACTIVITIES" MODIFY ("ACTIVITY_ID" NOT NULL ENABLE);
  ALTER TABLE "ODS_H_ACC_ACTIVITIES" MODIFY ("ACTIVITY_NAME" NOT NULL ENABLE);
  ALTER TABLE "ODS_H_ACC_ACTIVITIES" MODIFY ("TABLE_TYPE" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ODS_H_ACC_HEADERS_MAPPING
--------------------------------------------------------

  ALTER TABLE "ODS_H_ACC_HEADERS_MAPPING" MODIFY ("ACTIVITY_ID" NOT NULL ENABLE);
  ALTER TABLE "ODS_H_ACC_HEADERS_MAPPING" MODIFY ("HEADER_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ODS_H_ACC_HEADERS_MAPPING_BK
--------------------------------------------------------

  ALTER TABLE "ODS_H_ACC_HEADERS_MAPPING_BK" MODIFY ("ACTIVITY_ID" NOT NULL ENABLE);
  ALTER TABLE "ODS_H_ACC_HEADERS_MAPPING_BK" MODIFY ("HEADER_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ODS_NODES
--------------------------------------------------------

  ALTER TABLE "ODS_NODES" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "ODS_NODES" MODIFY ("ADDRESS" NOT NULL ENABLE);
  ALTER TABLE "ODS_NODES" MODIFY ("USER_NAME" NOT NULL ENABLE);
  ALTER TABLE "ODS_NODES" MODIFY ("PASSWORD" NOT NULL ENABLE);
  ALTER TABLE "ODS_NODES" MODIFY ("NUMBER_OF_SESSIONS" NOT NULL ENABLE);
  ALTER TABLE "ODS_NODES" MODIFY ("CONCURRENT_CALLS" NOT NULL ENABLE);
  ALTER TABLE "ODS_NODES" MODIFY ("CONNECTION_TIMEOUT" NOT NULL ENABLE);
  ALTER TABLE "ODS_NODES" MODIFY ("EXTRA_CONF" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table SIM_UCIP_ACCOUNT_DETAILS
--------------------------------------------------------

  ALTER TABLE "SIM_UCIP_ACCOUNT_DETAILS" MODIFY ("SUBSCRIBER" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table SMS_ERROR
--------------------------------------------------------

  ALTER TABLE "SMS_ERROR" ADD CONSTRAINT "SMSERROR_PK1" PRIMARY KEY ("SMS_ERROR_ID")
  USING INDEX "SMSERROR_PK1"  ENABLE;
  ALTER TABLE "SMS_ERROR" MODIFY ("SMS_ERROR_ID" NOT NULL ENABLE);
  ALTER TABLE "SMS_ERROR" MODIFY ("ENTRY_DATE" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table SMS_Q
--------------------------------------------------------

  ALTER TABLE "SMS_Q" ADD CONSTRAINT "SMSQ_PK" PRIMARY KEY ("SMS_ID")
  USING INDEX "SMSQ_PK"  ENABLE;
  ALTER TABLE "SMS_Q" MODIFY ("SMS_ID" NOT NULL ENABLE);
  ALTER TABLE "SMS_Q" MODIFY ("ENTRY_DATE" NOT NULL ENABLE);
  ALTER TABLE "SMS_Q" MODIFY ("MSISDN" NOT NULL ENABLE);
  ALTER TABLE "SMS_Q" MODIFY ("SMS_TEXT" NOT NULL ENABLE);
  ALTER TABLE "SMS_Q" MODIFY ("TEMPLATE_ID" NOT NULL ENABLE);
  ALTER TABLE "SMS_Q" MODIFY ("LANG_ID" NOT NULL ENABLE);
  ALTER TABLE "SMS_Q" MODIFY ("PRIORITY_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table TEST_VOUCHERS
--------------------------------------------------------

  ALTER TABLE "TEST_VOUCHERS" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "TEST_VOUCHERS" MODIFY ("SERIAL_NUMBER" NOT NULL ENABLE);
  ALTER TABLE "TEST_VOUCHERS" MODIFY ("REQUEST_ID" NOT NULL ENABLE);
  ALTER TABLE "TEST_VOUCHERS" MODIFY ("CREATEDAT" NOT NULL ENABLE);
  ALTER TABLE "TEST_VOUCHERS" MODIFY ("AMOUNT" NOT NULL ENABLE);
  ALTER TABLE "TEST_VOUCHERS" MODIFY ("PERCENTAGE" NOT NULL ENABLE);
  ALTER TABLE "TEST_VOUCHERS" ADD PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
    ENABLE;
  ALTER TABLE "TEST_VOUCHERS" ADD UNIQUE ("SERIAL_NUMBER")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
    ENABLE;
  ALTER TABLE "TEST_VOUCHERS" ADD UNIQUE ("REQUEST_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
    ENABLE;
--------------------------------------------------------
--  Constraints for Table TESTT_VOUCHERS
--------------------------------------------------------

  ALTER TABLE "TESTT_VOUCHERS" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "TESTT_VOUCHERS" ADD UNIQUE ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
    ENABLE;
  ALTER TABLE "TESTT_VOUCHERS" ADD UNIQUE ("REQUEST_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
    ENABLE;
  ALTER TABLE "TESTT_VOUCHERS" ADD UNIQUE ("AMOUNT")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
    ENABLE;
--------------------------------------------------------
--  Constraints for Table TX_CALL_REASONS
--------------------------------------------------------

  ALTER TABLE "TX_CALL_REASONS" MODIFY ("USER_ID" NOT NULL ENABLE);
  ALTER TABLE "TX_CALL_REASONS" MODIFY ("USER_NAME" NOT NULL ENABLE);
  ALTER TABLE "TX_CALL_REASONS" MODIFY ("MSISDN" NOT NULL ENABLE);
  ALTER TABLE "TX_CALL_REASONS" MODIFY ("MSISDN_LAST_DIGIT" NOT NULL ENABLE);
  ALTER TABLE "TX_CALL_REASONS" MODIFY ("ENTRY_DATE" NOT NULL ENABLE);
  ALTER TABLE "TX_CALL_REASONS" MODIFY ("CALL_REASON_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table TX_CUSTOMERS_BARRINGS
--------------------------------------------------------

  ALTER TABLE "TX_CUSTOMERS_BARRINGS" MODIFY ("ENTRY_DATE" NOT NULL ENABLE);
  ALTER TABLE "TX_CUSTOMERS_BARRINGS" MODIFY ("MSISDN_MOD_X" NOT NULL ENABLE);
  ALTER TABLE "TX_CUSTOMERS_BARRINGS" MODIFY ("BARRING_TYPE" NOT NULL ENABLE);
  ALTER TABLE "TX_CUSTOMERS_BARRINGS" MODIFY ("REASON" NOT NULL ENABLE);
  ALTER TABLE "TX_CUSTOMERS_BARRINGS" MODIFY ("REQUESTED_BY" NOT NULL ENABLE);
  ALTER TABLE "TX_CUSTOMERS_BARRINGS" MODIFY ("USER_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table TX_LOGIN
--------------------------------------------------------

  ALTER TABLE "TX_LOGIN" MODIFY ("TX_LOGIN_ID" NOT NULL ENABLE);
  ALTER TABLE "TX_LOGIN" MODIFY ("LOGIN_TIME" NOT NULL ENABLE);
  ALTER TABLE "TX_LOGIN" MODIFY ("STATUS" NOT NULL ENABLE);
  ALTER TABLE "TX_LOGIN" MODIFY ("MESSAGE" NOT NULL ENABLE);
  ALTER TABLE "TX_LOGIN" MODIFY ("USER_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table TX_SUBSCRIBER_ADJUSTMENTS
--------------------------------------------------------

  ALTER TABLE "TX_SUBSCRIBER_ADJUSTMENTS" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "TX_SUBSCRIBER_ADJUSTMENTS" MODIFY ("MSISDN" NOT NULL ENABLE);
  ALTER TABLE "TX_SUBSCRIBER_ADJUSTMENTS" MODIFY ("MSISDN_MOD_X" NOT NULL ENABLE);
  ALTER TABLE "TX_SUBSCRIBER_ADJUSTMENTS" MODIFY ("ADJ_DATE" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table TX_USED_VOUCHERS
--------------------------------------------------------

  ALTER TABLE "TX_USED_VOUCHERS" MODIFY ("VOUCHER_SERIAL_NUMBER" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table TX_USER_FOOTPRINT
--------------------------------------------------------

  ALTER TABLE "TX_USER_FOOTPRINT" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table TX_USER_MONETARY_TOTALS
--------------------------------------------------------

  ALTER TABLE "TX_USER_MONETARY_TOTALS" MODIFY ("CURR_DATE" NOT NULL ENABLE);
  ALTER TABLE "TX_USER_MONETARY_TOTALS" MODIFY ("USER_ID" NOT NULL ENABLE);
  ALTER TABLE "TX_USER_MONETARY_TOTALS" MODIFY ("LIMIT_ID" NOT NULL ENABLE);
  ALTER TABLE "TX_USER_MONETARY_TOTALS" MODIFY ("CURR_VALUE" NOT NULL ENABLE);
