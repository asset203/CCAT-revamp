package com.asset.ccat.lookup_service.database.dao;

import com.asset.ccat.lookup_service.annotation.LogExecutionTime;
import com.asset.ccat.lookup_service.database.row_mapper.ODSNodesRowMapper;
import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.ods_models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * @author Assem Hassan
 */
@Repository
public class ODSNodesDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String retrieveODSNodesForCachedLookupQuery;
    private String retrieveAllNodesQuery;
    private String addNodeQuery;
    private String deleteNodeByIdQuery;
    private String updateNodeWithPasswordQuery;
    private String updateNodeWithoutPasswordQuery;
    private String retrieveNodeByIdQuery;

    @LogExecutionTime
    public List<ODSNodeModel> retrieveODSNodesForCachedLookup() throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting ODSNodesDAO - retrieveODSNodesForCachedLookup");
        List<ODSNodeModel> odsNodes;
        try {
            if (Objects.isNull(retrieveODSNodesForCachedLookupQuery)) {
                retrieveODSNodesForCachedLookupQuery = "SELECT * FROM "
                        + DatabaseStructs.ODS_NODES.TABLE_NAME
                        + " Order By "
                        + DatabaseStructs.ODS_NODES.ADDRESS
                        +" ASC ";
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + retrieveODSNodesForCachedLookupQuery);
            odsNodes = jdbcTemplate.query(retrieveODSNodesForCachedLookupQuery, new BeanPropertyRowMapper<>(ODSNodeModel.class));
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveODSNodesForCachedLookupQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveODSNodesForCachedLookupQuery, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending ODSNodesDAO - retrieveODSNodes");

        return odsNodes;
    }


    @LogExecutionTime
    public List<ODSNodeModel> retrieveODSNodes() throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting ODSNodesDAO - retrieveODSNodes");
        List<ODSNodeModel> odsNodes;
        try {
            if (Objects.isNull(retrieveAllNodesQuery)) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT ")
                        .append(DatabaseStructs.ODS_NODES.ID).append(",")
                        .append(DatabaseStructs.ODS_NODES.ADDRESS).append(",")
                        .append(DatabaseStructs.ODS_NODES.PORT).append(",")
                        .append(DatabaseStructs.ODS_NODES.USER_NAME).append(",")
                        .append(DatabaseStructs.ODS_NODES.NUMBER_OF_SESSIONS).append(",")
                        .append(DatabaseStructs.ODS_NODES.CONCURRENT_CALLS).append(",")
                        .append(DatabaseStructs.ODS_NODES.EXTRA_CONF).append(",")
                        .append(DatabaseStructs.ODS_NODES.CONNECTION_TIMEOUT).append(",")
                        .append(DatabaseStructs.ODS_NODES.SCHEMA)
                        .append(" FROM ")
                        .append(DatabaseStructs.ODS_NODES.TABLE_NAME)
                        .append(" Order By ")
                        .append(DatabaseStructs.ODS_NODES.ADDRESS)
                        .append(" ASC ");
                retrieveAllNodesQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + retrieveAllNodesQuery);
            odsNodes = jdbcTemplate.query(retrieveAllNodesQuery, new BeanPropertyRowMapper<>(ODSNodeModel.class));
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveAllNodesQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveAllNodesQuery, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending ODSNodesDAO - retrieveODSNodes");

        return odsNodes;
    }

    @LogExecutionTime
    public boolean addODSNode(ODSNodeModel odsNodeModel) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting ODSNodesDAO - addODSNode");
        CCATLogger.DEBUG_LOGGER.debug("Start adding odsNodes with Address[" + odsNodeModel.getAddress() + "].");
        try {
            if (Objects.isNull(addNodeQuery)) {
                StringBuilder query = new StringBuilder("");
                query.append("INSERT INTO ")
                        .append(DatabaseStructs.ODS_NODES.TABLE_NAME)
                        .append("(")
                        .append(DatabaseStructs.ODS_NODES.ADDRESS).append(",")
                        .append(DatabaseStructs.ODS_NODES.PORT).append(",")
                        .append(DatabaseStructs.ODS_NODES.USER_NAME).append(",")
                        .append(DatabaseStructs.ODS_NODES.PASSWORD).append(",")
                        .append(DatabaseStructs.ODS_NODES.NUMBER_OF_SESSIONS).append(",")
                        .append(DatabaseStructs.ODS_NODES.CONCURRENT_CALLS).append(",")
                        .append(DatabaseStructs.ODS_NODES.CONNECTION_TIMEOUT).append(",")
                        .append(DatabaseStructs.ODS_NODES.EXTRA_CONF).append(",")
                        .append(DatabaseStructs.ODS_NODES.SCHEMA)
                        .append(")")
                        .append("VALUES ( ?, ? , ? , ? , ? , ? , ? , ? , ? )");
                addNodeQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + addNodeQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending ODSNodesDAO - addODSNode");

            return jdbcTemplate.update(addNodeQuery,
                    odsNodeModel.getAddress(),
                    odsNodeModel.getPort(),
                    odsNodeModel.getUserName(),
                    odsNodeModel.getPassword(),
                    odsNodeModel.getNumberOfSessions(),
                    odsNodeModel.getConcurrentCalls(),
                    odsNodeModel.getConnectionTimeout(),
                    odsNodeModel.getExtraConf(),
                    odsNodeModel.getSchema()) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + addNodeQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + addNodeQuery, e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public boolean deleteODSNodeById(Integer odsNodesId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting ODSNodesDAO - deleteODSNodeById");
        CCATLogger.DEBUG_LOGGER.debug("Start deleting odsNodes with ID[" + odsNodesId + "].");
        try {
            if (Objects.isNull(deleteNodeByIdQuery)) {
                StringBuilder query = new StringBuilder("");
                query.append("DELETE FROM ")
                        .append(DatabaseStructs.ODS_NODES.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ODS_NODES.ID)
                        .append(" = ?");
                deleteNodeByIdQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + deleteNodeByIdQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending ODSNodesDAO - deleteODSNodeById");

            return jdbcTemplate.update(deleteNodeByIdQuery, odsNodesId) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + deleteNodeByIdQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + deleteNodeByIdQuery, e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public ODSNodeModel retrieveODSNodeById(Integer odsNodesId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting ODSNodesDAO - retrieveODSNodeById");
        CCATLogger.DEBUG_LOGGER.debug("Start deleting odsNode with ID[" + odsNodesId + "].");
        ODSNodeModel odsNodeModel;
        try {
            if (Objects.isNull(retrieveNodeByIdQuery)) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT ")
                        .append(DatabaseStructs.ODS_NODES.ID).append(",")
                        .append(DatabaseStructs.ODS_NODES.ADDRESS).append(",")
                        .append(DatabaseStructs.ODS_NODES.PORT).append(",")
                        .append(DatabaseStructs.ODS_NODES.USER_NAME).append(",")
                        .append(DatabaseStructs.ODS_NODES.NUMBER_OF_SESSIONS).append(",")
                        .append(DatabaseStructs.ODS_NODES.CONCURRENT_CALLS).append(",")
                        .append(DatabaseStructs.ODS_NODES.EXTRA_CONF).append(",")
                        .append(DatabaseStructs.ODS_NODES.CONNECTION_TIMEOUT).append(",")
                        .append(DatabaseStructs.ODS_NODES.SCHEMA)
                        .append(" FROM ")
                        .append(DatabaseStructs.ODS_NODES.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ODS_NODES.ID).append(" = ? ");
                retrieveNodeByIdQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + retrieveNodeByIdQuery);
            odsNodeModel = jdbcTemplate.queryForObject(retrieveNodeByIdQuery, new ODSNodesRowMapper(), odsNodesId);
        } catch (EmptyResultDataAccessException e) {
            throw new LookupException(ErrorCodes.ERROR.NO_DATA_FOUND);
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveNodeByIdQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveNodeByIdQuery, e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending  ODSNodesDAO - retrieveODSNodeById");

        return odsNodeModel;
    }

    @LogExecutionTime
    public boolean updateODSNodeWithPassword(ODSNodeModel odsNodeModel) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting ODSNodesDAO - updateODSNodeWithPassword");
        CCATLogger.DEBUG_LOGGER.debug("Start updating odsNodes with ID[" + odsNodeModel.getId() + "].");
        try {
            if (Objects.isNull(updateNodeWithPasswordQuery)) {
                StringBuilder query = new StringBuilder("");
                query.append("UPDATE ")
                        .append(DatabaseStructs.ODS_NODES.TABLE_NAME)
                        .append(" SET ")
                        .append(DatabaseStructs.ODS_NODES.ADDRESS).append(" = ? ,")
                        .append(DatabaseStructs.ODS_NODES.PORT).append(" = ? ,")
                        .append(DatabaseStructs.ODS_NODES.USER_NAME).append(" = ? ,")
                        .append(DatabaseStructs.ODS_NODES.PASSWORD).append(" = ? ,")
                        .append(DatabaseStructs.ODS_NODES.NUMBER_OF_SESSIONS).append(" = ? ,")
                        .append(DatabaseStructs.ODS_NODES.CONCURRENT_CALLS).append(" = ? ,")
                        .append(DatabaseStructs.ODS_NODES.CONNECTION_TIMEOUT).append(" = ? ,")
                        .append(DatabaseStructs.ODS_NODES.EXTRA_CONF).append(" = ? ,")
                        .append(DatabaseStructs.ODS_NODES.SCHEMA).append(" = ? ")
                        .append(" WHERE ")
                        .append(DatabaseStructs.ODS_NODES.ID).append(" = ?");
                updateNodeWithPasswordQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + updateNodeWithPasswordQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending ODSNodesDAO - updateODSNodeWithPassword");

            return jdbcTemplate.update(updateNodeWithPasswordQuery,
                    odsNodeModel.getAddress(),
                    odsNodeModel.getPort(),
                    odsNodeModel.getUserName(),
                    odsNodeModel.getPassword(),
                    odsNodeModel.getNumberOfSessions(),
                    odsNodeModel.getConcurrentCalls(),
                    odsNodeModel.getConnectionTimeout(),
                    odsNodeModel.getExtraConf(),
                    odsNodeModel.getSchema(),
                    odsNodeModel.getId()) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + updateNodeWithPasswordQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + updateNodeWithPasswordQuery, e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public boolean updateODSNodeWithoutPassword(ODSNodeModel odsNodeModel) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting ODSNodesDAO - updateODSNodeWithPassword");
        CCATLogger.DEBUG_LOGGER.debug("Start updating odsNodes with ID[" + odsNodeModel.getId() + "].");
        try {
            if (Objects.isNull(updateNodeWithoutPasswordQuery)) {
                StringBuilder query = new StringBuilder("");
                query.append("UPDATE ")
                        .append(DatabaseStructs.ODS_NODES.TABLE_NAME)
                        .append(" SET ")
                        .append(DatabaseStructs.ODS_NODES.ADDRESS).append(" = ? ,")
                        .append(DatabaseStructs.ODS_NODES.PORT).append(" = ? ,")
                        .append(DatabaseStructs.ODS_NODES.USER_NAME).append(" = ? ,")
                        .append(DatabaseStructs.ODS_NODES.NUMBER_OF_SESSIONS).append(" = ? ,")
                        .append(DatabaseStructs.ODS_NODES.CONCURRENT_CALLS).append(" = ? ,")
                        .append(DatabaseStructs.ODS_NODES.CONNECTION_TIMEOUT).append(" = ? ,")
                        .append(DatabaseStructs.ODS_NODES.EXTRA_CONF).append(" = ? ,")
                        .append(DatabaseStructs.ODS_NODES.SCHEMA).append(" = ? ")
                        .append(" WHERE ")
                        .append(DatabaseStructs.ODS_NODES.ID).append(" = ?");
                updateNodeWithoutPasswordQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + updateNodeWithoutPasswordQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending ODSNodesDAO - updateODSNodeWithPassword");

            return jdbcTemplate.update(updateNodeWithoutPasswordQuery,
                    odsNodeModel.getAddress(),
                    odsNodeModel.getPort(),
                    odsNodeModel.getUserName(),
                    odsNodeModel.getNumberOfSessions(),
                    odsNodeModel.getConcurrentCalls(),
                    odsNodeModel.getConnectionTimeout(),
                    odsNodeModel.getExtraConf(),
                    odsNodeModel.getSchema(),
                    odsNodeModel.getId()) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + updateNodeWithoutPasswordQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + updateNodeWithoutPasswordQuery, e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }
}