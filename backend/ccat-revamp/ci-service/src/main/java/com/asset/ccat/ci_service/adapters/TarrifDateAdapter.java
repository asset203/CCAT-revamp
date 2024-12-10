package com.asset.ccat.ci_service.adapters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author Mahmoud Shehab
 */
public class TarrifDateAdapter extends XmlAdapter<String, Long> {

    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Override
    public String marshal(Long dateTime) {
        return null;
    }

    @Override
    public Long unmarshal(String dateTime) {
        if (dateTime != null && !"".equals(dateTime)) {
            return (LocalDate.parse(dateTime, dateFormat).toEpochDay() * 24 * 60 * 60);
        }
        return null;
    }

}
