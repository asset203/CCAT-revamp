package com.asset.ccat.procedureservice.database.dao;

import com.asset.ccat.procedureservice.annotation.LogExecutionTime;
import com.asset.ccat.procedureservice.configrations.Properties;
import com.asset.ccat.procedureservice.defines.ErrorCodes;
import com.asset.ccat.procedureservice.dto.models.PaymentGatewayVoucherModel;
import com.asset.ccat.procedureservice.exceptions.ProcedureException;
import com.asset.ccat.procedureservice.logger.CCATLogger;
import com.asset.ccat.procedureservice.managers.ProcedureServiceManager;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class VoucherDao {

    private final Properties properties;

    public VoucherDao(Properties properties) {
        this.properties = properties;
    }

    @LogExecutionTime
    public PaymentGatewayVoucherModel getPaymentGatewayVoucher(String voucherSerial) throws ProcedureException {
        CCATLogger.DEBUG_LOGGER.debug("PGVoucherDAO - retrieveRecords() : Started");
        try {
            HikariDataSource hikariDataSource = ProcedureServiceManager.PG_DATASOURCE;
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(hikariDataSource)
                    .withProcedureName(properties.getVoucherPgDbProcedureName());
            SqlParameterSource in = new MapSqlParameterSource().addValue("voucher_serial", voucherSerial);
            Map<String, Object> out = jdbcCall.execute(in);

            PaymentGatewayVoucherModel paymentGatewayVoucherModel = new PaymentGatewayVoucherModel();
            paymentGatewayVoucherModel.setMsisdn(out.get("MSISDN").toString());
            paymentGatewayVoucherModel.setBillingNumber(Integer.valueOf(out.get("CUSTOMER_CODE").toString()));

            CCATLogger.DEBUG_LOGGER.debug("PGVoucherDAO - retrieveRecords() : Ended Successfully");
            return paymentGatewayVoucherModel;
        }catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.debug("error while execute " + properties.getVoucherPgDbProcedureName());
            CCATLogger.ERROR_LOGGER.error("error while execute " + properties.getVoucherPgDbProcedureName()+ ex.getMessage());
            throw new ProcedureException(ErrorCodes.ERROR.DATABASE_ERROR,null,ex.getMessage());
        }

    }
}
