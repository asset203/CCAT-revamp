package com.asset.ccat.data_migration_service.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Assem.Hassan
 */
public class CCATLogger {

    public static final Logger DEBUG_LOGGER = LogManager.getLogger("businessLogger");
    public static final Logger FOOTPRINT_LOGGER = LogManager.getLogger("footprintLogger");
    public static final Logger ERROR_LOGGER = LogManager.getLogger("errorLogger");
    public static final Logger INTERFACE_LOGGER = LogManager.getLogger("interfaceLogger");

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
