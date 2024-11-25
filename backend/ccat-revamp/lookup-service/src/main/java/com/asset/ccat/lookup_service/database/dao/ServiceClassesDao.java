/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.database.dao;

import com.asset.ccat.lookup_service.annotation.LogExecutionTime;
import com.asset.ccat.lookup_service.database.extractors.AdmServiceClassExtractor;
import com.asset.ccat.lookup_service.database.extractors.migration.ServiceClassesMigrationExtractor;
import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.AdmServiceClassModel;
import com.asset.ccat.lookup_service.models.migration.MigrationModel;
import com.asset.ccat.lookup_service.models.migration.ServiceClassesMigrationSummary;
import com.asset.ccat.lookup_service.models.responses.AdmServiceClass;
import com.asset.ccat.lookup_service.models.responses.AdmServiceClassResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author wael.mohamed
 */
@Repository
public class ServiceClassesDao {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    AdmServiceClassExtractor admClassExtractor;
    @Autowired
    ServiceClassesMigrationExtractor admClassMigrationExtractor;


    private String retrieveServiceClassByIdQuery;
    private String retrieveServiceClassQuery;
    private String retrieveServiceClassesTableQuery;
    private String addServiceClassQuery;
    private String updateServiceClassQuery;
    private String serviceClasshasChildQuery;
    private String deleteServiceClassQuery;

