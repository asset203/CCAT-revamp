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
            CCATLogger.DEBUG_LOGGER.debug("Precondition = {}", preCondition);
            String columnIdxStr = getColumnIndexFromString(preCondition);
            int columnIdx = Integer.parseInt(columnIdxStr);
            int index = preCondition.indexOf("}") + 1;
            String operator = preCondition.substring(index, index + 2);
            String preCondVal = preCondition.substring(index + 2);
            String val = (String) columns[columnIdx];

            CCATLogger.DEBUG_LOGGER.debug("val={} || operator = {} ||  preCondVal={} || colIndex=[{}] --- Columns={}", val, operator, preCondVal, columnIdx, columns);
            if ("==".equals(operator)) {
                if (preCondVal.equalsIgnoreCase(val)) {
                    result = true;
                }
            } else if ("!=".equals(operator) && (val != null && !preCondVal.equalsIgnoreCase(val)))
                result = true;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while comparing a record with a preCondition. ", ex);
            CCATLogger.ERROR_LOGGER.error("Error while comparing a record with a preCondition. ", ex);
        }
        CCATLogger.DEBUG_LOGGER.debug("PreCondition satisfied = {}", result);
        return result;
    }
}
