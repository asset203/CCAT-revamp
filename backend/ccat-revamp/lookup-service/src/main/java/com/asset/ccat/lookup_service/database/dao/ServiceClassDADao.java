/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.database.dao;

import com.asset.ccat.lookup_service.annotation.LogExecutionTime;
import com.asset.ccat.lookup_service.database.extractors.migration.ServiceClassesMigrationExtractor;
import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.DedicatedAccount;
import com.asset.ccat.lookup_service.models.migration.MigrationModel;
import com.asset.ccat.lookup_service.models.migration.ServiceClassesMigrationSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @author wael.mohamed
 */
@Repository
public class ServiceClassDADao {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    ServiceClassesMigrationExtractor daClassExtractor;

    private String addServiceClassDAQuery;
    private String deleteServiceClassDAQuery;
    private String retrieveServiceClassesDATableQuery;

    @LogExecutionTime
    public boolean addRecordToServiceClassDA(Integer serviceClassId, DedicatedAccount dedAcc) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting LookUpDAO - addRecordInServiceClassDA");
        try {
            if (addServiceClassDAQuery == null) {
                addServiceClassDAQuery = "INSERT INTO "
                        + DatabaseStructs.ADM_SERVICE_CLASS_DA.TABLE_NAME
                        + "(" + DatabaseStructs.ADM_SERVICE_CLASS_DA.SERVICE_CLASS_ID
                        + "," + DatabaseStructs.ADM_SERVICE_CLASS_DA.DA_ID
                        + "," + DatabaseStructs.ADM_SERVICE_CLASS_DA.DESCRIPTION
                        + "," + DatabaseStructs.ADM_SERVICE_CLASS_DA.RATING_FACTOR + " ) "
                        + "values (?,?,?,?)";
            }
            CCATLogger.DEBUG_LOGGER.debug("addServiceClassDAQuery = " + addServiceClassDAQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending LookUpDAO - addRecordInServiceClassDA");
            return jdbcTemplate.update(addServiceClassDAQuery,
                    serviceClassId, dedAcc.getDaID(), dedAcc.getDescription(), dedAcc.getRattingFactor()) != 0;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("addServiceClassDAQuery :" + addServiceClassDAQuery + "\n ex --> " + ex);
            CCATLogger.ERROR_LOGGER.error("addServiceClassDAQuery :" + addServiceClassDAQuery + "\n ex --> ", ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public boolean deleteRecordsFromServiceClassDA(Integer serviceClassId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting ADM_SERVICE_CLASS_DA - deleteRecords with ID " + serviceClassId);
        try {
            if (deleteServiceClassDAQuery == null) {
                deleteServiceClassDAQuery = "DELETE FROM "
                        + DatabaseStructs.ADM_SERVICE_CLASS_DA.TABLE_NAME
                        + " WHERE "
                        + DatabaseStructs.ADM_SERVICE_CLASS_DA.SERVICE_CLASS_ID
                        + " = ?";
            }
            CCATLogger.DEBUG_LOGGER.debug("deleteServiceClassDAQuery = " + deleteServiceClassDAQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending ADM_SERVICE_CLASS_DA - deleteRecords");
            return jdbcTemplate.update(deleteServiceClassDAQuery, serviceClassId) != 0;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("deleteServiceClassDAQuery :" + deleteServiceClassDAQuery + "\n ex --> " + ex);
            CCATLogger.ERROR_LOGGER.error("deleteServiceClassDAQuery :" + deleteServiceClassDAQuery + "\n ex --> ", ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public MigrationModel retrieveServiceClassesDATable() throws LookupException {
        try {
            if (retrieveServiceClassesDATableQuery == null) {
                retrieveServiceClassesDATableQuery = " SELECT "
                        + DatabaseStructs.ADM_SERVICE_CLASS_DA.TABLE_NAME + "." + "*" + ","
                        + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASSES.CODE
                        + " FROM "
                        + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME
                        + " INNER JOIN "
                        + DatabaseStructs.ADM_SERVICE_CLASS_DA.TABLE_NAME
                        + " ON " + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASSES.CODE
                        + " = " + DatabaseStructs.ADM_SERVICE_CLASS_DA.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASS_DA.SERVICE_CLASS_ID
                        + " Order By "
                        + DatabaseStructs.ADM_SERVICE_CLASSES.NAME
                        +" ASC ";
            }
            CCATLogger.DEBUG_LOGGER.debug("retrieveServiceClassesDATableQuery " + retrieveServiceClassesDATableQuery);

            return jdbcTemplate.query(retrieveServiceClassesDATableQuery,
                    daClassExtractor.setTableName(DatabaseStructs.ADM_SERVICE_CLASS_DA.TABLE_NAME));
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveServiceClassesDATableQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveServiceClassesDATableQuery, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public void mergeServiceClassesDA(MigrationModel migrationModel, HashSet<Integer> existingCodes, ArrayList<ServiceClassesMigrationSummary> summary) throws LookupException {
        try {
            CCATLogger.DEBUG_LOGGER.debug("Start retrieving existing service classes DA");
            HashMap<Integer, HashSet<Integer>> existingDAs = retrieveServiceClassesDAIds();
            CCATLogger.DEBUG_LOGGER.debug("retrieved existing service classes DA successfully");

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
            CCATLogger.DEBUG_LOGGER.debug("Service Classes DA built insert statement : " + insertStmtBuilder.toString());

            // build update statement
            updateStmtBuilder.append("UPDATE ")
                    .append(migrationModel.getTableName())
                    .append(" SET ");

            for (int i = 0; i < columns.size(); i++) {
                if (columns.get(i).equals(DatabaseStructs.ADM_SERVICE_CLASSES.CODE)
                        || columns.get(i).equals(DatabaseStructs.ADM_SERVICE_CLASS_DA.SERVICE_CLASS_ID)
                        || columns.get(i).equals(DatabaseStructs.ADM_SERVICE_CLASS_DA.DA_ID)) {
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
                    .append(DatabaseStructs.ADM_SERVICE_CLASS_DA.SERVICE_CLASS_ID)
                    .append(" = ? ")
                    .append(" AND ")
                    .append(DatabaseStructs.ADM_SERVICE_CLASS_DA.DA_ID)
                    .append(" = ?");
            CCATLogger.DEBUG_LOGGER.debug("Service Classes built update statement : " + updateStmtBuilder.toString());

            //construct query parameters for insert & update statements
            for (HashMap<String, String> dataRowMap : migrationModel.getData()) {
                List<Object> dataRowList = new ArrayList<>();
                Integer code = Integer.valueOf(dataRowMap.get(DatabaseStructs.ADM_SERVICE_CLASSES.CODE));
                Integer daId = Integer.valueOf(dataRowMap.get(DatabaseStructs.ADM_SERVICE_CLASS_DA.DA_ID));
                boolean isInsert = (existingDAs.get(code) == null || !existingDAs.get(code).contains(daId));
                ServiceClassesMigrationSummary summaryItem = new ServiceClassesMigrationSummary(migrationModel.getTableName());
                summaryItem.setIdentifier(String.valueOf(daId));

                // skip row if service class doesnt exist
                if(!existingCodes.contains(code)){
                    summaryItem.setAction(isInsert? "Insert": "Update");
                    summaryItem.setStatus("Failed");
                    summaryItem.setStatusMessage("Service class with code ["+code+"] doesn't exist");
                    summaryMap.put(daId, summaryItem);
                    continue;
                }

                for (String column : columns) {
                    if (column.equals(DatabaseStructs.ADM_SERVICE_CLASSES.CODE)) {
                        continue;
                    }
                    if (column.equals(DatabaseStructs.ADM_SERVICE_CLASS_DA.SERVICE_CLASS_ID)) {
                        if (isInsert) {
                            dataRowList.add(code);
                        }
                    } else if (column.equals(DatabaseStructs.ADM_SERVICE_CLASS_DA.DA_ID)) {
                        if (isInsert) {
                            dataRowList.add(daId);
                        }
                    } else {
                        dataRowList.add(dataRowMap.get(column));
                    }
                }
                if (isInsert) {
                    CCATLogger.DEBUG_LOGGER.debug("Insert service class DA for service class with code [" + code + "]");
                    summaryItem.setAction("Insert");
                    inserts.put(daId, dataRowList);
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("Update service class DA for service class with code [" + code + "]");
                    summaryItem.setAction("Update");
                    dataRowList.add(code);
                    dataRowList.add(daId);
                    updates.put(daId, dataRowList);
                }
                summaryItem.setStatus("Success");
                summaryMap.put(daId, summaryItem);
            }

            //execute batch insert
            CCATLogger.DEBUG_LOGGER.debug("Start executing service class DA batch insert of size[" + inserts.size() + "]");
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
            CCATLogger.DEBUG_LOGGER.debug("Start executing service class DA batch update of size [" + updates.size() + "]");
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
            CCATLogger.DEBUG_LOGGER.debug("Finished merging service classes DA");
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while merging service classes DA");
            CCATLogger.ERROR_LOGGER.error("Error while merging service classes DA", ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public HashMap<Integer, HashSet<Integer>> retrieveServiceClassesDAIds() throws LookupException {
        try {
            String query = " SELECT "
                    + DatabaseStructs.ADM_SERVICE_CLASS_DA.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASS_DA.DA_ID + ","
                    + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASSES.CODE
                    + " FROM "
                    + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME
                    + " INNER JOIN "
                    + DatabaseStructs.ADM_SERVICE_CLASS_DA.TABLE_NAME
                    + " ON " + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASSES.CODE
                    + " = " + DatabaseStructs.ADM_SERVICE_CLASS_DA.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASS_DA.SERVICE_CLASS_ID;

            CCATLogger.DEBUG_LOGGER.debug("retrieveServiceClassesDAIds query " + query);

            return jdbcTemplate.query(query,
                    new ResultSetExtractor<HashMap<Integer, HashSet<Integer>>>() {
                        @Override
                        public HashMap<Integer, HashSet<Integer>> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                            HashMap<Integer, HashSet<Integer>> idMap = new HashMap<>();

                            while (resultSet.next()) {
                                int code = resultSet.getInt(DatabaseStructs.ADM_SERVICE_CLASSES.CODE);
                                int id = resultSet.getInt(DatabaseStructs.ADM_SERVICE_CLASS_DA.DA_ID);
                                if (idMap.get(code) == null) {
                                    idMap.put(code, new HashSet<>());
                                }
                                idMap.get(code).add(id);
                            }
                            return idMap;
                        }
                    });
        } catch (Exception ex) {
            CCATLogger.ERROR_LOGGER.debug("error while retrieving Service classes DA ids ");
            CCATLogger.ERROR_LOGGER.error("error while retrieving Service classes DA ids: ", ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }



}