    @LogExecutionTime
    public AdmServiceClassModel retrieveServiceClassById(Integer serviceClassId) throws LookupException {
        try {
            if (retrieveServiceClassByIdQuery == null) {
                retrieveServiceClassByIdQuery = " SELECT "
                        + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASSES.NAME + ","
                        + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASSES.CODE + ","
                        + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASSES.PREACTIVATION_ALLOWED + ","
                        + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASSES.IS_GRANDFATHER + ","
                        + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASSES.IS_CI_CONVERSION + ","
                        + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASSES.CI_SERVICE_NAME + ","
                        + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASSES.CI_PACKAGE_NAME + ","
                        + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASSES.IS_ALLOW_MIGRATION + ","
                        + DatabaseStructs.ADM_SERVICE_CLASS_ACC.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASS_ACC.ACC_ID + ","
                        + DatabaseStructs.ADM_SERVICE_CLASS_ACC.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASS_ACC.DESCRIPTION + ","
                        + DatabaseStructs.ADM_SERVICE_CLASS_DA.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASS_DA.DA_ID + ","
                        + DatabaseStructs.ADM_SERVICE_CLASS_DA.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASS_DA.DESCRIPTION + " AS " + DatabaseStructs.ADM_SERVICE_CLASS_DA.DA_DESC + ","
                        + DatabaseStructs.ADM_SERVICE_CLASS_DA.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASS_DA.RATING_FACTOR + ","
                        + DatabaseStructs.ADM_SO_SC_DESCRIPTION.TABLE_NAME + "." + DatabaseStructs.ADM_SO_SC_DESCRIPTION.PLAN_ID + ","
                        + DatabaseStructs.ADM_SO_SC_DESCRIPTION.TABLE_NAME + "." + DatabaseStructs.ADM_SO_SC_DESCRIPTION.DESCRIPTION + " AS " + DatabaseStructs.ADM_SO_SC_DESCRIPTION.DESCRIPTION_ALIAS
                        + " FROM "
                        + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME
                        + " LEFT JOIN "
                        + DatabaseStructs.ADM_SERVICE_CLASS_ACC.TABLE_NAME
                        + " ON " + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASSES.CODE
                        + " = " + DatabaseStructs.ADM_SERVICE_CLASS_ACC.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASS_ACC.SERVICE_CLASS_ID
                        + " LEFT JOIN "
                        + DatabaseStructs.ADM_SERVICE_CLASS_DA.TABLE_NAME
                        + " ON " + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASSES.CODE
                        + " = " + DatabaseStructs.ADM_SERVICE_CLASS_DA.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASS_DA.SERVICE_CLASS_ID
                        + " LEFT JOIN "
                        + DatabaseStructs.ADM_SO_SC_DESCRIPTION.TABLE_NAME
                        + " ON " + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASSES.CODE
                        + " = " + DatabaseStructs.ADM_SO_SC_DESCRIPTION.TABLE_NAME + "." + DatabaseStructs.ADM_SO_SC_DESCRIPTION.SERVICE_CLASS_ID
                        + " WHERE " + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASSES.CODE
                        + " = ? AND "
                        + DatabaseStructs.ADM_SERVICE_CLASSES.IS_DELETED + " = 0 ";
            }
            CCATLogger.DEBUG_LOGGER.debug("retrieveServiceClassByIdQuery " + retrieveServiceClassByIdQuery);
            return jdbcTemplate.query(retrieveServiceClassByIdQuery, admClassExtractor, serviceClassId);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveServiceClassByIdQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveServiceClassByIdQuery, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public List<AdmServiceClassResponse> retrieveServiceClasses() throws LookupException {
        try {
            if (retrieveServiceClassQuery == null) {
                retrieveServiceClassQuery = "SELECT "
                        + DatabaseStructs.ADM_SERVICE_CLASSES.CODE
                        + " , "
                        + DatabaseStructs.ADM_SERVICE_CLASSES.NAME
                        + " FROM "
                        + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME
                        + " WHERE " + DatabaseStructs.ADM_SERVICE_CLASSES.IS_DELETED + " = 0 "
                        + " Order By "
                        + DatabaseStructs.ADM_SERVICE_CLASSES.NAME
                        +" ASC ";
            }
            CCATLogger.DEBUG_LOGGER.debug("retrieveServiceClassQuery : " + retrieveServiceClassQuery);

            List<AdmServiceClassResponse> list = jdbcTemplate.query(retrieveServiceClassQuery,
                    new BeanPropertyRowMapper<>(AdmServiceClassResponse.class));
            return list;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveServiceClassQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveServiceClassQuery, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public boolean addServiceClass(AdmServiceClass serviceClass) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting ServiceClassDAO - addServiceClass");
        int row = 0;
        try {
            if (addServiceClassQuery == null) {
                addServiceClassQuery = "INSERT INTO "
                        + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME
                        + "(" + DatabaseStructs.ADM_SERVICE_CLASSES.NAME + ","
                        + DatabaseStructs.ADM_SERVICE_CLASSES.CODE + ","
                        + DatabaseStructs.ADM_SERVICE_CLASSES.IS_GRANDFATHER + ","
                        + DatabaseStructs.ADM_SERVICE_CLASSES.PREACTIVATION_ALLOWED + ","
                        + DatabaseStructs.ADM_SERVICE_CLASSES.IS_CI_CONVERSION + ","
                        + DatabaseStructs.ADM_SERVICE_CLASSES.IS_ALLOW_MIGRATION + ","
                        + DatabaseStructs.ADM_SERVICE_CLASSES.CI_SERVICE_NAME + ","
                        + DatabaseStructs.ADM_SERVICE_CLASSES.CI_PACKAGE_NAME
                        + ") "
                        + "VALUES (?,?,?,?,?,?,?,?)";
            }

            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + addServiceClassQuery);

            row = jdbcTemplate.update(addServiceClassQuery,
                    serviceClass.getName(),
                    serviceClass.getCode(),
                    serviceClass.getIsGrandfather(),
                    (serviceClass.getPreActivationAllowed() == false ? 0 : 1),
                    (serviceClass.getIsCiConversion() == false ? 0 : 1),
                    (serviceClass.getIsAllowedMigration() == false ? 0 : 1),
                    serviceClass.getCiServiceName(),
                    serviceClass.getCiPackageName());
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + addServiceClassQuery + "\n ex " + ex);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + addServiceClassQuery, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
        return row != 0;
    }

    @LogExecutionTime
    public boolean updateServiceClass(AdmServiceClass serviceClass) throws LookupException {
        try {
            if (updateServiceClassQuery == null) {
                updateServiceClassQuery = "UPDATE " + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME
                        + " SET " + DatabaseStructs.ADM_SERVICE_CLASSES.NAME + " = ? ,"
                        + DatabaseStructs.ADM_SERVICE_CLASSES.IS_GRANDFATHER + " = ? ,"
                        + DatabaseStructs.ADM_SERVICE_CLASSES.PREACTIVATION_ALLOWED + " = ? ,"
                        + DatabaseStructs.ADM_SERVICE_CLASSES.IS_CI_CONVERSION + " = ? ,"
                        + DatabaseStructs.ADM_SERVICE_CLASSES.IS_ALLOW_MIGRATION + " = ? ,"
                        + DatabaseStructs.ADM_SERVICE_CLASSES.CI_SERVICE_NAME + " = ? ,"
                        + DatabaseStructs.ADM_SERVICE_CLASSES.CI_PACKAGE_NAME + " = ? "
                        + " WHERE " + DatabaseStructs.ADM_SERVICE_CLASSES.CODE + " = ? ";
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + updateServiceClassQuery);
            return jdbcTemplate.update(updateServiceClassQuery,
                    serviceClass.getName(),
                    (serviceClass.getIsGrandfather() == true ? 1 : 0),
                    (serviceClass.getPreActivationAllowed() == true ? 1 : 0),
                    (serviceClass.getIsCiConversion() == true ? 1 : 0),
                    (serviceClass.getIsAllowedMigration() == true ? 1 : 0),
                    (serviceClass.getCiServiceName()),
                    (serviceClass.getCiPackageName()),
                    serviceClass.getCode()) != 0;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + updateServiceClassQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + updateServiceClassQuery, ex);
            CCATLogger.DEBUG_LOGGER.debug("error while executing: " + updateServiceClassQuery + "\n " + ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public boolean serviceClasshasChild(int serviceClassId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting ServiceClassDAO - ServiceClasshasChild");
        try {
            if (serviceClasshasChildQuery == null) {
                serviceClasshasChildQuery = "SELECT * FROM "
                        + DatabaseStructs.ADM_BUSINESS_PLANS.TABLE_NAME
                        + " WHERE "
                        + DatabaseStructs.ADM_BUSINESS_PLANS.IS_DELETED + " = 0 "
                        + " AND "
                        + DatabaseStructs.ADM_BUSINESS_PLANS.CODE + " = ?";
            }
            CCATLogger.DEBUG_LOGGER.debug("serviceClasshasChildQuery = " + serviceClasshasChildQuery);
            int result = jdbcTemplate.update(serviceClasshasChildQuery, serviceClassId);
            CCATLogger.DEBUG_LOGGER.debug("Ending ServiceClassDAO - ServiceClasshasChild");
            return result > 0;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + serviceClasshasChildQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + serviceClasshasChildQuery, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public boolean deleteServiceClass(int serviceClassId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting ServiceClassDAO - deleteServiceClass");
        try {
            int i;
            deleteServiceClassQuery = "UPDATE "
                    + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME
                    + " SET "
                    + DatabaseStructs.ADM_SERVICE_CLASSES.IS_DELETED + " = 1 "
                    + " WHERE "
                    + DatabaseStructs.ADM_SERVICE_CLASSES.CODE + " = ?";
            CCATLogger.DEBUG_LOGGER.debug("deleteServiceClassQuery  = " + deleteServiceClassQuery);
            i = jdbcTemplate.update(deleteServiceClassQuery, serviceClassId);
            if (i == 1) {
                return true;
            }
            CCATLogger.DEBUG_LOGGER.debug("Ending ServiceClassDAO - deleteServiceClass");
            return false;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + deleteServiceClassQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + deleteServiceClassQuery, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }


    @LogExecutionTime
    public MigrationModel retrieveAdmServiceClassesTable() throws LookupException {
        try {
            if (retrieveServiceClassesTableQuery == null) {
                retrieveServiceClassesTableQuery = " SELECT *"
                        + " FROM "
                        + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME
                        + " WHERE " + DatabaseStructs.ADM_SERVICE_CLASSES.IS_DELETED + " = 0"
                        + " Order By "
                        + DatabaseStructs.ADM_SERVICE_CLASSES.NAME
                        +" ASC ";
            }
            CCATLogger.DEBUG_LOGGER.debug("retrieveServiceClassesTableQuery " + retrieveServiceClassesTableQuery);

            return jdbcTemplate.query(retrieveServiceClassesTableQuery,
                    admClassMigrationExtractor.setTableName(DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME));
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveServiceClassesTableQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveServiceClassesTableQuery, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public  HashSet<Integer> mergeServiceClasses(MigrationModel migrationModel, ArrayList<ServiceClassesMigrationSummary> summary) throws LookupException {
        try {
            CCATLogger.DEBUG_LOGGER.debug("Start retrieving existing service classes");
            HashSet<Integer> existingCodes = retrieveServiceClassesIds();
            CCATLogger.DEBUG_LOGGER.debug("retrieved existing service classes successfully");

            StringBuilder updateStmtBuilder = new StringBuilder();
            StringBuilder insertStmtBuilder = new StringBuilder();
            String insertStmtValuesPart = " VALUES (";

            HashMap<Integer, List<Object>> inserts = new HashMap<>();
            HashMap<Integer, List<Object>> updates = new HashMap<>();
            HashMap<Integer, ServiceClassesMigrationSummary> summaryMap = new HashMap<>();
            List<String> columns = migrationModel.getHeaders();

            // build update & insert statement
            updateStmtBuilder.append("UPDATE ")
                    .append(migrationModel.getTableName())
                    .append(" SET ");

            for (int i = 0; i < columns.size(); i++) {
                if (columns.get(i).equals(DatabaseStructs.ADM_SERVICE_CLASSES.CODE)) {
                    continue;
                }
                updateStmtBuilder.append(columns.get(i)).append(" = ?");
                if (i != (columns.size() - 1)) {
                    updateStmtBuilder.append(" , ");
                }
            }

            updateStmtBuilder.append(" WHERE ")
                    .append(migrationModel.getKeyIdentifier())
                    .append(" = ? ");

            CCATLogger.DEBUG_LOGGER.debug("Service Classes built update statement : " + updateStmtBuilder.toString());

            // build insert statement
            insertStmtBuilder.append("INSERT INTO ")
                    .append(migrationModel.getTableName())
                    .append(" (");

            for (int i = 0; i < columns.size(); i++) {
                if (i > 0) {
                    insertStmtBuilder.append(" , ");
                    insertStmtValuesPart += " , ";
                }
                insertStmtBuilder.append(columns.get(i));
                insertStmtValuesPart += "?";
            }

            insertStmtBuilder.append(")").append(insertStmtValuesPart).append(")");
            CCATLogger.DEBUG_LOGGER.debug("Service Classes built insert statement : " + insertStmtBuilder.toString());

            //construct query parameters for insert & update statements
            for (HashMap<String, String> dataRowMap : migrationModel.getData()) {
                List<Object> dataRowList = new ArrayList<>();
                ServiceClassesMigrationSummary summaryItem = new ServiceClassesMigrationSummary(migrationModel.getTableName());
                int code = Integer.parseInt(dataRowMap.get(migrationModel.getKeyIdentifier()));
                summaryItem.setIdentifier(String.valueOf(code));
                for (String column : columns) {
                    if (column.equals(DatabaseStructs.ADM_SERVICE_CLASSES.CODE)) {
                        if (existingCodes.contains(code)) {
                            CCATLogger.DEBUG_LOGGER.debug("Updating service class with code [" + code + "]");
                            summaryItem.setAction("Update");
                            updates.put(code, dataRowList);
                        } else {
                            CCATLogger.DEBUG_LOGGER.debug("Inserting service class with code [" + code + "]");
                            summaryItem.setAction("Insert");
                            dataRowList.add(dataRowMap.get(column));
                            inserts.put(code, dataRowList);
                        }
                    } else {
                        dataRowList.add(dataRowMap.get(column));
                    }
                }
                summaryItem.setStatus("Success");
                summaryMap.put(code,summaryItem);
            }

            //execute batch insert
            CCATLogger.DEBUG_LOGGER.debug("Start executing service class batch insert of size [" + inserts.size() + "]");
            try (PreparedStatement pstmt = jdbcTemplate.getDataSource().getConnection().prepareStatement(insertStmtBuilder.toString());) {
                for (List<Object> columnValues : inserts.values()) {
                    int index = 1;
                    for (Object value : columnValues) {
                        pstmt.setObject(index++, value);
                    }
                    pstmt.addBatch();
                }
                int[] insertResult = pstmt.executeBatch();
                if(insertResult.length < inserts.size()){
                    CCATLogger.DEBUG_LOGGER.error("Batch insert failed, some records weren't inserted");
                    throw new Exception("Batch insert failed, some records weren't inserted");
                }
            } catch (Exception ex) {
                CCATLogger.ERROR_LOGGER.debug("Batch insert failed with error [" + ex.getMessage() + "]");
                for(Integer code : inserts.keySet()){
                    ServiceClassesMigrationSummary summaryItem = summaryMap.get(code);
                    summaryItem.setStatus("Failed");
                    summaryItem.setStatusMessage(ex.getMessage());
                }
            }

            //execute batch update
            CCATLogger.DEBUG_LOGGER.debug("Start executing service class batch update of size [" + updates.size() + "]");
            try (PreparedStatement pstmt = jdbcTemplate.getDataSource().getConnection().prepareStatement(updateStmtBuilder.toString());) {
                for (Map.Entry<Integer, List<Object>> columnValuesEntry : updates.entrySet()) {
                    int index = 1;
                    for (Object value : columnValuesEntry.getValue()) {
                        pstmt.setObject(index++, value);
                    }
                    pstmt.setObject(index, columnValuesEntry.getKey());
                    pstmt.addBatch();
                }
                int[] updateResult = pstmt.executeBatch();
                if(updateResult.length < updates.size()){
                    CCATLogger.DEBUG_LOGGER.error("Batch update failed, some records weren't updated");
                    throw new Exception("Batch update failed, some records weren't updated");
                }
            } catch (Exception ex) {
                CCATLogger.ERROR_LOGGER.debug("Batch update failed with error [" + ex.getMessage() + "]");
                for(Integer code : updates.keySet()){
                    ServiceClassesMigrationSummary summaryItem = summaryMap.get(code);
                    summaryItem.setStatus("Failed");
                    summaryItem.setStatusMessage(ex.getMessage());
                }
            }
            //return all sevice classes ids
            CCATLogger.DEBUG_LOGGER.debug("Start retrieving existing service classes after merging");
            existingCodes = retrieveServiceClassesIds();

            summary.addAll(summaryMap.values());
            CCATLogger.DEBUG_LOGGER.debug("Merging service classes finished successfully");
            return existingCodes;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while merging service classes");
            CCATLogger.ERROR_LOGGER.error("Error while merging service classes", ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    public HashSet<Integer> retrieveServiceClassesIds() throws LookupException {

        try {
            String query = "SELECT " + DatabaseStructs.ADM_SERVICE_CLASSES.CODE
                    + " FROM " + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME
                    + " WHERE " + DatabaseStructs.ADM_SERVICE_CLASSES.IS_DELETED + " = 0";

            HashSet<Integer> ids = jdbcTemplate.query(query, new ResultSetExtractor<HashSet<Integer>>() {
                @Override
                public HashSet<Integer> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                    HashSet<Integer> ids = new HashSet<>();
                    while (resultSet.next()) {
                        ids.add(resultSet.getInt(DatabaseStructs.ADM_SERVICE_CLASSES.CODE));
                    }
                    return ids;
                }
            });
            return ids;

        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while retrieving sevice classes ids");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving sevice classes ids", ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }

    }
}
