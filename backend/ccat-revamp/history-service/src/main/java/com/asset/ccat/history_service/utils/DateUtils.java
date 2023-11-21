/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.history_service.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahmoud Shehab
 */
@Component
public class DateUtils {

    private final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
    private final String DB_DATE_FORMAT = "MM/dd/yyyy";

    public String getCurrentFormattedDate() {
        DateTimeFormatter dssDateFormat = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
        return dssDateFormat.format(LocalDateTime.now());
    }

    /**
     * @param date
     * @return DEFAULT_FORMAT = "dd/MM/yyyy";
     */
    public String getDefaultFormattedDate(Date date) {
        DateTimeFormatter defaultFormat = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
        return defaultFormat.format(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
    }
    
    public String getDBFormattedDate(Date date) {
        DateTimeFormatter dbFormat = DateTimeFormatter.ofPattern(DB_DATE_FORMAT);
        return dbFormat.format(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
    }

}
