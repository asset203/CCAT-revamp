/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.user_management.defines;

/**
 *
 * @author Mahmoud Shehab
 */
public class ErrorCodes {

    public static class SUCCESS {

        public final static int SUCCESS = 0;
    }

    public static class ERROR {

        public final static int INVALID_USERNAME_OR_PASSWORD = -400;
        public final static int EXPIRED_TOKEN = -401;
        public final static int NOT_AUTHORIZED = -402;
        public final static int INVALID_TOKEN = -403;
        public final static int DATABASE_ERROR = -404;
        public final static int UNKNOWN_ERROR = -405;
        public final static int CANNOT_GENERATE_TOKEN = -406;
        public final static int LDAP_AUTH_FAILED = -407;
        public final static int MISSING_FIELD = -408;
        public final static int USER_NOT_FOUND = -409;
        public final static int PROFILE_NOT_FOUND = -410;
        public final static int NO_PROFILES_FOUND = -411;
        public final static int NO_USERS_FOUND = -412;
        public final static int ADD_FAILED = -413;
        public final static int UPDATE_FAILED = -414;
        public final static int DELETE_FAILED = -415;
        public final static int PROFILE_HAS_CHILDREN = -416;
        public final static int PARSING_FAILED = -417;
        public final static int INVALID_FILE_TEMPLATE = -418;
        public final static int INVALID_INPUT = -419;
        public final static int UNSUPPORTED_FILE_TYPE = -420;
        public final static int EMPTY_FILE = -421;
        public final static int UNSUPPORTED_OPERATION_TYPE = -422;
        public final static int WRITING_FAILED = -423;

        public static int DEBIT_EXCEEDED = -424;   //"Maximum debit exceeded. Maximum debit amount allowed per transaction is " + debitLimit + " L.E.";
        public static int DEBIT_ALLOWED = -425;    //"Maximum debit exceeded. Maximum daily debit amount allowed is " + dailyDebitLimit + " L.E. and only " + todaysDebitTotal + " L.E. is remaining.";
        public static int DEBIT_ALL_USED = -426;   //"You have used all the allowable daily debit amount.";

        public static int REBATE_EXCEEDED = -427;    //"Maximum rebate exceeded. Maximum rebate amount allowed per transaction is " + rebateLimit + " L.E.";
        public static int REBATE_ALLOWED = -428;     //"Maximum rebate exceeded. Maximum daily rebate amount allowed is " + dailyRebateLimit + " L.E. and only " + todaysRebateTotal + " L.E. is remaining.";
        public static int REBATE_ALL_USED = -429;    //"You have used all the allowable daily rebate amount.";

    }

}
