/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.springcloudconfigserver.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Mahmoud Shehab
 */
@Component
@ConfigurationProperties
public class MessagesCache {

    private final Map<String, Map<Integer, String>> MESSAGES_MAP = new HashMap<>();

    public Map<String, Map<Integer, String>> getExceptionMessages() {
        return MESSAGES_MAP;
    }

    public String getsuccessMsg(Integer code) {

        if (MESSAGES_MAP != null && MESSAGES_MAP.get("success") != null
                && MESSAGES_MAP.get("success").get(code) != null) {
            return MESSAGES_MAP.get("success").get(code);
        }
        return "";
    }

    public String getErrorMsg(Integer code) {

        if (MESSAGES_MAP != null && MESSAGES_MAP.get("error") != null
                && MESSAGES_MAP.get("error").get(code * -1) != null) {
            return MESSAGES_MAP.get("error").get(code * -1);
        }
        return "";
    }

    public String getWarningMsg(Integer code) {

        if (MESSAGES_MAP != null && MESSAGES_MAP.get("warn") != null
                && MESSAGES_MAP.get("warn").get(code) != null) {
            return MESSAGES_MAP.get("warn").get(code);
        }
        return "";
    }

    public String replaceArgument(String msg, String arg) {

        if (msg != null && !msg.isBlank()) {
            return msg.replace("$1", arg);
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
