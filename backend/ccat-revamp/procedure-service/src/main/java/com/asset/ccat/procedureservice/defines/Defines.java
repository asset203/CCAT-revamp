package com.asset.ccat.procedureservice.defines;


public class Defines {

    public static class ContextPaths {

        public static final String BASE_CONTEXT_PATH = "/ccat";
        public static final String LOOKUPS = "/lookup-service";
        public static final String CONFIGURATIONS = "/configurations";
        public static final String CACHED_LOOKUPS = "/cached-lookups";
        public static final String VOUCHER = "/voucher";
        public static final String FLEX_SHARE = "/flex-share";
        public static final String PAYMENT_GATEWAY_VOUCHER = "/payment-gateway-voucher";

    }

    public static class WEB_ACTIONS {

        public static final String GET = "/get";
        public static final String GET_ALL = "/get-all";
        public static final String ADD = "/add";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        public static final String UPLOAD = "/upload";
        public static final String DOWNLOAD = "/download";
        public static final String INQUIRY = "/inquiry";
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

    public static class FLEX_SHARE {
        public static final String MSISDN_IN = "msisdn_in";
        public static final String PROMO_ID_IN = "promo_id_in";
        public static final String CHANNEL_ID_IN = "channle_id_in";
        public static final String TRANS_TYPE_IN = "trans_type_in";
        public static final String BUNDLE_OUT = "bundle_out";
        public static final String CUST_STATUS = "cust_status";
        public static final String INQUIRY_DATE = "inquiry_date";
        public static final String OPT_OUT_DATE = "opt_out_date";
        public static final String OPT_IN_DATE = "opt_in_date";
        public static final String OPT_IN_COUNT = "opt_in_count";
        public static final String PARAM_1_OUT = "param_1_out";
        public static final String PARAM_2_OUT = "param_2_out";
        public static final String PARAM_3_OUT = "param_3_out";
        public static final String PARAM_4_OUT = "param_4_out";
        public static final String PARAM_5_OUT = "param_5_out";
        public static final String PARAM_6_OUT = "param_6_out";
        public static final String PARAM_7_OUT = "param_7_out";
        public static final String PARAM_8_OUT = "param_8_out";
        public static final String PARAM_9_OUT = "param_9_out";
        public static final String PARAM_10_OUT = "param_10_out";
        public static final String PARAM_11_OUT = "param_11_out";

        public static final String STATUS_OUT = "status_out";

    }



}
