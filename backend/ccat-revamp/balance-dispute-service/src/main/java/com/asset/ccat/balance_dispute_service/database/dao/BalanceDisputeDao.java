package com.asset.ccat.balance_dispute_service.database.dao;

import com.asset.ccat.balance_dispute_service.annotation.LogExecutionTime;
import com.asset.ccat.balance_dispute_service.configrations.Properties;
import com.asset.ccat.balance_dispute_service.defines.DatabaseStructs;
import com.asset.ccat.balance_dispute_service.defines.ErrorCodes;
import com.asset.ccat.balance_dispute_service.dto.models.BalanceDisputeInterfaceDataModel;
import com.asset.ccat.balance_dispute_service.dto.models.SPParameterModel;
import com.asset.ccat.balance_dispute_service.dto.requests.SubscriberRequest;
import com.asset.ccat.balance_dispute_service.exceptions.BalanceDisputeException;
import com.asset.ccat.balance_dispute_service.logger.CCATLogger;
import com.asset.ccat.balance_dispute_service.managers.BalanceDisputeServiceManager;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import oracle.jdbc.internal.OracleTypes;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

@Repository
public class BalanceDisputeDao {


  private final JdbcTemplate jdbcTemplate;

  private final Properties properties;
  private final BeanPropertyRowMapper<BalanceDisputeInterfaceDataModel> balanceDisputeInterfaceDataModelBeanPropertyRowMapper = new BeanPropertyRowMapper<>(
      BalanceDisputeInterfaceDataModel.class);
  private String retrieveAllSPsDataQuery;

  public BalanceDisputeDao(JdbcTemplate jdbcTemplate, Properties properties) {
    this.jdbcTemplate = jdbcTemplate;
    this.properties = properties;
  }

