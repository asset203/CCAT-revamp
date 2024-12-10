package com.asset.ccat.ci_service.defines;

/**
 * @author nour.ihab
 */
public class CIDefines {

    public static final String responseCode = "responseCode";
    public static final String faultCode = "faultCode";

    public static final String successCode = "2";
    public static final class UCIPCodes {

        public static final String SUCCESSFULL = "0";
        public static final String GRACE = "1";
        public static final String AFTER_GRACE = "2";
    }

    public static final class GET_PREPAID_PROFILE_PH {

        public final static String MSISDN = "$msisdn$";

    }

    public static final class SERVICE_CLASSES {

        public final static String CI_MSISDN = "$msisdn$";
        public final static String CI_SERVICE_ID = "$serviceId$";
        public final static String CI_SERVICE_NAME = "$serviceName$";
        public final static String CI_PACKAGE_NAME = "$packageId$";

    }

    public static final class AS7AB_PLUS {

        //As7ab plus constants
        //FAF_-603	= The customer is not the owner of any family
        public static String DELETE_FAMILY_SUB_NOT_OWNER_ERR = "-603";
        //FAF_0 = Success
        public static String SUCCESS_0 = "0";
        //FAF_1 = Success
        public static String SUCCESS_1 = "1";
        //FAF_2 = Success
        public static String SUCCESS_2 = "2";
    }

    //Added By Ihab
    public static final class PREPAIDVBP {
        public static final class ACTIONS {
            public static final String SUBSCRIPTION = "Subscription";
            public static final String UNSUBSCRIPTION = "Unsubscription";
            public static final String CHECK = "Check";
        }

        public static final class CHECK_SUBSCRIPTION_PARAMETERS {
            public final static String PVBP_MSISDN = "$msisdn$";
        }

        public static final class UNSUBSCRIPTION_PARAMETERS {
            public final static String PVBP_MSISDN = "$msisdn$";
        }

        public static final class SUBSCRIPTION_PARAMETERS {
            public final static String PVBP_MSISDN = "$msisdn$";
            public final static String PVBP_ORIGIN_OPERATOR_ID = "$originOperatorID$"; //userName
            public final static String PVBP_MAX_NUM_OF_RENEWALS = "$maxNumOfRenewals$";
            public final static String PVBP_AMOUNT = "$amount$";
            public final static String PVBP_EXTERNAL_DATA_1 = "$externalData1$";
            public final static String PVBP_EXTERNAL_DATA_2 = "$externalData2$";
        }

        public static final class CHECK_SUBSCRIPTION_RESPONSES {
            public final static String SUCCESS_CODE = "2";
        }

        public static final class UNSUBSCRIPTION_RESPONSES {
            public final static String SUCCESS_CODE = "2";
        }

        public static final class SUBSCRIPTION_RESPONSES {
            public final static String SUCCESS_CODE = "2";
        }


    }

    public static final class FLEX_SHARE {
        public static final class URL_REPLACEMENTS {

            public final static String SENDER_MSISDN = "$msisdnSender$";
            public final static String RECEIVER_MSISDN = "$msisdnReceiver$";
            public final static String CHANNEL_ID = "$channelId$";
            public final static String TRANSFER_AMOUNT = "$amountFlex$";
        }

        public static final class ELIGIBILITY_RESULT_MAP_KEYS {
            public final static String REQUEST_STATUS = "REQUEST_STATUS";
            public final static String TRANSFER_AMOUNT = "TRANSFER_AMOUNT";
            public final static String MAX_ELIG_AMOUNT = "MAX_ELIG_AMOUNT";
            public final static String MAX_ELIG_AMOUNT_FEES = "MAX_ELIG_AMOUNT_FEES";
            public final static String FEES = "FEES";
            public final static String EXPIRY_DATE = "EXPIRY_DATE";
        }


    }

    public static final class SUPER_FLEX {
        public final static String SUPER_FLEX_MSISDN = "$msisdn$";
        public final static String SUPER_FLEX_THRESHOLD_AMOUNT = "$amount$";
        public final static String SUPER_FLEX_PACKAGE_ID = "$packageId$";
        public final static String SUPER_FLEX_ERROR_PREFIX = "SF_";
        public final static String SUPER_FLEX_CI_SUCCESS_CODE = "SUPER_FLEX_CI_SUCCESS_CODE";
    }


}
