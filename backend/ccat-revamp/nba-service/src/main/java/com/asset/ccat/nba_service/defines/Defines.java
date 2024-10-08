/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.nba_service.defines;

/**
 * @author Mahmoud Shehab
 */
public class Defines {

  public static class ContextPaths {

    public static final String BASE_CONTEXT_PATH = "/ccat";
    public static final String LOOKUPS = "/lookups";
    public static final String CONFIGURATIONS = "/configurations";
    public static final String NBA_SERVERS = "/nba-servers";
    public static final String GIFTS = "/gifts";
    public static final String TIBCO_GIFTS = "/tibco-gifts";
    public static final String REJECT = "/reject";
    public static final String ACCEPT = "/accept";
    public static final String SEND = "/send";
  }

  public static class WEB_ACTIONS {

    public static final String GET = "/get";
    public static final String GET_ALL = "/get-all";
    public static final String ADD = "/add";
    public static final String UPDATE = "/update";
    public static final String DELETE = "/delete";
    public static final String UPLOAD = "/upload";
    public static final String DOWNLOAD = "/download";
  }

  public static class SecurityKeywords {

    public static final String USERNAME = "username";
    public static final String PREFIX = "prefix";
    public static final String SESSION_ID = "sessionId";
  }

  public static class SEVERITY {

    public static final Integer CLEAR = 0;
    public static final Integer VALIDATION = 1;
    public static final Integer ERROR = 2;
    public static final Integer FATAL = 3;
  }

  public static class LOOKUPS {

  }

  public static final class GIFT_MODEL_ATTR {

    public static final String GIFT_TYPE = "giftType";
    public static final String GIFT_ID = "giftId";
    public static final String GIFT_SEQ_ID = "giftSeqID";
    public static final String GIFT_TRIGGER_ID = "giftTriggerID";
    public static final String GIFT_NAME = "giftName";
    public static final String OFFER_DESC_AR = "giftDescription";
    public static final String GIFT_CATEGORY = "giftCategory";
    public static final String STATUS = "giftStatus";
    public static final String ASSIGN_DATE = "timeOfAssignment";
    public static final String REDEMPTION_EXP_DATE = "timeOfAssignmentExpiry";
    public static final String TIME_INQUIRY = "timeOfInquiry";
    public static final String REDEMPTION_DATE = "timeOfRdemption";
    public static final String TIME_PROVISING = "timeOfProvisioning";
    public static final String GIFT_START_DATE = "giftStartDate";
    public static final String GIFT_END_DATE = "giftEndDate";
    public static final String GIFT_FEES_UNIT = "giftFeesUnit";
    public static final String IS_ASSEIGNMENT_SALLENFNY = "isAssignmentSallefny";
    public static final String IS_REDEMPTION_SALLENFNY = "isRedemptionSallefny";
    public static final String GIFT_MINUTES_VALUE = "giftMinutesValue";
    public static final String GIFT_SMS_VALUE = "giftSMSValue";
    public static final String GIFT_MI_VALUE = "giftMIValue";
    public static final String GIFT_LIFECYCLE = "giftLifeCycle";
    public static final String GIFT_CONTENT = "giftContent";
    public static final String GIFT_SOCIAL = "giftSocial";
    public static final String GIFT_FLEX = "giftFlex";
    public static final String GIFT_SUPER_NUMBER = "giftSuperNumber";
    public static final String GIFT_VALIDITY = "giftNoDays";
    public static final String GIFT_SHORT_CODE = "giftShortCode";
    public static final String GIFT_QUOTA = "giftUnits";
    public static final String GIFT_FEES = "giftFees";
    public static final String ASSIGN_SCRIPT = "giftParam1";
    public static final String REDEEM_SCRIPT = "giftParam2";
    public static final String INCENTIVE = "giftParam3";
    public static final String GIFT_NO_OF_RENEWALS = "giftParam4";
    public static final String GIFT_BUNDLE = "giftParam5";
    public static final String IS_REJECTED = "giftParam6";
    public static final String SMS_IS_SENT = "giftParam7";
    public static final String ASSIGN_CHANNEL_ID = "giftParam8";
    public static final String REDEMPTION_CHANNEL_ID = "giftParam9";
    public static final String AGENT_ID = "giftParam10";
    public static final String SALES_SCRIPT_1 = "giftParam11";
    public static final String GIFT_PARAM_12 = "giftParam12";
    public static final String SALES_SCRIPT_3 = "giftParam13";
    public static final String REDEEM_ACCUMULATED_INCENTIVE = "giftParam14";
    public static final String AGENT_ID_REJECT = "giftParam15";
    public static final String TOTAL_INCENTIVE = "TOTAL_INCENTIVE";
    public static final String AGENT_ID_SMS = "giftParam16";
    public static final String ORG_QUOTA_IVR = "giftParam18";
    public static final String TOTAL_QUOTA_IVR = "giftParam17";
    public static final String ORG_FEES_IVR = "giftParam19";
    public static final String IS_GRACE = "giftParam20";
    public static final String GRACE_ACCUMULATED_INCENTIVE = "giftParam21";
    public static final String REJECTION_DATE = "giftParam22";

