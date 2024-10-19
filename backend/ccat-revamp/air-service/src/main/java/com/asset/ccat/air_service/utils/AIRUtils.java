package com.asset.ccat.air_service.utils;

import com.asset.ccat.air_service.configurations.Properties;
import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.defines.Defines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.exceptions.AIRVoucherException;
import com.asset.ccat.air_service.logger.CCATLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Mahmoud Shehab
 */
@Component
public class AIRUtils {

    private final Properties properties;
    private final DateTimeFormatter airFormatNew;
    private final DateTimeFormatter ccatFormat;
    private final DateTimeFormatter airFormat;
    private final DateTimeFormatter airFormatSubString;
    private final DateTimeFormatter airFormatGmt;
    private final DateTimeFormatter ccatExpiryDate;
    private final DateTimeFormatter ccatExpiryTimeStamp;

    @Autowired
    public AIRUtils(Properties properties) {
        this.properties = properties;
        airFormatNew = DateTimeFormatter.ofPattern(properties.getAirDateFormatNew());
        ccatFormat = DateTimeFormatter.ofPattern(properties.getCcatDateFormat());
        ccatExpiryDate = DateTimeFormatter.ofPattern(properties.getCcatExpiryDate());
        ccatExpiryTimeStamp = DateTimeFormatter.ofPattern(properties.getCcatExpiryTimeStamp());
        airFormat = DateTimeFormatter.ofPattern(properties.getAirDateFormat());
        airFormatSubString = DateTimeFormatter.ofPattern(properties.getAirDateFormat());
        airFormatGmt = DateTimeFormatter.ofPattern(properties.getAirDateFormatGmt());
        TimeZone.setDefault(TimeZone.getTimeZone("Africa/Cairo"));
    }

    public String getCurrentFormattedDate() {
        return airFormatNew.format(LocalDateTime.now());
    }

    public Float amountInLE(String amountStr) {
        return (float) Integer.parseInt(amountStr) / 100;
    }

    public String amountInPT(Float amount) {
        int intAmount = (int) (amount * 100);
        return String.valueOf(intAmount);
    }