  @LogExecutionTime
  public List<BalanceDisputeInterfaceDataModel> retrieveAllSPsData()
      throws BalanceDisputeException {
    CCATLogger.DEBUG_LOGGER.debug("BalanceDisputeDao - retrieveAllSPsData() : Started");
    try {
      if (retrieveAllSPsDataQuery == null) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ")
            .append(DatabaseStructs.BALANCE_DISPUTE_INTERFACE.TABLE_NAME);
        retrieveAllSPsDataQuery = query.toString();
      }
      CCATLogger.DEBUG_LOGGER.debug(
          "BalanceDisputeDao - retrieveAllSPsData() : Ended Successfully");
      return jdbcTemplate.query(retrieveAllSPsDataQuery,
          balanceDisputeInterfaceDataModelBeanPropertyRowMapper);
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("error while getting Balance Dispute Interface Data. {}", ex.getMessage());
      CCATLogger.ERROR_LOGGER.error("error while getting Balance Dispute Interface Data. ", ex);
      throw new BalanceDisputeException(ErrorCodes.ERROR.DATABASE_ERROR, null, ex.getMessage());
    }
  }

  @LogExecutionTime
  public Map<String, Object> callStoredFunction(String storedFunctionName,
      List<SPParameterModel> parameters) throws BalanceDisputeException {
    CCATLogger.DEBUG_LOGGER.debug("Start executing stored function --> [{}]", storedFunctionName);
    try {
      HikariDataSource dataSource = BalanceDisputeServiceManager.BALANCE_DISPUTE_DATASOURCE;

      SimpleJdbcCall call = new SimpleJdbcCall(dataSource)
          .withFunctionName(storedFunctionName)
          .withoutProcedureColumnMetaDataAccess();

      call.addDeclaredParameter(
          new SqlOutParameter("RESULTS", OracleTypes.CURSOR, new ColumnMapRowMapper()));

      MapSqlParameterSource inputParameters = new MapSqlParameterSource();

      for (SPParameterModel parameter : parameters) {
        call.addDeclaredParameter(new SqlParameter(parameter.getParameterName(), Types.VARCHAR));
        inputParameters.addValue(parameter.getParameterName(), parameter.getParameterValue());
      }

      Map<String, Object> results = call.execute(inputParameters);

      CCATLogger.DEBUG_LOGGER.debug( "stored function {} : Ended Successfully", storedFunctionName);
      return results;

    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("error while execute sql function. ", ex);
      CCATLogger.ERROR_LOGGER.error("error while execute sql function. ", ex);
      throw new BalanceDisputeException(ErrorCodes.ERROR.DATABASE_ERROR, null, ex.getMessage());
    }
  }

  @LogExecutionTime
  public Map<String, Object> callStoredProcedure(SubscriberRequest request) throws BalanceDisputeException {
    CCATLogger.DEBUG_LOGGER.debug("stored function {} : Started",
        "GET_DATA_PARTIAL_CDRS");
    try {
      HikariDataSource dataSource = BalanceDisputeServiceManager.BALANCE_DISPUTE_DATASOURCE;

      SimpleJdbcCall call = new SimpleJdbcCall(dataSource)
          .withProcedureName("GET_DATA_PARTIAL_CDRS")
          .withoutProcedureColumnMetaDataAccess();

      call.addDeclaredParameter(new SqlParameter("MSISDN", OracleTypes.NUMBER));
      call.addDeclaredParameter(
          new SqlOutParameter("PARTIAL_CDRS", OracleTypes.CURSOR, new ColumnMapRowMapper()));
      call.addDeclaredParameter(
          new SqlOutParameter("ERROR_CODE", OracleTypes.NUMBER, new ColumnMapRowMapper()));

      MapSqlParameterSource inputParameters = new MapSqlParameterSource();
      inputParameters.addValue("MSISDN", request.getMsisdn());

      Map<String, Object> results = call.execute(inputParameters);

      CCATLogger.DEBUG_LOGGER.debug(
          "BalanceDisputeDao - callStoredFunction() for stored function {} : Ended Successfully",
          "GET_DATA_PARTIAL_CDRS");
      return results;

    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("error while execute sql stored procedure. ", ex);
      CCATLogger.ERROR_LOGGER.error("error while execute sql stored procedure. ", ex);
      throw new BalanceDisputeException(ErrorCodes.ERROR.DATABASE_ERROR, null, ex.getMessage());
    }

  }
