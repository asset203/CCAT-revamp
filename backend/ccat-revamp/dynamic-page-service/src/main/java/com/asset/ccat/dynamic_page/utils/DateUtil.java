package com.asset.ccat.dynamic_page.utils;

import com.asset.ccat.dynamic_page.logger.CCATLogger;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

@Component
public class DateUtil {

    public Object getDateFormatted(Long timeephoch, String dateFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        Date date = new Date(timeephoch);
        return formatter.format(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
    }


    public Object getTimeepochFromDate(Object date, String format) {
        if (date instanceof java.sql.Date) {
            if (format != null && !format.isEmpty()) {
                return new SimpleDateFormat(format).format((java.sql.Date) date);
            }
            return ((java.sql.Date) date).getTime();
        } else if (date instanceof java.util.Date) {
            if (format != null && !format.isEmpty()) {
                return new SimpleDateFormat(format).format((java.util.Date) date);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            sdf.setTimeZone(TimeZone.getTimeZone("Africa/Cairo"));  // Use Egypt's timezone properly
            return sdf.format((java.util.Date) date);
        } else if (date instanceof String && format != null && !format.isBlank()) {
            String dateStr = (String) date;
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(format);
            DateTimeFormatter isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateStr, isoFormatter);
            return outputFormatter.format(zonedDateTime);
        } else {
            CCATLogger.DEBUG_LOGGER.debug("Date without format " + date.toString());
        return date; // in case any other return object as it is
        }
    }

}
