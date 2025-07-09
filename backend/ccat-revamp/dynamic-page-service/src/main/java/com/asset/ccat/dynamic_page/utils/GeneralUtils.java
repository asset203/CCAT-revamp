package com.asset.ccat.dynamic_page.utils;

import com.asset.ccat.dynamic_page.constants.ParameterDataTypes;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import oracle.jdbc.OracleTypes;

@Component
public class GeneralUtils {

    @Autowired
    private DateUtil dateUtil;

    public int getSqlType(int dataType) {
        if (dataType == ParameterDataTypes.INT.id) {
            return OracleTypes.NUMBER;
        } else if (dataType == ParameterDataTypes.DATE.id) {
            return OracleTypes.DATE;
        } else {
            return OracleTypes.VARCHAR; // default
        }
    }

    /**
     * @param object     : object to be casted
     * @param dataType   : data type to cast object to
     * @param dateFormat : date format used in case casting from long timeepoch to date object,
     *                   N.B. if date format is not provided and datatype is date the object is casted from date object
     *                   to long timeepoch
     * @return object after casting to desired datatype
     */
    public Object castObject(Object object, int dataType, String dateFormat) {
        try {
            CCATLogger.DEBUG_LOGGER.debug("Casting object of type >> " + ParameterDataTypes.getType(dataType));
            if (dataType == ParameterDataTypes.INT.id) {
                return (Integer) getInt(object, dataType);
            } else if (dataType == ParameterDataTypes.DATE.id) {
                if (dateFormat != null && (object instanceof Long) && !dateFormat.isBlank()) {
                    // cast from timeepoch to date object
                    return dateUtil.getDateFormatted((Long) object, dateFormat);
                } else {
                    //cast from date to timeepoch
                    return dateUtil.getTimeepochFromDate(object,dateFormat);
                }
            } else if (dataType == ParameterDataTypes.CURSOR.id) {
                return object; // not handled from here (useless)
            } else if (dataType == ParameterDataTypes.STRING.id) {
                return (String) object;
            } else {
                return object;
            }
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.debug("Failed to cast object >> " + ex.getMessage());
            CCATLogger.DEBUG_LOGGER.error("Failed to cast object >> " + ex.getMessage(), ex);
        }
        return object; // in case of failure return object without casting
    }


    public Integer getInt(Object object, int dataType) {
        if (dataType == ParameterDataTypes.STRING.id) {
            return Integer.parseInt((String) object);
        } else {
            return ((Number) object).intValue();
        }
    }

    public String getString(Object object, int dataType, String dateFormat) {
        if (dataType == ParameterDataTypes.STRING.id) {
            return (String) object;
        } else if (dataType == ParameterDataTypes.INT.id) {
            return String.valueOf(((Number) object).intValue());
        } else if (dataType == ParameterDataTypes.DATE.id) {
            return (String) dateUtil.getDateFormatted((Long) object, dateFormat);
        } else {
            return object.toString();
        }
    }
}