    public Date formatAirToCcatDate(String dateStr) {
        try {
            if (Objects.nonNull(dateStr) && !dateStr.equals(Defines.AIR_DEFINES.INFINITY_DATE_AIR)) {
                LocalDate locateDate = LocalDate.parse(dateStr, airFormatNew);

                return Date.from(locateDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            }
        } catch (DateTimeParseException ex) {
            CCATLogger.DEBUG_LOGGER.debug("Error while parsing date to String [" + dateStr + "], format [" + properties.getCcatDateFormat() + "]");
            CCATLogger.ERROR_LOGGER.error("Error while parsing date to String [" + dateStr + "], format [" + properties.getCcatDateFormat() + "]", ex);
        }
        return null;
    }

    public Date parseAirDate(String dateStr) {
        Date result = null;
        try {
            if (Objects.nonNull(dateStr) && !dateStr.equals(Defines.AIR_DEFINES.INFINITY_DATE_AIR)) {
                LocalDate locateDate = LocalDate.parse(dateStr.subSequence(0, 8), airFormatSubString);
                result = java.util.Date.from(locateDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                return result;
            }
        } catch (DateTimeParseException ex) {
            try {
                LocalDateTime locateDateTime = LocalDateTime.parse(dateStr, airFormat);
                result = java.util.Date.from(locateDateTime.atZone(ZoneId.systemDefault()).toInstant());
            } catch (DateTimeParseException ex1) {
                CCATLogger.DEBUG_LOGGER.info("Error while parsing date to String [" + dateStr + "], format [" + properties.getAirDateFormat() + "]");
                CCATLogger.ERROR_LOGGER.error("Error while parsing date to String [" + dateStr + "], format [" + properties.getAirDateFormat() + "]", ex);
            }
        }
        return result;
    }

    public String formatNewAIR(Date date) throws ParseException {
        return airFormatNew.format(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
    }

    public String formatExpiryDate(Date date) {
        return ccatExpiryDate.format(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
    }

    public String formatExpiryTimeStamp(Date date) {
        return ccatExpiryTimeStamp.format(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
    }

    public Date addDaysToCurrentDate(String daysStr) {
        if (Objects.nonNull(daysStr)) {
            int days = Integer.parseInt(daysStr);
            Calendar c1 = Calendar.getInstance();
            c1.setTime(new Date());
            c1.add(Calendar.DATE, days);
            return c1.getTime();
        }
        return null;
    }

    public Date formatAir2CCDateTime(String dateStr) {
        try {
            if (Objects.isNull(dateStr) || dateStr.startsWith("9999")) {
                return null;
            } else {
                LocalDateTime locateDate = LocalDateTime.parse(dateStr, airFormat);
                return java.util.Date.from(locateDate.atZone(ZoneId.systemDefault()).toInstant());
            }
        } catch (DateTimeParseException ex) {
            try {
                LocalDateTime locateDate = LocalDateTime.parse(dateStr, airFormatGmt);
                return java.util.Date.from(locateDate.atZone(ZoneId.systemDefault()).toInstant());

            } catch (DateTimeParseException e) {
                return null;
            }
        }
    }

    public Date formatDateString(String dateString) {
        String[] formats = {properties.getAirDateFormat(), properties.getAirDateFormatGmt()};

        for (String format : formats) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                sdf.setLenient(false);
                return sdf.parse(dateString);
            } catch (ParseException e) {
                CCATLogger.DEBUG_LOGGER.warn("Cannot parse date=[{}] with format=[{}], will continue to the next format if exist.", dateString, format);
            }
        }
        return null;
    }

    public String formatCCATDate(Date date) {
        if (Objects.nonNull(date)) {
            return ccatFormat.format(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
        }
        return "";
    }

    public void validateAIRResponse(HashMap responseMap, String transaction, Long time, String operator) throws AIRServiceException, AIRException {
        if (Objects.isNull(responseMap)) {
            printAirLog(transaction, time, operator, ErrorCodes.ERROR.AIR_RESPONSE_NULL + "");
            throw new AIRServiceException(ErrorCodes.ERROR.AIR_RESPONSE_NULL);
        }
        if (responseMap.get(AIRDefines.responseCode) == null) {
            String faultCode = (String) responseMap.get(AIRDefines.faultCode);
            CCATLogger.DEBUG_LOGGER.debug("Air-Server's faultCode = {}", faultCode);
            printAirLog(transaction, time, operator, faultCode);
            throw new AIRException(Integer.parseInt(faultCode));
        }
        if (responseMap.get(AIRDefines.responseCode) != null) {
            String responseCode = String.valueOf(responseMap.get(AIRDefines.responseCode));
            CCATLogger.DEBUG_LOGGER.debug("Air-Server's responseCode = {}", responseCode);
            printAirLog(transaction, time, operator, responseCode);
        }
    }

    public void printAirLog(String transaction, Long start, String operator, String status) {
        CCATLogger.INTERFACE_LOGGER.info("Operator[" + operator + "] Transaction[" + transaction
                + "] Status[" + status + "] After[" + start + "] msec");
    }

    public void validateUCIPResponseCodes(String responseCode) throws AIRException {
        if (!responseCode.equals(AIRDefines.UCIPCodes.SUCCESSFULL)
                && !responseCode.equals(AIRDefines.UCIPCodes.GRACE)
                && !responseCode.equals(AIRDefines.UCIPCodes.AFTER_GRACE)) {
            throw new AIRException(Integer.parseInt(responseCode));
        } else {
            CCATLogger.DEBUG_LOGGER.debug("UICP Commands Response Code is [{}]", responseCode);
        }
    }

    public void validateACIPResponseCodes(String responseCode) throws AIRException {
        if (!responseCode.equals(AIRDefines.ACIPCodes.SUCCESSFULL)) {
            CCATLogger.DEBUG_LOGGER.debug("Error while doing ACIP Commands [" + responseCode
                    + "]");

            throw new AIRException(Integer.parseInt(responseCode));
        } else {
            CCATLogger.DEBUG_LOGGER.debug("ACIP Commands Response Code is [" + responseCode
                    + "]");
        }
    }

    public void validateVCIPResponseCodes(String responseCode) throws AIRVoucherException {
        if (!responseCode.equals(AIRDefines.VCIPCodes.SUCCESSFULL)) {
            CCATLogger.DEBUG_LOGGER.debug("Error while doing VCIP Commands [" + responseCode
                    + "]");
            throw new AIRVoucherException(Integer.parseInt(responseCode));
        } else {
            CCATLogger.DEBUG_LOGGER.debug("VCIP Commands Response Code is [" + responseCode
                    + "]");
        }
    }

    public boolean isNumberValid(String number, String numberPattern) {
        String pattern = "^" + numberPattern + "\\d*$";

        return Pattern.matches(pattern, number);

    }
}