//    @LogExecutionTime
//    public Map callSlGetAdjFn() throws BalanceDisputeException {
//        CCATLogger.DEBUG_LOGGER.debug("BalanceDisputeDao - callSlGetAdjFn() : Started");
//        try {
//            HikariDataSource dataSource = BalanceDisputeServiceManager.BALANCE_DISPUTE_DATASOURCE;
//
//            SimpleJdbcCall call = new SimpleJdbcCall(dataSource)
//                    .withFunctionName(properties.getSlGetAdjFn())
//                    .declareParameters(
//                            new SqlOutParameter("RESULTS", OracleTypes.CURSOR, new ColumnMapRowMapper()),
//                            new SqlParameter("MOB_NO", Types.VARCHAR),
//                            new SqlParameter("DATE1", Types.VARCHAR),
//                            new SqlParameter("DATE2", Types.VARCHAR),
//                            new SqlParameter("ADJ_TYPE", Types.INTEGER)
//                    ).withoutProcedureColumnMetaDataAccess();
//
//            SqlParameterSource inputParameters = new MapSqlParameterSource()
//                    .addValue("MOB_NO", "-1")
//                    .addValue("DATE1", "-1")
//                    .addValue("DATE2", "-1")
//                    .addValue("ADJ_TYPE", 0);
//
//            Map results = call.execute(inputParameters);
//
//            CCATLogger.DEBUG_LOGGER.debug("BalanceDisputeDao - callSlGetAdjFn() : Ended Successfully");
//            return results;
//
//        }catch (Exception ex) {
//            CCATLogger.DEBUG_LOGGER.debug("error while execute " + properties.getSlGetAdjFn());
//            CCATLogger.ERROR_LOGGER.error("error while execute " + properties.getSlGetAdjFn()+ ex.getMessage());
//            throw new BalanceDisputeException(ErrorCodes.ERROR.DATABASE_ERROR,null,ex.getMessage());
//        }
//
//    }
//    @LogExecutionTime
//    public Map callGetMocPreFnRaNew4() throws BalanceDisputeException {
//        CCATLogger.DEBUG_LOGGER.debug("BalanceDisputeDao - callGetMocPreFnRaNew4() : Started");
//        try {
//            HikariDataSource dataSource = BalanceDisputeServiceManager.BALANCE_DISPUTE_DATASOURCE;
//
//            SimpleJdbcCall call = new SimpleJdbcCall(dataSource)
//                    .withFunctionName(properties.getGetMocPreFnRaNew4())
//                    .declareParameters(
//                            new SqlOutParameter("RESULTS", OracleTypes.CURSOR, new ColumnMapRowMapper()),
//                            new SqlParameter("p_msisdn", Types.VARCHAR),
//                            new SqlParameter("DATE1", Types.VARCHAR),
//                            new SqlParameter("DATE2", Types.VARCHAR),
//                            new SqlParameter("TYPE", Types.INTEGER)
//                    ).withoutProcedureColumnMetaDataAccess();
//
//            SqlParameterSource inputParameters = new MapSqlParameterSource()
//                    .addValue("p_msisdn", "-1")
//                    .addValue("DATE1", "-1")
//                    .addValue("DATE2", "-1")
//                    .addValue("TYPE", 0);
//
//            Map<String, Object> results = call.execute(inputParameters);
//
//            CCATLogger.DEBUG_LOGGER.debug("BalanceDisputeDao - callGetMocPreFnRaNew4() : Ended Successfully");
//            return results;
//
//        }catch (Exception ex) {
//            CCATLogger.DEBUG_LOGGER.debug("error while execute " + properties.getGetMocPreFnRaNew4() );
//            CCATLogger.ERROR_LOGGER.error("error while execute " + properties.getGetMocPreFnRaNew4()+ ex.getMessage());
//            throw new BalanceDisputeException(ErrorCodes.ERROR.DATABASE_ERROR,null,ex.getMessage());
//        }
//
//    }
//    @LogExecutionTime
//    public Map callGetLastDateFn() throws BalanceDisputeException {
//        CCATLogger.DEBUG_LOGGER.debug("BalanceDisputeDao - callGetLastDateFn() : Started");
//        try {
//            HikariDataSource dataSource = BalanceDisputeServiceManager.BALANCE_DISPUTE_DATASOURCE;
//
//            SimpleJdbcCall call = new SimpleJdbcCall(dataSource)
//                    .withFunctionName(properties.getGetLastDateFn())
//                    .declareParameters(
//                            new SqlOutParameter("RESULTS", OracleTypes.CURSOR, new ColumnMapRowMapper()),
//                            new SqlParameter("MOB_NO", Types.VARCHAR),
//                            new SqlParameter("TYPE", Types.INTEGER)
//                    ).withoutProcedureColumnMetaDataAccess();
//
//            SqlParameterSource inputParameters = new MapSqlParameterSource()
//                    .addValue("MOB_NO", "-1")
//                    .addValue("TYPE", 0);
//
//            Map<String, Object> results = call.execute(inputParameters);
//
//
//            CCATLogger.DEBUG_LOGGER.debug("BalanceDisputeDao - callGetLastDateFn() : Ended Successfully");
//            return results;
//
//        }catch (Exception ex) {
//            CCATLogger.DEBUG_LOGGER.debug("error while execute " + properties.getGetLastDateFn());
//            CCATLogger.ERROR_LOGGER.error("error while execute " + properties.getGetLastDateFn()+ ex.getMessage());
//            throw new BalanceDisputeException(ErrorCodes.ERROR.DATABASE_ERROR,null,ex.getMessage());
//        }
//
//    }

}
