/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.ods_service.utils;

import com.asset.ccat.ods_service.configurations.Properties;
import com.asset.ccat.ods_service.logger.CCATLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author Mahmoud Shehab
 */
@Component
public class DateUtils {

    private final Properties properties;

    private final DateTimeFormatter dssDateFormat;
    private final DateTimeFormatter flexShareDateFormat;

    @Autowired
    public DateUtils(Properties properties) {
        this.properties = properties;
        dssDateFormat = DateTimeFormatter.ofPattern(properties.getDssDateFormat());
        flexShareDateFormat = DateTimeFormatter.ofPattern(properties.getFlexShareHistoryDateFormat());
    }

    public String getCurrentFormattedDate() {
        return dssDateFormat.format(LocalDateTime.now());
    }

    public String getDssFormattedDate(Date date) {
        return dssDateFormat.format(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
    }

    public String getFlexShareFormattedDate(Date date) {
        return flexShareDateFormat.format(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
    }

    public String getStringDate(Long date) {
        try {
            if (date == null)
                throw new IllegalArgumentException("Date cannot be null");
            Date dateObj = new Date(date);
            SimpleDateFormat dateFormat = new SimpleDateFormat(properties.getDssDateFormat());
            return dateFormat.format(dateObj);
        } catch (Exception ex){
            CCATLogger.DEBUG_LOGGER.warn("Exception while parsing date[{}] with format: {}", date, properties.getDssDateFormat());
            CCATLogger.DEBUG_LOGGER.error("ex: ", ex);
            return "";
        }
    }

}
