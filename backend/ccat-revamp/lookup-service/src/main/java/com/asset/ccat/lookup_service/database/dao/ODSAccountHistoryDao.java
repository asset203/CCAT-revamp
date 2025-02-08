/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.database.dao;

import com.asset.ccat.lookup_service.annotation.LogExecutionTime;
import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.ods_models.ODSActivityDetailsMapping;
import com.asset.ccat.lookup_service.models.ods_models.ODSActivityHeader;
import com.asset.ccat.lookup_service.models.ods_models.ODSActivityHeaderMapping;
import com.asset.ccat.lookup_service.models.ods_models.ODSActivityModel;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Mahmoud Shehab
 */
@Repository
public class ODSAccountHistoryDao {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @LogExecutionTime
  public HashMap<String, ODSActivityModel> retrieveOdsActivities() {
    HashMap<String, ODSActivityModel> result = null;
    String sqlStatement = "";
    try {
      sqlStatement = " Select * "
          + " From " + DatabaseStructs.ODS_H_ACC_ACTIVITIES.TABLE_NAME
          + " Where " + DatabaseStructs.ODS_H_ACC_ACTIVITIES.IS_DELETED + " = 0"
          + " Order By "
          + DatabaseStructs.ODS_H_ACC_ACTIVITIES.ACTIVITY_NAME
          + " ASC ";
      result = jdbcTemplate.query(sqlStatement, (ResultSet rs) -> {
        HashMap<String, ODSActivityModel> map = new HashMap<>();

        while (rs.next()) {
          ODSActivityModel model = new ODSActivityModel();
          model.withActivityId(rs.getInt(DatabaseStructs.ODS_H_ACC_ACTIVITIES.ACTIVITY_ID))
              .withActivityName(rs.getString(DatabaseStructs.ODS_H_ACC_ACTIVITIES.ACTIVITY_NAME))
              .withTableType(rs.getString(DatabaseStructs.ODS_H_ACC_ACTIVITIES.TABLE_TYPE))
              .withDrIdIdx(rs.getInt(DatabaseStructs.ODS_H_ACC_ACTIVITIES.DR_ID_IDX))
              .withDaAmountIdx(rs.getInt(DatabaseStructs.ODS_H_ACC_ACTIVITIES.DA_AMOUNT_IDX))
              .withAdjActionIdx(rs.getInt(DatabaseStructs.ODS_H_ACC_ACTIVITIES.ADJ_ACTION_IDX))
              .withBalanceIdx(rs.getInt(DatabaseStructs.ODS_H_ACC_ACTIVITIES.BALANCE_IDX))
              .withExpDateIdx(rs.getInt(DatabaseStructs.ODS_H_ACC_ACTIVITIES.EXP_DATE_IDX))
              .withIsNewFormatIdx(rs.getInt(DatabaseStructs.ODS_H_ACC_ACTIVITIES.IS_NEW_FORMAT_IDX))
                  .withScIdx(rs.getString(DatabaseStructs.ODS_H_ACC_ACTIVITIES.SC_IDX));
          map.put(model.getTableType(), model);
        }
        return map;
      });
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("error while executing: " + sqlStatement);
      CCATLogger.ERROR_LOGGER.error("error while executing: " + sqlStatement, ex);
    }
    return result;
  }

  @LogExecutionTime
  public HashMap<Integer, ODSActivityHeader> retrieveOdsHeaders() {
    HashMap<Integer, ODSActivityHeader> result = null;
    String sqlStatement = "";
    try {
      sqlStatement = " Select * "
          + " From " + DatabaseStructs.ODS_H_ACC_HEADERS.TABLE_NAME
          + " Where " + DatabaseStructs.ODS_H_ACC_HEADERS.IS_DELETED + " = 0"
          + " Order By "
          + DatabaseStructs.ODS_H_ACC_HEADERS.HEADER_NAME
          + " ASC ";
      result = jdbcTemplate.query(sqlStatement, (ResultSet rs) -> {
        HashMap<Integer, ODSActivityHeader> map = new HashMap<>();

        while (rs.next()) {
          ODSActivityHeader model = new ODSActivityHeader();
          model.withHeaderId(rs.getInt(DatabaseStructs.ODS_H_ACC_HEADERS.HEADER_ID))
              .withAHeaderName(rs.getString(DatabaseStructs.ODS_H_ACC_HEADERS.HEADER_NAME))
              .withHeaderType(rs.getString(DatabaseStructs.ODS_H_ACC_HEADERS.HEADER_TYPE))
              .withDisplayName(rs.getString(DatabaseStructs.ODS_H_ACC_HEADERS.DISPLAY_NAME))
              .withOrder(rs.getInt(DatabaseStructs.ODS_H_ACC_HEADERS.ORDER));
          map.put(model.getHeaderId(), model);
        }
        return map;
      });
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("error while executing: " + sqlStatement);
      CCATLogger.ERROR_LOGGER.error("error while executing: " + sqlStatement, ex);
    }
    return result;
  }

