package com.asset.ccat.dynamic_page.database.dao;

import com.asset.ccat.dynamic_page.annotation.LogExecutionTime;
import com.asset.ccat.dynamic_page.database.extractors.DynamicPagesAndStepsExtractor;
import com.asset.ccat.dynamic_page.database.row_mapper.DynamicPagesRowMapper;
import com.asset.ccat.dynamic_page.defines.DatabaseStructs;
import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.models.dynamic_page.DynamicPageModel;
import com.asset.ccat.dynamic_page.models.requests.dynamic_page.AddDynamicPageRequest;
import com.asset.ccat.dynamic_page.models.requests.dynamic_page.UpdateDynamicPageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;

/**
 * @author assem.hassan
 */
@Repository
public class DynamicPagesDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DynamicPagesRowMapper dynamicPagesMapper;
    @Autowired
    private DynamicPagesAndStepsExtractor dynamicPagesAndStepsExtractor;
    private String retrieveAllDynamicPagesQuery;
    private String retrieveAllDnPagesWithStepsQuery;
    private String retrieveDnPageWithStepsQuery;
    private String retrieveDynamicPageByIdQuery;
    private String deleteDynamicPageByIdQuery;
    private String updateDynamicPageByIdQuery;
    private String addDynamicPageQuery;

    @LogExecutionTime
    public List<DynamicPageModel> retrieveAllDynamicPages() throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPagesDao - retrieveAllDynamicPages()");
        try {
            if (retrieveAllDynamicPagesQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("SELECT * ")
                        .append(" FROM ").append(DatabaseStructs.DYN_PAGES.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.DYN_PAGES.IS_DELETED).append("= 0")
                        .append(" ORDER BY ").append(DatabaseStructs.DYN_PAGES.CREATION_DATE).append(" DESC");
                retrieveAllDynamicPagesQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + retrieveAllDynamicPagesQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPagesDao - retrieveAllDynamicPages()");

            return jdbcTemplate.query(retrieveAllDynamicPagesQuery, dynamicPagesMapper);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while execute " + retrieveAllDynamicPagesQuery);
            CCATLogger.ERROR_LOGGER.error("Error while execute " + retrieveAllDynamicPagesQuery, ex);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public HashMap<Integer, DynamicPageModel> retrieveAllDnPagesWithSteps() throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPagesDao - retrieveAllDnPagesWithSteps()");
        try {
            if (retrieveAllDnPagesWithStepsQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("SELECT * ")
                        .append(" FROM ").append(DatabaseStructs.DYN_PAGES.TABLE_NAME).append(" dp")
                        .append(" INNER JOIN ").append(DatabaseStructs.DYN_PAGES_STEPS.TABLE_NAME).append(" dps")
                        .append(" ON ")
                        .append("dp.").append(DatabaseStructs.DYN_PAGES.ID)
                        .append(" = ")
                        .append("dps.").append(DatabaseStructs.DYN_PAGES_STEPS.PAGE_ID)
                        .append(" WHERE ")
                        .append("dp.").append(DatabaseStructs.DYN_PAGES.IS_DELETED).append("= 0")
                        .append(" AND ")
                        .append("dps.").append(DatabaseStructs.DYN_PAGES_STEPS.IS_DELETED).append(" = 0")
                        .append(" ORDER BY dps.").append(DatabaseStructs.DYN_PAGES_STEPS.STEP_ORDER).append(" ASC");
                retrieveAllDnPagesWithStepsQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + retrieveAllDnPagesWithStepsQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPagesDao - retrieveAllDnPagesWithSteps()");

            return jdbcTemplate.query(retrieveAllDnPagesWithStepsQuery, dynamicPagesAndStepsExtractor);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while execute " + retrieveAllDnPagesWithStepsQuery);
            CCATLogger.ERROR_LOGGER.error("Error while execute " + retrieveAllDnPagesWithStepsQuery, ex);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public DynamicPageModel retrieveDynamicPageById(Integer pageId) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPagesDao - retrieveDynamicPageById()");

        try {
            if (retrieveDynamicPageByIdQuery == null) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT * FROM ").append(DatabaseStructs.DYN_PAGES.TABLE_NAME)
                        .append(" WHERE ").append(DatabaseStructs.DYN_PAGES.ID)
                        .append(" = ?")
                        .append(" AND ")
                        .append(DatabaseStructs.DYN_PAGES.IS_DELETED).append("= 0");
                retrieveDynamicPageByIdQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPagesDao - retrieveDynamicPageById()");

            return jdbcTemplate.queryForObject(retrieveDynamicPageByIdQuery
                    , dynamicPagesMapper
                    , pageId);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveDynamicPageByIdQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveDynamicPageByIdQuery, ex);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public Integer addDynamicPage(AddDynamicPageRequest addDynamicPageRequest, Integer privilegeId) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPagesDao - addDynamicPage()");
        try {
            if (addDynamicPageQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("INSERT INTO ")
                        .append(DatabaseStructs.DYN_PAGES.TABLE_NAME)
                        .append(" (").append(DatabaseStructs.DYN_PAGES.ID).append(",")
                        .append(DatabaseStructs.DYN_PAGES.PAGE_NAME).append(",")
                        .append(DatabaseStructs.DYN_PAGES.PRIVILEGE_ID).append(",")
                        .append(DatabaseStructs.DYN_PAGES.PRIVILEGE_NAME).append(",")
                        .append(DatabaseStructs.DYN_PAGES.REQUIRE_SUBSCRIBER).append(") ")
                        .append("VALUES (")
                        .append(DatabaseStructs.DYN_PAGES.SEQUENCE_NAME).append(".NEXTVAL, ")
                        .append("?, ?, ?, ?)");
                addDynamicPageQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + addDynamicPageQuery);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update((Connection connection) -> {
                PreparedStatement ps = connection.prepareStatement(
                        addDynamicPageQuery, new String[]{DatabaseStructs.DYN_PAGES.ID});
                ps.setString(1, addDynamicPageRequest.getPageName());
                ps.setInt(2, privilegeId);
                ps.setString(3, addDynamicPageRequest.getPrivilegeName());
                ps.setInt(4, addDynamicPageRequest.getRequireSubscriber() ? 1 : 0);
                return ps;
            }, keyHolder);
            CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPagesDao - addDynamicPage()");

            return keyHolder.getKey() == null ? null : keyHolder.getKey().intValue();
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + addDynamicPageQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + addDynamicPageQuery, e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public boolean updateDynamicPage(UpdateDynamicPageRequest updateDynamicPageRequest) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPagesDao - updateDynamicPage()");
        try {
            if (updateDynamicPageByIdQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("UPDATE ")
                        .append(DatabaseStructs.DYN_PAGES.TABLE_NAME)
                        .append(" SET ")
                        .append(DatabaseStructs.DYN_PAGES.PAGE_NAME).append(" = ? ,")
                        .append(DatabaseStructs.DYN_PAGES.REQUIRE_SUBSCRIBER).append(" = ? ")
                        .append(" WHERE ").append(DatabaseStructs.DYN_PAGES.ID)
                        .append(" = ?");
                updateDynamicPageByIdQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + updateDynamicPageByIdQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPagesDao - updateDynamicPage()");

            return jdbcTemplate.update(updateDynamicPageByIdQuery, updateDynamicPageRequest.getPageName()
                    , updateDynamicPageRequest.getRequireSubscriber()
                    , updateDynamicPageRequest.getPageId()) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public boolean deleteDynamicPage(Integer pageId) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPagesDao - deleteDynamicPage()");
        try {
            if (deleteDynamicPageByIdQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("UPDATE ").append(DatabaseStructs.DYN_PAGES.TABLE_NAME)
                        .append(" SET ")
                        .append(DatabaseStructs.DYN_PAGES.IS_DELETED).append(" = 1")
                        .append(" WHERE ")
                        .append(DatabaseStructs.DYN_PAGES.ID).append(" = ?");
                deleteDynamicPageByIdQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + deleteDynamicPageByIdQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPagesDao - deleteDynamicPage()");

            return jdbcTemplate.update(deleteDynamicPageByIdQuery, pageId) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public HashMap<Integer,DynamicPageModel> retrieveDynamicPageWithSteps(Integer pageId) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPagesDao - retrieveDynamicPageWithSteps()");
        try {
            if (retrieveDnPageWithStepsQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("SELECT * ")
                        .append(" FROM ").append(DatabaseStructs.DYN_PAGES.TABLE_NAME).append(" dp")
                        .append(" LEFT JOIN ").append(DatabaseStructs.DYN_PAGES_STEPS.TABLE_NAME).append(" dps")
                        .append(" ON ")
                        .append("dp.").append(DatabaseStructs.DYN_PAGES.ID)
                        .append(" = ")
                        .append("dps.").append(DatabaseStructs.DYN_PAGES_STEPS.PAGE_ID)
                        .append(" AND ")
                        .append("dps.").append(DatabaseStructs.DYN_PAGES_STEPS.IS_DELETED).append(" = 0")
                        .append(" WHERE ")
                        .append("dp.").append(DatabaseStructs.DYN_PAGES.ID).append("= ?")
                        .append(" AND ")
                        .append("dp.").append(DatabaseStructs.DYN_PAGES.IS_DELETED).append("= 0")
                        .append(" ORDER BY dps.").append(DatabaseStructs.DYN_PAGES_STEPS.STEP_ORDER).append(" ASC");
                retrieveDnPageWithStepsQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + retrieveDnPageWithStepsQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPagesDao - retrieveDnPageWithStepsQuery()");

            return jdbcTemplate.query(retrieveDnPageWithStepsQuery, dynamicPagesAndStepsExtractor, pageId);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while execute " + retrieveDnPageWithStepsQuery);
            CCATLogger.ERROR_LOGGER.error("Error while execute " + retrieveDnPageWithStepsQuery, ex);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }
}
