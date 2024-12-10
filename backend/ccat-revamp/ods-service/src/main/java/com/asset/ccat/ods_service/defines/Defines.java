package com.asset.ccat.ods_service.defines;

/**
 * @author Mahmoud Shehab
 */
public class Defines {

    public static class ContextPaths {

        public static final String BASE_CONTEXT_PATH = "/ccat";
        public static final String LOOKUPS = "/lookup-service";
        public static final String CONFIGURATIONS = "/configurations";
        public static final String DSS_REPORT = "/dss-report";
        public static final String TRAFFIC_BEHAVIOR = "/traffic-behavior";
        public static final String USSD_REPORT = "/ussd";
        public static final String BTIVR_REPORT = "/btivr";
        public static final String CONTRACT_BILL = "/contract-bill";
		public static final String VODAFONE_ONE_REDEEM = "/vodafone-one-redeem";
  		public static final String VODAFONE_ONE_PROFILE = "/vodafone-one-profile";
        public static final String CACHED_LOOKUPS = "/cached-lookups";
        public static final String TRANSACTION_ADMIN = "/transaction-admin";
        public static final String TRANSACTION_LINK = "/link-description";
        public static final String ODS = "/ods";
        public static final String ODS_NODES = "/ods-nodes";
        public static final String DSS_NODES = "/dss-nodes";
        public static final String FLEX_SHARE_HISTORY_NODES = "/flex-history-nodes";
        public static final String LK_ACCOUNT_FLAGS = "/lk-account-flags";
        public static final String BT_STATUS = "/bt-status";
        public static final String DSS_COLUMNS = "/dss-columns";
        public static final String ACCOUNT_HISTORY = "/account-history";
        public static final String FLEX_SHARE_HISTORY = "/flex-share-history";
        public static final String ACTIVITIES = "/activities";
        public static final String ACTIVITIES_HEADERS = "/activities-headers";
        public static final String ACTIVITIES_HEADERS_MAPPING = "/activities-header-mapping";
        public static final String ACTIVITIES_DETAILS_MAPPING = "/activities-details-mapping";
        public static final String TYPE = "/type";
        public static final String CODE = "/code";
        public static final String LINK = "/link";
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

    public static class CONSTANANTS {

        public static final String PAGE_BT_IVR_868 = "GET_BT_IVR_868";
        public static final String PAGE_TRAFFIC_BEHAVIOR = "TrafficBehavior";
        public static final String PAGE_USSD = "GET_USSD_REPORT";
        public static final String PAGE_CONTRACT_BILL = "GET_CONTRACT_BILL";
    }

    public static class FlexShareHistorySP {

        // INPUTS
        public static final String MSISDN = "msisdn";
        public static final String PARTY_TYPE = "party_type";
        public static final String START_DATE = "start_date";
        public static final String END_DATE = "end_date";


        // OUPUTS parameters
        public static final String DATA_CURSOR = "data_cursor";
        public static final String ERROR_CODE = "ERROR_CODE";
        public static final String ERROR_MESSAGE = "error_message";

        // data cursor metadata
        public static final String SENDER_MSISDN = "SENDER_MSISDN";
        public static final String RECEIVER_MSISDN = "RECEIVER_MSISDN";
        public static final String FLEXES_AMOUNT = "FLEXES_AMOUNT";
        public static final String FEES_V = "FEES_V";
        public static final String STATUS_V = "STATUS_V";
        public static final String FLEXES_START_DATE = "FLEXES_START_DATE";
        public static final String FLEXES_EXP_DATE = "FLEXES_EXP_DATE";
        public static final String CHANNEL = "CHANNEL";

    }

}
