package com.asset.ccat.lookup_service.database.dao;


import com.asset.ccat.lookup_service.annotation.LogExecutionTime;
import com.asset.ccat.lookup_service.database.extractors.CommunitiesExtractor;
import com.asset.ccat.lookup_service.database.row_mapper.CommunityAdminRowMapper;
import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.CommunityAdminModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class CommunityAdminDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CommunitiesExtractor communitiesExtractor;

    private String retrieveCommunityAdmin;

    private String updateCommunityAdminQuery;

    private String deleteCommunityAdminQuery;

    private String addCommunityAdminQuery;

    private String findCommunityAdminByIdQuery;

    private String findCommunityAdminByNameQuery;

    @LogExecutionTime
    public List<CommunityAdminModel> retrieveCommunities() throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Starting CommunityAdminDao - retrieveCommunities");

        List<CommunityAdminModel> communities;

        try {
            if (retrieveCommunityAdmin == null) {
                StringBuilder query = new StringBuilder();
                query.append("Select * From ").append(DatabaseStructs.ADM_COMMUNITIES.TABLE_NAME)
                        .append(" Where ").append(DatabaseStructs.ADM_COMMUNITIES.IS_DELETED)
                        .append("= ?")
                        .append(" Order By ")
                        .append(DatabaseStructs.ADM_COMMUNITIES.COMMUNITY_ID)
                        .append(" ASC ");
                retrieveCommunityAdmin = query.toString();

            }

            CCATLogger.DEBUG_LOGGER.info("SqlStatement = " + retrieveCommunityAdmin);

            communities = jdbcTemplate.query(retrieveCommunityAdmin, new CommunityAdminRowMapper(), false);
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());

        }
        CCATLogger.DEBUG_LOGGER.info("result is " + communities);
        CCATLogger.DEBUG_LOGGER.debug("Ending CommunityAdminDao - retrieveCommunityAdmin");
        return communities;
    }


    @LogExecutionTime
    public int updateCommunityAdmin(CommunityAdminModel community) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting CommunityAdminDao - updateCommunityAdmin");

        try {
            if (updateCommunityAdminQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("update ")
                        .append(DatabaseStructs.ADM_COMMUNITIES.TABLE_NAME)
                        .append(" SET ").append(DatabaseStructs.ADM_COMMUNITIES.COMMUNITY_DESCRIPTION)
                        .append(" = ? ").append(" WHERE ")
                        .append(DatabaseStructs.ADM_COMMUNITIES.COMMUNITY_ID).append(" = ? ")
                        .append(" AND ").append(DatabaseStructs.ADM_COMMUNITIES.IS_DELETED).append(" = 0");
                updateCommunityAdminQuery = query.toString();
            }
            int res = jdbcTemplate.update(updateCommunityAdminQuery,
                    community.getCommunityDescription(),
                    community.getCommunityId()
            );
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + updateCommunityAdminQuery);
            CCATLogger.DEBUG_LOGGER.info("result is " + res);
            CCATLogger.DEBUG_LOGGER.debug("Ending CommunityAdminDao - updateCommunityAdmin");
            return res;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }


    @LogExecutionTime
    public int deleteCommunityAdmin(int communityId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting CommunityAdminDao - deleteCommunityAdmin");

        try {
            if (deleteCommunityAdminQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("update ").append(DatabaseStructs.ADM_COMMUNITIES.TABLE_NAME).append(" SET ").append(DatabaseStructs.ADM_COMMUNITIES.IS_DELETED).append(" = ? ").append(" WHERE ").append(DatabaseStructs.ADM_COMMUNITIES.COMMUNITY_ID).append(" = ?");
                deleteCommunityAdminQuery = query.toString();
            }
            int res = jdbcTemplate.update(deleteCommunityAdminQuery.toString(),
                    true,
                    communityId
            );
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + deleteCommunityAdminQuery);
            CCATLogger.DEBUG_LOGGER.info("result is " + res);
            CCATLogger.DEBUG_LOGGER.debug("Ending CommunityAdminDao - deleteCommunityAdmin");
            return res;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public int addCommunityAdmin(CommunityAdminModel community) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting CommunityAdminDao - addCommunityAdmin");

        try {
            if (addCommunityAdminQuery == null) {
                StringBuilder query = new StringBuilder();
                query
                        .append("INSERT INTO ").append(DatabaseStructs.ADM_COMMUNITIES.TABLE_NAME).append("(")
                        .append(DatabaseStructs.ADM_COMMUNITIES.COMMUNITY_ID)
                        .append(",").append(DatabaseStructs.ADM_COMMUNITIES.COMMUNITY_DESCRIPTION)
                        .append(") ")
                        .append("values ( ?,?)");
                addCommunityAdminQuery = query.toString();
            }
            int res = jdbcTemplate.update(addCommunityAdminQuery.toString(),
                    community.getCommunityId(),
                    community.getCommunityDescription()
            );
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + addCommunityAdminQuery);
            CCATLogger.DEBUG_LOGGER.info("result is " + res);
            CCATLogger.DEBUG_LOGGER.debug("Ending CommunityAdminDao - addCommunityAdmin");
            return res;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }


    }


    @LogExecutionTime
    public Integer findCommunityAdminById(int communityAdminId) throws LookupException {
        try {
            if (findCommunityAdminByIdQuery == null) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT COUNT(1) FROM ")
                        .append(DatabaseStructs.ADM_COMMUNITIES.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_COMMUNITIES.COMMUNITY_ID).append(" = ?")
                        .append(" AND ")
                        .append(DatabaseStructs.ADM_COMMUNITIES.IS_DELETED).append("= 0");
                findCommunityAdminByIdQuery = query.toString();

            }
            return jdbcTemplate.queryForObject(findCommunityAdminByIdQuery.toString(), Integer.class,
                    communityAdminId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + findCommunityAdminByIdQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + findCommunityAdminByIdQuery, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public List<CommunityAdminModel> findCommunityAdmin(CommunityAdminModel community) throws LookupException {
        try {
            if (findCommunityAdminByNameQuery == null) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT * FROM ")
                        .append(DatabaseStructs.ADM_COMMUNITIES.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_COMMUNITIES.COMMUNITY_DESCRIPTION).append(" = ?")
                        .append(" AND ")
                        .append(DatabaseStructs.ADM_COMMUNITIES.IS_DELETED).append("= 0");
                findCommunityAdminByNameQuery = query.toString();

            }
            return jdbcTemplate.query(findCommunityAdminByNameQuery.toString(),new CommunityAdminRowMapper(),
                    community.getCommunityDescription());
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + findCommunityAdminByNameQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + findCommunityAdminByNameQuery, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    public HashMap<Integer, CommunityAdminModel> retrieveAllCommunitiesAsMap() {
        HashMap<Integer, CommunityAdminModel> result = null;
        String sql = "";
        try {
            sql = "SELECT * FROM " + Defines.LOOKUPS.ADM_COMMUNITIES + " where is_deleted = 0"
                    + " Order By "
                    + DatabaseStructs.ADM_COMMUNITIES.COMMUNITY_DESCRIPTION
                    +" ASC ";
            result = jdbcTemplate.query(sql, communitiesExtractor);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while retrieving air servers" + sql);
            CCATLogger.ERROR_LOGGER.error("Error while retrieving air servers " + sql, ex);
        }
        return result;
    }

}
