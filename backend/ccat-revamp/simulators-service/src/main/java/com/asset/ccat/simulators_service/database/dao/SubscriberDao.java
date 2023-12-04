package com.asset.ccat.simulators_service.database.dao;


import com.asset.ccat.simulators_service.defines.Defines.SEVERITY;
import com.asset.ccat.simulators_service.defines.ErrorCodes;
import com.asset.ccat.simulators_service.exceptions.SimulatorsException;
import com.asset.ccat.simulators_service.logger.CCATLogger;
import com.asset.ccat.simulators_service.models.AccountDetailsModel;
import com.asset.ccat.simulators_service.models.UCIPModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * @author mahmoud.shehab
 */
@Repository
public class SubscriberDao {


  private final JdbcTemplate jdbcTemplate;

  @Autowired
  SubscriberDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }
//  public boolean getAccountDetails(String msisdn) {
//    Connection connection = null;
//    Statement statement = null;
//    ResultSet resultSet = null;
//    boolean isExists = false;
//    try {
//      System.out.println("getAccountDetails");
//      connection = DBManager.getSharedConnection();
//      String sql = "SELECT * FROM SIM_UCIP_ACCOUNT_DETAILS where SUBSCRIBER = " + msisdn;
//      statement = connection.createStatement();
//      resultSet = statement.executeQuery(sql);
//
//      if (resultSet.next()) {
//        isExists = true;
//      }
//    } catch (Exception ex) {
//      System.out.println("error");
//    } finally {
//      if (resultSet != null) {
//        try {
//          resultSet.close();
//        } catch (SQLException ex) {
//          Logger.getLogger(SubscriberDao.class.getName()).log(Level.SEVERE, null, ex);
//        }
//      }
//      if (statement != null) {
//        try {
//          statement.close();
//        } catch (SQLException ex) {
//          Logger.getLogger(SubscriberDao.class.getName()).log(Level.SEVERE, null, ex);
//        }
//      }
//      if (connection != null) {
//        try {
//          connection.close();
//        } catch (SQLException ex) {
//          Logger.getLogger(SubscriberDao.class.getName()).log(Level.SEVERE, null, ex);
//        }
//      }
//    }
//    return isExists;
//  }

  public List<AccountDetailsModel> getAccountDetails(String msisdn) throws SimulatorsException {
    CCATLogger.DEBUG_LOGGER.debug("Starting getAccountDetails()");
    StringBuilder query = new StringBuilder("");
    try {
      query.append(
          "SELECT * FROM SIM_UCIP_ACCOUNT_DETAILS WHERE SUBSCRIBER = ?");
      CCATLogger.DEBUG_LOGGER.debug("query = " + query);

      return jdbcTemplate.query(query.toString(),BeanPropertyRowMapper.newInstance(AccountDetailsModel.class),msisdn );
    } catch (Exception e) {
      CCATLogger.DEBUG_LOGGER.error("Database error occurred while executing  [" + query + "]");
      CCATLogger.ERROR_LOGGER.error(
          "Database error occurred while executing  [" + query + "]", e);
      throw new SimulatorsException(ErrorCodes.ERROR.DATABASE_ERROR, SEVERITY.ERROR,
          e.getMessage());
    }
  }

  public int installMsisdn(String msisdn,String languageId,String serviceClassId) throws SimulatorsException {
    CCATLogger.DEBUG_LOGGER.debug("Starting installMsisdn()");
    StringBuilder query = new StringBuilder("");
    try {
      query.append(
          "INSERT INTO SIM_UCIP_ACCOUNT_DETAILS (SUBSCRIBER,LANGUAGE_ID,SERVICE_CLASS_ID) VALUES (?,?,?)");
      CCATLogger.DEBUG_LOGGER.debug("query = " + query);

      return jdbcTemplate.update(query.toString(), msisdn,languageId,serviceClassId);
    } catch (Exception e) {
      CCATLogger.DEBUG_LOGGER.error("Database error occurred while executing  [" + query + "]");
      CCATLogger.ERROR_LOGGER.error(
          "Database error occurred while executing  [" + query + "]", e);
      throw new SimulatorsException(ErrorCodes.ERROR.DATABASE_ERROR, SEVERITY.ERROR,
          e.getMessage());
    }
  }

  //  public int installMsisdn(String msisdn) {
//    Connection connection = null;
//    Statement statement = null;
//    Integer resultSet = null;
//    try {
//      System.out.println("getAccountDetails");
//      connection = DBManager.getSharedConnection();
//      String sql = "INSERT INTO SIM_UCIP_ACCOUNT_DETAILS (SUBSCRIBER) VALUES (" + msisdn + ")";
//      statement = connection.createStatement();
//      resultSet = statement.executeUpdate(sql);
//
//    } catch (Exception ex) {
//      System.out.println("error");
//    } finally {
//      if (statement != null) {
//        try {
//          statement.close();
//        } catch (SQLException ex) {
//          Logger.getLogger(SubscriberDao.class.getName()).log(Level.SEVERE, null, ex);
//        }
//      }
//      if (connection != null) {
//        try {
//          connection.close();
//        } catch (SQLException ex) {
//          Logger.getLogger(SubscriberDao.class.getName()).log(Level.SEVERE, null, ex);
//        }
//      }
//    }
//    return resultSet;
//  }
  public int deleteMsisdn(String msisdn) throws SimulatorsException {
    CCATLogger.DEBUG_LOGGER.debug("Starting deleteMsisdn()");
    StringBuilder query = new StringBuilder("");
    try {
      query.append(
          "DELETE FROM SIM_UCIP_ACCOUNT_DETAILS WHERE SUBSCRIBER = ?");
      CCATLogger.DEBUG_LOGGER.debug("query = " + query);

      return jdbcTemplate.update(query.toString(), msisdn);
    } catch (Exception e) {
      CCATLogger.DEBUG_LOGGER.error("Database error occurred while executing  [" + query + "]");
      CCATLogger.ERROR_LOGGER.error(
          "Database error occurred while executing  [" + query + "]", e);
      throw new SimulatorsException(ErrorCodes.ERROR.DATABASE_ERROR, SEVERITY.ERROR,
          e.getMessage());
    }
  }
