package com.asset.ccat.dynamic_page.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class DateUtil {

    public Object getDateFormatted(Long timeephoch, String dateFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        Date date = new Date(timeephoch);
        return formatter.format(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
    }

    public Object getTimeepochFromDate(Object date) {
        if (date instanceof java.sql.Date) {
            return ((java.sql.Date) date).getTime();
        } else if (date instanceof java.util.Date) {
            return ((java.util.Date) date).getTime();
        } else {
            return date; // in case any other return object as it is
        }
    }
}
