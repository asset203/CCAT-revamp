package com.asset.ccat.lookup_service.database.dao;


import com.asset.ccat.lookup_service.annotation.LogExecutionTime;
import com.asset.ccat.lookup_service.database.extractors.AccountGroupsWithBitsExtractor;
import com.asset.ccat.lookup_service.database.row_mapper.AccountGroupsRowMapper;
import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.AccountGroupBitDescModel;
import com.asset.ccat.lookup_service.models.AccountGroupModel;
import com.asset.ccat.lookup_service.models.AccountGroupWithBitsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Repository
public class AccountGroupsDao {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String retrieveAccountGroups;

    private String updateAccountGroupQuery;

    private String deleteAccountGroupQuery;

    private String addAccountGroupQuery;

    private String findAccountGroupByIdQuery;

    private String findAccountGroupByNameQuery;

    @LogExecutionTime
    public List<AccountGroupModel> retrieveAccountGroups() throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Starting AccountGroupsDao - retrieveAccountGroups");

        List<AccountGroupModel> accountGroups;

        try {
            if (retrieveAccountGroups == null) {
                StringBuilder query = new StringBuilder();
                query.append("Select * From ").
                        append(DatabaseStructs.ADM_ACCOUNT_GROUPS.TABLE_NAME)
                        .append(" Where ").append(DatabaseStructs.ADM_ACCOUNT_GROUPS.IS_DELETED)
                        .append("= ? ")
                        .append(" Order By ")
                        .append(DatabaseStructs.ADM_ACCOUNT_GROUPS.GROUP_ID)
                        .append(" ASC ");
                retrieveAccountGroups = query.toString();

            }

            CCATLogger.DEBUG_LOGGER.info("SqlStatement = " + retrieveAccountGroups);

            accountGroups = jdbcTemplate.query(retrieveAccountGroups.toString(), new AccountGroupsRowMapper(), false);
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());

        }
        CCATLogger.DEBUG_LOGGER.info("result is " + accountGroups);
        CCATLogger.DEBUG_LOGGER.debug("Ending AccountGroupsDao - retrieveAccountGroups");
        return accountGroups;
    }


    @LogExecutionTime
    public int updateAccountGroup(AccountGroupModel accountGroup) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting AccountGroupsDao - updateAccountGroup");

        try {
            if (updateAccountGroupQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("update ").
                        append(DatabaseStructs.ADM_ACCOUNT_GROUPS.TABLE_NAME)
                        .append(" SET ").append(DatabaseStructs.ADM_ACCOUNT_GROUPS.NAME)
                        .append(" = ? ").append(" WHERE ")
                        .append(DatabaseStructs.ADM_ACCOUNT_GROUPS.GROUP_ID)
                        .append(" = ? ")
                        .append(" AND ").append(DatabaseStructs.ADM_COMMUNITIES.IS_DELETED).append(" = 0");
                updateAccountGroupQuery = query.toString();
            }
            int res = jdbcTemplate.update(updateAccountGroupQuery.toString(),
                    accountGroup.getAccountGroupDescription(),
                    accountGroup.getAccountGroupId()
            );
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + updateAccountGroupQuery);
            CCATLogger.DEBUG_LOGGER.info("result is " + res);
            CCATLogger.DEBUG_LOGGER.debug("Ending AccountGroupsDao - updateAccountGroup");
            return res;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public int deleteAccountGroup(int accountGroupId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting AccountGroupsDao - updateAccountGroup");

        try {
            if (deleteAccountGroupQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("update ").append(DatabaseStructs.ADM_ACCOUNT_GROUPS.TABLE_NAME).append(" SET ").append(DatabaseStructs.ADM_ACCOUNT_GROUPS.IS_DELETED).append(" = ? ").append(" WHERE ").append(DatabaseStructs.ADM_ACCOUNT_GROUPS.GROUP_ID).append(" = ?");
                deleteAccountGroupQuery = query.toString();
            }
            int res = jdbcTemplate.update(deleteAccountGroupQuery.toString(),
                    true,
                    accountGroupId
            );
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + updateAccountGroupQuery);
            CCATLogger.DEBUG_LOGGER.info("result is " + res);
            CCATLogger.DEBUG_LOGGER.debug("Ending AccountGroupsDao - deleteAccountGroup");
            return res;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }


    @LogExecutionTime
    public int addAccountGroup(AccountGroupModel accountGroup) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting AccountGroupsDao - addAccountGroup");

        try {
            if (addAccountGroupQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("INSERT INTO ").append(DatabaseStructs.ADM_ACCOUNT_GROUPS.TABLE_NAME).append("(")
                        .append(DatabaseStructs.ADM_ACCOUNT_GROUPS.GROUP_ID)
                        .append(",").append(DatabaseStructs.ADM_ACCOUNT_GROUPS.NAME)
                        .append(") ")
                        .append("values ( ?,?)");
                addAccountGroupQuery = query.toString();

            }
            int res = jdbcTemplate.update(addAccountGroupQuery.toString(),
                    accountGroup.getAccountGroupId(),
                    accountGroup.getAccountGroupDescription()
            );
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + addAccountGroupQuery);
            CCATLogger.DEBUG_LOGGER.info("result is " + res);
            CCATLogger.DEBUG_LOGGER.debug("Ending AccountGroupsDao - addAccountGroup");
            return res;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public Integer findAccountGroupById(int accountGroupId) throws LookupException {
        try {
            if (findAccountGroupByIdQuery == null) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT COUNT(1) FROM ").append(DatabaseStructs.ADM_ACCOUNT_GROUPS.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_ACCOUNT_GROUPS.GROUP_ID).append(" = ?")
                        .append(" AND ")
                        .append(DatabaseStructs.ADM_ACCOUNT_GROUPS.IS_DELETED).append("= 0");
                findAccountGroupByIdQuery = query.toString();

            }
            return jdbcTemplate.queryForObject(findAccountGroupByIdQuery.toString(), Integer.class,
                    accountGroupId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + findAccountGroupByIdQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + findAccountGroupByIdQuery, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public List<AccountGroupModel> findAccountGroup(AccountGroupModel accountGroup) throws LookupException {
        try {
            if (findAccountGroupByNameQuery == null) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT * FROM ")
                        .append(DatabaseStructs.ADM_ACCOUNT_GROUPS.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_ACCOUNT_GROUPS.NAME).append(" = ?")
                        .append(" AND ")
                        .append(DatabaseStructs.ADM_ACCOUNT_GROUPS.IS_DELETED).append("= 0");
                findAccountGroupByNameQuery = query.toString();

            }
            return jdbcTemplate.query(findAccountGroupByNameQuery.toString(), new AccountGroupsRowMapper(),
                    accountGroup.getAccountGroupDescription());
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + findAccountGroupByNameQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + findAccountGroupByNameQuery, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public HashMap<Integer, AccountGroupWithBitsModel> retrieveAccountGroupsMap(List<AccountGroupBitDescModel> bitsLookup) {
        CCATLogger.DEBUG_LOGGER.info("Starting AccountGroupsDao - retrieveAccountGroupsMap");

        HashMap<Integer, AccountGroupWithBitsModel> accountGroups = null;

        try {
            if (retrieveAccountGroups == null) {
                StringBuilder query = new StringBuilder();
                query.append("Select * From ").
                        append(DatabaseStructs.ADM_ACCOUNT_GROUPS.TABLE_NAME)
                        .append(" Where ").append(DatabaseStructs.ADM_ACCOUNT_GROUPS.IS_DELETED)
                        .append("= ? ")
                        .append(" Order By ")
                        .append(DatabaseStructs.ADM_ACCOUNT_GROUPS.GROUP_ID)
                        .append(" ASC ");
                retrieveAccountGroups = query.toString();
            }

            CCATLogger.DEBUG_LOGGER.info("SqlStatement = " + retrieveAccountGroups);

            accountGroups = jdbcTemplate.query(retrieveAccountGroups,new AccountGroupsWithBitsExtractor(bitsLookup),0);
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
        }
        CCATLogger.DEBUG_LOGGER.info("result is " + accountGroups);
        CCATLogger.DEBUG_LOGGER.debug("Ending AccountGroupsDao - retrieveAccountGroupsMap");
        return accountGroups;
    }

}