//  public int DeleteMsisdn(String msisdn) {
//    Connection connection = null;
//    Statement statement = null;
//    Integer resultSet = null;
//    try {
//      System.out.println("getAccountDetails");
//      connection = DBManager.getSharedConnection();
//      String sql = "DELETE FROM SIM_UCIP_ACCOUNT_DETAILS WHERE SUBSCRIBER = " + msisdn;
//      statement = connection.createStatement();
//      resultSet = statement.executeUpdate(sql);
//
//    } catch (Exception ex) {
//      System.out.println("error");
//    } finally {
//      if (statement != null) {
//        try {
//          statement.close();
//        } catch (SQLException ex) {
//          Logger.getLogger(SubscriberDao.class.getName()).log(Level.SEVERE, null, ex);
//        }
//      }
//      if (connection != null) {
//        try {
//          connection.close();
//        } catch (SQLException ex) {
//          Logger.getLogger(SubscriberDao.class.getName()).log(Level.SEVERE, null, ex);
//        }
//      }
//    }
//    return resultSet;
//  }


  public int updateAccountDetails(String msisdn, String languageId) throws SimulatorsException {
    CCATLogger.DEBUG_LOGGER.debug("Starting updateAccountDetails()");
    StringBuilder query = new StringBuilder("");
    try {
//      Integer languageIdInt = Integer.parseInt(languageId);
      query.append(
              "UPDATE SIM_UCIP_ACCOUNT_DETAILS SET LANGUAGE_ID = ? WHERE SUBSCRIBER = ? ");

      CCATLogger.DEBUG_LOGGER.debug("query = " + query);

      return jdbcTemplate.update(query.toString(), languageId , msisdn);
    } catch (Exception e) {
      CCATLogger.DEBUG_LOGGER.error("Database error occurred while executing  [" + query + "]");
      CCATLogger.ERROR_LOGGER.error(
              "Database error occurred while executing  [" + query + "]", e);
      throw new SimulatorsException(ErrorCodes.ERROR.DATABASE_ERROR, SEVERITY.ERROR,
              e.getMessage());
    }
  }

  public int updateServiceClass(String msisdn, String serviceClassId) throws SimulatorsException {
    CCATLogger.DEBUG_LOGGER.debug("Starting updateServiceClass()");
    StringBuilder query = new StringBuilder("");
    try {
      query.append(
              "UPDATE SIM_UCIP_ACCOUNT_DETAILS SET SERVICE_CLASS_ID = ? WHERE SUBSCRIBER = ? ");

      CCATLogger.DEBUG_LOGGER.debug("query = " + query);

      return jdbcTemplate.update(query.toString(), serviceClassId , msisdn);
    } catch (Exception e) {
      CCATLogger.DEBUG_LOGGER.error("Database error occurred while executing  [" + query + "]");
      CCATLogger.ERROR_LOGGER.error(
              "Database error occurred while executing  [" + query + "]", e);
      throw new SimulatorsException(ErrorCodes.ERROR.DATABASE_ERROR, SEVERITY.ERROR,
              e.getMessage());
    }
  }

  public int updateBalance(String msisdn , String adjustmentAmount ) throws SimulatorsException {
    CCATLogger.DEBUG_LOGGER.debug("Starting updateBalance()");
    StringBuilder query = new StringBuilder("");
    try {
      query.append(
              "UPDATE SIM_UCIP_ACCOUNT_DETAILS SET ADJUSTMENT_AMOUNT= ? WHERE SUBSCRIBER = ? ");

      CCATLogger.DEBUG_LOGGER.debug("query = " + query);

      return jdbcTemplate.update(query.toString(),
             adjustmentAmount,
              msisdn);

//      jdbcTemplate.update((Connection connection) -> {
//        PreparedStatement ps = connection.prepareStatement(query.toString());
//
//        ps.setString(1, adjustmentAmount);
//        ps.setString(2, msisdn);
//        return ps;
//      });
//      return 1;
    } catch (Exception e) {
      CCATLogger.DEBUG_LOGGER.error("Database error occurred while executing  [" + query + "]");
      CCATLogger.ERROR_LOGGER.error(
              "Database error occurred while executing  [" + query + "]", e);
      throw new SimulatorsException(ErrorCodes.ERROR.DATABASE_ERROR, SEVERITY.ERROR,
              e.getMessage());
    }
  }
}
