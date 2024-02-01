package com.asset.ccat.gateway.validators;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.report.*;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author mohamed.metwaly
 */
@Component
public class ReportsValidator {
    public void getAllGeneralReportValidator(GetDssReportRequest request) throws GatewayValidationException {
        CCATLogger.DEBUG_LOGGER.debug("Start Dss report validation");
        if (Objects.isNull(request.getMsisdn())) {
            CCATLogger.DEBUG_LOGGER.debug("missing msisdn");
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        } else if (Objects.isNull(request.getDateTo())) {
            CCATLogger.DEBUG_LOGGER.debug("missing dateTo");
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "dateTo");
        } else if (Objects.isNull(request.getDateFrom())) {
            CCATLogger.DEBUG_LOGGER.debug("missing dateFrom");
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "dateFrom");
        } else if (Objects.isNull(request.getPagination().getOffset())) {
            CCATLogger.DEBUG_LOGGER.debug("missing offset");
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "offset");
        } else if (Objects.isNull(request.getPagination().getFetchCount())) {
            CCATLogger.DEBUG_LOGGER.debug("missing fetchCount");
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "fetchCount");
        } else if (Objects.isNull(request.getPagination().getIsGetAll())) {
            CCATLogger.DEBUG_LOGGER.debug("missing isGetAll");
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "isGetAll");
        }
        CCATLogger.DEBUG_LOGGER.debug("end Dss report validation successfully");

    }

    public void getOutGoingViewReportValidator(GetOutgoingViewReportRequest request) throws GatewayValidationException {
        this.getAllGeneralReportValidator(request);
        CCATLogger.DEBUG_LOGGER.debug("Start outgoing view report validation");
        if (Objects.isNull(request.getFlag())) {
            CCATLogger.DEBUG_LOGGER.debug("missing flag");
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "flag");
        }
        CCATLogger.DEBUG_LOGGER.debug("end outgoing view report validation successfully");
    }

    public void getContractBalanceTransferReportValidator(GetContractBalanceTransferReportRequest request) throws GatewayValidationException {

        this.getAllGeneralReportValidator(request);
        CCATLogger.DEBUG_LOGGER.debug("Start contract balance transfer report validation");
        if (Objects.isNull(request.getFlag())) {
            CCATLogger.DEBUG_LOGGER.debug("missing flag");

            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "flag");
        }
        CCATLogger.DEBUG_LOGGER.debug("end contract balance transfer report validation successfully");
    }

    public void getComplaintViewReportValidator(GetComplaintViewReportRequest request) throws GatewayValidationException {
        this.getAllGeneralReportValidator(request);
        CCATLogger.DEBUG_LOGGER.debug("Start complaint view  report validation");
        if (Objects.isNull(request.getFlag())) {
            CCATLogger.DEBUG_LOGGER.debug("missing flag");
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "flag");
        }
        CCATLogger.DEBUG_LOGGER.debug("end complaint view report validation successfully");
    }
    public void getContractBillReportValidator(GetContractBillReportRequest request) throws GatewayValidationException {
        CCATLogger.DEBUG_LOGGER.debug("Start contract bill report validation");
        if (Objects.isNull(request.getMsisdn())) {
            CCATLogger.DEBUG_LOGGER.debug("missing msisdn");
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        } else if (Objects.isNull(request.getNumOfBill())) {
            CCATLogger.DEBUG_LOGGER.debug("missing numOfBill");
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "numOfBill");
        } else if (Objects.isNull(request.getReportType())) {
            CCATLogger.DEBUG_LOGGER.debug("missing reportType");
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "reportType");
        } else if (Objects.isNull(request.getPagination().getOffset())) {
            CCATLogger.DEBUG_LOGGER.debug("missing offset");
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "offset");
        } else if (Objects.isNull(request.getPagination().getFetchCount())) {
            CCATLogger.DEBUG_LOGGER.debug("missing fetchCount");
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "fetchCount");
        } else if (Objects.isNull(request.getPagination().getIsGetAll())) {
            CCATLogger.DEBUG_LOGGER.debug("missing isGetAll");
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "isGetAll");
        }
        CCATLogger.DEBUG_LOGGER.debug("end contract bill  report validation successfully");

    }
}