  @LogExecutionTime
  public HashMap<Integer, HashMap<Integer, ODSActivityHeaderMapping>> retrieveOdsHeadersMapping() {
    HashMap<Integer, HashMap<Integer, ODSActivityHeaderMapping>> result = null;
    String sqlStatement = "";
    try {
      sqlStatement = " Select * "
          + " From " + DatabaseStructs.ODS_H_ACC_HEADERS_MAPPING.TABLE_NAME
          + " Where " + DatabaseStructs.ODS_H_ACC_HEADERS_MAPPING.IS_DELETED + " = 0";
      result = jdbcTemplate.query(sqlStatement, (ResultSet rs) -> {
        HashMap<Integer, HashMap<Integer, ODSActivityHeaderMapping>> resultMap = new HashMap<>();

        while (rs.next()) {
          Integer activityId = rs.getInt(DatabaseStructs.ODS_H_ACC_HEADERS_MAPPING.ACTIVITY_ID);
          Integer headerId = rs.getInt(DatabaseStructs.ODS_H_ACC_HEADERS_MAPPING.HEADER_ID);

          if (resultMap.get(activityId) != null) {
            HashMap<Integer, ODSActivityHeaderMapping> mappingMap = resultMap.get(
                activityId);
            ODSActivityHeaderMapping model = new ODSActivityHeaderMapping();

            String columnIdxString = rs.getString(
                DatabaseStructs.ODS_H_ACC_HEADERS_MAPPING.COLUMN_IDX);
            Integer columnIdx = null;
            if (columnIdxString != null && !"".equals(columnIdxString)) {
              columnIdx = Integer.parseInt(columnIdxString);
            }
            model.withHeaderId(headerId)
                .withActivityId(activityId)
                .withColumnIdx(columnIdx)
                .withIsStatic(rs.getInt(DatabaseStructs.ODS_H_ACC_HEADERS_MAPPING.IS_STATIC))
                .withCustomFormat(
                    rs.getString(DatabaseStructs.ODS_H_ACC_HEADERS_MAPPING.CUSTOM_FORMAT))
                .withPreConditions(
                    rs.getString(DatabaseStructs.ODS_H_ACC_HEADERS_MAPPING.PRE_CONDITIONS))
                .withDefaultValue(
                    rs.getString(DatabaseStructs.ODS_H_ACC_HEADERS_MAPPING.DEFAULT_VALUE))
                .withPreConditionsValue(
                    rs.getString(DatabaseStructs.ODS_H_ACC_HEADERS_MAPPING.PRE_CONDITIONS_VALUE));
            mappingMap.put(model.getHeaderId(), model);
          } else {
            HashMap<Integer, ODSActivityHeaderMapping> mappingMap = new HashMap<>();
            ODSActivityHeaderMapping model = new ODSActivityHeaderMapping();
            String columnIdxString = rs.getString(
                DatabaseStructs.ODS_H_ACC_HEADERS_MAPPING.COLUMN_IDX);
            Integer columnIdx = null;
            if (columnIdxString != null && !"".equals(columnIdxString)) {
              columnIdx = Integer.parseInt(columnIdxString);
            }
            model.withHeaderId(headerId)
                .withActivityId(activityId)
                .withColumnIdx(columnIdx)
                .withIsStatic(rs.getInt(DatabaseStructs.ODS_H_ACC_HEADERS_MAPPING.IS_STATIC))
                .withCustomFormat(
                    rs.getString(DatabaseStructs.ODS_H_ACC_HEADERS_MAPPING.CUSTOM_FORMAT))
                .withPreConditions(
                    rs.getString(DatabaseStructs.ODS_H_ACC_HEADERS_MAPPING.PRE_CONDITIONS))
                .withDefaultValue(
                    rs.getString(DatabaseStructs.ODS_H_ACC_HEADERS_MAPPING.DEFAULT_VALUE))
                .withPreConditionsValue(
                    rs.getString(DatabaseStructs.ODS_H_ACC_HEADERS_MAPPING.PRE_CONDITIONS_VALUE));
            mappingMap.put(model.getHeaderId(), model);
            resultMap.put(activityId, mappingMap);
          }
        }
        return resultMap;
      });
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("error while executing: " + sqlStatement);
      CCATLogger.ERROR_LOGGER.error("error while executing: " + sqlStatement, ex);
    }
    return result;
  }

