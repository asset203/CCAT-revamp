/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.nba_service.defines;

/**
 *
 * @author Mahmoud Shehab
 */
public class ErrorCodes {

    public static class SUCCESS {

        public final static int SUCCESS = 0;
    }

    public static class ERROR {

        public final static int NO_COMMANDS_FOUND = -300;
        public final static int EXPIRED_TOKEN = -301;
        public final static int NOT_AUTHORIZED = -302;
        public final static int INVALID_TOKEN = -303;
        public final static int UNKNOWN_ERROR = -305;
        public final static int NBA_ERROR = -306;
        public final static int NO_DATA_FOUND = -307;
        public final static int TIBCO_PARSING_FAIL = -308;
        public final static int TIBCO_NBA_UNREACHABLE= -309;
        public final static int SEND_SMS_TEMPLATE_NOT_FOUND= -310;
        public final static int ACCEPT_OFFER_TEMPLATE_NOT_FOUND= -311;

    }

}
