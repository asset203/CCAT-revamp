package com.asset.ccat.gateway.validators.admins;


import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.admin.foot_print.GetFootPrintReportRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Assem.Hassan
 */
@Component
public class FootPrintReportValidator {

    public void validateGetFootPrintReport(GetFootPrintReportRequest getFootPrintReportRequest) throws GatewayException {
        if ((Objects.isNull(getFootPrintReportRequest.getSearchFootPrintReport().getMsisdn()))
                && (Objects.isNull(getFootPrintReportRequest.getSearchFootPrintReport().getUserName()))
                && (Objects.isNull(getFootPrintReportRequest.getSearchFootPrintReport().getDateFrom()))
                && (Objects.isNull(getFootPrintReportRequest.getSearchFootPrintReport().getDateTo()))) {

            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "Entering Any Field ");
        }
    }
}
