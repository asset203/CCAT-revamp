/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.springcloudconfigserver.defines;

/**
 * @author Mahmoud Shehab
 */
public class ErrorCodes {

    public static class SUCCESS {

        public final static int SUCCESS = 0;
        public final static int NO_DATA = 1;
    }

    public static class ERROR {

        public final static int INVALID_USERNAME_OR_PASSWORD = -100;
        public final static int EXPIRED_TOKEN = -101;
        public final static int NOT_AUTHORIZED = -102;
        public final static int INVALID_TOKEN = -103;
        public final static int UNKNOWN_ERROR = -104;
        public final static int NO_DATA_FOUND = -105;
        public final static int DATABASE_ERROR = -106;


    }

    public static class WARNING {

        public final static int MISSING_FIELD = 2;
        public final static int INVALID_INPUT = 3;
        public final static int DUPLICATED_DATA = 4;
        public final static int MUST_BE_MATCHED = 5;
        public final static int EMPTY_FILE = 6;
        public final static int MAX_FILE_UPLOAD_SIZE_EXCEEDED = 7;
        public final static int ALREADY_EXISTED= 8;

    }
}