  @LogExecutionTime
  public HashMap<Integer, List<ODSActivityDetailsMapping>> retrieveOdsDetailsMapping() {
    HashMap<Integer, List<ODSActivityDetailsMapping>> result = null;
    String sqlStatement = "";
    try {
      sqlStatement = " Select * "
          + " From " + DatabaseStructs.ODS_H_ACC_ACTIVITY_DETAILS.TABLE_NAME
          + " Where " + DatabaseStructs.ODS_H_ACC_ACTIVITY_DETAILS.IS_DELETED + " = 0"
          + " Order By "
          + DatabaseStructs.ODS_H_ACC_ACTIVITY_DETAILS.DISPLAY_ORDER
          + " ASC  ";
      result = jdbcTemplate.query(sqlStatement, (ResultSet rs) -> {
        HashMap<Integer, List<ODSActivityDetailsMapping>> resultMap = new HashMap<>();

        while (rs.next()) {
          Integer activityId = rs.getInt(DatabaseStructs.ODS_H_ACC_ACTIVITY_DETAILS.ACTIVITY_ID);
          if (resultMap.get(activityId) != null) {
            ArrayList<ODSActivityDetailsMapping> list = (ArrayList) resultMap.get(activityId);
            ODSActivityDetailsMapping model = new ODSActivityDetailsMapping();
            model.withActivityId(activityId)
                .withColumnIdx(rs.getInt(DatabaseStructs.ODS_H_ACC_ACTIVITY_DETAILS.COLUMN_IDX))
                .withColumnKey(rs.getString(DatabaseStructs.ODS_H_ACC_ACTIVITY_DETAILS.COLUMN_KEY))
                .withDisplayName(
                    rs.getString(DatabaseStructs.ODS_H_ACC_ACTIVITY_DETAILS.DISPLAY_NAME))
                .withDetailType(
                    rs.getString(DatabaseStructs.ODS_H_ACC_ACTIVITY_DETAILS.DETAIL_TYPE));
            list.add(model);
          } else {
            ArrayList<ODSActivityDetailsMapping> list = new ArrayList<>();
            ODSActivityDetailsMapping model = new ODSActivityDetailsMapping();
            model.withActivityId(activityId)
                .withColumnIdx(rs.getInt(DatabaseStructs.ODS_H_ACC_ACTIVITY_DETAILS.COLUMN_IDX))
                .withColumnKey(rs.getString(DatabaseStructs.ODS_H_ACC_ACTIVITY_DETAILS.COLUMN_KEY))
                .withDisplayName(
                    rs.getString(DatabaseStructs.ODS_H_ACC_ACTIVITY_DETAILS.DISPLAY_NAME))
                .withDetailType(
                    rs.getString(DatabaseStructs.ODS_H_ACC_ACTIVITY_DETAILS.DETAIL_TYPE));
            list.add(model);
            resultMap.put(activityId, list);
          }
        }
        return resultMap;
      });
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("error while executing: " + sqlStatement);
      CCATLogger.ERROR_LOGGER.error("error while executing: " + sqlStatement, ex);
    }
    return result;
  }

}
