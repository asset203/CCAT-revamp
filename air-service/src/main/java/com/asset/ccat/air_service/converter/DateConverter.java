/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import java.util.Date;

/**
 *
 * @author Mahmoud Shehab
 */
public class DateConverter extends StdConverter<Long, Date> {

    @Override
    public Date convert(final Long value) {
        return new Date((value));
    }

}
