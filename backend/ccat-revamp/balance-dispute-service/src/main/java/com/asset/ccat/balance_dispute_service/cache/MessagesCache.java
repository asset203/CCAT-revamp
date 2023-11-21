package com.asset.ccat.balance_dispute_service.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
@ConfigurationProperties
public class MessagesCache {

    private final Map<String, Map<Integer, String>> MESSAGES_MAP = new HashMap<>();

    public Map<String, Map<Integer, String>> getExceptionMessages() {
        return MESSAGES_MAP;
    }

    public String getSuccessMsg(Integer code) {

        String success = "success";
        if (MESSAGES_MAP.get(success) != null && MESSAGES_MAP.get(success).get(code) != null) {
            return MESSAGES_MAP.get(success).get(code);
        }
        return "";
    }

    public String getErrorMsg(Integer code) {

        String error = "error";
        if (MESSAGES_MAP.get(error) != null && MESSAGES_MAP.get(error).get(code * -1) != null) {
            return MESSAGES_MAP.get(error).get(code * -1);
        }
        return "";
    }

    public String getFlexShareErrorMsg(Integer code) {
        String error = "flex-share";
        if (MESSAGES_MAP.get(error) != null && MESSAGES_MAP.get(error).get(code * -1) != null) {
            return MESSAGES_MAP.get(error).get(code * -1);
        }
        return "";
    }

    public String getWarningMsg(Integer code) {

        String warn = "warn";
        if (MESSAGES_MAP.get(warn) != null && MESSAGES_MAP.get(warn).get(code) != null) {
            return MESSAGES_MAP.get(warn).get(code);
        }
        return "";
    }

    public String replaceArgument(String msg, String[] args) {
        if (msg != null && !msg.isBlank()) {
            if (args.length > 0) {
                for (int i = 1; i <= args.length; i++) {
                    msg = msg.replace(("$" + i), args[i - 1]);
                }
            }
            return msg;
        }
        return "";
    }
}
