package com.asset.ccat.ci_service.adapters;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * @author Mahmoud Shehab
 */
public class ProductDateAdapter extends XmlAdapter<String, Long> {

    private final DateTimeFormatter dateFormat = new DateTimeFormatterBuilder()
            .appendPattern("dd-MMM-")
            .appendValueReduced(ChronoField.YEAR, 2, 2, 1980)
            .toFormatter();

    @Override
    public String marshal(Long dateTime) {
        return null;
    }

    @Override
    public Long unmarshal(String dateTime) {
        return LocalDate.parse(dateTime, dateFormat).toEpochDay() * 24 * 60 * 60 * 1000;
    }

}
