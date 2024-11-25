/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.dynamic_page.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Mahmoud Shehab
 */
public class CCATLogger {
//    private static final Logger INFO_LOGGER = LogManager.getLogger("infoLogger");

    public static final Logger DEBUG_LOGGER = LogManager.getLogger("businessLogger");
    public static final Logger FOOTPRINT_LOGGER = LogManager.getLogger("footprintLogger");
    public static final Logger ERROR_LOGGER = LogManager.getLogger("errorLogger");
    public static final Logger TPS_LOGGER = LogManager.getLogger("statisticsTPSLogger");

    /**
     * 0.13 milliseconds avg time
     *
     * @param msg
     * @return
     */
    private static String format(String msg) {
        StringBuilder formattedMsg = new StringBuilder();
        StackTraceElement ste = getCallerFromStack();
        formattedMsg.append("[")
                .append(ste.getClassName().substring(ste.getClassName().lastIndexOf(".") + 1))
                .append(".")
                .append(ste.getMethodName())
                .append("] ")
                .append(msg);
        return formattedMsg.toString();
    }

    private static StackTraceElement getCallerFromStack() {
        return Thread.currentThread().getStackTrace()[4];
    }
}
