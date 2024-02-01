/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.util;

import com.asset.ccat.gateway.cache.MessagesCache;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.rabbitmq.models.FootprintModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Mahmoud Shehab
 */
@Component
public class GatewayUtil {

    @Autowired
    Properties properties;

    @Autowired
    MessagesCache messagesCache;

    public String dateToString(Date date) {
        SimpleDateFormat DateFor = new SimpleDateFormat(properties.getDateFormat());
        String stringDate = DateFor.format(date);
        return stringDate;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Integer d = Integer.parseInt(strNum);
            System.out.println(d);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static Integer lengthOfInteger(Integer number) {
        if (number < 100000) {
            if (number < 100) {
                if (number < 10) {
                    return 1;
                } else {
                    return 2;
                }
            } else {
                if (number < 1000) {
                    return 3;
                } else {
                    if (number < 10000) {
                        return 4;
                    } else {
                        return 5;
                    }
                }
            }
        } else {
            if (number < 10000000) {
                if (number < 1000000) {
                    return 6;
                } else {
                    return 7;
                }
            } else {
                if (number < 100000000) {
                    return 8;
                } else {
                    if (number < 1000000000) {
                        return 9;
                    } else {
                        return 10;
                    }
                }
            }
        }
    }

    public boolean isMsisdnValid(String msisdn) {
        return Objects.nonNull(msisdn)
                && !msisdn.isEmpty()
                && !msisdn.isBlank()
                && msisdn.matches(properties.getMsisdnRegex());
    }

    public FootprintModel footprintExceptionHandler(Throwable th, FootprintModel footprint) {
        String msg = "";
        if (th instanceof GatewayValidationException) {
            GatewayValidationException gex = ((GatewayValidationException) th);
            msg = messagesCache.getWarningMsg(gex.getErrorCode());
            if (!Objects.isNull(gex.getArgs())) {
                msg = messagesCache.replaceArgument(msg, gex.getArgs());
            }
            footprint.setErrorCode(Integer.toString(gex.getErrorCode()));
            footprint.setErrorMessage(msg);
            footprint.setStatus(Defines.FOOT_PRINT_STATUS.FAILED_STATUS);
        } else if (th instanceof GatewayException) {
            GatewayException gex = ((GatewayException) th);
            if (Objects.isNull(gex.getMessage()) && gex.getArgs() != null && gex.getArgs().length > 0) {
                msg = messagesCache.getErrorMsg(gex.getErrorCode());
                msg = messagesCache.replaceArgument(msg, gex.getArgs());
            } else if (!Objects.isNull(gex.getMessage())) {
                msg = gex.getMessage();
            } else {
                msg = messagesCache.getErrorMsg(gex.getErrorCode());
            }
            footprint.setErrorCode(Integer.toString(gex.getErrorCode()));
            footprint.setErrorMessage(msg);
            footprint.setStatus(Defines.FOOT_PRINT_STATUS.FAILED_STATUS);
        } else {
            footprint.setErrorCode(Integer.toString(ErrorCodes.ERROR.UNKNOWN_ERROR));
            footprint.setErrorMessage(messagesCache.getErrorMsg(ErrorCodes.ERROR.UNKNOWN_ERROR));
            footprint.setStatus(Defines.FOOT_PRINT_STATUS.FAILED_STATUS);
        }
        return footprint;
    }
}
