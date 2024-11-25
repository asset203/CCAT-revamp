package com.asset.ccat.procedureservice.database.dao;

import com.asset.ccat.procedureservice.annotation.LogExecutionTime;
import com.asset.ccat.procedureservice.configrations.Properties;
import com.asset.ccat.procedureservice.defines.Defines;
import com.asset.ccat.procedureservice.defines.ErrorCodes;
import com.asset.ccat.procedureservice.dto.models.flex_share.FlexShareInquirySPResponse;
import com.asset.ccat.procedureservice.exceptions.ProcedureException;
import com.asset.ccat.procedureservice.logger.CCATLogger;
import com.asset.ccat.procedureservice.managers.ProcedureServiceManager;
import com.zaxxer.hikari.HikariDataSource;
import oracle.jdbc.OracleTypes;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class FlexShareDao {

    private final Properties properties;
    public FlexShareDao(Properties properties) {
        this.properties = properties;
    }



    @LogExecutionTime
    public FlexShareInquirySPResponse inquiry(String msisdn) throws ProcedureException {
        CCATLogger.DEBUG_LOGGER.debug("FlexShareDao - inquiry() : Started");
        try {
            HikariDataSource hikariDataSource = ProcedureServiceManager.FLEX_SHARE_DATASOURCE;
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(hikariDataSource)
                    .withProcedureName(properties.getFlexShareInquiryStoredProcedureName())
                    .declareParameters(
                            new SqlParameter(Defines.FLEX_SHARE.MSISDN_IN, OracleTypes.VARCHAR),
                            new SqlParameter(Defines.FLEX_SHARE.PROMO_ID_IN, OracleTypes.NUMBER),
                            new SqlParameter(Defines.FLEX_SHARE.CHANNEL_ID_IN, OracleTypes.NUMBER),
                            new SqlParameter(Defines.FLEX_SHARE.TRANS_TYPE_IN, OracleTypes.NUMBER),
                            new SqlOutParameter(Defines.FLEX_SHARE.BUNDLE_OUT,OracleTypes.NUMBER),
                            new SqlOutParameter(Defines.FLEX_SHARE.CUST_STATUS,OracleTypes.NUMBER),
                            new SqlOutParameter(Defines.FLEX_SHARE.INQUIRY_DATE,OracleTypes.DATE),
                            new SqlOutParameter(Defines.FLEX_SHARE.OPT_OUT_DATE,OracleTypes.DATE),
                            new SqlOutParameter(Defines.FLEX_SHARE.OPT_IN_DATE,OracleTypes.DATE),
                            new SqlOutParameter(Defines.FLEX_SHARE.OPT_IN_COUNT,OracleTypes.NUMBER),
                            new SqlOutParameter(Defines.FLEX_SHARE.PARAM_1_OUT,OracleTypes.VARCHAR),
                            new SqlOutParameter(Defines.FLEX_SHARE.PARAM_2_OUT,OracleTypes.VARCHAR),
                            new SqlOutParameter(Defines.FLEX_SHARE.PARAM_3_OUT,OracleTypes.VARCHAR),
                            new SqlOutParameter(Defines.FLEX_SHARE.PARAM_4_OUT,OracleTypes.VARCHAR),
                            new SqlOutParameter(Defines.FLEX_SHARE.PARAM_5_OUT,OracleTypes.VARCHAR),
                            new SqlOutParameter(Defines.FLEX_SHARE.PARAM_6_OUT,OracleTypes.VARCHAR),
                            new SqlOutParameter(Defines.FLEX_SHARE.PARAM_7_OUT,OracleTypes.VARCHAR),
                            new SqlOutParameter(Defines.FLEX_SHARE.PARAM_8_OUT,OracleTypes.VARCHAR),
                            new SqlOutParameter(Defines.FLEX_SHARE.PARAM_9_OUT,OracleTypes.VARCHAR),
                            new SqlOutParameter(Defines.FLEX_SHARE.PARAM_10_OUT,OracleTypes.VARCHAR),
                            new SqlOutParameter(Defines.FLEX_SHARE.PARAM_11_OUT,OracleTypes.VARCHAR),
                            new SqlOutParameter(Defines.FLEX_SHARE.STATUS_OUT,OracleTypes.NUMBER)
                    );
            MapSqlParameterSource in = new MapSqlParameterSource();
            in.addValue(Defines.FLEX_SHARE.MSISDN_IN, properties.getFlexShareMsisdnPrefix()+msisdn);
            in.addValue(Defines.FLEX_SHARE.PROMO_ID_IN, properties.getFlexSharePromoIdIn());
            in.addValue(Defines.FLEX_SHARE.CHANNEL_ID_IN, properties.getFlexShareChannelIdIn());
            in.addValue(Defines.FLEX_SHARE.TRANS_TYPE_IN, properties.getFlexShareTransTypeIn());

            Map<String, Object> out = jdbcCall.execute(in);

            FlexShareInquirySPResponse response = new FlexShareInquirySPResponse();
            response.setBundleOut(Integer.valueOf(out.get(Defines.FLEX_SHARE.BUNDLE_OUT).toString()));
            response.setParam2Out(out.get(Defines.FLEX_SHARE.PARAM_2_OUT).toString());
            response.setStatusOut(out.get(Defines.FLEX_SHARE.STATUS_OUT).toString());

            CCATLogger.DEBUG_LOGGER.debug("FlexShareDao - inquiry() : Ended Successfully");
            CCATLogger.DEBUG_LOGGER.debug("FlexShareDao - inquiry() : Ended Successfully : "+response);
            return response;
        }catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.debug("error while execute " + properties.getFlexShareInquiryStoredProcedureName());
            CCATLogger.ERROR_LOGGER.error("error while execute " + properties.getFlexShareInquiryStoredProcedureName()+ ex.getMessage());
            throw new ProcedureException(ErrorCodes.ERROR.DATABASE_ERROR,null,ex.getMessage()+"");
        }

    }
    @LogExecutionTime
    public Map<String, Object> update(String msisdn,String inputValue) throws ProcedureException {
        CCATLogger.DEBUG_LOGGER.debug("FlexShareDao - update() : Started");
        try {
            HikariDataSource hikariDataSource = ProcedureServiceManager.FLEX_SHARE_DATASOURCE;
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(hikariDataSource)
                    .withProcedureName(properties.getFlexShareUpdateStoredProcedureName())
                    .declareParameters(
                            new SqlParameter(Defines.FLEX_SHARE.MSISDN_IN, OracleTypes.VARCHAR),
                            new SqlParameter(Defines.FLEX_SHARE.PROMO_ID_IN, OracleTypes.NUMBER),
                            new SqlParameter(Defines.FLEX_SHARE.CHANNEL_ID_IN, OracleTypes.NUMBER),
                            new SqlParameter(Defines.FLEX_SHARE.TRANS_TYPE_IN, OracleTypes.NUMBER),
                            new SqlParameter(Defines.FLEX_SHARE.BUNDLE_OUT,OracleTypes.NUMBER),
                            new SqlOutParameter(Defines.FLEX_SHARE.CUST_STATUS,OracleTypes.NUMBER),
                            new SqlOutParameter(Defines.FLEX_SHARE.INQUIRY_DATE,OracleTypes.DATE),
                            new SqlOutParameter(Defines.FLEX_SHARE.OPT_OUT_DATE,OracleTypes.DATE),
                            new SqlOutParameter(Defines.FLEX_SHARE.OPT_IN_DATE,OracleTypes.DATE),
                            new SqlOutParameter(Defines.FLEX_SHARE.OPT_IN_COUNT,OracleTypes.NUMBER),
                            new SqlOutParameter(Defines.FLEX_SHARE.PARAM_1_OUT,OracleTypes.VARCHAR),
                            new SqlOutParameter(Defines.FLEX_SHARE.PARAM_2_OUT,OracleTypes.VARCHAR),
                            new SqlOutParameter(Defines.FLEX_SHARE.PARAM_3_OUT,OracleTypes.VARCHAR),
                            new SqlOutParameter(Defines.FLEX_SHARE.PARAM_4_OUT,OracleTypes.VARCHAR),
                            new SqlOutParameter(Defines.FLEX_SHARE.PARAM_5_OUT,OracleTypes.VARCHAR),
                            new SqlOutParameter(Defines.FLEX_SHARE.PARAM_6_OUT,OracleTypes.VARCHAR),
                            new SqlOutParameter(Defines.FLEX_SHARE.PARAM_7_OUT,OracleTypes.VARCHAR),
                            new SqlOutParameter(Defines.FLEX_SHARE.PARAM_8_OUT,OracleTypes.VARCHAR),
                            new SqlOutParameter(Defines.FLEX_SHARE.PARAM_9_OUT,OracleTypes.VARCHAR),
                            new SqlOutParameter(Defines.FLEX_SHARE.PARAM_10_OUT,OracleTypes.VARCHAR),
                            new SqlOutParameter(Defines.FLEX_SHARE.PARAM_11_OUT,OracleTypes.VARCHAR),
                            new SqlOutParameter(Defines.FLEX_SHARE.STATUS_OUT,OracleTypes.NUMBER)
                    );
            MapSqlParameterSource in = new MapSqlParameterSource();
            in.addValue(Defines.FLEX_SHARE.MSISDN_IN, properties.getFlexShareMsisdnPrefix()+msisdn);
            in.addValue(Defines.FLEX_SHARE.PROMO_ID_IN, properties.getFlexSharePromoIdIn());
            in.addValue(Defines.FLEX_SHARE.CHANNEL_ID_IN, properties.getFlexShareChannelIdIn());
            in.addValue(Defines.FLEX_SHARE.TRANS_TYPE_IN, properties.getFlexShareTransTypeIn());
            in.addValue(Defines.FLEX_SHARE.BUNDLE_OUT, inputValue);

            Map<String, Object> out = jdbcCall.execute(in);

            CCATLogger.DEBUG_LOGGER.debug("FlexShareDao - update() : Ended Successfully");
            CCATLogger.DEBUG_LOGGER.debug(out);
            return out;
        }catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.debug("error while execute " + properties.getFlexShareUpdateStoredProcedureName());
            CCATLogger.ERROR_LOGGER.error("error while execute " + properties.getFlexShareUpdateStoredProcedureName()+ ex.getMessage());
            throw new ProcedureException(ErrorCodes.ERROR.DATABASE_ERROR,null,ex.getMessage()+"");
        }

    }

}
