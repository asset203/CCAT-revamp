/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.ods_service.utils;

import com.asset.ccat.ods_service.logger.CCATLogger;
import java.util.regex.Pattern;

/**
 *
 * @author Mahmoud Shehab
 */
public class OdsUtils {

    public static String getColumnIndexFromString(String text) {
        int indexOfOpenEdBracket = text.indexOf("{");
        if (indexOfOpenEdBracket != -1) {
            int indexOfClosedBracket = text.indexOf("}");
            if (indexOfClosedBracket != -1) {
                return text.substring(indexOfOpenEdBracket + 1, indexOfClosedBracket);
            }
        }
        return null;
    }

    public static String replaceParameters(String value, String parameterTagName, String text) {
        String replacedText = text;
        if (parameterTagName != null && !"".equals(parameterTagName)) {
            String s = value;
            Pattern p = Pattern.compile("([-&\\|!\\(\\){}\\[\\]\\^\"\\~\\*\\$\\?:\\\\])");
            s = p.matcher(s).replaceAll("\\\\$1");
            replacedText = replacedText.replaceAll("\\{" + parameterTagName.trim() + "\\}", s);
        }
        return replacedText;
    }

    public static boolean checkPreCondition(String preCondition, Object[] columns) {
        boolean result = false;
        try {
            String columnIdxStr = getColumnIndexFromString(preCondition);
            Integer columnIdx = Integer.parseInt(columnIdxStr);
            int index = preCondition.indexOf("}") + 1;
            String operator = preCondition.substring(index, index + 2);
            String preCondVal = preCondition.substring(index + 2, preCondition.length());
            String val = (String) columns[columnIdx];
            if ("==".equals(operator)) {
                if (preCondVal.equalsIgnoreCase(val)) {
                    return true;
                }
            } else if ("!=".equals(operator)) {
                if (val != null && !preCondVal.equalsIgnoreCase(val)) {
                    return true;
                }
            }
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.debug("Error while parsing record with preCondition [" + preCondition + "]");
            CCATLogger.ERROR_LOGGER.error("Error while parsing record with preCondition[" + preCondition + "]", ex);

        }
        return result;
    }
}
