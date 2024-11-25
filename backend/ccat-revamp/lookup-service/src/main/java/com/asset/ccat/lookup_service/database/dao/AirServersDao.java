package com.asset.ccat.lookup_service.database.dao;

import com.asset.ccat.lookup_service.annotation.LogExecutionTime;
import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.AIRServer;
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
public class AirServersDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String retrieveAllAirServersQuery;
    private String addAirServerQuery;
    private String deleteAirServerByIDQuery;
    private String updateAirServerQuery;
    private String retrieveAirServerByIdQuery;

    @LogExecutionTime
    public List<AIRServer> retrieveAllAirServers() throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting AirServersDao - retrieveAllAirServers()");
        List<AIRServer> airServerList;
        try {
            if (Objects.isNull(retrieveAllAirServersQuery)) {
                retrieveAllAirServersQuery = "SELECT * FROM "
                        + DatabaseStructs.LK_AIR_SERVERS.TABLE_NAME
                        + " Order By "
                        + DatabaseStructs.LK_AIR_SERVERS.AGENT_NAME
                        +" ASC ";
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + retrieveAllAirServersQuery);
            airServerList = jdbcTemplate.query(retrieveAllAirServersQuery, new BeanPropertyRowMapper<>(AIRServer.class));
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveAllAirServersQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveAllAirServersQuery, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending AirServersDao - retrieveAllAirServers()");

        return airServerList;
    }

    @LogExecutionTime
    public AIRServer retrieveAirServerById(Integer airServerId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting AirServersDao - retrieveAirServerById()");
        CCATLogger.DEBUG_LOGGER.debug("Start deleting airServer with ID[" + airServerId + "].");
        AIRServer airServer;
        try {
            if (Objects.isNull(retrieveAirServerByIdQuery)) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT  ")
                        .append(DatabaseStructs.LK_AIR_SERVERS.ID).append(",")
                        .append(DatabaseStructs.LK_AIR_SERVERS.URL).append(",")
                        .append(DatabaseStructs.LK_AIR_SERVERS.AGENT_NAME).append(",")
                        .append(DatabaseStructs.LK_AIR_SERVERS.HOST).append(",")
                        .append(DatabaseStructs.LK_AIR_SERVERS.AUTHORIZATION).append(",")
                        .append(DatabaseStructs.LK_AIR_SERVERS.IS_DOWN).append(",")
                        .append(DatabaseStructs.LK_AIR_SERVERS.CAPABILITY_VALUE)
                        .append(" FROM ")
                        .append(DatabaseStructs.LK_AIR_SERVERS.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.LK_AIR_SERVERS.ID).append(" = ? ")
                        .append(" Order By ")
                        .append(DatabaseStructs.LK_AIR_SERVERS.AGENT_NAME)
                        .append(" ASC ");
                retrieveAirServerByIdQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + retrieveAirServerByIdQuery);
            airServer = jdbcTemplate.queryForObject(retrieveAirServerByIdQuery,
                    new BeanPropertyRowMapper<>(AIRServer.class),
                    airServerId);
        } catch (EmptyResultDataAccessException e) {
            throw new LookupException(ErrorCodes.ERROR.NO_DATA_FOUND);
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveAirServerByIdQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveAirServerByIdQuery, e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending  AirServersDao - retrieveAirServerById()");

        return airServer;
    }

    @LogExecutionTime
    public boolean addAirServer(AIRServer airServer) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting AirServersDao - addAirServer()");
        CCATLogger.DEBUG_LOGGER.debug("Start adding AirServer with URL[" + airServer.getUrl() + "].");
        try {
            if (Objects.isNull(addAirServerQuery)) {
                StringBuilder query = new StringBuilder("");
                query.append("INSERT INTO ").append(DatabaseStructs.LK_AIR_SERVERS.TABLE_NAME)
                        .append("(")
                        .append(DatabaseStructs.LK_AIR_SERVERS.ID).append(", ")
                        .append(DatabaseStructs.LK_AIR_SERVERS.URL).append(", ")
                        .append(DatabaseStructs.LK_AIR_SERVERS.AGENT_NAME).append(", ")
                        .append(DatabaseStructs.LK_AIR_SERVERS.HOST).append(", ")
                        .append(DatabaseStructs.LK_AIR_SERVERS.AUTHORIZATION).append(", ")
                        .append(DatabaseStructs.LK_AIR_SERVERS.IS_DOWN).append(", ")
                        .append(DatabaseStructs.LK_AIR_SERVERS.CAPABILITY_VALUE)
                        .append(") VALUES (")
                        .append(DatabaseStructs.SEQUENCE.S_LK_AIR_SERVERS)
                        .append(".NEXTVAL,?,?,?,?,?,?)");
                addAirServerQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + addAirServerQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending AirServersDao - addAirServer()");

            return jdbcTemplate.update(addAirServerQuery,
                    airServer.getUrl(),
                    airServer.getAgentName(),
                    airServer.getHost(),
                    airServer.getAuthorization(),
                    airServer.getIsDown(),
                    airServer.getCapabilityValue()) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + addAirServerQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + addAirServerQuery, e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }


    @LogExecutionTime
    public boolean updateAirServer(AIRServer airServer) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting AirServersDao - updateAirServer()");
        CCATLogger.DEBUG_LOGGER.debug("Start updating AirServer with ID[" + airServer.getId() + "].");
        try {
            if (Objects.isNull(updateAirServerQuery)) {
                StringBuilder query = new StringBuilder("");
                query.append("UPDATE ").append(DatabaseStructs.LK_AIR_SERVERS.TABLE_NAME)
                        .append(" SET ")
                        .append(DatabaseStructs.LK_AIR_SERVERS.URL).append(" = ? ,")
                        .append(DatabaseStructs.LK_AIR_SERVERS.AGENT_NAME).append(" = ? ,")
                        .append(DatabaseStructs.LK_AIR_SERVERS.HOST).append(" = ? ,")
                        .append(DatabaseStructs.LK_AIR_SERVERS.AUTHORIZATION).append(" = ? ,")
                        .append(DatabaseStructs.LK_AIR_SERVERS.IS_DOWN).append(" = ? ,")
                        .append(DatabaseStructs.LK_AIR_SERVERS.CAPABILITY_VALUE).append(" = ? ")
                        .append(" WHERE ")
                        .append(DatabaseStructs.LK_AIR_SERVERS.ID).append(" = ? ");
                updateAirServerQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + updateAirServerQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending AirServersDao - updateAirServer()");

            return jdbcTemplate.update(updateAirServerQuery,
                    airServer.getUrl(),
                    airServer.getAgentName(),
                    airServer.getHost(),
                    airServer.getAuthorization(),
                    airServer.getIsDown(),
                    airServer.getCapabilityValue(),
                    airServer.getId()) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + updateAirServerQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + updateAirServerQuery, e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }


    @LogExecutionTime
    public boolean deleteAirServerById(Integer airServerId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting AirServersDao - deleteAirServerById()");
        CCATLogger.DEBUG_LOGGER.debug("Start deleting AirServer with ID[" + airServerId + "].");
        try {
            if (Objects.isNull(deleteAirServerByIDQuery)) {
                StringBuilder query = new StringBuilder();
                query.append("DELETE FROM ")
                        .append(DatabaseStructs.LK_AIR_SERVERS.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.LK_AIR_SERVERS.ID).append(" = ? ");
                deleteAirServerByIDQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + deleteAirServerByIDQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending AirServersDao - deleteAirServerById()");

            return jdbcTemplate.update(deleteAirServerByIDQuery, airServerId) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + deleteAirServerByIDQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + deleteAirServerByIDQuery, e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }
}