    public static final String GIFT_PARAM_23 = "giftParam23";
    public static final String GIFT_PARAM_24 = "giftParam24";
    public static final String GIFT_PARAM_25 = "giftParam25";
    public static final String GIFT_PARAM_26 = "giftParam26";
    public static final String GIFT_PARAM_27 = "giftParam27";
    public static final String GIFT_PARAM_28 = "giftParam28";
    public static final String GIFT_PARAM_29 = "giftParam29";
    public static final String GIFT_PARAM_30 = "giftParam30";
    public static final String GIFT_PARAM_31 = "giftParam31";
    public static final String GIFT_PARAM_32 = "giftParam32";
    public static final String GIFT_PARAM_33 = "giftParam33";
    public static final String GIFT_PARAM_34 = "giftParam34";
    public static final String GIFT_PARAM_35 = "giftParam35";
    public static final String GIFT_PARAM_36 = "giftParam36";
    public static final String GIFT_PARAM_37 = "giftParam37";
    public static final String GIFT_PARAM_38 = "giftParam38";
    public static final String GIFT_PARAM_39 = "giftParam39";
    public static final String GIFT_PARAM_40 = "giftParam40";
  }

  public static final class SP_CONSTANTS {

    public static final String SP_NAME = "SP_NAME";
    public static final String NBA_DB_URL = "NBA_DB_URL";
    public static final String NBA_DB_PASSWORD = "NBA_DB_PASSWORD";
    public static final String NBA_DB_USERNAME = "NBA_DB_USERNAME";
    public static final String DB_CONNECTION_TYPE = "DB_CONNECTION_TYPE";
    public static final String DB_DATASOURCENAME_STRING = "DB_DATASOURCENAME_STRING";
    public static final String GIFT_KEY = "Gift";
    public static final String MSISDN_KEY = "msisdn";
    public static final String IN_PARAM_KEY = "param";
    public static final String IN_PARAM_1 = "param1";
    public static final String AGENT_ID_KEY = "AGENT_ID";
    public static final String ERR_CODE_KEY = "Errcode";
    public static final String ERR_DESC_KEY = "Errdesc";
    public static final String CHANNEL_ID_KEY = "channelId";
    public static final String TRIGGER_ID_KEY = "tiggerId";
    public static final String OUT_PARAM_KEY = "outputparam";
    public static final String GIFT_SEQ_ID_KEY = "giftSeqID";

    public static final String REPLY_SUCESS = "0";
    public static final String REPLY_FAIL = "-1";
    public static final String REPLY_NO_GIFTS_RETURNED = "-2";
    public static final String NBA_REPLY_FAIL = "NBA_-1";
    public static final String NBA_REPLY_NO_GIFTS_RETURNED = "NBA_-2";
  }

  public static final class HTTP_CONSTANTS {

    public static final String MSISDN_PH = "$msisdn$";
    public static final String PARAM1_PH = "$param1$";
    public static final String AGENT_ID_PH = "$agentId$";
    public static final String PROMO_ID_PH = "$promoId$";
    public static final String WLIST_ID_PH = "$wlistId$";
    public static final String TRIGGER_ID_PH = "$triggerId$";
    public static final String CHANNEL_ID_PH = "$channelId$";
    public static final String MSISDN_KEY = "msisdn";
    public static final String PARAM1_KEY = "param1";
    public static final String PARAM2_KEY = "param2";
    public static final String PARAM3_KEY = "param3";
    public static final String PROMO_ID_KEY = "promoId";
    public static final String WLIST_ID_KEY = "wlistId";
    public static final String TRIGGER_ID_KEY = "triggerId";
    public static final String CHANNEL_ID_KEY = "channelId";
    public static final String RESPONSE_CODE = "STATUS_CODE";
    public static final String RESPONSE_DESC = "STATUS_DESCRIPTION";
  }

  public static final class NBA_DEFINES {

    public static final String MSISDN_PREFIX = "20";
    public static final String REDEEM_OFFER_CLASSPATH = "classpath:templates/RedeemOffer.json";
    public static final String SEND_SMS_CLASSPATH = "classpath:templates/SendSms.json";
  }

  public static final class NBA_PLACEHOLDERS {

    public static final String TRIGGER_ID = "$triggerId$";
    public static final String CUSTOMER_ACCOUNT_ID = "$customerAccountId$";
    public static final String AGENT_ID = "$agentId$";
    public static final String CATEGORY = "$category$";
    public static final String CHANNEL_ID = "$channelId$";
    public static final String SHORT_CODE = "$shortCode$";
    public static final String W_LIST = "$wlist$";
    public static final String GIFT_SEQ_ID = "$giftSeqID$";
    public static final String PROMO_ID = "$idValue$";

  }

  public static final class HTTP_HEADERS {

    public static final String X_SOURCE_SYSTEM = "X-Source-System";
    public static final String X_SOURCE_IDENTITY_TOKEN = "X-Source-Identity-Token";

  }
}
