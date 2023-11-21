package com.asset.ccat.lookup_service.database.dao;

import com.asset.ccat.lookup_service.annotation.LogExecutionTime;
import com.asset.ccat.lookup_service.database.extractors.migration.ServiceClassesMigrationExtractor;
import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.ServiceOfferingPlanDescModel;
import com.asset.ccat.lookup_service.models.migration.MigrationModel;
import com.asset.ccat.lookup_service.models.migration.ServiceClassesMigrationSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Component
public class ServiceClassSOPlanDescDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ServiceClassesMigrationExtractor admClassMigrationExtractor;

    private String addServiceClassSOPlanDescription;
    private String deleteServiceClassSOPlanDescription;
    private String retrieveSCSOPlanDescTable;

    @LogExecutionTime
    public int addServiceClassSOPlanDescription(Integer serviceClassId, ServiceOfferingPlanDescModel descModel) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting ServiceClassSOPlanDescDao - addServiceClassSOPlanDescription()");

        try {
            if (addServiceClassSOPlanDescription == null) {
                StringBuilder query = new StringBuilder();
                query.append("INSERT INTO ")
                        .append(DatabaseStructs.ADM_SO_SC_DESCRIPTION.TABLE_NAME).append("(")
                        .append(DatabaseStructs.ADM_SO_SC_DESCRIPTION.PLAN_ID)
                        .append(",").append(DatabaseStructs.ADM_SO_SC_DESCRIPTION.SERVICE_CLASS_ID)
                        .append(",").append(DatabaseStructs.ADM_SO_SC_DESCRIPTION.DESCRIPTION)
                        .append(") ")
                        .append("values ( ?,?,?)");
                addServiceClassSOPlanDescription = query.toString();

            }
            int res = jdbcTemplate.update(addServiceClassSOPlanDescription,
                    descModel.getPlanId(),
                    serviceClassId,
                    descModel.getPlanDescription());
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + addServiceClassSOPlanDescription);
            CCATLogger.DEBUG_LOGGER.info("result is " + res);
            CCATLogger.DEBUG_LOGGER.debug("Ending ServiceClassSOPlanDescDao - addServiceClassSOPlanDescription()");
            return res;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public int deleteServiceClassSOPlanDescription(Integer serviceClassId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting ServiceOfferingPlansDao - deleteSOServiceClassDescription()");

        try {
            if (deleteServiceClassSOPlanDescription == null) {
                StringBuilder query = new StringBuilder();
                query.append("DELETE FROM ")
                        .append(DatabaseStructs.ADM_SO_SC_DESCRIPTION.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_SO_SC_DESCRIPTION.SERVICE_CLASS_ID)
                        .append("= ?");
                deleteServiceClassSOPlanDescription = query.toString();
            }
            int res = jdbcTemplate.update(deleteServiceClassSOPlanDescription, serviceClassId);
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + deleteServiceClassSOPlanDescription);
            CCATLogger.DEBUG_LOGGER.info("result is " + res);
            CCATLogger.DEBUG_LOGGER.debug("Ending ServiceOfferingPlansDao - deleteSOServiceClassDescription()");
            return res;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public MigrationModel retrieveServiceClassSOPlanDescTable() throws LookupException {
        try {
            if (retrieveSCSOPlanDescTable == null) {
                retrieveSCSOPlanDescTable = " SELECT "
                        + DatabaseStructs.ADM_SO_SC_DESCRIPTION.TABLE_NAME + "." + "*" + ","
                        + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASSES.CODE
                        + " FROM "
                        + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME
                        + " INNER JOIN "
                        + DatabaseStructs.ADM_SO_SC_DESCRIPTION.TABLE_NAME
                        + " ON " + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASSES.CODE
                        + " = " + DatabaseStructs.ADM_SO_SC_DESCRIPTION.TABLE_NAME + "." + DatabaseStructs.ADM_SO_SC_DESCRIPTION.SERVICE_CLASS_ID
                        + " Order By "
                        + DatabaseStructs.ADM_SERVICE_CLASSES.NAME
                        +" ASC ";
            }
            CCATLogger.DEBUG_LOGGER.debug("retrieveSCSOPlanDescTable " + retrieveSCSOPlanDescTable);

            return jdbcTemplate.query(retrieveSCSOPlanDescTable,
                    admClassMigrationExtractor.setTableName(DatabaseStructs.ADM_SO_SC_DESCRIPTION.TABLE_NAME));
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveSCSOPlanDescTable);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveSCSOPlanDescTable, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public HashMap<Integer, HashSet<Integer>> retrieveSCSODescriptionsMap() throws LookupException {
        try {
            String query = " SELECT "
                    + DatabaseStructs.ADM_SO_SC_DESCRIPTION.TABLE_NAME + "." + DatabaseStructs.ADM_SO_SC_DESCRIPTION.PLAN_ID + ","
                    + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASSES.CODE
                    + " FROM "
                    + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME
                    + " INNER JOIN "
                    + DatabaseStructs.ADM_SO_SC_DESCRIPTION.TABLE_NAME
                    + " ON " + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASSES.CODE
                    + " = " + DatabaseStructs.ADM_SO_SC_DESCRIPTION.TABLE_NAME + "." + DatabaseStructs.ADM_SO_SC_DESCRIPTION.SERVICE_CLASS_ID;

            CCATLogger.DEBUG_LOGGER.debug("retrieveSCSODescriptionsMap query " + query);

            return jdbcTemplate.query(query,
                    new ResultSetExtractor<HashMap<Integer, HashSet<Integer>>>() {
                        @Override
                        public HashMap<Integer, HashSet<Integer>> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                            HashMap<Integer, HashSet<Integer>> idMap = new HashMap<>();

                            while (resultSet.next()) {
                                int code = resultSet.getInt(DatabaseStructs.ADM_SERVICE_CLASSES.CODE);
                                int id = resultSet.getInt(DatabaseStructs.ADM_SO_SC_DESCRIPTION.PLAN_ID);
                                if (idMap.get(code) == null) {
                                    idMap.put(code, new HashSet<>());
                                }
                                idMap.get(code).add(id);
                            }
                            return idMap;
                        }
                    });
        } catch (Exception ex) {
            CCATLogger.ERROR_LOGGER.debug("error while retrieving SC SO Descriptions plan ids: ");
            CCATLogger.ERROR_LOGGER.error("error while retrieving SC SO Descriptions plan ids: ", ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public void mergeSCSODescriptions(MigrationModel migrationModel, HashSet<Integer> existingCodes, ArrayList<ServiceClassesMigrationSummary> summary) throws LookupException {

        try {
            CCATLogger.DEBUG_LOGGER.debug("Start retrieving existing service classes plans description");
            HashMap<Integer, HashSet<Integer>> existingPlanDescs = retrieveSCSODescriptionsMap();
            CCATLogger.DEBUG_LOGGER.debug("retrieved existing service classes plans description successfully");

            StringBuilder updateStmtBuilder = new StringBuilder();
            StringBuilder insertStmtBuilder = new StringBuilder();
            String insertStmtValuesPart = " VALUES (";

            HashMap<Integer, List<Object>> inserts = new HashMap<>();
            HashMap<Integer, List<Object>> updates = new HashMap<>();
            HashMap<Integer, ServiceClassesMigrationSummary> summaryMap = new HashMap<>();
            List<String> columns = migrationModel.getHeaders();

            // build insert statement
            insertStmtBuilder.append("INSERT INTO ")
                    .append(migrationModel.getTableName())
                    .append(" (");

            for (int i = 0; i < columns.size(); i++) {
                if (columns.get(i).equals(DatabaseStructs.ADM_SERVICE_CLASSES.CODE)) {
                    continue;
                }
                insertStmtBuilder.append(columns.get(i));
                insertStmtValuesPart += "?";
                if (i != (columns.size() - 1)) {
                    insertStmtBuilder.append(",");
                    insertStmtValuesPart += ",";
                }
            }

            //remove additional ,
            if (insertStmtBuilder.lastIndexOf(",") == insertStmtBuilder.length() - 1) {
                insertStmtBuilder.deleteCharAt(insertStmtBuilder.length() - 1);
                insertStmtValuesPart = insertStmtValuesPart.substring(0, insertStmtValuesPart.length() - 1);
            }

            insertStmtBuilder.append(")").append(insertStmtValuesPart).append(")");
            CCATLogger.DEBUG_LOGGER.debug("Service Classes SO Plan Description built insert statement : " + insertStmtBuilder.toString());

            // build update statement
            updateStmtBuilder.append("UPDATE ")
                    .append(migrationModel.getTableName())
                    .append(" SET ");

            for (int i = 0; i < columns.size(); i++) {
                if (columns.get(i).equals(DatabaseStructs.ADM_SERVICE_CLASSES.CODE)
                        || columns.get(i).equals(DatabaseStructs.ADM_SERVICE_CLASS_ACC.SERVICE_CLASS_ID)
                        || columns.get(i).equals(DatabaseStructs.ADM_SO_SC_DESCRIPTION.PLAN_ID)) {
                    continue;
                }
                updateStmtBuilder.append(columns.get(i)).append(" = ?");
                if (i != (columns.size() - 1)) {
                    updateStmtBuilder.append(",");
                }
            }

            //remove additional ,
            if (updateStmtBuilder.lastIndexOf(",") == updateStmtBuilder.length() - 1) {
                updateStmtBuilder.deleteCharAt(updateStmtBuilder.length() - 1);
            }

            updateStmtBuilder.append(" WHERE ")
                    .append(DatabaseStructs.ADM_SO_SC_DESCRIPTION.SERVICE_CLASS_ID)
                    .append(" = ? ")
                    .append(" AND ")
                    .append(DatabaseStructs.ADM_SO_SC_DESCRIPTION.PLAN_ID)
                    .append(" = ?");
            CCATLogger.DEBUG_LOGGER.debug("Service Classes SO Plan Description built update statement : " + updateStmtBuilder.toString());

            //construct query parameters for insert & update statements
            for (HashMap<String, String> dataRowMap : migrationModel.getData()) {
                List<Object> dataRowList = new ArrayList<>();
                Integer code = Integer.valueOf(dataRowMap.get(DatabaseStructs.ADM_SERVICE_CLASSES.CODE));
                Integer planId = Integer.valueOf(dataRowMap.get(DatabaseStructs.ADM_SO_SC_DESCRIPTION.PLAN_ID));
                boolean isInsert = (existingPlanDescs.get(code) == null || !existingPlanDescs.get(code).contains(planId));
                ServiceClassesMigrationSummary summaryItem = new ServiceClassesMigrationSummary(migrationModel.getTableName());
                summaryItem.setIdentifier(String.valueOf(planId));

                //Skip row if Service class doesnt exist
                if (!existingCodes.contains(code)) {
                    summaryItem.setAction(isInsert ? "Insert" : "Update");
                    summaryItem.setStatus("Failed");
                    summaryItem.setStatusMessage("Service class with code [" + code + "] doesn't exist");
                    summaryMap.put(planId, summaryItem);
                    continue;
                }

                for (String column : columns) {
                    if (column.equals(DatabaseStructs.ADM_SERVICE_CLASSES.CODE)) {
                        continue;
                    }
                    if (column.equals(DatabaseStructs.ADM_SO_SC_DESCRIPTION.SERVICE_CLASS_ID)) {
                        if (isInsert) {
                            dataRowList.add(code);
                        }
                    } else if (column.equals(DatabaseStructs.ADM_SO_SC_DESCRIPTION.PLAN_ID)) {
                        if (isInsert) {
                            dataRowList.add(planId);
                        }
                    } else {
                        dataRowList.add(dataRowMap.get(column));
                    }
                }
                if (isInsert) {
                    CCATLogger.DEBUG_LOGGER.debug("Insert service classes SO Plan Description for service class of code [" + code + "]");
                    summaryItem.setAction("Insert");
                    inserts.put(planId, dataRowList);
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("Update service classes SO Plan Description for service class of code [" + code + "]");
                    summaryItem.setAction("Update");
                    dataRowList.add(code);
                    dataRowList.add(planId);
                    updates.put(planId, dataRowList);
                }
                summaryItem.setStatus("Success");
                summaryMap.put(planId, summaryItem);
            }

            //execute batch insert
            CCATLogger.DEBUG_LOGGER.debug("Start executing service class SO Plan Description batch insert of size[" + inserts.size() + "]");
            try (PreparedStatement pstmt = jdbcTemplate.getDataSource().getConnection().prepareStatement(insertStmtBuilder.toString());) {
                for (List<Object> columnValues : inserts.values()) {
                    int index = 1;
                    for (Object value : columnValues) {
                        pstmt.setObject(index++, value);
                    }
                    pstmt.addBatch();
                }
                int[] insertResult = pstmt.executeBatch();
                if (insertResult.length < inserts.size()) {
                    CCATLogger.DEBUG_LOGGER.error("Batch insert failed, some records weren't inserted");
                    throw new Exception("Batch insert failed, some records weren't inserted");
                }

            } catch (Exception ex) {
                CCATLogger.ERROR_LOGGER.debug("Batch insert failed with error [" + ex.getMessage() + "]");
                for (Integer id : inserts.keySet()) {
                    ServiceClassesMigrationSummary summaryItem = summaryMap.get(id);
                    summaryItem.setStatus("Failed");
                    summaryItem.setStatusMessage(ex.getMessage());
                }
            }

            //execute batch update
            CCATLogger.DEBUG_LOGGER.debug("Start executing service class SO Plan Description batch update of size[" + updates.size() + "]");
            try (PreparedStatement pstmt = jdbcTemplate.getDataSource().getConnection().prepareStatement(updateStmtBuilder.toString());) {
                for (List<Object> columnValues : updates.values()) {
                    int index = 1;
                    for (Object value : columnValues) {
                        pstmt.setObject(index++, value);
                    }
                    pstmt.addBatch();
                }
                int[] updateResult = pstmt.executeBatch();
                if (updateResult.length < updates.size()) {
                    CCATLogger.DEBUG_LOGGER.error("Batch update failed, some records weren't updated");
                    throw new Exception("Batch update failed, some records weren't updated");
                }
            } catch (Exception ex) {
                CCATLogger.ERROR_LOGGER.debug("Batch update failed with error [" + ex.getMessage() + "]");
                for (Integer id : updates.keySet()) {
                    ServiceClassesMigrationSummary summaryItem = summaryMap.get(id);
                    summaryItem.setStatus("Failed");
                    summaryItem.setStatusMessage(ex.getMessage());
                }
            }
            summary.addAll(summaryMap.values());
            CCATLogger.DEBUG_LOGGER.debug("Finished merging service classes SO Plan Description");
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while merging service classes SO Plan Description");
            CCATLogger.ERROR_LOGGER.error("Error while merging service classes SO Plan Description", ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }
}
