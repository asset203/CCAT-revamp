package com.asset.ccat.balance_dispute_service.database.dao;

import com.asset.ccat.balance_dispute_service.annotation.LogExecutionTime;
import com.asset.ccat.balance_dispute_service.configrations.Properties;
import com.asset.ccat.balance_dispute_service.defines.DatabaseStructs;
import com.asset.ccat.balance_dispute_service.defines.ErrorCodes;
import com.asset.ccat.balance_dispute_service.dto.models.BalanceDisputeInterfaceDataModel;
import com.asset.ccat.balance_dispute_service.dto.models.SPParameterModel;
import com.asset.ccat.balance_dispute_service.dto.requests.SubscriberRequest;
import com.asset.ccat.balance_dispute_service.exceptions.BalanceDisputeException;
import com.asset.ccat.balance_dispute_service.exceptions.BalanceDisputeFileException;
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

      CCATLogger.DEBUG_LOGGER.debug( "-----Calling stored function {} : Ended Successfully----", storedFunctionName);
      return results;

    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("error while execute sql function. ", ex);
      CCATLogger.ERROR_LOGGER.error("error while execute sql function. ", ex);
      throw new BalanceDisputeException(ErrorCodes.ERROR.DATABASE_ERROR, null);
    }
  }

  @LogExecutionTime
  public Map<String, Object> callStoredProcedure(SubscriberRequest request) throws BalanceDisputeFileException {
    String storedProcedureName = "GET_DATA_PARTIAL_CDRS";
    CCATLogger.DEBUG_LOGGER.debug("stored procedure {} : Started", storedProcedureName);
    try {
      HikariDataSource dataSource = BalanceDisputeServiceManager.BALANCE_DISPUTE_DATASOURCE;

      SimpleJdbcCall call = new SimpleJdbcCall(dataSource)
          .withProcedureName(storedProcedureName)
          .withoutProcedureColumnMetaDataAccess();

      call.addDeclaredParameter(new SqlParameter("MSISDN", OracleTypes.NUMBER));
      call.addDeclaredParameter(new SqlOutParameter("PARTIAL_CDRS", OracleTypes.CURSOR, new ColumnMapRowMapper()));
      call.addDeclaredParameter(new SqlOutParameter("ERROR_CODE", OracleTypes.NUMBER, new ColumnMapRowMapper()));

      MapSqlParameterSource inputParameters = new MapSqlParameterSource();
      inputParameters.addValue("MSISDN", request.getMsisdn());

      Map<String, Object> results = call.execute(inputParameters);

      CCATLogger.DEBUG_LOGGER.debug("stored procedure {} Ended: with response = {}", storedProcedureName, results);
      return results;

    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("error while execute sql stored procedure. ", ex);
      CCATLogger.ERROR_LOGGER.error("error while execute sql stored procedure. ", ex);
      throw new BalanceDisputeFileException(ErrorCodes.ERROR.DATABASE_ERROR, null, ex.getMessage());
    }
  }

}
