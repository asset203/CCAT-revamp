package com.asset.ccat.lookup_service.database.dao;

import com.asset.ccat.lookup_service.annotation.LogExecutionTime;
import com.asset.ccat.lookup_service.database.row_mapper.DSSNodesRowMapper;
import com.asset.ccat.lookup_service.database.row_mapper.FlexShareHistoryNodeRowMapper;
import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.ods_models.DSSNodeModel;
import com.asset.ccat.lookup_service.models.ods_models.FlexShareHistoryNodeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class FlexShareHistoryNodesDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String retrieveAllNodesQuery;
    private String getAllFlexShareHistoryNodesForCachedLKQuery;
    private String addNodeQuery;
    private String deleteNodeByIdQuery;
    private String updateNodeWithPasswordQuery;
    private String updateNodeWithoutPasswordQuery;
    private String retrieveNodeByIdQuery;

    @LogExecutionTime
    public List<FlexShareHistoryNodeModel> retrieveFlexShareHistoryNodesForCachedLookup() throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting FlexShareHistoryNodesDao - retrieveFlexShareHistoryNodesForCachedLookup");
        List<FlexShareHistoryNodeModel> flexShareHistoryNodes = null;
        try {
            if (Objects.isNull(getAllFlexShareHistoryNodesForCachedLKQuery)) {
                getAllFlexShareHistoryNodesForCachedLKQuery = "SELECT * FROM "
                        + DatabaseStructs.FLEX_SHARE_HISTORY_NODES.TABLE_NAME
                        + " Order By "
                        + DatabaseStructs.FLEX_SHARE_HISTORY_NODES.ADDRESS
                        +" ASC ";
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + getAllFlexShareHistoryNodesForCachedLKQuery);
            flexShareHistoryNodes = jdbcTemplate.query(getAllFlexShareHistoryNodesForCachedLKQuery,
                    new BeanPropertyRowMapper<>(FlexShareHistoryNodeModel.class));
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + getAllFlexShareHistoryNodesForCachedLKQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + getAllFlexShareHistoryNodesForCachedLKQuery, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending FlexShareHistoryNodesDao - retrieveFlexShareHistoryNodesForCachedLookup");

        return flexShareHistoryNodes;
    }


    @LogExecutionTime
    public List<FlexShareHistoryNodeModel> retrieveFlexShareHistoryNodes() throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting FlexShareHistoryNodesDao - retrieveFlexShareHistoryNodes");
        List<FlexShareHistoryNodeModel> flexShareHistoryNodeList;
        try {
            if (Objects.isNull(retrieveAllNodesQuery)) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT ")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.ID).append(",")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.ADDRESS).append(",")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.PORT).append(",")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.USERNAME).append(",")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.NUMBER_OF_SESSIONS).append(",")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.CONCURRENT_CALLS).append(",")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.EXTRA_CONF).append(",")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.CONNECTION_TIMEOUT).append(",")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.SCHEMA)
                        .append(" FROM ")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.TABLE_NAME)
                        .append(" Order By ")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.ADDRESS)
                        .append(" ASC ");;
                retrieveAllNodesQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + retrieveAllNodesQuery);
            flexShareHistoryNodeList = jdbcTemplate.query(retrieveAllNodesQuery, new FlexShareHistoryNodeRowMapper());
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveAllNodesQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveAllNodesQuery, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending FlexShareHistoryNodesDao - retrieveFlexShareHistoryNodes");

        return flexShareHistoryNodeList;
    }

    @LogExecutionTime
    public boolean addFlexShareHistoryNode(FlexShareHistoryNodeModel flexShareHistoryNodeModel) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting FlexShareHistoryNodesDao - addFlexShareHistoryNode");
        CCATLogger.DEBUG_LOGGER.debug("Start adding flexShareHistoryNode with Address[" + flexShareHistoryNodeModel.getAddress() + "].");
        try {
            if (Objects.isNull(addNodeQuery)) {
                StringBuilder query = new StringBuilder("");
                query.append("INSERT INTO ")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.TABLE_NAME)
                        .append("(")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.ID).append(",")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.ADDRESS).append(",")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.PORT).append(",")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.USERNAME).append(",")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.PASSWORD).append(",")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.NUMBER_OF_SESSIONS).append(",")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.CONCURRENT_CALLS).append(",")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.CONNECTION_TIMEOUT).append(",")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.EXTRA_CONF).append(",")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.SCHEMA)
                        .append(") VALUES (")
                        .append(DatabaseStructs.SEQUENCE.S_FLEX_SHARE_HISTORY_NODES)
                        .append(".NEXTVAL,?,?,?,?,?,?,?,?,?)");
                addNodeQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + addNodeQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending FlexShareHistoryNodesDao - addFlexShareHistoryNode");

            return jdbcTemplate.update(addNodeQuery,
                    flexShareHistoryNodeModel.getAddress(),
                    flexShareHistoryNodeModel.getPort(),
                    flexShareHistoryNodeModel.getUsername(),
                    flexShareHistoryNodeModel.getPassword(),
                    flexShareHistoryNodeModel.getNumberOfSessions(),
                    flexShareHistoryNodeModel.getConcurrentCalls(),
                    flexShareHistoryNodeModel.getConnectionTimeout(),
                    flexShareHistoryNodeModel.getExtraConf(),
                    flexShareHistoryNodeModel.getSchema()) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + addNodeQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + addNodeQuery, e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public boolean deleteFlexShareHistoryNodeById(Integer flexShareHistoryNodeId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting FlexShareHistoryNodesDao - deleteFlexShareHistoryNodeById");
        CCATLogger.DEBUG_LOGGER.debug("Start deleting flexShareHistoryNode with ID[" + flexShareHistoryNodeId + "].");
        try {
            if (Objects.isNull(deleteNodeByIdQuery)) {
                StringBuilder query = new StringBuilder("");
                query.append("DELETE FROM ")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.ID)
                        .append(" = ?");
                deleteNodeByIdQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + deleteNodeByIdQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending FlexShareHistoryNodesDao - deleteFlexShareHistoryNodeById");

            return jdbcTemplate.update(deleteNodeByIdQuery, flexShareHistoryNodeId) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + deleteNodeByIdQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + deleteNodeByIdQuery, e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public FlexShareHistoryNodeModel retrieveFlexShareHistoryNodeById(Integer flexShareHistoryNodeId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting FlexShareHistoryNodesDao - retrieveFlexShareHistoryNodeById");
        CCATLogger.DEBUG_LOGGER.debug("Start deleting flexShareHistoryNode with ID[" + flexShareHistoryNodeId + "].");
        FlexShareHistoryNodeModel flexShareHistoryNodeModel;
        try {
            if (Objects.isNull(retrieveNodeByIdQuery)) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT ")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.ID).append(",")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.ADDRESS).append(",")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.PORT).append(",")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.USERNAME).append(",")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.NUMBER_OF_SESSIONS).append(",")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.CONCURRENT_CALLS).append(",")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.EXTRA_CONF).append(",")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.CONNECTION_TIMEOUT).append(",")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.SCHEMA)
                        .append(" FROM ")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.ID).append(" = ? ");
                retrieveNodeByIdQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + retrieveNodeByIdQuery);
            flexShareHistoryNodeModel = jdbcTemplate.queryForObject(retrieveNodeByIdQuery,
                    new FlexShareHistoryNodeRowMapper(),
                    flexShareHistoryNodeId);
        } catch (EmptyResultDataAccessException e) {
            throw new LookupException(ErrorCodes.ERROR.NO_DATA_FOUND);
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveNodeByIdQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveNodeByIdQuery, e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending  FlexShareHistoryNodesDao - retrieveFlexShareHistoryNodeById");

        return flexShareHistoryNodeModel;
    }

    @LogExecutionTime
    public boolean updateFlexShareHistoryNodeWithPassword(FlexShareHistoryNodeModel flexShareHistoryNodeModel) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting FlexShareHistoryNodesDao - updateFlexShareHistoryNodeWithPassword");
        CCATLogger.DEBUG_LOGGER.debug("Start updating flexShareHistoryNode with ID[" + flexShareHistoryNodeModel.getId() + "].");
        try {
            if (Objects.isNull(updateNodeWithPasswordQuery)) {
                StringBuilder query = new StringBuilder("");
                query.append("UPDATE ")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.TABLE_NAME)
                        .append(" SET ")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.ADDRESS).append(" = ? ,")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.PORT).append(" = ? ,")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.USERNAME).append(" = ? ,")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.PASSWORD).append(" = ? ,")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.NUMBER_OF_SESSIONS).append(" = ? ,")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.CONCURRENT_CALLS).append(" = ? ,")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.CONNECTION_TIMEOUT).append(" = ? ,")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.EXTRA_CONF).append(" = ? ,")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.SCHEMA).append(" = ? ")
                        .append(" WHERE ")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.ID).append(" = ?");
                updateNodeWithPasswordQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + updateNodeWithPasswordQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending FlexShareHistoryNodesDao - updateFlexShareHistoryNodeWithPassword");

            return jdbcTemplate.update(updateNodeWithPasswordQuery,
                    flexShareHistoryNodeModel.getAddress(),
                    flexShareHistoryNodeModel.getPort(),
                    flexShareHistoryNodeModel.getUsername(),
                    flexShareHistoryNodeModel.getPassword(),
                    flexShareHistoryNodeModel.getNumberOfSessions(),
                    flexShareHistoryNodeModel.getConcurrentCalls(),
                    flexShareHistoryNodeModel.getConnectionTimeout(),
                    flexShareHistoryNodeModel.getExtraConf(),
                    flexShareHistoryNodeModel.getSchema(),
                    flexShareHistoryNodeModel.getId()) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + updateNodeWithPasswordQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + updateNodeWithPasswordQuery, e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public boolean updateFlexShareHistoryNodeWithoutPassword(FlexShareHistoryNodeModel flexShareHistoryNodeModel) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting FlexShareHistoryNodesDao - updateFlexShareHistoryNodeWithoutPassword");
        CCATLogger.DEBUG_LOGGER.debug("Start updating flexShareHistoryNode with ID[" + flexShareHistoryNodeModel.getId() + "].");
        try {
            if (Objects.isNull(updateNodeWithoutPasswordQuery)) {
                StringBuilder query = new StringBuilder("");
                query.append("UPDATE ")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.TABLE_NAME)
                        .append(" SET ")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.ADDRESS).append(" = ? ,")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.PORT).append(" = ? ,")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.USERNAME).append(" = ? ,")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.NUMBER_OF_SESSIONS).append(" = ? ,")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.CONCURRENT_CALLS).append(" = ? ,")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.CONNECTION_TIMEOUT).append(" = ? ,")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.EXTRA_CONF).append(" = ? ,")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.SCHEMA).append(" = ? ")
                        .append(" WHERE ")
                        .append(DatabaseStructs.FLEX_SHARE_HISTORY_NODES.ID).append(" = ?");
                updateNodeWithoutPasswordQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + updateNodeWithoutPasswordQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending FlexShareHistoryNodesDao - updateFlexShareHistoryNodeWithoutPassword");

            return jdbcTemplate.update(updateNodeWithoutPasswordQuery, flexShareHistoryNodeModel.getAddress(),
                    flexShareHistoryNodeModel.getPort(),
                    flexShareHistoryNodeModel.getUsername(),
                    flexShareHistoryNodeModel.getNumberOfSessions(),
                    flexShareHistoryNodeModel.getConcurrentCalls(),
                    flexShareHistoryNodeModel.getConnectionTimeout(),
                    flexShareHistoryNodeModel.getExtraConf(),
                    flexShareHistoryNodeModel.getSchema(),
                    flexShareHistoryNodeModel.getId()) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + updateNodeWithoutPasswordQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + updateNodeWithoutPasswordQuery, e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }
}
