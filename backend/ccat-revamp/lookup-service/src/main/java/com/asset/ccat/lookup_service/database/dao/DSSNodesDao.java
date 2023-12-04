package com.asset.ccat.lookup_service.database.dao;

import com.asset.ccat.lookup_service.annotation.LogExecutionTime;
import com.asset.ccat.lookup_service.database.row_mapper.DSSNodesRowMapper;
import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.ods_models.DSSNodeModel;
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
public class DSSNodesDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String retrieveAllNodesQuery;
    private String retrieveDSSNodesForCachedLookupQuery;
    private String addNodeQuery;
    private String deleteNodeByIdQuery;
    private String updateNodeWithPasswordQuery;
    private String updateNodeWithoutPasswordQuery;
    private String retrieveNodeByIdQuery;

    @LogExecutionTime
    public List<DSSNodeModel> retrieveDSSNodesForCachedLookup() throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting DSSNodesDao - retrieveDSSNodesForCachedLookup");
        List<DSSNodeModel> dssNodes;
        try {
            if (Objects.isNull(retrieveDSSNodesForCachedLookupQuery)) {
                retrieveDSSNodesForCachedLookupQuery = "SELECT * FROM "
                        + DatabaseStructs.DSS_NODES.TABLE_NAME
                        + " Order By "
                        + DatabaseStructs.DSS_NODES.ADDRESS
                        +" ASC ";
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + retrieveDSSNodesForCachedLookupQuery);
            dssNodes = jdbcTemplate.query(retrieveDSSNodesForCachedLookupQuery, new BeanPropertyRowMapper<>(DSSNodeModel.class));
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveDSSNodesForCachedLookupQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveDSSNodesForCachedLookupQuery, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending DSSNodesDao - retrieveDSSNodesForCachedLookup");

        return dssNodes;
    }


    @LogExecutionTime
    public List<DSSNodeModel> retrieveDSSNodes() throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting DSSNodesDao - retrieveDSSNodes");
        List<DSSNodeModel> dssNodes;
        try {
            if (Objects.isNull(retrieveAllNodesQuery)) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT ")
                        .append(DatabaseStructs.DSS_NODES.ID).append(",")
                        .append(DatabaseStructs.DSS_NODES.ADDRESS).append(",")
                        .append(DatabaseStructs.DSS_NODES.PORT).append(",")
                        .append(DatabaseStructs.DSS_NODES.USER_NAME).append(",")
                        .append(DatabaseStructs.DSS_NODES.NUMBER_OF_SESSIONS).append(",")
                        .append(DatabaseStructs.DSS_NODES.CONCURRENT_CALLS).append(",")
                        .append(DatabaseStructs.DSS_NODES.EXTRA_CONF).append(",")
                        .append(DatabaseStructs.DSS_NODES.CONNECTION_TIMEOUT).append(",")
                        .append(DatabaseStructs.DSS_NODES.SCHEMA)
                        .append(" FROM ")
                        .append(DatabaseStructs.DSS_NODES.TABLE_NAME)
                        .append(" Order By ")
                        .append(DatabaseStructs.DSS_NODES.ADDRESS)
                        .append(" ASC ");
                retrieveAllNodesQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + retrieveAllNodesQuery);
            dssNodes = jdbcTemplate.query(retrieveAllNodesQuery, new BeanPropertyRowMapper<>(DSSNodeModel.class));
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveAllNodesQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveAllNodesQuery, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending DSSNodesDao - retrieveDSSNodes");

        return dssNodes;
    }

    @LogExecutionTime
    public boolean addDSSNode(DSSNodeModel dssNodeModel) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting DSSNodesDao - addDSSNode");
        CCATLogger.DEBUG_LOGGER.debug("Start adding dssNode with Address[" + dssNodeModel.getAddress() + "].");
        try {
            if (Objects.isNull(addNodeQuery)) {
                StringBuilder query = new StringBuilder("");
                query.append("INSERT INTO ")
                        .append(DatabaseStructs.DSS_NODES.TABLE_NAME)
                        .append("(")
                        .append(DatabaseStructs.DSS_NODES.ADDRESS).append(",")
                        .append(DatabaseStructs.DSS_NODES.PORT).append(",")
                        .append(DatabaseStructs.DSS_NODES.USER_NAME).append(",")
                        .append(DatabaseStructs.DSS_NODES.PASSWORD).append(",")
                        .append(DatabaseStructs.DSS_NODES.NUMBER_OF_SESSIONS).append(",")
                        .append(DatabaseStructs.DSS_NODES.CONCURRENT_CALLS).append(",")
                        .append(DatabaseStructs.DSS_NODES.CONNECTION_TIMEOUT).append(",")
                        .append(DatabaseStructs.DSS_NODES.EXTRA_CONF).append(",")
                        .append(DatabaseStructs.DSS_NODES.SCHEMA)
                        .append(")")
                        .append("VALUES ( ?, ? , ? , ? , ? , ? , ? , ? , ? )");
                addNodeQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + addNodeQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending DSSNodesDao - addDSSNode");

            return jdbcTemplate.update(addNodeQuery,
                    dssNodeModel.getAddress(),
                    dssNodeModel.getPort(),
                    dssNodeModel.getUserName(),
                    dssNodeModel.getPassword(),
                    dssNodeModel.getNumberOfSessions(),
                    dssNodeModel.getConcurrentCalls(),
                    dssNodeModel.getConnectionTimeout(),
                    dssNodeModel.getExtraConf(),
                    dssNodeModel.getSchema()) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + addNodeQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + addNodeQuery, e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public boolean deleteDSSNodeById(Integer dssNodeId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting DSSNodesDao - deleteDSSNodeById");
        CCATLogger.DEBUG_LOGGER.debug("Start deleting dssNode with ID[" + dssNodeId + "].");
        try {
            if (Objects.isNull(deleteNodeByIdQuery)) {
                StringBuilder query = new StringBuilder("");
                query.append("DELETE FROM ")
                        .append(DatabaseStructs.DSS_NODES.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.DSS_NODES.ID)
                        .append(" = ?");
                deleteNodeByIdQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + deleteNodeByIdQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending DSSNodesDao - deleteDSSNodeById");

            return jdbcTemplate.update(deleteNodeByIdQuery, dssNodeId) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + deleteNodeByIdQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + deleteNodeByIdQuery, e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public DSSNodeModel retrieveDSSNodeById(Integer dssNodeId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting DSSNodesDao - retrieveDSSNodeById");
        CCATLogger.DEBUG_LOGGER.debug("Start deleting dssNodes with ID[" + dssNodeId + "].");
        DSSNodeModel dssNodeModel;
        try {
            if (Objects.isNull(retrieveNodeByIdQuery)) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT  ")
                        .append(DatabaseStructs.DSS_NODES.ID).append(",")
                        .append(DatabaseStructs.DSS_NODES.ADDRESS).append(",")
                        .append(DatabaseStructs.DSS_NODES.PORT).append(",")
                        .append(DatabaseStructs.DSS_NODES.USER_NAME).append(",")
                        .append(DatabaseStructs.DSS_NODES.NUMBER_OF_SESSIONS).append(",")
                        .append(DatabaseStructs.DSS_NODES.CONCURRENT_CALLS).append(",")
                        .append(DatabaseStructs.DSS_NODES.EXTRA_CONF).append(",")
                        .append(DatabaseStructs.DSS_NODES.CONNECTION_TIMEOUT).append(",")
                        .append(DatabaseStructs.DSS_NODES.SCHEMA)
                        .append(" FROM ")
                        .append(DatabaseStructs.DSS_NODES.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.DSS_NODES.ID).append(" = ? ");
                retrieveNodeByIdQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + retrieveNodeByIdQuery);
            dssNodeModel = jdbcTemplate.queryForObject(retrieveNodeByIdQuery, new DSSNodesRowMapper(), dssNodeId);
        } catch (EmptyResultDataAccessException e) {
            throw new LookupException(ErrorCodes.ERROR.NO_DATA_FOUND);
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveNodeByIdQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveNodeByIdQuery, e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending  DSSNodesDao - retrieveDSSNodeById");

        return dssNodeModel;
    }

    @LogExecutionTime
    public boolean updateDSSNodeWithPassword(DSSNodeModel dssNodeModel) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting DSSNodesDao - updateDSSNodeWithPassword");
        CCATLogger.DEBUG_LOGGER.debug("Start updating dssNodes with ID[" + dssNodeModel.getId() + "].");
        try {
            if (Objects.isNull(updateNodeWithPasswordQuery)) {
                StringBuilder query = new StringBuilder("");
                query.append("UPDATE ")
                        .append(DatabaseStructs.DSS_NODES.TABLE_NAME)
                        .append(" SET ")
                        .append(DatabaseStructs.DSS_NODES.ADDRESS).append(" = ? ,")
                        .append(DatabaseStructs.DSS_NODES.PORT).append(" = ? ,")
                        .append(DatabaseStructs.DSS_NODES.USER_NAME).append(" = ? ,")
                        .append(DatabaseStructs.DSS_NODES.PASSWORD).append(" = ? ,")
                        .append(DatabaseStructs.DSS_NODES.NUMBER_OF_SESSIONS).append(" = ? ,")
                        .append(DatabaseStructs.DSS_NODES.CONCURRENT_CALLS).append(" = ? ,")
                        .append(DatabaseStructs.DSS_NODES.CONNECTION_TIMEOUT).append(" = ? ,")
                        .append(DatabaseStructs.DSS_NODES.EXTRA_CONF).append(" = ? ,")
                        .append(DatabaseStructs.DSS_NODES.SCHEMA).append(" = ? ")
                        .append(" WHERE ")
                        .append(DatabaseStructs.DSS_NODES.ID).append(" = ?");
                updateNodeWithPasswordQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + updateNodeWithPasswordQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending DSSNodesDao - updateDSSNodeWithPassword");

            return jdbcTemplate.update(updateNodeWithPasswordQuery,
                    dssNodeModel.getAddress(),
                    dssNodeModel.getPort(),
                    dssNodeModel.getUserName(),
                    dssNodeModel.getPassword(),
                    dssNodeModel.getNumberOfSessions(),
                    dssNodeModel.getConcurrentCalls(),
                    dssNodeModel.getConnectionTimeout(),
                    dssNodeModel.getExtraConf(),
                    dssNodeModel.getSchema(),
                    dssNodeModel.getId()) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + updateNodeWithPasswordQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + updateNodeWithPasswordQuery, e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public boolean updateDSSNodeWithoutPassword(DSSNodeModel dssNodeModel) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting DSSNodesDao - updateDSSNodeWithoutPassword");
        CCATLogger.DEBUG_LOGGER.debug("Start updating dssNodes with ID[" + dssNodeModel.getId() + "].");
        try {
            if (Objects.isNull(updateNodeWithoutPasswordQuery)) {
                StringBuilder query = new StringBuilder("");
                query.append("UPDATE ")
                        .append(DatabaseStructs.DSS_NODES.TABLE_NAME)
                        .append(" SET ")
                        .append(DatabaseStructs.DSS_NODES.ADDRESS).append(" = ? ,")
                        .append(DatabaseStructs.DSS_NODES.PORT).append(" = ? ,")
                        .append(DatabaseStructs.DSS_NODES.USER_NAME).append(" = ? ,")
                        .append(DatabaseStructs.DSS_NODES.NUMBER_OF_SESSIONS).append(" = ? ,")
                        .append(DatabaseStructs.DSS_NODES.CONCURRENT_CALLS).append(" = ? ,")
                        .append(DatabaseStructs.DSS_NODES.CONNECTION_TIMEOUT).append(" = ? ,")
                        .append(DatabaseStructs.DSS_NODES.EXTRA_CONF).append(" = ? ,")
                        .append(DatabaseStructs.DSS_NODES.SCHEMA).append(" = ? ")
                        .append(" WHERE ")
                        .append(DatabaseStructs.DSS_NODES.ID).append(" = ?");
                updateNodeWithoutPasswordQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + updateNodeWithoutPasswordQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending DSSNodesDao - updateDSSNodeWithoutPassword");

            return jdbcTemplate.update(updateNodeWithoutPasswordQuery, dssNodeModel.getAddress(),
                    dssNodeModel.getPort(),
                    dssNodeModel.getUserName(),
                    dssNodeModel.getNumberOfSessions(),
                    dssNodeModel.getConcurrentCalls(),
                    dssNodeModel.getConnectionTimeout(),
                    dssNodeModel.getExtraConf(),
                    dssNodeModel.getSchema(),
                    dssNodeModel.getId()) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + updateNodeWithoutPasswordQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + updateNodeWithoutPasswordQuery, e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }
}