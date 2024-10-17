/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.database.dao;

import com.asset.ccat.lookup_service.annotation.LogExecutionTime;
import com.asset.ccat.lookup_service.database.extractors.TransactionCodeExtractor;
import com.asset.ccat.lookup_service.database.extractors.TransactionTypeExtractor;
import com.asset.ccat.lookup_service.database.extractors.TransactionTypeLinkedCodeExtractor;
import com.asset.ccat.lookup_service.database.extractors.TransactionTypeWithFeatureExtractor;
import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.LkTransactionCode;
import com.asset.ccat.lookup_service.models.LkTransactionType;
import com.asset.ccat.lookup_service.models.TransactionCode;
import com.asset.ccat.lookup_service.models.TransactionType;
import com.asset.ccat.lookup_service.models.TransactionLink;
import com.asset.ccat.lookup_service.models.requests.transaction.UpdateTransactionLinkRequest;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Mahmoud Shehab
 */
@Repository
public class TransactionsDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TransactionTypeExtractor transactionTypeExtractor;
    @Autowired
    private TransactionTypeWithFeatureExtractor typeWithFeatureExtractor;
    @Autowired
    private TransactionCodeExtractor transactionCodeExtractor;
    @Autowired
    private TransactionTypeLinkedCodeExtractor transactionLinkedCodeExtractor;
    private String retrieveTransactionTypeQuery;
    private String retrieveTransactionTypeFeatureQuery;
    private String retrieveAllTransactionCodesQuery;
    private String retrieveAllLinkedCodeByTypeIdQuery;
    private String retrieveAllLinkesQuery;
    private String addTransactionCodeQuery;
    private String addTransactionTypeQuery;
    private String deleteTransactionTypeQuery;
    private String deleteTransactionCodeQuery;
    private String updateTransactionTypeQuery;
    private String updateTransactionCodeQuery;
    private String deleteFeatureByTypeIdQuery;
    private String addFeaturesByTypeIdQuery;
    private String isFoundLinkTransactionQuery;
    private String addTransactionLinkQuery;
    private String deleteTransactionLinkQuery;
    private String isTransactionTypeLinkedQuery;
    private String transactionTypehasChildQuery;

    @LogExecutionTime
    public HashMap<Integer, List<LkTransactionType>> retrieveAllTypes() {

        HashMap<Integer, List<LkTransactionType>> result = null;
        String sqlStatement = "";
        try {
            sqlStatement
                    = " Select  " + DatabaseStructs.ADM_TX_TYPES.ID + ","
                    + DatabaseStructs.ADM_TX_TYPES.NAME + ","
                    + DatabaseStructs.ADM_TX_TYPES.IS_DEFAULT + ","
                    + DatabaseStructs.ADM_TX_FEATURE_TYPES.FEATURE_ID + ","
                    + DatabaseStructs.ADM_TX_TYPES.VALUE
                    + " From " + DatabaseStructs.ADM_TX_TYPES.TABLE_NAME + ", "
                    + DatabaseStructs.ADM_TX_FEATURE_TYPES.TABLE_NAME
                    + " Where " + DatabaseStructs.ADM_TX_TYPES.IS_DELETED + " = 0"
                    + " AND " + DatabaseStructs.ADM_TX_FEATURE_TYPES.TYPE_ID + " = " + DatabaseStructs.ADM_TX_TYPES.ID
                    + " Order by lower(" + DatabaseStructs.ADM_TX_TYPES.NAME + ")";

            result = jdbcTemplate.query(sqlStatement, transactionTypeExtractor);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("ERROR_LOGGER.error while executing: " + sqlStatement);
            CCATLogger.ERROR_LOGGER.error("ERROR_LOGGER.error while executing: " + sqlStatement, ex);
        }
        return result;
    }

    @LogExecutionTime
    public HashMap<Integer, List<LkTransactionCode>> retrieveCodes() {
        HashMap<Integer, List<LkTransactionCode>> result = null;
        String sqlStatement = "";
        try {
            sqlStatement = " Select  " + DatabaseStructs.ADM_TX_CODES.ID + ","
                    + DatabaseStructs.ADM_TX_CODES.NAME + ","
                    + DatabaseStructs.ADM_TX_CODES.IS_DEFAULT + ","
                    + DatabaseStructs.ADM_TX_LINKS.TX_TYPE_ID + ","
                    + DatabaseStructs.ADM_TX_CODES.VALUE
                    + " From " + DatabaseStructs.ADM_TX_CODES.TABLE_NAME + ", "
                    + DatabaseStructs.ADM_TX_LINKS.TABLE_NAME
                    + " Where " + DatabaseStructs.ADM_TX_TYPES.IS_DELETED + " = 0"
                    + " AND " + DatabaseStructs.ADM_TX_LINKS.TX_CODE_ID + " = " + DatabaseStructs.ADM_TX_CODES.ID
                    + " Order by lower(" + DatabaseStructs.ADM_TX_TYPES.NAME + ")";
            result = jdbcTemplate.query(sqlStatement, transactionCodeExtractor);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("ERROR_LOGGER.error while executing: " + sqlStatement);
            CCATLogger.ERROR_LOGGER.error("ERROR_LOGGER.error while executing: " + sqlStatement, ex);
        }
        return result;
    }

    @LogExecutionTime   //note used
    public List<LkTransactionType> retrieveAllTransactionTypes() throws LookupException {
        try {
            if (retrieveTransactionTypeQuery == null) {
                retrieveTransactionTypeQuery
                        = " Select  " + DatabaseStructs.ADM_TX_TYPES.ID + ","
                        + DatabaseStructs.ADM_TX_TYPES.NAME + ","
                        + DatabaseStructs.ADM_TX_TYPES.VALUE + ","
                        //+ DatabaseStructs.ADM_TX_TYPES.DESCRIPTION + ","
                        + DatabaseStructs.ADM_TX_TYPES.IS_DEFAULT + ","
                        + DatabaseStructs.ADM_TX_TYPES.IS_DELETED
                        + " From " + DatabaseStructs.ADM_TX_TYPES.TABLE_NAME
                        + " Where " + DatabaseStructs.ADM_TX_TYPES.IS_DELETED + " = 0"
                        + " Order by lower(" + DatabaseStructs.ADM_TX_TYPES.NAME + ")";
            }
            CCATLogger.DEBUG_LOGGER.debug("retrieveServiceClassByIdQuery " + retrieveTransactionTypeQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending TransactionsDao - retrieveAllTransactionTypes");

            return jdbcTemplate.query(retrieveTransactionTypeQuery, new BeanPropertyRowMapper<>(LkTransactionType.class));
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception in " + ex);
            CCATLogger.ERROR_LOGGER.error("Exception in ", ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public List<TransactionType> retrieveAllTransactionTypesWithFeatures() throws LookupException {
        try {
            if (retrieveTransactionTypeFeatureQuery == null) {
                retrieveTransactionTypeFeatureQuery = " SELECT "
                        + DatabaseStructs.ADM_TX_TYPES.TABLE_NAME + "." + DatabaseStructs.ADM_TX_TYPES.ID + ","
                        + DatabaseStructs.ADM_TX_TYPES.TABLE_NAME + "." + DatabaseStructs.ADM_TX_TYPES.NAME + ","
                        + DatabaseStructs.ADM_TX_TYPES.TABLE_NAME + "." + DatabaseStructs.ADM_TX_TYPES.VALUE + ","
                        + DatabaseStructs.ADM_TX_FEATURE_TYPES.TABLE_NAME + "." + DatabaseStructs.ADM_TX_FEATURE_TYPES.FEATURE_ID
                        + " FROM " + DatabaseStructs.ADM_TX_TYPES.TABLE_NAME
                        + " LEFT JOIN " + DatabaseStructs.ADM_TX_FEATURE_TYPES.TABLE_NAME
                        + " ON " + DatabaseStructs.ADM_TX_TYPES.TABLE_NAME + "." + DatabaseStructs.ADM_TX_TYPES.ID
                        + " = " + DatabaseStructs.ADM_TX_FEATURE_TYPES.TABLE_NAME + "." + DatabaseStructs.ADM_TX_FEATURE_TYPES.TYPE_ID
                        + " WHERE "
                        //+ DatabaseStructs.ADM_TX_FEATURE_TYPES.TABLE_NAME + "." + DatabaseStructs.ADM_TX_FEATURE_TYPES.TYPE_ID + " = " + DatabaseStructs.ADM_TX_TYPES.TABLE_NAME + "." + DatabaseStructs.ADM_TX_TYPES.ID
                        //+ " AND " 
                        + DatabaseStructs.ADM_TX_TYPES.TABLE_NAME + "." + DatabaseStructs.ADM_TX_TYPES.IS_DELETED + " = 0"
                        + " Order by lower(" + DatabaseStructs.ADM_TX_TYPES.TABLE_NAME + "." + DatabaseStructs.ADM_TX_TYPES.NAME + ")";
            }
            CCATLogger.DEBUG_LOGGER.debug("SQL Query = {} ", retrieveTransactionTypeFeatureQuery);
            return jdbcTemplate.query(retrieveTransactionTypeFeatureQuery, typeWithFeatureExtractor);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception in retrieveAllTransactionTypesWithFeatures \n ",  ex);
            CCATLogger.ERROR_LOGGER.error("Exception in retrieveAllTransactionTypesWithFeatures ", ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public List<TransactionCode> retrieveAllTransactionCodes() throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting TransactionsDao - retrieveAllTransactionCodes");
        try {
            if (retrieveAllTransactionCodesQuery == null) {
                retrieveAllTransactionCodesQuery = " SELECT "
                        + DatabaseStructs.ADM_TX_CODES.ID + ","
                        + DatabaseStructs.ADM_TX_CODES.NAME + ","
                        + DatabaseStructs.ADM_TX_CODES.DESCRIPTION + ","
                        + DatabaseStructs.ADM_TX_CODES.VALUE
                        + " FROM " + DatabaseStructs.ADM_TX_CODES.TABLE_NAME
                        + " WHERE " + DatabaseStructs.ADM_TX_CODES.IS_DELETED + " = 0"
                        + " Order by lower(" + DatabaseStructs.ADM_TX_CODES.NAME + ")";
            }
            CCATLogger.DEBUG_LOGGER.debug("retrieveAllTransactionCodesQuery = " + retrieveAllTransactionCodesQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending TransactionsDao - retrieveAllTransactionCodes");
            return jdbcTemplate.query(retrieveAllTransactionCodesQuery, new BeanPropertyRowMapper<>(TransactionCode.class));
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception in retrieveAllTransactionCodes \n" + ex);
            CCATLogger.ERROR_LOGGER.error("Exception in retrieveAllTransactionCodes ", ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public List<TransactionCode> retrieveAllLinkedCodeByTypeId(Integer id) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting TransactionsDao - retrieveAllLinkedCodeByTypeId[" + id + "]");
        try {
            if (retrieveAllLinkedCodeByTypeIdQuery == null) {
                retrieveAllLinkedCodeByTypeIdQuery = " SELECT "
                        + DatabaseStructs.ADM_TX_CODES.TABLE_NAME + "." + DatabaseStructs.ADM_TX_CODES.ID + ","
                        + DatabaseStructs.ADM_TX_CODES.TABLE_NAME + "." + DatabaseStructs.ADM_TX_CODES.NAME + ","
                        + DatabaseStructs.ADM_TX_CODES.TABLE_NAME + "." + DatabaseStructs.ADM_TX_CODES.DESCRIPTION + ","
                        + DatabaseStructs.ADM_TX_CODES.TABLE_NAME + "." + DatabaseStructs.ADM_TX_CODES.VALUE
                        + " FROM "
                        + DatabaseStructs.ADM_TX_CODES.TABLE_NAME
                        + " LEFT JOIN " + DatabaseStructs.ADM_TX_LINKS.TABLE_NAME
                        + " ON " + DatabaseStructs.ADM_TX_CODES.TABLE_NAME + "." + DatabaseStructs.ADM_TX_CODES.ID
                        + " = " + DatabaseStructs.ADM_TX_LINKS.TABLE_NAME + "." + DatabaseStructs.ADM_TX_LINKS.TX_CODE_ID
                        + " WHERE " + DatabaseStructs.ADM_TX_LINKS.TABLE_NAME + "." + DatabaseStructs.ADM_TX_LINKS.TX_TYPE_ID + " = ? "
                        + " AND " + DatabaseStructs.ADM_TX_CODES.TABLE_NAME + "." + DatabaseStructs.ADM_TX_CODES.IS_DELETED + " = 0"
                        + " Order by lower(" + DatabaseStructs.ADM_TX_CODES.TABLE_NAME + "." + DatabaseStructs.ADM_TX_CODES.NAME + ")";
            }
            CCATLogger.DEBUG_LOGGER.debug("retrieveAllLinkedCodeByTypeIdQuery = " + retrieveAllLinkedCodeByTypeIdQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending TransactionsDao - retrieveAllLinkedCodeByTypeId");
            return jdbcTemplate.query(retrieveAllLinkedCodeByTypeIdQuery, new BeanPropertyRowMapper<>(TransactionCode.class), id);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception in retrieveAllLinkedCodeByTypeId \n" + ex);
            CCATLogger.ERROR_LOGGER.error("Exception in retrieveAllLinkedCodeByTypeId ", ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }
    
    @LogExecutionTime
    public List<TransactionLink> retrieveAllLinkes() throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting TransactionsDao - retrieveAllLinkes");
        try {
            if (retrieveAllLinkesQuery == null) {
                retrieveAllLinkesQuery = "select l.TX_TYPE_ID TYPE_ID, l.TX_CODE_ID CODE_ID, l.DESCRIPTION,"
                        + " t.VALUE TYPE_VALUE, c.VALUE CODE_VALUE FROM "
                        + DatabaseStructs.ADM_TX_LINKS.TABLE_NAME + " l "
                        + " JOIN " + DatabaseStructs.ADM_TX_TYPES.TABLE_NAME + " t on " 
                        + "t." + DatabaseStructs.ADM_TX_TYPES.ID + " = " + "l."+  DatabaseStructs.ADM_TX_LINKS.TX_TYPE_ID
                        + " JOIN " + DatabaseStructs.ADM_TX_CODES.TABLE_NAME + " c on " 
                        + " t." + DatabaseStructs.ADM_TX_CODES.ID + " = " + "c."+  DatabaseStructs.ADM_TX_CODES.ID;
            }
            CCATLogger.DEBUG_LOGGER.debug("retrieveAllLinkedCodeByTypeIdQuery = " + retrieveAllLinkesQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending TransactionsDao - retrieveAllLinkedCodeByTypeId");
            return jdbcTemplate.query(retrieveAllLinkesQuery, new BeanPropertyRowMapper<>(TransactionLink.class));
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception in retrieveAllLinkesQuery \n" + ex);
            CCATLogger.ERROR_LOGGER.error("Exception in retrieveAllLinkesQuery ", ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public int addTransactionCode(String name, String value, String description) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting TransactionsDao - addTransactionCode");
        try {
            if (addTransactionCodeQuery == null) {
                addTransactionCodeQuery
                        = "INSERT INTO "
                        + DatabaseStructs.ADM_TX_CODES.TABLE_NAME
                        + "(" + DatabaseStructs.ADM_TX_CODES.ID
                        + "," + DatabaseStructs.ADM_TX_CODES.NAME
                        + "," + DatabaseStructs.ADM_TX_CODES.VALUE
                        + "," + DatabaseStructs.ADM_TX_CODES.DESCRIPTION
                        + ") "
                        + "values ( " + DatabaseStructs.SEQUENCE.S_ADM_TX_CODES + ".nextval, ? , ? , ? )";
            }
            CCATLogger.DEBUG_LOGGER.debug("addTransactionCodeQuery = " + addTransactionCodeQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending TransactionsDao - addTransactionCode");
            return jdbcTemplate.update(addTransactionCodeQuery,
                    name, value, description);
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("Exception in addTransactionCode \n" + e);
            CCATLogger.ERROR_LOGGER.error("Exception in addTransactionCode ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public int addTransactionType(Integer typeId, String name, String value) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting TransactionsDao - addTransactionType");
        try {
            if (addTransactionTypeQuery == null) {
                addTransactionTypeQuery
                        = "INSERT INTO "
                        + DatabaseStructs.ADM_TX_TYPES.TABLE_NAME
                        + "(" + DatabaseStructs.ADM_TX_TYPES.ID
                        + "," + DatabaseStructs.ADM_TX_TYPES.NAME
                        + "," + DatabaseStructs.ADM_TX_TYPES.VALUE
                        + ") "
                        + "values ( ? , ? , ? )";
            }

            CCATLogger.DEBUG_LOGGER.debug("addTransactionTypeQuery = " + addTransactionTypeQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending TransactionsDao - addTransactionType");
            return jdbcTemplate.update(addTransactionTypeQuery, typeId, name, value);
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("Ending TransactionsDao With Exception \n" + e);
            CCATLogger.ERROR_LOGGER.error("Ending TransactionsDao With Exception ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }

    }

    @LogExecutionTime
    public boolean deleteTransactionType(int typeId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting TransactionsDao - deleteTransactionType");
        try {
            if (deleteTransactionTypeQuery == null) {
                deleteTransactionTypeQuery
                        = "update "
                        + DatabaseStructs.ADM_TX_TYPES.TABLE_NAME
                        + " set "
                        + DatabaseStructs.ADM_TX_TYPES.IS_DELETED + " = 1 "
                        + " where "
                        + DatabaseStructs.ADM_TX_TYPES.ID + " = ? ";
            }
            CCATLogger.DEBUG_LOGGER.debug("deleteTransactionTypeQuery = " + deleteTransactionTypeQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending TransactionsDao - deleteTransactionType");
            return jdbcTemplate.update(deleteTransactionTypeQuery, typeId) > 0;

        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("Exception in deleteTransactionType \n" + e);
            CCATLogger.ERROR_LOGGER.error("Exception in deleteTransactionType ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public boolean deleteTransactionCode(int codeId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting TransactionsDao - deleteTransactionCode");
        try {
            if (deleteTransactionCodeQuery == null) {
                deleteTransactionCodeQuery
                        = "update "
                        + DatabaseStructs.ADM_TX_CODES.TABLE_NAME
                        + " set "
                        + DatabaseStructs.ADM_TX_CODES.IS_DELETED + " = 1 "
                        + " where "
                        + DatabaseStructs.ADM_TX_CODES.ID + " = ? ";
            }

            CCATLogger.DEBUG_LOGGER.debug("deleteTransactionCodeQuery = " + deleteTransactionCodeQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending TransactionsDao - deleteTransactionCode");
            return jdbcTemplate.update(deleteTransactionCodeQuery, codeId) != 0;

        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("Exception in deleteTransactionCode \n" + e);
            CCATLogger.ERROR_LOGGER.error("Exception in deleteTransactionCode ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public int updateTransactionType(Integer typeId, String name, String value) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting TransactionsDao - updateTransactionType");
        try {
            if (updateTransactionTypeQuery == null) {
                updateTransactionTypeQuery
                        = "update " + DatabaseStructs.ADM_TX_TYPES.TABLE_NAME
                        + " SET "
                        + DatabaseStructs.ADM_TX_TYPES.NAME + " = ? ,"
                        + DatabaseStructs.ADM_TX_TYPES.VALUE + " = ? "
                        + " where "
                        + DatabaseStructs.ADM_TX_TYPES.ID + " = ?";
            }
            CCATLogger.DEBUG_LOGGER.debug("updateTransactionTypeQuery = " + updateTransactionTypeQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending TransactionsDao - updateTransactionType");
            return jdbcTemplate.update(updateTransactionTypeQuery, name, value, typeId);
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("Exception in updateTransactionType \n" + e);
            CCATLogger.ERROR_LOGGER.error("Exception in updateTransactionType ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public int updateTransactionCode(Integer codeId, String name,
            String value, String description) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting TransactionsDao - updateTransactionCode");
        try {
            if (updateTransactionCodeQuery == null) {
                updateTransactionCodeQuery
                        = "update " + DatabaseStructs.ADM_TX_CODES.TABLE_NAME
                        + " SET "
                        + DatabaseStructs.ADM_TX_CODES.NAME + " = ? ,"
                        + DatabaseStructs.ADM_TX_CODES.VALUE + " = ? ,"
                        + DatabaseStructs.ADM_TX_CODES.DESCRIPTION + " = ? "
                        + " where "
                        + DatabaseStructs.ADM_TX_CODES.ID + " = ?";
            }
            CCATLogger.DEBUG_LOGGER.debug("updateTransactionCodeQuery = " + updateTransactionCodeQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending TransactionsDao - updateTransactionCode");
            return jdbcTemplate.update(updateTransactionCodeQuery, name, value, description, codeId);
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("Exception in updateTransactionCode \n" + e);
            CCATLogger.ERROR_LOGGER.error("Exception in updateTransactionCode ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public boolean deleteFeaturesByTypeId(Integer typeId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting TransactionsDao - deleteFeaturesByTypeId");
        try {
            if (deleteFeatureByTypeIdQuery == null) {
                deleteFeatureByTypeIdQuery = "DELETE FROM "
                        + DatabaseStructs.ADM_TX_FEATURE_TYPES.TABLE_NAME
                        + " WHERE TYPE_ID = ?";
            }
            CCATLogger.DEBUG_LOGGER.debug("deleteFeatureByTypeIdQuery = " + deleteFeatureByTypeIdQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending TransactionsDao - deleteFeaturesByTypeId");
            return jdbcTemplate.update(deleteFeatureByTypeIdQuery, typeId) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("Exception in deleteFeaturesByTypeId \n" + e);
            CCATLogger.ERROR_LOGGER.error("Exception in deleteFeaturesByTypeId ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());

        }
    }

    @LogExecutionTime
    public boolean addFeaturesByTypeId(Integer typeId, List<Integer> featureIds) throws LookupException {
        int[] rows;
        CCATLogger.DEBUG_LOGGER.debug("Starting TransactionsDao - addFeaturesByTypeId");
        try {
            if (addFeaturesByTypeIdQuery == null) {
                addFeaturesByTypeIdQuery
                        = " INSERT INTO  "
                        + DatabaseStructs.ADM_TX_FEATURE_TYPES.TABLE_NAME
                        + " ( "
                        + DatabaseStructs.ADM_TX_FEATURE_TYPES.TYPE_ID + ","
                        + DatabaseStructs.ADM_TX_FEATURE_TYPES.FEATURE_ID
                        + " ) "
                        + " VALUES ( ?, ? ) ";
            }
            CCATLogger.DEBUG_LOGGER.debug("addFeaturesByTypeIdQuery = " + addFeaturesByTypeIdQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending TransactionsDao - addFeaturesByTypeId");
            CCATLogger.DEBUG_LOGGER.debug("addEligibleServiceClassQuery  = " + addFeaturesByTypeIdQuery);
            rows = jdbcTemplate.batchUpdate(addFeaturesByTypeIdQuery, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1, typeId);
                    ps.setInt(2, featureIds.get(i));
                }

                @Override
                public int getBatchSize() {
                    return featureIds.size();
                }
            });
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("Exception in addFeaturesByTypeId \n" + e);
            CCATLogger.ERROR_LOGGER.error("Exception in addFeaturesByTypeId ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());

        }

        return rows.length > 0;
    }

    @LogExecutionTime
    public boolean isFoundLinkTransaction(Integer typeId, Integer codeId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting TransactionsDao - isFoundLinkTransaction");
        try {
            if (isFoundLinkTransactionQuery == null) {
                isFoundLinkTransactionQuery
                        = "select count(*) from "
                        + DatabaseStructs.ADM_TX_LINKS.TABLE_NAME
                        + " where " + DatabaseStructs.ADM_TX_LINKS.TX_TYPE_ID + " = ? "
                        + " And " + DatabaseStructs.ADM_TX_LINKS.TX_CODE_ID + " = ? ";
            }

            CCATLogger.DEBUG_LOGGER.debug("isFoundLinkTransactionQuery = " + isFoundLinkTransactionQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending TransactionsDao - isFoundLinkTransaction");
            return jdbcTemplate.queryForObject(isFoundLinkTransactionQuery, Integer.class, typeId, codeId) > 0;

        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("Exception in isFoundLinkTransaction \n" + e);
            CCATLogger.ERROR_LOGGER.error("Exception in isFoundLinkTransaction ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());

        }
    }

    @LogExecutionTime
    public boolean addTransactionLink(UpdateTransactionLinkRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting TransactionsDao - addTransactionLink");
        try {
            if (addTransactionLinkQuery == null) {
                addTransactionLinkQuery
                        = "INSERT INTO "
                        + DatabaseStructs.ADM_TX_LINKS.TABLE_NAME
                        + "("
                        + DatabaseStructs.ADM_TX_LINKS.TX_TYPE_ID
                        + ","
                        + DatabaseStructs.ADM_TX_LINKS.TX_CODE_ID
                        + ","
                        + DatabaseStructs.ADM_TX_LINKS.DESCRIPTION
                        + ")"
                        + " VALUES (?,?,?)";
            }

            CCATLogger.DEBUG_LOGGER.debug("addTransactionLinkQuery = " + addTransactionLinkQuery);
            return jdbcTemplate.update(addTransactionLinkQuery, 
                    request.getTypeId(),
                    request.getCodeId(),
                    request.getDescription()) != 0;

        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("Exception in addTransactionLink \n" + e);
            CCATLogger.ERROR_LOGGER.error("Exception in addTransactionLink ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());

        }
    }

    @LogExecutionTime
    public boolean removeTransactionLink(Integer typeId, Integer codeId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting TransactionsDao - removeTransactionLink");
        try {
            if (deleteTransactionLinkQuery == null) {
                deleteTransactionLinkQuery
                        = " DELETE FROM "
                        + DatabaseStructs.ADM_TX_LINKS.TABLE_NAME
                        + " WHERE " + DatabaseStructs.ADM_TX_LINKS.TX_TYPE_ID + " = ? "
                        + " AND " + DatabaseStructs.ADM_TX_LINKS.TX_CODE_ID + " = ?";
            }

            CCATLogger.DEBUG_LOGGER.debug("deleteTransactionLinkQuery = " + deleteTransactionLinkQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending TransactionsDao - removeTransactionLink");
            return jdbcTemplate.update(deleteTransactionLinkQuery, typeId, codeId) != 0;

        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("Exception in removeTransactionLink \n" + e);
            CCATLogger.ERROR_LOGGER.error("Exception in removeTransactionLink ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());

        }
    }

    @LogExecutionTime
    public boolean isTransactionTypeLinked(int typeId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting TransactionDAO - isTransactionTypeLinked");
        try {
            if (isTransactionTypeLinkedQuery == null) {
                isTransactionTypeLinkedQuery = "Select count(*) "
                        + " from " + DatabaseStructs.ADM_TX_LINKS.TABLE_NAME
                        + " where " + DatabaseStructs.ADM_TX_LINKS.TX_TYPE_ID + " = ?";
            }

            CCATLogger.DEBUG_LOGGER.debug("isTransactionTypeLinkedQuery = " + isTransactionTypeLinkedQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending TransactionDAO - isTransactionTypeLinked");
            return jdbcTemplate.queryForObject(isTransactionTypeLinkedQuery, Integer.class, typeId) > 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("SQLException in isTransactionTypeLinked \n" + e);
            CCATLogger.ERROR_LOGGER.error("SQLException in isTransactionTypeLinked", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }

    }

    @LogExecutionTime
    public boolean transactionTypehasChild(int typeId) throws LookupException {

        CCATLogger.DEBUG_LOGGER.debug("Starting TransactionDAO - transactionTypehasChild");
        try {
            if (transactionTypehasChildQuery == null) {
                transactionTypehasChildQuery = "Select count(*) "
                        + " from " + DatabaseStructs.ADM_TX_TYPES.TABLE_NAME
                        + " where " + DatabaseStructs.ADM_TX_TYPES.ID + " =  ? "
                        + " And " + DatabaseStructs.ADM_TX_TYPES.IS_DELETED + " = 0 "
                        + " And " + DatabaseStructs.ADM_TX_TYPES.IS_DEFAULT + " = 1 ";
            }

            CCATLogger.DEBUG_LOGGER.debug("transactionTypehasChildQuery = " + transactionTypehasChildQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending TransactionDAO - transactionTypehasChild");
            return jdbcTemplate.queryForObject(transactionTypehasChildQuery, Integer.class, typeId) > 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("Exception in transactionTypehasChild \n" + e);
            CCATLogger.ERROR_LOGGER.error("Exception in transactionTypehasChild", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }

    }
    private String transactionCodehasChildQuery;

    @LogExecutionTime
    public boolean transactionCodehasChild(int codeId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting TransactionDAO - transactionCodehasChild");
        try {
            if (transactionCodehasChildQuery == null) {
                transactionCodehasChildQuery
                        = "Select count(*) "
                        + " from " + DatabaseStructs.ADM_TX_CODES.TABLE_NAME
                        + " where " + DatabaseStructs.ADM_TX_CODES.ID + " = ? "
                        + " And " + DatabaseStructs.ADM_TX_CODES.IS_DELETED + " = 0 "
                        + " And " + DatabaseStructs.ADM_TX_CODES.IS_DEFAULT + " = 1 ";
            }

            CCATLogger.DEBUG_LOGGER.debug("transactionCodehasChildQuery = " + transactionCodehasChildQuery);

            CCATLogger.DEBUG_LOGGER.debug("Ending TransactionDAO - transactionCodehasChild");
            return jdbcTemplate.queryForObject(transactionCodehasChildQuery, Integer.class, codeId) > 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("Exception in transactionCodehasChild \n" + e);
            CCATLogger.ERROR_LOGGER.error("Exception in transactionCodehasChild", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }

    }

}
